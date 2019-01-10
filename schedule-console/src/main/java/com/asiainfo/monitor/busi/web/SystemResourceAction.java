package com.asiainfo.monitor.busi.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAPISystemResourceSV;
import com.asiainfo.monitor.tools.util.Resource;

public class SystemResourceAction extends BaseAction {

	private static transient Log log=LogFactory.getLog(SystemResourceAction.class);
	/**
	 * 从应用系统classpath下获取配置文件信息
	 * @param appId
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public String getConfigFileContent(String appId,String fileName) throws Exception{
		StringBuilder result=new StringBuilder("");
		try {
			IAPISystemResourceSV systemSV=(IAPISystemResourceSV)ServiceFactory.getService(IAPISystemResourceSV.class);
			result.append(systemSV.getConfigFileContent(appId,fileName));
		} catch (Exception e) {
			result.append("Failed");
		}
		return result.toString();
	}
	
	
	/**
	 * 从应用系统classpath下获取配置文件信息
	 * @param appId
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public String getConfigFileClassLoader(String appId,String fileName) throws Exception{
		StringBuilder result=new StringBuilder("");
		try {
			IAPISystemResourceSV systemSV=(IAPISystemResourceSV)ServiceFactory.getService(IAPISystemResourceSV.class);
			result.append(systemSV.getConfigFileClassLoader(appId,fileName));
			
		} catch (Exception	 e) {
			result.append("Failed");
		}
		return result.toString();
	}

	/**
	 * 获取被监控端App配置资源结构
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public String getAppConfigTreeXml() throws Exception{
		String result=null;
		try{
			result=Resource.loadFileFromClassPath("appconfig.xml");
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}
}
