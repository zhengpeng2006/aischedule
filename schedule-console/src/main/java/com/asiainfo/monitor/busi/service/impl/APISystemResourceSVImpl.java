package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPISystemResourceSV;
import com.asiainfo.monitor.interapi.api.SystemResourceApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class APISystemResourceSVImpl implements IAPISystemResourceSV {

	/**
	 * 从应用系统classpath下获取配置文件信息
	 * @param appId
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public String getConfigFileContent(String appId,String fileName) throws RemoteException,Exception{
		String ret = "";
		try {
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=serverSV.getServerByServerId(appId);
			if (appServer==null){
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
			}
			if (appServer.getJmxSet()==null ){
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
			if (appServer.getJmxSet().getValue().equalsIgnoreCase("ON") ||
					appServer.getJmxSet().getValue().equalsIgnoreCase("TRUE")){
				ret = SystemResourceApi.getResourceFromClasspath(appServer.getLocator_Type(),appServer.getLocator(), fileName);
			}else{
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
			
		} catch (Exception e) {
			ret = "Failed";
		}
		return ret;
	}
	
	
	/**
	 * 从应用系统classpath下获取配置文件信息
	 * @param appId
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public String getConfigFileClassLoader(String appId,String fileName)throws RemoteException,Exception{
		StringBuffer ret = new StringBuffer();
		try {
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=serverSV.getServerByServerId(appId);
			if (appServer==null){
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
			}
			if (appServer.getJmxSet()==null ){
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
			if (appServer.getJmxSet().getValue().equalsIgnoreCase("ON") ||
					appServer.getJmxSet().getValue().equalsIgnoreCase("TRUE")){
				String filePath = SystemResourceApi.fetchResourcePath(appServer.getLocator_Type(),appServer.getLocator(), fileName);
				// 文件全路径
				ret.append(AIMonLocaleFactory.getResource("MOS0000302") + ":\n");
				ret.append(filePath).append("\n\n\n\n");
				// 文件ClassLoader
				ret.append(AIMonLocaleFactory.getResource("MOS0000303") + ":\n");			
				ret.append(SystemResourceApi.fetchClassLoaderByResourcePath(appServer.getLocator_Type(),appServer.getLocator(), fileName));
			}else{
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
			
			
		} catch (Exception	 e) {
			ret.append("Failed");
		}
		return ret.toString();
	}

}
