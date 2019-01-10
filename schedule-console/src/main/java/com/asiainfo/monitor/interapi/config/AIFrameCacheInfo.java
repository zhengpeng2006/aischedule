package com.asiainfo.monitor.interapi.config;

import java.io.Serializable;

public class AIFrameCacheInfo implements Serializable {
	
	private static final long serialVersionUID = -5802309683734025659L;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrimarykey() {
		return primarykey;
	}

	public void setPrimarykey(String primarykey) {
		this.primarykey = primarykey;
	}

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	private String type;
	
	private String primarykey;
	
	private String hashcode;
	
	
}
