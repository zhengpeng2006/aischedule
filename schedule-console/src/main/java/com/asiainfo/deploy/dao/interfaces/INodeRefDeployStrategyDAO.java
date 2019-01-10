package com.asiainfo.deploy.dao.interfaces;

import java.util.List;

import com.ai.appframe2.common.AIException;
import com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelationBean;

/**
 * 节点引用部署策略的数据库操作接口
 * @author 孙德东(24204)
 */
public interface INodeRefDeployStrategyDAO {
	/**
	 * 查询节点引用的部署策略id
	 * @param nodeId
	 * @return
	 * @throws Exception 
	 */
    long getStrategyIdByNodeId(long nodeId) throws Exception;
    
    /**
     * 获取引用了某个部署策略的所有节点
     * @param strategyId
     * @return
     * @throws AIException 
     * @throws Exception 
     */
    BODeployNodeStrategyRelationBean[] getNodesByStrategyId(long strategyId) throws Exception;
    
    /**
     * 获取各个节点的部署策略
     * @param nodeList
     * @return
     * @throws Exception
     */
    BODeployNodeStrategyRelationBean[] getStrategysByNodes(List<Long> nodeList) throws Exception;
}
