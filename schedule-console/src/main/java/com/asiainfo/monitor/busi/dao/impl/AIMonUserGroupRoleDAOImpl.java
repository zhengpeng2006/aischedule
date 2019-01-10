package com.asiainfo.monitor.busi.dao.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserGroupRoleDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRoleRelEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRoleRelValue;

public class AIMonUserGroupRoleDAOImpl implements IAIMonUserGroupRoleDAO{

	/**
	 * 根据条件取得用户组角色关系信息
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRoleRelValue[] query(String condition, Map parameter) throws Exception {
		return BOAIMonUserGroupRoleRelEngine.getBeans(condition, parameter);
	}
	
	/**
	 * 根据主键取得用户组角色关系信息
	 * 
	 * @param relateId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRoleRelValue getBeanById(long relateId) throws Exception {
		return BOAIMonUserGroupRoleRelEngine.getBean(relateId);
	}
	
	/**
	 * 根据组ID取得对应的角色ID
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRoleRelValue[] getRoleIdByGroupId(String groupId) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(groupId)) {
			condition.append(IBOAIMonUserGroupRoleRelValue.S_UserGroupId).append(" =:groupId");
			parameter.put("groupId", Long.parseLong(groupId));
		}
		return query(condition.toString(), parameter);
	}
	
	/**
	 * 保存或修改用户组角色关系信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRoleRelValue value) throws Exception {
		if (value.isNew()) {
			value.setRelatId(BOAIMonUserGroupRoleRelEngine.getNewId().longValue());
		}
		BOAIMonUserGroupRoleRelEngine.save(value);
	}
	
	/**
	 * 批量保存或修改用户组角色关系信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRoleRelValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setRelatId(BOAIMonUserGroupRoleRelEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate...");
		}
		BOAIMonUserGroupRoleRelEngine.saveBatch(values);
	}
	
	/**
	 * 删除用户组角色关系信息
	 * 
	 * @param relateId
	 * @throws Exception
	 */
	public void delete(long relateId) throws Exception {
		IBOAIMonUserGroupRoleRelValue value = BOAIMonUserGroupRoleRelEngine.getBean(relateId);
		if (null != value) {
			value.delete();
			BOAIMonUserGroupRoleRelEngine.save(value);
		}
	}

    public boolean checkUserGroupRoleRelByUserId(String userId, String userGroupId) throws RemoteException, Exception
    {
        StringBuilder sb = new StringBuilder("");
        sb.append(IBOAIMonUserGroupRoleRelValue.S_UserRoleId + " = :userRoleId  and ");
        sb.append(IBOAIMonUserGroupRoleRelValue.S_UserGroupId + " = :userGroupId ");
        Map parameter = new HashMap();
        parameter.put("userRoleId", userId);
        parameter.put("userGroupId", userGroupId);
        IBOAIMonUserGroupRoleRelValue[] values = BOAIMonUserGroupRoleRelEngine.getBeans(sb.toString(), parameter);
        if(values != null && values.length >= 1)
            return true;
        else
            return false;
    }
}
