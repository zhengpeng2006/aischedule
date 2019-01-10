package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPExecValue;

public interface IAIMonPExecDAO {

	/**
	 * 根据条件查询进程监控配置
	 * 
	 * @param name
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPExecValue[] query(String cond, Map param, Integer startNum, Integer endNum) throws Exception;

	/**
	 * 根据主键取得进程监控配置信息
	 * 
	 * @param execId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPExecValue getBeanById(long execId)throws Exception;
	
	public IBOAIMonPExecValue[] getExecByName(String execName, Integer startNum, Integer endNum) throws Exception;
	
	public int getExecCount(String execName) throws Exception;
	
	/**
	 * 批量保存或修改进程监控配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPExecValue[] values) throws Exception;
	
	/**
	 * 保存或修改进程监控配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public long saveOrUpdate(IBOAIMonPExecValue value) throws Exception;
	
	/**
	 * 删除进程监控配置
	 * 
	 * @param execId
	 * @throws Exception
	 */
	public void delete(long execId) throws Exception;
}
