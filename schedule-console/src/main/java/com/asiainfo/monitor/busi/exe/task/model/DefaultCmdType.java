package com.asiainfo.monitor.busi.exe.task.model;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostAtomicSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerAtomicSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.CmdType;
import com.asiainfo.monitor.tools.util.TypeConst;

public abstract class DefaultCmdType extends CmdType
{

    public abstract void doTask(ITaskRtn taskRtn) throws Exception;

    public String process(String infoValue) throws Exception
    {
        String result = infoValue;
        IAIMonPhysicHostAtomicSV hostSV = null;
        IAIMonServerAtomicSV serverSV = null;
        String[] vars = com.ai.appframe2.util.StringUtils.getParamFromString(result, "{", "}");
        if(vars != null && vars.length > 0) {
            for(int pCount = 0; pCount < vars.length; pCount++) {
                KeyName itemVar = this.getContainer().getParent().findParameter(vars[pCount].toUpperCase());
                if(itemVar != null) {
                    result = StringUtils.replace(result, "{" + vars[pCount] + "}", itemVar.getKey());
                }
                else {
                    if(vars[pCount].toUpperCase().equals(TypeConst.HOST) || vars[pCount].toUpperCase().equals(TypeConst.HOST + TypeConst._NAME)
                            || vars[pCount].toUpperCase().equals(TypeConst.IP)) {
                        if(hostSV == null)
                            hostSV = (IAIMonPhysicHostAtomicSV) ServiceFactory.getService(IAIMonPhysicHostAtomicSV.class);
                        IBOAIMonPhysicHostValue host = hostSV.getPhysicHostById(((BaseContainer) this.getContainer()).getHostId());
                        if(host != null) {
                            if(vars[pCount].toUpperCase().equals(TypeConst.HOST)
                                    || vars[pCount].toUpperCase().equals(TypeConst.HOST + TypeConst._NAME))
                                result = StringUtils.replace(result, "{" + vars[pCount] + "}", host.getHostName());
                            else
                                result = StringUtils.replace(result, "{" + vars[pCount] + "}", host.getHostIp());
                        }
                    }

                    if(vars[pCount].toUpperCase().equals(TypeConst.SERVER) || vars[pCount].toUpperCase().equals(TypeConst.SERVER + TypeConst._NAME)) {
                        if(serverSV == null)
                            serverSV = (IAIMonServerAtomicSV) ServiceFactory.getService(IAIMonServerAtomicSV.class);
                        IBOAIMonServerValue server = serverSV.getServerBeanById(((BaseContainer) this.getContainer()).getServerId());
                        if(server != null) {
                            result = StringUtils.replace(result, "{" + vars[pCount] + "}", server.getServerName());
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public abstract String getType();

}
