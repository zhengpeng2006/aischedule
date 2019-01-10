package com.asiainfo.monitor.interapi.config;

import java.io.Serializable;

public class AIMemoryInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6893489072976480234L;
	
	private String idCode;
	private String name;
	private String time;
	private long total;
	private long used;
	public AIMemoryInfo() {
		super();
	}
	public AIMemoryInfo(long total, long used) {
		super();
		this.total = total;
		this.used = used;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getUsed() {
		return used;
	}
	public void setUsed(long used) {
		this.used = used;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
