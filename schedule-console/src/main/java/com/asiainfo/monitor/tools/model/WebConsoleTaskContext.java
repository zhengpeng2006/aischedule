package com.asiainfo.monitor.tools.model;

import java.io.Serializable;

import com.asiainfo.monitor.tools.model.interfaces.IWebConsoleTaskContext;


/**
 * 任务上下文
 * @author Guocx
 *
 */
public class WebConsoleTaskContext extends DefaultTaskContext implements IWebConsoleTaskContext,Serializable {
	
	private String clientID = null;
	
	public WebConsoleTaskContext(){
		super();
	}
	
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	
	public String getClientID() {
		return this.clientID;
	}
}
