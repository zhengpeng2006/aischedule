package com.asiainfo.monitor.busi.service.interfaces;


import java.rmi.RemoteException;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgAnalyseDefineValue;


public interface IAIMonColgAnalyseDefineSV {

	public IBOAIMonColgAnalyseDefineValue getDefineById(long id) throws RemoteException,Exception;
	
	public IBOAIMonColgAnalyseDefineValue getDefineByAnalyseCodeAndId(String analyseCode, String analyseType, String analyseId) throws RemoteException,Exception;
	
	public void saveOrUpdate(IBOAIMonColgAnalyseDefineValue value) throws RemoteException,Exception;
	
	public void saveOrUpdate(IBOAIMonColgAnalyseDefineValue[] values) throws RemoteException,Exception;
	
	public void deleteDefine(long id) throws RemoteException,Exception;
}
