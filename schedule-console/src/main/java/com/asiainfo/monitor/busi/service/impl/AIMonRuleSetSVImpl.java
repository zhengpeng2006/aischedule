package com.asiainfo.monitor.busi.service.impl;


import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonRuleSetDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonRuleSetSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonRuleSetValue;

public class AIMonRuleSetSVImpl implements IAIMonRuleSetSV{

	/**
	 * 根据规则集标识，读取规则集定义
	 * @param ruleSetId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonRuleSetValue getRuleSetById(String ruleSetId) throws RemoteException,Exception {
		IAIMonRuleSetDAO ruleSetDAO=(IAIMonRuleSetDAO)ServiceFactory.getService(IAIMonRuleSetDAO.class);
		return ruleSetDAO.getRuleSetById(ruleSetId);
	}
	
	/**
	 * 根据类型读取规则集信
	 */
	public IBOAIMonRuleSetValue[] getRuleSetByType(String ruleType,String ruleKind) throws RemoteException,Exception {
		IAIMonRuleSetDAO ruleSetDAO=(IAIMonRuleSetDAO)ServiceFactory.getService(IAIMonRuleSetDAO.class);
		return ruleSetDAO.getRuleSetByType(ruleType,ruleKind);
	}
	/**
	 * 批量保存或修改规则集设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetValue[] values) throws RemoteException,Exception {
		IAIMonRuleSetDAO ruleSetDAO=(IAIMonRuleSetDAO)ServiceFactory.getService(IAIMonRuleSetDAO.class);
		ruleSetDAO.saveOrUpdate(values);		
	}

	/**
	 * 保存或修改规则集设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonRuleSetValue value) throws RemoteException,Exception {
		IAIMonRuleSetDAO ruleSetDAO=(IAIMonRuleSetDAO)ServiceFactory.getService(IAIMonRuleSetDAO.class);
		ruleSetDAO.saveOrUpdate(value);
	}
	
	/**
	 * 删除规则集
	 * @param ruleSetId
	 * @throws Exception
	 */
	public void deleteRuleSet (long ruleSetId) throws RemoteException,Exception {
		IAIMonRuleSetDAO ruleSetDAO=(IAIMonRuleSetDAO)ServiceFactory.getService(IAIMonRuleSetDAO.class);
		ruleSetDAO.deleteRuleSet(ruleSetId);
	}
}
