package com.asiainfo.monitor.busi.cache;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonCmdCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCmdSV;

public class AIMonCmdCheckListener implements ICacheListener
{

    @Override
    public void validityCheck(ICheckCache cacheObj) throws Exception
    {
        if(MonCacheManager.getCacheReadOnlySet())
            return;
        if(cacheObj != null) {
            MonCacheManager.remove(AIMonCmdCacheImpl.class, cacheObj.getKey());
        }

        IAIMonCmdSV cmdSV = (IAIMonCmdSV) ServiceFactory.getService(IAIMonCmdSV.class);
        cacheObj = cmdSV.getCmdByIdFromDb(cacheObj.getKey());

        if(cacheObj != null && cacheObj.getEnable()) {
            MonCacheManager.put(AIMonCmdCacheImpl.class, cacheObj.getKey(), cacheObj);
        }
    }

}
