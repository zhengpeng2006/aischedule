package com.asiainfo.monitor.busi.exe.task.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTimeValue;
import com.asiainfo.monitor.tools.model.TimeModel;

public interface IAIMonPTimeSV {

	/**
	 * 根据条件查询监控时间段配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTimeValue[] getTimeInfoByExpr(String expr, Integer startNum, Integer endNum) throws RemoteException,Exception;
	
	/**
	 * 根据条件得到总件数
	 * 
	 * @param expr
	 * @return
	 * @throws Exception
	 */
	public int getTimeInfoCount(String expr) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改监控时间段配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTimeValue[] values) throws RemoteException,Exception;
	
	/**
	 * 根据主键取得监控时间段配置信息
	 * 
	 * @param timeId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTimeValue getBeanById(long timeId) throws RemoteException,Exception;
	
	/**
	 * 保存或修改监控时间段配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTimeValue value) throws RemoteException,Exception;
	
	/**
	 * 保存监控时间段配置(向导模式)
	 * @param model
	 * @throws Exception
	 */
	public void savePTimeByGuide(TimeModel model) throws RemoteException,Exception;
	
	/**
	 * 删除监控时间段配置(若有任务关联此监控时间，则报内容为FAIL的异常)
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long timeId) throws RemoteException,Exception;
}
