package com.asiainfo.monitor.interapi.config;

public class CallResult {
	public static final int SUCCESS 	= 1;
	public static final int FAIL		= 0;
	
	private int 	result;
	private String 	remark;
	
	
	public CallResult() {
		super();
	}
	
	public CallResult(int result, String remark) {
		super();
		this.result = result;
		this.remark = remark;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
