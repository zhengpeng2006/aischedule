package com.asiainfo.schedule.core.center;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.Constants.ScheduleType;
import com.asiainfo.schedule.core.utils.DateUtils;

/**
 * 任务时间调度 quartz实现
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年9月18日
 * @modify
 */
public class TaskSchedulerForQuartz implements ITaskScheduler {
	private static final Logger logger = LoggerFactory.getLogger(TaskSchedulerForQuartz.class);
	private static AtomicInteger serial = new AtomicInteger(0);
	private final String startJobGroupName;
	private final String endJobGroupName;
	private final String startTriggerGroupName;
	private final String endTriggerGroupName;
	private Scheduler scheduler;
	private String managerId;

	private Map<String, TaskBean> taskMap = new HashMap<String, TaskBean>();

	TaskSchedulerForQuartz() throws Exception {
		/**
		 * org.quartz.scheduler.instanceName = ai-scheduler
		 * org.quartz.scheduler.instanceId = AUTO
		 * 
		 * org.quartz.threadPool.class = org.quartz.simpl.SimpleThreadPool
		 * org.quartz.threadPool.threadCount = 2
		 * org.quartz.threadPool.threadPriority = 5
		 * 
		 * org.quartz.jobStore.misfireThreshold = 60000 org.quartz.jobStore.class =
		 * org.quartz.simpl.RAMJobStore
		 */
		Properties prop = new Properties();
		prop.setProperty("org.quartz.scheduler.instanceName", "ai-scheduler");
		prop.setProperty("org.quartz.scheduler.instanceId", "AUTO");
		prop.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		prop.setProperty("org.quartz.threadPool.threadCount", ""
				+ DataManagerFactory.getDataManager().getScheduleConfig().getScheduleThreadPoolSize());
		prop.setProperty("org.quartz.threadPool.threadPriority", "5");
		prop.setProperty("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
		SchedulerFactory objSchedulerFactory = new StdSchedulerFactory(prop);
		int no = serial.incrementAndGet();
		startJobGroupName = "START-JOB-GROUP-" + no;
		endJobGroupName = "END-JOB-GROUP-" + no;
		startTriggerGroupName = "START-TRIGGER-GROUP-" + no;
		endTriggerGroupName = "END-TRIGGER-GROUP-" + no;
		scheduler = objSchedulerFactory.getScheduler();
		scheduler.start();
		logger.info("初始化Quartz调度器成功！");
	}

	@Override
	public void setManager(String managerId) {
		this.managerId = managerId;
	}

	@Override
	public void shutdown() {
		try {
			scheduler.shutdown();
			taskMap.clear();
		} catch (Exception ex) {
			logger.error("shutdown exception!", ex);
		}
	}

	@Override
	public void removeTask(String taskCode) {
		try {
			scheduler.deleteJob(taskCode, startJobGroupName);
			scheduler.deleteJob(taskCode, endJobGroupName);
		} catch (SchedulerException e) {
			logger.error("delete job exception!", e);
		}
		taskMap.remove(taskCode);
	}

	@Override
	public void removeAllTask() {
		try {
			String[] allJobs = scheduler.getJobNames(this.startJobGroupName);
			for (String job : allJobs) {
				logger.debug("delete job:" + job);
				removeTask(job);
			}
		} catch (SchedulerException e) {
			logger.error("delete job exception!", e);
		}

	}

	private void addTask(TaskBean task) {
		String startTime = task.getStartTime();
		if (task == null || CommonUtils.isBlank(startTime)) {
			return;
		}

		String triggerName = task.getTaskCode();
		Trigger startTrigger = null;
		Trigger endTrigger = null;
		String endTime = task.getEndTime();
		long curTime = System.currentTimeMillis();
		try {
			if (startTime.startsWith("run$")) {
				startTime = startTime.substring(startTime.indexOf("run$") + "run$".length());
				Date startDate = null;
				if (startTime.equalsIgnoreCase("now")) {
					startDate = new Date(System.currentTimeMillis() + 5000);
				} else {
					startDate = DateUtils.parseYYYYMMddHHmmss(startTime);
					if (startDate.getTime() - curTime < 0) {
						logger.warn("任务配置的开始时间小于当前系统时间，不再调度。");
						return;
					}
				}
				startTrigger = new SimpleTrigger(triggerName, startTriggerGroupName, startDate, null, 0, 0);
				// add stop job
				if (!CommonUtils.isBlank(endTime)) {
					endTrigger = new SimpleTrigger(triggerName, endTriggerGroupName, DateUtils.parseYYYYMMddHHmmss(endTime), null, 0, 0);
				}
			} else {
				startTrigger = new CronTrigger(triggerName, startTriggerGroupName, CommonUtils.translateCron(startTime));
				// add stop job
				if (!CommonUtils.isBlank(endTime)) {
					endTrigger = new CronTrigger(triggerName, endTriggerGroupName, CommonUtils.translateCron(endTime));
				}
			}

			String jobName = task.getTaskCode();
			if (startTrigger != null) {
				JobDetail job = new JobDetail(jobName, startJobGroupName, TaskJob.class);
				job.getJobDataMap().put("TASK_VERSION", task.getVersion()+"");
				job.getJobDataMap().put("TASK_CODE", task.getTaskCode());
				job.getJobDataMap().put("TASK_NAME", task.getTaskName());
				job.getJobDataMap().put("SCHEDULE_TYPE", ScheduleType.start.name());
				job.getJobDataMap().put("MANAGER_ID", this.managerId);
				scheduler.scheduleJob(job, startTrigger);
			}

			if (endTrigger != null) {
				JobDetail job = new JobDetail(jobName, endJobGroupName, TaskJob.class);
				job.getJobDataMap().put("TASK_VERSION", task.getVersion()+"");
				job.getJobDataMap().put("TASK_CODE", task.getTaskCode());
				job.getJobDataMap().put("TASK_NAME", task.getTaskName());
				job.getJobDataMap().put("SCHEDULE_TYPE", ScheduleType.end.name());
				job.getJobDataMap().put("MANAGER_ID", this.managerId);
				scheduler.scheduleJob(job, endTrigger);
			}
			taskMap.put(task.getTaskCode(), task);
		} catch (Exception ex) {
			logger.error("addtask error!"+task.getTaskCode(), ex);
		}
	}

	@Override
	public void refreshScheduleTask(List<TaskBean> tasks) throws Exception {

		if (tasks == null || tasks.size() == 0) {
			this.removeAllTask();
		}
		// purge 清理
		for (String taskCode : this.taskMap.keySet()) {
			boolean isFound = false;
			for (TaskBean task : tasks) {
				if (task.getTaskCode().equals(taskCode)) {
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				this.removeTask(taskCode);
			}
		}

		for (TaskBean task : tasks) {
			String taskCode = task.getTaskCode();
			if (!taskMap.containsKey(taskCode)) {
				this.addTask(task);
			} else {
				long version = taskMap.get(taskCode).getVersion();
				logger.debug("cache task[" + taskCode + "]version:" + version + ",fact version:" + task.getVersion());
				if (task.getVersion() > version) {
					logger.info("任务版本存在更新，重新调度！" + taskCode);
					this.removeTask(taskCode);
					this.addTask(task);
				}
			}
		}
	}

	@Override
	public SchedulerStat stats() {
		SchedulerStat stat = new SchedulerStat();
		try {
			stat.setActiveCount(scheduler.getCurrentlyExecutingJobs().size());
			String[] triggers = scheduler.getTriggerNames(this.startTriggerGroupName);
			if (triggers == null)
				return stat;
			stat.setScheduleTaskCount(triggers.length);
			String[] endTriggers = scheduler.getTriggerNames(this.endTriggerGroupName);
			stat.setEndTimeTaskCount(endTriggers == null ? 0 : endTriggers.length);
			stat.setTaskCount(this.taskMap.size());
			stat.setManageTaskList(this.taskMap.keySet());
			List<TaskStat> taskstats = new ArrayList<TaskStat>(triggers.length);
			for (String t : triggers) {
				Trigger trigger = scheduler.getTrigger(t, this.startTriggerGroupName);
				TaskStat tstat = new TaskStat();
				tstat.setTaskCode(t);
				tstat.setCurVersion(this.taskMap.get(t).getVersion());
				if (trigger.getPreviousFireTime() != null) {
					tstat.setLastSchedTime(DateUtils.formatYYYYMMddHHmmss(trigger.getPreviousFireTime()));
				}
				if (trigger.getNextFireTime() != null) {
					tstat.setNextSchedTime(DateUtils.formatYYYYMMddHHmmss(trigger.getNextFireTime()));
				}

				Trigger endTrigger = scheduler.getTrigger(t, this.endTriggerGroupName);
				if (endTrigger != null) {
					tstat.setNextEndTime(DateUtils.formatYYYYMMddHHmmss(endTrigger.getNextFireTime()));
				}
				taskstats.add(tstat);
			}
			stat.setTaskStats(taskstats);
		} catch (SchedulerException e) {
			logger.error("", e);
		}
		return stat;
	}

}
