package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoFilterValue;

public interface IAIMonPInfoFilterDAO {
	
	/**
	 * 根据主键取得任务过滤信息
	 * @param filterId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoFilterValue getFilterById(long filterId) throws Exception;
	
	/**
	 * 根据条件取得任务过滤信息
	 * @param condition
	 * @param parameter
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoFilterValue[] query(String condition, Map parameter, Integer startNum, Integer endNum) throws Exception;
	
	/**
	 * 根据条件取得任务过滤信息
	 * 
	 * @param expr
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoFilterValue[] getFilterByCond(String filterName, Integer startNum, Integer endNum) throws Exception;
	
	/**
	 * 取得总件数
	 * 
	 * @param expr
	 * @return
	 * @throws Exception
	 */
	public int getFilterCount(String filterName) throws Exception;

	/**
	 * 保存或修改任务过滤信息
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoFilterValue value) throws Exception;
	
	/**
	 * 批量保存或修改任务过滤信息
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoFilterValue[] values) throws Exception;
	
	/**
	 * 删除任务过滤信息
	 * @param filterId
	 * @throws Exception
	 */
	public void delete(long filterId) throws Exception;	
	
}
