package com.asiainfo.monitor.busi.service.interfaces;


import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;

/**
 * 静态数据表数据查询服务
 * @author lisong
 *
 */
public interface IAIMonStaticDataSV {
	
	/**
	 * 通过自定义查询条件查询
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] getAllStaticDataBean() throws RemoteException,Exception;
	/**
	 * 通过codeType查询静态数据集
	 * @param codeType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] queryByCodeType(String codeType) throws RemoteException,Exception;
	
	/**
	 * 通过ExternCodeType查询静态数据集
	 * @param externCodeType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] queryByEct(String externCodeType) throws RemoteException,Exception;
	
	/**
	 * 通过CodeType和CodeValue查询静态数据
	 * @param codeType
	 * @param codeValue
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue queryByCodeTypeAndValue(String codeType, String codeValue) throws RemoteException,Exception;
	
	/**
	 * 根据codeType数组获取静态数据集
	 * 
	 * @param codeTypes
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] queryByCodeType(String[] codeTypes) throws RemoteException,Exception;
	
	/**
	 * 保存静态数据
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonStaticDataValue value) throws RemoteException,Exception;
	
	/**
	 * 批量保存静态数据
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonStaticDataValue[] values) throws RemoteException,Exception;
	
	/**
	 * 通过静态表查询配置的任务信息
	 * @param codeType（taskType）
	 * @param codeValue（taskCode）
	 * @return
	 */
	public IBOAIMonStaticDataValue[] qryTaskInfos(String codeType, String codeValue, String taskCode1) throws RemoteException,Exception;
}
