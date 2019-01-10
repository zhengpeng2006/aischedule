package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlCacheSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowCacheSV;
import com.asiainfo.monitor.busi.stat.control.ServerStatControl;
import com.asiainfo.monitor.busi.stat.control.ServerStatControl.CacheControlEnum;
import com.asiainfo.monitor.interapi.api.cache.BusiCacheMonitorApi;
import com.asiainfo.monitor.interapi.api.cache.CacheMonitorApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class APIControlCacheSVImpl implements IAPIControlCacheSV {

	private static transient Log log=LogFactory.getLog(APIControlCacheSVImpl.class);
	
	/**
	 * 批量清除框架某类型部分缓存
	 * @param appIds
	 * @param keys
	 * @throws Exception
	 */
	public void clearFrameCache(Object[] appIds,String type,Object[] keys) throws RemoteException,Exception {
		try{
			if (appIds==null || appIds.length<1)
				// 清除平台缓存没有传入应用参数
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000133"));
			if (keys==null || keys.length<1)
				// 清除平台缓存没有传入参数Key
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000134"));
			
			int threadCount=(appIds.length>>1)+1;
			int cpuCount=Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3))
				threadCount=cpuCount*3;
			ServerStatControl.controlCacheStatus(threadCount,-1,appIds,type,keys,CacheControlEnum.CLEAR_FRAMECACHE);
		}catch(Exception e){
			log.error("Call APIControlCacheSVImpl's Method clearFrameCache has Exception :"+e.getMessage());
		}
	}
	
	/**
	 * 批量清除框架某类型部分缓存
	 * @param appIds
	 * @param type
	 * @throws Exception
	 */
	public void clearFrameCacheByType(Object[] appIds,String type) throws RemoteException,Exception {
		if (appIds==null || appIds.length<1)
			// 清除平台缓存没有传入应用参数
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000133"));
		try{
			int threadCount=(appIds.length>>1)+1;
			int cpuCount=Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3))
				threadCount=cpuCount*3;
			ServerStatControl.controlCacheStatus(threadCount,-1,appIds,type,null,CacheControlEnum.CLEAR_FRAMECACHE);
		}catch(Exception e){
			log.error("Call APIControlCacheSVImpl's Method clearFrameCacheByType has Exception :"+e.getMessage());
		}
	}
	
	/**
	 * 清除框架指定类型缓存
	 * @param appId
	 * @param type
	 * @throws Exception
	 */
	public void clearFrameCacheByType(String appId,String type) throws RemoteException,Exception {
		if (StringUtils.isBlank(appId))
			return;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=serverSV.getServerByServerId(appId);
			if (appServer==null)
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
			if (appServer.getJmxSet()==null){
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
			if (appServer.getJmxSet().getValue().equalsIgnoreCase("ON") ||
					appServer.getJmxSet().getValue().equalsIgnoreCase("TRUE")){
				CacheMonitorApi.delCache(appServer.getLocator_Type(),appServer.getLocator(),type,"");
			}else{
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
			
		}catch(Exception e){
			log.error("Call APIControlCacheSVImpl's Method clearFrameCacheByType has Exception :"+e.getMessage());
		}
	}
	
	
	/**
	 * 清除单应用缓存
	 * @param appId
	 * @param type
	 * @throws Exception
	 */
	public void clearBusiCache(String appId,String type) throws RemoteException,Exception {
		if (StringUtils.isBlank(appId))
			return;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=serverSV.getServerByServerId(appId);
			if (appServer==null)
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
			if (appServer.getJmxSet()==null || appServer.getJmxSet().getValue().equalsIgnoreCase("OFF") ||
					appServer.getJmxSet().getValue().equalsIgnoreCase("FALSE")){
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
			
			List busiCache = null;
			// "全部"
			if(StringUtils.isBlank(type) || AIMonLocaleFactory.getResource("MOS0000007").equals(type)){
				IAPIShowCacheSV showCacheSV=(IAPIShowCacheSV)ServiceFactory.getService(IAPIShowCacheSV.class);
				busiCache = showCacheSV.getBusiCacheType(appId);
			}else{
				busiCache = new ArrayList();
				busiCache.add(type);
			}
			for(int i=0;i<busiCache.size();i++){
				BusiCacheMonitorApi.refreshCache(appServer.getLocator_Type(),appServer.getLocator(),busiCache.get(i).toString());
			}			
		}catch(Exception e){
			log.error("Call APIControlCacheSVImpl's Method clearFrameCache has Exception :"+e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 批量清除多应用业务缓存
	 * @param appIds
	 * @param type
	 * @throws Exception
	 */
	public void clearBusiCache(Object[] appIds,String type) throws RemoteException,Exception {
		if (appIds==null || appIds.length<1)
			return;
		try{
			int threadCount=(appIds.length>>1)+1;
			int cpuCount=Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3))
				threadCount=cpuCount*3;
			ServerStatControl.controlCacheStatus(threadCount,-1,appIds,type,null,CacheControlEnum.CLEAR_BUSICACHE);			
		}catch(Exception e){
			log.error("Call APIControlCacheSVImpl's Method clearFrameCache has Exception :"+e.getMessage());
		}
	}
	
	/**
	 * 获得监控自有缓存信息
	 * @return
	 * @throws Exception
	 */
	public List getAllLocalCache() throws RemoteException,Exception {
		List cache = null;
		try{
			cache = MonCacheManager.getAllCache();
		}catch(Exception e){
			log.error("Call APIControlCacheSVImpl's Method getAllLocalCache has Exception :"+e.getMessage());
		}
		return cache;
	}
	
	/**
	 * 刷新监控自有缓存
	 * @throws Exception
	 */
	public void clearAllLocalCache() throws RemoteException,Exception {
		try{
			MonCacheManager.clearAllCache();
		}catch(Exception e){
			log.error("Call APIControlCacheSVImpl's Method clearAllLocalCache has Exception :"+e.getMessage());
		}
	}
	
	/**
	 * 刷新选中缓存
	 * @throws Excecption
	 */
	public void clearSelectedCache(String key) throws RemoteException,Exception {
		try {
			MonCacheManager.clearSelectedCache(key);
		} catch (Exception e) {
			log.error("Call APIControlCacheSVImpl's Method clearSelectedCache has Exception :"+e.getMessage());
		}
	}
	
}
