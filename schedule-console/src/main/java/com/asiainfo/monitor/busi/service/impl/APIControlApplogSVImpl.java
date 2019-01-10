package com.asiainfo.monitor.busi.service.impl;



import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlApplogSV;
import com.asiainfo.monitor.interapi.api.log.AppLogSetApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class APIControlApplogSVImpl implements IAPIControlApplogSV {

	private final static transient Log log=LogFactory.getLog(APIControlApplogSVImpl.class);
	
	/**
	 * 设置日志级别
	 * @param appId
	 * @param logPkg
	 * @param logLevel
	 * @return
	 * @throws Exception
	 */
	public int setLogLevel(String appId,String logPkg,String logLevel) throws RemoteException,Exception{
		if (StringUtils.isBlank(appId))
			return 0;
		int result=0;
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
				result=AppLogSetApi.setLogLevel(appServer.getLocator_Type(),appServer.getLocator(),logPkg,logLevel);
			}else{
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
		}catch(Exception e){
			log.error("Call AppLogSetAction's Method setLogLevel has Exception :"+e.getMessage());
		}
		return result;
	}
}
