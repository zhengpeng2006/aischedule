package com.asiainfo.monitor.busi.exe.task.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonThresholdValue;
import com.asiainfo.monitor.tools.model.ThresholdModel;

public interface IAIMonPThresholdSV {

	/**
	 * 根据条件查询进程阀值配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonThresholdValue[] getThresholdByExpr(String expr, Integer startNum, Integer endNum) throws RemoteException,Exception;
	
	/**
	 * 取得总件数
	 * 
	 * @param expr
	 * @return
	 * @throws Exception
	 */
	public int getThresholdCount(String expr) throws RemoteException,Exception;

	/**
	 * 根据主键取得进程阀值配置信息
	 * 
	 * @param thresholdId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonThresholdValue getBeanById(long thresholdId) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改进程阀值配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonThresholdValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存或修改进程阀值配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonThresholdValue value) throws RemoteException,Exception;
	
	/**
	 * 保存进程阀值配置(向导模式)
	 * @param model
	 * @throws Exception
	 */
	public void savePThresholdByGuide(ThresholdModel model) throws RemoteException,Exception;
	
	/**
	 * 删除进程阀值配置(若有关联的任务则报内容为FAIL的异常)
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long thresholdId) throws RemoteException,Exception;
	
	/**
     * 根据条件查询进程阀值配置
     * 
     * @param cond
     * @param param
     * @return
     * @throws Exception
     */
	public IBOAIMonThresholdValue[] getThresholdByExprAndName(String expr, String name, Integer startNum, Integer endNum) throws RemoteException,Exception;
}
