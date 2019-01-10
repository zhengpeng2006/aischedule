package com.asiainfo.monitor.busi.cache;


import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserRoleCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleSV;

public class AIMonUserRoleCheckListener implements ICacheListener {

	public AIMonUserRoleCheckListener() {
		super();
		// TODO 自动生成构造函数存根
	}

	public void validityCheck(ICheckCache cacheObj) throws Exception {
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonUserRoleCacheImpl.class,cacheObj.getKey());
		}
		IAIMonUserRoleSV userRoleSV=(IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
		cacheObj=userRoleSV.getRoleGuildByIdFromDb(cacheObj.getKey());
		if (cacheObj!=null && cacheObj.getEnable()){
			MonCacheManager.put(AIMonUserRoleCacheImpl.class,cacheObj.getKey(),cacheObj);
		}
	}

}
