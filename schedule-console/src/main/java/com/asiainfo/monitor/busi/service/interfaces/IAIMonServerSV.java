package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ailk.common.data.IDataBus;
import com.asiainfo.monitor.busi.cache.impl.Server;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.interapi.ServerControlInfo;
import com.asiainfo.monitor.tools.common.SimpleResult;

public interface IAIMonServerSV
{
    
    /**
     * 根据标识读取应用值对象信息
     * 
     * @return
     * @throws Exception
     */
    public IBOAIMonServerValue getServerBeanById(String id)
        throws RemoteException, Exception;
    
    /**
     * 读取所有正常的Server
     * 
     * @return
     * @throws Exception
     */
    public List getAllServer()
        throws RemoteException, Exception;
    
    /**
     * 读取所有正常的Server
     * 
     * @return
     * @throws Exception
     */
    public List getAllServerNocache()
        throws RemoteException, Exception;
    
    /**
     * 读取所有应用值对象信息
     * 
     * @return
     * @throws Exception
     */
    public IBOAIMonServerValue[] getAllServerBean()
        throws RemoteException, Exception;
    
    /**
     * 根据 标识读取应用
     * 
     * @param serverId
     * @return
     * @throws Exception
     */
    public ServerConfig getServerByServerId(String serverId)
        throws RemoteException, Exception;
    
    /**
     * 根据服务码，读取服务
     * 
     * @param serverId
     * @return AppServerConfig
     * @throws Exception
     */
    public ServerConfig getServerByServerCode(String serverCode)
        throws RemoteException, Exception;
    
    /**
     * 根据应用类型，读取服务
     * 
     * @param serverId
     * @return AppServerConfig
     * @throws Exception
     */
    public List getServerByTempType(String type)
        throws RemoteException, Exception;
    
    /**
     * 根据 标识读取应用
     * 
     * @param serverId
     * @return
     * @throws Exception
     */
    public ServerConfig getServerByServerIdNocache(String serverId)
        throws RemoteException, Exception;
    
    /**
     * 从数据库读取Server
     * 
     * @param serverId
     * @return
     * @throws Exception
     */
    public Server getServerByServerIdFromDb(String serverId)
        throws RemoteException, Exception;
    
    /**
     * 根据应用码从数据库读取Server
     * 
     * @param serverCode
     * @return
     * @throws Exception
     */
    public Server getServerByServerCodeFromDb(String serverCode)
        throws RemoteException, Exception;
    
    /**
     * 将值对象封装
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public Server wrapperServerByBean(IBOAIMonServerValue value)
        throws RemoteException, Exception;
    
    /**
     * 根据主机信息，读取应用信息
     * 
     * @param hostId
     * @return
     * @throws Exception
     */
    public List getAppServerConfigByNodeId(String nodeId)
        throws RemoteException, Exception;
    
    /**
     * 根据主机信息，读取应用信息
     * 
     * @param hostId
     * @return
     * @throws Exception
     */
    public List getAppServerConfigByNodeIdNocache(String nodeId)
        throws RemoteException, Exception;
    
    /**
     * 根据主机信息以及区域信息，读取应用信息
     * 
     * @param hostId
     * @return
     * @throws Exception
     */
    public List getAppServerConfigByNodeIdAndUserId(String nodeId, String userId)
        throws RemoteException, Exception;
    
    /**
     * 根据条件查询应用信息,拼成xml传给Topu展示
     * 
     * @param serverName
     * @return
     * @throws Exception
     */
    public String getServerTopuXmlByName(String serverName, String busiAreaId)
        throws RemoteException, Exception;
    
    /**
     * 根据组标识、主机标识读取所有服务的拓扑
     * 
     * @param groupId
     * @param hostId
     * @return
     * @throws Exception
     */
    public String getServerTopuXmlByNodeIdAndUserId(String nodeId, String userId)
        throws RemoteException, Exception;
    
    /**
     * 根据名称，全匹配标志读取符合条件的应用
     * 
     * @param serverName
     * @param matchWhole
     * @return
     * @throws Exception
     */
    public List getAppServerConfigByName(String serverName, String busiAreaId, boolean matchWhole)
        throws RemoteException, Exception;
    
