package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserValue;

public class AIMonUserRoleDomainCacheImpl extends AbstractCache
{

    private static final Log log = LogFactory.getLog(AIMonUserRoleDomainCacheImpl.class);

    public HashMap getData() throws Exception
    {
        HashMap result = new HashMap();
        IAIMonUserSV userSV = (IAIMonUserSV) ServiceFactory.getService(IAIMonUserSV.class);
        IBOAIMonUserValue[] values = userSV.getAllUserBean();

        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                UserGroup userInfo = userSV.wrapperUserRoleDomainByBean(values[i]);
                if (userInfo != null)
                    result.put(userInfo.getId(), userInfo);
            }
        }

        return result;
    }

}
