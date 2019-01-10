package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonWPersonDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWPersonValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonWPersonSV;

public class AIMonWPersonSVImpl implements IAIMonWPersonSV {

	/**
	 * 根据条件查询告警短信发送人员配置信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWPersonValue[] getPersonByCond(String personName, String region, Integer startNum, Integer endNum) throws RemoteException,Exception {
		IAIMonWPersonDAO personDao = (IAIMonWPersonDAO)ServiceFactory.getService(IAIMonWPersonDAO.class);
		return personDao.getPersonByCond(personName, region, startNum, endNum);
	}
	
	/**
	 * 根据条件取得总件数
	 * 
	 * @param personName
	 * @param region
	 * @return
	 * @throws Exception
	 */
	public int getPersonCount(String personName, String region) throws RemoteException,Exception {
		IAIMonWPersonDAO personDao = (IAIMonWPersonDAO)ServiceFactory.getService(IAIMonWPersonDAO.class);
		return personDao.getPersonCount(personName, region);
	}
	
	/**
	 * 根据主键取得告警短信发送人员配置信息
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWPersonValue getBeanById(long personId) throws RemoteException,Exception {
		IAIMonWPersonDAO personDao = (IAIMonWPersonDAO)ServiceFactory.getService(IAIMonWPersonDAO.class);
		return personDao.getBeanById(personId);
	}
	
	/**
	 * 批量保存或修改告警短信发送人员配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWPersonValue[] values) throws RemoteException,Exception {
		IAIMonWPersonDAO personDao = (IAIMonWPersonDAO)ServiceFactory.getService(IAIMonWPersonDAO.class);
		personDao.saveOrUpdate(values);
	}
	
	/**
	 * 保存或修改告警短信发送人员配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWPersonValue value) throws RemoteException,Exception {
		IAIMonWPersonDAO personDao = (IAIMonWPersonDAO)ServiceFactory.getService(IAIMonWPersonDAO.class);
		personDao.saveOrUpdate(value);
	}
	
	/**
	 * 删除告警短信发送人员配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long personId) throws RemoteException,Exception {
		IAIMonWPersonDAO personDao = (IAIMonWPersonDAO)ServiceFactory.getService(IAIMonWPersonDAO.class);
		personDao.delete(personId);
	}
}
