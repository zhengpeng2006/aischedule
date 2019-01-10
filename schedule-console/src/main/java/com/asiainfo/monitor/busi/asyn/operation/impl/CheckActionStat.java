package com.asiainfo.monitor.busi.asyn.operation.impl;

import com.asiainfo.monitor.busi.asyn.operation.AsynOperation;
import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;


public class CheckActionStat extends AsynOperation {
	
	public CheckActionStat(){
		super();
	}
	
	@Override
	public OperateCallable takeOperateCallable(Object id) {
		return new CheckActionStatCallable(String.valueOf(id));
	}

}
