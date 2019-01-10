package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupValue;

public interface IAIMonUserGroupDAO {

	/**
	 * 根据条件取得用户组信息
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupValue[] query(String condition, Map parameter) throws Exception;
	
	/**
	 * 读取所有的用户组信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupValue[] getAllUserGroup() throws Exception;
	
	/**
     * 保存或修改用户组信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserGroupValue value) throws Exception;

    /**
     * 批量保存或修改用户组信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserGroupValue[] values) throws Exception;

    /**
     * 根据条件取得用户组信息
     * 
     * @param groupCode
     * @param groupName
     * @return
     * @throws Exception
     */
    public IBOAIMonUserGroupValue[] getUserGroupByCond(String groupCode, String groupName) throws Exception;

    /**
     * 删除用户组信息
     * 
     * @param userGroupId
     * @throws Exception
     */
    public void delete(long userGroupId) throws Exception;

    /**
     * 根据主键取得用户组信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserGroupValue getBeanById(long userGroupId) throws Exception;
}
