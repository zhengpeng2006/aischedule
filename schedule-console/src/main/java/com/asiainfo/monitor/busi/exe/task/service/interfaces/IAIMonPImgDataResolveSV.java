package com.asiainfo.monitor.busi.exe.task.service.interfaces;



import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPImgDataResolveValue;


public interface IAIMonPImgDataResolveSV {
	
	/**
	 * 根据ID查询
	 * 
	 * @param grpId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue getMonPImgDataResolveById(long resolveId) throws RemoteException,Exception;
	
	/**
	 * 读取所有解析分组信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue[] getAllMonPImgDataResolve() throws RemoteException,Exception;
	/**
	 * 根据条件查询
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue[] getPImgDataResovleByName(String name, Integer startNum, Integer endNum) throws RemoteException,Exception ;
	
	
	public int getPImgDataResolveCountByName(String name) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPImgDataResolveValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存或修改表
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPImgDataResolveValue value) throws RemoteException,Exception;
	
	/**
	 * 删除
	 * 
	 * @param groupId
	 * @throws Exception
	 */
	public void delete(long groupId) throws RemoteException,Exception;
}
