package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

public interface IAPIControlCacheSV {

	/**
	 * 批量清除框架某类型部分缓存
	 * @param appIds
	 * @param keys
	 * @throws Exception
	 */
	public void clearFrameCache(Object[] appIds, String type, Object[] keys) throws RemoteException,Exception;
	
	/**
	 * 批量清除框架某类型部分缓存
	 * @param appIds
	 * @param type
	 * @throws Exception
	 */
	public void clearFrameCacheByType(Object[] appIds, String type) throws RemoteException,Exception;
	
	/**
	 * 清楚框架指定类型缓存
	 * @param appId
	 * @param type
	 * @throws Exception
	 */
	public void clearFrameCacheByType(String appId, String type) throws RemoteException,Exception;
	
	
	/**
	 * 批量清除业务某类型部分缓存
	 * @param appId
	 * @param type
	 * @throws Exception
	 */
	public void clearBusiCache(String appId, String type) throws RemoteException,Exception;
	
	
	/**
	 * 批量清除多应用业务缓存
	 * @param appIds
	 * @param type
	 * @throws Exception
	 */
	public void clearBusiCache(Object[] appIds, String type) throws RemoteException,Exception;
	
	/**
	 * 获得监控自有缓存信息
	 * @return
	 * @throws Exception
	 */
	public List getAllLocalCache() throws RemoteException,Exception;

	/**
	 * 刷新监控自有缓存
	 * @throws Exception
	 */
	public void clearAllLocalCache() throws RemoteException,Exception;

	/**
	 * 刷新选中缓存
	 * @throws Excecption
	 */
	public void clearSelectedCache(String key) throws RemoteException,Exception;
}
