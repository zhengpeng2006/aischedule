package com.asiainfo.monitor.busi.dao.interfaces;

import java.rmi.RemoteException;
import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRelValue;

public interface IAIMonUserGroupRelDAO {

	/**
	 * 根据条件取得用户与组关系
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue[] query(String condition, Map parameter) throws Exception;

	/**
	 * 根据主键取得用户与组关系
	 * 
	 * @param relateId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue getBeanById(long relateId) throws Exception;
	
	/**
	 * 根据组ID取得该组的用户ID
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue[] getUserIdByGroupId(String groupId) throws Exception;

	/**
	 * 根据用户标识获取用户与组的关联关系
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue getUserGroupRelatByUserId(String userId) throws Exception;
	
	/**
	 * 保存或修改用户与组关系
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRelValue value) throws Exception;
	
	/**
	 * 批量保存或修改用户与组关系
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRelValue[] values) throws Exception;
	
	/**
	 * 删除用户与组关系
	 * 
	 * @param relateId
	 * @throws Exception
	 */
	public void delete(long relateId) throws Exception;
	
	/**
	 * 检查该用户是否已经加入用户组
	 * @param userId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public boolean checkUserGroupRelByUserId(String userId) throws RemoteException,Exception;
	
    public boolean checkUserGroupRelByUserId(String userId, String userGroupId) throws RemoteException, Exception;

}
