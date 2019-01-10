package com.asiainfo.monitor.busi.service.interfaces;


import java.rmi.RemoteException;

import com.asiainfo.monitor.interapi.config.CallResult;

public interface IAPIControlServerStatusSV {

	/**
	 * 启动服务器
	 * @param appIds
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public CallResult[] startServer(Object[] appIds, int timeOut, String clientID) throws RemoteException,Exception;
	
	/**
	 * 停止服务器
	 * @param appIds
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public CallResult[] stopServer(Object[] appIds, int timeOut, String clientID) throws RemoteException,Exception;
	
	/**
	 * 重新启动服务器,暂时只允许当前只有一台操作
	 * @param appIds
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public CallResult[] reStartServer(Object appIds[], int timeOut, String clientID) throws RemoteException,Exception;
}
