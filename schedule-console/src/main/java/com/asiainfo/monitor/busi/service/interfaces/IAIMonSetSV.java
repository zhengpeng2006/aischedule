package com.asiainfo.monitor.busi.service.interfaces;


import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.cache.impl.MonSet;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSetValue;

public interface IAIMonSetSV {

	/**
	 * 根据标识获取设置
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue getMonSetBeanById(String id) throws RemoteException,Exception;
	
	/**
	 * 根据应用标识、设置码查询设置
	 * @param appId
	 * @param vcode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue[] getMonSetBeanByAppIdAndVCode(String appId, String vcode) throws RemoteException,Exception;
	
	/**
	 * 根据应用标识、设置码从缓存中查询设置
	 * @param appId
	 * @param vcode
	 * @return
	 * @throws Exception
	 */
	public MonSet getMonSetByAppIdAndVCode(String appId, String vcode) throws RemoteException,Exception;
	
	/**
	 * 读取所有设置
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue[] getAllMonSetBean() throws RemoteException,Exception;
	
	/**
	 * 读取Jmx属性文件,并且设置Jmx是否启用
	 * @throws Exception
	 */
	public void synchroJmxState(Object[] appIds) throws RemoteException,Exception;
	
	/**
	 * 将设置简单封装
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public MonSet wrapperMonSetByBean(IBOAIMonSetValue value) throws RemoteException,Exception;
	
	/**
	 * 保存设置
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long saveOrUpdate(IBOAIMonSetValue value) throws RemoteException,Exception;
	
	/**
	 * 批量保存设置
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonSetValue[] values) throws RemoteException,Exception;
	
	/**
	 * 删除设置
	 * @param id
	 * @throws Exception
	 */
	public void deleteSet(String id) throws RemoteException,Exception;
}
