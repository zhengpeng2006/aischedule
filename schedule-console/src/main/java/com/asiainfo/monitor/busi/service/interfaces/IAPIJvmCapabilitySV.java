package com.asiainfo.monitor.busi.service.interfaces;



import java.util.List;

import com.ai.appframe2.common.DataContainerInterface;
import com.asiainfo.monitor.interapi.config.AIMemoryInfo;
import com.asiainfo.monitor.interapi.config.AIThreadInfo;
import com.asiainfo.monitor.tools.common.KeyName;

public interface IAPIJvmCapabilitySV {

	/**
	 * 获取多应用的虚拟内存信息
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getJVM5MemoryInfos(Object[] appIds) throws Exception;

	/**
	 * 获取系统信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public KeyName[] getSystemInfo(String appId, String findKey) throws Exception;

	/**
	 * 获取服务器线程信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public AIThreadInfo[] getThreadInfo(String appId, String name) throws Exception;

	/**
	 * 返回服务器内存信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public AIMemoryInfo getJVM5MemoryInfo(String appId) throws Exception;

	/**
	 * 获取系统信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getSystemInfoForAppFrame(String appId,
                                                             String findKey) throws Exception;
	
	
}
