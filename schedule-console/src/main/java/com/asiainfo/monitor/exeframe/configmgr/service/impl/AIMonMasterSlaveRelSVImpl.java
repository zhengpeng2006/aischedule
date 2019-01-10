package com.asiainfo.monitor.exeframe.configmgr.service.impl;

import java.util.List;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonMasterSlaveRelBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonMasterSlaveRelDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonMasterSlaveRelValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonMasterSlaveRelSV;

public class AIMonMasterSlaveRelSVImpl implements IAIMonMasterSlaveRelSV
{

    @Override
    public List<String> getSlaveHostId(String masterHostId) throws Exception
    {
        IAIMonMasterSlaveRelDAO msrDAO = (IAIMonMasterSlaveRelDAO) ServiceFactory.getService(IAIMonMasterSlaveRelDAO.class);
        return msrDAO.getSlaveHostId(masterHostId);
    }

    @Override
    public IBOAIMonMasterSlaveRelValue[] qryMasterSlaveByCondition(String masterHostId, String slaveHostId) throws Exception
    {
        IAIMonMasterSlaveRelDAO msrDAO = (IAIMonMasterSlaveRelDAO) ServiceFactory.getService(IAIMonMasterSlaveRelDAO.class);
        return msrDAO.qryMasterSlaveByCondition(masterHostId, slaveHostId);
    }

    @Override
    public void delete(IBOAIMonMasterSlaveRelValue[] value) throws Exception
    {
        IAIMonMasterSlaveRelDAO msrDAO = (IAIMonMasterSlaveRelDAO) ServiceFactory.getService(IAIMonMasterSlaveRelDAO.class);
        msrDAO.delete(value);
    }

    @Override
    public void save(BOAIMonMasterSlaveRelBean masterSlaveBean) throws Exception
    {
        IAIMonMasterSlaveRelDAO msrDAO = (IAIMonMasterSlaveRelDAO) ServiceFactory.getService(IAIMonMasterSlaveRelDAO.class);
        msrDAO.save(masterSlaveBean);
    }

    @Override
    public IBOAIMonMasterSlaveRelValue[] qryMasterSlaveRelByMasterHostId(String hostId) throws Exception
    {
        IAIMonMasterSlaveRelDAO msrDAO = (IAIMonMasterSlaveRelDAO) ServiceFactory.getService(IAIMonMasterSlaveRelDAO.class);
        return msrDAO.qryMasterSlaveRelByMasterHostId(hostId);
    }

    @Override
    public IBOAIMonMasterSlaveRelValue[] qryAllMasterSlaveRelInfo() throws Exception
    {
        IAIMonMasterSlaveRelDAO msrDAO = (IAIMonMasterSlaveRelDAO) ServiceFactory.getService(IAIMonMasterSlaveRelDAO.class);
        return msrDAO.qryAllMasterSlaveRelInfo();
    }

}
