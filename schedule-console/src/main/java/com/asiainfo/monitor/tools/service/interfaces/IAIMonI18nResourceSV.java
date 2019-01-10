package com.asiainfo.monitor.tools.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.tools.ivalues.IBOAIMonI18nResourceValue;


public interface IAIMonI18nResourceSV {

	/**
	 * 获得所有的语言配置数据
	 * @return IBOAIMonI18nResourceValue[]
	 * @throws Exception
	 */
    public IBOAIMonI18nResourceValue[] getI18nResource() throws RemoteException,Exception;
    
}
