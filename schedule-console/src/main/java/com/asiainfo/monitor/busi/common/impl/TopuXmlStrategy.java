package com.asiainfo.monitor.busi.common.impl;

import com.asiainfo.monitor.busi.cache.interfaces.IGroup;
import com.asiainfo.monitor.busi.cache.interfaces.IPhysicHost;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;
import com.asiainfo.monitor.busi.common.interfaces.IObjectStrategy;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.tools.util.TypeConst;

public class TopuXmlStrategy implements IObjectStrategy {

	public TopuXmlStrategy() {
		super();
		// TODO 自动生成构造函数存根
	}

	public Object visitGroup(IGroup group) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<Node name=\"").append(group.getGroup_Name()).append("\" id=\"").append(group.getGroup_Id()).append("\" nodeId=\"").append(group.getGroup_Id());
		sb.append("\" nodeClass=\"group\" nodeColor=\"").append(TypeConst._NODECOLOR).append("\" nodeSize=\"").append(TypeConst._NODESIZE).append("\" nodeIcon=\"group\"/>");
		sb.append("<Edge fromID=\"-1\" toID=\"").append(group.getGroup_Id()).append("\" color=\"").append(TypeConst._EDGECOLOR).append("\"/>");
		return sb.toString();
	}

	public Object visitHost(IPhysicHost host) throws Exception{
		StringBuilder sb = new StringBuilder();
        sb.append("<Node name=\"").append(host.getHostName()).append("\" id=\"").append(host.getParentId()).append("_").append(host.getHostId())
                .append("\" nodeId=\"").append(host.getHostId()).append("\"");
		sb.append(" nodeClass=\"host\" nodeColor=\"").append(TypeConst._NODECOLOR).append("\" nodeSize=\"").append(TypeConst._NODESIZE).append("\" nodeIcon=\"host\"/>");
        sb.append("<Edge fromID=\"").append(host.getParentId()).append("\" toID=\"").append(host.getParentId());
        sb.append("_").append(host.getHostId()).append("\" color=\"").append(TypeConst._EDGECOLOR).append("\"/>");
		return sb.toString();
	}
	
	public Object visitNode(IServerNode node) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<Node name=\"").append(node.getNode_Name()).append("\" id=\"").append(node.getGroup_Id()).append("_").append(node.getNode_Id()).append("\" nodeId=\"").append(node.getNode_Id()).append("\"");
		//节点不再拥有主机信息
//		sb.append(" phost=\""+node.getPhysic_Host()+"\"").append(" phostName=\""+((ServerNodeConfig)node).getPhysicHost().getPhost_Name()+"\"");
		sb.append(" nodeClass=\"host\" nodeColor=\"").append(TypeConst._NODECOLOR).append("\" nodeSize=\"").append(TypeConst._NODESIZE).append("\" nodeIcon=\"host\"/>");
		sb.append("<Edge fromID=\"").append(node.getGroup_Id()).append("\" toID=\"").append(node.getGroup_Id());
		sb.append("_").append(node.getNode_Id()).append("\" color=\"").append(TypeConst._EDGECOLOR).append("\"/>");
		return sb.toString();
	}

	public Object visitServer(IServer server) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (server.getClass().getSimpleName().equals("ServerConfig")){
			IGroup group=((ServerConfig)server).getNodeConfig().getGroupConfig();
			if (group==null)
				return "";
			sb.append("<Node name=\"").append(server.getApp_Name()).append("\" id=\"").append(group.getGroup_Id()).append("_").append(server.getNode_Id());
			sb.append("_").append(server.getApp_Id()).append("\" nodeId=\"").append(server.getApp_Id()).append("\" nodeClass=\"server\" nodeColor=\"");
			sb.append(TypeConst._S_NODECOLOR).append("\" nodeIcon=\"").append(server.getTemp_Type());
			sb.append("\" nodeSize=\"").append(TypeConst._NODESIZE).append("\" />");
			sb.append("<Edge fromID=\"").append(group.getGroup_Id()).append("_").append(server.getNode_Id()).append("\" toID=\"").append(group.getGroup_Id());
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
		}
		return sb.toString();
	}

}
