package com.asiainfo.monitor.tools.exe.task.model;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.model.interfaces.IClear;

public class TaskRtnModel implements IClear,Serializable{

	//标识:主机名称或应用名称
	private String idCode;
	
	private String transform;
	
	private String method;
	
	//执行任务的类型:shell/javacommand/command/table
	private String type;
	
	//备注描述
	private String remark;
	
	private List<ITaskRtn> rtns=null;

	public TaskRtnModel(){
		rtns=new LinkedList<ITaskRtn>();
	}
	
	public List getRtns() {
		return rtns;
	}
	
	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTransform() {
		return transform;
	}

	public void setTransform(String transform) {
		this.transform = transform;
	}

	public void addRtn(ITaskRtn value){
		this.rtns.add(value);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void clear(){
		if (this.rtns!=null)
			this.rtns.clear();
	}
}
