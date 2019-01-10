package com.asiainfo.monitor.tools.common;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.monitor.tools.common.interfaces.INodeInfo;

public class TreeNodeInfo extends AbstractNode implements INodeInfo{
	
	private Map child = new HashMap();
	
	public boolean hasChild(){
		return child!= null && child.size() > 0;
	}
	
	public void addChild(Object key,INodeInfo node) {
		child.put(key, node);
	}
	
	public INodeInfo getChild(Object key) {
		return (INodeInfo)child.get(key);
	}
	
	public boolean containChild(Object key) {
		return child.containsKey(key);
	}
	
	public void addChild(INodeInfo node){
		child.put(node.getId(),node);
	}
	
	public Object getChild(){
		return child;
	}
	
	public boolean equals(Object o) {
		return false;
	}
	
	public void operate(ITreeOperate operate) throws Exception{
		operate.operate(this);
	}
}
