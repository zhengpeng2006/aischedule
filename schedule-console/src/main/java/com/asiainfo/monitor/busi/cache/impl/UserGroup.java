package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;
import java.sql.Timestamp;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;
import com.asiainfo.monitor.busi.model.event.UserGroupEvent;

public class UserGroup extends DefaultCheckCache implements Serializable{
	//标识
	private String id;
	
	private String code;
	//名称
	private String name;
	//密码:已加密
	private String password;
	
	//生效日
	private Timestamp effectDate;
	
	//失效日
	private Timestamp expireDate;
	
	//允许更改密码
	private int allowChangePwd;
	//允许多人登陆
	private int multLoginFlag;
	//尝试登陆次数
	private int tryTimes;
	//是否锁定
	private int lockFlag;
	
	//组标识
	private String groupId;
	
	private GroupRole group;
	
	
	public UserGroup(){
		super();
	}

	public String getKey(){
		return this.id;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public GroupRole getGroup() {
		if (group!=null){
			return group;
		}
		synchronized (this){
			if (group!=null)
				return group;
			group=(GroupRole)(new UserGroupEvent()).getEventResult(groupId);			
		}
		return group;
	}

	public void setGroup(GroupRole group) {
		this.group = group;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Timestamp effectDate) {
		this.effectDate = effectDate;
	}

	public Timestamp getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
	}

	public int getAllowChangePwd() {
		return allowChangePwd;
	}

	public void setAllowChangePwd(int allowChangePwd) {
		this.allowChangePwd = allowChangePwd;
	}

	public int getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(int lockFlag) {
		this.lockFlag = lockFlag;
	}

	public int getMultLoginFlag() {
		return multLoginFlag;
	}

	public void setMultLoginFlag(int multLoginFlag) {
		this.multLoginFlag = multLoginFlag;
	}

	public int getTryTimes() {
		return tryTimes;
	}

	public void setTryTimes(int tryTimes) {
		this.tryTimes = tryTimes;
	}
	
	
}
