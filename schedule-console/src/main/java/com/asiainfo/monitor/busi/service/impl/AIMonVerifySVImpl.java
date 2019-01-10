package com.asiainfo.monitor.busi.service.impl;  

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonVerifyDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonVerifySV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonVerifyValue;
  
public class AIMonVerifySVImpl implements IAIMonVerifySV{

	@Override
	public IBOAIMonVerifyValue qryVerifyInfoByType(String verifyType) throws Exception {
		IAIMonVerifyDAO dao=(IAIMonVerifyDAO)ServiceFactory.getService(IAIMonVerifyDAO.class);
		return dao.qryVerifyInfoByType(verifyType);
	}

	@Override
	public IBOAIMonVerifyValue[] qryAllVerifyInfos() throws Exception {
		IAIMonVerifyDAO dao=(IAIMonVerifyDAO)ServiceFactory.getService(IAIMonVerifyDAO.class);
		return dao.qryAllVerifyInfos();
	}

}
