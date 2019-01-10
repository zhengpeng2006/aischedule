package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;

public interface IAIMonColgRuleDAO {
	
	/**
	 * 根据条件，查询规则信息
	 * @param cond
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] queryRule(String cond, Map para) throws Exception;
	
	/**
	 * 根据应用服务ID，读取综合规则信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getColgRuleByAppId(String appId) throws Exception;
	
	/**
	 * 根据规则名和规则类型读取综合规则信息
	 * @param ruleName
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getColgRuleByCond(String ruleName, String ruleType) throws Exception;
	
	/**
	 * 根据应用主机标识、服务标识、类型，读取综合规则信息
	 * @param hostId
	 * @param appId
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getColgRuleByHostIdAndAppIdAndRuleType(String hostId, String appId, String ruleType) throws Exception;
	
	/**
	 * 根据应用标识和类型读取综合规则信息
	 * @param appId
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getColgRuleByAppIdAndRuleType(String appId, String ruleType) throws Exception;
	
	/**
	 * 根据规则类型读取默认规则
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getDefaultColgRuleByType(String ruleType) throws Exception;
	
	/**
	 * 根据规则标识，读取综合规则信息
	 * @param colgRuleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue getColgRuleById(String colgRuleId) throws Exception;
	
	/**
	 * 批量保存或修改规则设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonColgRuleValue[] values) throws Exception;

	/**
	 * 保存或修改规则设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonColgRuleValue value) throws Exception;
	
	/**
	 * 删除规则
	 * @param colgRuleId
	 * @throws Exception
	 */
	public void deleteColgRule(long colgRuleId) throws Exception;
}
