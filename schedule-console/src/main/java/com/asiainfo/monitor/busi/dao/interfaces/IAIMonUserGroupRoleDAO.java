package com.asiainfo.monitor.busi.dao.interfaces;

import java.rmi.RemoteException;
import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRoleRelValue;

public interface IAIMonUserGroupRoleDAO {

	/**
	 * 根据条件取得用户组角色关系信息
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRoleRelValue[] query(String condition, Map parameter) throws Exception;
	
	/**
	 * 根据主键取得用户组角色关系信息
	 * 
	 * @param relateId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRoleRelValue getBeanById(long relateId) throws Exception;
	
	/**
	 * 根据组ID取得对应的角色ID
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRoleRelValue[] getRoleIdByGroupId(String groupId) throws Exception;
	
	/**
	 * 保存或修改用户组角色关系信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRoleRelValue value) throws Exception;
	
	/**
	 * 批量保存或修改用户组角色关系信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRoleRelValue[] values) throws Exception;
	
	/**
	 * 删除用户组角色关系信息
	 * 
	 * @param relateId
	 * @throws Exception
	 */
	public void delete(long relateId) throws Exception;

    public boolean checkUserGroupRoleRelByUserId(String userRoleId, String userGroupId) throws RemoteException, Exception;
}
