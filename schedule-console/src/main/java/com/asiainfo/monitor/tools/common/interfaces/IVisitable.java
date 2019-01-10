package com.asiainfo.monitor.tools.common.interfaces;

public interface IVisitable {

	public Object accept(IVisitor visitor) throws Exception;

}