    /**
     * 获取应用信息XML作为树节点
     * 
     * @param hostId
     * @return List
     * @throws Exception
     */
    public List getServerTreeXmlByNodeId(String nodeId)
        throws RemoteException, Exception;
    
    /**
     * 获取应用信息XML作为树节点
     * 
     * @param hostId
     * @return List
     * @throws Exception
     */
    public List getServerTreeXmlByNodeIdNocache(String nodeId)
        throws RemoteException, Exception;
    
    /**
     * 检查统一版本
     * 
     * @param ids
     * @return
     * @throws Exception
     */
    public SimpleResult checkUniteVersion(Object[] ids)
        throws RemoteException, Exception;
    
    /**
     * 读取，保存，检查版本信息
     * 
     * @param ids
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public SimpleResult checkVersion(Object[] ids)
        throws RemoteException, Exception;
    
    /**
     * 批量保存或修改应用服务器设置
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonServerValue[] values)
        throws RemoteException, Exception;
    
    /**
     * 保存或修改应用服务器信息
     * 
     * @param value
     * @return server_id
     * @throws Exception
     */
    public long saveOrUpdate(IBOAIMonServerValue value)
        throws RemoteException, Exception;
    
    /**
     * 保存或修改应用服务器信息,涉及域的更改,JMX状态的更改
     * 
     * @param value
     * @param domainId
     * @param jmxState
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonServerValue value, String domainId, String jmxState)
        throws RemoteException, Exception;
    
    /**
     * 删除应用服务器信息
     * 
     * @param serverId
     * @throws Exception
     */
    public void deleteServer(long serverId)
        throws RemoteException, Exception;
    
    /**
     * 根据组名、主机名、应用名获取应用信息
     * 
     * @param groupName
     * @param hostName
     * @param serverName
     * @return
     * @throws Exception
     */
    public List getServerInfo(String groupName, String nodeName, String serverName)
        throws RemoteException, Exception;
    
    /**
     * 根据组名、主机名、应用名获取应用信息
     * 
     * @param groupName
     * @param hostName
     * @param serverName
     * @return
     * @throws Exception
     */
    public List getServerInfoNocache(String groupName, String nodeName, String serverName)
        throws RemoteException, Exception;
    
    /**
     * 检查应用状态
     * 
     * @param serverInfo
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public ServerControlInfo[] getServerStatus(ServerControlInfo[] serverInfo)
        throws RemoteException, Exception;
    
    /**
     * 取得所有非App类型的应用ID
     * 
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public List getServerIdsByType(String tempType)
        throws RemoteException, Exception;
    
    public IBOAIMonServerValue[] qryServerInfo(String nodeId)
        throws RemoteException, Exception;
    
    public IDataBus getSelectList(String constCode)
        throws RemoteException, Exception;
    
    /**
     * 根据节点标识查询节点下所有应用信息，采用node in(1,2)查询条件
     * 
     * @param nodeIdStr
     * @return
     * @throws Exception
     */
    public IBOAIMonServerValue[] qryByCondition(List<String> nodeIdList)
        throws Exception;
    
    public IBOAIMonServerValue qryServerByServerId(String serverId)
        throws RemoteException, Exception;
    
    public boolean isExistServerByCode(String serverCode)
        throws Exception;
    
    public IBOAIMonServerValue qryServerByServerCode(String serverCode)
        throws Exception;
    
    /**
     * 通过应用编码查询主机标识
     * 
     * @param request
     * @throws Exception
     */
    public String qryHostIdByServerCode(String serverCode)
        throws Exception;
    
    public IBOAIMonServerValue[] qryServerByNodeId(String nodeId)
        throws Exception;
    
    /**
     * 返回所有应用和对应的主机ID
     * 
     * @return
     */
    public Map<String, Long> getServerHosts()
        throws Exception;
    
    /**
     * 返回所有主机和对应的应用列表
     * 
     * @return
     * @throws Exception
     */
    public JSONObject getServerHostsMap()
        throws Exception;
    
    List<IBOAIMonServerValue> qryServerByHostId(String hostId)
        throws Exception;
}
