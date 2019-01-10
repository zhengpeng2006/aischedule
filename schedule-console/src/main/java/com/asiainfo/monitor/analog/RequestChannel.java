package com.asiainfo.monitor.analog;

import java.util.LinkedList;
import java.util.Queue;

import com.asiainfo.monitor.acquire.dto.BusiErrLogBean;
import com.asiainfo.monitor.acquire.dto.BusiLogBean;

/**
 * 生产者管道，最多可容纳100个生产请求
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class RequestChannel
{
    private final int MAX_CNT = 100;
    //请求队列
    private Queue<BusiLogBean> queue = new LinkedList<BusiLogBean>();
    
    private Queue<BusiErrLogBean> errLogQueue = new LinkedList<BusiErrLogBean>();

    public synchronized void putRequest(BusiLogBean log)
    {
        System.out.println("..put in channel ...");
        while(queue.size() >= MAX_CNT) {
            try {
                System.out.println("....put wait();");
                wait();
            }
            catch(InterruptedException e) {
                System.out.println("putRequest error");
            }
        }
        
        queue.add(log);
        notifyAll();
    }
    
    public synchronized BusiLogBean takeRequest(){
        
        System.out.println("..take from channel...");
        while(queue.size() <= 0) {
            try {
                System.out.println(".... take wait();");
                wait();
            }
            catch(InterruptedException e) {
                System.out.println("take request error.");
            }
        }
        BusiLogBean busiLogBean = queue.remove();

        notifyAll();

        return busiLogBean;

    }

    public synchronized void putErrRequest(BusiErrLogBean log)
    {
        System.out.println("..put in channel ...");
        while(errLogQueue.size() >= MAX_CNT) {
            try {
                System.out.println("....put wait();");
                wait();
            }
            catch(InterruptedException e) {
                System.out.println("putRequest error");
            }
        }

        errLogQueue.add(log);
        notifyAll();
    }

    public synchronized BusiErrLogBean takeErrLogRequest()
    {

        System.out.println("..take from error log channel...");
        while(errLogQueue.size() <= 0) {
            try {
                System.out.println(".... take wait();");
                wait();
            }
            catch(InterruptedException e) {
                System.out.println("take error log request error.");
            }
        }
        BusiErrLogBean busiLogBean = errLogQueue.remove();

        notifyAll();

        return busiLogBean;

    }

}
