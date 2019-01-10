package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

public interface IAPIShowTraceSV {

	/**
	 * 根据应用标识读取AppTrace的状态、内容
	 * @param appServerIds
	 * @return
	 */
	public List getAppTraces(Object[] appServerIds) throws RemoteException,Exception;
	
	/**
	 * 根据应用标识，读取WebTrace状态、信息
	 * @param appServerIds
	 * @return
	 */
	public List getWebTraces(Object[] appServerIds) throws RemoteException,Exception;
	
}
