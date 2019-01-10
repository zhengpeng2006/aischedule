package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonDBUrlDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBURLValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonDBUrlSV;

public class AIMonDBUrlSVImpl implements IAIMonDBUrlSV {

	/**
	 * 根据条件查询数据库节点URL配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBURLValue[] getDBUrlByName(String urlName) throws RemoteException,Exception {
		IAIMonDBUrlDAO urlDao = (IAIMonDBUrlDAO) ServiceFactory
				.getService(IAIMonDBUrlDAO.class);
		return urlDao.getDBUrlByName(urlName);
	}
	
	/**
	 * 根据主键取得数据库节点URL配置信息
	 * 
	 * @param urlName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBURLValue getBeanByName(String urlName) throws RemoteException,Exception {
		IAIMonDBUrlDAO urlDao = (IAIMonDBUrlDAO) ServiceFactory.getService(IAIMonDBUrlDAO.class);
		return urlDao.getBeanByName(urlName);
	}
	
	/**
	 * 批量保存或修改数据库节点URL配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBURLValue[] values) throws RemoteException,Exception {
		IAIMonDBUrlDAO urlDao = (IAIMonDBUrlDAO) ServiceFactory
				.getService(IAIMonDBUrlDAO.class);
		urlDao.saveOrUpdate(values);
	}
	
	/**
	 * 保存或修改数据库节点URL配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBURLValue value) throws RemoteException,Exception {
		IAIMonDBUrlDAO urlDao = (IAIMonDBUrlDAO) ServiceFactory.getService(IAIMonDBUrlDAO.class);
		urlDao.saveOrUpdate(value);
	}
	
	/**
	 * 删除数据库节点URL配置
	 * 
	 * @param urlName
	 * @throws Exception
	 */
	public void delete(String urlName) throws RemoteException,Exception {
		IAIMonDBUrlDAO urlDao = (IAIMonDBUrlDAO) ServiceFactory.getService(IAIMonDBUrlDAO.class);
		urlDao.delete(urlName);
	}
	
	/**
	 * 检查节点URL名称是否存在
	 * 
	 * @param urlName
	 * @return
	 * @throws Exception
	 */
	public boolean checkUrlNameExists(String urlName) throws RemoteException,Exception {
		IAIMonDBUrlDAO urlDao = (IAIMonDBUrlDAO) ServiceFactory.getService(IAIMonDBUrlDAO.class);
		return urlDao.checkUrlNameExists(urlName);
	}
}
