package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

public interface IAPIControlTraceSV {

	/**
	 * 启动选择web应用的webtrace，并且根据路由信息，找到路由到的app服务器，并且打开apptrace
	 * 联动操作
	 * @param ids
	 * @param time
	 * @param userCode
	 * @param url
	 * @param custIp
	 * @param code
	 * @param className
	 * @param methodName
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public List startTraceByDrive(Object[] ids, String driveflag, String time, String userCode, String url, String custIp, String code, String className, String methodName) throws RemoteException,Exception;
	
	/**
	 * 联动停止Trace，在停止webtrace的过程中，可以同时停止路由的app应用的apptrace
	 * ids不限制为web应用的标识，也可以是app应用的标识或者类型为BOTH的应用标识
	 * @param ids
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public List stopTraceByDrive(Object[] ids, String driveflag) throws RemoteException,Exception;
	
	/**
	 * 启动Trace
	 * @param appServerIds
	 * @param code
	 * @param className
	 * @param methodName
	 * @return
	 */
	public List startAppTrace(Object[] ids, String code, String className, String methodName) throws RemoteException,Exception;
	
	/**
	 * 关闭Trace
	 * @param appServerIds
	 * @return
	 */
	public List stopAppTrace(Object[] ids) throws RemoteException,Exception;
	
	/**
	 * 启动WebTrace
	 * @param appServerIds
	 * @param code
	 * @return
	 */
	public List startWebTrace(Object[] ids, String time, String userCode, String url, String custIp) throws RemoteException,Exception;
	
	/**
	 * 关闭Trace
	 * @param appServerIds
	 * @return
	 */
	public List stopWebTrace(Object[] ids) throws RemoteException,Exception;
	
}
