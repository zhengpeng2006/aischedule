package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.acquire.service.ParallelUtil;
import com.asiainfo.monitor.busi.cache.interfaces.IPhysicHost;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.exe.task.impl.WebConsoleTaskFactory;
import com.asiainfo.monitor.busi.exe.task.model.TaskTypeCode;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCmdSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlServerStatusSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdValue;
import com.asiainfo.monitor.interapi.api.control.ServerControlApi;
import com.asiainfo.monitor.interapi.config.CallResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.interfaces.IWebConsoleTaskContext;

public class APIControlServerStatusSVImpl implements IAPIControlServerStatusSV {

	private static transient Log log=LogFactory.getLog(APIControlServerStatusSVImpl.class);
	/**
	 * 启动服务器
	 * @param appIds
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public CallResult[] startServer(Object[] appIds, int timeOut, String clientID) throws RemoteException,Exception{
		CallResult[] result = null;
		if (appIds != null && appIds.length > 0) {
			List list = new ArrayList(appIds.length);
			IAIMonServerSV serverSV = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer = null;
			for (int i = 0; i < appIds.length; i++) {
				appServer = serverSV.getServerByServerId(appIds[i].toString());
				if (appServer == null || appServer.getNodeConfig() == null){
					// 没找到应用实例[{0}]
					log.error(AIMonLocaleFactory.getResource("MOS0000070", (String)appIds[i]));
					continue;
				}
				IWebConsoleTaskContext startTask = null;
				WebConsoleTaskFactory factory = new WebConsoleTaskFactory();
				try{
					startTask = factory.getWebConsoleTaskContext(appServer,TaskTypeCode.START);
				}catch(Exception e){
					// "读取发布任务异常:"
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000135")+e.getMessage());
				}
				startTask.setClientID(clientID);
				list.add(startTask);
			}
			int threadCount = (list.size()>>1)+1;
			int cpuCount = Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3)){
				threadCount = cpuCount*3;
			}
			result = ServerControlApi.start(threadCount,timeOut,(IWebConsoleTaskContext[])list.toArray(new IWebConsoleTaskContext[0]));
		}
		return result;
		
	}
	
	/**
	 * 停止服务器
	 * @param appIds
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public CallResult[] stopServer(Object[] appIds, int timeOut, String clientID) throws RemoteException,Exception{
		CallResult[] result = null;
		if (appIds != null && appIds.length > 0) {
			List list = new ArrayList(appIds.length);
			IAIMonServerSV serverSV = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer = null;
			for (int i = 0; i < appIds.length; i++) {
				appServer = serverSV.getServerByServerId(appIds[i].toString());
				if (appServer == null || appServer.getNodeConfig() == null){
					// 没找到应用实例[{0}]
					log.error(AIMonLocaleFactory.getResource("MOS0000070", (String)appIds[i]));
					continue;
				}
				IWebConsoleTaskContext stopTask = null;
				WebConsoleTaskFactory factory = new WebConsoleTaskFactory();
				try{
					stopTask = factory.getWebConsoleTaskContext(appServer,TaskTypeCode.STOP);
				}catch(Exception e){
					// "读取发布任务异常:"
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000135")+e.getMessage());
				}
				stopTask.setClientID(clientID);
				list.add(stopTask);
			}
			int threadCount = (list.size()>>1)+1;
			int cpuCount = Runtime.getRuntime().availableProcessors();
			if (threadCount > (cpuCount*3)){
				threadCount = cpuCount*3;
			}
			result = ServerControlApi.start(threadCount,timeOut,(IWebConsoleTaskContext[])list.toArray(new IWebConsoleTaskContext[0]));
		}
		return result;
	}
	
	/**
	 * 重新启动服务器,暂时只允许当前只有一台操作
	 * @param appIds
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public CallResult[] reStartServer(Object appIds[], int timeOut, String clientID) throws RemoteException,Exception{
		CallResult[] result = null;
		if (appIds != null && appIds.length > 0) {
			List list = new ArrayList(appIds.length);
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=null;
			for (int i = 0; i < appIds.length; i++) {
				appServer = serverSV.getServerByServerId(appIds[i].toString());
				if (appServer == null || appServer.getNodeConfig() == null){
					// 没找到应用实例[{0}]
					log.error(AIMonLocaleFactory.getResource("MOS0000070", (String)appIds[i]));
					continue;
				}
				IWebConsoleTaskContext restartTask = null;
				WebConsoleTaskFactory factory = new WebConsoleTaskFactory();
				try{
					restartTask = factory.getWebConsoleTaskContext(appServer,TaskTypeCode.RESTART);
				}catch(Exception e){
					// "读取发布任务异常:"
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000135")+e.getMessage());
				}
				restartTask.setClientID(clientID);
				list.add(restartTask);
			}
			int threadCount=(list.size()>>1)+1;
			int cpuCount=Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3))
				threadCount=cpuCount*3;
			
			result=ServerControlApi.start(threadCount,timeOut,(IWebConsoleTaskContext[])list.toArray(new IWebConsoleTaskContext[0]));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.asiainfo.appframe.ext.monitor.service.interfaces.IAPIControlServerStatusSV#startServerByID(java.lang.Object[], int, int)
	 */
	public String[] startServerByID(Object[] appIds, int timeOut, int serverThreadCount) throws RemoteException, Exception {
		
		List list = new ArrayList(appIds.length);
		
		if (appIds != null && appIds.length > 0) {
			IAIMonServerSV serverSV = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			IAIMonCmdSV cmdSV = (IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
			ServerConfig appServer = null;
			for (int i = 0; i < appIds.length; i++) {
				appServer = serverSV.getServerByServerId(appIds[i].toString());
				if (appServer == null || appServer.getNodeConfig() == null){
					// 没找到应用实例[{0}]
					log.error(AIMonLocaleFactory.getResource("MOS0000070", (String)appIds[i]));
					continue;
				}
				
				IPhysicHost host = appServer.getPhysicHost();
				IBOAIMonCmdValue monCmd = cmdSV.getCmdById(Integer.parseInt(appServer.getServer().getStart_CmdId()));
				if(monCmd == null){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000135"));
				}

				ParallelUtil.MidServerTask objMidServerTask = new ParallelUtil.MidServerTask();
				objMidServerTask.ip = appServer.getApp_Ip();
                objMidServerTask.port = Integer.parseInt(appServer.getPhysicHost().getHostPort());
                objMidServerTask.username = host.getHostUser();
                objMidServerTask.password = K.k(host.getHostPwd());
				objMidServerTask.serverName = appServer.getApp_Name();
				objMidServerTask.path = (appServer.getPf_path() + " " + appServer.getApp_Name());
				objMidServerTask.shell = monCmd.getCmdExpr();
				list.add(objMidServerTask);
				
			}
		}
		
		String[] rtn = ParallelUtil.computeMidServer(serverThreadCount, timeOut, (ParallelUtil.MidServerTask[])(ParallelUtil.MidServerTask[])list.toArray(new ParallelUtil.MidServerTask[0]));
		
		return rtn;
	}
	
}
