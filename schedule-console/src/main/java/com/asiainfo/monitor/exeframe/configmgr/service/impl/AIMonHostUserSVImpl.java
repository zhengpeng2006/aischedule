package com.asiainfo.monitor.exeframe.configmgr.service.impl;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.AIMonHostUserCheckListener;
import com.asiainfo.monitor.busi.cache.impl.HostUser;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonHostUserDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostUserValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonHostUserSV;

public class AIMonHostUserSVImpl implements IAIMonHostUserSV {

    public IBOAIMonHostUserValue qryByCondition(String hostId, String userType) throws Exception
    {
        IAIMonHostUserDAO dao = (IAIMonHostUserDAO) ServiceFactory.getService(IAIMonHostUserDAO.class);
        return dao.qryByCondition(hostId, userType);
    }

    @Override
    public IBOAIMonHostUserValue[] qryByHostId(String hostId) throws Exception
    {
        IAIMonHostUserDAO dao = (IAIMonHostUserDAO) ServiceFactory.getService(IAIMonHostUserDAO.class);
        return dao.qryByHostId(hostId);
    }

    @Override
    public IBOAIMonHostUserValue qryHostUserById(String hostUserId) throws Exception
    {
        IAIMonHostUserDAO dao = (IAIMonHostUserDAO) ServiceFactory.getService(IAIMonHostUserDAO.class);
        IBOAIMonHostUserValue value = dao.qryHostUserById(hostUserId);
        value.setUserPwd(K.k(value.getUserPwd()));
        return value;
    }

    @Override
    public void delete(IBOAIMonHostUserValue value) throws Exception
    {
        IAIMonHostUserDAO dao = (IAIMonHostUserDAO) ServiceFactory.getService(IAIMonHostUserDAO.class);
        dao.delete(value);
    }

    @Override
    public void save(IBOAIMonHostUserValue value) throws Exception
    {
        IAIMonHostUserDAO dao = (IAIMonHostUserDAO) ServiceFactory.getService(IAIMonHostUserDAO.class);
        dao.save(value);
    }

    @Override
    public boolean isExistByUserName(String hostId, String userName) throws Exception
    {
        IAIMonHostUserDAO dao = (IAIMonHostUserDAO) ServiceFactory.getService(IAIMonHostUserDAO.class);
        return dao.isExistByUserName(hostId, userName);
    }

    @Override
    public IBOAIMonHostUserValue[] getAllUserInfo() throws Exception
    {
        IAIMonHostUserDAO dao = (IAIMonHostUserDAO) ServiceFactory.getService(IAIMonHostUserDAO.class);
        return dao.getAllUserInfo();
    }

    @Override
    public HostUser wrapperHostUserConfigByBean(IBOAIMonHostUserValue value) throws Exception
    {
        if(value == null || StringUtils.isBlank(value.getState()) || value.getState().equals("E"))
            return null;
        HostUser result = new HostUser();
        result.setCreate_Date(value.getCreateDate() + "");
        result.setHost_Id(value.getHostId() + "");
        result.setHost_User_Id(value.getHostUserId() + "");
        result.setRemark(value.getRemark());
        result.setState(value.getState());
        result.setUser_Name(value.getUserName());
        result.setUser_Pwd(value.getUserPwd());
        result.setUser_Type(value.getUserType());
        result.setCacheListener(new AIMonHostUserCheckListener());
        return result;
    }

    @Override
    public ICheckCache getHostUserByIdFromDb(String id) throws Exception
    {
        if(StringUtils.isBlank(id))
            return null;
        IAIMonHostUserDAO hostUserDAO = (IAIMonHostUserDAO) ServiceFactory.getService(IAIMonHostUserDAO.class);
        IBOAIMonHostUserValue value = hostUserDAO.getBeanById(Long.parseLong(id));
        HostUser result = this.wrapperHostUserConfigByBean(value);
        return result;
    }

}