package com.asiainfo.monitor.busi.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserRoleDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserRoleEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;

public class AIMonUserRoleDAOImpl implements IAIMonUserRoleDAO{

    private Timestamp nowDate = new Timestamp(System.currentTimeMillis());

	/**
	 * 根据条件取得用户角色信息
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserRoleValue[] query(String condition, Map parameter) throws Exception {
		return BOAIMonUserRoleEngine.getBeans(condition, parameter);
	}
	
	/**
     * 删除用户角色信息
     * 
     * @param userRoleId
     * @throws Exception
     */
    public void delete(long userRoleId) throws Exception
    {
        IBOAIMonUserRoleValue value = BOAIMonUserRoleEngine.getBean(userRoleId);
        if(null != value) {
            //			value.delete();
            value.setState("E");
            BOAIMonUserRoleEngine.save(value);
        }
    }

    /**
     * 读取所有用户角色信息
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue[] getAllUserRoleBean() throws Exception
    {
        return this.query(IBOAIMonUserRoleValue.S_State + " ='U'", null);
    }

    /**
     * 根据主键取得用户角色信息
     * 
     * @param userRoleId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue getBeanById(long userRoleId) throws Exception
    {
        return BOAIMonUserRoleEngine.getBean(userRoleId);
    }

    /**
     * 保存或修改用户角色信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserRoleValue value) throws Exception
    {
        if(value.isNew()) {
            value.setUserRoleId(BOAIMonUserRoleEngine.getNewId().longValue());
            value.setState(UserPrivConst.USER_STATE_U);
            value.setCreateDate(nowDate);
        }
        value.setDoneDate(nowDate);
        BOAIMonUserRoleEngine.save(value);
    }

    /**
     * 根据条件取得用户角色信息
     * 
     * @param roleCode
     * @param roleName
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue[] getUserRoleByCond(String roleCode, String roleName) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
    
        if(StringUtils.isNotBlank(roleCode)) {
            condition.append(IBOAIMonUserRoleValue.S_RoldCode).append(" = :roleCode");
            parameter.put("roleCode", roleCode);
        }
        if(StringUtils.isNotBlank(roleName)) {
            if(StringUtils.isNotBlank(roleCode)) {
                condition.append(" and ");
            }
            condition.append(IBOAIMonUserRoleValue.S_RoleName).append(" like :roleName");
            parameter.put("roleName", roleName + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(IBOAIMonUserRoleValue.S_State).append(" = :pstate ");
        parameter.put("pstate", "U");
        return query(condition.toString(), parameter);
    }

    /**
     * 保存或修改用户角色信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserRoleValue[] values) throws Exception
    {
        if(values != null && values.length > 0) {
            for(int i = 0; i < values.length; i++) {
                if(values[i].isNew()) {
                    values[i].setUserRoleId(BOAIMonUserRoleEngine.getNewId().longValue());
                }
            }
        } else {
            throw new Exception("no data to saveOrUpdate...");
        }
        BOAIMonUserRoleEngine.saveBatch(values);
    }
}
