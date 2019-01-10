package com.asiainfo.monitor.busi.asyn.operation.impl;

import com.asiainfo.monitor.busi.asyn.operation.AsynOperation;
import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;

public class SwitchAppClusterAsynOperate extends AsynOperation {

	private String newClusterAppCode;
	
	public SwitchAppClusterAsynOperate(String newClusterAppCode) {
		super();
		this.newClusterAppCode=newClusterAppCode;
	}

	@Override
	public OperateCallable takeOperateCallable(Object data) {
		return new SwitchAppClusterCallable(String.valueOf(data),newClusterAppCode);
	}

}
