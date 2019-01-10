package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;
import com.asiainfo.monitor.busi.model.event.PriEntityEvent;

public class PriEntity extends DefaultCheckCache implements Serializable {

	private String id;
	
	private String code;
	
	private String name;
	//1:菜单，2:扩展功能,3:面板
	private String type;
	
	private String attr;
	
	private String parentId;
	
	private PriEntity parent;
	
	private int seq;
	
	//发布类型:web,app等
	private String deployType;
	//1:系统所属,2:扩展
	private String selfType;
	
	//0:默认,1:预加载
	private String loadType;
	
	private String style;
	
	private String remarks;

	public PriEntity(){
		super();
	}
	
	public String getKey(){
		return this.id;
	}
	
	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDeployType() {
		return deployType;
	}

	public void setDeployType(String deployType) {
		this.deployType = deployType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PriEntity getParent() {
		if (parent!=null){
			return parent;
		}
		if (StringUtils.isBlank(parentId) || Long.parseLong(parentId)<=0)
			return null;
		synchronized (this){
			if (parent!=null)
				return parent;
			parent=(PriEntity)(new PriEntityEvent()).getEventResult(parentId);
		}		
		return parent;
	}

	public boolean sameCode(String entityCode){
		if (this.code.equals(entityCode))
			return true;
		else
			return false;
	}
	
	public void setParent(PriEntity parent) {
		this.parent = parent;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSelfType() {
		return selfType;
	}

	public void setSelfType(String selfType) {
		this.selfType = selfType;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
