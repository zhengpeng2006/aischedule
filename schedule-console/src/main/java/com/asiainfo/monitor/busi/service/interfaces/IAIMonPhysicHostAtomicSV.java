package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;

public interface IAIMonPhysicHostAtomicSV {

	public IBOAIMonPhysicHostValue getPhysicHostById(String hostId) throws RemoteException,Exception ;
	
	/**
	 * 根据物理主机名称查询主机信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPhysicHostValue[] getPhysicHostByName(String name) throws RemoteException,Exception;
	
	/**
	 * 返回所有物理主机信息值对象
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPhysicHostValue[] getAllPhostBean() throws RemoteException,Exception;

}
