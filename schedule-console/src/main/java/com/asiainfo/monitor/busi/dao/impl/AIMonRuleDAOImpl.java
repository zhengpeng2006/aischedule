package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonRuleDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonRuleEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleValue;

public class AIMonRuleDAOImpl implements IAIMonRuleDAO{

	/**
	 * 根据条件，查询规则信息
	 * @param cond
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue[] queryRule(String cond,Map para) throws Exception{
		return BOAIMonRuleEngine.getBeans(cond,para);
	}
	
	/**
	 * 根据规则标识，获取规则信息
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue getRuleById(String ruleId) throws Exception {
		
		IBOAIMonRuleValue ruleValue=BOAIMonRuleEngine.getBean(Long.parseLong(ruleId));
		return ruleValue;
	}
	
	/**
	 * 根据类型读取所有符合的规则记录
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue[] getRuleByType(String type,String kind) throws Exception{
		StringBuilder condition=new StringBuilder("");		
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(type)){
			condition.append(IBOAIMonRuleValue.S_RuleType).append("= :type");
			parameter.put("type",type);
		}
		if (StringUtils.isNotBlank(kind)){
			if (StringUtils.isNotBlank(condition.toString())){
				condition.append(" and ");
			}
			condition.append(IBOAIMonRuleValue.S_RuleKind).append(" = :kind");
			parameter.put("kind",kind);
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return queryRule(condition.toString(),parameter);
	}
	
	/**
	 * 根据条件读取所有符合的规则记录
	 * @param type
	 * @param kind
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue[] getRuleByCond(String type,String kind,String key) throws Exception{
		StringBuilder condition=new StringBuilder("");	
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(type)){
			condition.append(IBOAIMonRuleValue.S_RuleType).append("= :type");
			parameter.put("type",type);
		}
		if (StringUtils.isNotBlank(kind)){
			if (StringUtils.isNotBlank(condition.toString())){
				condition.append(" and ");
			}
			condition.append(IBOAIMonRuleValue.S_RuleKind).append(" = :kind");
			parameter.put("kind",kind);
		}
		if (StringUtils.isNotBlank(key)){
			if (StringUtils.isNotBlank(condition.toString())){
				condition.append(" and ");
			}
			condition.append(IBOAIMonRuleValue.S_RuleKey).append(" = :key");
			parameter.put("key",key);
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return queryRule(condition.toString(),parameter);
	}
	
	/**
	 * 批量保存或修改规则设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setRuleId(BOAIMonRuleEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate...");
		}
		BOAIMonRuleEngine.saveBatch(values);
	}

	/**
	 * 保存或修改规则设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleValue value) throws Exception {
		if (value.isNew()) {
			value.setRuleId(BOAIMonRuleEngine.getNewId().longValue());
		}
		BOAIMonRuleEngine.save(value);
	}
	
	/**
	 * 删除规则
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteRule (long ruleId) throws Exception{
		IBOAIMonRuleValue value = BOAIMonRuleEngine.getBean(ruleId);
		if (null != value) {
//			value.delete();
			value.setState("E");
			BOAIMonRuleEngine.save(value);
		}
	}
}
