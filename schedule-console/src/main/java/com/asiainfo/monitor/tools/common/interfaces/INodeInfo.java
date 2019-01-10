package com.asiainfo.monitor.tools.common.interfaces;



public interface INodeInfo {
	
	public void addChild(INodeInfo node) throws Exception;
	
	public void addChild(Object key, INodeInfo node) throws Exception;
	
	public boolean equals(Object o);
	
	public boolean containChild(Object key) ;
	
	public String getId();

	public void setId(String id);

	public String getType();

	public void setType(String type);	
	
	public INodeInfo getChild(Object key);
	
	public Object getChild();
	
	public boolean hasChild();
	
	public void setParent(INodeInfo parent);
	
	public INodeInfo getParent();
}
