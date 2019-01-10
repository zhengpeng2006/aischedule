package com.asiainfo.monitor.busi.cache;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonHostUserCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonHostUserSV;

public class AIMonHostUserCheckListener implements ICacheListener
{
    @Override
    public void validityCheck(ICheckCache cacheObj) throws Exception
    {
        if(MonCacheManager.getCacheReadOnlySet())
            return;
        if(cacheObj != null) {
            MonCacheManager.remove(AIMonHostUserCacheImpl.class, cacheObj.getKey());
        }

        IAIMonHostUserSV hostUserSV = (IAIMonHostUserSV) ServiceFactory.getService(IAIMonHostUserSV.class);
        cacheObj = hostUserSV.getHostUserByIdFromDb(cacheObj.getKey());

        if(cacheObj != null && cacheObj.getEnable()) {
            MonCacheManager.put(AIMonHostUserCacheImpl.class, cacheObj.getKey(), cacheObj);
        }
    }
}
