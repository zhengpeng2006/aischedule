package com.asiainfo.monitor.busi.exe.task.service.interfaces;



import java.rmi.RemoteException;

import com.ai.appframe2.bo.DataContainer;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPExecValue;
import com.asiainfo.monitor.busi.exe.task.model.TaskExecContainer;

public interface IAIMonPExecSV {

	/**
	 * 根据条件查询进程监控配置
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPExecValue[] getExecByName(String execName, Integer startNum, Integer endNum) throws RemoteException,Exception;

	public int getExecCount(String execName) throws RemoteException,Exception;
	
	/**
	 * 根据主键取得进程监控配置信息
	 * 
	 * @param execId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPExecValue getBeanById(long execId) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改进程监控配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPExecValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存或修改进程监控配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPExecValue value) throws RemoteException,Exception;
	
	/**
	 * 保存进程监控配置
	 * @param model
	 * @throws Exception
	 */
	public void savePExecByGuide(TaskExecContainer model) throws RemoteException,Exception;
	
	/**
	 * 保存进程监控配置
	 * @param model
	 * @throws Exception
	 */
	public void savePExecByGuide(TaskExecContainer model, DataContainer[] dcs) throws RemoteException,Exception;
	
	/**
	 * 删除进程监控配置
	 * 
	 * @param execId
	 * @throws Exception
	 */
	public void delete(long execId) throws RemoteException,Exception;
	
	/**
	 * 保存进程以及进程关联的参数
	 * 
	 * @param value
	 * @param paramDcs
	 * @throws Exception
	 */
	public void saveExecAndParams(IBOAIMonPExecValue value, DataContainer[] paramDcs) throws RemoteException, Exception;
	
	/**
     * 根据条件查询进程监控配置
     * 
     * @param name
     * @param expr
     * @return
     * @throws Exception
     */
    public IBOAIMonPExecValue[] getExecByNameAndExpr(String execName, String expr, Integer startNum, Integer endNum) throws RemoteException,Exception;
}
