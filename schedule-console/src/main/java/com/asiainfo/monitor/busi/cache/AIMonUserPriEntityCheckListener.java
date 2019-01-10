package com.asiainfo.monitor.busi.cache;


import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserPriEntityCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserPriEntitySV;

public class AIMonUserPriEntityCheckListener implements ICacheListener {

	public AIMonUserPriEntityCheckListener() {
		super();
		// TODO 自动生成构造函数存根
	}

	public void validityCheck(ICheckCache cacheObj) throws Exception {		
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonUserPriEntityCacheImpl.class,cacheObj.getKey());
		}		
		IAIMonUserPriEntitySV entirySV=(IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
		cacheObj=entirySV.getPriEntiryByIdFromDb(cacheObj.getKey());
		
		if (cacheObj!=null && cacheObj.getEnable()){
			MonCacheManager.put(AIMonUserPriEntityCacheImpl.class,cacheObj.getKey(),cacheObj);
		}
	}

}
