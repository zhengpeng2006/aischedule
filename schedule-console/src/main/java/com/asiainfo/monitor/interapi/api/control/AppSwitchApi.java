package com.asiainfo.monitor.interapi.api.control;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.cc.ClientControlMBean;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AppSwitchApi {

	private static transient Log log = LogFactory.getLog(AppSwitchApi.class);
	
	private static String S_MBEAN_NAME="appframe:name=ClientControlMBean";
	
	/**
	 * 重新连接App集群
	 * @param locatorType
	 * @param locator
	 * @throws Exception
	 */
	public static void reconnect(String locatorType,String locator) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,ClientControlMBean.class);
			if (proxyObject!=null){
				try{
					((ClientControlMBean)proxyObject).reconnect();
				}catch(NoClassDefFoundError ex){
					// 没找到远程["+locator+"]定义对象
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "ClientControlMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
	}
	
	/**
	 * 连接到指定App集群
	 * @param locatorType
	 * @param locator
	 * @param group
	 * @throws Exception
	 */
	public static void connect(String locatorType,String locator,String group) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,ClientControlMBean.class);
			if (proxyObject!=null){
				try{
					((ClientControlMBean)proxyObject).connect(group);
				}catch(NoClassDefFoundError ex){
					// 没找到远程["+locator+"]定义对象
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "ClientControlMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
	}
	
	/**
	 * 列出当前web所有的集群配置
	 * @param locatorType
	 * @param locator
	 * @return Map,key:集群名称;value:集群配置属性
	 * @throws Exception
	 */
	public static Map listAppClusters(String locatorType,String locator) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		Map result=null;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,ClientControlMBean.class);
			if (proxyObject!=null){
				try{
					result=((ClientControlMBean)proxyObject).listGroups();
				}catch(NoClassDefFoundError ex){
					// 没找到远程["+locator+"]定义对象
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "ClientControlMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return result;
	}
	
	/**
	 * 获取当前应用对应的最新App集群
	 * @param locatorType
	 * @param locator
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentAppCluster(String locatorType,String locator) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		String result=null;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,ClientControlMBean.class);
			if (proxyObject!=null){
				try{
					result=((ClientControlMBean)proxyObject).getCurrentAppCluster();
				}catch(NoClassDefFoundError ex){
					// 没找到远程["+locator+"]定义对象
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "ClientControlMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return result;
	}
	
	/**
	 * 获取当前应用对应的原始App集群
	 * @param locatorType
	 * @param locator
	 * @return
	 * @throws Exception
	 */
	public static String getOldAppCluster(String locatorType,String locator) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		String result=null;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,ClientControlMBean.class);
			if (proxyObject!=null){
				try{
					result=((ClientControlMBean)proxyObject).getOldAppCluster();
				}catch(NoClassDefFoundError ex){
					// 没找到远程["+locator+"]定义对象
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "ClientControlMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return result;
	}
	
}
