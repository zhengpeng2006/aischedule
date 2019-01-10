package com.asiainfo.schedule.core.po;

import java.util.Map;


public class TaskItemJobBean implements java.io.Serializable {

	private static final long serialVersionUID = 634005748195070711L;
	//任务执行流水(含分片信息)
	String itemJobId;
	//任务执行流水
	String taskJobId;
	String taskCode;
	//分片编码
	String taskItemId;
	String serverId;
	
	long taskVersion;
	
	int priority;
	
	Map<String,String> taskItemParam;
	
	String sts;
	
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public Map<String, String> getTaskItemParam() {
		return taskItemParam;
	}
	public void setTaskItemParam(Map<String, String> taskItemParam) {
		this.taskItemParam = taskItemParam;
	}
	public long getTaskVersion() {
		return taskVersion;
	}
	public void setTaskVersion(long taskVersion) {
		this.taskVersion = taskVersion;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getItemJobId() {
		return itemJobId;
	}
	public void setItemJobId(String itemJobId) {
		this.itemJobId = itemJobId;
	}
	public String getTaskJobId() {
		return taskJobId;
	}
	public void setTaskJobId(String taskJobId) {
		this.taskJobId = taskJobId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskItemId() {
		return taskItemId;
	}
	public void setTaskItemId(String taskItemId) {
		this.taskItemId = taskItemId;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	
}
