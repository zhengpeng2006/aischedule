package com.asiainfo.monitor.interapi.config;

import java.io.Serializable;

public class AIThreadInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3430448139070587210L;
	private long id;
	private String name;
	private String state;
	private String stack;
	public AIThreadInfo() {
		super();
	}
	public AIThreadInfo(long id, String name, String state, String stack) {
		super();
		this.id = id;
		this.name = name;
		this.state = state;
		this.stack = stack;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStack() {
		return stack;
	}
	public void setStack(String stack) {
		this.stack = stack;
	}
}
