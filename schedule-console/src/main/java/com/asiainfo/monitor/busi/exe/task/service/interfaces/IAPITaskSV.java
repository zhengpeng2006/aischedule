package com.asiainfo.monitor.busi.exe.task.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

import com.asiainfo.monitor.interapi.config.AIMemoryInfo;
import com.asiainfo.monitor.interapi.config.AITmSummary;

public interface IAPITaskSV {

	/**
	 * 后台定时任务,获取App应用的服务调用情况
	 * @param appId
	 * @param clazz
	 * @param method
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getSVMehtodInfo(String appId) throws RemoteException,Exception;
	
	/**
	 * 后台定时任务,获取运行时数据源信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getDataSourceRuntime(String appId) throws RemoteException,Exception;
	
	/**
	 * 后台定时任务,返回服务器内存信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public AIMemoryInfo getJVM5MemoryInfo(String appId) throws RemoteException,Exception;
	
	/**
	 * 后台定时任务,获取在线用户信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getOnlineUsers(String appId) throws RemoteException,Exception;
	
	/**
	 * 后台定时任务,读取事务概况信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public AITmSummary getTransaction(String appId) throws RemoteException,Exception;
}
