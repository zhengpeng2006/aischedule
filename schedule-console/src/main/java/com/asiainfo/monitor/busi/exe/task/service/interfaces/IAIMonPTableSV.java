package com.asiainfo.monitor.busi.exe.task.service.interfaces;

import java.rmi.RemoteException;

import com.ai.appframe2.bo.DataContainer;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTableValue;
import com.asiainfo.monitor.busi.exe.task.model.TaskTableContainer;

public interface IAIMonPTableSV {

	/**
	 * 根据条件查询表数据监控配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTableValue[] getTableInfoByName(String tableName, Integer startNum, Integer endNum) throws RemoteException,Exception;
	
	public int getTableInfoCount(String tableName) throws RemoteException,Exception;

	/**
	 * 根据主键取得表数据监控配置信息
	 * 
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTableValue getBeanById(long tableId) throws RemoteException,Exception;

	/**
	 * 批量保存或修改表数据监控配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTableValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存或修改表数据监控配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTableValue value) throws RemoteException,Exception;
	
	/**
	 * 保存表监控配置（向导模式）
	 * @param model
	 * @throws Exception
	 */
	public void savePTableByGuide(TaskTableContainer model) throws RemoteException,Exception;
	
	/**
	 * 保存表监控配置（向导模式）
	 * @param model
	 * @throws Exception
	 */
	public void savePTableByGuide(TaskTableContainer tableModel, DataContainer[] paramDcs) throws RemoteException,Exception;
	
	/**
	 * 删除表数据监控配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long tableId) throws RemoteException,Exception;

}
