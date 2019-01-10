package com.asiainfo.monitor.busi.common.impl;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.monitor.tools.common.AbstractNode;
import com.asiainfo.monitor.tools.common.interfaces.INodeInfo;

public class AbstractStruct extends AbstractNode implements INodeInfo {

	private Map child=new HashMap();
	
	private String name;
	
	public AbstractStruct() {
		super();
		// TODO 自动生成构造函数存根
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void addChild(INodeInfo node) {
		child.put(node.getId(),node);
	}

	@Override
	public boolean containChild(Object key) {
		return child.containsKey(key);
	}

	@Override
	public void addChild(Object key, INodeInfo node) {
		child.put(key,node);
	}

	@Override
	public INodeInfo getChild(Object key) {
		if (child.containsKey(key)){
			child.get(key);
		}
		return null;
	}

	@Override
	public boolean hasChild() {
		return child!=null && child.size()>0;
	}
	
	public Object getChild(){
		return child;
	}

}
