package com.asiainfo.monitor.busi.asyn.operation.impl;

import com.asiainfo.monitor.busi.asyn.operation.AsynOperation;
import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;

public class FrameCacheTypeAsynOperate extends AsynOperation {

	public FrameCacheTypeAsynOperate() {
		super();
		// TODO 自动生成构造函数存根
	}

	@Override
	public OperateCallable takeOperateCallable(Object data) {
		return new FrameCacheTypeCallable(String.valueOf(data));
	}

}
