package com.asiainfo.monitor.busi.dao.interfaces;


import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamDefineValue;

public interface IAIMonParamDefineDAO {

	/**
	 * 根据条件，查询参数信息
	 * @param cond
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamDefineValue[] queryParamDefine(String cond, Map para) throws Exception;
	
	/**
	 * 根据参数标识，获取参数信息
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamDefineValue getParamDefineById(String id) throws Exception;
	
	/**
	 * 根据类型读取所有符合的参数记录
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamDefineValue[] getParamDefineByRegiste(String type, String registeId) throws Exception;
	
	/**
	 * 批量保存或修改参数设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamDefineValue[] values) throws Exception;

	/**
	 * 保存或修改参数设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamDefineValue value) throws Exception;
	
	/**
	 * 删除参数
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteParamDefine(long id) throws Exception;
}
