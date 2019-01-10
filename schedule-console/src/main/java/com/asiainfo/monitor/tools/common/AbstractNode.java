package com.asiainfo.monitor.tools.common;



import com.asiainfo.monitor.tools.common.interfaces.INodeInfo;

public abstract class AbstractNode implements INodeInfo {

	private INodeInfo parent;
	
	private String id = "";
	
	private String type = "";
	
	public AbstractNode() {
		super();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id=id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public void setParent(INodeInfo parent){
		this.parent=parent;
	}
	
	public INodeInfo getParent(){
		return this.parent;
	}
	
	public abstract void addChild(INodeInfo node) throws Exception;

	public abstract boolean containChild(Object key);
	
	public abstract void addChild(Object key, INodeInfo node) throws Exception;

	public abstract INodeInfo getChild(Object key);

	public abstract boolean hasChild();

}
