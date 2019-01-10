package com.asiainfo.monitor.interapi.api.trace;


public class MdbTrace extends DefaultTrace implements ITrace {
	
	private String host;
	
	private String center;
	
	private String parameter;
	
	private String out;
	
	public MdbTrace(){
		super();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}
	
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public void addChild(ITrace objITrace) {
	}

	public boolean isNode() {
		return false;
	}

}
