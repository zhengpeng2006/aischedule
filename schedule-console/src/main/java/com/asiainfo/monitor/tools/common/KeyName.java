package com.asiainfo.monitor.tools.common;

import java.io.Serializable;

import com.asiainfo.monitor.tools.common.interfaces.IKeyName;

public class KeyName implements Serializable,IKeyName{

	private String key;
	
	private String name;
	
	public KeyName() {
		super();
		// TODO 自动生成构造函数存根
	}

	public KeyName(String name,String key){
		this.name=name;
		this.key=key;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
