package com.asiainfo.monitor.busi.common.impl;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.AIMonNodeCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.AIMonServerCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.IGroup;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;
import com.asiainfo.monitor.busi.common.interfaces.INodeVisitor;

public class TreeXmlNodeVisitor implements INodeVisitor {

	public TreeXmlNodeVisitor() {
		super();
	}

	public Object visitGroup(GroupNode node) throws Exception{
		StringBuilder sb = new StringBuilder();
		try{
			IGroup group=(IGroup)CacheFactory.get(AIMonGroupCacheImpl.class,node.getId());
			if(group==null)
				return "";
			
			sb.append("<Node name=\"").append(group.getGroup_Name()).append("\" id=\"").append(group.getGroup_Id());
			sb.append("\" tempType=\"\" nodeClass=\"group\"> ");
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		
		return sb.toString();
	}

	public Object visitHost(LogicNode node) throws Exception{
		StringBuilder sb = new StringBuilder();
		
		try{
			IServerNode logicNode=(IServerNode)CacheFactory.get(AIMonNodeCacheImpl.class,node.getId());
			if (logicNode==null)
				return "";
			sb.append("<Node name=\"").append(logicNode.getNode_Name()).append("\" id=\"").append(logicNode.getNode_Id());
			sb.append("\" tempType=\"\" nodeClass=\"host\">");
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		
		
		return sb.toString();
	}

	public Object visitServer(ServerNode node) throws Exception{
		StringBuilder sb = new StringBuilder();
		try{
			IServer server=(IServer)CacheFactory.get(AIMonServerCacheImpl.class,node.getId());
			if (server==null)
				return "";
			sb.append("<Node name=\"").append(server.getApp_Name()).append("\" id=\"").append(server.getApp_Id());
			sb.append("\" tempType=\"").append(server.getTemp_Type()).append("\" nodeClass=\"server\">");
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}		
		return sb.toString();
	}

}
