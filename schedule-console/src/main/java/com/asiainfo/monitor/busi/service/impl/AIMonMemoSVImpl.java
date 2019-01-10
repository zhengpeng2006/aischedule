package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonMemoDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonMemoSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonMemoValue;

public class AIMonMemoSVImpl implements IAIMonMemoSV {
	
	/**
	 * 根据条件取得备忘录信息
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonMemoValue[] getMemoByCond(String startDate, String endDate) throws RemoteException,Exception {
		IAIMonMemoDAO memoDAO = (IAIMonMemoDAO) ServiceFactory.getService(IAIMonMemoDAO.class);
		return memoDAO.getMemoByCond(startDate, endDate);
	}
	
	/**
	 * 保存备忘录
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveMemo(IBOAIMonMemoValue value) throws RemoteException,Exception {
		IAIMonMemoDAO memoDAO = (IAIMonMemoDAO) ServiceFactory.getService(IAIMonMemoDAO.class);
		memoDAO.saveMemo(value);
	}

}
