package com.asiainfo.monitor.busi.common.impl;



import com.asiainfo.monitor.busi.common.interfaces.INodeVisitor;
import com.asiainfo.monitor.tools.common.interfaces.INodeInfo;
import com.asiainfo.monitor.tools.common.interfaces.IVisitable;
import com.asiainfo.monitor.tools.common.interfaces.IVisitor;

public class LogicNode extends AbstractStruct implements INodeInfo,IVisitable {

	
	public LogicNode() {
		super();
		// TODO 自动生成构造函数存根
	}
	
	
	public Object accept(IVisitor visitor) throws Exception{
		if (visitor instanceof INodeVisitor){
			return ((INodeVisitor)visitor).visitHost(this);
		}
		return null;
	}
}
