package com.asiainfo.schedule.core.center;

public class TaskStat {
	String taskCode;
	long curVersion;
	String lastSchedTime;
	String nextSchedTime;
	String nextEndTime;

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public long getCurVersion() {
		return curVersion;
	}

	public void setCurVersion(long curVersion) {
		this.curVersion = curVersion;
	}

	public String getLastSchedTime() {
		return lastSchedTime;
	}

	public void setLastSchedTime(String lastSchedTime) {
		this.lastSchedTime = lastSchedTime;
	}

	public String getNextSchedTime() {
		return nextSchedTime;
	}

	public void setNextSchedTime(String nextSchedTime) {
		this.nextSchedTime = nextSchedTime;
	}

	public String getNextEndTime() {
		return nextEndTime;
	}

	public void setNextEndTime(String nextEndTime) {
		this.nextEndTime = nextEndTime;
	}

}