package com.asiainfo.monitor.busi.exe.task.job;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import com.asiainfo.monitor.busi.exe.task.MonitorProcTask;
import com.asiainfo.monitor.busi.exe.task.impl.BackTimingTaskFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BackTimingTaskContext;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;
import com.asiainfo.monitor.tools.util.TypeConst;

/**
 * 扫描任务数据的作业
 * 
 * 处理逻辑
 * 1、此作业每隔5分钟扫描task这个表，扫描的间隔时间可以灵活配置 扫描task表中的task_state为0和2 
 * 2、扫描数据后，获得当前调度器中正在执行的任务，如果发现有正在执行的任务，那么此任务ID不会更新到调度器中
 * 等待下一次的扫描数据并且作业处于未执行状态，那么才会更新此任务 
 * 3、如果可以更新任务，那么更新任务操作的顺序是 
 *      1) 首先删除此任务，并且删除此任务关联的触发器 
 *      2) 新建任务，新建触发器，将此任务和触发器关联加入到调度器中
 * <p> Title: </p> 
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2008 </p>
 * <p> Company: AI(NanJing) </p>
 * @author Yang Hua
 * @version 3.0
 */
public class TaskScanJob implements Job
{
    private transient static Log log = LogFactory.getLog(TaskScanJob.class);
    public final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TaskScanJob()
    {
    }

    /**
     * 真正的执行任务
     * 
     * @param context JobExecutionContext
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        try {
            _execute(context);
        }
        catch(Throwable ex) {
            log.error("Call TaskScanJob's method execute has Exception :", ex);
        }
    }

    /**
     * 内部执行任务
     * 
     * @param context JobExecutionContext
     * @throws Exception
     */
    private void _execute(JobExecutionContext context) throws Exception
    {

        // "扫描任务执行"
        if(log.isInfoEnabled()) {
            log.info(AIMonLocaleFactory.getResource("MOS0000199"));
        }

        //获取传递来的任务参数
        JobDataMap data = context.getJobDetail().getJobDataMap();
        long split = 0, mod = 1, value = 0;
        if(data.containsKey(MonitorProcTask.TASK_SPLIT)) {
            split = data.getLong(MonitorProcTask.TASK_SPLIT);
        }
        if(data.containsKey(MonitorProcTask.DATASPLIT_MOD)) {
            mod = data.getLong(MonitorProcTask.DATASPLIT_MOD);
        }
        if(data.containsKey(MonitorProcTask.DATASPLIT_VALUE)) {
            value = data.getLong(MonitorProcTask.DATASPLIT_VALUE);
        }

        /****************** 获取定时任务执行信息**********************************/
        BackTimingTaskContext[] task = BackTimingTaskFactory.getBackTimingTaskContext(split, mod, value);
        if(task == null || task.length == 0) {
            if(log.isInfoEnabled()) {
                log.info(AIMonLocaleFactory.getResource("MOS0000200"));
            }
            return;
        }

        //移除调度器中尚未执行的task
        List runningJobs = this.removeNotRuningTask(context);


        //合并任务
        if(MonitorProcTask.isMergeExecTask()) {

            //分离任务:将同一Key(InfoId或hostId+timeId+^_EXEC)的任务放到一起
            HashMap<String, List<BackTimingTaskContext>> tmpTaskmap = this.divideTaskList(task, runningJobs);

            // 合并相同Key的任务
            Set key = tmpTaskmap.keySet();
            for(Iterator iter = key.iterator(); iter.hasNext();) {
                String itemKey = (String) iter.next();
                List list = (List) tmpTaskmap.get(itemKey);

                BackTimingTaskContext obj = (BackTimingTaskContext) list.get(0);

                // job
                String jobName = "job_" + itemKey;
                String jobGroupName = MonitorProcTask.TASK_JOB_GROUP;

                //trigger
                String triggerName = "trigger_" + itemKey;
                String triggerGroupName = MonitorProcTask.TASK_TRIGGER_GROUP;

                JobDetail job = new JobDetail(jobName, jobGroupName, TaskJob.class);

                Trigger trigger = null;
                // 周期执行
                String cron = obj.getTime().getExpr();
                // "任务ID:" + obj.getId() + ",配置的周期执行时间为空"
                if(StringUtils.isBlank(cron)) {
                    log.error(AIMonLocaleFactory.getResource("MOS0000205", obj.getId()));
                    continue;
                }
                trigger = new CronTrigger(triggerName, triggerGroupName, cron);

                // 传入task_id这个参数
                job.getJobDataMap().put(MonitorProcTask.MON_P_INFO, list);

                context.getScheduler().scheduleJob(job, trigger);

                // 加入调度的任务ID:
                if(log.isDebugEnabled()) {
                    log.debug(AIMonLocaleFactory.getResource("MOS0000207") + obj.getId());
                }
            }
        }
        // 不合并任务
        else {
            // 加入任务
            for(int i = 0; i < task.length; i++) {
                String infoId = task[i].getId();
                try {
                    // 判断此任务是否正在执行
                    if(isRunning("job_" + infoId, MonitorProcTask.TASK_JOB_GROUP, runningJobs)) {
                        if(log.isInfoEnabled())
                            // 任务正在运行，此次不会加载此条数据.等待此任务运行结束,并且下次扫描的时候,才会加载此数据
                            log.info("job:" + infoId + "," + AIMonLocaleFactory.getResource("MOS0000202"));
                        continue;
                    }

                    // 删除作业和触发器后，重新加载作业和触发器到调度器中
                    String jobName = "job_" + infoId;
                    String jobGroupName = MonitorProcTask.TASK_JOB_GROUP;

                    String triggerName = "trigger_" + infoId;
                    String triggerGroupName = MonitorProcTask.TASK_TRIGGER_GROUP;

                    JobDetail job = new JobDetail(jobName, jobGroupName, TaskJob.class);

                    Trigger trigger = null;

                    // 周期执行
                    String cron = task[i].getTime().getExpr();
                    if(StringUtils.isBlank(cron)) {
                        // 任务ID:{0},配置的周期执行时间为空
                        log.error(AIMonLocaleFactory.getResource("MOS0000205", infoId));
                        continue;
                    }
                    if(task[i].getExecMethod().equals(TypeConst._TASKMETHOD_F)) {
                        try {
                            trigger = new SimpleTrigger(triggerName, triggerGroupName, DATE_FORMAT.parse(cron), null, 0, 0);
                        }
                        catch(ParseException ex) {
                            // 任务ID:" +task[i].getId()+ ",配置的固定执行时间错误
                            log.error(AIMonLocaleFactory.getResource("MOS0000206", task[i].getId()), ex);
                            continue;
                        }
                    }
                    else if(task[i].getExecMethod().equals(TypeConst._TASKMETHOD_C)) {
                        trigger = new CronTrigger(triggerName, triggerGroupName, cron);
                    }
                    else if(task[i].getExecMethod().equals(TypeConst._TASKMETHOD_I)) {
                        trigger = new SimpleTrigger(triggerName, triggerGroupName, new Date(), null, 0, 0);
                    }

                    // 传入task_id这个参数
                    job.getJobDataMap().put(MonitorProcTask.MON_P_INFO, task[i]);

                    context.getScheduler().scheduleJob(job, trigger);

                    if(log.isDebugEnabled()) {
                        // 加入调度的任务ID:
                        log.debug(AIMonLocaleFactory.getResource("MOS0000207") + infoId);
                    }
                }
                catch(Exception ex1) {
                    // 任务ID:" + infoId + ",无法调度
                    log.error(AIMonLocaleFactory.getResource("MOS0000208", infoId), ex1);
                    continue;
                }
            }
        }

    }

