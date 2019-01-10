package com.asiainfo.common.service.impl;

import java.util.Map;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.common.abo.bo.BOSchedulerOperationsBean;
import com.asiainfo.common.abo.dao.interfaces.ISchedulerOperationsDAO;
import com.asiainfo.common.abo.ivalues.IBOSchedulerOperationsValue;
import com.asiainfo.common.service.interfaces.ISchedulerOperationsSV;

public class SchedulerOperationsSVImpl implements ISchedulerOperationsSV{

	@Override
	public BOSchedulerOperationsBean getBeanById(long id) throws Exception {
		ISchedulerOperationsDAO dao = (ISchedulerOperationsDAO) ServiceFactory
				.getService(ISchedulerOperationsDAO.class);
		return dao.getBeanById(id);
	}

	@Override
	public IBOSchedulerOperationsValue[] getBeanByCondition(
			Map<String, Object> condition, int start, int end) throws Exception {
		ISchedulerOperationsDAO dao = (ISchedulerOperationsDAO) ServiceFactory
				.getService(ISchedulerOperationsDAO.class);
		return dao.getBeanByCondition(condition, start, end);
	}

	@Override
	public int getBeansCount(Map<String, Object> condition) throws Exception {
		ISchedulerOperationsDAO dao = (ISchedulerOperationsDAO) ServiceFactory
				.getService(ISchedulerOperationsDAO.class);
		return dao.getBeansCount(condition);
	}

}
