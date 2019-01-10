package com.asiainfo.monitor.interapi.config;

import com.asiainfo.monitor.tools.common.interfaces.IKeyName;

public class AppConnectInfo {

	//App集群代码(名称)
	private String appClusterCode;
	
	//连接到该集群的应用信息,key:应用标识，name:应用名称
	private IKeyName[] connectServers;
	
	//能够选择设置的App集群代码
	private String[] canSetAppClusterCode;
	
	//能够选择设置的App集群对应的环境变量
	private String[] canSetAppClusterEnv; 
	
	public AppConnectInfo(){
		
	}

	public String getAppClusterCode() {
		return appClusterCode;
	}

	public void setAppClusterCode(String appClusterCode) {
		this.appClusterCode = appClusterCode;
	}

	public IKeyName[] getConnectServers() {
		return connectServers;
	}

	public void setConnectServers(IKeyName[] connectServers) {
		this.connectServers = connectServers;
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
