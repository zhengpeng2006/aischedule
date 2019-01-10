package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

public interface IAPIControlAppSwitchSV {

	public List switchApp(Object[] appIds, String newAppClusterCode) throws RemoteException,Exception;
}
