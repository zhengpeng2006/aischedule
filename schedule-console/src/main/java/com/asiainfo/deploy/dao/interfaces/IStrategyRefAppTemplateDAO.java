package com.asiainfo.deploy.dao.interfaces;

/**
 * 部署策略引用的应用启停模板的数据库操作接口
 * 
 * @author 孙德东(24204)
 */
public interface IStrategyRefAppTemplateDAO {
	long getTemplateIdByStrategyId(long strategyId) throws Exception;

	long getStopTemplateIdByStrategyId(long strategyId) throws Exception;
}
