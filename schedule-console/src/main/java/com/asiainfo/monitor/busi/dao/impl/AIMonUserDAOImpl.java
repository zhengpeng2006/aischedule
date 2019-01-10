package com.asiainfo.monitor.busi.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;

public class AIMonUserDAOImpl implements IAIMonUserDAO{

    private Timestamp nowDate = new Timestamp(System.currentTimeMillis());
	/**
	 * 根据条件检索登录用户信息
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserValue[] query(String condition, Map parameter) throws Exception {
		return BOAIMonUserEngine.getBeans(condition, parameter);
	}
	
	/**
	 * 读取所有正常状态用户
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserValue[] getAllUser() throws Exception{
		String condition=IBOAIMonUserValue.S_State+" ='U' ";
		return query(condition,null);
	}
	/**
	 * 根据用户代码读取用户信息
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonUserValue[] getMonUserBeanByCode(String userCode) throws Exception{
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		condition.append(IBOAIMonUserValue.S_UserCode).append(" = :userCode");
		parameter.put("userCode", userCode);
		condition.append(" and "+IBOAIMonUserValue.S_State+" = 'U' ");
		return this.query(condition.toString(),parameter);
	}
	
	/**
     * 根据用户Code检索用户信息
     * 
     * @param userCode
     * @return IBOAIMonUserValue
     * @throws Exception
     */
    public IBOAIMonUserValue getUserInfoByCode(String userCode) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
    
        if (StringUtils.isNotBlank(userCode)) {
            condition.append(IBOAIMonUserValue.S_UserCode).append(" =:userCode");
            parameter.put("userCode", userCode);
        }
        condition.append(" and state = 'U' and lock_flag = 0 and EFFECT_DATE <= SYSDATE and EXPIRE_DATE >= SYSDATE ");
        IBOAIMonUserValue[] users = query(condition.toString(), parameter);
        if (users != null && users.length > 0) {
            return users[0];
        }
        return null;
    }

    /**
     * 删除用户信息
     * 
     * @param userId
     * @throws Exception
     */
    public void delete(long userId) throws Exception
    {
        IBOAIMonUserValue value = BOAIMonUserEngine.getBean(userId);
        if (null != value) {
            //			value.delete();
            value.setState("E");
            value.setDoneDate(nowDate);
            BOAIMonUserEngine.save(value);
        }
    }

    /**
     * 保存或修改用户信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserValue value) throws Exception
    {
        if (value.isNew()) {
            value.setUserId(BOAIMonUserEngine.getNewId().longValue());
            value.setCreateDate(nowDate);
            value.setState(UserPrivConst.USER_STATE_U);
        }
        value.setDoneDate(nowDate);// 设置操作日志
        BOAIMonUserEngine.save(value);
    }

    /**
     * 根据条件检索用户信息
     * 
     * @param userName
     * @param userCode
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getUserInfoByCond(String userCode, String userName) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
    
        if (StringUtils.isNotBlank(userCode)) {
            condition.append(IBOAIMonUserValue.S_UserCode).append(" like :userCode");
            parameter.put("userCode", "%" + userCode + "%");
        }
        if (StringUtils.isNotBlank(userName)) {
            if (StringUtils.isNotBlank(userCode)) {
                condition.append(" and ");
            }
            condition.append(IBOAIMonUserValue.S_UserName).append(" like :userName");
            parameter.put("userName", "%" + userName + "%");
        }
        if (StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        return query(condition.toString(), parameter);
    }

    /**
     * 根据条件检索用户信息
     * 
     * @param userName
     * @param userCode
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getUserInfoByCond(String userCode, String userName, int start, int end) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();

        if(StringUtils.isNotBlank(userCode)) {
            condition.append(IBOAIMonUserValue.S_UserCode).append(" like :userCode");
            parameter.put("userCode", "%" + userCode + "%");
        }
        if(StringUtils.isNotBlank(userName)) {
            if(StringUtils.isNotBlank(userCode)) {
                condition.append(" and ");
            }
            condition.append(IBOAIMonUserValue.S_UserName).append(" like :userName");
            parameter.put("userName", "%" + userName + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        return BOAIMonUserEngine.getBeans(null, condition.toString(), parameter, start, end, false);
    }
    /**
     * 检查用户账号是否存在
     * 
     * @param userCode
     * @throws Exception
     */
    public boolean checkUserCodeExist(String userCode) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
    
        if (StringUtils.isNotBlank(userCode)) {
            condition.append(IBOAIMonUserValue.S_UserCode).append(" =:userCode");
            parameter.put("userCode", userCode);
        }
        condition.append(" and state = 'U' ");
        IBOAIMonUserValue[] value = query(condition.toString(), parameter);
        if (null != value && value.length > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据主键取得登录用户信息
     * 
     * @param userId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue getBeanById(long userId) throws Exception
    {
        return BOAIMonUserEngine.getBean(userId);
    }

    /**
     * 批量保存或修改用户信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserValue[] values) throws Exception
    {
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                if (values[i].isNew()) {
                    values[i].setUserId(BOAIMonUserEngine.getNewId().longValue());
                }
            }
        } else {
            throw new Exception("no data to saveOrUpdate...");
        }
        BOAIMonUserEngine.saveBatch(values);
    }
}
