package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;
import com.asiainfo.monitor.busi.model.event.DomainEvent;

public class RoleDomain extends DefaultCheckCache implements Serializable{

	
	private String id;
	
	private String code;
	
	private String name;
	
	private String notes;
	
	private String domainId;
	
	private Domain domain;
	
	private List entities=new ArrayList();
	
	public RoleDomain(){
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public Domain getDomain() {
		if (domain!=null){
			return domain;
		}
		synchronized (this){
			if (domain!=null)
				return domain;
			domain=(Domain)(new DomainEvent()).getEventResult(domainId);			
		}
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public List getEntities() {
		return entities;
	}

	public void setEntities(List entities) {
		this.entities = entities;
	}
	
	public void addEntity(String entityId){
		this.entities.add(entityId);
	}
	
	
}
