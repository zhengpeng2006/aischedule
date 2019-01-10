package com.asiainfo.monitor.busi.exe.task;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;

import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

/**
 * 
 * 调度启动的主进程
 * 
 * 调度系统分为三大子系统 1、作业系统 也就是个性化的需要完成的任务接口的实现 2、触发系统 定义为触发作业系统去工作的系统。 现在分为，立即触发、cron（周期）触发、定时触发 3、调度系统
 * 调度系统为主要进程，去协调处理作业系统和触发系统
 * 
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: AI(NanJing)
 * </p>
 * 
 * @author Yang Hua
 * @version 3.0
 */
public class MonitorProcTask
{
    private transient static Log log = LogFactory.getLog(MonitorProcTask.class);

    // 数据分拆
    public static final String TASK_SPLIT = "SPLIT_ID";
    // 数据分片mod
    public static final String DATASPLIT_MOD = "DATASPLIT_MOD";
    // 数据分片的value
    public static final String DATASPLIT_VALUE = "DATASPLIT_VALUE";
    public static final String MON_P_INFO = "MON_P_INFO";
    public static final String TASK_JOB_GROUP = "TASK_JOB_GROUP";
    public static final String TASK_TRIGGER_GROUP = "TASK_TRIGGER_GROUP";
    private static boolean IS_MERGE_EXEC = false;

    static {
        String str = System.getProperty("mon.is_merge_exec");
        if(!StringUtils.isBlank(str) && (str.trim().equalsIgnoreCase("1") || str.trim().equalsIgnoreCase("Y"))) {
            IS_MERGE_EXEC = true;
            if(log.isInfoEnabled())
                // 配置采用合并任务
                log.info(AIMonLocaleFactory.getResource("MOS0000189"));
        }
    }

    public MonitorProcTask()
    {
    }

    public static boolean isMergeExecTask()
    {
        return IS_MERGE_EXEC;
    }

    public static void main(String[] args) throws Exception
    {

        TaskRegFactory regFactory = new TaskRegFactory();

        //注册并初始化定时扫描任务
        Map<String, Object> schedulerMap = regFactory.regTaskScanJob(args);

        Scheduler objScheduler = (Scheduler) schedulerMap.get("SCHEDULER");
        long bootPauseSeconds = (Long) schedulerMap.get("BOOT_PAUSE_TIME");

        //注册主机资源采集任务
        regFactory.regTaskAcqHostApiJob(objScheduler);

        // 启动调度系统
        if(log.isInfoEnabled()) {
            // 开始启动调度系统........
            log.info(AIMonLocaleFactory.getResource("MOS0000197"));
        }

        //启动任务调度
        objScheduler.start();

        // 启动休眠指定时间
        Thread.currentThread().sleep(bootPauseSeconds);

        if(log.isInfoEnabled()) {
        	//启动调度系统完成
            log.info(AIMonLocaleFactory.getResource("MOS0000198"));
        }

    }

}
