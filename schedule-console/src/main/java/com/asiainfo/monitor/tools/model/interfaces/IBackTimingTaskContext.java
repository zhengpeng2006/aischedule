package com.asiainfo.monitor.tools.model.interfaces;

import com.asiainfo.monitor.tools.model.TaskFilterModel;
import com.asiainfo.monitor.tools.model.ThresholdModel;
import com.asiainfo.monitor.tools.model.TimeModel;

public interface IBackTimingTaskContext extends ITaskContext {

	public String getLayer();

	public void setLayer(String layer);
	
	public String getGroupId();

	public void setGroupId(String groupId);
	
	public ThresholdModel getThreshold();

	public void setThreshold(ThresholdModel threshold);

	public TaskFilterModel getFilter();

	public void setFilter(TaskFilterModel filter);
	
	public TimeModel getTime();

	public void setTime(TimeModel time);
	
	public String getState();
	
	public void setState(String state);
	
	public ISmsState getSmsState();

	public void setSmsState(ISmsState smsState);
}
