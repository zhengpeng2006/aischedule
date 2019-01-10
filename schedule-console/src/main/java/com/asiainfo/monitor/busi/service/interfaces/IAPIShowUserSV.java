package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

public interface IAPIShowUserSV {

	/**
	 * 获取在线用户信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getOnlineUsers(String appId) throws RemoteException,Exception;
	
	/**
	 * 根据服务器分组获取在线用户信息
	 * 
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getOnlineUsers(Object[] appIds) throws RemoteException,Exception;
	
	/**
	 * 根据服务器分组,地区、组织查询在线用户信息
	 * @param appId
	 * @param regionId:查询全部为0
	 * @param orgName:组织名称，模糊匹配
	 * @return
	 * @throws Exception
	 */
	public List getOnlineUsersByRegionAndOrgName(Object[] appIds, String regionId, String orgName, String ipRep) throws RemoteException,Exception;
	
	/**
	 * 得到用户汇总信息,地区、终端数、用户数
	 * @param appIds
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public List getCollectOnlineUsers(Object[] appIds)  throws RemoteException,Exception;
	
	/**
	 * 获取多应用的在线用户统计
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getServerOnlineUsers(Object[] appIds) throws RemoteException,Exception;
}