    /**
     * 从调度器中移除尚未运行的Task，并返回运行中的task
     * @param context
     * @return
     * @throws SchedulerException
     * @throws Exception
     */
    private List removeNotRuningTask(JobExecutionContext context) throws SchedulerException, Exception
    {
        // 获得正在运行的任务
        List runningJobs = context.getScheduler().getCurrentlyExecutingJobs();

        if(log.isDebugEnabled()) {
            for(Iterator iter = runningJobs.iterator(); iter.hasNext();) {
                JobExecutionContext item = (JobExecutionContext) iter.next();
                // 如果是基类,打印出当前正在运行的任务
                if(item.getJobInstance() instanceof TaskJob) {
                    BackTimingTaskContext taskContext = (BackTimingTaskContext) item.getJobDetail().getJobDataMap().get(MonitorProcTask.MON_P_INFO);
                    String jobClass = item.getJobDetail().getJobClass().getName();

                    // "系统中还有未完成的任务,任务ID:{0},执行类:{1}"
                    log.debug(AIMonLocaleFactory.getResource("MOS0000201", taskContext.getId(), jobClass));
                }
            }
        }

        // 调度器中可以清除的任务，清除掉
        // task表中任务被删除了,那么必须先把调度器中的任务删除,否则这个任务一直没有删除,造成此任务一直执行,除非重启服务
        String[] jobNames = context.getScheduler().getJobNames(MonitorProcTask.TASK_JOB_GROUP);
        if(jobNames != null && jobNames.length != 0) {
            for(int i = 0; i < jobNames.length; i++) {
                // 判断此任务是否正在执行
                if(isRunning(jobNames[i], MonitorProcTask.TASK_JOB_GROUP, runningJobs)) {
                    if(log.isInfoEnabled()) {
                        // 任务正在运行，此次不会加载此条数据.等待此任务运行结束,并且下次扫描的时候,才会加载此数据
                        log.info("job:" + jobNames[i] + "," + AIMonLocaleFactory.getResource("MOS0000202"));
                    }
                    continue;
                }

                // 此任务现在肯定是未执行,删除调度器中的此任务和触发器
                try {
                    removeJobAndTrigger(jobNames[i], MonitorProcTask.TASK_JOB_GROUP, context);
                }
                catch(Exception ex2) {
                    if(log.isInfoEnabled()) {
                        // 从调度器中删除失败,现在不删除,下次删除
                        log.info("job:" + jobNames[i] + "," + AIMonLocaleFactory.getResource("MOS0000203"), ex2);
                    }
                    continue;
                }
            }
        }
        return runningJobs;
    }

