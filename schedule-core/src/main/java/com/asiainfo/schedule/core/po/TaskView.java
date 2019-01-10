package com.asiainfo.schedule.core.po;

import java.sql.Timestamp;
import java.util.Map;

/**
 *
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年10月14日
 * @modify 
 */
public class TaskView implements java.io.Serializable {

	private static final long serialVersionUID = -8125620130593565368L;

	private String groupCode;
	private String groupName;

	private String taskCode;
	private String taskName;
	private String taskType;
	private long version;
	private String state;
	private boolean splitRegion;
	
	private String taskDesc;
	private String depends;
	private String startTime;
	private String endTime;
	private int scanIntervalTime;
	private int scanNum;
	private int executeNum;
	private int idleSleepTime;
	private int threadNum;
	private String[] items;
	private String processClass;
	private String assignType;
	private String faultProcessMethod;
	private Timestamp createTime;
	// key:itemCode,value:appCode
	private Map<String, String> itemAppCodes;
	
	private String taskRunSts;
	
	

	public String getTaskRunSts() {
		return taskRunSts;
	}

	public void setTaskRunSts(String taskRunSts) {
		this.taskRunSts = taskRunSts;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isSplitRegion() {
		return splitRegion;
	}

	public void setSplitRegion(boolean splitRegion) {
		this.splitRegion = splitRegion;
	}

	public Map<String, String> getItemAppCodes() {
		return itemAppCodes;
	}

	public void setItemAppCodes(Map<String, String> itemAppCodes) {
		this.itemAppCodes = itemAppCodes;
	}

	
	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getDepends() {
		return depends;
	}

	public void setDepends(String depends) {
		this.depends = depends;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getScanIntervalTime() {
		return scanIntervalTime;
	}

	public void setScanIntervalTime(int scanIntervalTime) {
		this.scanIntervalTime = scanIntervalTime;
	}

	public int getScanNum() {
		return scanNum;
	}

	public void setScanNum(int scanNum) {
		this.scanNum = scanNum;
	}

	public int getExecuteNum() {
		return executeNum;
	}

	public void setExecuteNum(int executeNum) {
		this.executeNum = executeNum;
	}

	public int getIdleSleepTime() {
		return idleSleepTime;
	}

	public void setIdleSleepTime(int idleSleepTime) {
		this.idleSleepTime = idleSleepTime;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public String getProcessClass() {
		return processClass;
	}

	public void setProcessClass(String processClass) {
		this.processClass = processClass;
	}

	public String getAssignType() {
		return assignType;
	}

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	public String getFaultProcessMethod() {
		return faultProcessMethod;
	}

	public void setFaultProcessMethod(String faultProcessMethod) {
		this.faultProcessMethod = faultProcessMethod;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("groupCode:").append(this.groupCode).append(",");
		sb.append("groupName:").append(this.groupName).append(",");
		sb.append("taskCode:").append(this.taskCode).append(",");
		sb.append("taskName:").append(this.taskName).append(",");
		sb.append("taskType:").append(this.taskType).append(",");
		sb.append("taskVersion:").append(this.version).append(",");
		sb.append("startTime:").append(this.startTime).append(",");
		sb.append("endTime:").append(this.endTime).append(",");
		sb.append("state:").append(this.state).append(",");
		sb.append("runSts:").append(this.taskRunSts).append(",");
		sb.append("splitRegion:").append(this.splitRegion).append(",");
		sb.append("itemAppCodes:").append(this.itemAppCodes.toString());
		return sb.toString();
	}

}
