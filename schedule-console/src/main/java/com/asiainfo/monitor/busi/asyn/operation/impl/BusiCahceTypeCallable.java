package com.asiainfo.monitor.busi.asyn.operation.impl;

import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;
import com.asiainfo.monitor.busi.asyn.operation.ServerOperateCallable;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.interapi.api.cache.BusiCacheMonitorApi;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class BusiCahceTypeCallable extends ServerOperateCallable {

	public BusiCahceTypeCallable(String id) {
		super(id);
	}

	@Override
	public void concreteOperate(OperateCallable callable, SimpleResult sr) throws Exception {
		try{
			if (sr.getAttach() != null && sr.getAttach() instanceof IServer){
				ServerConfig server=(ServerConfig)sr.getAttach();
				try{
					String[] tmp = BusiCacheMonitorApi.getAllCacheType(server.getLocator_Type(),server.getLocator());
					sr.setSucc(true);
					sr.setValue(tmp);
				}catch(Exception e){
					sr.setSucc(false);
					sr.setValue(null);
				}
			}
		}catch(Exception e){
			// "读取业务缓存类型发生异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000236")+e.getMessage());
		}
	}

}
