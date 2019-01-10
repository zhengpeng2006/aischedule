package com.asiainfo.monitor.tools.common;

import java.io.Serializable;

import com.asiainfo.monitor.tools.common.interfaces.IKeyName;

public class SimpleResult implements Serializable, IKeyName {

	private String key;
	
	private String name;
	//结果
	private Object value;
	//结果是否预期
	private boolean succ;
	//附件
	private Object attach;
	
	private String msg;
	
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key=key;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public Object getValue(){
		return this.value;
	}
	
	public void setValue(Object value){
		this.value=value;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public Object getAttach() {
		return attach;
	}

	public void setAttach(Object attach) {
		this.attach = attach;
	}
}
