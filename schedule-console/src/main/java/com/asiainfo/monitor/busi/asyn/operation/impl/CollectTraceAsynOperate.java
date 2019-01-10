package com.asiainfo.monitor.busi.asyn.operation.impl;

import com.asiainfo.monitor.busi.asyn.operation.AsynOperation;
import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;

public class CollectTraceAsynOperate extends AsynOperation {

	@Override
	public OperateCallable takeOperateCallable(Object data) {
		return new CollectTraceCallable(String.valueOf(data));
	}

}
