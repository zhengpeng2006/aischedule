package com.asiainfo.monitor.busi.service.interfaces;

import com.asiainfo.monitor.busi.cache.impl.RoleDomain;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleValue;

public interface IAIMonUserRoleSV {

	/**
     * 删除用户角色信息
     * 
     * @param userRoleId
     * @throws Exception
     */
    public void delete(long userRoleId) throws Exception;

    /**
     * 读取所有用户角色信息
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue[] getAllUserRoleBean() throws Exception;

    /**
     * 根据用户角色标识从数据库读取用户角色并简单封装
     * @param id
     * @return
     * @throws Exception
     */
    public RoleDomain getRoleGuildByIdFromDb(String id) throws Exception;

    /**
     * 根据标识读取RoleGuild对象
     * @param id
     * @return
     * @throws Exception
     */
    public RoleDomain getRoleGuildById(String id) throws Exception;

    /**
     * 根据主键取得用户角色信息
     * 
     * @param userRoleId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue getBeanById(long userRoleId) throws Exception;

    /**
     * 保存或修改用户角色信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserRoleValue value) throws Exception;

    /**
     * 根据条件取得用户角色信息
     * 
     * @param roleCode
     * @param roleName
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue[] getUserRoleByCond(String roleCode, String roleName) throws Exception;

    /**
     * 根据条件取得用户角色信息
     * 
     * @param roleCode
     * @param roleName
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue[] getUserRoleByCond(String roleCode, String roleName, int start, int end) throws Exception;

    /**
     * 根据条件取得用户角色记录总数
     * 
     * @param roleCode
     * @param roleName
     * @return
     * @throws Exception
     */
    public int getUserRoleCntByCond(String roleCode, String roleName) throws Exception;

    /**
     * 将用户角色值对象封装
     * @param value
     * @return
     * @throws Exception
     */
    public RoleDomain wrapperRoleGuildByBean(IBOAIMonUserRoleValue value) throws Exception;

    /**
     * 根据用户Id获取角色信息
     * 
     * @param userId
     * @return IBOAIMonUserRoleValue[] 
     * @throws Exception
     */
    public IBOAIMonUserRoleValue[] queryRolesByUserId(String userId) throws Exception;

    /**
     * 保存或修改用户角色信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserRoleValue[] value) throws Exception;

    /**
     * 根据用户组查询用户角色信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue[] getUserRoleListByGroupId(String userGroupId) throws Exception;

    /**
     * 根据用户组查询要关联的用户角色信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue[] getRelUserRoleListByGroupId(String userGroupId) throws Exception;
}
