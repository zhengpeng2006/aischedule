package com.asiainfo.schedule.log.service.impl;

import java.rmi.RemoteException;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogDTLValue;
import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogValue;
import com.asiainfo.schedule.log.dao.interfaces.ISchedulerLogDAO;
import com.asiainfo.schedule.log.service.interfaces.ISchedulerLogSV;

public class SchedulerLogSVImpl implements ISchedulerLogSV {

	@Override
	public int getSchedTaskLogDtlCount(String taskCode, String jobId, String operatorId) throws Exception, RemoteException {
		ISchedulerLogDAO dao = (ISchedulerLogDAO)ServiceFactory.getService(ISchedulerLogDAO.class);
		return dao.getSchedTaskLogDtlCount(taskCode,jobId,operatorId);
	}

	@Override
	public IBOAISchedTaskLogDTLValue[] getSchedTaskLogDtls(String taskCode, String jobId, String operatorId, int start, int end)
			throws Exception, RemoteException {
		ISchedulerLogDAO dao = (ISchedulerLogDAO)ServiceFactory.getService(ISchedulerLogDAO.class);
		return dao.getSchedTaskLogDtls(taskCode,jobId,operatorId,start,end);
	}
	
	@Override
	public int getSchedTaskLogCount(String taskCode, String startDate,String endDate,String state) throws Exception, RemoteException {
		ISchedulerLogDAO dao = (ISchedulerLogDAO)ServiceFactory.getService(ISchedulerLogDAO.class);
		return dao.getSchedTaskLogCount(taskCode,startDate,endDate,state);
	}
	
	@Override
	public IBOAISchedTaskLogValue[] getSchedTaskLogs(String taskCode, String startDate,String endDate, String state,int start, int end)
			throws Exception, RemoteException {
		ISchedulerLogDAO dao = (ISchedulerLogDAO)ServiceFactory.getService(ISchedulerLogDAO.class);
		return dao.getSchedTaskLogs(taskCode,startDate,endDate,state,start,end);
	}

    @Override
    public IBOAISchedTaskLogValue getSchedTaskLog(String taskCode, String startDate, String endDate) throws Exception, RemoteException
    {
        ISchedulerLogDAO dao = (ISchedulerLogDAO)ServiceFactory.getService(ISchedulerLogDAO.class);
        return dao.getSchedTaskLog(taskCode,startDate,endDate);
    }
}
