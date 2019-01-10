package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;

public interface IAIMonServerAtomicSV {

	/**
	 * 根据标识读取应用值对象信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonServerValue getServerBeanById(String id) throws RemoteException,Exception;
	
	/**
	 * 根据应用码查找应用值对象
	 * @param code
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonServerValue getServerBeanByCode(String code) throws RemoteException,Exception;
	
	/**
	 * 根据IP和PORT查找应用值对象
	 * @param code
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonServerValue getServerBeanByIpPort(String ip, String port) throws RemoteException,Exception;
	
	/**
	 * 读取所有应用值对象信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonServerValue[] getAllServerBean() throws RemoteException,Exception;
}
