package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonServerRouteDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonServerRouteEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonServerRouteValue;

public class AIMonServerRouteDAOImpl implements IAIMonServerRouteDAO {

	/**
	 * 根据源应用标识查找路由应用标识
	 * @param s_Serv_Id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonServerRouteValue[] getMonServerRouteBySServId(String s_Serv_Id) throws Exception{
		StringBuilder sb=new StringBuilder();
		sb.append(IBOAIMonServerRouteValue.S_SServId).append("= :s_Serv_Id ");
		sb.append(" AND ");
		sb.append(IBOAIMonServerRouteValue.S_State).append("='U' ");
		Map params=new HashMap();
		params.put("s_Serv_Id", s_Serv_Id);
		return BOAIMonServerRouteEngine.getBeans(sb.toString(), params);
	}
	
	/**
	 * 批量保存应用路由信息
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonServerRouteValue[] values) throws Exception{
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setRouteId(BOAIMonServerRouteEngine.getNewId().longValue());
				}
			}
		}
		BOAIMonServerRouteEngine.saveBatch(values);
	}
	
	/**
	 * 保存应用路由信息
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonServerRouteValue value) throws Exception{
		if (value != null) {
			if (value.isNew()) {
				value.setRouteId(BOAIMonServerRouteEngine.getNewId().longValue());
			}
		}
		BOAIMonServerRouteEngine.save(value);
	}
}
