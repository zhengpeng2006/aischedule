package com.asiainfo.monitor.interapi.config;

import java.io.Serializable;

public class AIBusinessCacheInfo implements Serializable {

	private static final long serialVersionUID = -6639919690200430031L;

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getNewcount() {
		return newcount;
	}

	public void setNewcount(String newcount) {
		this.newcount = newcount;
	}

	public String getOldcount() {
		return oldcount;
	}

	public void setOldcount(String oldcount) {
		this.oldcount = oldcount;
	}

	public String getLastrefreshendtime() {
		return lastrefreshendtime;
	}

	public void setLastrefreshendtime(String lastrefreshendtime) {
		this.lastrefreshendtime = lastrefreshendtime;
	}

	public String getLastrefreshstarttime() {
		return lastrefreshstarttime;
	}

	public void setLastrefreshstarttime(String lastrefreshstarttime) {
		this.lastrefreshstarttime = lastrefreshstarttime;
	}

	private String classname;
	
	private String newcount;
	
	private String oldcount;
	
	private String lastrefreshendtime;
	
	private String lastrefreshstarttime;
	
}
