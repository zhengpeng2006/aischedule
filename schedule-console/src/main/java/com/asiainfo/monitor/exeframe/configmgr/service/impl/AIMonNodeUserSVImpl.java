package com.asiainfo.monitor.exeframe.configmgr.service.impl;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonNodeUserDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeUserValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonNodeUserSV;

public class AIMonNodeUserSVImpl implements IAIMonNodeUserSV
{
    /**
     * 根据节点标识查询节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonNodeUserValue[] qryNodeUserByNodeId(String nodeId) throws Exception
    {
        IAIMonNodeUserDAO dao = (IAIMonNodeUserDAO) ServiceFactory.getService(IAIMonNodeUserDAO.class);
        return dao.qryNodeUserByNodeId(nodeId);
    }

    /**
     * 根据节点用户标识查询该节点下的用户
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonNodeUserValue qryNodeUserById(String nodeUserId) throws Exception
    {
        IAIMonNodeUserDAO dao = (IAIMonNodeUserDAO) ServiceFactory.getService(IAIMonNodeUserDAO.class);
        return dao.qryNodeUserById(nodeUserId);
    }

    /**
     * 删除节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public void delete(IBOAIMonNodeUserValue value) throws Exception
    {
        IAIMonNodeUserDAO dao = (IAIMonNodeUserDAO) ServiceFactory.getService(IAIMonNodeUserDAO.class);
        dao.delete(value);
    }

    /**
     * 保存节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public void save(IBOAIMonNodeUserValue value) throws Exception
    {
        IAIMonNodeUserDAO dao = (IAIMonNodeUserDAO) ServiceFactory.getService(IAIMonNodeUserDAO.class);
        dao.save(value);
    }

}
