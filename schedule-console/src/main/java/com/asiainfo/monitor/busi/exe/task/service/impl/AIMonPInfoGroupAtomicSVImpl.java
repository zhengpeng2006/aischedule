package com.asiainfo.monitor.busi.exe.task.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPInfoGroupDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupAtomicSV;

public class AIMonPInfoGroupAtomicSVImpl implements IAIMonPInfoGroupAtomicSV {

	/**
	 * 根据标识读取归属区域代码再分组信息
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue getMonPInfoGroupById(long groupId) throws RemoteException,Exception {
		IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
		return dao.getMonPInfoGroupById(groupId);
	}
	
	
	/**
	 * 获取所有分组信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue[] getAllMonPInfoGroupBean() throws RemoteException,Exception{
		IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
		String condition=IBOAIMonPInfoGroupValue.S_State+" ='U' ";
		return dao.query(condition,null);
	}
	
	/**
	 * 修改或保存所属区域再分组信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoGroupValue value) throws RemoteException,Exception {
		IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
		dao.saveOrUpdate(value);		
	}
	
	/**
	 * 批量保存或修改所属区域再分组信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoGroupValue[] values) throws RemoteException,Exception {		
		IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
		dao.saveOrUpdate(values);		
	}
	
	/**
	 * 删除所属区域再分组信息
	 * 
	 * @param areaId
	 * @throws Exception
	 */
	public void delete(long groupId) throws RemoteException,Exception {
		IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
		dao.delete(groupId);		
	}

	

	public IBOAIMonPInfoGroupValue[] getPInfoGroupByCodeAndName(String groupCode, String groupName,String layer,Integer startNum, Integer endNum) throws RemoteException,Exception {
		IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
		return dao.getPInfoGroupByCodeAndName(groupCode,groupName, layer,startNum,  endNum);
	}

	public int getPInfoGroupCount(String groupCode, String groupName,String layer) throws RemoteException,Exception {
		IAIMonPInfoGroupDAO dao = (IAIMonPInfoGroupDAO) ServiceFactory.getService(IAIMonPInfoGroupDAO.class);
		return dao.getPInfoGroupCount(groupCode,groupName,layer);
	}
}
