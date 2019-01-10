package com.asiainfo.monitor.interapi;

public class ServerControlInfo implements java.io.Serializable {

	private String url;

	private String hostname;
	
	private String serverName;
	
	private String serverIp;

	private String serverId;

	private String pfPath;

	private long stopExecId;

	private long startExecId;

	private String grpName;

	private String status;

	private String info;
	
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
	public ServerControlInfo() {
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setServerName(String serverName) {

		this.serverName = serverName;
	}

	public void setPfPath(String pfPath) {
		this.pfPath = pfPath;
	}

	public void setStopExecId(long stopExecId) {
		this.stopExecId = stopExecId;
	}

	public void setStartExecId(long startExecId) {
		this.startExecId = startExecId;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getUrl() {
		return url;
	}

	public String getHostname() {
		return hostname;
	}

	public String getServerName() {

		return serverName;
	}

	public String getPfPath() {
		return pfPath;
	}

	public long getStopExecId() {
		return stopExecId;
	}

	public long getStartExecId() {
		return startExecId;
	}

	public String getGrpName() {
		return grpName;
	}

	public String getStatus() {

		return status;
	}

	public String getInfo() {
		return info;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	
	
}
