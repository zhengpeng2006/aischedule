package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleSetRelatValue;

public interface IAIMonRuleSetRelatDAO {

	public IBOAIMonRuleSetRelatValue[] queryRuleSetRelat(String cond, Map para) throws Exception;
	
	public IBOAIMonRuleSetRelatValue[] getRuleSetRelatByRuleSetId(String ruleSetId) throws Exception;
	
	/**
	 * 根据规则标识，读取规则集关联规则定义
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleSetRelatValue[] getRuleSetRelatByRuleId(String ruleId) throws Exception;
	
	/**
	 * 批量保存或修改规则集关联的规则设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetRelatValue[] values) throws Exception;

	/**
	 * 保存或修改规则集关联的规则设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetRelatValue value) throws Exception;
}
