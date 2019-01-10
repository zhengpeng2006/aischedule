package com.asiainfo.monitor.busi.exe.task.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.asiainfo.monitor.busi.exe.task.MonitorProcTask;
import com.asiainfo.monitor.busi.exe.task.impl.MonMergeTaskImpl;
import com.asiainfo.monitor.busi.exe.task.impl.MonTaskImpl;
import com.asiainfo.monitor.tools.model.BackTimingTaskContext;

/**
 * 所有作业任务的基础包装job 可以被继承
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: AI(NanJing)</p>
 * 
 * @author Yang Hua
 * @version 1.0
 */
public class TaskJob implements Job
{

    private transient static Log log = LogFactory.getLog(TaskJob.class);

    public TaskJob()
    {
    }

    /**
     * job中的需要实现的执行方法
     * 
     * @param context JobExecutionContext
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        // 参数准备
        JobDataMap data = context.getJobDetail().getJobDataMap();
        // 获取要执行的任务
        Object dataObj = data.get(MonitorProcTask.MON_P_INFO);

        List list = null;
        if(dataObj instanceof ArrayList) {
            list = (ArrayList) dataObj;
        }
        else if(dataObj instanceof BackTimingTaskContext) {
            list = new ArrayList();
            list.add(dataObj);
        }

        try {
            if(list != null) {
                if(list.size() == 1) {
                    // 普通任务
                    new MonTaskImpl().doTask(list);
                }
                else {
                    // 合并任务的执行
                    new MonMergeTaskImpl().doTask(list);
                }
            }

        }
        catch(Throwable ex) {
            log.error("Call TaskJob's method execute has Exception :", ex);
        }
    }

}
