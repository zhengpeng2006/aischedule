package com.asiainfo.monitor.busi.dao.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonMemoDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonMemoEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonMemoValue;

public class AIMonMemoDAOImpl implements IAIMonMemoDAO {

	/**
	 * 根据条件取得备忘录信息
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonMemoValue[] getMemoByCond(String startDate, String endDate) throws RemoteException,Exception {
		StringBuilder condition = new StringBuilder();
		if (!StringUtils.isBlank(startDate)) {
			condition.append(IBOAIMonMemoValue.S_CreateDate).append(" >= :startDate )");
		}
		
		if (!StringUtils.isBlank(endDate)) {
			if (!StringUtils.isBlank(startDate)) {
				condition.append(" and ");
			}
			condition.append(IBOAIMonMemoValue.S_CreateDate).append(" <= :endDate )");
		}
		Map param = new HashMap();
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		
		return BOAIMonMemoEngine.getBeans(condition.toString(), param);
	}
	
	
	/**
	 * 保存备忘录
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveMemo(IBOAIMonMemoValue value) throws Exception {
		if (value.isNew()) {
			value.setMemoId(BOAIMonMemoEngine.getNewId().longValue());
		}
		BOAIMonMemoEngine.save(value);
	}
	
}
