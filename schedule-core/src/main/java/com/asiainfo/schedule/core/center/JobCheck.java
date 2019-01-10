package com.asiainfo.schedule.core.center;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.po.ServerBean;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskItemJobBean;
import com.asiainfo.schedule.core.po.TaskJobBean;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.Constants;
import com.asiainfo.schedule.core.utils.Constants.JobSts;
import com.asiainfo.schedule.core.utils.Constants.RunSts;
import com.asiainfo.schedule.core.utils.Constants.TaskType;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.core.utils.SchedulerLogger;

/**
 * 调度任务作业ID的状态检查
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月28日
 * @modify
 */
public class JobCheck implements Callable<Boolean> {
	private static final Logger logger = LoggerFactory.getLogger(JobCheck.class);
	TaskBean task;
	DataManagerFactory dmFactory;
	String managerId;
	int taskMaxAlarmTime;
	

	public JobCheck(DataManagerFactory dmFactory, TaskBean task, String managerId) {
		this.task = task;
		this.dmFactory = dmFactory;
		this.managerId = managerId;
		try {
			if (this.dmFactory.getScheduleConfig().getItemJobAlarmTime() == 0) {
				this.taskMaxAlarmTime = this.dmFactory.getScheduleConfig().getHeartbeatInterval() * 3;
			} else {
				this.taskMaxAlarmTime = this.dmFactory.getScheduleConfig().getItemJobAlarmTime();
			}
		} catch (Exception e) {
			logger.error("task");
			this.taskMaxAlarmTime = 30000;
		}
	}

	public JobCheck(TaskBean task, String managerId) throws Exception {
		this(DataManagerFactory.getDataManager(), task, managerId);
	}

	@Override
	public Boolean call() throws Exception {

		try {
			String sts = this.dmFactory.getTaskRunSts(task.getTaskCode());
			String jobId = this.dmFactory.getTaskJobId(task.getTaskCode());
			if (TaskType.tf.name().equals(task.getTaskType()) && CommonUtils.isBlank(jobId) && RunSts.start.name().equals(sts)
					&& "run$now".equals(task.getStartTime())) {
				TaskJobBean newJob = this.dmFactory.createTaskJob(task.getTaskCode(), null, managerId);
				if (newJob == null) {
					return Boolean.FALSE;
				}
				if (logger.isInfoEnabled()) {
					logger.info("重新创建TF任务作业流水成功!!" + newJob.getJobId());
				}
				// 记录日志
				SchedulerLogger.createTaskLog(task.getTaskCode(), task.getTaskName(), task.getVersion() + "", newJob.getJobId(),
						newJob.getCreateTime(), "C");
				SchedulerLogger.addTaskLogDtl(task.getTaskCode(), task.getVersion() + "", newJob.getJobId(),
						Constants.ServerType.manager.name(), "", managerId, "重新创建任务", "");
				return Boolean.TRUE;
			}

			if (CommonUtils.isBlank(jobId) && !RunSts.stop.name().equals(sts)) {
				this.dmFactory.setTaskRunSts(task.getTaskCode(), RunSts.stop.name());
				return Boolean.TRUE;
			} else if (CommonUtils.isBlank(jobId)) {
				return Boolean.TRUE;
			}
			List<String> itemJobIds = this.dmFactory.getAllTaskItemJobId(task.getTaskCode(), jobId);
			if (itemJobIds == null || itemJobIds.size() == 0) {
				String exMsg = "";
				TaskJobBean jobBean = this.dmFactory.getTaskJobByJobId(task.getTaskCode(), jobId);
				if (jobBean != null && JobSts.C.name().equals(jobBean.getSts())) {
					// 30s内如果状态还是新建，则执行清除操作。解决创建任务流水时出现连接异常导致数据不一致的情况。20150409
					if((System.currentTimeMillis() - DateUtils.parseYYYYMMddHHmmss(jobBean.getCreateTime()).getTime()) < 30000){
						logger.info("任务【" + task.getTaskCode() + "】对应的作业流水【" + jobId + "】正在创建。。。");
						return Boolean.TRUE;
					}else{
						exMsg = "异常：任务流水号新建时超过30秒未处理成功，执行清除操作。";
					}
				}
				logger.info("任务【" + task.getTaskCode() + "】对应的作业流水【" + jobId + "】所有任务项已经处理完成，移除任务流水号。");
				this.dmFactory.deleteTaskJob(task.getTaskCode(), jobId);
				// 对于tf类型的任务不能把状态自动改为停止，否则调度器在重新生成作业流水时无法区分出人为终止还是重新分派时自动终止。
				if (!RunSts.stop.name().equals(sts) && !TaskType.tf.name().equals(task.getTaskType())) {
					this.dmFactory.setTaskRunSts(task.getTaskCode(), RunSts.stop.name());
				}
				SchedulerLogger.finshTaskLog(task.getTaskCode(), jobId, DateUtils.getCurrentDate());
				SchedulerLogger.addTaskLogDtl(task.getTaskCode(), task.getVersion() + "", jobId, Constants.ServerType.manager.name(), "",
						managerId, "任务处理完成", exMsg);
			} else {
				TaskJobBean jobBean = this.dmFactory.getTaskJobByJobId(task.getTaskCode(), jobId);
				if (System.currentTimeMillis() - DateUtils.parseYYYYMMddHHmmss(jobBean.getCreateTime()).getTime() > taskMaxAlarmTime) {
					List<TaskItemJobBean> itemJobs = this.dmFactory.getAllTaskItemJobBean(task.getTaskCode(), jobId);
					for (TaskItemJobBean itemJob : itemJobs) {
						//分片30s未执行，且服务没有心跳，告警
						ServerBean serverHeart = dmFactory.getServerRegistry(itemJob.getServerId(), Constants.ServerType.processor);
						if (serverHeart == null) {
							SchedulerLogger.addTaskErrorLogDtl(task.getTaskCode(), task.getVersion() + "", jobId, Constants.ServerType.manager.name(),
									itemJob.getTaskItemId(), itemJob.getServerId(), "任务分片超过30s未执行", Constants.TaskErrLevel.LEVEL_5, "主备进程均无响应！");
						}
					}
				}
			}
			return Boolean.TRUE;
		} catch (Exception ex) {
			logger.error("", ex);
			return Boolean.FALSE;
		}
	}

}
