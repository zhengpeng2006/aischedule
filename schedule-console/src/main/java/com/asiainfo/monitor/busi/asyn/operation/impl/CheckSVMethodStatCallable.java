package com.asiainfo.monitor.busi.asyn.operation.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;
import com.asiainfo.monitor.busi.asyn.operation.ServerOperateCallable;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.interapi.api.statistics.SVMethodMonitorApi;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class CheckSVMethodStatCallable extends ServerOperateCallable {

	private static transient Log log = LogFactory.getLog(CheckSVMethodStatCallable.class);
	
	public CheckSVMethodStatCallable(String id){
		super(id);
		
	}
	@Override
	public void concreteOperate(OperateCallable callable, SimpleResult sr) throws Exception {
		try{
			if (sr.getAttach()!=null && sr.getAttach() instanceof IServer){
				ServerConfig server=(ServerConfig)sr.getAttach();
				try{
					//如果不是App，则不检查
					if (!server.supportMethod()){
						sr.setSucc(false);
						sr.setValue(false);
						//该应用类型不支持Method统计
						sr.setMsg(AIMonLocaleFactory.getResource("MOS0000314",server.getApp_Id()));
						return;
					}
					boolean status=SVMethodMonitorApi.fetchState(server.getLocator_Type(),server.getLocator());
					sr.setSucc(true);
					sr.setValue(status);
				}catch(Exception e){
					sr.setSucc(false);
					sr.setValue(false);
					// 获取服务统计状态异常:
					log.error(AIMonLocaleFactory.getResource("MOS0000239")+e.getMessage());
				}
				
			}
		}catch(Exception e){
			// "检查SVMethod打开状态发生异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000240")+e.getMessage());
		}
	}

}
