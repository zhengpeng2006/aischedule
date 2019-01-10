package com.asiainfo.monitor.busi.asyn.operation.impl;

import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;
import com.asiainfo.monitor.busi.asyn.operation.ServerOperateCallable;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.interapi.api.statistics.SQLMonitorApi;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class CheckSqlStatCallable extends ServerOperateCallable {

	public CheckSqlStatCallable(String id){
		super(id);
	}
	
	@Override
	public void concreteOperate(OperateCallable callable, SimpleResult sr) throws Exception {
		try{
			if (sr.getAttach()!=null && sr.getAttach() instanceof IServer){
				ServerConfig server=(ServerConfig)sr.getAttach();
				try{
					//如果不是App，则不检查
					if (!server.supportSql()){
						sr.setSucc(false);
						sr.setValue(false);
						//该应用类型不支持SQL统计
						sr.setMsg(AIMonLocaleFactory.getResource("MOS0000313",server.getApp_Id()));
						return;
					}
					boolean status=SQLMonitorApi.fetchStatus(server.getLocator_Type(),server.getLocator());
					sr.setSucc(true);
					sr.setValue(status);
				}catch(Exception e){
					sr.setSucc(false);
					sr.setValue(false);
				}
			}
		}catch(Exception e){
			// 检查SQL打开状态发生异常:
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000238")+e.getMessage());
		}
	}

}
