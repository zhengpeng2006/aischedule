package com.asiainfo.monitor.busi.dao.impl;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonRuleSetDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonRuleSetEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleSetValue;

public class AIMonRuleSetDAOImpl implements IAIMonRuleSetDAO{

	/**
	 * 根据条件，查询规则集
	 * @param cond
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleSetValue[] queryRuleSet(String cond,Map para) throws Exception{
		return BOAIMonRuleSetEngine.getBeans(cond,para);
	}
	
	/**
	 * 根据规则集标识，获取规则集信息
	 * @param ruleSetId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleSetValue getRuleSetById(String ruleSetId) throws Exception {
		IBOAIMonRuleSetValue ruleSetValue=BOAIMonRuleSetEngine.getBean(Long.parseLong(ruleSetId));
		return ruleSetValue;
		
	}
	
	/**
	 * 根据类型，获取规则集信息
	 * @param ruleType
	 * @param ruleKind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleSetValue[] getRuleSetByType(String ruleType,String ruleKind) throws Exception {
		StringBuilder condition=new StringBuilder("");	
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(ruleType)){
			condition.append(IBOAIMonRuleSetValue.S_RulesetType).append("= :ruleType");
			parameter.put("ruleType",ruleType);
		}
		if (StringUtils.isNotBlank(ruleKind)){
			if (StringUtils.isNotBlank(condition.toString())){
				condition.append(" and ");
			}
			condition.append(IBOAIMonRuleSetValue.S_RulesetKind).append(" = :ruleKind");
			parameter.put("ruleKind",ruleKind);
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return queryRuleSet(condition.toString(),parameter);
	}
	
	/**
	 * 批量保存或修改规则集设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setRulesetId(BOAIMonRuleSetEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate...");
		}
		BOAIMonRuleSetEngine.saveBatch(values);
	}

	/**
	 * 保存或修改规则集设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetValue value) throws Exception {
		if (value.isNew()) {
			value.setRulesetId(BOAIMonRuleSetEngine.getNewId().longValue());
		}
		BOAIMonRuleSetEngine.save(value);
	}
	
	/**
	 * 删除规则集
	 * @param ruleSetId
	 * @throws Exception
	 */
	public void deleteRuleSet (long ruleSetId) throws Exception{
		IBOAIMonRuleSetValue value = BOAIMonRuleSetEngine.getBean(ruleSetId);
		if (null != value) {
//			value.delete();
			value.setState("E");
			BOAIMonRuleSetEngine.save(value);
		}
	}
}
