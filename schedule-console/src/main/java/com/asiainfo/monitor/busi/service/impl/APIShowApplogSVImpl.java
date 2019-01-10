package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowApplogSV;
import com.asiainfo.monitor.interapi.api.log.AppLogSetApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class APIShowApplogSVImpl implements IAPIShowApplogSV {

	private final static transient Log log=LogFactory.getLog(APIShowApplogSVImpl.class);
	
	/**
	 * 读取日志级别信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getLogLevels(String appId) throws RemoteException,Exception {
		if (StringUtils.isBlank(appId))
			return null;
		List result=null;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=serverSV.getServerByServerId(appId);
			if (appServer==null){
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
			}
			if (appServer.getJmxSet()==null || appServer.getJmxSet().getValue().equals("OFF") || 
					appServer.getJmxSet().getValue().equals("FALSE")){
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
			Object[] infos=AppLogSetApi.getLogLevels(appServer.getLocator_Type(),appServer.getLocator());
			if (infos==null || infos.length<1)
				return result;
			result=new ArrayList();
			
			for (int i=0;i<infos.length;i++){
				String[] item=(String[])infos[i];
				if (item[0].indexOf("com.asiainfo")>=0 || item[0].indexOf("com.ai.")>=0){
					Map map=new HashMap();
					map.put("LOG_PKG",item[0]);
					map.put("LOG_LEVEL",item[1]);
					result.add(map);
				}
				
			}
		}catch(Exception e){
			log.error("Call APIShowApplogSVImpl's Method getLogLevels has Exception :"+e.getMessage());
		}
		return result;
	}
}
