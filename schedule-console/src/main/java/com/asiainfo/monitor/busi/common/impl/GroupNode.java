package com.asiainfo.monitor.busi.common.impl;


import com.asiainfo.monitor.busi.common.interfaces.INodeVisitor;
import com.asiainfo.monitor.tools.common.interfaces.INodeInfo;
import com.asiainfo.monitor.tools.common.interfaces.IVisitable;
import com.asiainfo.monitor.tools.common.interfaces.IVisitor;

public class GroupNode extends AbstractStruct implements INodeInfo,IVisitable {

	
	public GroupNode() {
		super();
	}

	public Object accept(IVisitor visitor) throws Exception{
		if (visitor instanceof INodeVisitor){
			return ((INodeVisitor)visitor).visitGroup(this);
		}
		return null;
	}
}
