package com.asiainfo.monitor.busi.exe.task.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;

public interface IAIMonPInfoGroupAtomicSV {

	/**
	 * 根据标识读取归属区域代码再分组信息
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue getMonPInfoGroupById(long groupId) throws RemoteException,Exception;
	
	/**
	 * 获取所有分组信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue[] getAllMonPInfoGroupBean() throws RemoteException,Exception;
	
	/**
	 * 修改或保存所属区域再分组信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoGroupValue value) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改所属区域再分组信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoGroupValue[] values) throws RemoteException,Exception;
	
	/**
	 * 删除所属区域再分组信息
	 * 
	 * @param areaId
	 * @throws Exception
	 */
	public void delete(long groupId) throws RemoteException,Exception;

	public IBOAIMonPInfoGroupValue[] getPInfoGroupByCodeAndName(String groupCode, String groupName, String layer, Integer startNum, Integer endNum) throws RemoteException,Exception;

	public int getPInfoGroupCount(String groupCode, String groupName, String layer) throws RemoteException,Exception;
}
