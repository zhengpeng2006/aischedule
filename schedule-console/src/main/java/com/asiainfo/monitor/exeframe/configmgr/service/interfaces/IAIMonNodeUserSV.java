package com.asiainfo.monitor.exeframe.configmgr.service.interfaces;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeUserValue;

public interface IAIMonNodeUserSV
{
    /**
     * 根据节点标识查询节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonNodeUserValue[] qryNodeUserByNodeId(String nodeId) throws Exception;

    /**
     * 根据节点用户标识查询该节点下的用户
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonNodeUserValue qryNodeUserById(String nodeUserId) throws Exception;

    /**
     * 删除节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    public void delete(IBOAIMonNodeUserValue value) throws Exception;

    /**
     * 保存节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    public void save(IBOAIMonNodeUserValue value) throws Exception;
}
