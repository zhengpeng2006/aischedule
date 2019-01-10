package com.asiainfo.deploy.api.interfaces;

import java.util.List;
import java.util.Map;

import com.ai.appframe2.common.AIException;
import com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelationBean;
import com.asiainfo.deploy.common.bo.BODeployNodeVersionBean;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;

/**
 * 节点部署策略的接口
 * @author 孙德东(24204)
 */
public interface IDeployStrategySVProvider 
{
	/**
	 * 获取系统中所有的部署策略
	 * @return Key:部署策略Id Value:部署策略Name
	 * @throws Exception 
	 */
	Map<Long, BODeployStrategyBean> getDeployStrategyList() throws Exception;
    
	/**
	 * 获取节点所配置的部署策略
	 * @param nodeId
	 * @return
	 * @throws Exception 
	 */
	BODeployStrategyBean getDeployStrategyByNodeId(long nodeId) throws Exception;

	/**
	 * 获取节点部署关系
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	BODeployNodeStrategyRelationBean getNodeStrategyRelByNodeId(long nodeId) throws Exception;
	/**
	 * 添加节点引用的部署策略
	 * @param nodeId  节点
	 * @param strategyId 节点所配置的部署策略id
	 * @throws AIException
	 * @throws Exception 
	 */
	void addNodeDeployStrategy(long nodeId, long strategyId) throws Exception;

	/**
	 * 修改节点引用的部署策略
	 * @throws Exception
	 */
	void modifyNodeDeployStrategy(long nodeId, long strategyId) throws Exception;
	/**
	 * 修改节点引用的部署策略
	 * @param relationBean
	 * @throws Exception
	 */
	void deleteNodeDeployStrategy(long nodeId) throws Exception;
	
	/**
     * 获取引用了某个部署策略的所有节点
     * @param strategyId
     * @return
     * @throws AIException 
     * @throws Exception 
     */
    BODeployNodeStrategyRelationBean[] getNodesByStrategyId(long strategyId) throws Exception;
    
    /**
	 * 根据节点的id获取节点的版本信息
	 * @param nodes
	 * @return
	 * @throws Exception 
	 */
	BODeployNodeVersionBean[] qryNodeVersionsByNodes(List<Long> nodes) throws Exception;
    
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
}
