package com.asiainfo.monitor.exeframe.configmgr.service.interfaces;

import com.asiainfo.monitor.busi.cache.impl.HostUser;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostUserValue;

public interface IAIMonHostUserSV {

    public IBOAIMonHostUserValue qryByCondition(String hostId, String userType) throws Exception;

    /**
     * 根据主机查询所有用户信息
     * @param hostId
     * @return
     * @throws Exception
     */
    public IBOAIMonHostUserValue[] qryByHostId(String hostId) throws Exception;

    /**
     * 
    * @Function: IAIMonHostUserSV.java
    * @Description: 根据用户标识查询用户信息
    *
    * @param:参数描述
    * @return：返回结果描述
    * @throws：异常描述
    * @author: szh
     */
    public IBOAIMonHostUserValue qryHostUserById(String hostUserId) throws Exception;

    /**
     * 
    * @Function: IAIMonHostUserSV.java
    * @Description:删除用户信息
    *
    * @param:参数描述
    * @return：返回结果描述
    * @throws：异常描述
    * @author: szh
     */
    public void delete(IBOAIMonHostUserValue value) throws Exception;

    public void save(IBOAIMonHostUserValue value) throws Exception;

    public boolean isExistByUserName(String hostId, String userName) throws Exception;

    public IBOAIMonHostUserValue[] getAllUserInfo() throws Exception;

    public HostUser wrapperHostUserConfigByBean(IBOAIMonHostUserValue value) throws Exception;

    public ICheckCache getHostUserByIdFromDb(String key) throws Exception;

}