package com.asiainfo.common.abo.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.common.abo.bo.BOSchedulerOperationsBean;
import com.asiainfo.common.abo.bo.BOSchedulerOperationsEngine;
import com.asiainfo.common.abo.dao.interfaces.ISchedulerOperationsDAO;
import com.asiainfo.common.abo.ivalues.IBOSchedulerOperationsValue;
import com.asiainfo.common.utils.CommonConstants;

public class SchedulerOperationsDAOImpl implements ISchedulerOperationsDAO {

	@Override
	public BOSchedulerOperationsBean getBeanById(long id) throws Exception {
		return BOSchedulerOperationsEngine.getBean(id);
	}

	@Override
	public IBOSchedulerOperationsValue[] getBeanByCondition(
			Map<String, Object> condition, int start, int end) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("1 = 1");
		if (null != condition && !condition.isEmpty()){
			Set<String> set = condition.keySet();
			for (String str : set) {
				if (StringUtils.isNotBlank(str)){
					sb.append(" AND ");
					if (IBOSchedulerOperationsValue.S_OperationObjectContent.equalsIgnoreCase(str)){
						sb.append(str).append(" like :").append(str);
					}else if (IBOSchedulerOperationsValue.S_OperationClientIp.equalsIgnoreCase(str)){
						sb.append(IBOSchedulerOperationsValue.S_OperationClientIp).append(" like :").append(str);
					}else if (CommonConstants.OPERATION_QRY_START.equalsIgnoreCase(str)){
						sb.append(IBOSchedulerOperationsValue.S_CreateDate).append(" >= :").append(str);
					}else if (CommonConstants.OPERATION_QRY_END.equalsIgnoreCase(str)){
						sb.append(IBOSchedulerOperationsValue.S_CreateDate).append(" <= :").append(str);
					}else{
						sb.append(str).append(" = :").append(str);
					}
				}
			}
			sb.append(" order by ").append(IBOSchedulerOperationsValue.S_CreateDate).append(" desc");
		}
		return BOSchedulerOperationsEngine.getBeans(null,sb.toString(), condition,start,end,false);
	}
	
	@Override
	public int getBeansCount(Map<String, Object> condition) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("1 = 1");
		if (null != condition && !condition.isEmpty()){
			Set<String> set = condition.keySet();
			for (String str : set) {
				if (StringUtils.isNotBlank(str)){
					sb.append(" AND ");
					if (IBOSchedulerOperationsValue.S_OperationObjectContent.equalsIgnoreCase(str)){
						sb.append(str).append(" like :").append(str);
					}else if (CommonConstants.OPERATION_QRY_START.equalsIgnoreCase(str)){
						sb.append(IBOSchedulerOperationsValue.S_CreateDate).append(" >= :").append(str);
					}else if (CommonConstants.OPERATION_QRY_END.equalsIgnoreCase(str)){
						sb.append(IBOSchedulerOperationsValue.S_CreateDate).append(" <= :").append(str);
					}else{
						sb.append(str).append(" = :").append(str);
					}
				}
			}
		}
		return BOSchedulerOperationsEngine.getBeansCount(sb.toString(), condition);
	}
}
