package com.asiainfo.monitor.busi.service.interfaces;

import java.util.List;

import com.asiainfo.monitor.busi.cache.impl.UserGroup;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserValue;

public interface IAIMonUserSV {

	public IBOAIMonUserValue getUserInfoByCode(String userCode) throws Exception;

    /**
     * 根据主键删除用户信息
     * 
     * @param userId
     * @throws Exception
     */
    public void delete(long userId) throws Exception;

    /**
     * 根据用户编码在缓存中获取用户信息
     * @param userCode
     * @return
     * @throws Exception
     */
    public UserGroup getUserRoleDomain(String userCode) throws Exception;

    /**
     * 根据用户Code检索用户信息
     * 
     * @param userCode
     * @return IBOAIMonUserValue
     * @throws Exception
     */
    public IBOAIMonUserValue getAvailableUserInfoByCode(String userCode) throws Exception;

    /**
     * 根据用户标识读取用户拥有域信息
     * @param userId
     * @return
     * @throws Exception
     */
    public List getDomainsByUserId(String userId) throws Exception;

    /**
     * 根据标识从数据库读取用户信息并封装成用户角色域
     * @param id
     * @return
     * @throws Exception
     */
    public UserGroup getUserRoleDomainByIdFromDb(String id) throws Exception;

    /**
     * 根据标识从数据库读取用户信息并封装成用户角色域
     * @param id
     * @return
     * @throws Exception
     */
    public UserGroup getUserRoleDomainByCodeFromDb(String userCode) throws Exception;

    /**
     * 保存或修改登录用户信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserValue value) throws Exception;

    /**
     * 根据条件检索用户信息
     * 
     * @param userName
     * @param userCode
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getUserInfoByCond(String userCode, String userName) throws Exception;

    /**
     * 分页查询
     * 
     * @param userCode
     * @param userName
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getUserInfoByCond(String userCode, String userName, int start, int end) throws Exception;

    /**
     * 检查用户账号是否存在
     * 
     * @param userCode
     * @throws Exception
     */
    public boolean checkUserCodeExist(String userCode) throws Exception;

    /**
     * 获取所有用户信息
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getAllUserBean() throws Exception;
    /**
     * 根据主键取得登录用户信息
     * 
     * @param userId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue getBeanById(long userId) throws Exception;
    /**
     * 批量保存或修改登录用户信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserValue[] values) throws Exception;

    /**
     * 将用户值对象封装成用户角色域
     * @param value
     * @return
     * @throws Exception
     */
    public UserGroup wrapperUserRoleDomainByBean(IBOAIMonUserValue value) throws Exception;

    /**
     * 根据条件查询记录数
     * 
     * @param userName
     * @param userCode
     * @return
     * @throws Exception
     */
    public int getUserCntByCond(String userName, String userCode) throws Exception;
}
