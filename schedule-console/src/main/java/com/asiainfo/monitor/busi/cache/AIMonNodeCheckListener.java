package com.asiainfo.monitor.busi.cache;


import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonNodeCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;

public class AIMonNodeCheckListener implements ICacheListener {

	public AIMonNodeCheckListener() {
		super();
		// TODO 自动生成构造函数存根
	}

	public void validityCheck(ICheckCache cacheObj) throws Exception {
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonNodeCacheImpl.class,cacheObj.getKey());
		}
		IAIMonNodeSV hostSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
		cacheObj=hostSV.getNodeByIdFormDb(cacheObj.getKey());
		if (cacheObj!=null && cacheObj.getEnable()){
			MonCacheManager.put(AIMonNodeCacheImpl.class,cacheObj.getKey(),cacheObj);
		}
		
	}

}
