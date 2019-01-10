package com.asiainfo.schedule.log.dao.interfaces;

import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogDTLValue;
import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogValue;

public interface ISchedulerLogDAO {
	

	public int getSchedTaskLogDtlCount(String taskCode, String jobId, String operatorId) throws Exception;

	public IBOAISchedTaskLogDTLValue[] getSchedTaskLogDtls(String taskCode, String jobId, String operatorId, int start, int end) throws Exception;

	public int getSchedTaskLogCount(String taskCode, String startDate, String endDate, String state) throws Exception;

	public IBOAISchedTaskLogValue[] getSchedTaskLogs(String taskCode, String startDate, String endDate, String state, int start, int end) throws Exception;

    public IBOAISchedTaskLogValue getSchedTaskLog(String taskCode, String startDate, String endDate) throws Exception;

}
