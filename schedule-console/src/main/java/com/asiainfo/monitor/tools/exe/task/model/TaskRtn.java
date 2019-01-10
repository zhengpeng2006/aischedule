package com.asiainfo.monitor.tools.exe.task.model;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;

public class TaskRtn implements ITaskRtn,Cloneable{
	
	//任务标识标
	private String idCode=null;

	//寄主标识
	private String parentId=null;
	
	//应用、主机
	private String code=null;
	
	private String name=null;
	
	//时间
	private String time=null;	
	
	//信息
	private String msg = null;

//	警告级别
	private int level = 0;
	
	private String warn=null;

	//预留字段
	private boolean beTure=false;
	
	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	
	public String getParentId(){
		return this.parentId;
	}
	
	public void setParentId(String parentId){
		this.parentId=parentId;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public Object clone(){
		ITaskRtn taskRtn=new TaskRtn();
		taskRtn.setIdCode(this.idCode);
		taskRtn.setCode(this.code);
		taskRtn.setMsg(this.msg);
		taskRtn.setLevel(this.level);
		return taskRtn;
	}
	
	
	public String getWarn() {
		return warn;
	}

	public void setWarn(String warn) {
		this.warn = warn;
	}

	public String toString(){
		StringBuilder sb=new StringBuilder("");
		sb.append("idcode:"+idCode);
		sb.append("@");
		sb.append("name:"+name);
		sb.append("@");
		sb.append("time:"+time);		
		sb.append("@");
		sb.append("msg:"+msg);
		sb.append("@");
		sb.append("level:"+level);
		sb.append("@");
		sb.append("warn:"+warn);
		
		return sb.toString();
	}

	public boolean isBeTure() {
		return beTure;
	}

	public void setBeTure(boolean beTure) {
		this.beTure = beTure;
	}
}
