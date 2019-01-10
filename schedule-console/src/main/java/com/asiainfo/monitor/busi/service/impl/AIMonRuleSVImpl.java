package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonRuleDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonRuleSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleValue;

public class AIMonRuleSVImpl implements IAIMonRuleSV{

	/**
	 * 根据规则标识，读取规则信息
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue getRuleById(String ruleId) throws RemoteException,Exception{
		IAIMonRuleDAO ruleDAO=(IAIMonRuleDAO)ServiceFactory.getService(IAIMonRuleDAO.class);
		return ruleDAO.getRuleById(ruleId);
	}
	
	/**
	 * 根据类型，读取规则信息
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue[] getRuleByType(String type,String kind) throws RemoteException,Exception{
		IAIMonRuleDAO ruleDAO=(IAIMonRuleDAO)ServiceFactory.getService(IAIMonRuleDAO.class);
		return ruleDAO.getRuleByType(type,kind);
	}
	
	/**
	 * 根据条件，读取规则信息
	 * @param type
	 * @param kind
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleValue[] getRuleByCond(String type,String kind,String key) throws RemoteException,Exception{
		IAIMonRuleDAO ruleDAO=(IAIMonRuleDAO)ServiceFactory.getService(IAIMonRuleDAO.class);
		return ruleDAO.getRuleByCond(type,kind,key);
	}
	
	/**
	 * 批量保存或修改规则设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleValue[] values) throws RemoteException,Exception {
		IAIMonRuleDAO ruleDAO=(IAIMonRuleDAO)ServiceFactory.getService(IAIMonRuleDAO.class);
		ruleDAO.saveOrUpdate(values);
	}

	/**
	 * 保存或修改规则设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleValue value) throws RemoteException,Exception {
		IAIMonRuleDAO ruleDAO=(IAIMonRuleDAO)ServiceFactory.getService(IAIMonRuleDAO.class);
		ruleDAO.saveOrUpdate(value);
	}
	
	/**
	 * 删除规则
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteRule (long ruleId) throws RemoteException,Exception {
		IAIMonRuleDAO ruleDAO=(IAIMonRuleDAO)ServiceFactory.getService(IAIMonRuleDAO.class);
		ruleDAO.deleteRule(ruleId);
	}
}
