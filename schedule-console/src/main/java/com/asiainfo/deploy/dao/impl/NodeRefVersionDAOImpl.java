package com.asiainfo.deploy.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.deploy.common.bo.BODeployNodeVersionBean;
import com.asiainfo.deploy.common.bo.BODeployNodeVersionEngine;
import com.asiainfo.deploy.common.ivalues.IBODeployNodeVersionValue;
import com.asiainfo.deploy.dao.interfaces.INodeRefVersionDAO;

public class NodeRefVersionDAOImpl implements INodeRefVersionDAO{

	@Override
	public BODeployNodeVersionBean[] qryNodeVersionsByNodes(List<Long> nodeList) throws Exception {
		if (nodeList == null || nodeList.isEmpty()) return new BODeployNodeVersionBean[0];
		
		StringBuilder condition = new StringBuilder("");
		 condition.append(IBODeployNodeVersionValue.S_NodeId)
	        .append(" in (");
		 
		 Map parameter = new HashMap();
	    for (int i = 0; i < nodeList.size(); i++) {
	    	condition.append(":node" + i);
	    	if (i != nodeList.size() - 1) {
	    		condition.append(",");
	    	}
	    	parameter.put("node" + i, nodeList.get(i));
	    }
	    condition.append(")");
		 
        
		return BODeployNodeVersionEngine.getBeans(condition.toString(), parameter);
		
	}

	@Override
	public void saveNodeVersion(long nodeId, long versionId) throws Exception {
		BODeployNodeVersionBean bean = BODeployNodeVersionEngine.getBean(nodeId);
		if (bean.isNew()) {
			bean.setCreateDate(new Timestamp(new Date().getTime()));
		}
		bean.setVersionId(versionId);
		//TODO 
		bean.setOperatorId(101);
		BODeployNodeVersionEngine.save(bean);
	}

}
