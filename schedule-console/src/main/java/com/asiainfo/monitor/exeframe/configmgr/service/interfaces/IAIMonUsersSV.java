package com.asiainfo.monitor.exeframe.configmgr.service.interfaces;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonUsersValue;

public interface IAIMonUsersSV
{
    /**
     * 根据用户编码查询用户信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonUsersValue[] qryUsersInfoByUserCode(String userCode) throws Exception;

    /**
     * 根据用户Id查询用户信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonUsersValue qryUserById(String userId) throws Exception;

    /**
     * 删除用户信息
     * 
     * @param request
     * @throws Exception
     */
    public void delete(IBOAIMonUsersValue value) throws Exception;

    /**
     * 保存用户信息
     * 
     * @param request
     * @throws Exception
     */
    public void save(IBOAIMonUsersValue value) throws Exception;

    /**
     * 获取所有用户信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonUsersValue[] qryAllUsersInfo() throws Exception;

    /**
     * 判断用户编码是否存在
     * 
     * @param request
     * @throws Exception
     */
    public boolean userCodeIsExist(String userCode) throws Exception;

    /**
     * 根据主机标识查询用户信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonUsersValue qryUserInfoByHostId(String hostId) throws Exception;
}
