package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

public interface IAPIControlApplogSV {

	/**
	 * 设置日志级别
	 * @param appId
	 * @param logPkg
	 * @param logLevel
	 * @return
	 * @throws Exception
	 */
	public int setLogLevel(String appId, String logPkg, String logLevel) throws RemoteException,Exception;
}
