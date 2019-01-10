package com.asiainfo.schedule.core.po;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 调度任务配置对象
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月21日
 * @modify
 */
public class TaskBean implements Serializable {
	private static final long serialVersionUID = 2710651466131824623L;

	private String taskCode;
	private String taskName;
	private String taskDesc;
	// 任务类型：single\complex\reload\tf
	private String taskType;
	private String taskGroupCode;
	// 依赖任务编码列表，以","分隔
	private String depends;
	private long version;

	// 开始、结束时间配置说明：
	// 开始时间配置为：run$now 立即执行。（针对原tf任务执行端轮询执行）
	// 1.调度器中增加立即执行开始任务 （只执行一次）
	// 2.调度器执行时，查看jobId是否已经生成并且状态为active，如果存在不做任何处理；若不存在则重新生成jobId，并分发任务、启动进程，并生成执行日志
	// 3.结束时间不生效，配置为空。
	// 开始时间配置为：run$yyyy-mm-dd hh24:mi:ss 固定在某个时间点执行
	// 1.当配置的时间小于当前时间，不做任何处理。
	// 2.当配置的时间大于当前时间，添加一个固定时间点的开始任务（只执行一次）
	// 3.调度器执行处理与上面run$now方式一致。
	// 4.结束时间必须配置为空或者固定的某个时间点，为空不做停止处理；不为空若配置的时间小于当前系统时间认为失效，不做处理，否则添加一个固定时间的停止任务。
	// 开始时间配置为：0 0 2 * * * ? * 周期执行
	// 1.解析表达式计算下次开始时间，调度器中增加任务。
	// 2.调度器执行处理时与上面run$now方式一致。
	// 3.当前任务执行完后，计算下次任务时间添加到调度器中
	// 4.结束时间必须配置为空或者周期执行表达式。为空不做停止处理；不为空则再计算下次开始时间时计算该次的结束时间，并添加到调度器中。到时间执行时，则停止任务。并记录日志。
	// 开始时间配置为空（针对依赖类的任务，等待外部条件触发执行），调度中心不做时间控制。只管理执行状态。结束时间不生效。
	private String startTime;
	private String endTime;
	private int scanIntervalTime;
	private int scanNum;
	private int executeNum;
	private int idleSleepTime;
	private int threadNum;

	/**
	 * 任务是否按地市拆分
	 */
	private boolean splitRegion;
	
	/**
	 * 拆分细项
	 */
	private String[] items;
	// 业务实现类
	private String processClass;
	// 任务分拆项的分派类型（A：自动，，M：人工）
	private String assignType;
	// 故障处理方式。M：人工处理；A：自动处理
	private String faultProcessMethod;

	private String state;

	private Timestamp createTime;
	
	// 任务执行优先级
	private int priority;
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	// 是否输出日志
	private boolean isLog;

	public boolean isLog() {
		return isLog;
	}
	public void setLog(boolean isLog) {
		this.isLog = isLog;
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

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskGroupCode() {
		return taskGroupCode;
	}

	public void setTaskGroupCode(String taskGroupCode) {
		this.taskGroupCode = taskGroupCode;
	}

	public String getDepends() {
		return depends;
	}

	public void setDepends(String depends) {
		this.depends = depends;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
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

	public boolean isSplitRegion() {
		return splitRegion;
	}

	public void setSplitRegion(boolean splitRegion) {
		this.splitRegion = splitRegion;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
