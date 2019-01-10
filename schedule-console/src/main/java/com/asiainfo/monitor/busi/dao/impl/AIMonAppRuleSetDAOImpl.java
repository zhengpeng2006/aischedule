package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonAppRuleSetDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonAppRuleSetBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonAppRuleSetEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAppRuleSetValue;


public class AIMonAppRuleSetDAOImpl implements IAIMonAppRuleSetDAO{

	/**
	 * 根据条件，查询App 的规则集
	 * @param cond
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAppRuleSetValue[] queryAppRuleSet(String cond,Map para) throws Exception {
		return BOAIMonAppRuleSetEngine.getBeans(cond,para);
	}
	
	/**
	 * 根据应用服务器标识以及类型，获取关联的规则集
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAppRuleSetValue getAppRuleSetByAppId(String appId,String type) throws Exception {
		StringBuilder condition=new StringBuilder("");	
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(appId)){
			condition.append(BOAIMonAppRuleSetBean.S_AppId+ " = :appId ");
			parameter.put("appId",appId);
		}
		if (StringUtils.isNotBlank(type)){
			if (StringUtils.isNotBlank(condition.toString())){
				condition.append(" and ");
			}
			condition.append(BOAIMonAppRuleSetBean.S_AppRuleType+" = :appRuleType ");
			parameter.put("appRuleType",type);
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
			
		IBOAIMonAppRuleSetValue[] appRuleSetValues=queryAppRuleSet(condition.toString(),parameter);
		
		if (appRuleSetValues!=null && appRuleSetValues.length>0){			
			return appRuleSetValues[0];
		}else{
			return null;
		}
	}
	
	/**
	 * 根据应用服务器标识查询关联的规则集
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAppRuleSetValue[] getAppRuleSetByAppId(String appId) throws Exception {
		StringBuilder condition=new StringBuilder("");	
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(appId)){
			condition.append(BOAIMonAppRuleSetBean.S_AppId).append(" = :appId ");
			parameter.put("appId",appId);
		} 
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return queryAppRuleSet(condition.toString(),parameter);
	}
	
	public void saveOrUpdate(IBOAIMonAppRuleSetValue[] values) throws Exception {
		
		BOAIMonAppRuleSetEngine.saveBatch(values);
	}

	public void saveOrUpdate(IBOAIMonAppRuleSetValue value) throws Exception {

		BOAIMonAppRuleSetEngine.save(value);
	}
}