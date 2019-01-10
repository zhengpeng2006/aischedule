package com.asiainfo.monitor.busi.exe.task.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBAcctValue;

public interface IAIMonDBAcctSV {

	/**
	 * 根据条件查询数据源配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBAcctValue[] getDBAcctByCode(String acctCode) throws RemoteException,Exception;
	
	/**
	 * 根据主键取得数据源配置信息
	 * 
	 * @param acctCode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBAcctValue getBeanByCode(String acctCode) throws RemoteException,Exception;

	/**
	 * 批量保存或修改数据源配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBAcctValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存或修改数据源配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBAcctValue value) throws RemoteException,Exception;
	
	/**
	 * 删除数据源配置
	 * 
	 * @param acctCode
	 * @throws Exception
	 */
	public void delete(String acctCode) throws RemoteException,Exception;
	
	/**
	 * 检查数据源代码是否存在
	 * 
	 * @param acctCode
	 * @return
	 * @throws Exception
	 */
	public boolean checkCodeExists(String acctCode) throws RemoteException,Exception;
}
