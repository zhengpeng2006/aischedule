/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.socket.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 默认实现
 *
 * @author sundedong(grucee)
 */
public class DefaultOperationFuture implements OperationFuture {

    private static final transient Log LOG = LogFactory.getLog(OperationFuture.class);

    private Object attachment;
    private boolean done = false;
    private Throwable cause = null;
    private int waiters = 0;

    private JSONObject result = null;
    //回掉方法
    private final List<OperationListener> listeners = new ArrayList<OperationListener>();

    public DefaultOperationFuture() {
    }
    
    public DefaultOperationFuture(Object attachment) {
        this.attachment = attachment;
    }
    
    @Override
    public synchronized boolean isDone() {
        return done;
    }

    @Override
    public synchronized boolean isSuccess() {
        return done && cause == null;
    }

    @Override
    public synchronized Throwable getCause() {
       return cause;
    }
    
    @Override
    public boolean setResult(JSONObject result) {
        synchronized(this) {
            if (done) {
                return false;
            }
            
            done = true;
            this.result = result;
            if (waiters > 0) {
                notifyAll();
            }
        }
        
        notifyListeners();
        return true;
    }
    
    /**
     * 无超时等待
     *
     * @return
     * @throws InterruptedException
     */
    @Override
    public JSONObject getResult() throws InterruptedException {
        await();
        return getResultWithoutWait();
    }

    /**
     * 超时等待
     * @param timeoutMillis
     * @return
     * @throws InterruptedException 
     */
    @Override
    public JSONObject getResult(long timeoutMillis) throws Exception {
//        boolean finished = await(timeoutMillis);
//        if (!finished) {
//            throw new Exception("time out.");
//        }
        await(timeoutMillis);
        return getResultWithoutWait();
    }

    @Override
    public JSONObject getResult(long timeout, TimeUnit unit) throws Exception {
//        boolean finished = await(timeout, unit);
//         if (!finished) {
//            throw new Exception("time out.");
//        }
        await(timeout, unit);
        return getResultWithoutWait();
    }

    //此处有锁
    private synchronized JSONObject getResultWithoutWait() {
        return this.result;
    }
    
    private void await() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        synchronized (this) {
            while (!done) {
                waiters++;
                try {
                    wait();
                } finally {
                    waiters--;
                }
            }
        }
    }

    private boolean await(long timeout, TimeUnit unit)
            throws InterruptedException {
        return await0(unit.toNanos(timeout), true);
    }

    private boolean await(long timeoutMillis) throws InterruptedException {
        return await0(MILLISECONDS.toNanos(timeoutMillis), true);
    }

    private OperationFuture awaitUninterruptibly() {
        boolean interrupted = false;
        synchronized (this) {
            while (!done) {
                waiters++;
                try {
                    wait();
                } catch (InterruptedException e) {
                    interrupted = true;
                } finally {
                    waiters--;
                }
            }
        }

        if (interrupted) {
            Thread.currentThread().interrupt();
        }

        return this;
    }

    private boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
        try {
            return await0(unit.toNanos(timeout), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    private boolean awaitUninterruptibly(long timeoutMillis) {
        try {
            return await0(MILLISECONDS.toNanos(timeoutMillis), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    private boolean await0(long timeoutNanos, boolean interruptable) throws InterruptedException {
        if (interruptable && Thread.interrupted()) {
            throw new InterruptedException();
        }

        long startTime = timeoutNanos <= 0 ? 0 : System.nanoTime();
        long waitTime = timeoutNanos;
        boolean interrupted = false;

        try {
            synchronized (this) {
                if (done || waitTime <= 0) {
                    return done;
                }

                waiters++;
                try {
                    for (;;) {
                        try {
                            wait(waitTime / 1000000, (int) (waitTime % 1000000));
                        } catch (InterruptedException e) {
                            if (interruptable) {
                                throw e;
                            } else {
                                interrupted = true;
                            }
                        }

                        if (done) {
                            return true;
                        } else {
                            waitTime = timeoutNanos - (System.nanoTime() - startTime);
                            if (waitTime <= 0) {
                                return done;
                            }
                        }
                    }
                } finally {
                    waiters--;
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void addListener(OperationListener listener) {
        boolean notifyNow = false;
        synchronized (this) {
            if (done) {
                notifyNow = true;
            } else {
                listeners.add(listener);
            }
        }

        if (notifyNow) {
            notifyListener(listener);
        }
    }

    private void notifyListeners() {
        for (OperationListener listener : listeners) {
            notifyListener(listener);
        }
    }
    
    private void notifyListener(OperationListener listener) {
        listener.operation(this);
    }
    
    @Override
    public boolean setFailure(Throwable cause) {
        synchronized (this) {
            // Allow only once.
            if (done) {
                return false;
            }

            this.cause = cause;
            done = true;
            if (waiters > 0) {
                notifyAll();
            }
        }

        notifyListeners();
        return true;
    }

    @Override
    public Object getAttachment() {
        return this.attachment;
    }
}
