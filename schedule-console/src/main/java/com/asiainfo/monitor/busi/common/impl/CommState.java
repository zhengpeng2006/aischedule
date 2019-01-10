package com.asiainfo.monitor.busi.common.impl;

import com.asiainfo.monitor.busi.common.IState;

public class CommState implements IState{

	//开始时间
	private long startTime;
	
	//状态
	private Object state;
	
	//结束时间
	private long overTime;
	
	public CommState(){		
	}
	
	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	public Object getState() {
		return state;
	}


	public void setState(Object state) {
		this.state = state;
	}

	
	public long getOverTime() {
		return overTime;
	}

	public void setOverTime(long overTime) {
		this.overTime = overTime;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成方法存根

	}

}
