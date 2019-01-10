package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.util.criteria.Criteria;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.monitor.busi.cache.AIMonServerCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonServerCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.AIMonStaticDataCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.Server;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;
import com.asiainfo.monitor.busi.common.impl.GroupNode;
import com.asiainfo.monitor.busi.common.impl.LogicNode;
import com.asiainfo.monitor.busi.common.impl.ServerComparator;
import com.asiainfo.monitor.busi.common.impl.ServerNode;
import com.asiainfo.monitor.busi.common.impl.TopuXmlStrategy;
import com.asiainfo.monitor.busi.common.impl.TreeXmlNodeVisitor;
import com.asiainfo.monitor.busi.common.impl.TreeXmlStrategy;
import com.asiainfo.monitor.busi.common.interfaces.INodeVisitor;
import com.asiainfo.monitor.busi.common.interfaces.IObjectStrategy;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonDomainSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonSetSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowServerStatusSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonSetBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainRelatValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSetValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonServerBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonServerEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonServerDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.interapi.ServerControlInfo;
import com.asiainfo.monitor.interapi.api.control.ServerControlApi;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.common.TreeNodeInfo;
import com.asiainfo.monitor.tools.common.interfaces.INodeInfo;
import com.asiainfo.monitor.tools.common.interfaces.IVisitable;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonServerSVImpl implements IAIMonServerSV
{
    
    private static transient Log log = LogFactory.getLog(AIMonServerSVImpl.class);
    
    /**
     * 根据标识读取应用值对象信息
     * 
     * @return
     * @throws Exception
     */
    @Override
    public IBOAIMonServerValue getServerBeanById(String id)
        throws RemoteException, Exception
    {
        IAIMonServerDAO appServerDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        return appServerDAO.getServerByServerId(id);
    }
    
    /**
     * 根据服务标识，读取服务
     * 
     * @param serverId
     * @return AppServerConfig
     * @throws Exception
     */
    @Override
    public ServerConfig getServerByServerId(String serverId)
        throws RemoteException, Exception
    {
        Server result = (Server)MonCacheManager.get(AIMonServerCacheImpl.class, serverId);
        if (result == null)
        {
            result = this.getServerByServerIdFromDb(serverId);
            if (!MonCacheManager.getCacheReadOnlySet() && result != null)
            {
                MonCacheManager.put(AIMonServerCacheImpl.class, result.getApp_Id(), result);
            }
        }
        return new ServerConfig(result);
    }
    
    /**
     * 根据服务码，读取服务
     * 
     * @param serverId
     * @return AppServerConfig
     * @throws Exception
     */
    @Override
    public ServerConfig getServerByServerCode(String serverCode)
        throws RemoteException, Exception
    {
        Server result = null;
        HashMap map = MonCacheManager.getAll(AIMonServerCacheImpl.class);
        if (map != null && map.size() > 0)
        {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext())
            {
                Entry entry = (Entry)it.next();
                result = (Server)entry.getValue();
                if (result != null && result.getApp_Code().equalsIgnoreCase(serverCode))
                {
                    break;
                }
                result = null;
            }
        }
        if (result == null)
        {
            result = this.getServerByServerCodeFromDb(serverCode);
            if (!MonCacheManager.getCacheReadOnlySet() && result != null)
            {
                MonCacheManager.put(AIMonServerCacheImpl.class, result.getApp_Id(), result);
            }
        }
        return new ServerConfig(result);
    }
    
    /**
     * 根据应用类型，读取服务
     * 
     * @param type
     * @return List
     * @throws Exception
     */
    @Override
    public List getServerByTempType(String type)
        throws RemoteException, Exception
    {
        List list = null;
        HashMap map = MonCacheManager.getAll(AIMonServerCacheImpl.class);
        if (map != null && map.size() > 0)
        {
            list = new ArrayList();
            Iterator it = map.entrySet().iterator();
            Server item;
            while (it.hasNext())
            {
                Entry entry = (Entry)it.next();
                item = (Server)entry.getValue();
                if (item != null && item.getTemp_Type().equalsIgnoreCase(type))
                {
                    list.add(new ServerConfig(item));
                }
            }
        }
        if (list == null || list.size() <= 0)
        {
            list = new ArrayList();
            IAIMonServerDAO serverDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
            IBOAIMonServerValue[] values = serverDAO.getServerByTempType(type);
            if (values != null && values.length > 0)
            {
                Server item = null;
                for (int i = 0; i < values.length; i++)
                {
                    if (values[i] != null)
                        item = this.wrapperServerByBean(values[i]);
                    list.add(new ServerConfig(item));
                    if (!MonCacheManager.getCacheReadOnlySet() && item != null)
                    {
                        MonCacheManager.put(AIMonServerCacheImpl.class, item.getApp_Id(), item);
                    }
                }
            }
        }
        return list;
    }
    
    /**
     * 根据标识从数据库中读取应用
     * 
     * @param serverId
     * @return
     * @throws Exception
     */
    @Override
    public ServerConfig getServerByServerIdNocache(String serverId)
        throws RemoteException, Exception
    {
        Server result = this.getServerByServerIdFromDb(serverId);
        return new ServerConfig(result);
    }
    
    /**
     * 从数据库读取Server
     * 
     * @param serverId
     * @return
     * @throws Exception
     */
    @Override
    public Server getServerByServerIdFromDb(String serverId)
        throws RemoteException, Exception
    {
        Server result = null;
        IAIMonServerDAO serverDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        IBOAIMonServerValue serverValue = serverDAO.getServerByServerId(serverId);
        result = this.wrapperServerByBean(serverValue);
        return result;
    }
    
    /**
     * 根据应用码从数据库读取Server
     * 
     * @param serverCode
     * @return
     * @throws Exception
     */
    @Override
    public Server getServerByServerCodeFromDb(String serverCode)
        throws RemoteException, Exception
    {
        Server result = null;
        IAIMonServerDAO serverDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        IBOAIMonServerValue serverValue = serverDAO.getServerByServerCode(serverCode);
        result = this.wrapperServerByBean(serverValue);
        return result;
    }
    
    /**
     * 将值对象封装
     * 
     * @param value
     * @return
     * @throws Exception
     */
    @Override
    public Server wrapperServerByBean(IBOAIMonServerValue value)
        throws RemoteException, Exception
    {
        Server result = null;
        if (value == null || StringUtils.isBlank(value.getState()) || value.getState().equals("E"))
        {
            return null;
        }
        result = new Server();
        result.setApp_Id(value.getServerId() + "");
        result.setApp_Code(value.getServerCode());
        result.setApp_Name(value.getServerName());
        result.setApp_Ip(value.getServerIp());
        result.setApp_Port(String.valueOf(value.getServerPort()));
        result.setCheck_Url(value.getCheckUrl());
        result.setNode_Id(value.getNodeId() + "");
        result.setTemp_Type(value.getTempType());
        result.setLocator(value.getLocator());
        result.setMidware_Type(value.getMidwareType());
        result.setLocator_Type(value.getLocatorType());
        result.setStart_CmdId(value.getStartCmdId() + "");
        result.setStop_CmdId(value.getStopCmdId() + "");
        result.setState(value.getState());
        result.setSversion(value.getSversion() + "");
        result.setRemark(value.getRemark());
        
        IAIMonDomainSV domainSV = (IAIMonDomainSV)ServiceFactory.getService(IAIMonDomainSV.class);
        IBOAIMonDomainRelatValue domainRelatValue = domainSV.getDomainRelatByTypeAndRelatId("3", result.getApp_Id());
        if (domainRelatValue != null)
        {
            result.setDomain(domainRelatValue.getDomainId() + "");
            IBOAIMonDomainValue domain = domainSV.getDomainBeanById(domainRelatValue.getDomainId());
            if (domain != null)
            {
                result.setDomain_Code(domain.getDomainCode());
            }
        }
        IAIMonSetSV setSV = (IAIMonSetSV)ServiceFactory.getService(IAIMonSetSV.class);
        IBOAIMonSetValue[] sets = setSV.getMonSetBeanByAppIdAndVCode(result.getApp_Id(), "JMX_STATE");
        if (sets != null && sets.length > 0)
        {
            result.setJmxState(sets[0].getSetValue());
        }
        result.setCacheListener(new AIMonServerCheckListener());
        return result;
    }
    
    /**
     * 读取所有正常的Server
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List getAllServer()
        throws RemoteException, Exception
    {
        List result = null;
        HashMap serverMap = MonCacheManager.getAll(AIMonServerCacheImpl.class);
        if (serverMap != null && serverMap.size() > 0)
        {
            result = new ArrayList();
            Iterator it = serverMap.entrySet().iterator();
            while (it != null && it.hasNext())
            {
                Entry item = (Entry)it.next();
                if (((Server)item.getValue()).getEnable())
                    result.add(new ServerConfig((Server)item.getValue()));
            }
        }
        return result;
    }
    
    /**
     * 从数据库中读取所有应用信息
     */
    @Override
    public List getAllServerNocache()
        throws RemoteException, Exception
    {
        List result = new ArrayList();
        IBOAIMonServerValue[] serverValues = getAllServerBean();
        if (serverValues != null && serverValues.length > 0)
        {
            for (int i = 0; i < serverValues.length; i++)
            {
                Server item = this.wrapperServerByBean(serverValues[i]);
                if (item != null)
                    result.add(new ServerConfig(item));
            }
        }
        return result;
    }
    
    /**
     * 读取所有应用值对象信息
     * 
     * @return
     * @throws Exception
     */
    @Override
    public IBOAIMonServerValue[] getAllServerBean()
        throws RemoteException, Exception
    {
        IAIMonServerDAO appServerDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        return appServerDAO.getAllServer();
    }
    
    /**
     * 根据主机信息，读取应用信息
     * 
     * @param nodeId
     * @return
     * @throws Exception
     */
    @Override
    public List getAppServerConfigByNodeId(String nodeId)
        throws RemoteException, Exception
    {
        HashMap serverMap = MonCacheManager.getAll(AIMonServerCacheImpl.class);
        Iterator it = serverMap.entrySet().iterator();
        List result = new ArrayList();
        while (it != null && it.hasNext())
        {
            Entry item = (Entry)it.next();
            if (((Server)item.getValue()).getNode_Id().equals(nodeId))
            {
                result.add(new ServerConfig((Server)item.getValue()));
            }
        }
        Collections.sort(result, new ServerComparator());
        return result;
    }
    
    /**
     * 根据主机信息，读取应用信息
     * 
     * @param nodeId
     * @return
     * @throws Exception
     */
    @Override
    public List getAppServerConfigByNodeIdNocache(String nodeId)
        throws RemoteException, Exception
    {
        List result = new ArrayList();
        IAIMonServerDAO appServerDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        IBOAIMonServerValue[] serverValues = appServerDAO.getServerByCond(nodeId, null);
        if (serverValues == null || serverValues.length == 0)
        {
            return result;
        }
        for (int i = 0; i < serverValues.length; i++)
        {
            Server item = this.wrapperServerByBean(serverValues[i]);
            if (item != null)
                result.add(new ServerConfig(item));
        }
        Collections.sort(result, new ServerComparator());
        return result;
    }
    
    /**
     * 根据主机信息以及区域信息，读取应用信息
     * 
     * @param nodeId
     * @return
     * @throws Exception
     */
    @Override
    public List getAppServerConfigByNodeIdAndUserId(String nodeId, String userId)
        throws RemoteException, Exception
    {
        IAIMonUserSV userSV = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
        List domains = userSV.getDomainsByUserId(userId);
        HashMap serverMap = MonCacheManager.getAll(AIMonServerCacheImpl.class);
        
        Iterator it = serverMap.entrySet().iterator();
        List result = new ArrayList();
        while (it != null && it.hasNext())
        {
            Entry item = (Entry)it.next();
            Server server = (Server)item.getValue();
            if (server.getNode_Id().equals(nodeId))
            {
                if (domains == null || domains.isEmpty() || domains.size() < 1)
                {
                    result.add(new ServerConfig(server));
                }
                else
                {
                    if (StringUtils.isNotBlank(server.getDomain()))
                    {
                        if (domains.contains(server.getDomain()))
                        {
                            result.add(new ServerConfig(server));
                        }
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * 根据名称查找匹配的应用
     * 
     * @param serverName
     * @param userId
     * @param matchWhole:全字匹配
     * @return
     * @throws Exception
     */
    @Override
    public List getAppServerConfigByName(String serverName, String userId, boolean matchWhole)
        throws RemoteException, Exception
    {
        List result = null;
        List domains = null;
        if (StringUtils.isNotBlank(userId))
        {
            IAIMonUserSV userSV = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
            domains = userSV.getDomainsByUserId(userId);
        }
        
        HashMap serverMap = MonCacheManager.getAll(AIMonServerCacheImpl.class);
        if (serverMap == null || serverMap.size() == 0)
            return result;
        Iterator it = serverMap.entrySet().iterator();
        result = new ArrayList();
        while (it != null && it.hasNext())
        {
            Entry item = (Entry)it.next();
            IServer server = (IServer)item.getValue();
            if (matchWhole)
            {
                if (server.getApp_Name().toLowerCase().equalsIgnoreCase(serverName.toLowerCase()))
                {
                    if (domains == null || domains.size() < 1)
                        result.add(server);
                    else
                    {
                        if (StringUtils.isNotBlank(server.getDomain()))
                        {
                            if (domains.contains(server.getDomain()))
                            {
                                result.add(server);
                            }
                        }
                    }
                }
            }
            else
            {
                if (server.getApp_Name().toLowerCase().indexOf(serverName.toLowerCase()) >= 0)
                {
                    if (domains == null || domains.size() < 1)
                        result.add(server);
                    else
                    {
                        if (StringUtils.isNotBlank(server.getDomain()))
                        {
                            if (domains.contains(server.getDomain()))
                            {
                                result.add(server);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * 根据条件查询应用信息,拼成xml传给Topu展示
     * 
     * @param serverName
     * @return
     * @throws Exception
     */
    @Override
    public String getServerTopuXmlByName(String serverName, String userId)
        throws RemoteException, Exception
    {
        StringBuilder treeXml = new StringBuilder();
        try
        {
            INodeInfo sysNode = marshalByAppName(serverName, userId);
            // 遍历Tree节点
            if (sysNode != null && sysNode.hasChild())
            {
                treeXml.append("<Node name=\"")
                    .append(AIMonLocaleFactory.getResource("MOS0000104"))
                    .append("\" id=\"-1\" nodeClass=\"root\">");
                Map map = (HashMap)sysNode.getChild();
                Iterator it = map.entrySet().iterator();
                Map hostMap = null;
                INodeVisitor visitor = new TreeXmlNodeVisitor();
                INodeInfo groupNode = null;
                INodeInfo hostNode = null;
                INodeInfo serverNode = null;
                while (it.hasNext())
                {
                    Entry gItem = (Entry)it.next();
                    groupNode = (INodeInfo)gItem.getValue();
                    if (0 == Integer.parseInt(groupNode.getId()))
                        continue;
                    treeXml.append(((IVisitable)groupNode).accept(visitor));
                    hostMap = (HashMap)groupNode.getChild();
                    Iterator jt = hostMap.entrySet().iterator();
                    Map serverMap = null;
                    while (jt.hasNext())
                    {
                        Entry hItem = (Entry)jt.next();
                        hostNode = (INodeInfo)hItem.getValue();
                        if (0 == Integer.parseInt(hostNode.getId()))
                            continue;
                        treeXml.append(((IVisitable)hostNode).accept(visitor));
                        serverMap = (HashMap)hostNode.getChild();
                        Iterator kt = serverMap.entrySet().iterator();
                        while (kt.hasNext())
                        {
                            Entry sItem = (Entry)kt.next();
                            serverNode = (INodeInfo)sItem.getValue();
                            if (0 == Integer.parseInt(serverNode.getId()))
                                continue;
                            treeXml.append(((IVisitable)serverNode).accept(visitor));
                            treeXml.append("</Node>");
                        }
                        treeXml.append("</Node>");
                    }
                    treeXml.append("</Node>");
                }
                treeXml.append("</Node>");
                visitor = null;
                groupNode = null;
                hostNode = null;
                serverNode = null;
            }
        }
        catch (Exception e)
        {
            log.error("Call AIMonServerSVImpl's Method getServerTopuXmlByName has Exception :" + e.getMessage());
        }
        return treeXml.toString();
    }
    
    /**
     * 根据组标识、主机标识读取所有服务的拓扑
     * 
     * @param groupId
     * @param nodeId
     * @return
     * @throws Exception
     */
    @Override
    public String getServerTopuXmlByNodeIdAndUserId(String nodeId, String userId)
        throws RemoteException, Exception
    {
        StringBuilder result = new StringBuilder("");
        try
        {
            if (StringUtils.isNotBlank(nodeId))
            {
                List serverValues = this.getAppServerConfigByNodeIdAndUserId(nodeId, userId);
                if (serverValues != null && serverValues.size() > 0)
                {
                    result.append("<Graph>");
                    IObjectStrategy strategy = new TopuXmlStrategy();
                    for (int i = 0; i < serverValues.size(); i++)
                    {
                        result.append(strategy.visitServer((ServerConfig)serverValues.get(i)));
                    }
                    result.append("</Graph>");
                }
            }
        }
        catch (Exception e)
        {
            log.error("Call AIMonServerSVImpl's Method getServerTopuXmlByGrpIdAndHostId has Exception :"
                + e.getMessage());
        }
        return result.toString();
    }
    
    /**
     * 获取应用信息XML作为树节点
     * 
     * @param nodeId
     * @return List
     * @throws Exception
     */
    @Override
    public List getServerTreeXmlByNodeId(String nodeId)
        throws RemoteException, Exception
    {
        if (StringUtils.isBlank(nodeId))
            return null;
        List result = null;
        try
        {
            List serverValues = this.getAppServerConfigByNodeId(nodeId);
            if (null == serverValues || serverValues.size() < 1)
                return null;
            result = new ArrayList();
            IObjectStrategy visitorStrategy = new TreeXmlStrategy();
            for (int i = 0; i < serverValues.size(); i++)
            {
                result.add(visitorStrategy.visitServer((IServer)serverValues.get(i)));
            }
        }
        catch (Exception e)
        {
            log.error("Call AIMonServerSVImpl's Method getServerTreeXmlByHostId has Exception :" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 获取应用信息XML作为树节点
     * 
     * @param nodeId
     * @return List
     * @throws Exception
     */
    @Override
    public List getServerTreeXmlByNodeIdNocache(String nodeId)
        throws RemoteException, Exception
    {
        if (StringUtils.isBlank(nodeId))
            return null;
        List result = null;
        try
        {
            IObjectStrategy visitorStrategy = new TreeXmlStrategy();
            List serverValues = this.getAppServerConfigByNodeIdNocache(nodeId);
            if (null == serverValues || serverValues.size() < 1)
                return null;
            result = new ArrayList();
            for (int i = 0; i < serverValues.size(); i++)
            {
                IServer server = (IServer)serverValues.get(i);
                result.add(String.valueOf(visitorStrategy.visitServer(server)));
            }
        }
        catch (Exception e)
        {
            log.error("Call AIMonServerSVImpl's Method getServerTreeXmlByHostId has Exception :" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 根据应用名，返回组--主机--应用结构对象
     * 
     * @param appName
     * @return
     * @throws Exception
     */
    private INodeInfo marshalByAppName(String appName, String userId)
        throws RemoteException, Exception
    {
        if (StringUtils.isBlank(appName))
            // "应用名为空"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000112"));
        
        List values = getAppServerConfigByName(appName, userId, false);
        if (values == null || values.size() <= 0)
            return null;
        
        INodeInfo sysNode = new TreeNodeInfo();
        // 主机节点集合
        INodeInfo tmpNode = new TreeNodeInfo();
        INodeInfo groupNode = null;
        INodeInfo logicNode = null;
        INodeInfo serverNode = null;
        for (int i = 0; i < values.size(); i++)
        {
            serverNode = new ServerNode();
            serverNode.setId(((IServer)values.get(i)).getApp_Id() + "");
            if (!tmpNode.containChild(((IServer)values.get(i)).getNode_Id()))
            {
                logicNode = new LogicNode();
                logicNode.setId(((IServer)values.get(i)).getNode_Id() + "");
                logicNode.addChild(((IServer)values.get(i)).getApp_Id(), serverNode);
                tmpNode.addChild(((IServer)values.get(i)).getNode_Id(), logicNode);
            }
            else
            {
                logicNode = tmpNode.getChild(((IServer)values.get(i)).getNode_Id());
                logicNode.addChild(((IServer)values.get(i)).getApp_Id(), serverNode);
            }
            serverNode.setParent(logicNode);
        }
        if (tmpNode.hasChild())
        {
            // 根据主机找归属组
            Map tmpMap = (HashMap)tmpNode.getChild();
            Iterator nt = tmpMap.keySet().iterator();
            IServerNode nodeValue = null;
            while (nt.hasNext())
            {
                logicNode = (INodeInfo)tmpMap.get(nt.next());
                IAIMonNodeSV nodeSV = (IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
                nodeValue = nodeSV.getNodeByNodeId(logicNode.getId());
                if (!sysNode.containChild(nodeValue.getGroup_Id()))
                {
                    groupNode = new GroupNode();
                    groupNode.setId(nodeValue.getGroup_Id());
                    groupNode.addChild(nodeValue.getNode_Id(), logicNode);
                    sysNode.addChild(nodeValue.getGroup_Id(), groupNode);
                }
                else
                {
                    groupNode = sysNode.getChild(nodeValue.getGroup_Id());
                    groupNode.addChild(logicNode.getId(), logicNode);
                }
                logicNode.setParent(groupNode);
            }
        }
        return sysNode;
    }
    
    /**
     * 检查统一版本
     * 
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public SimpleResult checkUniteVersion(Object[] ids)
        throws RemoteException, Exception
    {
        if (ids == null || ids.length < 1)
            // 没有传入参数
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000005"));
        IAIMonServerDAO serverDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        List versions = new ArrayList();
        SimpleResult result = new SimpleResult();
        for (int i = 0; i < ids.length; i++)
        {
            if (ids[i] != null)
            {
                IBOAIMonServerValue value = serverDAO.getServerByServerId(String.valueOf(ids[i]));
                if (value != null)
                {
                    if (StringUtils.isBlank(value.getSversion()))
                    {
                        result.setSucc(false);
                        // "存在空版本信息"
                        result.setMsg(AIMonLocaleFactory.getResource("MOS0000113"));
                        return result;
                    }
                    if (!versions.contains(value.getSversion()))
                    {
                        if (versions.size() > 1)
                        {
                            result.setSucc(false);
                            // "存在多个版本信息"
                            result.setMsg(AIMonLocaleFactory.getResource("MOS0000114"));
                            return result;
                        }
                        versions.add(value.getSversion());
                    }
                }
            }
        }
        if (versions.size() > 1)
        {
            result.setSucc(false);
            result.setMsg(AIMonLocaleFactory.getResource("MOS0000114"));
            return result;
        }
        else
        {
            result.setSucc(true);
            result.setMsg(AIMonLocaleFactory.getResource("MOS0000115"));
            return result;
        }
    }
    
    /**
     * 读取，保存，检查版本信息
     * 
     * @param ids
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    @Override
    public SimpleResult checkVersion(Object[] ids)
        throws RemoteException, Exception
    {
        
        SimpleResult result = new SimpleResult();
        IBOAIMonServerValue[] servers = null;
        
        try
        {
            if (ids == null || ids.length < 0)
            {
                return null;
            }
            List appList = new ArrayList();
            ServerConfig appServer = null;
            IBOAIMonServerValue server = null;
            for (int i = 0; i < ids.length; i++)
            {
                appServer = this.getServerByServerId(ids[i].toString());
                
                if (appServer != null)
                {
                    server = new BOAIMonServerBean();
                    server.setServerId(Long.parseLong(appServer.getApp_Id()));
                    server.setServerCode(appServer.getApp_Code());
                    server.setServerName(appServer.getApp_Name());
                    server.setLocatorType(appServer.getLocator_Type());
                    server.setLocator(appServer.getLocator());
                    server.setServerIp(appServer.getApp_Ip());
                    server.setServerPort(Short.parseShort(appServer.getApp_Port()));
                    server.setCheckUrl(appServer.getCheck_Url());
                    server.setMidwareType(appServer.getMidware_Type());
                    server.setTempType(appServer.getTemp_Type());
                    server.setNodeId(Long.parseLong(appServer.getNode_Id()));
                    server.setStartCmdId(Short.parseShort(appServer.getStart_CmdId()));
                    server.setStopCmdId(Short.parseShort(appServer.getStop_CmdId()));
                    server.setState(appServer.getState());
                    server.setSversion(appServer.getSversion());
                    server.setRemark(appServer.getRemark());
                    
                    appList.add(server);
                }
                
            }
            if (appList.size() > 0)
            {
                servers = (IBOAIMonServerValue[])appList.toArray(new IBOAIMonServerValue[0]);
            }
            ServerControlInfo[] controlInfo = null;
            IAPIShowServerStatusSV showStateSv =
                (IAPIShowServerStatusSV)ServiceFactory.getService(IAPIShowServerStatusSV.class);
            controlInfo = showStateSv.getAppServerStates(ids, 0, 0);
            
            for (int i = 0; i < servers.length; i++)
            {
                long sid = servers[i].getServerId();
                for (int j = 0; j < controlInfo.length; j++)
                {
                    if (Long.toString(sid).equals(controlInfo[j].getServerId()))
                    {
                        String version = controlInfo[j].getInfo();
                        servers[i].setStsToOld();
                        servers[i].setSversion(version);
                    }
                }
            }
            
            saveOrUpdate(servers);
            
            result = checkUniteVersion(ids);
            
        }
        catch (Exception e)
        {
            log.error("Call AIMonServerSVImpl's Method checkVersion has Exception :" + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 批量保存或修改应用服务器设置
     * 
     * @param values
     * @throws Exception
     */
    @Override
    public void saveOrUpdate(IBOAIMonServerValue[] values)
        throws RemoteException, Exception
    {
        boolean modify = values[0].isModified();
        IAIMonServerDAO serverDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        serverDAO.saveOrUpdate(values);
        for (int i = 0; i < values.length; i++)
        {
            if (modify)
            {
                Server item = (Server)MonCacheManager.get(AIMonServerCacheImpl.class, values[i].getServerId() + "");
                if (item != null)
                    item.setEnable(false);
            }
            else
            {
                Server item = this.wrapperServerByBean(values[i]);
                MonCacheManager.put(AIMonServerCacheImpl.class, item.getApp_Id(), item);
            }
        }
    }
    
    /**
     * 保存或修改应用服务器设置
     * 
     * @param value
     * @throws Exception
     */
    @Override
    public long saveOrUpdate(IBOAIMonServerValue value)
        throws RemoteException, Exception
    {
        boolean modify = value.isModified();
        
        if (value == null || StringUtils.isBlank(value.getServerCode()))
            // "应用编码不能为空!"
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000116"));
        IAIMonServerDAO serverDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        /*
         * if(value.isNew()) { //校验server_code不能重复 IBOAIMonServerValue server =
         * serverDAO.getServerByServerCode(value.getServerCode()); if(server != null && !server.isNew()) //
         * "应用编码："+value.getServerCode()+"，已经存在，无法保存！" throw new Exception(AIMonLocaleFactory.getResource("MOS0000117",
         * value.getServerCode())); }
         */
        long id = serverDAO.saveOrUpdate(value);
        if (modify)
        {
            Server item = (Server)MonCacheManager.get(AIMonServerCacheImpl.class, value.getServerId() + "");
            if (item != null)
                item.setEnable(false);
        }
        else
        {
            Server item = this.wrapperServerByBean(value);
            MonCacheManager.put(AIMonServerCacheImpl.class, item.getApp_Id(), item);
        }
        return id;
    }
    
    /**
     * 保存或修改应用服务器信息,涉及域的更改
     * 
     * @param value
     * @param domainId 域标识
     * @throws Exception
     */
    @Override
    public void saveOrUpdate(IBOAIMonServerValue serverValue, String domainId, String jmxState)
        throws RemoteException, Exception
    {
        boolean modify = serverValue.isModified();
        
        IAIMonServerDAO serverDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        long serverId = saveOrUpdate(serverValue);
        
        try
        {
            IAIMonDomainSV domainSV = (IAIMonDomainSV)ServiceFactory.getService(IAIMonDomainSV.class);
            if (StringUtils.isBlank(domainId))
                domainSV.deleteRelat(String.valueOf(serverId), "3");
            else
                domainSV.saveRelat(domainId, String.valueOf(serverId), "3");
        }
        catch (Exception ex)
        {
            // "保存应用服务器信息，维护应用与域的关系出现异常!"
            log.error(AIMonLocaleFactory.getResource("MOS0000118"), ex);
            throw ex;
        }
        
        try
        {
            IAIMonSetSV setSV = (IAIMonSetSV)ServiceFactory.getService(IAIMonSetSV.class);
            IBOAIMonSetValue[] setValues = setSV.getMonSetBeanByAppIdAndVCode(String.valueOf(serverId), "JMX_STATE");
            IBOAIMonSetValue setValue;
            if (setValues == null || setValues.length == 0)
            {
                setValue = new BOAIMonSetBean();
                setValue.setServerId(serverValue.getServerId());
                setValue.setAppName(serverValue.getServerName());
                setValue.setSetVcode("JMX_STATE");
                setValue.setSetValue(jmxState);
                setValue.setCreateDate(new Timestamp(System.currentTimeMillis()));
                setValue.setState("U");
            }
            else
            {
                setValue = setValues[0];
                setValue.setSetValue(jmxState);
            }
            setSV.saveOrUpdate(setValue);
        }
        catch (Exception ex)
        {
            // "保存应用服务器信息，维护JMX状态出现异常!"
            log.error(AIMonLocaleFactory.getResource("MOS0000119"), ex);
            throw ex;
        }
        if (modify)
        {
            Server item = (Server)MonCacheManager.get(AIMonServerCacheImpl.class, serverValue.getServerId() + "");
            if (item != null)
                item.setEnable(false);
        }
        else
        {
            Server item = this.wrapperServerByBean(serverValue);
            MonCacheManager.put(AIMonServerCacheImpl.class, item.getApp_Id(), item);
        }
        
    }
    
    /**
     * 删除应用服务器信息
     * 
     * @param appServerId
     * @throws Exception
     */
    @Override
    public void deleteServer(long serverId)
        throws RemoteException, Exception
    {
        IAIMonServerDAO serverDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        serverDAO.deleteServer(serverId);
        // 删除应用所属域
        IAIMonDomainSV domainSV = (IAIMonDomainSV)ServiceFactory.getService(IAIMonDomainSV.class);
        domainSV.deleteRelat(String.valueOf(serverId), "3");
        // 删除应用的JMX开启状态
        IAIMonSetSV setSV = (IAIMonSetSV)ServiceFactory.getService(IAIMonSetSV.class);
        IBOAIMonSetValue[] setValues = setSV.getMonSetBeanByAppIdAndVCode(String.valueOf(serverId), "JMX_STATE");
        if (setValues == null || setValues.length == 0)
            return;
        setValues[0].setState("E");
        setSV.saveOrUpdate(setValues[0]);
        Server item = (Server)MonCacheManager.get(AIMonServerCacheImpl.class, serverId + "");
        if (item != null)
            item.setEnable(false);
    }
    
    /**
     * 根据组名、主机名、应用名获取应用信息
     * 
     * @param groupName
     * @param hostName
     * @param serverName
     * @return
     * @throws Exception
     */
    @Override
    public List getServerInfo(String groupName, String nodeName, String serverName)
        throws RemoteException, Exception
    {
        List allServers = this.getAllServer();
        List result = new ArrayList();
        ServerConfig serverCfg = null;
        
        for (int i = 0; i < allServers.size(); i++)
        {
            serverCfg = (ServerConfig)allServers.get(i);
            if (StringUtils.isNotBlank(serverName) && !serverName.equals(serverCfg.getApp_Name()))
            {
                continue;
            }
            if (StringUtils.isNotBlank(nodeName) && !nodeName.equals(serverCfg.getNodeConfig().getNode_Name()))
            {
                continue;
            }
            if (StringUtils.isNotBlank(groupName)
                && !groupName.equals(serverCfg.getNodeConfig().getGroupConfig().getGroup_Name()))
            {
                continue;
            }
            result.add(serverCfg);
        }
        return result;
    }
    
    /**
     * 根据组名、主机名、应用名获取应用信息
     * 
     * @param groupName
     * @param hostName
     * @param serverName
     * @return
     * @throws Exception
     */
    @Override
    public List getServerInfoNocache(String groupName, String hostName, String serverName)
        throws RemoteException, Exception
    {
        List allServers = this.getAllServerNocache();
        List result = new ArrayList();
        ServerConfig serverCfg = null;
        
        for (int i = 0; i < allServers.size(); i++)
        {
            serverCfg = (ServerConfig)allServers.get(i);
            if (StringUtils.isNotBlank(serverName) && !serverName.equals(serverCfg.getApp_Name()))
            {
                
                continue;
            }
            if (StringUtils.isNotBlank(hostName) && !hostName.equals(serverCfg.getNodeConfig().getNode_Name()))
            {
                continue;
            }
            if (StringUtils.isNotBlank(groupName)
                && !groupName.equals(serverCfg.getNodeConfig().getGroupConfig().getGroup_Name()))
            {
                continue;
            }
            result.add(serverCfg);
        }
        return result;
    }
    
    @Override
    public ServerControlInfo[] getServerStatus(ServerControlInfo[] serverInfo)
        throws Exception
    {
        return ServerControlApi.monitorServerControl(3, 2, serverInfo);
    }
    
    /**
     * 取得所有非App类型的应用ID
     * 
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    @Override
    public List getServerIdsByType(String tempType)
        throws RemoteException, Exception
    {
        List allServers = this.getAllServer();
        List result = new ArrayList();
        ServerConfig serverCfg = null;
        
        for (int i = 0; i < allServers.size(); i++)
        {
            serverCfg = (ServerConfig)allServers.get(i);
            if ("APP".equals(serverCfg.getTemp_Type()))
            {
                continue;
            }
            result.add(serverCfg.getApp_Id());
        }
        return result;
    }
    
    /**
     * 通过nodeId取出应用节点
     * 
     * @param nodeId
     * @throws Exception
     */
    @Override
    public IBOAIMonServerValue[] qryServerInfo(String nodeId)
        throws RemoteException, Exception
    {
        IAIMonServerDAO serDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        return serDAO.qryServerInfo(nodeId);
    }
    
    /**
     * 
     * @Description:获取参数选择列表
     * 
     * @param:参数描述
     * @return：返回结果描述
     * @throws：异常描述
     */
    @Override
    public IDataBus getSelectList(String constCode)
        throws RemoteException, Exception
    {
        HashMap all = CacheFactory.getAll(AIMonStaticDataCacheImpl.class);
        IBOAIMonStaticDataValue[] result = null;
        if ("LOCATOR_TYPE".equals(constCode))
        {
            result = (IBOAIMonStaticDataValue[])all.get("_CT^LOCATOR_TYPE");
        }
        if ("TEMP_TYPE".equals(constCode))
        {
            result = (IBOAIMonStaticDataValue[])all.get("_CT^TEMP_TYPE");
        }
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < result.length; i++)
        {
            JSONObject obj = new JSONObject();
            String key = result[i].getCodeName();
            String val = result[i].getCodeValue();
            obj.put("TEXT", key);
            obj.put("VALUE", val);
            jsonArray.add(obj);
        }
        return new DataBus(context, jsonArray);
    }
    
    @Override
    public IBOAIMonServerValue qryServerByServerId(String serverId)
        throws RemoteException, Exception
    {
        IAIMonServerDAO serDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        return serDAO.qryServerByServerId(serverId);
    }
    
    @Override
    public IBOAIMonServerValue[] qryByCondition(List<String> nodeIdList)
        throws Exception
    {
        Criteria conSql = new Criteria();
        conSql.addIn(IBOAIMonServerValue.S_NodeId, nodeIdList);
        conSql.addEqual(IBOAIMonServerValue.S_State, CommonConst.STATE_U);
        
        return BOAIMonServerEngine.getBeans(conSql);
    }
    
    @Override
    public boolean isExistServerByCode(String serverCode)
        throws Exception
    {
        IAIMonServerDAO serDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        return serDAO.isExistServerByCode(serverCode);
    }
    
    @Override
    public IBOAIMonServerValue qryServerByServerCode(String serverCode)
        throws Exception
    {
        log.debug("SserverCode:" + serverCode);
        IAIMonServerDAO serDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        return serDAO.qryServerByServerCode(serverCode);
    }
    
    @Override
    public String qryHostIdByServerCode(String serverCode)
        throws Exception
    {
        IAIMonServerDAO serDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        return serDAO.qryHostIdByServerCode(serverCode);
    }
    
    @Override
    public IBOAIMonServerValue[] qryServerByNodeId(String nodeId)
        throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if (StringUtils.isNotBlank(nodeId))
        {
            condition.append(IBOAIMonServerValue.S_NodeId).append(" = :nodeId");
            parameter.put("nodeId", nodeId);
        }
        if (StringUtils.isNotBlank(condition.toString()))
        {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        return BOAIMonServerEngine.getBeans(condition.toString(), parameter);
    }
    
    @Override
    public List<IBOAIMonServerValue> qryServerByHostId(String hostId)
        throws Exception
    {
        List<IBOAIMonServerValue> IBOAIMonServerValues = new ArrayList<IBOAIMonServerValue>();
        
        IAIMonNodeSV nodeSv = (IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
        IBOAIMonNodeValue[] nodes = nodeSv.qryNodeInfo(hostId);
        
        if (nodes == null)
        {
            return IBOAIMonServerValues;
        }
        
        for (IBOAIMonNodeValue node : nodes)
        {
            IBOAIMonServerValue[] values = qryServerByNodeId(Long.toString(node.getNodeId()));
            if (values == null)
            {
                break;
            }
            
            IBOAIMonServerValues.addAll(Arrays.asList(values));
        }
        
        return IBOAIMonServerValues;
        
    }
    
    @Override
    public Map<String, Long> getServerHosts()
        throws Exception
    {
        IAIMonServerDAO serDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        return serDAO.getServerHosts();
    }
    
    @Override
    public JSONObject getServerHostsMap()
        throws Exception
    {
        IAIMonServerDAO serDAO = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        return serDAO.getServerHostsMap();
    }
    
}
