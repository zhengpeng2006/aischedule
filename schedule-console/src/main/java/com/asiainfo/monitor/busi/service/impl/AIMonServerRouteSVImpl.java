package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonServerRouteDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerRouteSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonServerRouteValue;

public class AIMonServerRouteSVImpl implements IAIMonServerRouteSV {

	private static transient Log log=LogFactory.getLog(AIMonServerRouteSVImpl.class);
	
	/**
	 * 根据源应用标识查找路由应用标识
	 * @param s_Serv_Id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonServerRouteValue[] getMonServerRouteBySServId(String s_Serv_Id) throws RemoteException,Exception{		
		IAIMonServerRouteDAO dao=(IAIMonServerRouteDAO)ServiceFactory.getService(IAIMonServerRouteDAO.class);
		return dao.getMonServerRouteBySServId(s_Serv_Id);
	}
	
	/**
	 * 批量保存应用路由信息
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonServerRouteValue[] values) throws RemoteException,Exception{
		IAIMonServerRouteDAO dao=(IAIMonServerRouteDAO)ServiceFactory.getService(IAIMonServerRouteDAO.class);
		dao.saveOrUpdate(values);
	}
	
	/**
	 * 保存应用路由信息
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonServerRouteValue value) throws RemoteException,Exception{
		IAIMonServerRouteDAO dao=(IAIMonServerRouteDAO)ServiceFactory.getService(IAIMonServerRouteDAO.class);
		dao.saveOrUpdate(value);
	}
	
}
