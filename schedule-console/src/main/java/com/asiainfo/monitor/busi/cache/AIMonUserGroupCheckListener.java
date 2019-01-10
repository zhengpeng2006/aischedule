package com.asiainfo.monitor.busi.cache;


import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupSV;

public class AIMonUserGroupCheckListener implements ICacheListener {

	public AIMonUserGroupCheckListener() {
		super();
		// TODO 自动生成构造函数存根
	}

	public void validityCheck(ICheckCache cacheObj) throws Exception {
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonUserGroupCacheImpl.class,cacheObj.getKey());
		}
		IAIMonUserGroupSV userGroupSV=(IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
		cacheObj=userGroupSV.getUserGroupByIdFromDb(cacheObj.getKey());
		if (cacheObj!=null && cacheObj.getEnable()){
			MonCacheManager.put(AIMonUserGroupCacheImpl.class,cacheObj.getKey(),cacheObj);
		}
	}

}
