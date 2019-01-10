package com.asiainfo.monitor.busi.cache;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonConModeCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonConModeSV;

public class AIMonConModeCheckListener implements ICacheListener
{

    @Override
    public void validityCheck(ICheckCache cacheObj) throws Exception
    {
        if(MonCacheManager.getCacheReadOnlySet())
            return;
        if(cacheObj != null) {
            MonCacheManager.remove(AIMonConModeCacheImpl.class, cacheObj.getKey());
        }

        IAIMonConModeSV conSV = (IAIMonConModeSV) ServiceFactory.getService(IAIMonConModeSV.class);
        cacheObj = conSV.getConModeByIdFromDb(cacheObj.getKey());

        if(cacheObj != null && cacheObj.getEnable()) {
            MonCacheManager.put(AIMonConModeCacheImpl.class, cacheObj.getKey(), cacheObj);
        }
    }
}
