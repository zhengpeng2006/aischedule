package com.asiainfo.deploy.api.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.api.interfaces.IDeployStrategySVProvider;
import com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelationBean;
import com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelationEngine;
import com.asiainfo.deploy.common.bo.BODeployNodeVersionBean;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.deploy.dao.interfaces.IDeployStrategyDAO;
import com.asiainfo.deploy.dao.interfaces.INodeRefDeployStrategyDAO;
import com.asiainfo.deploy.dao.interfaces.INodeRefVersionDAO;

/**
 * 节点部署策略的对外接口实现类
 * @author 孙德东(24204)
 */
public class DeployStrategySVProviderImpl implements IDeployStrategySVProvider{

	@Override
	public Map<Long, BODeployStrategyBean> getDeployStrategyList() throws Exception {
		IDeployStrategyDAO strategyDao = (IDeployStrategyDAO)ServiceFactory.getService(IDeployStrategyDAO.class);
		BODeployStrategyBean[] beans = strategyDao.getAllDeployStrategy();
		
		Map<Long, BODeployStrategyBean> map = new HashMap<Long, BODeployStrategyBean>();
		if (beans == null) {
			return map;
		}
		
		for (BODeployStrategyBean bean : beans) {
			map.put(bean.getDeployStrategyId(), bean);
		}
		return map;
	}

	@Override
	public void addNodeDeployStrategy(long nodeId, long strategyId) throws Exception {
		BODeployNodeStrategyRelationBean relationBean = new BODeployNodeStrategyRelationBean();
		relationBean.setNodeId(nodeId);
		relationBean.setDeployStrategyId(strategyId);
		relationBean.setCreateTime(new Timestamp(new Date().getTime()));
		relationBean.setModifyTime(new Timestamp(new Date().getTime()));
		
		BODeployNodeStrategyRelationEngine.save(relationBean);
	}

	@Override
	public BODeployStrategyBean getDeployStrategyByNodeId(long nodeId) throws Exception {
		//从节点关系表中查询出节点上的部署策略id
		BODeployNodeStrategyRelationBean relation = BODeployNodeStrategyRelationEngine.getBean(nodeId);
		
		IDeployStrategyDAO strategyDao = (IDeployStrategyDAO)ServiceFactory.getService(IDeployStrategyDAO.class);
		return strategyDao.getStrategyById(relation.getDeployStrategyId());
	}

	@Override
	public BODeployNodeStrategyRelationBean getNodeStrategyRelByNodeId(long nodeId) throws Exception {
		return BODeployNodeStrategyRelationEngine.getBean(nodeId);
	}
	
	@Override
	public void modifyNodeDeployStrategy(long nodeId, long strategyId) throws Exception {
		BODeployNodeStrategyRelationBean rel = getNodeStrategyRelByNodeId(nodeId);
		if (rel == null) throw new Exception("no node deploy relation is found.");
		rel.setDeployStrategyId(strategyId);
		
		BODeployNodeStrategyRelationEngine.save(rel);;
	}

	@Override
	public void deleteNodeDeployStrategy(long nodeId) throws Exception {
		BODeployNodeStrategyRelationBean relationBean = getNodeStrategyRelByNodeId(nodeId);
		relationBean.delete();
		
		BODeployNodeStrategyRelationEngine.save(relationBean);
	}

	@Override
	public BODeployNodeStrategyRelationBean[] getNodesByStrategyId(
			long strategyId) throws Exception {
		INodeRefDeployStrategyDAO dao = (INodeRefDeployStrategyDAO)ServiceFactory.getService(INodeRefDeployStrategyDAO.class);
		return dao.getNodesByStrategyId(strategyId);
	}

	@Override
	public BODeployNodeVersionBean[] qryNodeVersionsByNodes(List<Long> nodes)
			throws Exception {
		INodeRefVersionDAO dao = (INodeRefVersionDAO)ServiceFactory.getService(INodeRefVersionDAO.class);
		return dao.qryNodeVersionsByNodes(nodes);
	}

	@Override
	public BODeployStrategyBean[] getStrategyByCondition(
			Map<String, Object> condition) throws Exception {
		IDeployStrategyDAO dao = (IDeployStrategyDAO)ServiceFactory.getService(IDeployStrategyDAO.class);
		return dao.getStrategyByCondition(condition);
	}

	@Override
	public void deleteStrategy(long strategyId) throws Exception {
		IDeployStrategyDAO dao = (IDeployStrategyDAO)ServiceFactory.getService(IDeployStrategyDAO.class);
		dao.deleteStrategy(strategyId);
	}

	@Override
	public void saveStrategy(BODeployStrategyBean strategy) throws Exception {
		IDeployStrategyDAO dao = (IDeployStrategyDAO)ServiceFactory.getService(IDeployStrategyDAO.class);
		dao.saveStrategy(strategy);
	}

	@Override
	public BODeployStrategyBean[] getAllDeployStrategy() throws Exception {
		IDeployStrategyDAO dao = (IDeployStrategyDAO)ServiceFactory.getService(IDeployStrategyDAO.class);
		return dao.getAllDeployStrategy();
	}

	@Override
	public BODeployStrategyBean getStrategyById(long strategyId)
			throws Exception {
		IDeployStrategyDAO dao = (IDeployStrategyDAO)ServiceFactory.getService(IDeployStrategyDAO.class);
		return dao.getStrategyById(strategyId);
	}
	
	
}
