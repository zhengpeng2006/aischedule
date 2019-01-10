package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonColgAnalyseDefineDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonColgAnalyseDefineSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgAnalyseDefineValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonColgAnalyseDefineSVImpl implements IAIMonColgAnalyseDefineSV {

	public IBOAIMonColgAnalyseDefineValue getDefineById(long id) throws RemoteException,Exception{
		IAIMonColgAnalyseDefineDAO defineDAO=(IAIMonColgAnalyseDefineDAO)ServiceFactory.getService(IAIMonColgAnalyseDefineDAO.class);
		return defineDAO.getBeanById(id);
	}
	
	public IBOAIMonColgAnalyseDefineValue getDefineByAnalyseCodeAndId(String analyseCode,String analyseType,String analyseId) throws RemoteException,Exception{
		if (StringUtils.isBlank(analyseCode) || StringUtils.isBlank(analyseType) || StringUtils.isBlank(analyseId))
			// 分析码和分析对象标识不能为空
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000097"));
		IAIMonColgAnalyseDefineDAO defineDAO=(IAIMonColgAnalyseDefineDAO)ServiceFactory.getService(IAIMonColgAnalyseDefineDAO.class);
		return defineDAO.getBeanByAnalyseCodeAndId(analyseCode,analyseType,analyseId);
	}
	
	
	public void saveOrUpdate(IBOAIMonColgAnalyseDefineValue value) throws RemoteException,Exception{
		if (value==null)
			// 没有需要保存的数据！
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000048"));
		IAIMonColgAnalyseDefineDAO defineDAO=(IAIMonColgAnalyseDefineDAO)ServiceFactory.getService(IAIMonColgAnalyseDefineDAO.class);
		defineDAO.saveOrUpdate(value);
	}
	
	public void saveOrUpdate(IBOAIMonColgAnalyseDefineValue[] values) throws RemoteException,Exception{
		if (values==null)
			// 没有需要保存的数据！
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000048"));
		IAIMonColgAnalyseDefineDAO defineDAO=(IAIMonColgAnalyseDefineDAO)ServiceFactory.getService(IAIMonColgAnalyseDefineDAO.class);
		defineDAO.saveOrUpdate(values);
	}
	
	public void deleteDefine(long id) throws RemoteException,Exception{
		if (id<=0)
			// "标识为空"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000098"));
		IAIMonColgAnalyseDefineDAO defineDAO=(IAIMonColgAnalyseDefineDAO)ServiceFactory.getService(IAIMonColgAnalyseDefineDAO.class);
		defineDAO.deleteDefine(id);
	}
}
