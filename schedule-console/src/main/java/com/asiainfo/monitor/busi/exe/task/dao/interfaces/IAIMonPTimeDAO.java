package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTimeValue;

public interface IAIMonPTimeDAO {

	/**
	 * 根据条件查询监控时间段配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTimeValue[] query(String cond, Map param, Integer startNum, Integer endNum) throws Exception;
	
	/**
	 * 根据主键取得监控时间段配置信息
	 * 
	 * @param timeId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTimeValue getBeanById(long timeId) throws Exception;
	
	/**
	 * 根据条件取得信息
	 * 
	 * @param expr
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTimeValue[] getTimeInfoByExpr(String expr, Integer startNum, Integer endNum) throws Exception;
	
	/**
	 * 根据条件取得总件数
	 * 
	 * @param expr
	 * @return
	 * @throws Exception
	 */
	public int getTimeInfoCount(String expr) throws Exception;
	
	/**
	 * 批量保存或修改监控时间段配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTimeValue[] values) throws Exception;
	
	/**
	 * 保存或修改监控时间段配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTimeValue value) throws Exception;
	
	/**
	 * 删除监控时间段配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long timeId) throws Exception;
}
