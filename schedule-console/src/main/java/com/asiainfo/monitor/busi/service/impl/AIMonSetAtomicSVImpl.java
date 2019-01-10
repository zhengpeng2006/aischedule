package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonSetDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonSetAtomicSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSetValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonSetAtomicSVImpl implements IAIMonSetAtomicSV {

	/**
	 * 根据标识获取设置
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonSetValue getMonSetBeanById(String id) throws RemoteException,Exception{
		IAIMonSetDAO dao=(IAIMonSetDAO)ServiceFactory.getService(IAIMonSetDAO.class);
		return dao.getMonSetBeanById(Long.parseLong(id));
	}
	
	/**
	 * 根据应用标识、状态码查询应用状态信息
	 * @param appId
	 * @param vCode
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonSetValue getMonSetBeanBySCodeVCode(String appId,String vCode) throws RemoteException,Exception{
		if (StringUtils.isBlank(appId) || StringUtils.isBlank(vCode))
			// "参数传入错误
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000008") + ", appid="+appId+",vcode="+vCode);
		IAIMonSetDAO dao=(IAIMonSetDAO)ServiceFactory.getService(IAIMonSetDAO.class);
		IBOAIMonSetValue[] result=dao.getMonSetBeanByAppIdAndVCode(Long.parseLong(appId),vCode);
		if (result==null || result.length>1)
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000352",new String[]{appId,vCode}));
		
		return result[0];
	}
}
