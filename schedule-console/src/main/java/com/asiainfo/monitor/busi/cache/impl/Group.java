package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;
import com.asiainfo.monitor.busi.cache.interfaces.IGroup;

public class Group extends DefaultCheckCache implements Serializable,IGroup {

	private String group_Id;
	
	private String group_Name;
	
	private String group_Desc;
	
	//域类型集,各类型已逗号隔开
	private String domain;
	
	private String domainCode;
	
	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	private String state;
	
	public Group() {
		super();
	}

	public String getGroup_Desc() {
		return group_Desc;
	}

	public void setGroup_Desc(String group_Desc) {
		this.group_Desc = group_Desc;
	}

	public String getGroup_Id() {
		return group_Id;
	}

	public void setGroup_Id(String group_Id) {
		this.group_Id = group_Id;		
	}

	public String getGroup_Name() {
		return group_Name;
	}

	public void setGroup_Name(String group_Name) {
		this.group_Name = group_Name;
	}

	public String getKey(){
		return this.group_Id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
