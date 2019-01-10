package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

public interface IAPISystemResourceSV {

	/**
	 * 从应用系统classpath下获取配置文件信息
	 * @param appId
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public String getConfigFileContent(String appId, String fileName) throws RemoteException,Exception;
	
	/**
	 * 从应用系统classpath下获取配置文件信息
	 * @param appId
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public String getConfigFileClassLoader(String appId, String fileName) throws RemoteException,Exception;
	
}
