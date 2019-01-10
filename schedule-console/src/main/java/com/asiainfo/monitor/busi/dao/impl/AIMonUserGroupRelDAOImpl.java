package com.asiainfo.monitor.busi.dao.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserGroupRelDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRelEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRelValue;

public class AIMonUserGroupRelDAOImpl implements IAIMonUserGroupRelDAO{

	/**
	 * 根据条件取得用户与组关系
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue[] query(String condition, Map parameter) throws Exception {
		return BOAIMonUserGroupRelEngine.getBeans(condition, parameter);
	}

	/**
	 * 根据主键取得用户与组关系
	 * 
	 * @param relateId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue getBeanById(long relateId) throws Exception {
		return BOAIMonUserGroupRelEngine.getBean(relateId);
	}
	
	/**
	 * 根据组ID取得该组的用户ID
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue[] getUserIdByGroupId(String groupId) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(groupId)) {
			condition.append(IBOAIMonUserGroupRelValue.S_UserGroupId).append(" = :groupId");
			parameter.put("groupId", Long.parseLong(groupId));
		}
		return query(condition.toString(), parameter);
	}
	
	/**
	 * 根据用户标识获取用户与组的关联关系
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupRelValue getUserGroupRelatByUserId(String userId) throws Exception{
		StringBuilder sb=new StringBuilder("");
		sb.append(IBOAIMonUserGroupRelValue.S_UserId+" = :userId ");
		Map parameter=new HashMap();
		parameter.put("userId",userId);
		IBOAIMonUserGroupRelValue[] values=query(sb.toString(),parameter);
		if (values!=null && values.length>0)
			return values[0];
		else
			return null;
	}

	/**
	 * 保存或修改用户与组关系
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRelValue value) throws Exception {
		if (value.isNew()) {
			value.setRelateId(BOAIMonUserGroupRelEngine.getNewId().longValue());
		}
		BOAIMonUserGroupRelEngine.save(value);
	}
	
	/**
	 * 批量保存或修改用户与组关系
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonUserGroupRelValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setRelateId(BOAIMonUserGroupRelEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate...");
		}
		BOAIMonUserGroupRelEngine.saveBatch(values);
	}
	
	/**
	 * 删除用户与组关系
	 * 
	 * @param relateId
	 * @throws Exception
	 */
	public void delete(long relateId) throws Exception{
		IBOAIMonUserGroupRelValue value = BOAIMonUserGroupRelEngine.getBean(relateId);
		if (null != value) {
			value.delete();
			BOAIMonUserGroupRelEngine.save(value);
		}
	}
	
	/**
	 * 检查该用户是否已经加入用户组
	 * @param userId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public boolean checkUserGroupRelByUserId(String userId) throws RemoteException,Exception {
		StringBuilder sb=new StringBuilder("");
		sb.append(IBOAIMonUserGroupRelValue.S_UserId+" = :userId ");
		Map parameter=new HashMap();
		parameter.put("userId",userId);
		IBOAIMonUserGroupRelValue[] values=query(sb.toString(),parameter);
		if (values!=null && values.length >= 1)
			return true;
		else
			return false;
	}

    public boolean checkUserGroupRelByUserId(String userId, String userGroupId) throws RemoteException, Exception
    {
        StringBuilder sb = new StringBuilder("");
        sb.append(IBOAIMonUserGroupRelValue.S_UserId + " = :userId ");
        sb.append(IBOAIMonUserGroupRelValue.S_UserGroupId + " = :userGroupId ");
        Map parameter = new HashMap();
        parameter.put("userId", userId);
        parameter.put("userGroupId", userGroupId);
        IBOAIMonUserGroupRelValue[] values = query(sb.toString(), parameter);
        if(values != null && values.length >= 1)
            return true;
        else
            return false;
    }

}
