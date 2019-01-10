package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRoleRelValue;


public interface IAIMonUserGroupRoleSV {

	/**
	 * 根据主键取得用户组角色关系信息
	 * 
	 * @param relateId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRoleRelValue getBeanById(long relateId) throws RemoteException,Exception;
	
	/**
	 * 根据组ID取得对应的角色ID
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRoleRelValue[] getRoleIdByGroupId(String groupId) throws RemoteException,Exception;
	
	/**
	 * 保存或修改用户组角色关系信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRoleRelValue value) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改用户组角色关系信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRoleRelValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存用户组和角色关系信息
	 * 
	 * @param oldValues
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRoleRelValue[] oldValues, IBOAIMonUserGroupRoleRelValue[] values) throws RemoteException,Exception;
	
	/**
	 * 删除用户组角色关系信息
	 * 
	 * @param relateId
	 * @throws Exception
	 */
	public void delete(long relateId) throws RemoteException,Exception;

    /**
     * 根据组和用户标识删除信息
     * 
     * @param userGroupId
     * @param userId
     */
    public boolean delete(long userGroupId, long userRoleId) throws Exception;

    /**
     * 检查该角色是否已经加入用户组
     * 
     * @param userId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public boolean checkUserGroupRoleRelByUserId(String userRoleId, String userGroupId) throws RemoteException, Exception;
}
