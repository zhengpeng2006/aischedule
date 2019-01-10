package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.interapi.config.CallResult;

public interface IAPIControlServerReleaseSV {
	public CallResult[] releaseServer(Object[] appIds, int timeOut, String clientID) throws RemoteException,Exception;

	/**
	 * 发布应用
	 * @param appIds
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public CallResult[] versionBack(Object[] appIds, int timeOut, String clientID, String cmdSetId) throws RemoteException,Exception;
}
