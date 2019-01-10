package com.asiainfo.monitor.busi.exe.task.service.interfaces;




import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWTriggerValue;

public interface IAIMonWTriggerSV {

	/**
	 * 根据条件查询告警记录
	 * 
	 * @param sqlCondition
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue[] getMonWTriggerByRecordId(String recordId, Integer startIndex, Integer endIndex) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改警告记录
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWTriggerValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存或修改警告记录
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWTriggerValue value) throws RemoteException,Exception;
	
	/**
	 * 保存告警并处理之前的告警，将X分钟之前的同任务告警转成历史
	 * @param values
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveAndProcess(IBOAIMonWTriggerValue[] values) throws RemoteException,Exception;
	
	/**
	 * 根据层类型，告警级别和起止日期查询结果数量
	 * @param layerType
	 * @param warnLevel
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public int count(String layerType, String warnLevel, String startDate, String endDate) throws RemoteException,Exception;
	
	/**
	 * 根据层类型，告警级别和起止日期查询结果
	 * @param layerType
	 * @param warnLevel
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue[] getAllByCond(String layerType, String warnLevel, String startDate, String endDate) throws RemoteException,Exception;
	
	/**
	 * 根据层类型，告警级别和起止日期查询结果(分页查询)
	 * @param layerType
	 * @param warnLevel
	 * @param startDate
	 * @param endDate
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue[] getAllByCond(String layerType, String warnLevel, String startDate, String endDate, int startIndex, int endIndex) throws RemoteException,Exception;

	public int countByInfoIdCond(String infoId, String warnLevel, String startDate, String endDate) throws RemoteException,Exception;
	
	public IBOAIMonWTriggerValue[] getTriggerValuesByInfoIdCond(String infoId, String warnLevel, String startDate,
                                                                String endDate, Integer startIndex, Integer endIndex) throws RemoteException,Exception;
	
	/**
	 * 修复告警记录,将告警记录转移到历史表，历史表中失效日期为修复日期，状态为F
	 * @param triggerId
	 * @param remarks
	 * @throws Exception
	 */
	public void repairWTrigger(Object[] triggerId, String remarks) throws RemoteException,Exception;
}
