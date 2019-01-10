package com.asiainfo.monitor.tools.model;


import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.model.interfaces.IWebPanelTaskContext;

public class WebPanelTaskContext extends DefaultTaskContext implements IWebPanelTaskContext,Serializable {

	private static Log log=LogFactory.getLog(WebPanelTaskContext.class);
	
	private String layer;

	//	警告
	private ThresholdModel threshold=null;
	
	private TimeModel time=null;
	
	private String viewWrapperClass;
	
	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public TimeModel getTime() {
		return time;
	}

	public void setTime(TimeModel time) {
		this.time = time;
	}

	public ThresholdModel getThreshold() {
		return threshold;
	}

	public void setThreshold(ThresholdModel threshold) {
		this.threshold = threshold;
	}
	
	public String getViewWrapperClass() {
		return viewWrapperClass;
	}

	public void setViewWrapperClass(String viewWrapperClass) {
		this.viewWrapperClass = viewWrapperClass;
	}
}
