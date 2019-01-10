package com.asiainfo.deploy.dao.interfaces;

import java.util.Map;

import com.asiainfo.deploy.common.bo.BODeployStrategyBean;

/**
 * 部署策略数据库操作接口
 * @author 孙德东(24204)
 */
public interface IDeployStrategyDAO {
	/**
	 * 获取所有的部署策略
	 * @return
	 * @throws Exception 
	 */
	BODeployStrategyBean[] getAllDeployStrategy() throws Exception;
	/**
	 * 根据主键获取部署策略
	 * @param strategyId
	 * @return
	 * @throws Exception
	 */
	BODeployStrategyBean getStrategyById(long strategyId) throws Exception;
	
	/**
	 * 根据条件查询部署策略
	 * @param condition 查询条件（key为字段名）
	 * @return
	 * @throws Exception
	 */
	BODeployStrategyBean[] getStrategyByCondition(Map<String, Object> condition) throws Exception;
	
	/**
	 * 删除部署策略
	 * @param strategyId
	 * @throws Exception
	 */
	void deleteStrategy(long strategyId) throws Exception;
	
	/**
	 * 保存部署策略
	 * @param strategyId
	 * @throws Exception
	 */
	void saveStrategy(BODeployStrategyBean strategy) throws Exception;
}
