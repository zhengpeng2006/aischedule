package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCmdSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdValue;

public class AIMonCmdCacheImpl extends AbstractCache
{

    @Override
    public HashMap getData() throws Exception
    {
        HashMap result = new HashMap();
        IAIMonCmdSV cmdSV = (IAIMonCmdSV) ServiceFactory.getService(IAIMonCmdSV.class);

        if(cmdSV != null) {
            IBOAIMonCmdValue[] cmds = cmdSV.getAllCmd();
            if(cmds != null && cmds.length > 0) {
                for(int i = 0; i < cmds.length; i++) {
                    Cmd cmd = cmdSV.wrapperCmdConfigByBean(cmds[i]);
                    if(cmd != null) {
                        result.put(cmd.getCmd_Id() + "", cmd);
                    }
                }
            }
        }
        return result;
    }

}
