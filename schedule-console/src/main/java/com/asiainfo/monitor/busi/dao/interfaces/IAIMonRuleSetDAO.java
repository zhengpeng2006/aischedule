package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleSetValue;

public interface IAIMonRuleSetDAO {

	public IBOAIMonRuleSetValue[] queryRuleSet(String cond, Map para) throws Exception;
	
	public IBOAIMonRuleSetValue getRuleSetById(String ruleSetId) throws Exception;
	
	public IBOAIMonRuleSetValue[] getRuleSetByType(String ruleType, String ruleKind) throws Exception;
	/**
	 * 批量保存或修改规则集设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetValue[] values) throws Exception;

	/**
	 * 保存或修改规则集设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetValue value) throws Exception;
	
	/**
	 * 删除规则集
	 * @param ruleSetId
	 * @throws Exception
	 */
	public void deleteRuleSet(long ruleSetId) throws Exception;
	
}
