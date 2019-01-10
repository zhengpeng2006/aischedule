package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonColgRuleDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonColgRuleEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonColgRuleDAOImpl implements IAIMonColgRuleDAO {
	
	/**
	 * 根据条件，查询综合规则信息
	 * @param cond
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] queryRule(String cond,Map para) throws Exception{
		return BOAIMonColgRuleEngine.getBeans(cond,para);
	}
	
	/**
	 * 根据规则标识，读取综合规则信息
	 * @param colgRuleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue getColgRuleById(String colgRuleId) throws Exception {
		IBOAIMonColgRuleValue ruleValue=BOAIMonColgRuleEngine.getBean(Long.parseLong(colgRuleId));
		return ruleValue;
	}
	
	/**
	 * 根据应用服务ID，读取综合规则信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getColgRuleByAppId(String appId) throws Exception{
		StringBuilder condition=new StringBuilder("");	
		condition.append(IBOAIMonColgRuleValue.S_AppId).append("=:appId").append(" and state = 'U' ");
		Map parameter = new HashMap();
		parameter.put("appId", appId);
		return queryRule(condition.toString(), parameter);
	}
	
	/**
	 * 根据应用主机标识、服务标识、类型，读取综合规则信息
	 * @param hostId
	 * @param appId
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getColgRuleByHostIdAndAppIdAndRuleType(String hostId,String appId,String ruleType) throws Exception{
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		
		if (StringUtils.isNotBlank(hostId)) {
			condition.append(IBOAIMonColgRuleValue.S_HostId).append("=:hostId");
			parameter.put("hostId", hostId);
		}
		
		if (StringUtils.isNotBlank(appId)) {
			if (StringUtils.isNotBlank(condition.toString())) {
				condition.append(" and ");
			}
			condition.append(IBOAIMonColgRuleValue.S_AppId).append("=:appId");
			parameter.put("appId", appId);
		}
		if (StringUtils.isNotBlank(ruleType)) {
			if (StringUtils.isNotBlank(condition.toString())) {
				condition.append(" and ");
			}
			condition.append(IBOAIMonColgRuleValue.S_RuleType).append("= :ruleType");
			parameter.put("ruleType", ruleType);
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return queryRule(condition.toString(), parameter);
	}
	
	/**
	 * 根据应用服务ID、类型，读取综合规则信息
	 * @param appId
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getColgRuleByAppIdAndRuleType(String appId,String ruleType) throws Exception{
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(appId)) {
			condition.append(IBOAIMonColgRuleValue.S_AppId).append("=:appId");
			parameter.put("appId", appId);
		}
		if (StringUtils.isNotBlank(ruleType)) {
			if (StringUtils.isNotBlank(condition.toString())) {
				condition.append(" and ");
			}
			condition.append(IBOAIMonColgRuleValue.S_RuleType).append("= :ruleType");
			parameter.put("ruleType", ruleType);
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return queryRule(condition.toString(), parameter);
	}
	
	/**
	 * 根据规则类型读取默认规则
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getDefaultColgRuleByType(String ruleType) throws Exception{
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isBlank(ruleType))
			// 读取默认类型规则时,没有定义规则类型参数
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000213"));
		
		condition.append(IBOAIMonColgRuleValue.S_RuleType).append("= :ruleType");
		parameter.put("ruleType", ruleType);
		condition.append(" and ");
		condition.append(IBOAIMonColgRuleValue.S_RuleKind+"='DEFAULT'");
		condition.append(" and ");
		condition.append(" state = 'U' ");
		return queryRule(condition.toString(), parameter);
	}
	
	public IBOAIMonColgRuleValue[] getColgRuleByCond(String ruleName, String ruleType) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(ruleName)) {
			condition.append(IBOAIMonColgRuleValue.S_RuleName).append(" like :ruleName");
			parameter.put("ruleName", "%" + ruleName + "%");
		}
		if (StringUtils.isNotBlank(ruleType)) {
			if (StringUtils.isNotBlank(ruleName)) {
				condition.append(" and ");
			}
			condition.append(IBOAIMonColgRuleValue.S_RuleType).append(" =:ruleType");
			parameter.put("ruleType", ruleType);
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return queryRule(condition.toString(), parameter);
	}
	
	/**
	 * 批量保存或修改规则设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonColgRuleValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setRuleId(BOAIMonColgRuleEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate...");
		}
		BOAIMonColgRuleEngine.saveBatch(values);
	}

	/**
	 * 保存或修改规则设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonColgRuleValue value) throws Exception {
		if (value.isNew()) {
			value.setRuleId(BOAIMonColgRuleEngine.getNewId().longValue());
		}
		BOAIMonColgRuleEngine.save(value);
	}
	
	/**
	 * 删除规则
	 * @param colgRuleId
	 * @throws Exception
	 */
	public void deleteColgRule (long colgRuleId) throws Exception{
		IBOAIMonColgRuleValue value = BOAIMonColgRuleEngine.getBean(colgRuleId);
		if (null != value) {
//			value.delete();
			value.setState("E");
			BOAIMonColgRuleEngine.save(value);
		}
	}
}
