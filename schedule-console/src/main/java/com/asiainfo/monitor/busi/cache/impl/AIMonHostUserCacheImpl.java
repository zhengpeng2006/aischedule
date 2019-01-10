package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostUserValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonHostUserSV;

public class AIMonHostUserCacheImpl extends AbstractCache
{

    @Override
    public HashMap getData() throws Exception
    {
        HashMap result = new HashMap();
        IAIMonHostUserSV hostUserSV = (IAIMonHostUserSV) ServiceFactory.getService(IAIMonHostUserSV.class);

        if(hostUserSV != null) {
            IBOAIMonHostUserValue[] cmds = hostUserSV.getAllUserInfo();
            if(cmds != null && cmds.length > 0) {
                for(int i = 0; i < cmds.length; i++) {
                    HostUser hostUser = hostUserSV.wrapperHostUserConfigByBean(cmds[i]);
                    if(hostUser != null) {
                        result.put(hostUser.getHost_User_Id() + "", hostUser);
                    }
                }
            }
        }
        return result;
    }

}
