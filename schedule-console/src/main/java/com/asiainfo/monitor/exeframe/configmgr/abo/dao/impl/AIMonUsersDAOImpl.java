package com.asiainfo.monitor.exeframe.configmgr.abo.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.comframe.utils.TimeUtil;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonUsersEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonUsersDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonUsersValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonUsersSV;

public class AIMonUsersDAOImpl implements IAIMonUsersDAO
{
    /**
     * 根据用户编码查询用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonUsersValue[] qryUsersInfoByUserCode(String userCode) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();

        if(StringUtils.isNotBlank(userCode)) {
            condition.append(IBOAIMonUsersValue.S_UserCode).append(" like :userCode");
            parameter.put("userCode", "%" + userCode + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        System.out.println(condition.toString());
        return BOAIMonUsersEngine.getBeans(condition.toString(), parameter);
    }

    /**
     * 根据用户标识查询用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonUsersValue qryUserById(String userId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        long userIdLong = Long.parseLong(userId);
        if(StringUtils.isNotBlank(userId)) {
            condition.append(IBOAIMonUsersValue.S_UserId).append(" = :userId");
            parameter.put("userId", userIdLong);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append("state='U'");
        IBOAIMonUsersValue[] values = BOAIMonUsersEngine.getBeans(condition.toString(), parameter);
        if(values.length > 0) {
            return values[0];
        }
        return null;
    }

    /**
     * 删除用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public void delete(IBOAIMonUsersValue value) throws Exception
    {
        if(null != value) {
            value.setState("E");
            BOAIMonUsersEngine.save(value);
        }
    }

    /**
     * 保存用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public void save(IBOAIMonUsersValue value) throws Exception
    {
        if(value.isNew() && value.getUserId() <= 0) {
            value.setUserId(BOAIMonUsersEngine.getNewId().longValue());
            value.setState(CommonConst.STATE_U);
            value.setCreateDate(TimeUtil.getSysTime());
        }
        BOAIMonUsersEngine.save(value);
    }

    @Override
    public IBOAIMonUsersValue[] qryAllUsersInfo() throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        condition.append(" state='U'");
        IBOAIMonUsersValue[] values = BOAIMonUsersEngine.getBeans(condition.toString(), parameter);
        return values;
    }

    @Override
    public boolean userCodeIsExist(String userCode) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(userCode)) {
            condition.append(IBOAIMonUsersValue.S_UserCode).append(" = :userCode");
            parameter.put("userCode", userCode);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append("state='U'");
        IBOAIMonUsersValue[] values = BOAIMonUsersEngine.getBeans(condition.toString(), parameter);
        if(values.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据主机标识查询用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonUsersValue qryUserInfoByHostId(String hostId) throws Exception
    {
        StringBuffer sql = new StringBuffer("");
        if(StringUtils.isNotBlank(hostId)) {
            sql.append(
                    "select t4.* from ai_mon_physic_host t1,ai_mon_node t2,ai_mon_node_user t3,ai_mon_users t4"
                            + " where t1.host_id=t2.host_id and t2.node_id=t3.node_id and t3.user_id=t4.user_id "
                            + "and t1.state='U' and t2.state='U' and t3.state='U' and t4.state='U' and t3.user_type='M' and t1.host_id=").append(
                    Long.parseLong(hostId));
            IBOAIMonUsersValue[] values = BOAIMonUsersEngine.getBeansFromSql(sql.toString(), null);
            if(values.length > 0) {
                return values[0];
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception
    {
        IAIMonUsersSV sv = (IAIMonUsersSV) ServiceFactory.getService(IAIMonUsersSV.class);
        sv.qryUserInfoByHostId("4");
    }
}
