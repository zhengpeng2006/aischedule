package com.asiainfo.monitor.busi.cache;


import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonServerCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;

public class AIMonServerCheckListener implements ICacheListener {

	public AIMonServerCheckListener() {
		super();
		// TODO 自动生成构造函数存根
	}

	public void validityCheck(ICheckCache cacheObj) throws Exception {
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonServerCacheImpl.class,cacheObj.getKey());
		}
		
		IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
		cacheObj=serverSV.getServerByServerIdFromDb(cacheObj.getKey());
		if (cacheObj!=null && cacheObj.getEnable()){
			MonCacheManager.put(AIMonServerCacheImpl.class,cacheObj.getKey(),cacheObj);
		}
		
	}

}
