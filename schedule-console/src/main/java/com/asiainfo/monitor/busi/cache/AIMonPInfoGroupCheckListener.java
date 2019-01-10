package com.asiainfo.monitor.busi.cache;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonPInfoGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV;

public class AIMonPInfoGroupCheckListener implements ICacheListener {

	public AIMonPInfoGroupCheckListener() {
		super();
		// TODO 自动生成构造函数存根
	}

	public void validityCheck(ICheckCache cacheObj) throws Exception {
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonPInfoGroupCacheImpl.class,cacheObj.getKey());
		}
		
		IAIMonPInfoGroupSV infoGroupSV=(IAIMonPInfoGroupSV)ServiceFactory.getService(IAIMonPInfoGroupSV.class);
		cacheObj=infoGroupSV.getMonPInfoGroupByCodeFromDb(cacheObj.getKey());
		
		if (cacheObj!=null && cacheObj.getEnable()){
			MonCacheManager.put(AIMonPInfoGroupCacheImpl.class,cacheObj.getKey(),cacheObj);
		}
	}

}
