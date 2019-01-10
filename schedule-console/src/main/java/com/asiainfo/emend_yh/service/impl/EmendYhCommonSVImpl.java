package com.asiainfo.emend_yh.service.impl;  

import java.util.List;
import java.util.Map;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.emend_yh.dao.interfaces.IEmendYhCommonDAO;
import com.asiainfo.emend_yh.ivalues.IBOAmSchedPsMonValue;
import com.asiainfo.emend_yh.ivalues.IBOAmTaskRelyValue;
import com.asiainfo.emend_yh.service.interfaces.IEmendYhCommonSV;
  
public class EmendYhCommonSVImpl implements IEmendYhCommonSV {

	@Override
	public IBOAmTaskRelyValue[] qryAllRely() throws Exception {
		IEmendYhCommonDAO dao=(IEmendYhCommonDAO)ServiceFactory.getService(IEmendYhCommonDAO.class);
		return dao.qryAllRely();
	}

	@Override
	public IBOAmTaskRelyValue[] qryRely(String taskGroup) throws Exception {
		IEmendYhCommonDAO dao=(IEmendYhCommonDAO)ServiceFactory.getService(IEmendYhCommonDAO.class);
		return dao.qryRely(taskGroup);
	}

	@Override
	public IBOAmSchedPsMonValue[] qryPsMonByCondition(String taskCode, String regionId, long bsBillingCycle) throws Exception {
		IEmendYhCommonDAO dao=(IEmendYhCommonDAO)ServiceFactory.getService(IEmendYhCommonDAO.class);
		return dao.qryPsMonByCondition(taskCode, regionId, bsBillingCycle);
	}

	@Override
	public List<String> qryGroupInfo() throws Exception {
		IEmendYhCommonDAO dao=(IEmendYhCommonDAO)ServiceFactory.getService(IEmendYhCommonDAO.class);
		return dao.qryGroupInfo();
	}

}
