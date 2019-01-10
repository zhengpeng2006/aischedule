package com.asiainfo.monitor.exeframe.configmgr.service.impl;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonUsersDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonUsersValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonUsersSV;

public class AIMonUsersSVImpl implements IAIMonUsersSV
{
    /**
     * 根据用户编码查询用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonUsersValue[] qryUsersInfoByUserCode(String userCode) throws Exception
    {
        IAIMonUsersDAO dao = (IAIMonUsersDAO) ServiceFactory.getService(IAIMonUsersDAO.class);
        return dao.qryUsersInfoByUserCode(userCode);
    }

    /**
     * 根据用户标识查询用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonUsersValue qryUserById(String userId) throws Exception
    {
        IAIMonUsersDAO dao = (IAIMonUsersDAO) ServiceFactory.getService(IAIMonUsersDAO.class);
        return dao.qryUserById(userId);
    }

    /**
     * 删除用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public void delete(IBOAIMonUsersValue value) throws Exception
    {
        IAIMonUsersDAO dao = (IAIMonUsersDAO) ServiceFactory.getService(IAIMonUsersDAO.class);
        dao.delete(value);
    }

    /**
     * 保存用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public void save(IBOAIMonUsersValue value) throws Exception
    {
        IAIMonUsersDAO dao = (IAIMonUsersDAO) ServiceFactory.getService(IAIMonUsersDAO.class);
        dao.save(value);
    }

    /**
     * 获取所有用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonUsersValue[] qryAllUsersInfo() throws Exception
    {
        IAIMonUsersDAO dao = (IAIMonUsersDAO) ServiceFactory.getService(IAIMonUsersDAO.class);
        return dao.qryAllUsersInfo();
    }

    /**
     * 判断用户编码是否存在
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public boolean userCodeIsExist(String userCode) throws Exception
    {
        IAIMonUsersDAO dao = (IAIMonUsersDAO) ServiceFactory.getService(IAIMonUsersDAO.class);
        return dao.userCodeIsExist(userCode);
    }

    /**
     * 根据主机标识查询监控用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonUsersValue qryUserInfoByHostId(String hostId) throws Exception
    {
        IAIMonUsersDAO dao = (IAIMonUsersDAO) ServiceFactory.getService(IAIMonUsersDAO.class);
        return dao.qryUserInfoByHostId(hostId);
    }

}
