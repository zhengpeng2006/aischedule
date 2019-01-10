package com.asiainfo.monitor.busi.common.impl;

import com.asiainfo.monitor.busi.cache.interfaces.IGroup;
import com.asiainfo.monitor.busi.cache.interfaces.IPhysicHost;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;
import com.asiainfo.monitor.busi.common.interfaces.IObjectStrategy;

public class TreeXmlStrategy implements IObjectStrategy {


	public Object visitGroup(IGroup group) throws Exception {
		StringBuilder treeXml = new StringBuilder();
		treeXml.append("<node label=\"").append(group.getGroup_Name()).append("\" id=\"")
		       .append(group.getGroup_Id())
		       .append("\" nodeClass=\"group\" icon=\"groupImage\" state=\"0\" isBranch=\"true\"/>\n");
		return treeXml.toString();
	}

	public Object visitHost(IPhysicHost host) throws Exception {
		StringBuilder treeXml = new StringBuilder();				
        treeXml.append("<node label=\"").append(host.getHostName()).append("\" id=\"").append(host.getHostId()).append("\" parentid=\"")
                .append(host.getParentId())
		       .append("\" nodeClass=\"host\" icon=\"hostImage\" state=\"0\" isBranch=\"true\"/>\n");
		return treeXml.toString();
	}
	
	public Object visitNode(IServerNode node) throws Exception {
		StringBuilder treeXml = new StringBuilder();				
		treeXml.append("<node label=\"").append(node.getNode_Name()).append("\" id=\"")
		       .append(node.getNode_Id()).append("\" parentid=\"").append(node.getGroup_Id())
		       .append("\" nodeClass=\"host\" icon=\"hostImage\" state=\"0\" isBranch=\"true\"/>\n");
		return treeXml.toString();
	}

	public Object visitServer(IServer server) throws Exception {
		StringBuilder treeXml = new StringBuilder();
		treeXml.append("<node label=\"").append(server.getApp_Name()).append("\" id=\"")
		       .append(server.getApp_Id()).append("\" parentid=\"").append(server.getNode_Id())
		       .append("\" nodeClass=\"server\" tempType=\"").append(server.getTemp_Type())
		       .append("\" icon=\"serverImage\" state=\"0\"/>\n");
		return treeXml.toString();
	}

}
