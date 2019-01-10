package com.asiainfo.monitor.busi.dao.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonMemoValue;

public interface IAIMonMemoDAO {

	/**
	 * 根据条件取得备忘录信息
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonMemoValue[] getMemoByCond(String startDate, String endDate) throws RemoteException,Exception;
	
	/**
	 * 保存备忘录
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveMemo(IBOAIMonMemoValue value) throws Exception;
}
