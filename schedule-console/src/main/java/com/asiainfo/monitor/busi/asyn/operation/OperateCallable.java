package com.asiainfo.monitor.busi.asyn.operation;

import java.util.concurrent.Callable;

import com.asiainfo.monitor.tools.common.SimpleResult;

public abstract class OperateCallable implements Callable {

	public SimpleResult call() throws Exception{
		SimpleResult sr=new SimpleResult();
		try{
			operate(this,sr);
		}catch(Exception e){
			sr.setSucc(false);			
		}
		return sr;
	}
	
	public abstract void operate(OperateCallable callable,SimpleResult sr) throws Exception;
}
