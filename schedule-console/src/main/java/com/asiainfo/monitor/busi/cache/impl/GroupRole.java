package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;

public class GroupRole extends DefaultCheckCache implements Serializable {

	private String id;
	
	private String code;
	
	private String name;
	
	private List roles=new ArrayList();
	
	public GroupRole(){
		super();
	}

	public String getKey(){
		return this.id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getRoles() {
		return roles;
	}

	public void setRoles(List roles) {
		this.roles = roles;
	}
	
	public void addRoles(String roleId){
		this.roles.add(roleId);
	}
	
}
