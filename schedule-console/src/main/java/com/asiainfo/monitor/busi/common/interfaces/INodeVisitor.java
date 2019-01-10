package com.asiainfo.monitor.busi.common.interfaces;

import com.asiainfo.monitor.busi.common.impl.GroupNode;
import com.asiainfo.monitor.busi.common.impl.LogicNode;
import com.asiainfo.monitor.busi.common.impl.ServerNode;
import com.asiainfo.monitor.tools.common.interfaces.IVisitor;

public interface INodeVisitor extends IVisitor {

	public Object visitGroup(GroupNode node) throws Exception;
	
	public Object visitHost(LogicNode node) throws Exception;
	
	public Object visitServer(ServerNode node) throws Exception;
	
}
