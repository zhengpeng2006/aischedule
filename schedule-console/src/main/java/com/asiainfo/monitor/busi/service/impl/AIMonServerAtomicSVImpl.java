package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerAtomicSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonServerDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;

public class AIMonServerAtomicSVImpl implements IAIMonServerAtomicSV {
	

	private static transient Log log=LogFactory.getLog(AIMonServerAtomicSVImpl.class);
	
	
	/**
	 * 根据标识读取应用值对象信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonServerValue getServerBeanById(String id) throws RemoteException,Exception{
		IAIMonServerDAO appServerDAO=(IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
		return appServerDAO.getServerByServerId(id);
	} 
	
	/**
	 * 根据应用码查找应用值对象
	 * @param code
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonServerValue getServerBeanByCode(String code) throws RemoteException,Exception{
		IAIMonServerDAO serverDAO=(IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
		IBOAIMonServerValue serverValue=serverDAO.getServerByServerCode(code);
		return serverValue;
	}
	
	/**
	 * 根据IP和PORT查找应用值对象
	 * @param code
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonServerValue getServerBeanByIpPort(String ip,String port) throws RemoteException,Exception{
		IAIMonServerDAO serverDAO=(IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
		IBOAIMonServerValue serverValue=serverDAO.getServerByIpPort(ip, port);
		return serverValue;
	}
	
	/**
	 * 读取所有应用值对象信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonServerValue[] getAllServerBean() throws RemoteException,Exception{
		IAIMonServerDAO appServerDAO=(IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
		return appServerDAO.getAllServer();
	}
	
}
