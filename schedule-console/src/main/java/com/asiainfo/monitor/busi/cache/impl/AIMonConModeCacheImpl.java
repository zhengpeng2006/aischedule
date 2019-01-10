package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonConModeValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonConModeSV;

public class AIMonConModeCacheImpl extends AbstractCache
{
    @Override
    public HashMap getData() throws Exception
    {
        HashMap result = new HashMap();
        IAIMonConModeSV conSV = (IAIMonConModeSV) ServiceFactory.getService(IAIMonConModeSV.class);

        if(conSV != null) {
            IBOAIMonConModeValue[] cons = conSV.getAllConMode();
            if(cons != null && cons.length > 0) {
                for(int i = 0; i < cons.length; i++) {
                    ConMode conMode = conSV.wrapperConModeConfigByBean(cons[i]);
                    if(conMode != null) {
                        result.put(conMode.getCon_Id() + "", conMode);
                    }
                }
            }
        }
        return result;
    }

}
