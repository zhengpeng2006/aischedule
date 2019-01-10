package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlCacheSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class ControlCacheAction extends BaseAction {
	
	private static transient Log log=LogFactory.getLog(ControlCacheAction.class);

	/**
	 * 批量清除框架某类型部分缓存
	 * @param appId
	 * @param type
	 * @param keys
	 * @throws Exception
	 */
	public void clearFrameCache(Object[] ids, String type, Object[] keys) throws Exception {
		if (ids==null || ids.length<1)
			return;
		try{
		    Map m = new HashMap();
		    List l = null;
			IAPIControlCacheSV controlCacheSV=(IAPIControlCacheSV)ServiceFactory.getService(IAPIControlCacheSV.class);
			for(int i=0;i<ids.length;i++){
			    if(m.containsKey(ids[i])){
			        l = (List)m.get(ids[i]);
			        if(null==l){
			            l = new ArrayList();
			        }
			    }else{
			        l = new ArrayList();
			    }
			    l.add(keys);
			    m.put(ids[i], l);
			}
			
			Object[] appIds = m.keySet().toArray();
			Object[] appId = new Object[1];
			for(int j=0;j<appIds.length;j++){
			    appId[0] = appIds[j];
			    controlCacheSV.clearFrameCache(appId,type,(Object[])((List)m.get(appIds[j])).get(0));
			}
		}catch(Exception e){
			log.error("Call CacheMonitorAction's Method clearFrameCache has Exception :"+e.getMessage());
		}
	}
	
	/**
	 * 清除制定业务某类型部分缓存
	 * @param appId
	 * @param type
	 * @param keys
	 * @throws Exception
	 */
	public void clearBusiCache(String appId,String type) throws Exception {
		if (StringUtils.isBlank(appId))
			return;
		try{
			IAPIControlCacheSV controlCacheSV=(IAPIControlCacheSV)ServiceFactory.getService(IAPIControlCacheSV.class);
			controlCacheSV.clearBusiCache(appId,type);			
		}catch(Exception e){
			log.error("Call CacheMonitorAction's Method clearBusiCache has Exception :"+e.getMessage());
		}
	}
	
	/**
	 * 批量清除制定业务某类型部分缓存
	 * @param appIds
	 * @param type
	 * @throws Exception
	 */
	public void clearBusiCache(Object[] appIds,String type) throws Exception{
	    if(null==appIds||0==appIds.length) return;
        try{
            IAPIControlCacheSV controlCacheSV=(IAPIControlCacheSV)ServiceFactory.getService(IAPIControlCacheSV.class);
            for(int i=0;i<appIds.length;i++){
                controlCacheSV.clearBusiCache(appIds[i].toString(),type);
            }
        }catch(Exception e){
            log.error("Call CacheMonitorAction's Method clearBusiCache has Exception :"+e.getMessage());
        }
	}
	
	/**
	 * 批量清除制定业务多个类型缓存
	 * @param appIds
	 * @param types
	 * @throws Exception
	 */
	public void clearBusiCache(Object[] appIds, Object[] types) throws Exception {
		if(null==appIds||0==appIds.length) return;
		try {
			IAPIControlCacheSV controlCacheSV=(IAPIControlCacheSV)ServiceFactory.getService(IAPIControlCacheSV.class);
			String type = null;
			for (int i=0; i<types.length; i++) {
				type = types[i].toString().trim();
				for (int j=0; j<appIds.length; j++) {
					controlCacheSV.clearBusiCache(appIds[j].toString(),type);
				}
			}
		} catch (Exception e) {
			log.error("Call CacheMonitorAction's Method clearBusiCache has Exception :"+e.getMessage());
		}
	}
	
	/**
	 * 批量清除框架指定类型缓存
	 * @param appId
	 * @param type
	 * @throws Exception
	 */
	public void clearFrameCacheByType(Object[] appIds,String type) throws Exception {
		if (appIds==null || appIds.length<1)
			return;
		try{
			IAPIControlCacheSV controlCacheSV=(IAPIControlCacheSV)ServiceFactory.getService(IAPIControlCacheSV.class);
			controlCacheSV.clearFrameCacheByType(appIds,type);
			
		}catch(Exception e){
			log.error("Call CacheMonitorAction's Method clearFrameCacheByType has Exception :"+e.getMessage());
		}
	}
	
	/**
	 * 获取本地监控缓存信息
	 * @return
	 * @throws Exception
	 */
	public List getAllLocalCache() throws Exception {
		List cache = null;
		Map cacheInfo = new HashMap();
		Map localCache = null;
		try{
			IAPIControlCacheSV controlCacheSV=(IAPIControlCacheSV)ServiceFactory.getService(IAPIControlCacheSV.class);
			IAIMonStaticDataSV staticDataSV=(IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
			IBOAIMonStaticDataValue[] result=staticDataSV.queryByCodeType("CACHE_TYPE");
			
			cache = controlCacheSV.getAllLocalCache();
			
			if (result != null) {
				for (int i = 0; i < result.length; i++) {
					cacheInfo.put(result[i].getCodeValue(), result[i].getCodeName());
				}
			}

			if (cache != null) {
				for (int i = 0; i < cache.size(); i++ ) {
					localCache = (Map) cache.get(i);
					localCache.put("CACHENAME", cacheInfo.get(localCache.get("CLASSNAME")));
				}
			}

		}catch(Exception e){
			log.error("Call CacheMonitorAction's Method getAllLocalCache has Exception :"+e.getMessage());
		}
		return cache;
	}
	
	/**
	 * 刷新监控自有缓存
	 * @throws Exception
	 */
	public String clearAllLocalCache() throws Exception {
		String result = "";
		try{
			IAPIControlCacheSV controlCacheSV=(IAPIControlCacheSV)ServiceFactory.getService(IAPIControlCacheSV.class);
			controlCacheSV.clearAllLocalCache();
			// "刷新缓存成功"
			result = AIMonLocaleFactory.getResource("MOS0000011");
		}catch(Exception e){
			log.error("Call CacheMonitorAction's Method clearAllLocalCache has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 刷新选中缓存
	 * @throws Exception
	 */
	public void clearSelectedCache(Object[] keys) throws Exception {
		if (keys == null || keys.length < 1) {
			return;
		}
		try {
			IAPIControlCacheSV controlCacheSV=(IAPIControlCacheSV)ServiceFactory.getService(IAPIControlCacheSV.class);
			for (int i = 0; i < keys.length; i++) {
				controlCacheSV.clearSelectedCache(keys[i].toString());
			}
		} catch (Exception e) {
			log.error("Call CacheMonitorAction's Method clearSelectedCache has Exception :"+e.getMessage());
		}
	}
	
}
