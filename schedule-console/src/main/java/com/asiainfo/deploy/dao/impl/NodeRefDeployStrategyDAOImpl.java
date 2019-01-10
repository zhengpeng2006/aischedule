package com.asiainfo.deploy.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelationBean;
import com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelationEngine;
import com.asiainfo.deploy.common.ivalues.IBODeployNodeStrategyRelationValue;
import com.asiainfo.deploy.dao.interfaces.INodeRefDeployStrategyDAO;

/**
 * 节点引用部署策略的数据库操作类
 * 
 * @author 孙德东(24204)
 */
public class NodeRefDeployStrategyDAOImpl implements INodeRefDeployStrategyDAO {

	@Override
	public long getStrategyIdByNodeId(long nodeId) throws Exception {
		return BODeployNodeStrategyRelationEngine.getBean(nodeId).getDeployStrategyId();
	}

	@Override
	public BODeployNodeStrategyRelationBean[] getNodesByStrategyId(long strategyId) throws Exception {
		BODeployNodeStrategyRelationBean bean = new BODeployNodeStrategyRelationBean();
		bean.setDeployStrategyId(strategyId);

		return BODeployNodeStrategyRelationEngine.getBeans(bean);
	}

	@Override
	public BODeployNodeStrategyRelationBean[] getStrategysByNodes(List<Long> nodeList) throws Exception {
		if (nodeList == null || nodeList.isEmpty())
			return new BODeployNodeStrategyRelationBean[0];

		Criteria sql = new Criteria();
		sql.addIn(IBODeployNodeStrategyRelationValue.S_NodeId, nodeList);
		
		return BODeployNodeStrategyRelationEngine.getBeans(sql);
	}
}
