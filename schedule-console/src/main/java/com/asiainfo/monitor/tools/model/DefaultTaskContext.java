package com.asiainfo.monitor.tools.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.common.TaskWorker;
import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.common.interfaces.IWorker;
import com.asiainfo.monitor.tools.exe.task.model.TaskRtnModel;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.interfaces.IContext;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;
import com.asiainfo.monitor.tools.model.interfaces.ITaskContext;

public class DefaultTaskContext extends BaseContainer implements ITaskContext, Serializable
{

    private final static transient Log log = LogFactory.getLog(DefaultTaskContext.class);

    // 标识码
    private String code;

    // 执行方式
    private String execMethod;

    // 结果
    private TaskRtnModel rtnModel = null;

    private List cmdContainers = new LinkedList();

    // 执行方式,asyn:true异步执行
    private boolean asyn = false;

    private ThreadPoolExecutor executor;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getExecMethod()
    {
        return execMethod;
    }

    public void setExecMethod(String execMethod)
    {
        this.execMethod = execMethod;
    }

    public void setCmdContainers(List cmdContainers)
    {
        this.cmdContainers = cmdContainers;
        if(this.cmdContainers != null && this.cmdContainers.size() > 0) {
            for(int i = 0; i < this.cmdContainers.size(); i++) {
                ((IContext) this.cmdContainers.get(i)).setParent(this);
            }
        }
    }

    public void putCmdItem(ITaskCmdContainer cmdItem)
    {
        this.cmdContainers.add(cmdItem);
    }

    public boolean isAsyn()
    {
        return asyn;
    }

    public void setAsyn(boolean asyn)
    {
        this.asyn = asyn;
    }

    public int size()
    {
        if(cmdContainers == null)
            return 0;
        return this.cmdContainers.size();
    }

    public List getCmdContainers()
    {
        return this.cmdContainers;
    }

    public TaskRtnModel getRtnModel()
    {
        return rtnModel;
    }

    public void setRtnModel(TaskRtnModel rtnModel)
    {
        this.rtnModel = rtnModel;
    }

    public void addTaskRtn(ITaskRtn taskRtn)
    {
        if(rtnModel == null) {
            if(l.tryLock()) {
                try {
                    if(rtnModel == null)
                        rtnModel = new TaskRtnModel();
                }
                finally {
                    l.unlock();
                }
            }
        }
        rtnModel.addRtn(taskRtn);
    }

    // 执行命令
    public void action() throws Exception
    {
        // 这里判断有问题，cmdContainers不可能为null的
        if(cmdContainers == null || cmdContainers.size() < 1) {
            // 命令集没有具体命令
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000253"));
        }
        // 调用前触发事件，初始化事件参数或者立即和固定执行，检查任务状态
        fireContainerEvent(BaseContainer._DO_BEFORE_EVENT, attach);

        // 是否异步
        if(isAsyn()) {
            signal = new CountDownLatch(cmdContainers.size());
            int cpuCount = Runtime.getRuntime().availableProcessors();
            int maxCount = cpuCount * 5;
            // 如果命令个数小于cpu的个数的3倍，则统一将默认线程数设置为cpu个数,否则设置为cpu个数的3倍
            int coreCount = cmdContainers.size() < (cpuCount * 3) ? cpuCount : cpuCount * 3;
            if(coreCount > cmdContainers.size()) {
                coreCount = cmdContainers.size();
            }
            executor = new ThreadPoolExecutor(coreCount, maxCount, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(cpuCount * 10),
                    new ThreadPoolExecutor.CallerRunsPolicy());
        }

        for(int i = 0; i < cmdContainers.size(); i++) {
            if(isAsyn()) {
                ((IContext) cmdContainers.get(i)).setSignal(signal);
                executor.execute(new TaskWorker((IWorker) cmdContainers.get(i)));
            }
            else {
                ((IWorker) cmdContainers.get(i)).action();
            }
        }
        if(isAsyn()) {
            try {
                signal.await();
            }
            catch(InterruptedException e) {
                throw new Exception(e);
            }
            if(executor != null) {
                executor.shutdownNow();
                executor = null;
            }
        }
        // 如果为立即执行或固定执行，则修改任务状态为已执行
        fireContainerEvent(BaseContainer._DO_AFTER_EVENT, attach);
    }

    public void clear()
    {
        if(this.rtnModel != null)
            this.rtnModel.clear();
    }

}
