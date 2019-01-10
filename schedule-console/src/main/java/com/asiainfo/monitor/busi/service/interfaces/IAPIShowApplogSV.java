package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

public interface IAPIShowApplogSV {

	/**
	 * 读取日志级别信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getLogLevels(String appId) throws RemoteException,Exception;
}
