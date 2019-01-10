package com.asiainfo.monitor.exeframe.configmgr.abo.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.comframe.utils.TimeUtil;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostUserBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostUserEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonHostUserDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostUserValue;

public class AIMonHostUserDAOImpl implements IAIMonHostUserDAO
{

    public long getBeanId() throws Exception
    {
        return BOAIMonHostUserEngine.getNewId().longValue();
    }

    public BOAIMonHostUserBean getBeanById(long id) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        String userId=Long.toString(id);
        if(StringUtils.isNotBlank(userId)) {
            condition.append(IBOAIMonHostUserValue.S_HostUserId).append(" = :userId");
            parameter.put("userId", id);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        IBOAIMonHostUserValue[] values = BOAIMonHostUserEngine.getBeans(condition.toString(), parameter);
        if(values.length > 0) {
            return (BOAIMonHostUserBean) values[0];
        }
        return null;
    }

    public void save(BOAIMonHostUserBean value) throws Exception
    {
        if(value.isNew() && value.getHostUserId() <= 0)
            value.setHostUserId(getBeanId());
        BOAIMonHostUserEngine.save(value);
    }

    @Override
    public IBOAIMonHostUserValue qryByCondition(String hostId, String userType)
    {
        return null;
    }

    @Override
    public IBOAIMonHostUserValue[] qryByHostId(String hostId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(hostId)) {
            condition.append(IBOAIMonHostUserValue.S_HostId).append(" = :hostId");
            parameter.put("hostId", hostId);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        return BOAIMonHostUserEngine.getBeans(condition.toString(), parameter);

    }

    @Override
    public IBOAIMonHostUserValue qryHostUserById(String hostUserId) throws Exception
    {
        return BOAIMonHostUserEngine.getBean(Long.parseLong(hostUserId));
    }

    @Override
    public void delete(IBOAIMonHostUserValue value) throws Exception
    {
        if(null != value) {
            value.setState("E");
            BOAIMonHostUserEngine.save(value);
        }
    }

    @Override
    public void save(IBOAIMonHostUserValue value) throws Exception
    {
        if(value.isNew() && value.getHostUserId() <= 0) {
            value.setHostUserId(getBeanId());
            value.setState(CommonConst.STATE_U);
            value.setCreateDate(TimeUtil.getSysTime());
        }
        BOAIMonHostUserEngine.save(value);
    }

    @Override
    public boolean isExistByUserName(String hostId, String userName) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(userName)) {
            condition.append(IBOAIMonHostUserValue.S_HostId).append(" = :hostId");
            parameter.put("hostId", hostId);
        }
        if(StringUtils.isNotBlank(userName)) {
            condition.append(" and ");
            condition.append(IBOAIMonHostUserValue.S_UserName).append(" = :userName");
            parameter.put("userName", userName);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        IBOAIMonHostUserValue[] values = BOAIMonHostUserEngine.getBeans(condition.toString(), parameter);

        if(values.length > 0) {
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public IBOAIMonHostUserValue[] getAllUserInfo() throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        condition.append(" state = 'U' ");
        IBOAIMonHostUserValue[] values = BOAIMonHostUserEngine.getBeans(condition.toString(), parameter);
        return values;
    }

}
