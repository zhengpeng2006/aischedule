package com.asiainfo.monitor.busi.common;

public interface IState {

	/**
	 * 返回开始时间
	 * @return
	 */
	public long getStartTime();

	/**
	 * 设置开始时间
	 * @param startTime
	 */
	public void setStartTime(long startTime);

	/**
	 * 返回状态
	 * @return
	 */
	public Object getState();

	/**
	 * 设置状态
	 * @param state
	 */
	public void setState(Object state);
	
	/**
	 * 返回结束时间
	 * @return
	 */
	public long getOverTime();

	/**
	 * 设置结束时间
	 * @param overTime
	 */
	public void setOverTime(long overTime);
	
}
