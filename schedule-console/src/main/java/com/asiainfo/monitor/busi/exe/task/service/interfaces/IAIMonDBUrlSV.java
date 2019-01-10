package com.asiainfo.monitor.busi.exe.task.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBURLValue;

public interface IAIMonDBUrlSV {

	/**
	 * 根据条件查询数据库节点URL配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBURLValue[] getDBUrlByName(String urlName) throws RemoteException,Exception;
	
	/**
	 * 根据主键取得数据库节点URL配置信息
	 * 
	 * @param urlName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBURLValue getBeanByName(String urlName) throws RemoteException,Exception;

	/**
	 * 批量保存或修改数据库节点URL配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBURLValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存或修改数据库节点URL配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBURLValue value) throws RemoteException,Exception;
	
	/**
	 * 删除数据库节点URL配置
	 * 
	 * @param urlName
	 * @throws Exception
	 */
	public void delete(String urlName) throws RemoteException,Exception;
	
	/**
	 * 检查节点URL名称是否存在
	 * 
	 * @param urlName
	 * @return
	 * @throws Exception
	 */
	public boolean checkUrlNameExists(String urlName) throws RemoteException,Exception;
}
