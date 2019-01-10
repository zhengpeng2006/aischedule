package com.asiainfo.monitor.exeframe.configmgr.abo.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonConModeBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonConModeEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonConModeDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonConModeValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;

public class AIMonConModeDAOImpl implements IAIMonConModeDAO
{
    private Timestamp nowDate = new Timestamp(System.currentTimeMillis());
    public long getBeanId() throws Exception
    {
        return BOAIMonConModeEngine.getNewId().longValue();
    }

    public BOAIMonConModeBean getBeanById(long id) throws Exception
    {
        return BOAIMonConModeEngine.getBean(id);
    }

    public void save(BOAIMonConModeBean value) throws Exception
    {
        if(value.isNew() && value.getConId() <= 0)
            value.setConId(getBeanId());
        BOAIMonConModeEngine.save(value);
    }

    @Override
    public IBOAIMonConModeValue qryConModeInfoByConId(String conId) throws Exception
    {
        long id = Long.parseLong(conId);
        return BOAIMonConModeEngine.getBean(id);
    }

    @Override
    public long saveOrUpdate(IBOAIMonConModeValue value) throws Exception
    {
        if(value.isNew()) {
            value.setConId(BOAIMonConModeEngine.getNewId().longValue());
            value.setCreateDate(nowDate);
            value.setState(UserPrivConst.USER_STATE_U);
        }
        long conId = value.getConId();
        BOAIMonConModeEngine.save(value);
        return conId;
    }

    @Override
    public IBOAIMonConModeValue[] getAllConMode() throws Exception
    {
        StringBuilder sb = new StringBuilder("");
        sb.append(IBOAIMonConModeValue.S_State + " ='U'");
        return BOAIMonConModeEngine.getBeans(sb.toString(), null);
    }

    @Override
    public IBOAIMonConModeValue qryConInfoById(String conId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        long conIdLong = Long.parseLong(conId);
        if(StringUtils.isNotBlank(conId)) {
            condition.append(IBOAIMonConModeValue.S_ConId).append(" = :conId");
            parameter.put("conId", conIdLong);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append("state='U'");
        IBOAIMonConModeValue[] values = BOAIMonConModeEngine.getBeans(condition.toString(), parameter);
        if(values.length > 0) {
            return values[0];
        }
        return null;
    }
}
