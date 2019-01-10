package com.asiainfo.deploy.api.external;

import java.util.List;
import java.util.Map;

import com.ai.appframe2.common.AIException;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.api.interfaces.IDeployStrategySVProvider;
import com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelationBean;
import com.asiainfo.deploy.common.bo.BODeployNodeVersionBean;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;


/**
 * 部署策略对外接口
 * @author 孙德东(24204)
 */
public class DeployStrategyUtils {
	/**
	 * 获取系统中所有的部署策略
	 * @return Key:部署策略Id Value:部署策略Name
	 * @throws Exception 
	 */
	public static Map<Long, BODeployStrategyBean> getDeployStrategyList() throws Exception {
		return ((IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class)).getDeployStrategyList();
	}
    
	/**
	 * 获取节点所配置的部署策略
	 * @param nodeId
	 * @return
	 * @throws Exception 
	 */
	public static BODeployStrategyBean getDeployStrategyByNodeId(long nodeId) throws Exception {
		return ((IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class)).getDeployStrategyByNodeId(nodeId);
	}

	/**
	 * 获取节点部署关系
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public static BODeployNodeStrategyRelationBean getNodeStrategyRelByNodeId(long nodeId) throws Exception {
		return ((IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class)).getNodeStrategyRelByNodeId(nodeId);
	}
	
	/**
	 * 获取节点部署关系的id
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public static long getDeployStrategyIdByNodeId(long nodeId) throws Exception {
		BODeployNodeStrategyRelationBean relBean = getNodeStrategyRelByNodeId(nodeId);
		if (relBean == null) {
			throw new Exception("cann't find the specific strategy of nodeId:" + nodeId);
		}
		return relBean.getDeployStrategyId();
	}
	
	/**
	 * 添加节点引用的部署策略
	 * @param nodeId  节点
	 * @param strategyId 节点所配置的部署策略id
	 * @throws AIException
	 * @throws Exception 
	 */
	public static void addNodeDeployStrategy(long nodeId, long strategyId) throws Exception {
		((IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class)).addNodeDeployStrategy(nodeId, strategyId);
	}

	/**
	 * 修改节点引用的部署策略
	 * @throws Exception
	 */
	public static void modifyNodeDeployStrategy(long nodeId, long strategyId) throws Exception {
		((IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class)).modifyNodeDeployStrategy(nodeId, strategyId);
	}
	/**
	 * 修改节点引用的部署策略
	 * @param relationBean
	 * @throws Exception
	 */
	public static void deleteNodeDeployStrategy(long nodeId) throws Exception {
		((IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class)).deleteNodeDeployStrategy(nodeId);
	}
	
	/**
     * 获取引用了某个部署策略的所有节点
     * @param strategyId
     * @return
     * @throws AIException 
     * @throws Exception 
     */
	public static BODeployNodeStrategyRelationBean[] getNodesByStrategyId(
			long strategyId) throws Exception {
		return ((IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class)).getNodesByStrategyId(strategyId);
	}
	
	/**
	 * 根据节点的id获取节点的版本信息
	 * @param nodes
	 * @return
	 * @throws Exception 
	 */
	public static BODeployNodeVersionBean[] qryNodeVersionsByNodes(List<Long> nodes) throws Exception{
		return ((IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class)).qryNodeVersionsByNodes(nodes);
	};
}
