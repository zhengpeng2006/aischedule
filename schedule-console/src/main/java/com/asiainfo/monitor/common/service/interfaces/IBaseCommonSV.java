package com.asiainfo.monitor.common.service.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.asiainfo.monitor.busi.cache.impl.PhysicHost;
import com.asiainfo.monitor.common.combin.CmbHostBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;

public interface IBaseCommonSV
{   

    /**
     * 根据组标识查询改组下主机
     * 
     * @param groupId
     * @throws Exception
     */
    public IBOAIMonPhysicHostValue[] qryHostInfosByGroupId(String groupId) throws Exception;
    /**
     * 根据组标识查询改组下主机
     * 
     * @param groupId
     * @throws Exception
     */
    public List<PhysicHost> qryGroupHostInfo(String groupId) throws Exception;

    /**
     * 根据主机标识获取节点信息
     * 
     * @param hostId
     * @return
     * @throws Exception
     */
    public Map<String, List<IBOAIMonNodeValue>> fetchHostNodeMap() throws Exception;

    /**
     * 根据主机标识，查询主机所有联合信息，用户，连接方式，备机等。
     * @param hostId
     * @return
     * @throws Exception
     */
    public CmbHostBean qryCmbHostInfo(String hostId) throws Exception;

    /**
     * 根据应用标识查询主机信息
     * @param serverId
     * @return
     * @throws Exception
     */
    public CmbHostBean qryCmbHostInfoByServerId(String serverId) throws Exception;

    /**
     * 通过应用编码查询节点信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public IBOAIMonNodeValue qryNodeInfoByServerCode(String serverCode) throws Exception;

    /**
     * 通过应用编码查询应用信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public IBOAIMonServerValue qryServerByServerCode(String serverCode) throws Exception;

    /**
     * 根据主机标识查询，主机下所有应用
     * @param hostId
     * @return
     * @throws Exception
     */
    public List<IBOAIMonServerValue> qryServerList(String hostId) throws Exception;

    /**
     * 根据节点标识查询主机信息
     * @param hostId
     * @return
     * @throws Exception
     */
    public CmbHostBean qryCmbHostInfoByNodeId(String nodeId) throws Exception;
    
    
    /**
     * 根据主机标识查询所属分组
     * @param hostId
     * @return
     * @throws Exception
     */
    public BOAIMonGroupBean queryGroupByHostId(String hostId) throws Exception;

    /**
     * 应用编码获取主机信息
     * 
     * @param request
     * @throws Exception
     */
    public Map<String, IBOAIMonPhysicHostValue> qryHostInfoByAppCode(List<String> list) throws Exception;

    /**
     * 查询所有主机信息的缓存
     * 
     * @param request
     * @throws Exception
     */
    public Map<String, PhysicHost> qryHostInfo() throws Exception;

    /**
     * 根据节点标识查询节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    public Map<String, String> qryNodeUserInfoByNodeId(String nodeId) throws Exception;
    
    /**
     * 根据应用标识查询节点发布用户信息
     * 
     * @param request
     * @throws Exception
     */
    public Map<String, String> qryNodeUserInfoByServerId(String serverId) throws Exception;
    
    /**
     * 根据主机标识查询监控用户信息
     * 
     * @param request
     * @throws Exception
     */
    public Map<String, String> qryMonitorNodeUserInfoByHostIp(String hostIp) throws Exception;

    /**
     * 根据应用标识查询监控用户
     * 
     * @param request
     * @throws Exception
     */
    public Map<String, String> qryMonitorNodeUserInfoByServerId(String serverId) throws Exception;

    /**
     * 根据条件查询主机信息
     * @param groupCode
     * @param hostId
     * @param serverCode
     * @return
     */
    public Map<String, IBOAIMonPhysicHostValue> qryHostInfoByCondition(String groupCode, String hostId, String serverCode) throws Exception;

    /**
     * 根据主机标识查询监控节点上的用户
     * 
     * @param request
     * @throws Exception
     */
    public Map<String, String> qryMonitorNodeUserInfoByHostId(String hostId) throws Exception;

    /**
    * 根据主机标识查询该主机上监控节点的部署策略路径
    * 
    * @param request
    * @throws Exception
    */
    public String qryMonNodeDeployPathByHostId(String hostId) throws Exception;

    /**
     * 根据主机IP查询监控节点的路径
     * 
     * @param request
     * @throws Exception
     */
    public String qryMonNodeDeployPathByIp(String hostIp) throws Exception;
    
    /**
     * 需要导出的主机信息
     * @return
     * @throws Exception
     */
    public JSONArray exportHostInfo() throws Exception;
    
    /**
     * 需要导出的主备机信息
     * @return
     */
    public HashMap<String,String> exportMasterSlaveInfo() throws Exception;
}
