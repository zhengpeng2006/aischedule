package com.asiainfo.schedule.core.center;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.po.TaskJobBean;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.Constants;
import com.asiainfo.schedule.core.utils.Constants.RunSts;
import com.asiainfo.schedule.core.utils.Constants.ScheduleType;
import com.asiainfo.schedule.core.utils.SchedulerLogger;

public class TaskJob implements Job {
	private static transient final Logger logger = LoggerFactory.getLogger(TaskJob.class);

	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {

		String schedType = jobContext.getJobDetail().getJobDataMap().getString("SCHEDULE_TYPE");
		String taskCode = jobContext.getJobDetail().getJobDataMap().getString("TASK_CODE");
		String taskName = jobContext.getJobDetail().getJobDataMap().getString("TASK_NAME");
		String taskVersion = jobContext.getJobDetail().getJobDataMap().getString("TASK_VERSION");
		String managerId = jobContext.getJobDetail().getJobDataMap().getString("MANAGER_ID");

		try {
			String jobId = DataManagerFactory.getDataManager().getTaskJobId(taskCode);
			if (ScheduleType.start.name().equals(schedType)) {
				// 校验是否已经有任务正在处理，有的话那这次就不再生成作业。
				if (!CommonUtils.isBlank(jobId)) {
					logger.error("任务【" + taskCode + "】到达时间执行，但还存在未处理完的作业流水：" + jobId + ",不能重复执行。");
					return;
				}
				// 新生成任务作业
				TaskJobBean newJob = DataManagerFactory.getDataManager().createTaskJob(taskCode, null, managerId);
				if (newJob == null) {
					return;
				}
				logger.info("任务【" + taskCode + "】到达执行时间开始执行，成功新生成作业流水号：" + newJob.getJobId());

				// 记录日志
				SchedulerLogger.createTaskLog(taskCode, taskName, taskVersion, newJob.getJobId(), newJob.getCreateTime(), "C");
				SchedulerLogger.addTaskLogDtl(taskCode, taskVersion, newJob.getJobId(), Constants.ServerType.manager.name(), "", managerId, "到达执行时间新建", "");

			} else if (ScheduleType.end.name().equals(schedType)) {
				String state = DataManagerFactory.getDataManager().getTaskRunSts(taskCode);
				if (!RunSts.stop.name().equals(state)) {
					// 停止任务
					DataManagerFactory.getDataManager().setTaskRunSts(taskCode, Constants.RunSts.stop.name());
					logger.info("任务【" + taskCode + "】到达结束时间停止执行.");
					// 记录日志
					SchedulerLogger.addTaskLogDtl(taskCode, taskVersion, jobId, Constants.ServerType.manager.name(), "", managerId, "到达结束时间停止执行", "");
				}
			} else {
				throw new Exception("不支持的任务调度类型！" + schedType);
			}
		} catch (Exception ex) {
			logger.error("任务【" + taskCode + "】，" + schedType + " 过程中出现异常。", ex);
			// 记录错误日志
			SchedulerLogger.addTaskErrorLogDtl(taskCode, taskVersion, "", Constants.ServerType.manager.name(), "", managerId, "任务过程中出现异常。", Constants.TaskErrLevel.LEVEL_6,
					CommonUtils.getExceptionMesssage(ex, 1000));
		}
	}

}
