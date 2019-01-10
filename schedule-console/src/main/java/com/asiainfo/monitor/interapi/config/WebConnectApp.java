package com.asiainfo.monitor.interapi.config;

public class WebConnectApp {

	//应用标识
	private String serverId;
	
	//应用名称
	private String serverName;
	
	//当前应用连接的集群信息
	private String curAppClusterCode;
	
	//先前应用连接的集群信息
	private String oldAppClusterCode;
	
	//能够选择设置的App集群代码
	private String[] canSetAppClusterCode;
	
	//能够选择设置的App集群对应的环境变量
	private String[] canSetAppClusterEnv;
	
	public WebConnectApp(){
		
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getCurAppClusterCode() {
		return curAppClusterCode;
	}

	public void setCurAppClusterCode(String curAppClusterCode) {
		this.curAppClusterCode = curAppClusterCode;
	}

	public String getOldAppClusterCode() {
		return oldAppClusterCode;
	}

	public void setOldAppClusterCode(String oldAppClusterCode) {
		this.oldAppClusterCode = oldAppClusterCode;
	}

	public String[] getCanSetAppClusterCode() {
		return canSetAppClusterCode;
	}

	public void setCanSetAppClusterCode(String[] canSetAppClusterCode) {
		this.canSetAppClusterCode = canSetAppClusterCode;
	}

	public String[] getCanSetAppClusterEnv() {
		return canSetAppClusterEnv;
	}

	public void setCanSetAppClusterEnv(String[] canSetAppClusterEnv) {
		this.canSetAppClusterEnv = canSetAppClusterEnv;
	}
}
