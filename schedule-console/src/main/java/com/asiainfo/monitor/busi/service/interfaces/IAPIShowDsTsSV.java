package com.asiainfo.monitor.busi.service.interfaces;

import java.util.List;

import com.ai.appframe2.common.DataContainerInterface;
import com.asiainfo.monitor.interapi.config.AITmSummary;

public interface IAPIShowDsTsSV {

	
	/**
	 * 读取事务概况信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public AITmSummary getTransaction(String appId) throws Exception;

	/**
	 * 获取运行时数据源信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getDataSourceRuntime(String appId) throws Exception;

	/**
	 * 读取多应用的事务统计信息
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getTmStatInfo(Object[] appIds) throws Exception;

	/**
	 * 读取数据源配置文件
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getDataSourceConfigForAppFrame(String appId)
			throws Exception;

	/**
	 * 读取数据源配置文件
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getDataSourceConfig(String appId) throws Exception;

	/**
	 * 获取多应用运行时数据源统计信息
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getDataSourceRuntimes(Object[] appIds) throws Exception;
}
