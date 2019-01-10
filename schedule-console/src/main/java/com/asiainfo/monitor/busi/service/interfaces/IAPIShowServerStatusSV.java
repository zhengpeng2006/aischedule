package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

import com.ai.appframe2.common.DataStructInterface;
import com.asiainfo.monitor.interapi.ServerControlInfo;

public interface IAPIShowServerStatusSV {

	/**
	 * 根据服务器组读取服务器信息
	 * @param groupId
	 * @param appServerName
	 * @return
	 * @throws Exception
	 */
	public DataStructInterface[] getAppServerByGroupId(String groupId, String appServerName, Integer threadCount, Integer timeOut, String isSelected) throws RemoteException,Exception;
	
	/**
	 * 计算ORB的ID
	 * @param appNames
	 * @return
	 * @throws Exception
	 */
	public List comptuerOrbId(Object[] appNames) throws RemoteException,Exception;
	
	/**
	 * 检查应用的状态
	 * @param appIds
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public List getAppServerState(Object[] appIds, Integer threadCount, Integer timeOut) throws RemoteException,Exception;
	
	/**
	 * 检查服务器状态
	 * 
	 * @param appIds
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public ServerControlInfo[] getAppServerStates(Object[] appIds, Integer threadCount, Integer timeOut) throws RemoteException,Exception;
	
	/**
	 * 检查服务器状态
	 * @param appId
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public ServerControlInfo getAppServerState(String appId, Integer threadCount, Integer timeOut) throws RemoteException,Exception;
}
