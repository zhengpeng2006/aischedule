package com.asiainfo.monitor.busi.asyn.operation.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;
import com.asiainfo.monitor.busi.asyn.operation.ServerOperateCallable;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.interapi.api.statistics.ActionMonitorApi;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class CheckActionStatCallable extends ServerOperateCallable {

	private static transient Log log = LogFactory.getLog(CheckActionStatCallable.class);
	
	public CheckActionStatCallable(String id){
		super(id);
	}
	
	@Override
	public void concreteOperate(OperateCallable callable, SimpleResult sr) throws Exception {
		try{
			if (sr.getAttach()!=null && sr.getAttach() instanceof IServer){
				ServerConfig server=(ServerConfig)sr.getAttach();
				try{
					//如果不是web，则
					if (!server.supportAction()){
						sr.setSucc(false);
						sr.setValue(false);
						//该应用类型不支持Action统计
						sr.setMsg(AIMonLocaleFactory.getResource("MOS0000315",server.getApp_Id()));
						return;
					}
					boolean status=ActionMonitorApi.fetchStatus(server.getLocator_Type(),server.getLocator());
					sr.setSucc(true);
					sr.setValue(status);
				}catch(Exception e){
					sr.setSucc(false);
					sr.setValue(false);
				}
			}
		}catch(Exception e){
			// 检查Action打开状态发生异常:
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000237")+e.getMessage());
		}
		
	}

}
