package com.asiainfo.monitor.busi.asyn.operation.impl;

import java.util.List;

import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;
import com.asiainfo.monitor.busi.asyn.operation.ServerOperateCallable;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.interapi.api.cache.CacheMonitorApi;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class FrameCacheTypeCallable extends ServerOperateCallable {

	public FrameCacheTypeCallable(String id) {
		super(id);
	}

	@Override
	public void concreteOperate(OperateCallable callable, SimpleResult sr) throws Exception {
		try{
			if (sr.getAttach()!=null && sr.getAttach() instanceof IServer){
				ServerConfig server=(ServerConfig)sr.getAttach();
				try{
					List tmp=CacheMonitorApi.getCacheType(server.getLocator_Type(),server.getLocator());
					sr.setSucc(true);
					sr.setValue(tmp);
				}catch(Exception e){
					sr.setSucc(false);
					sr.setValue(null);
				}
			}			
		}catch(Exception e){
			// 读取框架缓存类型发生异常:
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000246")+e.getMessage());
		}
	}

}
