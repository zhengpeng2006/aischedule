package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

public interface IAPIControlStatSV {

	/**
	 * 启动在线服务统计监控
	 * @param appServerIds
	 * @param second
	 * @return
	 */
	public List startSVMethod(Object[] ids, String second) throws RemoteException,Exception;
	
	/**
	 * 停止在线服务统计监控
	 * @param appServerIds
	 * @param second
	 * @return
	 */
	public List stopSVMethod(Object[] ids) throws RemoteException,Exception;
	
	/**
	 * 启动在线服务统计监控
	 * @param appServerIds
	 * @param second
	 * @return
	 */
	public List startSQL(Object[] ids, String second) throws RemoteException,Exception;
	
	/**
	 * 停止在线服务统计监控
	 * @param appServerIds
	 * @param second
	 * @return
	 */
	public List stopSQL(Object[] ids) throws RemoteException,Exception;
	
	/**
	 * 启动在线服务统计监控
	 * @param appServerIds
	 * @param second
	 * @return
	 */
	public List startAction(Object[] ids, String second) throws RemoteException,Exception;
	
	/**
	 * 停止在线服务统计监控
	 * @param appServerIds
	 * @param second
	 * @return
	 */
	public List stopAction(Object[] ids) throws RemoteException,Exception;
	
	
}
