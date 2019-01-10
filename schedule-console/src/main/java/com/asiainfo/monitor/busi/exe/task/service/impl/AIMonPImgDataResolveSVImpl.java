package com.asiainfo.monitor.busi.exe.task.service.impl;



import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPImgDataResolveDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPImgDataResolveValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPImgDataResolveSV;

public class AIMonPImgDataResolveSVImpl implements IAIMonPImgDataResolveSV{

	
	
	/**
	 * 根据ID查询
	 * @param grpId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue getMonPImgDataResolveById(long resolveId) throws RemoteException,Exception {
		IAIMonPImgDataResolveDAO grpDao = (IAIMonPImgDataResolveDAO)ServiceFactory.getService(IAIMonPImgDataResolveDAO.class);
		return grpDao.getMonPImgDataResolveById(resolveId);
	}
	
	/**
	 * 查询所有的解析分组
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue[] getAllMonPImgDataResolve() throws RemoteException,Exception{
		IAIMonPImgDataResolveDAO grpDao = (IAIMonPImgDataResolveDAO)ServiceFactory.getService(IAIMonPImgDataResolveDAO.class);
		String condition=IBOAIMonPImgDataResolveValue.S_State+"='U'";
		return grpDao.query(condition,null);
	}
	
	/**
	 * 根据条件查询
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue[] getPImgDataResovleByName(String name, Integer startNum, Integer endNum) throws RemoteException,Exception {
		IAIMonPImgDataResolveDAO grpDao = (IAIMonPImgDataResolveDAO)ServiceFactory.getService(IAIMonPImgDataResolveDAO.class);
		return grpDao.getImgDataResolveByName(name, startNum, endNum);
	}
	
	/**
	 * 根据再分组名称读取解析个数
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public int getPImgDataResolveCountByName(String name) throws RemoteException,Exception {
		IAIMonPImgDataResolveDAO grpDao = (IAIMonPImgDataResolveDAO)ServiceFactory.getService(IAIMonPImgDataResolveDAO.class);
		return grpDao.getImgDataResolveCount(name);
	}
	
	/**
	 * 批量保存或修改
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPImgDataResolveValue[] values) throws RemoteException,Exception {
		IAIMonPImgDataResolveDAO grpDao = (IAIMonPImgDataResolveDAO)ServiceFactory.getService(IAIMonPImgDataResolveDAO.class);
		grpDao.saveOrUpdate(values);
	}
	
	/**
	 * 保存或修改表
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPImgDataResolveValue value) throws RemoteException,Exception {
		IAIMonPImgDataResolveDAO grpDao = (IAIMonPImgDataResolveDAO)ServiceFactory.getService(IAIMonPImgDataResolveDAO.class);
		grpDao.saveOrUpdate(value);
	}
	
	/**
	 * 删除
	 * 
	 * @param groupId
	 * @throws Exception
	 */
	public void delete(long groupId) throws RemoteException,Exception {
		IAIMonPImgDataResolveDAO grpDao = (IAIMonPImgDataResolveDAO)ServiceFactory.getService(IAIMonPImgDataResolveDAO.class);
		grpDao.delete(groupId);
	}
}
