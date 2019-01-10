package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWDtlValue;

public interface IAIMonWDtlDAO {

	/**
	 * 根据条件查询监控信息发送人员关联配置信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue[] query(String cond, Map param, Integer startNum, Integer endNum) throws Exception;

	/**
	 * 根据主键取得监控信息发送人员关联配置信息
	 * 
	 * @param dtlId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue getBeanById(long dtlId) throws Exception;
	
	/**
	 * 根据条件查询监控信息发送人员关联配置信息
	 * 
	 * @param taskId
	 * @param personId
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue[] getDtlByCond(String taskId, String personId, Integer startNum, Integer endNum) throws Exception;
	
	/**
	 * 根据任务读取任务警告发送人员信息
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue[] getDtlByTaskId(String taskId) throws Exception;
	
	/**
	 * 根据条件取得总件数
	 * 
	 * @param taskId
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public int getDtlCount(String taskId, String personId) throws Exception;
	
	/**
	 * 批量保存或修改监控信息发送人员关联配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWDtlValue[] values) throws Exception;
	
	/**
	 * 保存或修改监控信息发送人员关联配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWDtlValue value) throws Exception;
	
	/**
	 * 删除监控信息发送人员关联配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long dtlId) throws Exception;
}
