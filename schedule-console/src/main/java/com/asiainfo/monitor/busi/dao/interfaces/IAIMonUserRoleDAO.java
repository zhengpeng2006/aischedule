package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleValue;

public interface IAIMonUserRoleDAO {

	/**
	 * 根据条件取得用户角色信息
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserRoleValue[] query(String condition, Map parameter) throws Exception;
	
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
     * 保存或修改用户角色信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserRoleValue[] value) throws Exception;
}
