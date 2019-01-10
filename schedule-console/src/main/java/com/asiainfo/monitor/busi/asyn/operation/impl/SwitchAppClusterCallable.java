package com.asiainfo.monitor.busi.asyn.operation.impl;



import com.asiainfo.monitor.busi.asyn.operation.OperateCallable;
import com.asiainfo.monitor.busi.asyn.operation.ServerOperateCallable;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.interapi.api.control.AppSwitchApi;
import com.asiainfo.monitor.tools.common.SimpleResult;

public class SwitchAppClusterCallable extends ServerOperateCallable
{

    private String newClusterAppCode;
	
	public SwitchAppClusterCallable(String id,String newClusterAppCode){
		super(id);
		this.newClusterAppCode=newClusterAppCode;
	}
	
	@Override
	public void concreteOperate(OperateCallable callable, SimpleResult sr) throws Exception {
		try{
			if (sr.getAttach()!=null && sr.getAttach() instanceof IServer){
				ServerConfig server=(ServerConfig)sr.getAttach();
				try{
					AppSwitchApi.connect(server.getLocator_Type(),server.getLocator(),newClusterAppCode);					
					sr.setSucc(true);
					sr.setValue(null);
				}catch(Exception e){
					sr.setSucc(false);
					sr.setMsg("应用["+server.getApp_Name()+"]切换app集群["+newClusterAppCode+"]异常:"+e.getMessage());
					sr.setValue(null);
				}
			}			
		}catch(Exception e){
			//读取web应用集群信息异常:
			throw new Exception("将web应用切换到新的app集群["+newClusterAppCode+"]异常:"+e.getMessage());
		}
	}

}
