package com.asiainfo.monitor.busi.cache.impl;

import java.io.Serializable;

import com.asiainfo.monitor.busi.cache.DefaultCheckCache;
import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;


public class ServerNode extends DefaultCheckCache implements Serializable,IServerNode {

	private String node_Id;
	
	private String node_Code;
	
	private String node_Name;
	
	private String group_Id;
	
	private String state;
	
	private String remark;
	
    private String host_id;

	public ServerNode() {
		super();
	}
	
	public String getKey(){
		return this.node_Id;
	}


	public String getGroup_Id() {
		return group_Id;
	}

	public void setGroup_Id(String group_Id) {
		this.group_Id = group_Id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getNode_Id() {
		return node_Id;
	}

	public void setNode_Id(String nodeId) {
		node_Id = nodeId;
	}

	public String getNode_Code() {
		return node_Code;
	}

	public void setNode_Code(String nodeCode) {
		node_Code = nodeCode;
	}

	public String getNode_Name() {
		return node_Name;
	}

	public void setNode_Name(String nodeName) {
		node_Name = nodeName;
	}

    public String getHost_id()
    {
        return host_id;
    }

    public void setHost_id(String host_id)
    {
        this.host_id = host_id;
    }
	
}
