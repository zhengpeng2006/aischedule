package com.asiainfo.monitor.busi.cache;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonPhostCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;

public class AIMonPhostCheckListener implements ICacheListener {


	/**
	 * 清除并重置缓存对象
	 */
	public void validityCheck(ICheckCache cacheObj) throws Exception {
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonPhostCacheImpl.class,cacheObj.getKey());
		}
		
		IAIMonPhysicHostSV phostSV=(IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
		cacheObj=phostSV.getPhostByPhostId(cacheObj.getKey());
		
		if (cacheObj!=null && cacheObj.getEnable()){
			MonCacheManager.put(AIMonPhostCacheImpl.class,cacheObj.getKey(),cacheObj);
		}
	}
}
