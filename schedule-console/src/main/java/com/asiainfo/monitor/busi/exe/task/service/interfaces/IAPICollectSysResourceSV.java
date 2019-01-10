package com.asiainfo.monitor.busi.exe.task.service.interfaces;

import java.rmi.RemoteException;

public interface IAPICollectSysResourceSV {

	/**
	 * 供后台定时任务采集资源文件
	 * @param id
	 * @param filename
	 * @throws RemoteException
	 * @throws Exception
	 */
	public String collectSysResource(String id, String filename) throws RemoteException,Exception;
	
	/**
	 * 根据选择的WEB应用采集集群路由配置信息，并同步到数据库中
	 * @param id
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void collectAppClusterResource(String id) throws RemoteException,Exception;
}
