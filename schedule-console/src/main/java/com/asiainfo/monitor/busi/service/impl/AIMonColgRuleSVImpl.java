package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonColgRuleDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonColgRuleSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgRuleValue;

public class AIMonColgRuleSVImpl implements IAIMonColgRuleSV {

	/**
	 * 根据规则标识，读取综合规则信息
	 * @param colgRuleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue getColgRuleById(String colgRuleId) throws RemoteException,Exception{
		IAIMonColgRuleDAO dao=(IAIMonColgRuleDAO)ServiceFactory.getService(IAIMonColgRuleDAO.class);
		return dao.getColgRuleById(colgRuleId);
	}
	
	/**
	 * 根据应用服务ID，读取综合规则信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue[] getColgRuleByAppId(String appId) throws RemoteException,Exception{
		IAIMonColgRuleDAO dao=(IAIMonColgRuleDAO)ServiceFactory.getService(IAIMonColgRuleDAO.class);
		return dao.getColgRuleByAppId(appId);
	}
	
	/**
	 * 根据规则名和规则类型读取综合规则信息
	 * @param ruleName
	 * @param ruleType
	 * @return
	 */
	public IBOAIMonColgRuleValue[] getColgRuleByCond(String ruleName, String ruleType) throws RemoteException,Exception {
		IAIMonColgRuleDAO dao=(IAIMonColgRuleDAO)ServiceFactory.getService(IAIMonColgRuleDAO.class);
		return dao.getColgRuleByCond(ruleName, ruleType);
	}
	
	/**
	 * 根据类型读取默认规则信息
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue getDefaultColgRuleByType(String ruleType) throws RemoteException,Exception{
//		IAIMonColgRuleDAO dao=(IAIMonColgRuleDAO)ServiceFactory.getService(IAIMonColgRuleDAO.class);
//		IBOAIMonColgRuleValue[] colgRuleValues= dao.getDefaultColgRuleByType(ruleType);
//		if (colgRuleValues!=null && colgRuleValues.length>0){
//			return colgRuleValues[0];
//		}else{
			return null;
        //		}
	}
	/**
	 *  根据应用服务ID、综合类型，读取综合规则信息
	 * @param appId
	 * @param ruleType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgRuleValue getColgRuleByHostIdAndAppIdAndRuleType(String hostId,String appId,String ruleType) throws RemoteException,Exception{
		IAIMonColgRuleDAO dao=(IAIMonColgRuleDAO)ServiceFactory.getService(IAIMonColgRuleDAO.class);
		IBOAIMonColgRuleValue[] colgRuleValues=dao.getColgRuleByHostIdAndAppIdAndRuleType(hostId,appId,ruleType);
		if (colgRuleValues!=null && colgRuleValues.length>0){
			return colgRuleValues[0];
		}else{
			return null;
		}
	}
	/**
	 * 批量保存或修改规则设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonColgRuleValue[] values) throws RemoteException,Exception {
		IAIMonColgRuleDAO dao=(IAIMonColgRuleDAO)ServiceFactory.getService(IAIMonColgRuleDAO.class);
		dao.saveOrUpdate(values);
	}

	/**
	 * 保存或修改规则设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonColgRuleValue value) throws RemoteException,Exception {
		IAIMonColgRuleDAO dao=(IAIMonColgRuleDAO)ServiceFactory.getService(IAIMonColgRuleDAO.class);
		dao.saveOrUpdate(value);
	}
	
	/**
	 * 删除规则
	 * @param colgRuleId
	 * @throws Exception
	 */
	public void deleteColgRule (long colgRuleId) throws RemoteException,Exception {
		IAIMonColgRuleDAO dao=(IAIMonColgRuleDAO)ServiceFactory.getService(IAIMonColgRuleDAO.class);
		dao.deleteColgRule(colgRuleId);
	}
	
}
