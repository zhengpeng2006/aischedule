package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRelValue;


public interface IAIMonUserGroupRelSV {

	/**
	 * 根据主键取得用户与组关系
	 * 
	 * @param relateId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue getBeanById(long relateId) throws RemoteException,Exception;
	
	/**
	 * 根据组ID取得该组的用户ID
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue[] getUserIdByGroupId(String groupId) throws RemoteException,Exception;
	
	/**
	 * 根据用户标识获取用户与组的关联关系
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue getUserGroupRelatByUserId(String userId) throws RemoteException,Exception;
	
	/**
	 * 保存或修改用户与组关系
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRelValue value) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改用户与组关系
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRelValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存用户组与用户的关系
	 * 
	 * @param oldValues
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRelValue[] oldValues, IBOAIMonUserGroupRelValue[] values, Object[] userIds) throws RemoteException,Exception;
	
	/**
	 * 删除用户与组关系
	 * 
	 * @param relateId
	 * @throws Exception
	 */
	public void delete(long relateId) throws RemoteException,Exception;
	
	/**
	 * 检查该用户是否已经加入用户组
	 * @param userId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public boolean checkUserGroupRelByUserId(String userId) throws RemoteException,Exception;

    /**
     * 根据组和用户标识删除信息
     * 
     * @param userGroupId
     * @param userId
     */
    public boolean delete(long userGroupId, long userId) throws Exception;

    /**
     * 检查该用户是否已经加入用户组
     * 
     * @param userId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public boolean checkUserGroupRelByUserId(String userId, String userGroupId) throws RemoteException, Exception;
}
