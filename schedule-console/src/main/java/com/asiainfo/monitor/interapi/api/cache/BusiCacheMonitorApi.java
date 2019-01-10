package com.asiainfo.monitor.interapi.api.cache;




import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.cache.CacheMonitorMBean;
import com.ai.appframe2.complex.mbean.standard.cache.CacheSummary;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class BusiCacheMonitorApi {

	private static transient Log log = LogFactory.getLog(BusiCacheMonitorApi.class);

	private static String S_MBEAN_NAME="appframe:name=CacheMonitor";

	/**
	 * 读取所有业务缓存类型
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 */
	public static String[] getAllCacheType(String locatorType,String locator) throws Exception {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator)){
			return null;
		}
		String[] result = null;
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,CacheMonitorMBean.class);
			if (proxyObject != null){
				try{
					result = ((CacheMonitorMBean)proxyObject).listAllCache();
				}catch(NoClassDefFoundError ex){
					// 没找到远程["+locator+"]定义对象
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "CacheMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 读取所有业务缓存对象
	 * @param appSrvCfg
	 * @return
	 * @throws Exception 
	 */
	
	public static CacheSummary[] getAllCaches(String locatorType,String locator,String type) throws Exception {
		
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		CacheSummary[] result=null;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,CacheMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((CacheMonitorMBean)proxyObject).fetchCache(type);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "CacheMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}

	/*
	public static List getAllCaches(AppServerConfig appSrvCfg) throws Exception {
		
		if (appSrvCfg == null || StringUtils.isBlank(appSrvCfg.getJmx_Rml()))
			return null;
		List result=null;
		try {
			CacheSummary[] rtn = (CacheSummary[]) RMIClientManager.invokeMBean(appSrvCfg.getJmx_Rml(), S_MBEAN_NAME,"fetchCache", null, null);
			if (rtn!=null){
				result=new ArrayList(rtn.length);
				for (int i=0;i<rtn.length;i++){
					result.add(rtn[i]);
				}
			}
		} catch (Exception ex) {
			log.error("异常", ex);
		} finally {
			if (objCacheMonitorMBean != null) {
			 ClientProxy.destroyObject(objCacheMonitorMBean);
			 }
		}
		return result;
	}
*/
	/**
	 * 强制刷新具体某一业务缓存
	 * @param appSrvCfg
	 * @param cacheId
	 * @return
	 * @throws Exception
	 */
	public static int refreshCache(String locatorType,String locator, String cacheId) throws Exception {

		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return 0;
		
		int result = 1;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,CacheMonitorMBean.class);
			if (proxyObject!=null){
				String rtn=null;
				try{
					rtn=((CacheMonitorMBean)proxyObject).forceRefresh(cacheId);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "CacheMonitorMBean");
				}
				if (StringUtils.isNotBlank(rtn) && rtn.indexOf("finished")==-1){
					result=0;
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}

}
