package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;

public interface IAIMonParamValuesSV {

	/**
	 * 根据参数标识，读取参数信息
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue getParamValuesById(String id) throws RemoteException,Exception;
	
	/**
	 * 根据类型，读取参数信息
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue[] getParamValuesByType(String type, String registeId) throws RemoteException,Exception;
	
	
	/**
	 * 批量保存或修改参数设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamValuesValue[] values) throws RemoteException,Exception;

	/**
	 * 保存或修改参数设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamValuesValue value) throws RemoteException,Exception;
	
	/**
	 * 删除参数
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteParamValues(long id) throws RemoteException,Exception ;

	/**
	 * 根据参数ID获取参数值信息
	 * @param paramId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue[] getParamValuesByParamId(long paramId) throws RemoteException,Exception ;

	/**
	 * delete param_values by registeType and registeId
	 * @param registeType
	 * @param registeId
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void deleteParamValues(int registeType, long registeId) throws RemoteException,Exception ;
}