    /**
     * 分离任务
     * @param task
     * @param runningJobs
     * @return
     */
    private HashMap<String, List<BackTimingTaskContext>> divideTaskList(BackTimingTaskContext[] task, List runningJobs)
    {
        // "提供任务合并支持"
        HashMap<String, List<BackTimingTaskContext>> tmpTaskmap = new HashMap<String, List<BackTimingTaskContext>>();
        // 加入任务
        for(int i = 0; i < task.length; i++) {
            String infoId = task[i].getId();
            try {
                String key = null;
                // 主键 = 主机标识^时间段标识^EXEC
                if(!StringUtils.isBlank(task[i].getHostId()) && task[i].getTime().getId() > 0
                        && ((ITaskCmdContainer) task[i].getCmdContainers().get(0)).getType().equals(ITaskCmdContainer._TASK_EXEC)) {

                    key = task[i].getHostId() + "^" + task[i].getTime().getId() + "^" + ITaskCmdContainer._TASK_EXEC;
                }
                else {
                    key = String.valueOf(infoId);
                }

                // 判断此任务是否正在执行
                if(isRunning("job_" + key, MonitorProcTask.TASK_JOB_GROUP, runningJobs)) {
                    // "job名称:"+ key+ ",任务正在运行，此次不会加载此条数据.等待此任务运行结束,并且下次扫描的时候,才会加载此数据"
                    log.info("job:" + infoId + "," + AIMonLocaleFactory.getResource("MOS0000202"));
                    continue;
                }
                if(tmpTaskmap.containsKey(key)) {
                    List list = (List) tmpTaskmap.get(key);
                    list.add(task[i]);
                }
                else {
                    List list = new ArrayList();
                    list.add(task[i]);
                    tmpTaskmap.put(key, list);
                }
            }
            catch(Exception ex1) {
                log.error("任务ID:" + infoId + ",无法调度", ex1);
                continue;
            }
        }
        return tmpTaskmap;
    }

    /**
     * 是否处于运行中
     * 
     * @param jobName String
     * @param groupName String
     * @param runningJobs List
     * @throws Exception
     * @return boolean
     */
    private boolean isRunning(String jobName, String groupName, List runningJobs) throws Exception
    {
        boolean rtn = false;
        for(Iterator iter = runningJobs.iterator(); iter.hasNext();) {
            JobExecutionContext item = (JobExecutionContext) iter.next();
            if(item.getJobDetail().getName().equalsIgnoreCase(jobName) && item.getJobDetail().getGroup().equalsIgnoreCase(groupName)) {
                rtn = true;
                break;
            }
        }
        return rtn;
    }

    /**
     * 
     * @param jobName String
     * @param groupName String
     * @param context JobExecutionContext
     * @throws Exception
     */
    private void removeJobAndTrigger(String jobName, String groupName, JobExecutionContext context) throws Exception
    {
        // 删除job的时候，会将关联的trigger一起删除
        boolean rtn = context.getScheduler().deleteJob(jobName, groupName);
        if(log.isDebugEnabled()) {
            if(rtn) {
                // "删除的job名称:" + jobName
                log.debug(AIMonLocaleFactory.getResource("MOS0000209", jobName));
            }
            else {
                // job名称:" + jobName + "在调度器中没有找到,所以不需要删除
                log.debug(AIMonLocaleFactory.getResource("MOS0000210", jobName));
            }
        }
    }

}
