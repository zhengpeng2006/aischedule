package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;

public class MonPInfoGroup extends DefaultCheckCache implements Serializable {

	private String id;
	
	private String code;
	
	private String name;
	
	private String desc;
	
	private String parentId;
	
	private String sortId;
	
	private String layer;
	
	private String style;
	
	private String remark;
	
	public MonPInfoGroup() {
		super();
	}

	public String getKey(){
		return this.id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
