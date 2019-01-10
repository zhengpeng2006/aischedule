package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSetValue;


public interface IAIMonSetAtomicSV {

	/**
	 * 根据标识获取设置
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue getMonSetBeanById(String id) throws RemoteException,Exception;
	
	/**
	 * 根据应用标识、状态码查询应用状态信息
	 * @param appId
	 * @param vCode
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonSetValue getMonSetBeanBySCodeVCode(String appId, String vCode) throws RemoteException,Exception;
}
