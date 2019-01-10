package com.asiainfo.monitor.busi.dao.interfaces;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonServerRouteValue;

public interface IAIMonServerRouteDAO {

	/**
	 * 根据源应用标识查找路由应用标识
	 * @param s_Serv_Id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonServerRouteValue[] getMonServerRouteBySServId(String s_Serv_Id) throws Exception;
	
	/**
	 * 批量保存应用路由信息
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonServerRouteValue[] values) throws Exception;
	
	/**
	 * 保存应用路由信息
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonServerRouteValue value) throws Exception;
	
}
