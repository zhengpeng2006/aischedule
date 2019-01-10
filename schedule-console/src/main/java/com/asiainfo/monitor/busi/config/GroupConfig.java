package com.asiainfo.monitor.busi.config;

import com.asiainfo.monitor.busi.cache.impl.Group;
import com.asiainfo.monitor.busi.cache.interfaces.IGroup;


public class GroupConfig implements IGroup{

	private Group group;

	public GroupConfig(){
	}
	
	public GroupConfig(Group value){
		this.group=value;
	}
	
	public String getGroup_Desc() {
		return group.getGroup_Desc();
	}
	
	public String getGroup_Id() {
		return group.getGroup_Id();
	}

	public String getGroup_Name() {
		return group.getGroup_Name();
	}

	public String getState() {
		return group.getState();
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getDomain(){
		return group.getDomain();
	}
}
