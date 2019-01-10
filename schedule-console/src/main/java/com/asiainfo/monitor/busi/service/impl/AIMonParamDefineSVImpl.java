package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonParamDefineDAO;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonParamValuesDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamDefineSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamValuesSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamDefineValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonParamDefineSVImpl implements IAIMonParamDefineSV {

	/**
	 * 根据参数标识，读取参数信息
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamDefineValue getParamDefineById(String id) throws RemoteException,Exception{
		IAIMonParamDefineDAO ParamDefineDAO=(IAIMonParamDefineDAO)ServiceFactory.getService(IAIMonParamDefineDAO.class);
		return ParamDefineDAO.getParamDefineById(id);
	}
	
	/**
	 * 根据类型，读取参数信息
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamDefineValue[] getParamDefineByType(String type,String registeId) throws RemoteException,Exception{
		if (StringUtils.isBlank(type) || StringUtils.isBlank(registeId))
			// "没有传入参数"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000005"));
		IAIMonParamDefineDAO ParamDefineDAO=(IAIMonParamDefineDAO)ServiceFactory.getService(IAIMonParamDefineDAO.class);
		return ParamDefineDAO.getParamDefineByRegiste(type,registeId);
	}
	
	
	/**
	 * 批量保存或修改参数设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamDefineValue[] values) throws RemoteException,Exception {
		IAIMonParamDefineDAO ParamDefineDAO=(IAIMonParamDefineDAO)ServiceFactory.getService(IAIMonParamDefineDAO.class);
		ParamDefineDAO.saveOrUpdate(values);
	}

	/**
	 * 保存或修改参数设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamDefineValue value) throws RemoteException,Exception {
		IAIMonParamDefineDAO ParamDefineDAO=(IAIMonParamDefineDAO)ServiceFactory.getService(IAIMonParamDefineDAO.class);
		ParamDefineDAO.saveOrUpdate(value);
	}
	
	/**
	 * 删除参数
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteParamDefine(long id) throws RemoteException,Exception {
		//查看是否被引用
		IAIMonParamValuesSV valueSV = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
		IBOAIMonParamValuesValue[] values = valueSV.getParamValuesByParamId(id);
		if(values!=null && values.length >0)
			// "当前参数存在正在使用的任务或者面板,不允许删除!"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000111"));
		IAIMonParamDefineDAO ParamDefineDAO=(IAIMonParamDefineDAO)ServiceFactory.getService(IAIMonParamDefineDAO.class);
		ParamDefineDAO.deleteParamDefine(id);
	}
	
	/**
	 * 根据寄主类型以及标识删除参数定义
	 * @param registeType
	 * @param registeId
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void deleteParamDefineByRegisteType(int registeType, long registeId) throws RemoteException,Exception{
		IBOAIMonParamDefineValue[] defines = this.getParamDefineByType(String.valueOf(registeType), String.valueOf(registeId));
		if(defines == null || defines.length ==0)
			return;
		for(int i=0;i<defines.length;i++)
			defines[i].delete();
		IAIMonParamDefineDAO ParamDefineDAO=(IAIMonParamDefineDAO)ServiceFactory.getService(IAIMonParamDefineDAO.class);
		ParamDefineDAO.saveOrUpdate(defines);
	}
	
	/**
	 * 根据参数定义ID删除参数定义及参数值
	 * 
	 * @param paramId
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void deleteParam(long paramId) throws RemoteException,Exception{
		// 删除参数值
		IAIMonParamValuesSV valueSV = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
		IBOAIMonParamValuesValue[] values = valueSV.getParamValuesByParamId(paramId);
		if (values == null || values.length == 0)
			return;
		for (int i = 0; i<values.length; i++) {
			values[i].delete();
		}
		IAIMonParamValuesDAO valueDao = (IAIMonParamValuesDAO)ServiceFactory.getService(IAIMonParamValuesDAO.class);
		valueDao.saveOrUpdate(values);
		
		// 删除参数定义
		this.deleteParamDefine(paramId);
	}
}
