package com.asiainfo.monitor.busi.cache;


import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;

public class AIMonGroupCheckListener implements ICacheListener {

	public AIMonGroupCheckListener(){
		
	}
	
	/**
	 * 清除并重置缓存对象
	 */
	public void validityCheck(ICheckCache cacheObj) throws Exception {
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonGroupCacheImpl.class,cacheObj.getKey());
		}
		
		IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
		cacheObj=groupSV.getGroupByIdFromDb(cacheObj.getKey());
		
		if (cacheObj!=null && cacheObj.getEnable()){
			MonCacheManager.put(AIMonGroupCacheImpl.class,cacheObj.getKey(),cacheObj);
		}
	}

}
