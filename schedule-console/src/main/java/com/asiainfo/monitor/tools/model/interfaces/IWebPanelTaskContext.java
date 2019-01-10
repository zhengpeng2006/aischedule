package com.asiainfo.monitor.tools.model.interfaces;

import com.asiainfo.monitor.tools.model.ThresholdModel;


public interface IWebPanelTaskContext extends ITaskContext,Cloneable {

	public String getLayer();

	public void setLayer(String layer);

	public ThresholdModel getThreshold();

	public void setThreshold(ThresholdModel threshold);
	
	public String getViewWrapperClass();

	public void setViewWrapperClass(String viewWrapperClass);
	
	public Object clone();
}
