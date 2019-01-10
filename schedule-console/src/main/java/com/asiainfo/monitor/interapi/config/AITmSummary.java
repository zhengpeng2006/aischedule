package com.asiainfo.monitor.interapi.config;

import java.io.Serializable;

public class AITmSummary implements Serializable {

	private String id;
	
	private String code;
	
	private String name;
	
	private long commitCount;
	
	private long startCount;
	
	private long rollbackCount;
	
	private long suspendCount;
	
	private long resumeCount;
	  
	public AITmSummary() {
		super();
		// TODO 自动生成构造函数存根
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getCommitCount() {
		return commitCount;
	}

	public void setCommitCount(long commitCount) {
		this.commitCount = commitCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getResumeCount() {
		return resumeCount;
	}

	public void setResumeCount(long resumeCount) {
		this.resumeCount = resumeCount;
	}

	public long getRollbackCount() {
		return rollbackCount;
	}

	public void setRollbackCount(long rollbackCount) {
		this.rollbackCount = rollbackCount;
	}

	public long getStartCount() {
		return startCount;
	}

	public void setStartCount(long startCount) {
		this.startCount = startCount;
	}

	public long getSuspendCount() {
		return suspendCount;
	}

	public void setSuspendCount(long suspendCount) {
		this.suspendCount = suspendCount;
	}

	
}
