package com.asiainfo.monitor.exeframe.configmgr.abo.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.comframe.utils.TimeUtil;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonNodeUserEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonNodeUserDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeUserValue;

public class AIMonNodeUserDAOImpl implements IAIMonNodeUserDAO
{
    /**
     * 根据节点标识查询节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonNodeUserValue[] qryNodeUserByNodeId(String nodeId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(nodeId)) {
            condition.append(IBOAIMonNodeUserValue.S_NodeId).append(" = :nodeId");
            parameter.put("nodeId", Long.parseLong(nodeId));
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        IBOAIMonNodeUserValue[] values = BOAIMonNodeUserEngine.getBeans(condition.toString(), parameter);
        return values;
    }

    /**
     * 根据节点用户标识查询该节点下的用户
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonNodeUserValue qryNodeUserById(String nodeUserId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(nodeUserId)) {
            condition.append(IBOAIMonNodeUserValue.S_NodeUserId).append(" = :nodeUserId");
            parameter.put("nodeUserId", Long.parseLong(nodeUserId));
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        IBOAIMonNodeUserValue[] values = BOAIMonNodeUserEngine.getBeans(condition.toString(), parameter);
        if(values.length > 0) {
            return values[0];
        }
        return null;
    }

    /**
     * 删除节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public void delete(IBOAIMonNodeUserValue value) throws Exception
    {
        if(null != value) {
            value.setState("E");
            BOAIMonNodeUserEngine.save(value);
        }
    }

    /**
     * 保存节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public void save(IBOAIMonNodeUserValue value) throws Exception
    {
        if(value.isNew() && value.getNodeUserId() <= 0) {
            value.setNodeUserId(BOAIMonNodeUserEngine.getNewId().longValue());
            value.setState(CommonConst.STATE_U);
            value.setCreateDate(TimeUtil.getSysTime());
        }
        BOAIMonNodeUserEngine.save(value);
    }

}
