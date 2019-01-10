package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPTableDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTableValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTableAtomicSV;

public class AIMonPTableAtomicSVImpl implements IAIMonPTableAtomicSV {

	/**
	 * 根据主键取得表数据监控配置信息
	 * 
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTableValue getBeanById(long tableId) throws RemoteException,Exception {
		IAIMonPTableDAO tableDao = (IAIMonPTableDAO)ServiceFactory.getService(IAIMonPTableDAO.class);
		return tableDao.getBeanById(tableId);
	}
	
	
}
