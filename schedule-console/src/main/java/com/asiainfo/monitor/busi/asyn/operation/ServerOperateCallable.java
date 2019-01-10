package com.asiainfo.monitor.busi.asyn.operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.asyn.operation.impl.CollectFileCallable;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public abstract class ServerOperateCallable extends OperateCallable {

	private static transient Log log=LogFactory.getLog(ServerOperateCallable.class);
	
	protected String id;
	
	
	public ServerOperateCallable(String id){
		this.id=id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Override
	public void operate(OperateCallable callable,SimpleResult sr) throws Exception {
		try{
			IAIMonServerSV serverSV = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer = serverSV.getServerByServerId(id);
			if(appServer == null){
				// 没找到应用实例["+id+"]
				sr.setMsg(AIMonLocaleFactory.getResource("MOS0000070", id));
				sr.setSucc(false);
				return;
			}
			
			if ((appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equalsIgnoreCase("OFF") || 
					appServer.getJmxSet().getValue().equalsIgnoreCase("FALSE")) && !(this instanceof CollectFileCallable)){
				// 指定应用[appId:"+id+"]没有启动jmx注册
				sr.setMsg(AIMonLocaleFactory.getResource("MOS0000071", id));
				sr.setSucc(false);
				return;
			}
			sr.setKey(appServer.getApp_Id()+"");
			sr.setName(appServer.getApp_Name());
			sr.setAttach(appServer);
//			sr.setValue(appServer.getLocator_Type()+TypeConst._SPLIT_CHAR+appServer.getLocator());
			sr.setSucc(true);
			concreteOperate(callable,sr);
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000049")+e.getMessage());
		}

	}

	public abstract void concreteOperate(OperateCallable callable,SimpleResult sr) throws Exception;
	
}
