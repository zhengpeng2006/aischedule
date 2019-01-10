package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonRuleSetRelatDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonRuleSetRelatBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonRuleSetRelatEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleSetRelatValue;

public class AIMonRuleSetRelatDAOImpl implements IAIMonRuleSetRelatDAO{

	/**
	 * 根据条件，查询规则集关联规则信息
	 * @param cond
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleSetRelatValue[] queryRuleSetRelat(String cond,Map para) throws Exception{
		return BOAIMonRuleSetRelatEngine.getBeans(cond,para);
	}
	
	/**
	 * 根据规则集，获取规则集关联的规则信息
	 * @param ruleSetId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleSetRelatValue[] getRuleSetRelatByRuleSetId(String ruleSetId) throws Exception{
		StringBuilder condition=new StringBuilder("");	
		condition.append(BOAIMonRuleSetRelatBean.S_RulesetId).append(" = :ruleSetId ");
		Map parameter=new HashMap();
		parameter.put("ruleSetId",ruleSetId);
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return queryRuleSetRelat(condition.toString(),parameter);
	}
	
	/**
	 * 根据规则标识，读取规则集关联规则定义
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleSetRelatValue[] getRuleSetRelatByRuleId(String ruleId) throws Exception{
		StringBuilder condition=new StringBuilder("");	
		condition.append(BOAIMonRuleSetRelatBean.S_RuleId).append(" = :ruleId ");
		Map parameter=new HashMap();
		parameter.put("ruleId",ruleId);
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return queryRuleSetRelat(condition.toString(),parameter);
	}
	
	/**
	 * 批量保存或修改规则集关联的规则设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetRelatValue[] values) throws Exception {
		BOAIMonRuleSetRelatEngine.saveBatch(values);
	}

	/**
	 * 保存或修改规则集关联的规则设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetRelatValue value) throws Exception {
		BOAIMonRuleSetRelatEngine.save(value);
	}
}
