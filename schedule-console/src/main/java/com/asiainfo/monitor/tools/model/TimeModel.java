package com.asiainfo.monitor.tools.model;

import java.io.Serializable;

import com.asiainfo.monitor.tools.common.interfaces.IChildNode;
import com.asiainfo.monitor.tools.common.interfaces.IEnable;

public class TimeModel extends AbstractEnable implements IChildNode,IEnable,Serializable{

	private long id;
	
	private String name;
	
	private String type;
	
	private String expr;

	private long taskId;
	
	public TimeModel(){
		super();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public long getParentId(){
		return this.taskId;
	}
	
	public void setParentId(long taskId){
		this.taskId=taskId;
	}
	
	public long getSelfId(){
		return this.getId();
	}
	
	public void setSelfId(long selfId){
		this.setId(selfId);
	}
}
