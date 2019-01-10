package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWPersonValue;

public interface IAIMonWPersonDAO {

	/**
	 * 根据条件查询告警短信发送人员配置信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWPersonValue[] query(String cond, Map param, Integer startNum, Integer endNum) throws Exception;
	
	/**
	 * 根据主键取得告警短信发送人员配置信息
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWPersonValue getBeanById(long personId) throws Exception;
	
	/**
	 * 根据条件取得告警短信发送人员配置信息
	 * 
	 * @param personName
	 * @param region
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWPersonValue[] getPersonByCond(String personName, String region, Integer startNum, Integer endNum) throws Exception;
	
	/**
	 * 根据条件取得总件数
	 * 
	 * @param personName
	 * @param region
	 * @return
	 * @throws Exception
	 */
	public int getPersonCount(String personName, String region) throws Exception;
	
	/**
	 * 批量保存或修改告警短信发送人员配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWPersonValue[] values) throws Exception;
	
	/**
	 * 保存或修改告警短信发送人员配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWPersonValue value) throws Exception;
	
	/**
	 * 删除告警短信发送人员配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long personId) throws Exception;
}
