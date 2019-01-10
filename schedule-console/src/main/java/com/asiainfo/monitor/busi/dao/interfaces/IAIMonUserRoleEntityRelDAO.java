package com.asiainfo.monitor.busi.dao.interfaces;

import java.rmi.RemoteException;
import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleEntityRelValue;

public interface IAIMonUserRoleEntityRelDAO {

	/**
	 * 根据条件取得角色与实体关系信息
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserRoleEntityRelValue[] query(String condition, Map parameter) throws Exception;
	
	/**
	 * 根据主键取得角色与实体关系信息
	 * 
	 * @param relateId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserRoleEntityRelValue getBeanById(long relateId) throws Exception;
	
	/**
	 * 根据角色ID取得对应的实体权限ID
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserRoleEntityRelValue[] getEntityIdByRoleId(String roleId) throws Exception;

	/**
	 * 保存或修改角色与实体关系信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserRoleEntityRelValue value) throws Exception;
	
	/**
	 * 批量保存或修改角色与实体关系信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserRoleEntityRelValue[] values) throws Exception;
	
	/**
	 * 删除角色与实体关系信息
	 * 
	 * @param relateId
	 * @throws Exception
	 */
	public void delete(long relateId) throws Exception;
	
    public boolean checkUserRoleEntityRel(String entityId, String userRoleId) throws RemoteException, Exception;
	
}
