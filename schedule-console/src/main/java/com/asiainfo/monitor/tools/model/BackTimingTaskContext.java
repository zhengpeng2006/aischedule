package com.asiainfo.monitor.tools.model;


import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.model.interfaces.IBackTimingTaskContext;
import com.asiainfo.monitor.tools.model.interfaces.ISmsState;


/**
 * 后台定时任务上下文
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class BackTimingTaskContext extends DefaultTaskContext implements IBackTimingTaskContext,Serializable{

	private static Log log=LogFactory.getLog(BackTimingTaskContext.class);
	
	private String layer;
	
	private String groupId;
	
	//警告
	private ThresholdModel threshold=null;
	//执行时间
	private TimeModel time=null;
	
	private String state;
	
	//短信状态
	private ISmsState smsState=null;
	
	private TaskFilterModel filter=null;
	
	public BackTimingTaskContext(){
		super();
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}
	
	public ISmsState getSmsState() {
		return smsState;
	}

	public void setSmsState(ISmsState smsState) {
		this.smsState = smsState;
	}

	public TaskFilterModel getFilter() {
		return filter;
	}

	public void setFilter(TaskFilterModel filter) {
		this.filter = filter;
	}

	public ThresholdModel getThreshold() {
		return threshold;
	}

	public void setThreshold(ThresholdModel threshold) {
		this.threshold = threshold;
	}

	public TimeModel getTime() {
		return time;
	}

	public void setTime(TimeModel time) {
		this.time = time;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}