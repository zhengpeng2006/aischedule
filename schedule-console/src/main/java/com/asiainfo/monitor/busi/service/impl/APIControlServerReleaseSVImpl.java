package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.exe.task.impl.WebConsoleTaskFactory;
import com.asiainfo.monitor.busi.exe.task.model.TaskTypeCode;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlServerReleaseSV;
import com.asiainfo.monitor.interapi.api.control.ServerControlApi;
import com.asiainfo.monitor.interapi.config.CallResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.interfaces.IWebConsoleTaskContext;

public class APIControlServerReleaseSVImpl implements IAPIControlServerReleaseSV {
	
	private static transient Log log=LogFactory.getLog(APIControlServerReleaseSVImpl.class);
	
	
	/**
	 * 发布应用
	 * @param appIds
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public CallResult[] releaseServer(Object[] appIds,int timeOut, String clientID) throws RemoteException,Exception{
		CallResult[] result=null;
		try{
			if (appIds==null || appIds.length<0){
				return result;
			}
			List list=new ArrayList(appIds.length);
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=null;
			for (int i=0;i<appIds.length;i++){
				appServer=serverSV.getServerByServerId(appIds[i].toString());
				
				if (appServer==null || appServer.getNodeConfig()==null){
					// 没找到应用实例[{0}]
					log.error(AIMonLocaleFactory.getResource("MOS0000070", (String)appIds[i]));
					continue;
				}
				IWebConsoleTaskContext releaseTask=null;
				WebConsoleTaskFactory factory=new WebConsoleTaskFactory();
				try{
					releaseTask=factory.getWebConsoleTaskContext(appServer,TaskTypeCode.RELEASE);
				}catch(Exception e){
					// "读取发布任务异常:"
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000135")+e.getMessage());
				}
				releaseTask.setClientID(clientID);
				list.add(releaseTask);
			}			
			int threadCount=(list.size()>>1)+1;
			int cpuCount=Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3))
				threadCount=cpuCount*3;
			
			result=ServerControlApi.start(threadCount,timeOut,(IWebConsoleTaskContext[])list.toArray(new IWebConsoleTaskContext[0]));
			
			
		}catch(Exception e){
			log.error("Call APIControlServerStatusSVImpl's Method startServer has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 版本回退
	 * @param appIds
	 * @param timeOut
	 * @param clientID
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public CallResult[] versionBack(Object[] appIds,int timeOut, String clientID, String cmdSetId) throws RemoteException,Exception{
		CallResult[] result=null;
		try{
			if (appIds==null || appIds.length<0){
				return result;
			}
			List list=new ArrayList(appIds.length);
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=null;
			for (int i=0;i<appIds.length;i++){
				appServer=serverSV.getServerByServerId(appIds[i].toString());
				
				if (appServer==null || appServer.getNodeConfig()==null){
					// 没找到应用实例[{0}]
					log.error(AIMonLocaleFactory.getResource("MOS0000070", (String)appIds[i]));
					continue;
				}
				appServer.getServer().setVersionBack_CmdId(cmdSetId);
				IWebConsoleTaskContext releaseTask=null;
				WebConsoleTaskFactory factory=new WebConsoleTaskFactory();
				try{
					releaseTask=factory.getWebConsoleTaskContext(appServer,TaskTypeCode.VERSIONBACK);
				}catch(Exception e){
					// "读取发布任务异常:"
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000135")+e.getMessage());
				}
				releaseTask.setClientID(clientID);
				list.add(releaseTask);
			}			
			int threadCount=(list.size()>>1)+1;
			int cpuCount=Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3))
				threadCount=cpuCount*3;
			
			result=ServerControlApi.start(threadCount,timeOut,(IWebConsoleTaskContext[])list.toArray(new IWebConsoleTaskContext[0]));
			
			
		}catch(Exception e){
			log.error("Call APIControlServerStatusSVImpl's Method startServer has Exception :"+e.getMessage());
		}
		return result;
	}

}
