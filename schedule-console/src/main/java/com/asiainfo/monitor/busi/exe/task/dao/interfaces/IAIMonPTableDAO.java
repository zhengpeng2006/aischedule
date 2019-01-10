package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTableValue;

public interface IAIMonPTableDAO {

	/**
	 * 根据条件查询表数据监控配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTableValue[] query(String cond, Map param, Integer startNum, Integer endNum) throws Exception;
	
	/**
	 * 根据主键取得表数据监控配置信息
	 * 
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTableValue getBeanById(long tableId) throws Exception;
	
	/**
	 * 根据名称检索表数据监控配置信息
	 * 
	 * @param tableName
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTableValue[] getTableInfoByName(String tableName, Integer startNum, Integer endNum) throws Exception;
	
	public int getTableInfoCount(String tableName) throws Exception;
	
	/**
	 * 批量保存或修改表数据监控配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTableValue[] values) throws Exception;
	
	/**
	 * 保存或修改表数据监控配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTableValue value) throws Exception;
	
	/**
	 * 删除表数据监控配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long tableId) throws Exception;
}
