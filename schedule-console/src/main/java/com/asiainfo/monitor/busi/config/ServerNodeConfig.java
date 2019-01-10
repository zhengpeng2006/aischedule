package com.asiainfo.monitor.busi.config;


import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;
import com.asiainfo.monitor.busi.model.event.GroupEvent;

public class ServerNodeConfig implements IServerNode{

	private IServerNode node;
	
	private GroupConfig groupConfig;
	
	public ServerNodeConfig(){
		
	}
	
	public ServerNodeConfig(IServerNode value){
		this.node=value;
	}
	
	public IServerNode getNode() {
		return node;
	}

	public void setNode(IServerNode host) {
		this.node = host;
	}

	public GroupConfig getGroupConfig() {
		if (groupConfig!=null){
			return groupConfig;
		}
		synchronized (this){
			if (groupConfig!=null)
				return groupConfig;
			groupConfig=(GroupConfig)(new GroupEvent()).getEventResult(node.getGroup_Id());
		}
		return groupConfig;
	}

	
	
	public void setGroupConfig(GroupConfig groupConfig) {
		this.groupConfig = groupConfig;
	}

	public String getRemark() {
		return node.getRemark();
	}

	public String getState() {
		return node.getState();
	}

	public String getNode_Id() {
		return node.getNode_Id();
	}

	public String getNode_Name() {
		return node.getNode_Name();
	}

	public String getGroup_Id() {
		return node.getGroup_Id();
	}

	public String getNode_Code() {
		
		return node.getNode_Code();
	}

    @Override
    public String getHost_id()
    {
        return node.getHost_id();
    }
	
}
