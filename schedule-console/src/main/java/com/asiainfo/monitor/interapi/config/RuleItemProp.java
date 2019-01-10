package com.asiainfo.monitor.interapi.config;

import java.util.List;
import java.util.Map;

import com.asiainfo.monitor.tools.common.interfaces.INodeInfo;
import com.asiainfo.monitor.tools.model.ItemEntry;

public class RuleItemProp extends ItemEntry implements INodeInfo{

	private String type;
		
	public RuleItemProp(String key,String value){
		super(key,value);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isNode(){
		return false;
	}
	
	public Map getChild(){
		return null;
	}
	
	public void addChild(INodeInfo node){
		return;
	}
	
	public void addChild(List childList){
		return;
	}
	
	public INodeInfo containChild(INodeInfo rule){
		return null;
	}
	
	public String getId(){
		return null;
	}

	public void setId(String id){
		
	}
	
	public boolean containChild(Object key) {
		return false;
	}
	
	public void addChild(Object key,INodeInfo node) {
	}
	
	public INodeInfo getChild(Object key){
		return null;
	}
	
	public boolean hasChild(){
		return false;
	}
	
	public void setParent(INodeInfo parent){
		
	}
	
	public INodeInfo getParent(){
		return null;
	}
}
