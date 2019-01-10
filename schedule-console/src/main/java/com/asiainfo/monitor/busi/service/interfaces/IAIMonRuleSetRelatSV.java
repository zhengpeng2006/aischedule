package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleSetRelatValue;


public interface IAIMonRuleSetRelatSV {

	public IBOAIMonRuleSetRelatValue[] getRuleSetRelatByRuleSetId(String ruleSetId) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改规则集关联的规则设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetRelatValue[] values) throws RemoteException,Exception;
	
	/**
	 * 根据规则标识，读取规则集关联规则定义
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleSetRelatValue[] getRuleSetRelatByRuleId(String ruleId) throws RemoteException,Exception;

	/**
	 * 保存或修改规则集关联的规则设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetRelatValue value) throws RemoteException,Exception;
	
	public void saveRelatData(String ruleSetId, IBOAIMonRuleSetRelatValue[] values) throws RemoteException,Exception;
}
