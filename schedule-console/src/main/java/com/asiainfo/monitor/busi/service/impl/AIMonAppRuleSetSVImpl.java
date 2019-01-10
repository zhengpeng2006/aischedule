package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonAppRuleSetDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonAppRuleSetSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAppRuleSetValue;

public class AIMonAppRuleSetSVImpl implements IAIMonAppRuleSetSV{

	/**
	 * 获取类型为type的规则集
	 * @param appId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAppRuleSetValue getAppRuleSet(String appId,String type) throws RemoteException,Exception{
		IAIMonAppRuleSetDAO appRuleSetDAO=(IAIMonAppRuleSetDAO)ServiceFactory.getService(IAIMonAppRuleSetDAO.class);
		return appRuleSetDAO.getAppRuleSetByAppId(appId,type);
	}
	
	/**
	 * 根据应用服务器标识查询关联的规则集
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAppRuleSetValue[] getAppRuleSet(String appId) throws RemoteException,Exception{
		IAIMonAppRuleSetDAO appRuleSetDAO=(IAIMonAppRuleSetDAO)ServiceFactory.getService(IAIMonAppRuleSetDAO.class);
		return appRuleSetDAO.getAppRuleSetByAppId(appId);
	}
	
	/**
	 * 添加、修改和删除
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAppRuleSetValue[] values) throws RemoteException,Exception {
		IAIMonAppRuleSetDAO appRuleSetDAO=(IAIMonAppRuleSetDAO)ServiceFactory.getService(IAIMonAppRuleSetDAO.class);
		appRuleSetDAO.saveOrUpdate(values);
	}

	/**
	 * 添加、修改和删除
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAppRuleSetValue value) throws RemoteException,Exception {
		IAIMonAppRuleSetDAO appRuleSetDAO=(IAIMonAppRuleSetDAO)ServiceFactory.getService(IAIMonAppRuleSetDAO.class);
		appRuleSetDAO.saveOrUpdate(value);
	}
}
