package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;



public class Server extends DefaultCheckCache implements Serializable,IServer {

	private String app_Id;
	
	private String app_Code;
		
	private String app_Name;

	//中间件类型
	private String midware_Type;
	//发布类型web\app
	private String temp_Type;
	
	private String app_Ip;
	
	private String app_Port;
	
	//脚本路径
	private String pf_path;

    //主机标识
    private String host_Id;
	//节点标识
	private String node_Id;
	
	private String check_Url;
		
	private String jmx_Http;
	//远程调用类型
	private String locator_Type;
	
	private String locator;
	
	private String start_CmdId;
	
	private String stop_CmdId;	
	//类型:域类型标识(CRM\KF等应用)
	private String domain;
	private String domain_Code;

	private String jmxState;

	private String state;
	
	private String remark;
	
	private String sversion;
	
	private String versionBack_CmdId;
	
	
	public String getVersionBack_CmdId() {
		return versionBack_CmdId;
	}

	public void setVersionBack_CmdId(String versionBack_CmdId) {
		this.versionBack_CmdId = versionBack_CmdId;
	}

	public String getKey(){
		return this.app_Id;
	}
	
	public String getSversion() {
		return sversion;
	}

	public void setSversion(String sversion) {
		this.sversion = sversion;
	}

	public Server() {
		super();
	}

	public String getApp_Code() {
		return app_Code;
	}

	public void setApp_Code(String app_Code) {
		this.app_Code = app_Code;
	}

	public String getApp_Id() {
		return app_Id;
	}

	public void setApp_Id(String app_Id) {
		this.app_Id = app_Id;
	}

	public String getApp_Ip() {
		return app_Ip;
	}

	public void setApp_Ip(String app_Ip) {
		this.app_Ip = app_Ip;
	}

	public String getApp_Name() {
		return app_Name;
	}

	public void setApp_Name(String app_Name) {
		this.app_Name = app_Name;
	}

	public String getApp_Port() {
		return app_Port;
	}

	public void setApp_Port(String app_Port) {
		this.app_Port = app_Port;
	}

	public String getCheck_Url() {
		return check_Url;
	}

	public void setCheck_Url(String check_Url) {
		this.check_Url = check_Url;
	}

	public String getNode_Id() {
		return node_Id;
	}

	public void setNode_Id(String nodeId) {
		node_Id = nodeId;
	}

	public String getJmx_Http() {
		return jmx_Http;
	}

	public void setJmx_Http(String jmx_Http) {
		this.jmx_Http = jmx_Http;
	}

	public String getLocator() {
		return locator;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}

	public String getLocator_Type() {
		return locator_Type;
	}

	public void setLocator_Type(String locator_Type) {
		this.locator_Type = locator_Type;
	}

	public String getMidware_Type() {
		return midware_Type;
	}

	public void setMidware_Type(String midware_Type) {
		this.midware_Type = midware_Type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStart_CmdId() {
		return start_CmdId;
	}

	public void setStart_CmdId(String start_CmdId) {
		this.start_CmdId = start_CmdId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStop_CmdId() {
		return stop_CmdId;
	}

	public void setStop_CmdId(String stop_CmdId) {
		this.stop_CmdId = stop_CmdId;
	}

	public String getTemp_Type() {
		return temp_Type;
	}

	public void setTemp_Type(String temp_Type) {
		this.temp_Type = temp_Type;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domainId) {
		this.domain = domainId;
	}
	
	public String getDomain_Code() {
		return domain_Code;
	}

	public void setDomain_Code(String domainCode) {
		this.domain_Code = domainCode;
	}
	
	public String getJmxState() {
		return jmxState;
	}

	public void setJmxState(String jmxState) {
		this.jmxState = jmxState;
	}

    public String getHost_Id()
    {
        return host_Id;
    }

    public void setHost_Id(String hostId)
    {
        host_Id = hostId;
    }

	public String getPf_path() {
		return pf_path;
	}

	public void setPf_path(String pfPath) {
		pf_path = pfPath;
	}
}
