package com.asiainfo.deploy.dao.impl;

import com.asiainfo.deploy.common.bo.BODeployStrategyEngine;
import com.asiainfo.deploy.dao.interfaces.IStrategyRefAppTemplateDAO;

/**
 * 部署策略引用的应用启停模板的数据库操作类
 * 
 * @author 孙德东(24204)
 */
public class StrategyRefAppTemplateDAOImpl implements IStrategyRefAppTemplateDAO {

	@Override
	public long getTemplateIdByStrategyId(long strategyId) throws Exception {
		return BODeployStrategyEngine.getBean(strategyId).getTemplateId();
	}

	@Override
	public long getStopTemplateIdByStrategyId(long strategyId) throws Exception {
		return BODeployStrategyEngine.getBean(strategyId).getStopTemplateId();
	}

}
