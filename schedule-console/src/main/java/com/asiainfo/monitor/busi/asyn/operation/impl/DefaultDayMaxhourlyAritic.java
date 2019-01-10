package com.asiainfo.monitor.busi.asyn.operation.impl;

import java.util.List;

import com.asiainfo.monitor.busi.asyn.operation.AsynOperation;
import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;

public class DefaultDayMaxhourlyAritic extends AsynOperation {

	private int deepCount;
	
	public DefaultDayMaxhourlyAritic(int deepCount){
		super();
		this.deepCount=deepCount;
	}
	
	@Override
	public OperateCallable takeOperateCallable(Object data) {
		if (data==null)
			return null;
		OperateCallable callable=new DefaultDayMaxhourlyAriticCallable((List)data,deepCount);		
		return callable;
	}

}
