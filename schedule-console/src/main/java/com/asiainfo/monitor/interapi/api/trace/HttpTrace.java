package com.asiainfo.monitor.interapi.api.trace;


public class HttpTrace extends DefaultTrace implements ITrace {

	//请求Url
	private String url;
	//调用方式pm
	private String processMethod;
	//超时to
	private String timeOut;
	//状态码
	private String stateCode;
	//中心
	private String center;
	//header
	private String header;
	//参数q
	private String parameter;
	//返回数据 b
	private String result;
	
	public HttpTrace() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProcessMethod() {
		return processMethod;
	}

	public void setProcessMethod(String processMethod) {
		this.processMethod = processMethod;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void addChild(ITrace objITrace) {
	}

	public boolean isNode() {
		return false;
	}

	
}
