package com.asiainfo.monitor.interapi.api.trace;



public class MemTrace extends DefaultTrace implements ITrace {

	
	private String host;
	
	//获得连接的 时间 
	private String getTime;
	
	private String center;
	
	private String parameter;
	
	private String resultCount;
	
	public MemTrace() {
	}


	public void addChild(ITrace objITrace) {
	}

	public boolean isNode() {
		return false;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}


	public String getResultCount() {
		return resultCount;
	}

	public void setResultCount(String resultCount) {
		this.resultCount = resultCount;
	}


	public String getParameter() {
		return parameter;
	}


	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
