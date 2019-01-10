package com.asiainfo.monitor.busi.asyn.operation.impl;

import com.asiainfo.monitor.busi.asyn.operation.AsynOperation;
import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;

public class BusiCacheTypeAsynOperate extends AsynOperation {

	public BusiCacheTypeAsynOperate() {
		super();
	}

	@Override
	public OperateCallable takeOperateCallable(Object data) {
		return new BusiCahceTypeCallable(String.valueOf(data));
	}

}
