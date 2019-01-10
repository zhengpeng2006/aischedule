package com.asiainfo.schedule.core.center;

import java.io.Serializable;
import java.util.Collection;

/**
 * 调度器状态信息
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月22日
 * @modify
 */
public class SchedulerStat implements Serializable {
	private static final long serialVersionUID = -7623883054957087575L;

	// 正在执行的任务数
	int activeCount;
	// 等待执行的任务数
	int scheduleTaskCount;
	// 管理任务数
	int taskCount;
	// 结束时间限制任务数
	int endTimeTaskCount;
	// 管理的任务信息
	Collection<TaskStat> taskStats;

	Collection<String> manageTaskList;

	public int getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}

	public int getScheduleTaskCount() {
		return scheduleTaskCount;
	}

	public void setScheduleTaskCount(int scheduleTaskCount) {
		this.scheduleTaskCount = scheduleTaskCount;
	}

	public int getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}

	public int getEndTimeTaskCount() {
		return endTimeTaskCount;
	}

	public void setEndTimeTaskCount(int endTimeTaskCount) {
		this.endTimeTaskCount = endTimeTaskCount;
	}

	public Collection<TaskStat> getTaskStats() {
		return taskStats;
	}

	public void setTaskStats(Collection<TaskStat> taskStats) {
		this.taskStats = taskStats;
	}

	public Collection<String> getManageTaskList() {
		return this.manageTaskList;
	}

	public void setManageTaskList(Collection<String> manageTaskList) {
		this.manageTaskList = manageTaskList;
	}

}