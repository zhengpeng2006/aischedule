package com.asiainfo.monitor.interapi.api;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.system.SystemMonitorMBean;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class SystemResourceApi {
	private static transient Log log = LogFactory.getLog(SystemResourceApi.class);
	
	private static String S_MBEAN_NAME="appframe:name=SystemMonitor";
	/**
	 * 获得资源文件的内容
	 * @param appSrvCfg
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	public static String getResourceFromClasspath(String locatorType,String locator, String resource) throws Exception{		
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		String result="";
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,SystemMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((SystemMonitorMBean)proxyObject).fetchResourceFromClassPath(resource);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "SystemMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return result;
	}
	
	/**
	 * 获得资源的位置
	 * @param appSrvCfg
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	public static String fetchResourcePath(String locatorType,String locator,String resource) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		String result="";
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,SystemMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((SystemMonitorMBean)proxyObject).fetchResourcePath(resource);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "SystemMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return result;
	}
	
	/**
	 * 获得资源的位置对应的classloader
	 * @param appSrvCfg
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	public static String fetchClassLoaderByResourcePath(String locatorType,String locator,String resource) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		String result="";
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,SystemMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((SystemMonitorMBean)proxyObject).fetchClassLoaderByResourcePath(resource);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "SystemMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return result;
	}
	
	
	public static HashMap getPropertiesFromSystem(String locatorType,String locator, String key) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		HashMap result=null;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,SystemMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((SystemMonitorMBean)proxyObject).fetchSystemProperties(key);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "SystemMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return result;
		
	}
	
}
