package com.asiainfo.monitor.interapi.config;

import java.io.Serializable;

public class AIFrameCache implements Serializable {

	private String id;
	
	private String code;
	
	private String name;
	
	private String key = null;
	
	private String type = null;
	
	private Object obj = null;
	  
	public AIFrameCache() {
		super();
		// TODO 自动生成构造函数存根
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


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
