package com.asiainfo.monitor.busi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.complex.mbean.standard.cache.CacheSummary;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.asyn.operation.impl.BusiCacheTypeAsynOperate;
import com.asiainfo.monitor.busi.asyn.operation.impl.FrameCacheTypeAsynOperate;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowCacheSV;
import com.asiainfo.monitor.busi.stat.BusiCacheMultitaskStat;
import com.asiainfo.monitor.busi.stat.FrameCacheMultitaskStat;
import com.asiainfo.monitor.interapi.api.cache.BusiCacheMonitorApi;
import com.asiainfo.monitor.interapi.config.AIBusinessCacheInfo;
import com.asiainfo.monitor.interapi.config.AIFrameCache;
import com.asiainfo.monitor.interapi.config.AIFrameCacheInfo;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;
import com.asiainfo.monitor.tools.util.Util;

public class APIShowCacheSVImpl implements IAPIShowCacheSV {

	private static transient Log log=LogFactory.getLog(APIShowCacheSVImpl.class);
	
	/**
	 * 读取框架缓存类型
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getFrameCacheType(Object[] appIds) throws Exception {
		List result = new ArrayList();
		try {
			FrameCacheTypeAsynOperate asynOperate = new FrameCacheTypeAsynOperate();
			int threadCount = (appIds.length >> 1) + 1;
	
			//返回虚拟机可用CPU数量
			int cpuCount = Runtime.getRuntime().availableProcessors();
			if (threadCount > (cpuCount * 3)) {
				threadCount = cpuCount * 3;
			}
			List list = asynOperate.asynOperation(threadCount, -1, appIds, -1);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					SimpleResult sr = (SimpleResult) list.get(i);
					if (sr != null && sr.isSucc()) {
						if (sr.getValue() != null
								&& List.class.isAssignableFrom(sr.getValue().getClass())) {
							for (int j = 0; j < ((List) sr.getValue()).size(); j++) {
								String type = String.valueOf(((List) sr.getValue()).get(j));
								if (!result.contains(type)) {
									result.add(type);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Call APIShowCacheSVImpl's Method getFrameCacheType has Exception :" + e.getMessage());
		}
		return result;
	}

	/**
	 * 读取业务缓存类型
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getBusiCacheType(Object[] appIds) throws Exception {
		List result = new ArrayList();
		try {
			BusiCacheTypeAsynOperate asynOperate = new BusiCacheTypeAsynOperate();
			int threadCount = (appIds.length >> 1) + 1;
			int cpuCount = Runtime.getRuntime().availableProcessors();
			if (threadCount > (cpuCount * 3))
				threadCount = cpuCount * 3;
			List list = asynOperate.asynOperation(threadCount, -1, appIds, -1);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					SimpleResult sr = (SimpleResult) list.get(i);
					if (sr != null && sr.isSucc()) {
						if (sr.getValue() != null) {
							String[] types = (String[]) sr.getValue();
							if (types != null && types.length > 0) {
								for (int j = 0; j < types.length; j++) {
									if (!result.contains(types[j]))
										result.add(types[j]);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Call APIShowCacheSVImpl's Method getFrameCacheType has Exception :"
					+ e.getMessage());
		}
		return result;
	}

	/**
	 * 读取业务所有类型的缓存
	 * @param appId
	 * @param type
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List getBusiCache(String appId, String type, Integer start, Integer end)
			throws Exception {
		if (StringUtils.isBlank(appId))
			return null;
		List result = new ArrayList();
	
		try {
			IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory
					.getService(IAIMonServerSV.class);
			ServerConfig appServer = serverSV.getServerByServerId(appId);
			if (appServer == null) {
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070",
						appId));
			}
			if (appServer.getJmxSet() == null
					|| appServer.getJmxSet().getValue().equals("OFF")
					|| appServer.getJmxSet().getValue().equals("FALSE")) {
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071",
						appId));
			}
			// 全部
			if (AIMonLocaleFactory.getResource("MOS0000007").equals(type)) {
				type = "";
			}
			CacheSummary[] busiCaches = BusiCacheMonitorApi.getAllCaches(
					appServer.getLocator_Type(), appServer.getLocator(), type);
			if (busiCaches != null) {
				for (int i = 0; i < busiCaches.length; i++) {
					Map tmp = new HashMap();
					tmp.put("CLASSNAME", busiCaches[i].getClassName());
					tmp.put("NEWCOUNT", busiCaches[i].getNewCount());
					tmp.put("OLDCOUNT", busiCaches[i].getOldCount());
					tmp.put("LASTREFRESHENDTIME", Util
							.formatDateFromLog(busiCaches[i]
									.getLastRefreshEndTime()));
					tmp.put("LASTREFRESHSTARTTIME", Util
							.formatDateFromLog(busiCaches[i]
									.getLastRefreshStartTime()));
					result.add(tmp);
				}
			}
		} catch (Exception e) {
			log.error("Call APIShowCacheSVImpl's Method getFrameCache has Exception :"
					+ e.getMessage());
		}
		return result;
	}

	/**
	 * 根据多应用统计缓存信息
	 * @param appIds
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getBusiCacheFromMultiAppForAppFrame(Object[] appIds, String type) throws Exception {
		if (appIds == null || appIds.length < 1) {
			return null;
		}
		List result = new ArrayList();
		try {
			CacheSummary cs = null;
			Object[] objCache = null;
			Map tmp = null;
			int threadCount = (appIds.length >> 1) + 1;
			int cpuCount = Runtime.getRuntime().availableProcessors();
			if (threadCount > (cpuCount * 3)) {
				threadCount = cpuCount * 3;
			}
			BusiCacheMultitaskStat cacheStat = new BusiCacheMultitaskStat(
					threadCount, -1, appIds, type);
			Map cacheMap = cacheStat.getSummary();
			if (cacheMap != null && cacheMap.size() > 0) {
				objCache = cacheMap.values().toArray();
			}
			for (int i = 0; i < objCache.length; i++) {
				DataContainerInterface item = new DataContainer();
				cs = (CacheSummary) objCache[i];
				item.set("CHK", false);
				item.set("CLASSNAME", cs.getClassName());
				item.set("NEWCOUNT", cs.getNewCount());
				item.set("OLDCOUNT", cs.getOldCount());
				item.set("LASTREFRESHENDTIME",
						Util.formatDateFromLog(cs.getLastRefreshEndTime()));
				item.set("LASTREFRESHSTARTTIME",
						Util.formatDateFromLog(cs.getLastRefreshStartTime()));
				result.add(tmp);
			}
		} catch (Exception e) {
			log.error("Call APIShowCacheSVImpl's Method getBusiCacheFromMultiApp has Exception :"
					+ e.getMessage());
			//			throw new Exception(e.getMessage());
		}
		return (DataContainerInterface[]) result
				.toArray(new DataContainerInterface[0]);
	}

	/**
	 * 读取框架所有类型的缓存
	 * @param appId
	 * @param type
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List getFrameCache(Object[] appIds, String type, String key, Integer start, Integer end) throws Exception {
		// 全部
		if (StringUtils.isBlank(type) || AIMonLocaleFactory.getResource("MOS0000007").equals(type)) {
			type = " ";//放置空字符
		}
		if (StringUtils.isBlank(key)) {
			key = " ";//放置空字符
		}
		List result = new ArrayList();
		try {
			AIFrameCacheInfo tmp = null;
			int threadCount = (appIds.length >> 1) + 1;
			int cpuCount = Runtime.getRuntime().availableProcessors();
			if (threadCount > (cpuCount * 3)){
				threadCount = cpuCount * 3;
			}
			String condition = type + TypeConst._SPLIT_CHAR + key;
			FrameCacheMultitaskStat stat = new FrameCacheMultitaskStat(threadCount, -1, appIds, condition);
			Map summary = stat.getSummary();
			if (summary != null && summary.size() > 0) {
				Iterator it = summary.values().iterator();
				AIFrameCache data = null;
				for (int i = start == -1 ? 0 : start - 1; i < (end == -1 ? summary.size() : end) && it.hasNext(); i++) {
					if (i < summary.size()) {
						tmp = new AIFrameCacheInfo();
						data = (AIFrameCache) it.next();
//						Map cacheData = new HashMap();
//						cacheData.put("CHK", "false");
//						cacheData.put("CACHE_TYPE", data.getType());
//						cacheData.put("CACHE_KEY", data.getKey());
//						if (data.getObj() != null) {
//							cacheData.put("CACHE_CODE", data.getObj().toString());
//						}
						
						tmp.setType(data.getType());
						tmp.setPrimarykey(data.getKey());
						if (data.getObj() != null) {
							tmp.setHashcode(data.getObj().toString());
						}
							
						result.add(tmp);
					}
				}
			}
		} catch (Exception e) {
			log.error("Call APIShowCacheSVImpl's Method getFrameCache has Exception :" + e.getMessage());
		}
		return result;
	}

	/**
	 * 根据多应用统计缓存信息
	 * @param appIds
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List getBusiCacheFromMultiApp(Object[] appIds, String type)throws Exception {
		if (appIds == null || appIds.length < 1){
			return null;
		}
		List result = new ArrayList();
		try {
			CacheSummary cs = null;
			Object[] objCache = null;
			AIBusinessCacheInfo tmp = null;
			int threadCount = (appIds.length >> 1) + 1;
			int cpuCount = Runtime.getRuntime().availableProcessors();
			if (threadCount > (cpuCount * 3)){
				threadCount = cpuCount * 3;
			}
			BusiCacheMultitaskStat cacheStat = new BusiCacheMultitaskStat(threadCount, -1, appIds, type);
			Map cacheMap = cacheStat.getSummary();
			if (cacheMap != null && cacheMap.size() > 0) {
				objCache = cacheMap.values().toArray();
			}
			
			for (int i = 0; i < objCache.length; i++) {
				tmp = new AIBusinessCacheInfo();
				cs = (CacheSummary) objCache[i];
//				tmp.put("CHK", false);
//				tmp.put("CLASSNAME", cs.getClassName());
//				tmp.put("NEWCOUNT", cs.getNewCount());
//				tmp.put("OLDCOUNT", cs.getOldCount());
//				tmp.put("LASTREFRESHENDTIME",Util.formatDateFromLog(cs.getLastRefreshEndTime()));
//				tmp.put("LASTREFRESHSTARTTIME",Util.formatDateFromLog(cs.getLastRefreshStartTime()));
				
				tmp.setClassname(cs.getClassName());
				tmp.setNewcount(Long.toString(cs.getNewCount()));
				tmp.setOldcount(Long.toString(cs.getOldCount()));
				tmp.setLastrefreshendtime(Util.formatDateFromLog(cs.getLastRefreshEndTime()));
				tmp.setLastrefreshstarttime(Util.formatDateFromLog(cs.getLastRefreshStartTime()));
				
				result.add(tmp);
			}
		} catch (Exception e) {
			log.error("Call APIShowCacheSVImpl's Method getBusiCacheFromMultiApp has Exception :" + e.getMessage());
		}
		return result;
	}

	/**
	 * 读取业务缓存类型
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getBusiCacheType(String appId) throws Exception {
		if (StringUtils.isBlank(appId))
			return null;
		List result = new ArrayList();
		try {
	
			IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory
					.getService(IAIMonServerSV.class);
			ServerConfig appServer = serverSV.getServerByServerId(appId);
			if (appServer == null) {
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070",
						appId));
			}
			if (appServer.getJmxSet() == null
					|| appServer.getJmxSet().getValue().equals("OFF")
					|| appServer.getJmxSet().getValue().equals("FALSE")) {
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071",
						appId));
			}
			String[] types = BusiCacheMonitorApi.getAllCacheType(
					appServer.getLocator_Type(), appServer.getLocator());
			if (types != null && types.length > 0) {
				for (int i = 0; i < types.length; i++) {
					result.add(types[i]);
				}
			}
		} catch (Exception e) {
			log.error("Call APIShowCacheSVImpl's Method getFrameCacheType has Exception :"
					+ e.getMessage());
		}
		return result;
	}
	
}
