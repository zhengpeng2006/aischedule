package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonWDtlDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWDtlValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonWDtlSV;

public class AIMonWDtlSVImpl implements IAIMonWDtlSV {

	/**
	 * 根据条件查询监控信息发送人员关联配置信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue[] getDtlByCond(String taskId, String personId, Integer startNum, Integer endNum) throws RemoteException,Exception {
		IAIMonWDtlDAO dtlDao = (IAIMonWDtlDAO)ServiceFactory.getService(IAIMonWDtlDAO.class);
		return dtlDao.getDtlByCond(taskId, personId, startNum, endNum);
	}

	/**
	 * 根据条件取得总件数
	 * 
	 * @param taskId
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public int getDtlCount(String taskId, String personId) throws RemoteException,Exception {
		IAIMonWDtlDAO dtlDao = (IAIMonWDtlDAO)ServiceFactory.getService(IAIMonWDtlDAO.class);
		return dtlDao.getDtlCount(taskId, personId);
	}
	
	/**
	 * 根据任务标识读取任务警告发送人员信息
	 * @param taskId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue[] getDtlByTaskId(String taskId) throws RemoteException,Exception {
		IAIMonWDtlDAO dtlDao = (IAIMonWDtlDAO)ServiceFactory.getService(IAIMonWDtlDAO.class);
		return dtlDao.getDtlByTaskId(taskId);
	}
	
	/**
	 * 根据主键取得监控信息发送人员关联配置信息
	 * 
	 * @param dtlId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue getBeanById(long dtlId) throws RemoteException,Exception {
		IAIMonWDtlDAO dtlDao = (IAIMonWDtlDAO)ServiceFactory.getService(IAIMonWDtlDAO.class);
		return dtlDao.getBeanById(dtlId);
	}
	
	/**
	 * 批量保存或修改监控信息发送人员关联配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWDtlValue[] values) throws RemoteException,Exception {
		IAIMonWDtlDAO dtlDao = (IAIMonWDtlDAO)ServiceFactory.getService(IAIMonWDtlDAO.class);
		dtlDao.saveOrUpdate(values);
	}
	
	/**
	 * 保存或修改监控信息发送人员关联配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWDtlValue value) throws RemoteException,Exception {
		IAIMonWDtlDAO dtlDao = (IAIMonWDtlDAO)ServiceFactory.getService(IAIMonWDtlDAO.class);
		dtlDao.saveOrUpdate(value);
	}
	
	/**
	 * 删除监控信息发送人员关联配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long dtlId) throws RemoteException,Exception {
		IAIMonWDtlDAO dtlDao = (IAIMonWDtlDAO)ServiceFactory.getService(IAIMonWDtlDAO.class);
		dtlDao.delete(dtlId);
	}
}
