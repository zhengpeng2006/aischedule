package com.asiainfo.monitor.busi.stat;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.CacheData;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.interapi.api.cache.CacheMonitorApi;
import com.asiainfo.monitor.interapi.config.AIFrameCache;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

public class FrameCacheMultitaskStat extends MultitaskStat {

	private static transient Log log = LogFactory.getLog(FrameCacheMultitaskStat.class);
	
	public FrameCacheMultitaskStat(int threadCount, int timeOut, Object[] ids,String condition) {
		super(threadCount, timeOut, ids, condition);
	}

	@Override
	public void summary(List reqVector, Map summaryMap) throws Exception {
		if (reqVector!=null && reqVector.size()>0){
			
			CacheData objCacheData=null;
			for (int j=0;j<reqVector.size();j++){
				objCacheData=(com.ai.appframe2.common.CacheData)reqVector.get(j);
				
				String key=objCacheData.getType()+TypeConst._SPLIT_CHAR+objCacheData.getKey();
				if (!summaryMap.containsKey(key)){
					AIFrameCache cache=new AIFrameCache();
					cache.setType(objCacheData.getType());
					cache.setKey(objCacheData.getKey());
					cache.setObj(objCacheData.getObj());					
					summaryMap.put(key,cache);
				}else{
				}
			}
		}
	}

	@Override
	public List getSummary(String id) throws Exception {
		if (StringUtils.isBlank(id))
			return null;
		List result= null;
		
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=serverSV.getServerByServerId(id);
			if (appServer==null)
				return null;
			if (appServer.getJmxSet()==null || appServer.getJmxSet().getValue().equalsIgnoreCase("OFF") ||
					appServer.getJmxSet().getValue().equalsIgnoreCase("FALSE")){
				// "指定应用[{0}]没有启动jmx注册"
				log.error(AIMonLocaleFactory.getResource("MOS0000071", id));
				return result;
			}
			String type=null,key=null;
			if (condition.indexOf(TypeConst._SPLIT_CHAR)>=0){
				String[] s=StringUtils.split(condition,TypeConst._SPLIT_CHAR);
				type=s[0];
				key=s[1];
			}
			result=CacheMonitorApi.getCache(appServer.getLocator_Type(),appServer.getLocator(),type,key);
			
		}catch(Exception e){
			log.error("Call FrameCacheMultitaskStat's method getSummary has Exception :"+e.getMessage());
		}
		return result;
	}

}
