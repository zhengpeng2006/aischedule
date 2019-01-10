package com.asiainfo.monitor.busi.dao.interfaces;


import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;

public interface IAIMonParamValuesDAO {

	/**
	 * 根据条件，查询参数信息
	 * @param cond
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue[] queryParamValues(String cond, Map para) throws Exception;
	
	/**
	 * 根据参数标识，获取参数信息
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue getParamValuesById(String id) throws Exception;
	
	/**
	 * 根据类型读取所有符合的参数记录
	 * @param type
	 * @param registeId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue[] getParamValuesByRegiste(String type, String registeId) throws Exception;
	
	/**
	 * 批量保存或修改参数设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamValuesValue[] values) throws Exception;

	/**
	 * 保存或修改参数设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamValuesValue value) throws Exception;
	
	/**
	 * 删除参数
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteParamValues(long id) throws Exception;

	/**
	 * @param paramId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue[] getParamValuesByParamId(long paramId) throws Exception;
	
}
