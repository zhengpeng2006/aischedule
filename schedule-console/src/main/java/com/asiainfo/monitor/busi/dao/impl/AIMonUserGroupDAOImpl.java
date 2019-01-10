package com.asiainfo.monitor.busi.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserGroupDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;

public class AIMonUserGroupDAOImpl implements IAIMonUserGroupDAO {

    private Timestamp nowDate = new Timestamp(System.currentTimeMillis());
	/**
	 * 根据条件取得用户组信息
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupValue[] query(String condition, Map parameter) throws Exception {
		return BOAIMonUserGroupEngine.getBeans(condition, parameter);
	}
	
	/**
	 * 读取所有的用户组信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserGroupValue[] getAllUserGroup() throws Exception{
		return this.query(IBOAIMonUserGroupValue.S_State+" = 'U' ",null);
	}
	/**
     * 保存或修改用户组信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserGroupValue value) throws Exception
    {
        if(value.isNew()) {
            value.setUserGroupId(BOAIMonUserGroupEngine.getNewId().longValue());
            value.setState(UserPrivConst.USER_STATE_U);
            value.setCreateDate(nowDate);
        }
        value.setDoneDate(nowDate);

        BOAIMonUserGroupEngine.save(value);
    }

    /**
     * 批量保存或修改用户组信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserGroupValue[] values) throws Exception
    {
        if(values != null && values.length > 0) {
            for(int i = 0; i < values.length; i++) {
                if(values[i].isNew()) {
                    values[i].setUserGroupId(BOAIMonUserGroupEngine.getNewId().longValue());
                }
            }
        } else {
            throw new Exception("no data to saveOrUpdate...");
        }
        BOAIMonUserGroupEngine.saveBatch(values);
    }

    /**
     * 根据条件取得用户组信息
     * 
     * @param groupCode
     * @param groupName
     * @return
     * @throws Exception
     */
    public IBOAIMonUserGroupValue[] getUserGroupByCond(String groupCode, String groupName) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
    
        if(StringUtils.isNotBlank(groupCode)) {
            condition.append(IBOAIMonUserGroupValue.S_GroupCode).append(" = :groupCode");
            parameter.put("groupCode", groupCode);
        }
        if(StringUtils.isNotBlank(groupName)) {
            if(StringUtils.isNotBlank(groupCode)) {
                condition.append(" and ");
            }
            condition.append(IBOAIMonUserGroupValue.S_GroupName).append(" like :groupName");
            parameter.put("groupName", groupName + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(IBOAIMonUserGroupValue.S_State).append(" = :pstate ");
        parameter.put("pstate", "U");
        return query(condition.toString(), parameter);
    }

    /**
     * 删除用户组信息
     * 
     * @param userGroupId
     * @throws Exception
     */
    public void delete(long userGroupId) throws Exception
    {
        IBOAIMonUserGroupValue value = BOAIMonUserGroupEngine.getBean(userGroupId);
        if(null != value) {
            //			value.delete();
            value.setState("E");
            BOAIMonUserGroupEngine.save(value);
        }
    }

    /**
     * 根据主键取得用户组信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserGroupValue getBeanById(long userGroupId) throws Exception
    {
        return BOAIMonUserGroupEngine.getBean(userGroupId);
    }
}
