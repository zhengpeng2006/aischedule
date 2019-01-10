package com.asiainfo.monitor.busi.cache;


import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonDomainCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonDomainSV;

public class AIMonDomainCheckListener implements ICacheListener {

	public AIMonDomainCheckListener() {
		super();
		// TODO 自动生成构造函数存根
	}

	public void validityCheck(ICheckCache cacheObj) throws Exception {
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonDomainCacheImpl.class,cacheObj.getKey());
		}
		IAIMonDomainSV domainSV=(IAIMonDomainSV)ServiceFactory.getService(IAIMonDomainSV.class);		
		cacheObj=domainSV.getDomainByIdFromDb(cacheObj.getKey());
		if (cacheObj!=null){
			MonCacheManager.put(AIMonDomainCacheImpl.class,cacheObj.getKey(),cacheObj);
		}
	}

}
