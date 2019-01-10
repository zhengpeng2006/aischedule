package com.asiainfo.monitor.busi.exe.task.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWSmsValue;
import com.asiainfo.monitor.tools.model.interfaces.ISmsState;

public interface IAIMonWSmsSV {

	/**
	 * 保存短信信息
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long saveOrUpdate(IBOAIMonWSmsValue value) throws RemoteException,Exception;
	
	/**
	 * 批量保存短信信息
	 * @param values
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWSmsValue[] values) throws RemoteException,Exception;
	
	/**
	 * 批量保存短信信息，参数对象需要进行转化成值对象
	 * @param sms
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveOrUpdate(ISmsState[] sms) throws RemoteException,Exception;
	
}
