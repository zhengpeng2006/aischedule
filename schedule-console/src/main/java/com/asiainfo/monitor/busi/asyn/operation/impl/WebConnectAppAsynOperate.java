package com.asiainfo.monitor.busi.asyn.operation.impl;

import com.asiainfo.monitor.busi.asyn.operation.AsynOperation;
import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;

public class WebConnectAppAsynOperate extends AsynOperation {

	public WebConnectAppAsynOperate() {
		super();
	}

	@Override
	public OperateCallable takeOperateCallable(Object data) {
		return new WebConnectAppCallable(String.valueOf(data));
	}

}
