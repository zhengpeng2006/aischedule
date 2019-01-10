package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.List;

public interface IAPIFileOperationSV {

	/**
	 * 采集所有App主机的trace文件
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public int collectTraceFile() throws RemoteException,Exception;
	
	/**
	 * 采集文件
	 * @param appId
	 * @throws Exception
	 */
	public int collectTraceFile(Object[] appIds) throws RemoteException,Exception;
	
	/**
	 * 查询Trace文件
	 * @param appIds
	 * @param pathType
	 * @return
	 * @throws Exception
	 */
	public List queryTraceFile(String pathType) throws RemoteException,Exception;
	
	
	/**
	 * 显示Trace文件名,只能显示一个文件
	 * @param fileName
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String showTraceFile(String localPath, String localBackPath, String fileName, String path) throws RemoteException,Exception;
	
	/**
	 * 将文件从数据目录移到备份目录
	 * @param fileBuffer:服务器ID+"|"+本地路径+"|"+本地备份路径+"|"+文件名
	 * @return
	 * @throws Exception
	 */
	public boolean moveTraceFile(Object[] fileBuffer) throws RemoteException,Exception;
	
	/**
	 * 清除备份目录
	 * @param appId
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTraceFileForBack() throws RemoteException,Exception;
	
	/**
	 * 将Trace文件解析成树结构对象
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public List getTraceTreeXml(String file) throws RemoteException,Exception;
}
