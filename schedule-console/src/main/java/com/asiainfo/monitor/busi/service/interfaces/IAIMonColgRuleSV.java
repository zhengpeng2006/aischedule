package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;


public interface IAIMonColgRuleSV {
	/**
	 * 根据规则标识，读取综合规则信息
	 * @param colgRuleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue getColgRuleById(String colgRuleId) throws RemoteException,Exception;
	
	/**
	 * 根据应用服务ID，读取综合规则信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getColgRuleByAppId(String appId) throws RemoteException,Exception;
	
	/**
	 * 根据应用标识和规则类型读取综合规则信息
	 * @param appId
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue getColgRuleByHostIdAndAppIdAndRuleType(String hostId, String appId, String ruleType) throws RemoteException,Exception;
	
	/**
	 * 根据规则名和规则类型读取综合规则信息
	 * @param ruleName
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getColgRuleByCond(String ruleName, String ruleType) throws RemoteException,Exception;
	
	/**
	 * 根据类型读取默认规则信息
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue getDefaultColgRuleByType(String ruleType) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改规则设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonColgRuleValue[] values) throws RemoteException,Exception;

	/**
	 * 保存或修改规则设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonColgRuleValue value) throws RemoteException,Exception;
	
	/**
	 * 删除规则
	 * @param colgRuleId
	 * @throws Exception
	 */
	public void deleteColgRule(long colgRuleId) throws RemoteException,Exception;
}
