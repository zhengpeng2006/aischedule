package com.asiainfo.monitor.busi.asyn.operation.impl;

import com.asiainfo.monitor.busi.asyn.operation.AsynOperation;
import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;

public class CheckSVMethodStat extends AsynOperation {

	public CheckSVMethodStat(){
		super();
	}
	
	@Override
	public OperateCallable takeOperateCallable(Object id) {
		return new CheckSVMethodStatCallable(String.valueOf(id));
	}
}
