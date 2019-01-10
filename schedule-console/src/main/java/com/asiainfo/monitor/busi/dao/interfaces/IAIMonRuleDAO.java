package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleValue;

public interface IAIMonRuleDAO {

	/**
	 * 根据条件，查询规则信息
	 * @param cond
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue[] queryRule(String cond, Map para) throws Exception;
	
	/**
	 * 根据规则标识，获取规则信息
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue getRuleById(String ruleId) throws Exception;
	
	/**
	 * 根据类型读取所有符合的规则记录
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue[] getRuleByType(String type, String kind) throws Exception;
	
	/**
	 * 根据条件读取所有符合的规则记录
	 * @param type
	 * @param kind
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue[] getRuleByCond(String type, String kind, String key) throws Exception;
	
	/**
	 * 批量保存或修改规则设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleValue[] values) throws Exception;

	/**
	 * 保存或修改规则设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleValue value) throws Exception;
	
	/**
	 * 删除规则
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteRule(long ruleId) throws Exception;
	
}
