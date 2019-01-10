package com.asiainfo.schedule.core.po;

import java.sql.Timestamp;

/**
 * 服务注册信息定义
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月29日
 * @modify
 */
public class ServerBean implements java.io.Serializable {

	private static final long serialVersionUID = -9039313572812520740L;
	String serverId;
	String serverType;
	String ip;
	String hostName;
	String pid;
	Timestamp registTime;
	Timestamp heartbeatTime;
	String heartbeatInfo;

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public Timestamp getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime;
	}

	public Timestamp getHeartbeatTime() {
		return heartbeatTime;
	}

	public void setHeartbeatTime(Timestamp heartbeatTime) {
		this.heartbeatTime = heartbeatTime;
	}

	public String getHeartbeatInfo() {
		return heartbeatInfo;
	}

	public void setHeartbeatInfo(String heartbeatInfo) {
		this.heartbeatInfo = heartbeatInfo;
	}

}
