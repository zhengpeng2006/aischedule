package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWTriggerHisValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWTriggerValue;

public interface IAIMonWTriggerDAO {

	/**
	 * 根据条件查询告警记录信息
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue[] query(String condition, Map parameter) throws Exception;

	/**
	 * 根据告警标识查找告警记录
	 * @param trigger_Id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue getWTriggerBean(String trigger_Id)  throws Exception;
	
	/**
	 * 保存告警并处理之前的告警，将X分钟之前的同任务告警转成历史
	 * @param infoId
	 * @param second
	 * @param level
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue[] getMonWTriggerBefore(long infoId, int second, String level) throws Exception;
	
	/**
	 * 根据条件查询告警记录
	 * 
	 * @param condition
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerValue[] getMonWTriggerByCondition(String condition, Map parameter, int startIndex, int endIndex) throws Exception;
	
	/**
	 * 查询历史告警记录
	 * @param condition
	 * @param parameter
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWTriggerHisValue[] getMonWTriggerHisByCondition(String condition, Map parameter, int startIndex, int endIndex) throws Exception;
	
	/**
	 * 批量保存或修改警告记录
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWTriggerValue[] values) throws Exception;
	
	/**
	 * 保存或修改警告记录
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWTriggerValue value) throws Exception;
	
	/**
	 * 删除警告记录
	 * @param value
	 * @throws Exception
	 */
	public void deleteWTrigger(IBOAIMonWTriggerValue value) throws Exception;
	
	/**
	 * 批量删除警告记录
	 * @param value
	 * @throws Exception
	 */
	public void deleteWTrigger(IBOAIMonWTriggerValue[] values) throws Exception;
	/**
	 * 保存告警记录历史表
	 * @param value
	 * @throws Exception
	 */
	public void saveWTriggerHis(IBOAIMonWTriggerHisValue value) throws Exception;
	
	/**
	 * 批量保存告警记录历史表
	 * @param value
	 * @throws Exception
	 */
	public void saveWTriggerHis(IBOAIMonWTriggerHisValue[] values) throws Exception;
	
	/**
	 * 根据查询条件查询记录数量
	 * @param condition
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int count(String condition, Map params) throws Exception;
	
}
