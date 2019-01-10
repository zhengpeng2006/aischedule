package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleSetValue;


public interface IAIMonRuleSetSV {

	public IBOAIMonRuleSetValue getRuleSetById(String ruleSetId) throws Exception;
	

	public IBOAIMonRuleSetValue[] getRuleSetByType(String ruleType, String ruleKind) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改规则集设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetValue[] values) throws RemoteException,Exception;

	/**
	 * 保存或修改规则集设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetValue value) throws RemoteException,Exception;
	
	/**
	 * 删除规则集
	 * @param ruleSetId
	 * @throws Exception
	 */
	public void deleteRuleSet(long ruleSetId) throws RemoteException,Exception;
}
