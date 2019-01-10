package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostAtomicSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonPhysicHostDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;

public class AIMonPhysicHostAtomicSVImpl implements IAIMonPhysicHostAtomicSV{

	private static transient Log log=LogFactory.getLog(AIMonPhysicHostAtomicSVImpl.class);
	/**
	 * 根据服务器标识，读取物理主机信息
	 * @param HostId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPhysicHostValue getPhysicHostById(String hostId) throws RemoteException,Exception {
		IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO)ServiceFactory.getService(IAIMonPhysicHostDAO.class);
		return hostDao.getPhysicHostById(hostId);
	}
	
	/**
	 * 根据物理主机名称查询主机信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPhysicHostValue[] getPhysicHostByName(String name) throws RemoteException,Exception {
		IAIMonPhysicHostDAO hostDao = (IAIMonPhysicHostDAO)ServiceFactory.getService(IAIMonPhysicHostDAO.class);
        return hostDao.getPhysicHostByName(name);
		
	}
	
	/**
	 * 返回所有物理主机信息值对象
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPhysicHostValue[] getAllPhostBean() throws RemoteException,Exception {
		IAIMonPhysicHostDAO phostDAO=(IAIMonPhysicHostDAO)ServiceFactory.getService(IAIMonPhysicHostDAO.class);
		return phostDAO.getAllPhost();
	}
	
	
}
