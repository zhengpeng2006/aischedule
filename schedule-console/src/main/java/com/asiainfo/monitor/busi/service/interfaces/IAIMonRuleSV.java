package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleValue;


public interface IAIMonRuleSV {

	/**
	 * 根据规则标识，读取规则信息
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue getRuleById(String ruleId) throws RemoteException,Exception;
	
	/**
	 * 根据类型，读取规则信息
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue[] getRuleByType(String type, String kind) throws RemoteException,Exception;
	
	/**
	 * 根据条件，读取规则信息
	 * @param type
	 * @param kind
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue[] getRuleByCond(String type, String kind, String key) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改规则设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleValue[] values) throws RemoteException,Exception;

	/**
	 * 保存或修改规则设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleValue value) throws RemoteException,Exception;
	
	/**
	 * 删除规则
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteRule(long ruleId) throws RemoteException,Exception;
}
