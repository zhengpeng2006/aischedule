package com.asiainfo.monitor.busi.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.common.AIConfigManager;
import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.complex.cache.ICache;

public class MonCacheManager {

	private static boolean IS_READONLY = false;
	
	static{
		try {
			String strReturnUnmodifiedMap = AIConfigManager.getConfigItem("CACHE_READONLY");
			if (!StringUtils.isBlank(strReturnUnmodifiedMap)) {
				if (strReturnUnmodifiedMap.trim().equalsIgnoreCase("true")) {
					IS_READONLY = true;
				} else {
					IS_READONLY = false;
				}
			} else {
				IS_READONLY = false;
			}
		} catch (Throwable ex) {
			IS_READONLY = false;
		}
	}
	
	public static boolean getCacheReadOnlySet(){
		return IS_READONLY;
	}
	
	/**
	 * 刷新监控自有缓存
	 * @throws Exception
	 */
	public static void clearAllCache() throws Exception{
		Map cacheMap=CacheFactory._getCacheInstances();
		if (cacheMap!=null && cacheMap.size()>0){
			Iterator it=cacheMap.entrySet().iterator();
			while (it.hasNext()){
				Entry entry=(Entry)it.next();
				ICache instance=(ICache)entry.getValue();
				if (instance.getClass().getPackage().getName().indexOf("com.asiainfo.appframe.ext.monitor.cache")>=0){
					instance.refresh();
				}
			}
		}
	}
	
	/**
	 * 取得监控自有缓存
	 * @throws Exception
	 */
	public static List getAllCache() throws Exception {
		List cache = new ArrayList();
		Map tmp = null;
		long count = 0;
		String className = null;
		Map cacheMap=CacheFactory._getCacheInstances();
		if (cacheMap!=null && cacheMap.size()>0){
			Iterator it=cacheMap.entrySet().iterator();
			while (it.hasNext()){
				tmp = new HashMap();
				Entry entry=(Entry)it.next();
				ICache instance=(ICache)entry.getValue();
				if (instance.getClass().getPackage().getName().indexOf("com.asiainfo.appframe.ext.monitor.cache")>=0){
					className = instance.getClass().getName();
					if (instance.getAll() != null) {
						count = instance.getAll().size();
					}
					tmp.put("CHK", false);
					tmp.put("CLASSNAME", className);
					tmp.put("COUNT", count);
					cache.add(tmp);
				}
			}
		}
		
		return cache;
	}
	
	/**
	 * 存储缓存对象
	 * @param clazz
	 * @param key
	 * @param obj
	 * @throws Exception
	 */
	public static void put(Class clazz,Object key,Object obj) throws Exception{
		Map map=getAll(clazz);
        synchronized (map) {
			if (!map.containsKey(key)){
				map.put(key,obj);
			}
        }
	}
	/**
	 * 移除缓存对象
	 * @param clazz
	 * @param key
	 * @throws Exception
	 */
	public static void remove(Class clazz,Object key) throws Exception{
		Map map=getAll(clazz);
		synchronized(map){
			if (map.containsKey(key)){
				map.remove(key);
			}
		}
	}
	
	/**
	 * 读取所有缓存
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static HashMap getAll(Class clazz) throws Exception{
		return CacheFactory.getAll(clazz);
	}
	
	public static Object get(Class clazz, Object key) throws Exception{
		return CacheFactory.get(clazz,key);
	}
	/**
	 * 刷新选中缓存
	 * @param key
	 * @throws Exception
	 */
	public static void clearSelectedCache(String key) throws Exception {
		Map cacheMap=CacheFactory._getCacheInstances();
		ICache instance=(ICache)cacheMap.get(Class.forName(key));
		if (instance.getClass().getPackage().getName().indexOf("com.asiainfo.appframe.ext.monitor.cache")>=0){
			instance.refresh();
		}
	}
	
	
	
	public static void main(String args[]) throws Exception {

	}
	
}
