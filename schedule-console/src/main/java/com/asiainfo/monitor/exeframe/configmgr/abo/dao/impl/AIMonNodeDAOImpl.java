package com.asiainfo.monitor.exeframe.configmgr.abo.dao.impl;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonNodeBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonNodeEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonNodeDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;

public class AIMonNodeDAOImpl implements IAIMonNodeDAO
{

    public long getBeanId() throws Exception
    {
        return BOAIMonNodeEngine.getNewId().longValue();
    }

    public BOAIMonNodeBean getBeanById(long id) throws Exception
    {
        return BOAIMonNodeEngine.getBean(id);
    }

    public void save(BOAIMonNodeBean value) throws Exception
    {
        if(value.isNew() && value.getNodeId() <= 0)
            value.setNodeId(getBeanId());
        BOAIMonNodeEngine.save(value);
    }

    private Timestamp nowDate = new Timestamp(System.currentTimeMillis());

    /** 
     * 根据条件查询节点
     * @param cond
     * @param para
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue[] queryNode(String cond, Map para) throws Exception
    {
        return BOAIMonNodeEngine.getBeans(cond, para);
    }

    /** 
     * 读取正常的节点
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue[] getAllNode() throws Exception
    {
        return this.queryNode(IBOAIMonNodeValue.S_State + "='U' order by " + IBOAIMonNodeValue.S_NodeId, null);
    }

    /** 
     * 根据节点名读取节点配置信息
     * @param hostName
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue[] queryNodeByName(String nodeName) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(nodeName)) {
            condition.append(IBOAIMonNodeValue.S_NodeName).append(" = :serName");
            parameter.put("serName", nodeName);
        }
        return queryNode(condition.toString(), parameter);
    }

    /** 
     * 根据节点名读取节点配置信息模糊查询
     * @param hostName
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue[] getNodeByName(String nodeName) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(nodeName)) {
            condition.append(IBOAIMonNodeValue.S_NodeName).append(" like :nodeName");
            parameter.put("nodeName", nodeName + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(IBOAIMonNodeValue.S_State).append(" = :pstate ");
        parameter.put("pstate", "U");
        return queryNode(condition.toString(), parameter);
    }

    /** 
     * 根据节点标识，读取节点信息
     * @param serverId
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue getNodeByNodeId(String nodeId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        long nodeIdLong = Long.parseLong(nodeId);
        if(StringUtils.isNotBlank(nodeId)) {
            condition.append(IBOAIMonNodeValue.S_NodeId).append(" = :nodeId");
            parameter.put("nodeId", nodeIdLong);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append("state='U'");
        IBOAIMonNodeValue[] values = queryNode(condition.toString(), parameter);
        if(values.length > 0) {
            return values[0];
        }
        return null;
    }

    /** 
     * 根据组标识，读取节点信息
     * szh  
     * @param groupId
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue[] getNodeByGroupId(String groupId) throws Exception
    {
        IAIMonGroupSV gSv = (IAIMonGroupSV) ServiceFactory.getService(IAIMonGroupSV.class);
        List<IBOAIMonPhysicHostValue> hostList = gSv.qryGroupHostInfo(groupId);
        List listHostId = new ArrayList();
        for(int i = 0; i < hostList.size(); i++) {
            IBOAIMonPhysicHostValue ph = hostList.get(i);
            listHostId.add(ph.getHostId());
        }
        Criteria sql = new Criteria();
        if(listHostId.size() > 0)
            sql.addIn(IBOAIMonNodeValue.S_HostId, listHostId);
        IBOAIMonNodeValue[] values = BOAIMonNodeEngine.getBeans(sql);
        return values;
    }

    /** 
     * 批量保存或修改节点
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonNodeValue[] values) throws Exception
    {
        if(values != null && values.length > 0) {
            for(int i = 0; i < values.length; i++) {
                if(values[i].isNew()) {
                    values[i].setNodeId(BOAIMonNodeEngine.getNewId().longValue());
                }
            }
        }
        else {
            throw new Exception("no data to saveOrUpdate...");
        }
        BOAIMonNodeEngine.saveBatch(values);
    }

    /** 
     * 保存或修改节点
     * @param value
     * @return 
     * @throws Exception
     */
    public long saveOrUpdate(IBOAIMonNodeValue value) throws Exception
    {
        if(value.isNew()) {
            value.setNodeId(BOAIMonNodeEngine.getNewId().longValue());
            value.setCreateDate(nowDate);
            value.setState(UserPrivConst.USER_STATE_U);
        }
        BOAIMonNodeEngine.save(value);
        return value.getNodeId();
    }

    /** 
     * 根据组标识和节点名，读取节点信息
     * @param groupId
     * @param serName
     * @return
     * @throws Exception
     */
    public IBOAIMonNodeValue[] queryNodeByNodeName(String groupId, String nodeName) throws Exception
    {
        //标注：节点表里没有groupId了，暂时没根据groupId差节点，如果需要自行修改
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(nodeName)) {
            if(StringUtils.isNotBlank(condition.toString())) {
                condition.append(" and ");
            }
            condition.append(IBOAIMonNodeValue.S_NodeName + " = :serName");
            parameter.put("serName", nodeName);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        IBOAIMonNodeValue[] hostValues = queryNode(condition.toString(), parameter);
        return hostValues;
    }

