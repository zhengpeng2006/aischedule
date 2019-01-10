package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

public interface IAPIControlUserSV {

	/**
	 * 强制某用户退出
	 * @param appId
	 * @param serialId
	 * @throws Exception
	 */
	public void forceLogoutUser(String appId, String serialId) throws RemoteException,Exception;
}
