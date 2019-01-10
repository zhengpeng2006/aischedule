package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

import com.ailk.common.data.IDataBus;
import com.asiainfo.monitor.busi.cache.impl.ServerNode;
import com.asiainfo.monitor.busi.config.ServerNodeConfig;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;


public interface IAIMonNodeSV {

	/**
	 * 根据节点标识，读取节点信息
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public ServerNodeConfig getNodeByNodeId(String nodeId) throws RemoteException,Exception;
	
	
	/**
	 * 读取所有正常的节点
	 * @return
	 * @throws Exception
	 */
	public List getAllNode() throws RemoteException,Exception;
	
	/**
	 * 读取所有节点值对象
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonNodeValue[] getAllNodeBean() throws RemoteException,Exception;
	
	/**
	 * 根据组标识、节点名，匹配查找节点信息
	 * @param groupId
	 * @param nodeName
	 * @param matchWhole
	 * @return
	 * @throws Exception
	 */
	public List getNodeByGrpIdAndName(String groupId, String nodeName, boolean matchWhole) throws RemoteException,Exception;
	
	/**
	 * 根据组标识、节点名，匹配查找节点信息
	 * @param groupId
	 * @param nodeName
	 * @param matchWhole
	 * @return
	 * @throws Exception
	 */
	public List getNodeByGrpIdAndNameNocache(String groupId, String nodeName, boolean matchWhole) throws RemoteException,Exception;
	
	/**
	 * 根据节点名读取节点信息,模糊查询
	 * @param nodeName
	 * @return
	 * @throws Exception
	 */
	public List getNodeByName(String nodeName, boolean matchWhole) throws RemoteException,Exception;
	
	/**
	 * 根据节点名读取节点信息,模糊查询
	 * @param nodeName
	 * @param matchWhole
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public String getNodeXmlByName(String nodeName, boolean matchWhole) throws RemoteException,Exception;
	
	/**
	 * 根据组标识，读取节点信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List getNodeByGroupId(String groupId) throws RemoteException,Exception;
	/**
	 * 根据组标识，读取节点信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List getNodeByGroupIdNocache(String groupId) throws RemoteException,Exception;
	
	/**
	 * 获取节点信息XML作为树节点
	 * @param groupId
	 * @return List
	 * @throws Exception
	 */
	public List getNodeTreeXmlByGrpId(String groupId) throws RemoteException,Exception;
	
	/**
	 * 根据组标识和用户标识，读取节点拓扑
	 * @param groupId
	 * @param userId,用户标识暂且不用
	 * @return
	 * @throws Exception
	 */
	public String getNodeTopuXmlByGroupIdAndUserId(String groupId, String userId) throws RemoteException,Exception;
	
	/**
	 * 获取节点信息XML作为树节点
	 * @param groupId
	 * @return List
	 * @throws Exception
	 */
	public List getNodeTreeXmlByGrpIdNocache(String groupId) throws RemoteException,Exception;
	
	/**
	 * 根据标识从数据库读取节点信息并封装
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ServerNode getNodeByIdFormDb(String id) throws RemoteException,Exception;
	/**
	 * 将节点值对象简单封装
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public ServerNode wrapperNodeByBean(IBOAIMonNodeValue value) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改节点设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonNodeValue[] values) throws RemoteException,Exception;

	    /**
     * 保存或修改节点设置
     * @param value
     * @return 
     * @throws Exception
     */
    public long saveOrUpdate(IBOAIMonNodeValue value) throws RemoteException, Exception;
	
	/**
	 * 删除节点信息
	 * @param nodeId
	 * @throws Exception
	 */
	public void deleteNode(long nodeId) throws RemoteException,Exception;

    public IBOAIMonNodeValue[] qryNodeInfo(String hostId) throws RemoteException, Exception;

    public IBOAIMonNodeValue qryNodeInfoByNodeId(String nodeId) throws RemoteException, Exception;

    public boolean isExistNodeByCode(String hostId, String nodeCode) throws RemoteException, Exception;

    public boolean isExistNodeByName(String hostId, String nodeName) throws RemoteException, Exception;

    public IDataBus getSelectList() throws RemoteException, Exception;

    /**
     * 根据主机标识和节点标识查询节点信息
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonNodeValue qryNodeInfoByHostIdAndNodeCode(String hostId, String nodeCode) throws Exception;

    /**
     * 判断该主机下是否已存在监控节点
     * 
     * @param request
     * @throws Exception
     */
    public boolean isExistMonitorNode(String hostId) throws Exception;
}
