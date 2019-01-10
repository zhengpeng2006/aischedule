package com.asiainfo.monitor.busi.common.impl;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonServerCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.common.interfaces.INodeVisitor;
import com.asiainfo.monitor.tools.util.TypeConst;

public class TopuXmlNodeVisitor implements INodeVisitor {

	public TopuXmlNodeVisitor() {
		super();
		// TODO 自动生成构造函数存根
	}

	public Object visitGroup(GroupNode node) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<Node name=\"").append(node.getName()).append("\" id=\"").append(node.getId()).append("\" nodeId=\"").append(node.getId());
		sb.append("\" nodeClass=\"group\" nodeColor=\"").append(TypeConst._NODECOLOR).append("\" nodeSize=\"").append(TypeConst._NODESIZE).append("\" nodeIcon=\"group\"/>");
		sb.append("<Edge fromID=\"-1\" toID=\"").append(node.getId()).append("\" color=\"").append(TypeConst._EDGECOLOR).append("\"/>");
		return sb.toString();
	}

	public Object visitHost(LogicNode node) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<Node name=\"").append(node.getName()).append("\" id=\"").append(node.getParent().getId()).append("_").append(node.getId()).append("\" nodeId=\"").append(node.getId());
		sb.append("\" nodeClass=\"host\" nodeColor=\"").append(TypeConst._NODECOLOR).append("\" nodeSize=\"").append(TypeConst._NODESIZE).append("\" nodeIcon=\"host\"/>");
		sb.append("<Edge fromID=\"").append(node.getParent().getId()).append("\" toID=\"").append(node.getParent().getId());
		sb.append("_").append(node.getId()).append("\" color=\"").append(TypeConst._EDGECOLOR).append("\"/>");
		return sb.toString();
	}

	public Object visitServer(ServerNode node) throws Exception {
		IServer server=(IServer)CacheFactory.get(AIMonServerCacheImpl.class,node.getId());
		if (server==null)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append("<Node name=\"").append(server.getApp_Name()).append("\" id=\"").append(node.getParent().getParent().getId()).append("_").append(server.getNode_Id());
		sb.append("_").append(server.getApp_Id()).append("\" nodeId=\"").append(server.getApp_Id()).append("\" nodeClass=\"server\" nodeColor=\"");
		sb.append(TypeConst._S_NODECOLOR).append("\" nodeIcon=\"").append(server.getTemp_Type());
		sb.append("\" nodeSize=\"").append(TypeConst._NODESIZE).append("\" />");
		sb.append("<Edge fromID=\"").append(node.getParent().getParent().getId()).append("_").append(server.getNode_Id()).append("\" toID=\"").append(node.getParent().getParent().getId());
		sb.append("_").append(server.getNode_Id()).append("_").append(server.getApp_Id());
		if ((TypeConst.WEB).equals(server.getTemp_Type())) {
			sb.append("\" color=\"").append(TypeConst._S_WEB_EDGECOLOR).append("\"/>");
		} else if ((TypeConst.APP).equals(server.getTemp_Type())) {
			sb.append("\" color=\"").append(TypeConst._S_APP_EDGECOLOR).append("\"/>");
		} else if ((TypeConst.OTHER).equals(server.getTemp_Type())) {
			sb.append("\" color=\"").append(TypeConst._S_OTHER_EDGECOLOR).append("\"/>");
		} else if ((TypeConst.ITF).equals(server.getTemp_Type())) {
			sb.append("\" color=\"").append(TypeConst._S_ITF_EDGECOLOR).append("\"/>");
		} else {
			sb.append("\" color=\"").append(TypeConst._S_OTHER_EDGECOLOR).append("\"/>");
		}
		return sb.toString();
	}

}
