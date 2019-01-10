package com.asiainfo.monitor.busi.service.impl;


import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonLogMapDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonLogMapSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonLogBean;


public class AIMonLogMapSVImpl implements IAIMonLogMapSV {

	
	public void recordLog(BOAIMonLogBean qlogBean) throws RemoteException,Exception {
		try {
			IAIMonLogMapDAO logDao = (IAIMonLogMapDAO) ServiceFactory.getService(IAIMonLogMapDAO.class);
			logDao.recordLogToDB(qlogBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