    /** 
     * 删除节点信息
     * @param serverId
     * @throws Exception
     */
    public void deleteNode(long nodeId) throws Exception
    {
        IBOAIMonNodeValue value = BOAIMonNodeEngine.getBean(nodeId);
        if(null != value) {
            value.setState("E");
            BOAIMonNodeEngine.save(value);
        }
    }

    /** 
     * 通过hostId获取节点信息
     * @param hostId
     * @throws Exception
     */
    @Override
    public IBOAIMonNodeValue[] qryNodeInfo(String hostId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        long host = Long.parseLong(hostId);
        if(StringUtils.isNotBlank(hostId)) {
            condition.append(IBOAIMonNodeValue.S_HostId + "= :hostId");
            parameter.put("hostId", host);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' order by ").append(IBOAIMonNodeValue.S_NodeId);
        IBOAIMonNodeValue[] hostValues = queryNode(condition.toString(), parameter);
        return hostValues;
    }

    /**
     * 
    * @Function: AIMonNodeDAOImpl.java
    * @Description: 通过节点标识查询节点信息
    *
    * @param:参数描述nodeId
    * @return：返回结果描述
    * @throws：异常描述
    * @author: szh
     */
    @Override
    public IBOAIMonNodeValue qryNodeInfoByNodeId(String nodeId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        long nodeIdLong = Long.parseLong(nodeId);
        if(StringUtils.isNotBlank(nodeId)) {
            condition.append(IBOAIMonNodeValue.S_NodeId).append(" = :nodeId");
            parameter.put("nodeId", nodeIdLong);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append("state='U'");
        IAIMonNodeDAO nodeDAO = (IAIMonNodeDAO) ServiceFactory.getService(IAIMonNodeDAO.class);
        IBOAIMonNodeValue[] values = nodeDAO.queryNode(condition.toString(), parameter);
        if(values.length > 0) {
            return values[0];
        }
        return null;
    }

    @Override
    public IBOAIMonNodeValue[] getNodeByNodeList(List nodeIdList) throws RemoteException, Exception
    {
        Criteria sql = new Criteria();
        if(nodeIdList.size() > 0)
            sql.addIn(IBOAIMonNodeValue.S_NodeId, nodeIdList);
        IBOAIMonNodeValue[] nodeValues = BOAIMonNodeEngine.getBeans(sql);

        List<IBOAIMonNodeValue> listNode = new ArrayList<IBOAIMonNodeValue>();
        for(int i = 0; i < nodeValues.length; i++) {
            listNode.add(nodeValues[i]);
        }
        for(int i = 0; i < listNode.size(); i++) {
            if("E".equals(listNode.get(i).getState())) {
                listNode.remove(i);
            }
        }
        IBOAIMonNodeValue[] node = new IBOAIMonNodeValue[listNode.size()];
        for(int i = 0; i < listNode.size(); i++) {
            node[i] = listNode.get(i);
        }
        return node;
    }

    @Override
    public boolean isExistNodeByCode(String hostId, String nodeCode) throws Exception
    {
        IAIMonNodeSV nodeSv = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
        IBOAIMonNodeValue[] values = nodeSv.qryNodeInfo(hostId);
        if(values.length > 0) {
            for(IBOAIMonNodeValue value : values) {
                if(nodeCode.equals(value.getNodeCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isExistNodeByName(String hostId, String nodeName) throws Exception
    {
        IAIMonNodeSV nodeSv = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
        IBOAIMonNodeValue[] values = nodeSv.qryNodeInfo(hostId);
        if(values.length > 0) {
            for(IBOAIMonNodeValue value : values) {
                if(nodeName.equals(value.getNodeCode())) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * 根据主机标识和节点标识查询节点信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public IBOAIMonNodeValue qryNodeInfoByHostIdAndNodeCode(String hostId, String nodeCode) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        long host = Long.parseLong(hostId);
        if(StringUtils.isNotBlank(hostId)) {
            condition.append(IBOAIMonNodeValue.S_HostId + "= :hostId");
            parameter.put("hostId", host);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        if(StringUtils.isNotBlank(nodeCode)) {
            condition.append(IBOAIMonNodeValue.S_NodeCode + "= :nodeCode");
            parameter.put("nodeCode", nodeCode);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' order by ").append(IBOAIMonNodeValue.S_NodeId);
        IBOAIMonNodeValue[] hostValues = queryNode(condition.toString(), parameter);
        if(hostValues.length > 0) {
            return hostValues[0];
        }
        return null;
    }

    /**
     * 判断该主机下是否已存在监控节点
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public boolean isExistMonitorNode(String hostId) throws Exception
    {
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IBOAIMonPhysicHostValue hostValue = hostSv.getPhysicHostById(hostId);
        if(null != hostValue) {
            String ip = hostValue.getHostIp();
            StringBuilder sql = new StringBuilder("");
            sql.append("select n.* from ai_mon_physic_host h,ai_mon_node n where h.host_id=n.host_id and n.is_monitor_node='Y' and ")
                    .append("h.host_ip='")
                    .append(ip).append("' and h.state='U' and n.state='U'");
            IBOAIMonNodeValue[] values = BOAIMonNodeEngine.getBeansFromSql(sql.toString(), null);
            if(values.length > 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception
    {
        IAIMonNodeSV sv = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
        boolean b = sv.isExistMonitorNode("4");
        System.out.println(b);
    }
}

