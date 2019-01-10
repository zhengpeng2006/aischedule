package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlUserSV;
import com.asiainfo.monitor.interapi.api.user.UserManagerMonitorApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class APIControlUserSVImpl implements IAPIControlUserSV {

	private static transient Log log=LogFactory.getLog(APIControlUserSVImpl.class);
	
	/**
	 * 强制某用户退出
	 * @param appId
	 * @param serialId
	 * @throws Exception
	 */
	public void forceLogoutUser(String appId, String serialId) throws RemoteException,Exception{
		if (StringUtils.isBlank(appId))
			return;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=serverSV.getServerByServerId(appId);
			if (appServer==null)
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
			if (appServer.getJmxSet()==null ){
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
			if (appServer.getJmxSet().getValue().equalsIgnoreCase("ON") ||
					appServer.getJmxSet().getValue().equalsIgnoreCase("TRUE")){
				UserManagerMonitorApi.forceLogoutUser(appServer.getLocator_Type(),appServer.getLocator(),serialId);
			}else{
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
			
			
		}catch(Exception e){
			log.error("Call UserManagerMonitorAction's Method forceLogoutUser has Exception :"+e.getMessage());
		}
	}
}
