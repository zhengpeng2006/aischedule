package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

public interface IAPIShowAppSwitchSV {

	public List getWebConnectApp(Object[] appIds) throws RemoteException,Exception;
	
	public List getAppConnectInfo(Object[] appIds) throws RemoteException,Exception;
	
}
