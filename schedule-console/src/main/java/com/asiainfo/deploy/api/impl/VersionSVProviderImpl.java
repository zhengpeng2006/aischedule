package com.asiainfo.deploy.api.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.api.interfaces.IVersionSVProvider;
import com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelationBean;
import com.asiainfo.deploy.common.bo.BODeployNodeVersionBean;
import com.asiainfo.deploy.common.bo.BODeployVersionBean;
import com.asiainfo.deploy.dao.interfaces.INodeRefDeployStrategyDAO;
import com.asiainfo.deploy.dao.interfaces.INodeRefVersionDAO;
import com.asiainfo.deploy.dao.interfaces.IVersionDAO;

/**
 * 提供版本相关的接口
 * @author 孙德东(24204)
 */
public class VersionSVProviderImpl implements IVersionSVProvider{

	@Override
	public boolean isAllNodesHaveSameVersion(long deployStrategyId) throws Exception {
		
		INodeRefDeployStrategyDAO nodeRefStrategyDao = (INodeRefDeployStrategyDAO)ServiceFactory.getService(INodeRefDeployStrategyDAO.class);
		//获取部署策略下的所有节点
		BODeployNodeStrategyRelationBean[] nodes = nodeRefStrategyDao.getNodesByStrategyId(deployStrategyId);
		
		List<Long> nodeList = new ArrayList<Long>();
		for (BODeployNodeStrategyRelationBean node : nodes) {
			nodeList.add(node.getNodeId());
		}
		return isAllNodesHaveSameVersion(nodeList);
	}

	@Override
	public boolean isAllNodesHaveSameVersion(List<Long> nodeList) throws Exception {
		INodeRefVersionDAO nodeRefVersionDao = (INodeRefVersionDAO)ServiceFactory.getService(INodeRefVersionDAO.class);
		BODeployNodeVersionBean[] versions = nodeRefVersionDao.qryNodeVersionsByNodes(nodeList);
		
		Set<Long> set = new HashSet<Long>();
		for (BODeployNodeVersionBean version : versions) {
			set.add(version.getVersionId());
		}
		return set.size() < 1;
	}

	@Override
	public long nextId() throws Exception {
		IVersionDAO dao = (IVersionDAO) ServiceFactory
				.getService(IVersionDAO.class);
		return dao.nextId();
	}

	@Override
	public void saveVersion(BODeployVersionBean version) throws Exception {
		IVersionDAO dao = (IVersionDAO) ServiceFactory
				.getService(IVersionDAO.class);
		dao.saveVersion(version);
	}

	@Override
	public BODeployVersionBean currentVersion(long strategyId) throws Exception {
		IVersionDAO dao = (IVersionDAO) ServiceFactory
				.getService(IVersionDAO.class);		
		return dao.currentVersion(strategyId);
	}

	
}
