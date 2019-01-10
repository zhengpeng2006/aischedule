package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonParamValuesDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamValuesSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonParamValuesSVImpl implements IAIMonParamValuesSV {

	/**
	 * 根据参数标识，读取参数信息
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue getParamValuesById(String id) throws RemoteException,Exception{
		IAIMonParamValuesDAO paramValuesDAO=(IAIMonParamValuesDAO)ServiceFactory.getService(IAIMonParamValuesDAO.class);
		return paramValuesDAO.getParamValuesById(id);
	}
	
	/**
	 * 根据类型，读取参数信息
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue[] getParamValuesByType(String type,String registeId) throws RemoteException,Exception{
		if (StringUtils.isBlank(type) || StringUtils.isBlank(registeId)){
			// "没有传入参数"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000005"));
		}
		IAIMonParamValuesDAO paramValuesDAO = (IAIMonParamValuesDAO)ServiceFactory.getService(IAIMonParamValuesDAO.class);
		return paramValuesDAO.getParamValuesByRegiste(type,registeId);
	}
	
	
	/**
	 * 批量保存或修改参数设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamValuesValue[] values) throws RemoteException,Exception {
		IAIMonParamValuesDAO paramValuesDAO=(IAIMonParamValuesDAO)ServiceFactory.getService(IAIMonParamValuesDAO.class);
		paramValuesDAO.saveOrUpdate(values);
	}

	/**
	 * 保存或修改参数设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamValuesValue value) throws RemoteException,Exception {
		IAIMonParamValuesDAO paramValuesDAO=(IAIMonParamValuesDAO)ServiceFactory.getService(IAIMonParamValuesDAO.class);
		paramValuesDAO.saveOrUpdate(value);
	}
	
	/**
	 * 删除参数
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteParamValues(long id) throws RemoteException,Exception {
		IAIMonParamValuesDAO paramValuesDAO=(IAIMonParamValuesDAO)ServiceFactory.getService(IAIMonParamValuesDAO.class);
		paramValuesDAO.deleteParamValues(id);
	}
	

	/**
	 * 根据参数ID获取参数值信息
	 * @param paramId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue[] getParamValuesByParamId(long paramId)throws RemoteException,Exception {
		IAIMonParamValuesDAO paramValuesDAO=(IAIMonParamValuesDAO)ServiceFactory.getService(IAIMonParamValuesDAO.class);
		return paramValuesDAO.getParamValuesByParamId(paramId);
	}
	
	/**
	 * delete param_values by registeType and registeId
	 * @param registeType
	 * @param registeId
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void deleteParamValues(int registeType, long registeId) throws RemoteException,Exception {
		IAIMonParamValuesDAO paramValuesDAO=(IAIMonParamValuesDAO)ServiceFactory.getService(IAIMonParamValuesDAO.class);
		IBOAIMonParamValuesValue[] values = paramValuesDAO.getParamValuesByRegiste(String.valueOf(registeType),String.valueOf(registeId));
		if(values == null || values.length == 0)
			return;
		for(int i=0;i<values.length;i++)
			values[i].delete();
		paramValuesDAO.saveOrUpdate(values);
	}
	
}
