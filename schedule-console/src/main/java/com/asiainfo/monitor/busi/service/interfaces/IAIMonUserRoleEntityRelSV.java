package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleEntityRelValue;


public interface IAIMonUserRoleEntityRelSV {

	/**
	 * 根据主键取得角色与实体关系信息
	 * 
	 * @param relateId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserRoleEntityRelValue getBeanById(long relateId) throws RemoteException,Exception;
	
	/**
	 * 根据角色ID取得对应的实体权限ID
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserRoleEntityRelValue[] getEntityIdByRoleId(String roleId) throws RemoteException,Exception;
	
	/**
	 * 保存或修改角色与实体关系信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserRoleEntityRelValue value) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改角色与实体关系信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserRoleEntityRelValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存角色与实体关系信息
	 * 
	 * @param oldValues
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserRoleEntityRelValue[] oldValues,
                             IBOAIMonUserRoleEntityRelValue[] values) throws RemoteException,Exception;
	
	
	/**
	 * 获取角色关联实体的状态树
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public String getRoleEntityTreeXml(String roleId) throws RemoteException,Exception;
	/**
	 * 获取角色关联实体的状态树
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public String getRoleEntityTreeXmlNocache(String roleId) throws RemoteException,Exception;

    /**
     * 根据角色和对应实体除信息
     * 
     * @param userGroupId
     * @param userId
     */
    public boolean delete(long userRole, long entityId) throws Exception;

    /**
     * 检查该权限实体是否已经加入角色
     * 
     * @param userId
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public boolean checkUserRoleEntityRel(String entityId, String userRoleId) throws RemoteException, Exception;

    /**
     * 删除该角色的所有权限信息
     * 
     * @param userGroupId
     * @param userId
     */
    public boolean delete(long userRoleId) throws Exception;
}
