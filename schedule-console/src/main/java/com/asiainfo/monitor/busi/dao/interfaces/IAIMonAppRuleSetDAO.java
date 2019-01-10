package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAppRuleSetValue;

public interface IAIMonAppRuleSetDAO {

	/**
	 * 根据条件，查询App 的规则集
	 * @param cond
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAppRuleSetValue[] queryAppRuleSet(String cond, Map para) throws Exception;
	
	/**
	 * 根据应用服务器标识以及类型，获取关联的规则集
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAppRuleSetValue getAppRuleSetByAppId(String appId, String type) throws Exception;
	
	/**
	 * 根据应用服务器标识查询关联的规则集
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAppRuleSetValue[] getAppRuleSetByAppId(String appId) throws Exception;
	
	public void saveOrUpdate(IBOAIMonAppRuleSetValue[] values) throws Exception;

	public void saveOrUpdate(IBOAIMonAppRuleSetValue value) throws Exception ;
}
