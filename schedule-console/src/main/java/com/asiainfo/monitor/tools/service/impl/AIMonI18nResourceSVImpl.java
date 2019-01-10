package com.asiainfo.monitor.tools.service.impl;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.tools.dao.interfaces.IAIMonI18nResourceDAO;
import com.asiainfo.monitor.tools.ivalues.IBOAIMonI18nResourceValue;
import com.asiainfo.monitor.tools.service.interfaces.IAIMonI18nResourceSV;

public class AIMonI18nResourceSVImpl implements IAIMonI18nResourceSV{
	
	private static transient Log log=LogFactory.getLog(AIMonI18nResourceSVImpl.class);

	public IBOAIMonI18nResourceValue[] getI18nResource() throws RemoteException,Exception{
		IAIMonI18nResourceDAO dao=(IAIMonI18nResourceDAO)ServiceFactory.getService(IAIMonI18nResourceDAO.class);
		return dao.getI18nResource();
	}
}
