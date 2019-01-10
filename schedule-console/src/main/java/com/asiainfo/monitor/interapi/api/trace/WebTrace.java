package com.asiainfo.monitor.interapi.api.trace;


public class WebTrace extends DefaultTrace implements ITrace {

	private String url = null;

	private String clientIp = null;

	private String serverIp = null;

	private String serverName = null;

	private String orgId=null;
	
	public WebTrace() {
		super();
	}

	public void addChild(ITrace objITrace) {
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServerIp() {
		return serverIp;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
	public boolean isNode(){
		return false;
	}

}
