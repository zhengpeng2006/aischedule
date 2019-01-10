package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamDefineValue;

public interface IAIMonParamDefineSV {

	/**
	 * 根据参数标识，读取参数信息
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamDefineValue getParamDefineById(String id) throws RemoteException,Exception;
	
	/**
	 * 根据类型，读取参数信息
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamDefineValue[] getParamDefineByType(String type, String registeId) throws RemoteException,Exception;
	
	
	/**
	 * 批量保存或修改参数设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamDefineValue[] values) throws RemoteException,Exception;

	/**
	 * 保存或修改参数设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamDefineValue value) throws RemoteException,Exception;
	
	/**
	 * 删除参数
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteParamDefine(long id) throws RemoteException,Exception;

	/**
	 * 根据寄主类型以及标识删除参数定义
	 * @param registeType
	 * @param registeId
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void deleteParamDefineByRegisteType(int registeType, long registeId) throws RemoteException,Exception;
	
	/**
	 * 根据参数定义ID删除参数定义及参数值
	 * 
	 * @param paramId
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void deleteParam(long paramId) throws RemoteException,Exception;
}
