package com.asiainfo.monitor.busi.service.interfaces;

import java.util.List;

import com.ai.appframe2.common.DataContainerInterface;

public interface IAPIShowCacheSV {

	/**
	 * 读取框架缓存类型
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getFrameCacheType(Object[] appIds) throws Exception;

	/**
	 * 读取业务缓存类型
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getBusiCacheType(Object[] appIds) throws Exception;

	/**
	 * 读取业务所有类型的缓存
	 * @param appId
	 * @param type
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List getBusiCache(String appId, String type, Integer start, Integer end)
			throws Exception;

	/**
	 * 根据多应用统计缓存信息
	 * @param appIds
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getBusiCacheFromMultiAppForAppFrame(
            Object[] appIds, String type) throws Exception;

	/**
	 * 读取框架所有类型的缓存
	 * @param appId
	 * @param type
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List getFrameCache(Object[] appIds, String type, String key,
                              Integer start, Integer end) throws Exception;

	/**
	 * 根据多应用统计缓存信息
	 * @param appIds
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List getBusiCacheFromMultiApp(Object[] appIds, String type)
			throws Exception;

	/**
	 * 读取业务缓存类型
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getBusiCacheType(String appId) throws Exception;
	
}
