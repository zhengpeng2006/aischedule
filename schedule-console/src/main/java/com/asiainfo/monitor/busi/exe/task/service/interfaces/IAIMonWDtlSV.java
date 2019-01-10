package com.asiainfo.monitor.busi.exe.task.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWDtlValue;

public interface IAIMonWDtlSV {

	/**
	 * 根据条件查询监控信息发送人员关联配置信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue[] getDtlByCond(String taskId, String personId, Integer startNum, Integer endNum) throws RemoteException,Exception;

	/**
	 * 根据条件取得总件数
	 * 
	 * @param taskId
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public int getDtlCount(String taskId, String personId) throws RemoteException,Exception;

	/**
	 * 根据主键取得监控信息发送人员关联配置信息
	 * 
	 * @param dtlId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue getBeanById(long dtlId) throws RemoteException,Exception;
	
	/**
	 * 根据任务标识读取任务警告发送人员信息
	 * @param taskId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue[] getDtlByTaskId(String taskId) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改监控信息发送人员关联配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWDtlValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存或修改监控信息发送人员关联配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWDtlValue value) throws RemoteException,Exception;
	
	/**
	 * 删除监控信息发送人员关联配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long dtlId) throws RemoteException,Exception;
}
