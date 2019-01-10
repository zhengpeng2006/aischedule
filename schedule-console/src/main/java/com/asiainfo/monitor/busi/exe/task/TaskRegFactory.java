package com.asiainfo.monitor.busi.exe.task;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.asiainfo.monitor.busi.exe.task.job.TaskAcqHostApiJob;
import com.asiainfo.monitor.busi.exe.task.job.TaskScanJob;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.Resource;

/**
 * 
 * 任务注册工厂，专门用于注册定时任务服务
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class TaskRegFactory
{
    private transient static Log log = LogFactory.getLog(TaskRegFactory.class);
    private static final String LOG_PROPERTIES = "LOG_CFG_FILE";

    private int split = 0;
    private int mod = 1;
    private int value = 0;
    private String scanDataJobCron = "0 0/5 * * * ?"; // 默认的scanDataJob.cron的参数，每隔5分钟
    //任务扫描的和主机数据采集的定时时间区分开
    private String acqHostDataCron = "0 0/5 * * * ?";//主机数据采集间隔

    /**
     * 初始化任务日志
     * @throws Exception
     */
    public void iniTaskLog() throws Exception
    {
        /**************** 初始化日志，调度任务可有自己的日志 ***************************/
        String logCfgFile = System.getProperty(LOG_PROPERTIES);
        if(!StringUtils.isBlank(logCfgFile)) {
            InputStream input = new FileInputStream(logCfgFile);

            if(input != null) {
                Properties logProperties = new Properties();
                logProperties.load(input);
                PropertyConfigurator.configure(logProperties);
            }
        }
        if(log.isInfoEnabled()) {
            // 提醒:由于在一个线程中使用的数据库连接可能超过1个，请至少配置的连接池的最大数量为线程数量的1.5倍!
            log.info(AIMonLocaleFactory.getResource("MOS0000190"));
        }
    }

    /**
     * 注册扫描任务，主要定时扫描ai_mon_p_info表里的任务，并加入到调度引擎中
     * 
     * @param args
     * @return
     * @throws Exception
     */
    public Map<String, Object> regTaskScanJob(String[] args) throws Exception
    {
        //初始化任务参数
        this.initTaskParam(args);

        //初始化任务属性
        Properties prop = new Properties();
        long bootPauseSeconds = this.getJobTaskProperties(prop);

        SchedulerFactory objSchedulerFactory = new StdSchedulerFactory(prop);
        Scheduler objScheduler = objSchedulerFactory.getScheduler();

        try {

            JobDetail job = new JobDetail("TaskScanJob", "TaskScanJobGrp", TaskScanJob.class);

            Trigger trigger = new CronTrigger("TaskScanTrigger", "TaskScanTriggerGrp", scanDataJobCron);
            job.getJobDataMap().put(MonitorProcTask.TASK_SPLIT, new Long(split));
            job.getJobDataMap().put(MonitorProcTask.DATASPLIT_MOD, new Long(mod));
            job.getJobDataMap().put(MonitorProcTask.DATASPLIT_VALUE, new Long(value));
            objScheduler.scheduleJob(job, trigger);
        }
        // 扫描作业加入失败,系统退出
        catch(Exception ex) {
            log.error(AIMonLocaleFactory.getResource("MOS0000196"), ex);
            System.exit(-1);
        }

        Map<String, Object> schedulerMap = new HashMap<String, Object>();
        schedulerMap.put("SCHEDULER", objScheduler);
        schedulerMap.put("BOOT_PAUSE_TIME", bootPauseSeconds);

        return schedulerMap;
    }

    /**
     * 注册主机API信息采集任务
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public void regTaskAcqHostApiJob(Scheduler scheduler) throws Exception
    {
        try {
            JobDetail job = new JobDetail("TaskAcqHostApiJob", "TaskAcqHostApiJobGrp", TaskAcqHostApiJob.class);
            Trigger trigger = new CronTrigger("TaskAcqHostApiTrigger", "TaskAcqHostApiTriggerGrp", this.acqHostDataCron);
            scheduler.scheduleJob(job, trigger);

            log.info("..acquire host kpi time interval:" + this.acqHostDataCron);
        }
        catch(Exception ex) {
            log.error(AIMonLocaleFactory.getResource("MOS0000196"), ex);
            System.exit(-1);
        }

        // 启动调度系统
        if(log.isInfoEnabled()) {
            // 开始启动调度系统…………
            log.info("start host information acquire thread ...");
        }
    }

    /**
     * 初始化任务参数
     * @param args
     */
    private void initTaskParam(String[] args)
    {
        // 加载配置参数完成,取数据分片参数
        if(args != null && args.length == 3 && !StringUtils.isBlank(args[0]) && StringUtils.isNumeric(args[0]) && StringUtils.isNumeric(args[1])
                && !StringUtils.isBlank(args[1]) && !StringUtils.isBlank(args[2]) && StringUtils.isNumeric(args[2])) {

            split = Integer.valueOf(args[0].trim()).intValue();
            mod = Integer.valueOf(args[1].trim()).intValue();
            value = Integer.valueOf(args[2].trim()).intValue();

            // 配置了任务数据分片,此进程的数据分片参数配置为
            log.error(AIMonLocaleFactory.getResource("MOS0000194") + ": split=" + split + ",mod=" + mod + ",value=" + value);
            // 提示:由于配置了数据分片,请将其它数据分片的进程启动,否则会有部分任务没有处理!
            if(log.isInfoEnabled()) {
                log.info(AIMonLocaleFactory.getResource("MOS0000308"));
            }
        }
        else {
            // 没有配置任务数据分片,本进程取全部任务数据,请确保不要启动多份此进程
            if(log.isInfoEnabled()) {
                log.info(AIMonLocaleFactory.getResource("MOS0000195"));
            }
        }
    }

    /**
     * 初始化任务属性
     * @param prop
     * @return
     * @throws Exception
     */
    private long getJobTaskProperties(Properties prop) throws Exception
    {
        long bootPauseSeconds = 3; // 默认的启动暂停为3秒
        //读取任务配置文件
        Properties taskProp = Resource.loadPropertiesFromClassPath("task.properties", "task", true);
        prop.putAll(taskProp);
        //启动暂停时间配置
        String bootPauseTime = prop.getProperty("bootPauseSeconds");
        if(!StringUtils.isBlank(bootPauseTime) && StringUtils.isNumeric(bootPauseTime)) {
            bootPauseSeconds = Long.parseLong(bootPauseTime.trim());
        }
        if(!StringUtils.isBlank(prop.getProperty("acqHostDataCron"))) {
            this.acqHostDataCron = prop.getProperty("acqHostDataCron").trim();
        }
        
        if(!StringUtils.isBlank(prop.getProperty("scanDataJobCron"))) {
            scanDataJobCron = prop.getProperty("scanDataJobCron").trim();
        }
        else {
            // 没有配置scanDataJobCron参数,取默认的配置
            if(log.isInfoEnabled()) {
                log.info(AIMonLocaleFactory.getResource("MOS0000191") + ":" + scanDataJobCron);
            }
        }

        if(log.isInfoEnabled()) {
            log.info(AIMonLocaleFactory.getResource("MOS0000192", bootPauseSeconds + "")); // "启动暂停时间为:" + bootPauseSeconds + "秒"
            log.info(AIMonLocaleFactory.getResource("MOS0000193") + ":" + scanDataJobCron); // scanDataJobCron参数配置为
        }

        return bootPauseSeconds;
    }

}
