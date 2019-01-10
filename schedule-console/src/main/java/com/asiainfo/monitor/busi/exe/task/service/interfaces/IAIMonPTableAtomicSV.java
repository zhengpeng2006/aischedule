package com.asiainfo.monitor.busi.exe.task.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTableValue;

public interface IAIMonPTableAtomicSV {

	/**
	 * 根据主键取得表数据监控配置信息
	 * 
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTableValue getBeanById(long tableId) throws RemoteException,Exception;
	
}
