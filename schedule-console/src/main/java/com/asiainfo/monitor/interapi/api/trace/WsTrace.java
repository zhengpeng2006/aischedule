package com.asiainfo.monitor.interapi.api.trace;

public class WsTrace extends DefaultTrace implements ITrace {

	//请求url
	private String url;
	//方法 名
	private String methodName;
	//中心
	private String center;
	//超时to
	private String timeOut;
	//输入参数
	private String parameter;
	//输出
	private String out;
	
	public WsTrace() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	@Override
	public void addChild(ITrace objITrace) {
	}

	@Override
	public boolean isNode() {
		return false;
	}

}
