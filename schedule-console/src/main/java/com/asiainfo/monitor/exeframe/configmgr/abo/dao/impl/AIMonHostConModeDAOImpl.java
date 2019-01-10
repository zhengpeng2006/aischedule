package com.asiainfo.monitor.exeframe.configmgr.abo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostConModeBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostConModeEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonHostConModeDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostConModeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostUserValue;

public class AIMonHostConModeDAOImpl implements IAIMonHostConModeDAO
{

    public long getBeanId() throws Exception
    {
        return BOAIMonHostConModeEngine.getNewId().longValue();
    }

    public BOAIMonHostConModeBean getBeanById(long id) throws Exception
    {
        return BOAIMonHostConModeEngine.getBean(id);
    }

    public void save(BOAIMonHostConModeBean value) throws Exception
    {
        if(value.isNew() && value.getRelationId() <= 0)
            value.setRelationId(getBeanId());
        BOAIMonHostConModeEngine.save(value);
    }

    @Override
    public IBOAIMonHostConModeValue qryByConId(String conId) throws Exception
    {
        return null;
    }

    @Override
    public List<String> getConIdList(String hostId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        if(StringUtils.isNotBlank(hostId)) {
            condition.append(IBOAIMonHostConModeValue.S_HostId).append(" = :hostId");
        }
        Map param = new HashMap();
        param.put("hostId", Long.parseLong(hostId));
        IBOAIMonHostConModeValue[] value = BOAIMonHostConModeEngine.getBeans(condition.toString(), param);

        List<String> list = new ArrayList<String>();
        for(int i = 0; i < value.length; i++) {
            String conId = Long.toString(value[i].getConId());
            list.add(conId);
        }

        return list;
    }

    @Override
    public IBOAIMonHostConModeValue[] qryHostConModeByCondition(String hostId, String conId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(hostId)) {
            condition.append(IBOAIMonHostConModeValue.S_HostId).append(" = :hostId");
            parameter.put("hostId", hostId);
        }
        if(StringUtils.isNotBlank(hostId)) {
            condition.append(" and ");
            condition.append(IBOAIMonHostConModeValue.S_ConId).append(" = :conId");
            parameter.put("conId", conId);
        }
        IBOAIMonHostConModeValue[] value = BOAIMonHostConModeEngine.getBeans(condition.toString(), parameter);

        return value;
    }

    @Override
    public void delete(IBOAIMonHostConModeValue[] value) throws Exception
    {
        for(int i = 0; i < value.length; i++) {
            value[i].delete();
            BOAIMonHostConModeEngine.save(value[i]);
        }
    }

    @Override
    public IBOAIMonHostConModeValue[] qryBuHostId(String hostId) throws Exception
    {
        Criteria sql = new Criteria();
        sql.addEqual(IBOAIMonHostUserValue.S_HostId, hostId);

        return BOAIMonHostConModeEngine.getBeans(sql);
    }

}
