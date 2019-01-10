package com.asiainfo.schedule.core.po;

/**
 * 故障对象定义
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月29日
 * @modify
 */
public class FaultBean implements java.io.Serializable {

	private static final long serialVersionUID = 1203191080516771290L;

	String faultServerId;
	String faultTime;
	String msg;
	String bakServerId;

	// 上一次处理时间
	String lastProcessTime;
	// 故障处理次数
	int processCount;
	// 故障处理方式 A:自动处理，M:故障处理失败人工，F:处理结束
	String processType;

	public String getLastProcessTime() {
		return lastProcessTime;
	}

	public void setLastProcessTime(String lastProcessTime) {
		this.lastProcessTime = lastProcessTime;
	}

	public int getProcessCount() {
		return processCount;
	}

	public void setProcessCount(int processCount) {
		this.processCount = processCount;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getFaultServerId() {
		return faultServerId;
	}

	public void setFaultServerId(String faultServerId) {
		this.faultServerId = faultServerId;
	}

	public String getFaultTime() {
		return faultTime;
	}

	public void setFaultTime(String faultTime) {
		this.faultTime = faultTime;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getBakServerId() {
		return bakServerId;
	}

	public void setBakServerId(String bakServerId) {
		this.bakServerId = bakServerId;
	}

}
