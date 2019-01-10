package com.asiainfo.monitor.busi.asyn.operation.impl;


import java.util.Map;

import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;
import com.asiainfo.monitor.busi.asyn.operation.ServerOperateCallable;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.interapi.api.control.AppSwitchApi;
import com.asiainfo.monitor.interapi.config.WebConnectApp;
import com.asiainfo.monitor.tools.common.SimpleResult;

public class WebConnectAppCallable extends ServerOperateCallable {

	public WebConnectAppCallable(String id){
		super(id);
	}
	
	/**
	 * 
	 */
	public void concreteOperate(OperateCallable callable, SimpleResult sr) throws Exception {
		try{
			if (sr.getAttach()!=null && sr.getAttach() instanceof IServer){
				ServerConfig server=(ServerConfig)sr.getAttach();
				try{
					WebConnectApp info=new WebConnectApp();
					info.setServerId(server.getApp_Id());
					info.setServerName(server.getApp_Name());					
					Map tmp=AppSwitchApi.listAppClusters(server.getLocator_Type(),server.getLocator());
					if (tmp!=null && !tmp.isEmpty()){
						info.setCanSetAppClusterCode((String[])tmp.keySet().toArray(new String[0]));
						info.setCanSetAppClusterEnv((String[])tmp.values().toArray(new String[0]));
					}
					info.setCurAppClusterCode(AppSwitchApi.getCurrentAppCluster(server.getLocator_Type(),server.getLocator()));
					info.setOldAppClusterCode(AppSwitchApi.getOldAppCluster(server.getLocator_Type(),server.getLocator()));
					
					sr.setSucc(true);
					sr.setValue(info);
				}catch(Exception e){
					sr.setSucc(false);
					sr.setValue(null);
				}
			}			
		}catch(Exception e){
			//读取web应用集群信息异常:
			throw new Exception("读取web应用集群信息异常:"+e.getMessage());
		}
	}

}
