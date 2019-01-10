package com.asiainfo.schedule.log.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogDTLValue;
import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogValue;

public interface ISchedulerLogSV {

	public int getSchedTaskLogDtlCount(String taskCode, String jobId, String operatorId) throws Exception, RemoteException;

	public IBOAISchedTaskLogDTLValue[] getSchedTaskLogDtls(String taskCode, String jobId, String operatorId, int start, int end)
			throws Exception, RemoteException;

	public int getSchedTaskLogCount(String taskCode, String startDate, String endDate, String state) throws Exception, RemoteException;

	public IBOAISchedTaskLogValue[] getSchedTaskLogs(String taskCode, String startDate, String endDate, String state, int start, int end)
			throws Exception, RemoteException;
	/**
	 * 根据任务编码和开始结束时间查看日志最后一条记录
	 * @param taskCode
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public IBOAISchedTaskLogValue getSchedTaskLog(String taskCode, String startDate, String endDate)
            throws Exception, RemoteException;
}
