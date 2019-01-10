package com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonNodeBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;

public interface IAIMonNodeDAO
{

    public long getBeanId() throws Exception;

    public BOAIMonNodeBean getBeanById(long id) throws Exception;

    public void save(BOAIMonNodeBean value) throws Exception;

    /** 
     * 根据条件查询节点
     * @param cond
     * @param para
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue[] queryNode(String cond, Map para) throws Exception;

    /** 
     * 读取�?��正常的节�?
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue[] getAllNode() throws Exception;

    /** 
     * 根据节点名读取节点信�?
     * @param nodeName
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue[] queryNodeByName(String nodeName) throws Exception;

    /** 
     * 根据节点名读取节点配置信�?模糊查询
     * @param nodeName
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue[] getNodeByName(String nodeName) throws Exception;

    /** 
     * 根据节点标识，读取节点信�?
     * @param nodeId
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue getNodeByNodeId(String nodeId) throws Exception;

    /** 
     * 根据组标识，读取节点信息
     * @param nodeId
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue[] getNodeByGroupId(String groupId) throws Exception;

    /** 
     * 批量保存或修改节点设�?
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonNodeValue[] values) throws Exception;

    /** 
     * 保存或修改节点设�?
     * @param value
     * @return 
     * @throws Exception
     */
    public long saveOrUpdate(IBOAIMonNodeValue value) throws Exception;

    /** 
     * 根据组标识和节点名，读取节点信息
     * @param groupId
     * @param nodeName
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue[] queryNodeByNodeName(String groupId, String nodeName) throws Exception;

    /** 
     * 删除节点信息
     * @param nodeId
     * @throws Exception
     */
    public void deleteNode(long nodeId) throws Exception;

    /**
     * 根据主机标识查询节点信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonNodeValue[] qryNodeInfo(String hostId) throws Exception;

    /**
     * 根据节点标识查询节点信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonNodeValue qryNodeInfoByNodeId(String nodeId) throws Exception;

    /**
     * 获取节点信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonNodeValue[] getNodeByNodeList(List list) throws RemoteException, Exception;

    /**
     * 判断节点编码是否存在
     * 
     * @param request
     * @throws Exception
     */
    public boolean isExistNodeByCode(String hostId, String nodeCode) throws Exception;

    /**
     * 判断节点名称是否存在
     * 
     * @param request
     * @throws Exception
     */
    public boolean isExistNodeByName(String hostId, String nodeName) throws Exception;

    /**
     * 根据用户标识和节点编码查询节点信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonNodeValue qryNodeInfoByHostIdAndNodeCode(String hostId, String nodeCode) throws Exception;

    /**
     * 判断该主机下是否存在监控节点
     * 
     * @param request
     * @throws Exception
     */
    public boolean isExistMonitorNode(String hostId) throws Exception;

}
