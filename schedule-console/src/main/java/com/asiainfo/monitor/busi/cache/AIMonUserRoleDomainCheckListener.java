package com.asiainfo.monitor.busi.cache;


import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserRoleDomainCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserSV;

public class AIMonUserRoleDomainCheckListener implements ICacheListener {

	public AIMonUserRoleDomainCheckListener() {
		super();
		// TODO 自动生成构造函数存根
	}

	public void validityCheck(ICheckCache cacheObj) throws Exception {		
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonUserRoleDomainCacheImpl.class,cacheObj.getKey());
		}
		IAIMonUserSV userSV=(IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
		cacheObj=userSV.getUserRoleDomainByIdFromDb(cacheObj.getKey());
		if (cacheObj!=null && cacheObj.getEnable()){
			MonCacheManager.put(AIMonUserRoleDomainCacheImpl.class,cacheObj.getKey(),cacheObj);
		}
	}

}
