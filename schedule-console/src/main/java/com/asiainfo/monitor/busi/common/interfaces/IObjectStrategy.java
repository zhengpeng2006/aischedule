package com.asiainfo.monitor.busi.common.interfaces;

import com.asiainfo.monitor.busi.cache.interfaces.IGroup;
import com.asiainfo.monitor.busi.cache.interfaces.IPhysicHost;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;
import com.asiainfo.monitor.tools.common.interfaces.IVisitor;

public interface IObjectStrategy extends IVisitor {

	public Object visitGroup(IGroup group) throws Exception;
	
	public Object visitHost(IPhysicHost host) throws Exception;
	
	public Object visitNode(IServerNode node) throws Exception;
	
	public Object visitServer(IServer server) throws Exception;
}
