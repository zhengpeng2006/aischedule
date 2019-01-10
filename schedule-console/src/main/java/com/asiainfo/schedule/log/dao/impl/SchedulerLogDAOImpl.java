package com.asiainfo.schedule.log.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.schedule.bo.BOAISchedTaskLogBean;
import com.asiainfo.schedule.bo.BOAISchedTaskLogDTLEngine;
import com.asiainfo.schedule.bo.BOAISchedTaskLogEngine;
import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogDTLValue;
import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogValue;
import com.asiainfo.schedule.log.dao.interfaces.ISchedulerLogDAO;

public class SchedulerLogDAOImpl implements ISchedulerLogDAO {

	@Override
	public int getSchedTaskLogCount(String taskCode, String startDate, String endDate,String state) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		if (StringUtils.isNotBlank(taskCode)) {
			condition.append(" and ").append(IBOAISchedTaskLogValue.S_TaskCode).append(" like :taskCode ");
			params.put("taskCode", "%"+taskCode+"%");
		}
		if (StringUtils.isNotBlank(startDate)) {
			condition.append(" and ").append(IBOAISchedTaskLogValue.S_StartTime).append(" >= :startDate ");
			params.put("startDate", Timestamp.valueOf(startDate));
		}
		if (StringUtils.isNotBlank(endDate)) {
			condition.append(" and ").append(IBOAISchedTaskLogValue.S_StartTime).append(" <= :endDate ");
			params.put("endDate", Timestamp.valueOf(endDate));
		}
		if (StringUtils.isNotBlank(state)) {
			condition.append(" and ").append(IBOAISchedTaskLogValue.S_State).append(" = :sState ");
			params.put("sState", state);
		}
		
		return BOAISchedTaskLogEngine.getBeansCount(condition.toString(), params);
	}

	@Override
	public IBOAISchedTaskLogValue[] getSchedTaskLogs(String taskCode, String startDate, String endDate,String state, int start, int end)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		if (StringUtils.isNotBlank(taskCode)) {
			condition.append(" and ").append(IBOAISchedTaskLogValue.S_TaskCode).append(" like :taskCode ");
			params.put("taskCode", "%"+taskCode+"%");
		}
		if (StringUtils.isNotBlank(startDate)) {
			condition.append(" and ").append(IBOAISchedTaskLogValue.S_StartTime).append(" >= :startDate ");
			params.put("startDate", Timestamp.valueOf(startDate));
		}
		if (StringUtils.isNotBlank(endDate)) {
			condition.append(" and ").append(IBOAISchedTaskLogValue.S_StartTime).append(" <= :endDate ");
			params.put("endDate", Timestamp.valueOf(endDate));
		}
		
		if (StringUtils.isNotBlank(state)) {
			condition.append(" and ").append(IBOAISchedTaskLogValue.S_State).append(" = :sState ");
			params.put("sState", state);
		}
		
		condition.append(" order by ").append(IBOAISchedTaskLogValue.S_CreateDate).append(" desc ");
		return BOAISchedTaskLogEngine.getBeans(null, condition.toString(), params, start, end, false);
	}

	@Override
	public IBOAISchedTaskLogDTLValue[] getSchedTaskLogDtls(String taskCode, String jobId, String serverId, int start, int end)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		if (StringUtils.isNotBlank(taskCode)) {
			condition.append(" and ").append(IBOAISchedTaskLogDTLValue.S_TaskCode).append(" = :taskCode");
			params.put("taskCode", taskCode);
		}

		if (StringUtils.isNotBlank(jobId)) {
			condition.append(" and ").append(IBOAISchedTaskLogDTLValue.S_JobId).append(" = :jobId");
			params.put("jobId", jobId);
		}

		if (StringUtils.isNotBlank(serverId)) {
			condition.append(" and ").append(IBOAISchedTaskLogDTLValue.S_Operator).append(" = :serverId");
			params.put("serverId", serverId);
		}

		condition.append(" order by ").append(IBOAISchedTaskLogDTLValue.S_CreateDate).append(" desc, ")
				.append(IBOAISchedTaskLogDTLValue.S_JobId).append(",").append(IBOAISchedTaskLogDTLValue.S_TaskItem);

		return BOAISchedTaskLogDTLEngine.getBeans(null, condition.toString(), params, start, end, false);
	}

	@Override
	public int getSchedTaskLogDtlCount(String taskCode, String jobId, String operatorId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		if (StringUtils.isNotBlank(taskCode)) {
			condition.append(" and ").append(IBOAISchedTaskLogDTLValue.S_TaskCode).append(" = :taskCode");
			params.put("taskCode", taskCode);
		}

		if (StringUtils.isNotBlank(jobId)) {
			condition.append(" and ").append(IBOAISchedTaskLogDTLValue.S_JobId).append(" = :jobId");
			params.put("jobId", jobId);
		}

		if (StringUtils.isNotBlank(operatorId)) {
			condition.append(" and ").append(IBOAISchedTaskLogDTLValue.S_Operator).append(" = :serverId");
			params.put("serverId", operatorId);
		}

		return BOAISchedTaskLogDTLEngine.getBeansCount(condition.toString(), params);
	}

    @Override
    public IBOAISchedTaskLogValue getSchedTaskLog(String taskCode, String startDate, String endDate) throws Exception
    {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder condition = new StringBuilder(" 1=1 ");
        if (StringUtils.isNotBlank(taskCode)) {
            condition.append(" and ").append(IBOAISchedTaskLogValue.S_TaskCode).append(" like :taskCode ");
            params.put("taskCode", "%"+taskCode+"%");
        }
        if (StringUtils.isNotBlank(startDate)) {
            condition.append(" and ").append(IBOAISchedTaskLogValue.S_CreateDate).append(" >= :startDate ");
            params.put("startDate", Timestamp.valueOf(startDate));
        }
        if (StringUtils.isNotBlank(endDate)) {
            condition.append(" and ").append(IBOAISchedTaskLogValue.S_CreateDate).append(" <= :endDate ");
            params.put("endDate", Timestamp.valueOf(endDate));
        }
        
        condition.append(" order by ").append(IBOAISchedTaskLogValue.S_CreateDate).append(" desc ");
        BOAISchedTaskLogBean[] values= BOAISchedTaskLogEngine.getBeans(condition.toString(), params);
        if(values!=null&&values.length>0) {
            return values[0];
        }
        return null;
    }

}
