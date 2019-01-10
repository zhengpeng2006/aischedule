package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataStructInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.interfaces.IGroup;
import com.asiainfo.monitor.busi.cache.interfaces.IServer;
import com.asiainfo.monitor.busi.cache.interfaces.IServerNode;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowServerStatusSV;
import com.asiainfo.monitor.interapi.ServerControlInfo;
import com.asiainfo.monitor.interapi.api.control.ServerControlApi;
import com.asiainfo.monitor.interapi.api.ibm.WasServerControlApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class APIShowServerStatusSVImpl implements IAPIShowServerStatusSV {

	private static transient Log log=LogFactory.getLog(APIShowServerStatusSVImpl.class);
	/**
	 * 根据服务器组读取服务器信息
	 * @param groupId
	 * @param appServerName
	 * @return
	 * @throws Exception
	 */
	public DataStructInterface[] getAppServerByGroupId(String groupId,String appServerName,Integer threadCount,Integer timeOut,String isSelected) throws RemoteException,Exception {
		if (StringUtils.isBlank(groupId))
			return null;
		DataStructInterface[] appServerItem = null;
		List appServerValues = null;
		
		try{
			IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
			
			IGroup groupValue=groupSV.getGroupByGroupId(groupId);
			if (groupValue!=null){
				List controlInfos=new ArrayList();
				Map appServerMap=new HashMap();
				IAIMonNodeSV nodeSV=(IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
				
				List nodeValues=nodeSV.getNodeByGroupId(groupId);
				
				if (nodeValues!=null && nodeValues.size()>0){
					IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
					
					for (int j=0;j<nodeValues.size();j++){
						IServerNode node=(IServerNode)nodeValues.get(j);
						appServerValues=serverSV.getAppServerConfigByNodeId(node.getNode_Id());
						if (appServerValues!=null && appServerValues.size()>0){
							for (int k=0;k<appServerValues.size();k++){
								IServer server=(IServer)appServerValues.get(k);
								if(server.getApp_Name().indexOf(appServerName)==-1){
									continue;
								}
								ServerControlInfo controlInfo=new ServerControlInfo();
								controlInfo.setGrpName(groupValue.getGroup_Name());
								if ( (server instanceof ServerConfig) && (((ServerConfig)server).getPhysicHost()!=null)){
                                    controlInfo.setHostname(((ServerConfig) server).getPhysicHost().getHostName());
                                    controlInfo.setServerIp(((ServerConfig) server).getPhysicHost().getHostIp());
								}
								
								controlInfo.setServerName(server.getApp_Name());
								if (StringUtils.isNotBlank(server.getStart_CmdId()) && Long.parseLong(server.getStart_CmdId())>0)
									controlInfo.setStartExecId(Long.parseLong(server.getStart_CmdId()));
								if (StringUtils.isNotBlank(server.getStop_CmdId()) && Long.parseLong(server.getStop_CmdId())>0)
									controlInfo.setStopExecId(Long.parseLong(server.getStop_CmdId()));
								controlInfo.setUrl(server.getCheck_Url());
								controlInfos.add(controlInfo);
								appServerMap.put(server.getApp_Name(),server);
							}
						}
					}
				}
				
				if (controlInfos.size()>0){
					appServerItem = new DataContainer[controlInfos.size()];
					ServerControlInfo[] controlBuffer=(ServerControlInfo[])controlInfos.toArray(new ServerControlInfo[0]);
					try {
						ServerControlApi.monitorServerControl(threadCount,timeOut,controlBuffer);
					} catch (Exception e) {
						// "服务器异常"
						log.error(AIMonLocaleFactory.getResource("MOS0000142"));
					}
					for (int recount=0;recount<controlBuffer.length;recount++){
						if (appServerMap.containsKey(controlBuffer[recount].getServerName())){
							DataStructInterface tmpappServerItem=(DataContainer)appServerMap.get(controlBuffer[recount].getServerName());
							appServerItem[recount]=new DataContainer();
							appServerItem[recount].copy(tmpappServerItem);
							appServerItem[recount].set("HOST_NAME",controlBuffer[recount].getHostname());
							appServerItem[recount].set("HOST_IP",controlBuffer[recount].getServerIp());
							appServerItem[recount].set("CHK","false");
							if ("OK".equalsIgnoreCase(controlBuffer[recount].getStatus())){
								appServerItem[recount].set("REAL_STATE","1");
								if("normal".equals(isSelected)){
									appServerItem[recount].set("CHK","true");
								}
							}else if ("TIMEOUT".equalsIgnoreCase(controlBuffer[recount].getStatus())){
								appServerItem[recount].set("REAL_STATE","0");
							}else{
								appServerItem[recount].set("REAL_STATE","-1");
								if("unnormal".equals(isSelected)){
									appServerItem[recount].set("CHK","true");
								}
							}
							appServerItem[recount].set("VERSION_INFO",controlBuffer[recount].getInfo());
						}
					}
				}
			}
		}catch(Exception e){
			log.error("Call APIShowServerStatusSVImpl's Method getAppServerByGroupId has Exception :"+e.getMessage());
		}
		return appServerItem;
	}
	
	/**
	 * 计算ORB的ID
	 * @param appNames
	 * @return
	 * @throws Exception
	 */
	public List comptuerOrbId(Object[] appNames) throws RemoteException,Exception {
		List result=null;
		try{
		    if (appNames==null || appNames.length<1)
		    	return result;

		    result=new ArrayList(appNames.length);
		    
		    for (int i = 0; i < appNames.length; i++) {
		      String appServerName = appNames[i].toString().trim();
		      Map map=new HashMap(2);
		      map.put("APP_NAME",appServerName);
		      map.put("ORB_ID",WasServerControlApi.comptuerOrbId(appServerName));
		      result.add(map);
		    }
		}catch(Exception e){
			log.error("Call APIShowServerStatusSVImpl's Method comptuerOrbId has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 检查应用的状态
	 * @param appIds
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public List getAppServerState(Object[] appIds,Integer threadCount,Integer timeOut) throws RemoteException,Exception {
		List result=null;
		try{
			if (appIds==null || appIds.length<0){
				return result;
			}
			result=new ArrayList();			
			List controlList=new ArrayList();
			
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=null;
			for (int i=0;i<appIds.length;i++){
				appServer=serverSV.getServerByServerId(appIds[i].toString());
				
				if (appServer!=null){
					if (threadCount.intValue()<1)
						threadCount=new Integer(5);
					if (timeOut.intValue()<1)
						timeOut=new Integer(2);
					ServerControlInfo controlInfo=new ServerControlInfo();
					controlInfo.setGrpName(appServer.getNodeConfig().getGroupConfig().getGroup_Name());
					controlInfo.setHostname(appServer.getNodeConfig().getNode_Name());
					controlInfo.setServerName(appServer.getApp_Name());
					if (appServer.getStart_CmdSet()!=null)
						controlInfo.setStartExecId(Long.parseLong(appServer.getServer().getStart_CmdId()));
					if (appServer.getStop_CmdSet()!=null)
						controlInfo.setStopExecId(Long.parseLong(appServer.getServer().getStop_CmdId()));
					controlInfo.setUrl(appServer.getCheck_Url());
					controlList.add(controlInfo);
				}				
			}
			
			if (controlList.size()>0){
				ServerControlInfo[] controlInfoBuffer=(ServerControlInfo[])controlList.toArray(new ServerControlInfo[0]);
				try{
					controlInfoBuffer=ServerControlApi.monitorServerControl(threadCount.intValue(),timeOut.intValue(),controlInfoBuffer);
				}catch(Exception e){
					log.error(AIMonLocaleFactory.getResource("MOS0000142"));
				}
				int abCount = 0;
				for (int i=0;i<controlInfoBuffer.length;i++){
					if (controlInfoBuffer[i].getStatus()!=null && !controlInfoBuffer[i].getStatus().equalsIgnoreCase("OK")){
						abCount++;
					}
					Map map=new HashMap();
					map.put("SERVER_NAME",controlInfoBuffer[i].getServerName());
					map.put("HOST_NAME",controlInfoBuffer[i].getHostname());
					map.put("STATE",controlInfoBuffer[i].getStatus());
					map.put("VERSION", controlInfoBuffer[i].getInfo());
					map.put("SERVERCOUNT", ""+appIds.length);
					map.put("APPCOUNT", ""+appIds.length);
					map.put("ABNORMAL", ""+abCount);
					result.add(map);
				}
			}
			
		}catch(Exception e){
			log.error("Call APIShowServerStatusSVImpl's Method getAppState has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 检查服务器状态
	 * 
	 * @param appIds
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public ServerControlInfo[] getAppServerStates(Object[] appIds,Integer threadCount,Integer timeOut) throws RemoteException,Exception {
		ServerControlInfo[] controlInfoBuffer = null;
		try{
			if (appIds==null || appIds.length<0){
				return controlInfoBuffer;
			}
			List controlList=new ArrayList();
			
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=null;
			for (int i=0;i<appIds.length;i++){
				appServer=serverSV.getServerByServerId(appIds[i].toString());
				
				if (appServer!=null){
					if (threadCount.intValue()<1)
						threadCount=new Integer(5);
					if (timeOut.intValue()<1)
						timeOut=new Integer(2);
					ServerControlInfo controlInfo=new ServerControlInfo();
					controlInfo.setServerId(appServer.getApp_Id());
					controlInfo.setUrl(appServer.getCheck_Url());
					controlList.add(controlInfo);
				}				
			}
			
			if (controlList.size()>0){
				controlInfoBuffer =(ServerControlInfo[])controlList.toArray(new ServerControlInfo[0]);
				try{
					controlInfoBuffer=ServerControlApi.monitorServerControl(threadCount.intValue(),timeOut.intValue(),controlInfoBuffer);
				}catch(Exception e){
					log.error(AIMonLocaleFactory.getResource("MOS0000142"));
				}
			}
			
		}catch(Exception e){
			log.error("Call APIShowServerStatusSVImpl's Method getAppState has Exception :"+e.getMessage());
		}
		return controlInfoBuffer;
	}
	
	
	/**
	 * 检查服务器状态
	 * @param appId
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public ServerControlInfo getAppServerState(String appId,Integer threadCount,Integer timeOut) throws RemoteException,Exception {
		ServerControlInfo result=null;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=serverSV.getServerByServerId(appId);
			if (appServer==null){
				appServer=serverSV.getServerByServerIdNocache(appId);				
			}
			if (appServer==null){
				log.error("Can not found Server info,The id is "+appId);
				return null;
			}
			if (threadCount.intValue()<1)
				threadCount=new Integer(3);
			if (timeOut.intValue()<1)
				timeOut=new Integer(2);
			result=new ServerControlInfo();
			result.setGrpName(appServer.getNodeConfig().getGroupConfig().getGroup_Name());
			result.setHostname(appServer.getNodeConfig().getNode_Name());
			result.setServerName(appServer.getApp_Name());
			if (appServer.getStart_CmdSet()!=null)
				result.setStartExecId(Long.parseLong(appServer.getServer().getStart_CmdId()));
			if (appServer.getStop_CmdSet()!=null)
				result.setStopExecId(Long.parseLong(appServer.getServer().getStop_CmdId()));
			result.setUrl(appServer.getCheck_Url());
			ServerControlInfo[] controlInfos=new ServerControlInfo[1];
			controlInfos[0]=result;
			ServerControlInfo[] infos=ServerControlApi.monitorServerControl(threadCount.intValue(),timeOut.intValue(),controlInfos);
			result=infos[0];
		}catch(Exception e){
			log.error("Call APIShowServerStatusSVImpl's Method getAppServerState has Exception :"+e.getMessage());
		}
		return result;
	}
}
