package com.asiainfo.monitor.interapi.api.cache;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.ac.AppframeCacheMonitorMBean;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;





public class CacheMonitorApi {
	
	private static transient Log log=LogFactory.getLog(CacheMonitorApi.class);
	
	private static String S_MBEAN_NAME="appframe:name=AppframeCacheMonitor";
	/**
	 * 读取应用服务器缓存个数
	 * @param rmiUrl
	 * @param type
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static int count(String locatorType,String locator,String type,String key) throws Exception {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return 0;	
		List cacheContainers=getCache(locatorType,locator,type,key);
	    return cacheContainers.size();
	}
	
	
	
	/**
	 * 返回缓存的类型
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public static List getCacheType(String locatorType,String locator) throws Exception,RemoteException{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		List dcs=null;
		try{
			
			
			String[] types=null;
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,AppframeCacheMonitorMBean.class);
			if (proxyObject!=null){
				try{
					types=((AppframeCacheMonitorMBean)proxyObject).getTypes();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppframeCacheMonitorMBean");
				}
			}
			
			if (types!=null && types.length>0){	
				dcs=new ArrayList(types.length);
				
				for (int i=0;i<types.length;i++){
					dcs.add(types[i]);
				}
			}			
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return dcs;
	}
	
	/**
	 * 根据指定的注册URL，获取相应服务器的缓存信息
	 * @param rmiUrl
	 * @param type
	 * @param key
	 * @param startRowIndex
	 * @param endRowIndex
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public static List getCache(String locatorType,String locator,String type,String key) throws Exception, RemoteException {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		
		List result=null;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,AppframeCacheMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((AppframeCacheMonitorMBean)proxyObject).query(type, key);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppframeCacheMonitorMBean");
				}
			}
		}
		catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return result;
	}
	
	/**
	 * 读取排名前rank位的缓存类型
	 * @param rmiUrl
	 * @param rank
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public static List getCacheRank(String locatorType,String locator,int rank) throws Exception,RemoteException{
		return null;
	}
	/**
	 * 根据注册服务器URL，注销指定类型缓存
	 * @param rmiUrl
	 * @param type
	 * @throws Exception
	 * @throws RemoteException
	 */
	public static void delCache(String locatorType,String locator,String type,String key) throws Exception,RemoteException{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,AppframeCacheMonitorMBean.class);
			if (proxyObject!=null){
				if (StringUtils.isBlank(key)){
					try{
						((AppframeCacheMonitorMBean)proxyObject).remove(type);
					}catch(NoClassDefFoundError ex){
						throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppframeCacheMonitorMBean");
					}
				}else{
					try{
						((AppframeCacheMonitorMBean)proxyObject).remove(type,key);
					}catch(NoClassDefFoundError ex){
						throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppframeCacheMonitorMBean");
					}
				}
			}
			
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		
	}
}
