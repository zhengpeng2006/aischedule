package com.asiainfo.deploy.api.impl;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.api.interfaces.INodeRefVersionSVProvider;
import com.asiainfo.deploy.dao.interfaces.INodeRefVersionDAO;

public class NodeRefVersionSVProviderImpl implements INodeRefVersionSVProvider{
	/**
	 * 保存版本
	 * @param nodeId
	 * @param versionId
	 * @throws Exception
	 */
	public void saveNodeVersion(long nodeId, long versionId) throws Exception {
		INodeRefVersionDAO nodeVersionDao = (INodeRefVersionDAO)ServiceFactory.getService(INodeRefVersionDAO.class);
		nodeVersionDao.saveNodeVersion(nodeId, versionId);
	}
}
