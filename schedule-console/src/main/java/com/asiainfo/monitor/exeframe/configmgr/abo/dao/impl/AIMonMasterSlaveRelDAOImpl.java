package com.asiainfo.monitor.exeframe.configmgr.abo.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonMasterSlaveRelBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonMasterSlaveRelEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonMasterSlaveRelDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonMasterSlaveRelValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;

public class AIMonMasterSlaveRelDAOImpl implements IAIMonMasterSlaveRelDAO
{
    private Timestamp nowDate = new Timestamp(System.currentTimeMillis());
    public long getBeanId() throws Exception
    {
        return BOAIMonMasterSlaveRelEngine.getNewId().longValue();
    }

    public BOAIMonMasterSlaveRelBean getBeanById(long id) throws Exception
    {
        return BOAIMonMasterSlaveRelEngine.getBean(id);
    }

    public void save(BOAIMonMasterSlaveRelBean value) throws Exception
    {
        if(value.isNew() && value.getRelationId() <= 0) {
            value.setRelationId(getBeanId());
            value.setCreateDate(nowDate);
            value.setState(UserPrivConst.USER_STATE_U);
        }

        BOAIMonMasterSlaveRelEngine.save(value);
    }

    @Override
    public List<String> getSlaveHostId(String masterHostId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(masterHostId)) {
            condition.append(IBOAIMonMasterSlaveRelValue.S_MasterHostId).append(" = :masterHostId");
            parameter.put("masterHostId", masterHostId);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        IBOAIMonMasterSlaveRelValue[] value = BOAIMonMasterSlaveRelEngine.getBeans(condition.toString(), parameter);
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < value.length; i++) {
            String conId = Long.toString(value[i].getSlaveHostId());
            list.add(conId);
        }
        return list;
    }

    @Override
    public IBOAIMonMasterSlaveRelValue[] qryMasterSlaveByCondition(String masterHostId, String slaveHostId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(masterHostId)) {
            condition.append(IBOAIMonMasterSlaveRelValue.S_MasterHostId).append(" = :masterHostId");
            parameter.put("masterHostId", masterHostId);
        }
        if(StringUtils.isNotBlank(slaveHostId)) {
            condition.append(" and ");
            condition.append(IBOAIMonMasterSlaveRelValue.S_SlaveHostId).append(" = :slaveHostId");
            parameter.put("slaveHostId", slaveHostId);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        IBOAIMonMasterSlaveRelValue[] value = BOAIMonMasterSlaveRelEngine.getBeans(condition.toString(), parameter);
        return value;
    }

    @Override
    public void delete(IBOAIMonMasterSlaveRelValue[] value) throws Exception
    {
        for(int i = 0; i < value.length; i++) {
            value[i].delete();
            BOAIMonMasterSlaveRelEngine.save(value[i]);
        }
    }

    /**
     * 根据主机id查询主备机关系
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonMasterSlaveRelValue[] qryMasterSlaveRelByMasterHostId(String masterHostId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(masterHostId)) {
            condition.append(IBOAIMonMasterSlaveRelValue.S_MasterHostId).append(" = :masterHostId");
            parameter.put("masterHostId", masterHostId);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        IBOAIMonMasterSlaveRelValue[] values = BOAIMonMasterSlaveRelEngine.getBeans(condition.toString(), parameter);

        return values;
    }

    @Override
    public IBOAIMonMasterSlaveRelValue[] qryAllMasterSlaveRelInfo() throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        condition.append(" state = 'U' ");
        IBOAIMonMasterSlaveRelValue[] values = BOAIMonMasterSlaveRelEngine.getBeans(condition.toString(), parameter);
        return values;
    }

}
