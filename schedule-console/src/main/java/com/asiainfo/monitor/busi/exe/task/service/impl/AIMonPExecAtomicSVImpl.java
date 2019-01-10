package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPExecDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPExecValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecAtomicSV;

public class AIMonPExecAtomicSVImpl implements IAIMonPExecAtomicSV {

	/**
	 * 根据主键取得进程监控配置信息
	 * 
	 * @param execId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPExecValue getBeanById(long execId) throws RemoteException,Exception {
		IAIMonPExecDAO execDao = (IAIMonPExecDAO)ServiceFactory.getService(IAIMonPExecDAO.class);
		return execDao.getBeanById(execId);
	}
	

}
