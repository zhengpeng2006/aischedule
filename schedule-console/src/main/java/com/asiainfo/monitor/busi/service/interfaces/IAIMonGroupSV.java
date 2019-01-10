package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

import com.asiainfo.monitor.busi.cache.impl.Group;
import com.asiainfo.monitor.busi.config.GroupConfig;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;

public interface IAIMonGroupSV {

	/**
	 * 根据组名查询Group
	 * 
	 * @param cond : String
	 * @return IBOAIMonGroupValue[]
	 * @throws Exception, RemoteException
	 */
	public List getGroupByName(String groupName, boolean matchWhole) throws Exception,RemoteException;
	/**
	 * 根据组名查询Group
	 * 
	 * @param cond : String
	 * @return IBOAIMonGroupValue[]
	 * @throws Exception, RemoteException
	 */
	public List getGroupByNameNocache(String groupName, boolean matchWhole) throws Exception,RemoteException;
	
	/**
	 * 读取所有组信息
	 * @return
	 * @throws Exception
	 */
	public List getAllGroup() throws RemoteException,Exception;
	
	
	/**
	 * 根据用户获取组信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List getAllGroupByUserId(String userId) throws RemoteException,Exception;
	
	/**
	 * 根据组标识从数据库读取组信息并封装
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Group getGroupByIdFromDb(String id) throws RemoteException,Exception;
	/**
	 * 将值对象简单封装
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Group wrapperGroupConfigByBean(IBOAIMonGroupValue value) throws RemoteException,Exception;
	
	/**
	 * 读取所有组信息值对象
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonGroupValue[] getAllGroupBean() throws RemoteException,Exception;
	/**
	 * 根据组标识，读取组配置信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public GroupConfig getGroupByGroupId(String groupId) throws RemoteException,Exception;
	
	
	/**
	 * 读取拓扑图XML(金字塔形)
	 * 后面再将做为缓存
	 * @return
	 * @throws Exception
	 */
	public String getTopuXml() throws RemoteException,Exception;
	
	/**
	 * 根据Session用户获取关联域拓扑信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String getTopuXmlByUserId(String userId) throws RemoteException,Exception;
	
	/**
	 * 读取只有组信息树的XML
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAllGroupTreeXml() throws RemoteException,Exception;
	
	/**
	 * 读取只有组信息树的XML
	 * @return
	 * @throws Exception
	 */
	public String getAllGroupTreeXmlNocache() throws RemoteException,Exception;
	
	/**
	 * 构建全局结构饼图Xml
	 * @return
	 * @throws Exception
	 */
	public String getWholeFramePieXml() throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改应用服务器组设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonGroupValue[] values) throws RemoteException,Exception;

	/**
	 * 保存或修改应用服务器组设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonGroupValue value, String domainId) throws RemoteException,Exception;
	
	/**
	 * 删除组信息
	 * @param groupId
	 * @throws Exception
	 */
	public void deleteGroup(long groupId) throws RemoteException,Exception;

	/**
	 * 获取组、主机、应用完整树xml
	 * @return
	 * @throws Exception
	 */
	public String getWholeTreeXml() throws RemoteException,Exception;
	
    /**
     * 获取groupbean信息
     * 
     * @param groupId
     * @return
     * @throws Exception
     */
    public IBOAIMonGroupValue getGroupBean(String groupId) throws Exception;

    public List<IBOAIMonPhysicHostValue> qryGroupHostInfo(String groupId) throws Exception;

    public void saveOrUpdate(IBOAIMonGroupValue value) throws Exception;

    public boolean isExistGroupByCode(String groupCode) throws Exception;

    public boolean isExistGroupByName(String groupName) throws Exception;
    
    /**
     * 根据权限信息获取分组信息
     * @param priv
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public IBOAIMonGroupValue[] getGroupByPriv(String priv) throws RemoteException,Exception;
	
}
