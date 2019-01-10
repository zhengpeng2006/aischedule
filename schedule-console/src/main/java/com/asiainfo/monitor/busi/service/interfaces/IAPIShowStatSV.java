package com.asiainfo.monitor.busi.service.interfaces;

import java.util.List;

import com.ai.appframe2.common.DataContainerInterface;

public interface IAPIShowStatSV {

	/**
	 * 读取在线Sql统计的状态
	 * @param appId
	 * @return
	 */
	public boolean fetchSqlMonitorState(String appId) throws Exception;

	/**
	 * 检查多应用SQL统计状态信息
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List checkSqlStatByAppIds(Object[] appIds) throws Exception;

	/**
	 * 检查多应用SVMethod统计状态信息
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List checkSVMethodStatByAppIds(Object[] appIds) throws Exception;

	/**
	 * 检查多应用Action统计状态信息
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List checkActionStatByAppIds(Object[] appIds) throws Exception;

	/**
	 * 根据条件返回
	 * @param appId
	 * @param clazz
	 * @param method
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getSVMehtodInfo(String appId) throws Exception;


	/**
	 * 读取服务器列表的在线服务调用情况
	 * @param appIds
	 * @param clazz
	 * @param method
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getActionInfoByIds(Object[] appIds, String clazz, String method,
                                   Integer start, Integer end) throws Exception;


	/**
	 * 读取服务器列表的在线SQL调用情况
	 * @param appIds
	 * @param condition
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getSqlInfoByIds(Object[] appIds, String condition, Integer start,
                                Integer end) throws Exception;


	/**
	 * 查询实时sql统计信息
	 * @param appId
	 * @param condition
	 * @return
	 */
	public List getSqlInfo(String appId, String condition) throws Exception;


	/**
	 * 读取服务器列表的在线服务调用情况
	 * @param appIds
	 * @param clazz
	 * @param method
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getSVMehtodInfoByIds(Object[] appIds, String clazz, String method,
                                     Integer start, Integer end) throws Exception;


	/**
	 * 读取服务器列表的在线服务调用情况
	 * @param appIds
	 * @param clazz
	 * @param method
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getSVMehtodInfoByIds(Object[] appIds, String clazz, String method)
			throws Exception;


	/**
	 * 查询实时sql统计信息
	 * @param appId
	 * @param condition
	 * @return
	 */
	public DataContainerInterface[] getSqlInfoForAppFrame(String appId,
                                                          String condition) throws Exception;

	/**
	 * 根据条件返回
	 * @param appId
	 * @param clazz
	 * @param method
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getActionInfo(String appId, String clazz, String method,
                              Integer start, Integer end) throws Exception;

	/**
	 * 读取在线Action统计的状态
	 * @param appId
	 * @return
	 */
	public boolean fecthActionMonitorState(String appId) throws Exception;

	/**
	 * 根据条件返回
	 * @param appId
	 * @param clazz
	 * @param method
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getSVMehtodInfo(String appId, String clazz, String method,
                                Integer start, Integer end) throws Exception;

	/**
	 * 读取在线服务统计的状态
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public boolean fetchSVMethodState(String appId) throws Exception;

	/**
	 * 根据条件返回
	 * @param appId
	 * @param clazz
	 * @param method
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getSVMehtodInfoForAppFrame(String appId,
                                                               String clazz, String method, Integer start, Integer end)
			throws Exception;
}
