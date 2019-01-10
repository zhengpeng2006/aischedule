package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAppRuleSetValue;


public interface IAIMonAppRuleSetSV {
	
	/**
	 * 获取类型为type的规则集
	 * @param appId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAppRuleSetValue getAppRuleSet(String appId, String type) throws RemoteException,Exception;
	
	/**
	 * 根据应用服务器标识查询关联的规则集
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAppRuleSetValue[] getAppRuleSet(String appId) throws RemoteException,Exception;
	
	/**
	 * 添加、修改和删除
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAppRuleSetValue[] values) throws RemoteException,Exception;

	/**
	 * 添加、修改和删除
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAppRuleSetValue value) throws RemoteException,Exception;
}
