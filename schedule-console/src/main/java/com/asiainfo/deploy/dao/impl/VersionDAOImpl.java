package com.asiainfo.deploy.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.deploy.common.bo.BODeployVersionBean;
import com.asiainfo.deploy.common.bo.BODeployVersionEngine;
import com.asiainfo.deploy.common.ivalues.IBODeployVersionValue;
import com.asiainfo.deploy.dao.interfaces.IVersionDAO;

public class VersionDAOImpl implements IVersionDAO{

	@Override
	public long nextId() throws Exception {
		return BODeployVersionEngine.getNewId().longValue();
	}

	@Override
	public BODeployVersionBean currentVersion(long strategyId) throws Exception {
		StringBuilder condition = new StringBuilder();
		condition.append(IBODeployVersionValue.S_DeployStrategyId).append(" = :strategyId")
		         .append(" and ").append(IBODeployVersionValue.S_State).append(" = 'C'");
		
		 Map params = new HashMap();
		 params.put("strategyId", strategyId);
		 BODeployVersionBean[] rtns = BODeployVersionEngine.getBeans(condition.toString(), params);
		 
		 if (rtns == null || rtns.length == 0) {
			 return null;
		 }
		 if (rtns.length > 1) {
			 throw new Exception("more than one current version of strategy:" + strategyId);
		 }
		 
		 return rtns[0];
	}
	
	@Override
	public int countValidVersion(long strategyId) throws Exception {
		StringBuilder condition = new StringBuilder();
		condition.append(IBODeployVersionValue.S_DeployStrategyId).append(" = :strategyId")
		         .append(" and ").append(IBODeployVersionValue.S_State).append(" = 'V'");
		
		 Map params = new HashMap();
		 params.put("strategyId", strategyId);
		 
		 return BODeployVersionEngine.getBeansCount(condition.toString(), params);
	}

	@Override
	public void saveVersion(BODeployVersionBean version) throws Exception {
		BODeployVersionEngine.save(version);
	}

	@Override
	public BODeployVersionBean getVersionById(long versionId) throws Exception {
		return BODeployVersionEngine.getBean(versionId);
	}

	@Override
	public BODeployVersionBean[] getValidHistoryVersionsAsc(long strategyId) throws Exception {
		Criteria sql = new Criteria();
		sql.addEqual(IBODeployVersionValue.S_DeployStrategyId, strategyId)
		   .addEqual(IBODeployVersionValue.S_State, "V")
		   .addAscendingOrderByColumn(IBODeployVersionValue.S_VersionId);
		return BODeployVersionEngine.getBeans(sql);
	}

}
