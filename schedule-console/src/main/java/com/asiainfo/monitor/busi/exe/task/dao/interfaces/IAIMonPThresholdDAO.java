package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonThresholdValue;

public interface IAIMonPThresholdDAO {

	/**
	 * 根据条件查询进程阀值配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonThresholdValue[] query(String cond, Map param, Integer startNum, Integer endNum) throws Exception;
	
	/**
	 * 根据主键取得进程阀值配置信息
	 * 
	 * @param thresholdId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonThresholdValue getBeanById(long thresholdId) throws Exception;
	
	/**
	 * 根据表达式检索进程阀值配置信息
	 * 
	 * @param expr
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonThresholdValue[] getThresholdByExpr(String expr, Integer startNum, Integer endNum) throws Exception;
	
	/**
	 * 取得总件数
	 * 
	 * @param expr
	 * @return
	 * @throws Exception
	 */
	public int getThresholdCount(String expr) throws Exception;
	
	/**
	 * 批量保存或修改进程阀值配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonThresholdValue[] values) throws Exception;
	
	/**
	 * 保存或修改进程阀值配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonThresholdValue value) throws Exception;
	
	/**
	 * 删除进程阀值配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long thresholdId) throws Exception;
}
