package com.asiainfo.monitor.exeframe.configmgr.service.impl;

import java.util.List;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostConModeBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonHostConModeDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostConModeValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonHostConModeSV;

public class AIMonHostConModeSVImpl implements IAIMonHostConModeSV
{

    @Override
    public IBOAIMonHostConModeValue qryByConId(String conId) throws Exception
    {
        IAIMonHostConModeDAO dao = (IAIMonHostConModeDAO) ServiceFactory.getService(IAIMonHostConModeDAO.class);
        return dao.qryByConId(conId);
    }

    @Override
    public List<String> getConIdList(String hostId) throws Exception
    {
        IAIMonHostConModeDAO dao = (IAIMonHostConModeDAO) ServiceFactory.getService(IAIMonHostConModeDAO.class);
        return dao.getConIdList(hostId);
    }

    @Override
    public IBOAIMonHostConModeValue[] qryHostConModeByCondition(String hostId, String conId) throws Exception
    {
        IAIMonHostConModeDAO dao = (IAIMonHostConModeDAO) ServiceFactory.getService(IAIMonHostConModeDAO.class);
        return dao.qryHostConModeByCondition(hostId, conId);
    }

    @Override
    public void delete(IBOAIMonHostConModeValue[] value) throws Exception
    {
        IAIMonHostConModeDAO dao = (IAIMonHostConModeDAO) ServiceFactory.getService(IAIMonHostConModeDAO.class);
        dao.delete(value);
    }

    @Override
    public void save(BOAIMonHostConModeBean hostConBean) throws Exception
    {
        IAIMonHostConModeDAO dao = (IAIMonHostConModeDAO) ServiceFactory.getService(IAIMonHostConModeDAO.class);
        dao.save(hostConBean);
    }

    @Override
    public IBOAIMonHostConModeValue[] qryBuHostId(String hostId) throws Exception
    {
        IAIMonHostConModeDAO dao = (IAIMonHostConModeDAO) ServiceFactory.getService(IAIMonHostConModeDAO.class);
        return dao.qryBuHostId(hostId);
    }

}
