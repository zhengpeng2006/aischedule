package com.asiainfo.monitor.busi.asyn.operation.impl;

import com.asiainfo.monitor.busi.asyn.operation.AsynOperation;
import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;

public class CheckSqlStat extends AsynOperation {

	public CheckSqlStat(){
		super();
	}
	
	@Override
	public OperateCallable takeOperateCallable(Object id) {
		return new CheckSqlStatCallable(String.valueOf(id));
	}

}
