package com.asiainfo.monitor.busi.common.impl;


import com.asiainfo.monitor.busi.common.interfaces.INodeVisitor;
import com.asiainfo.monitor.tools.common.AbstractNode;
import com.asiainfo.monitor.tools.common.interfaces.INodeInfo;
import com.asiainfo.monitor.tools.common.interfaces.IVisitable;
import com.asiainfo.monitor.tools.common.interfaces.IVisitor;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

public class ServerNode extends AbstractNode implements INodeInfo,IVisitable {

	public ServerNode() {
		super();
		this.setType(TypeConst.SERVER);
	}

	@Override
	public void addChild(INodeInfo node) throws Exception{
		// 应用不能增加子节点
		throw new Exception(AIMonLocaleFactory.getResource("MOS0000227"));
	}

	@Override
	public boolean containChild(Object key) {
		return false;
	}

	@Override
	public void addChild(Object key, INodeInfo node) throws Exception{
		// 应用不能增加子节点
		throw new Exception(AIMonLocaleFactory.getResource("MOS0000227"));
	}

	@Override
	public INodeInfo getChild(Object key) {
		return null;
	}

	@Override
	public boolean hasChild() {
		return false;
	}
	
	public Object getChild(){
		return null;
	}
	
	public Object accept(IVisitor visitor) throws Exception{
		if (visitor instanceof INodeVisitor){
			return ((INodeVisitor)visitor).visitServer(this);
		}
		return null;
	}

}
