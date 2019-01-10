package com.asiainfo.schedule.core.po;

/**
 * 任务的运行视图信息
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年12月5日
 * @modify
 */
public class TaskRunView implements java.io.Serializable {
	private static final long serialVersionUID = 6962643070864014441L;

	private TaskBean taskBean;

	// 任务执行状态
	private String taskRunSts;
	// 计划下一次调度开始时间
	private String nextSchedStartTime;
	// 计划下一次调度结束时间
	private String nextSchedEndTime;

	// 任务执行作业流水号
	private TaskJobBean runningTaskJob;

	// 调度管理服务进程标识
	private String curManagerId;

	public TaskBean getTaskBean() {
		return taskBean;
	}

	public void setTaskBean(TaskBean taskBean) {
		this.taskBean = taskBean;
	}

	public String getTaskRunSts() {
		return taskRunSts;
	}

	public void setTaskRunSts(String taskRunSts) {
		this.taskRunSts = taskRunSts;
	}

	public String getNextSchedStartTime() {
		return nextSchedStartTime;
	}

	public void setNextSchedStartTime(String nextSchedStartTime) {
		this.nextSchedStartTime = nextSchedStartTime;
	}

	public String getNextSchedEndTime() {
		return nextSchedEndTime;
	}

	public void setNextSchedEndTime(String nextSchedEndTime) {
		this.nextSchedEndTime = nextSchedEndTime;
	}

	public TaskJobBean getRunningTaskJob() {
		return runningTaskJob;
	}

	public void setRunningTaskJob(TaskJobBean runningTaskJob) {
		this.runningTaskJob = runningTaskJob;
	}

	public String getCurManagerId() {
		return curManagerId;
	}

	public void setCurManagerId(String curManagerId) {
		this.curManagerId = curManagerId;
	}

}
