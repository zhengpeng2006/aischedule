package com.asiainfo.monitor.busi.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.cache.CacheSummary;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.interapi.api.cache.BusiCacheMonitorApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;


public class BusiCacheMultitaskStat extends MultitaskStat {

	private static transient Log log = LogFactory.getLog(BusiCacheMultitaskStat.class);
	
	public BusiCacheMultitaskStat(int threadCount, int timeOut, Object[] ids,String type) {
	    super(threadCount, timeOut, ids,type);
	}

	@Override
	public void summary(List reqVector, Map summaryMap) throws Exception {
		if (reqVector!=null && reqVector.size()>0){
			CacheSummary cacheSummary=null;
			for (int j=0;j<reqVector.size();j++){
				String key=((CacheSummary)reqVector.get(j)).getClassName();
				if (!summaryMap.containsKey(key)){
					summaryMap.put(key,(CacheSummary)reqVector.get(j));
				}else{
					cacheSummary=(CacheSummary)summaryMap.get(key);
					cacheSummary.setNewCount(cacheSummary.getNewCount()+((CacheSummary)reqVector.get(j)).getNewCount());
					cacheSummary.setOldCount(cacheSummary.getOldCount()+((CacheSummary)reqVector.get(j)).getOldCount());
					if (cacheSummary.getLastRefreshStartTime()>((CacheSummary)reqVector.get(j)).getLastRefreshStartTime())
						cacheSummary.setLastRefreshStartTime(((CacheSummary)reqVector.get(j)).getLastRefreshStartTime());
					if (cacheSummary.getLastRefreshEndTime()<((CacheSummary)reqVector.get(j)).getLastRefreshEndTime())
						cacheSummary.setLastRefreshEndTime(((CacheSummary)reqVector.get(j)).getLastRefreshEndTime());
				}
			}
		}
	}

	@Override
	public List getSummary(String id) throws Exception {
		if (StringUtils.isBlank(id)){
			return null;
		}
		
		List result= null;
		
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=serverSV.getServerByServerId(id);
			if (appServer==null)
				return null;
			if (appServer.getJmxSet()==null || appServer.getJmxSet().getValue().equalsIgnoreCase("OFF") ||
					appServer.getJmxSet().getValue().equalsIgnoreCase("FALSE")){
				// "指定应用[appId:"+id+"]没有启动jmx注册"
				log.error(AIMonLocaleFactory.getResource("MOS0000071", id));
				return result;
			}
			// 全部
			if(AIMonLocaleFactory.getResource("MOS0000007").equals(condition) || "%".equals(condition)){
			    condition="";
			}
			CacheSummary[] busiCaches=BusiCacheMonitorApi.getAllCaches(appServer.getLocator_Type(),appServer.getLocator(),condition);
			if (busiCaches!=null){
				result=new ArrayList(busiCaches.length);
				for (int i=0;i<busiCaches.length;i++){
					result.add(busiCaches[i]);
				}
			}
		}catch(Exception e){
			log.error("Call BusiCacheMultitaskStat's method getSummary has Exception :"+e.getMessage());
		}
		return result;
	}

}
