package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIJvmCapabilitySV;
import com.asiainfo.monitor.interapi.config.AIMemoryInfo;
import com.asiainfo.monitor.interapi.config.AIThreadInfo;
import com.asiainfo.monitor.tools.common.KeyName;

public class JvmCapabilityAction extends BaseAction {

	private static transient Log log=LogFactory.getLog(JvmCapabilityAction.class);
	
	/**
	 * 返回服务器内存信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getJVM5MemoryInfo(String appId) throws Exception{
		List result = null;
		try{
			IAPIJvmCapabilitySV jvmCapabilitySV=(IAPIJvmCapabilitySV)ServiceFactory.getService(IAPIJvmCapabilitySV.class);
			AIMemoryInfo memoryInfo = jvmCapabilitySV.getJVM5MemoryInfo(appId);
			if (memoryInfo != null){
				result = new ArrayList(1);
				Map map = new HashMap();
				map.put("TOTAL", new Long(memoryInfo.getTotal()/(1024*1024)));
				map.put("USED", new Long(memoryInfo.getUsed()/(1024*1024)));
				map.put("STEP", "");
				result.add(map);
			}
		}catch(Exception e){
			log.error("Call ServerAction's Method getJVM5MemoryInfo has Exception :"+e.getMessage());
		}
		return result;		
	}
	
	/**
	 * 获取多应用的Jvm信息
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getJVM5MemoryInfos(Object[] appIds) throws Exception{
		List result = null;
		try{
			IAPIJvmCapabilitySV jvmCapabilitySV = (IAPIJvmCapabilitySV)ServiceFactory.getService(IAPIJvmCapabilitySV.class);
			result = jvmCapabilitySV.getJVM5MemoryInfos(appIds);
		}catch(Exception e){
			log.error("Call ServerAction's Method getJVM5MemoryInfos has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取服务器线程信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getThreadInfo(String appId,String name) throws Exception {
		List result=null;
		try{
			
			IAPIJvmCapabilitySV jvmCapabilitySV=(IAPIJvmCapabilitySV)ServiceFactory.getService(IAPIJvmCapabilitySV.class);
			AIThreadInfo[] threadInfo=jvmCapabilitySV.getThreadInfo(appId,name);
			if (threadInfo!=null){				
				result=new ArrayList(threadInfo.length);
				for (int i=0;i<threadInfo.length;i++){
					if (StringUtils.isNotBlank(name)){
						if (threadInfo[i].getName().indexOf(name)>=0){
							Map map=new HashMap();
							map.put("INDEX_ID",i+"");
							map.put("ID",new Long(threadInfo[i].getId()));
							map.put("NAME",threadInfo[i].getName());
							map.put("STATE",threadInfo[i].getState());
							map.put("STACK",threadInfo[i].getStack());
							result.add(map);
						}
					}else{
						Map map=new HashMap();
						map.put("INDEX_ID",i+"");
						map.put("ID",new Long(threadInfo[i].getId()));
						map.put("NAME",threadInfo[i].getName());
						map.put("STATE",threadInfo[i].getState());
						map.put("STACK",threadInfo[i].getStack());
						result.add(map);
					}
					
				}
				
			}
		}catch(Exception e){
			log.error("Call ServerAction's Method getThreadInfo has Exception :"+e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 获取服务器线程信息(AppFrame)
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getThreadInfoForAppFrame(String appId,String name) throws Exception {
		List result=null;
		try{
			
			IAPIJvmCapabilitySV jvmCapabilitySV = (IAPIJvmCapabilitySV)ServiceFactory.getService(IAPIJvmCapabilitySV.class);
			AIThreadInfo[] threadInfo = jvmCapabilitySV.getThreadInfo(appId,name);
			if (threadInfo != null){				
				result = new ArrayList(threadInfo.length);
				for (int i=0;i<threadInfo.length;i++){
					if (StringUtils.isNotBlank(name)){
						if (threadInfo[i].getName().indexOf(name)>=0){
							DataContainerInterface item = new DataContainer();
							item.set("INDEX_ID",i+"");
							item.set("ID",new Long(threadInfo[i].getId()));
							item.set("NAME",threadInfo[i].getName());
							item.set("STATE",threadInfo[i].getState());
							item.set("STACK",threadInfo[i].getStack());
							result.add(item);
						}
					}else{
						DataContainerInterface item = new DataContainer();
						item.set("INDEX_ID",i+"");
						item.set("ID",new Long(threadInfo[i].getId()));
						item.set("NAME",threadInfo[i].getName());
						item.set("STATE",threadInfo[i].getState());
						item.set("STACK",threadInfo[i].getStack());
						result.add(item);
					}
					
				}
				
			}
		}catch(Exception e){
			log.error("Call ServerAction's Method getThreadInfo has Exception :"+e.getMessage());
		}
		return (DataContainerInterface[]) result.toArray(new DataContainerInterface[0]);
	}
	
	/**
	 * 获取系统信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getSystemInfo(String appId,String findKey) throws Exception{
		List result=null;
		try{
			IAPIJvmCapabilitySV jvmCapabilitySV=(IAPIJvmCapabilitySV)ServiceFactory.getService(IAPIJvmCapabilitySV.class);
			KeyName[] infos=jvmCapabilitySV.getSystemInfo(appId,findKey);
			if (infos!=null && infos.length>0){
				result=new ArrayList();
				for (int i=0;i<infos.length;i++){
					result.add(infos[i]);
				}
			}
		}catch(Exception e){
			log.error("Call ServerAction's Method getSystemInfo has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取系统信息(AppFrame)
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getSystemInfoForAppFrame(String appId,String findKey) throws Exception{
		DataContainerInterface[] retDataInfo = null;
		try{
			IAPIJvmCapabilitySV jvmCapabilitySV=(IAPIJvmCapabilitySV)ServiceFactory.getService(IAPIJvmCapabilitySV.class);
			retDataInfo = jvmCapabilitySV.getSystemInfoForAppFrame(appId, findKey);
		}catch(Exception e){
			log.error("Call ServerAction's Method getSystemInfo has Exception :"+e.getMessage());
		}
		return retDataInfo;
	}
	
	/**
	 * 获取服务器信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getServerInfo(String appId) throws Exception{
		List result=null;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=serverSV.getServerByServerId(appId);
			if (appServer==null)
				return null;
			result=new ArrayList(1);
			Map map=new HashMap(8);
			map.put("APP_ID",new Long(appServer.getApp_Id()));
			map.put("APP_CODE",appServer.getApp_Code());
			map.put("APP_NAME",appServer.getApp_Name());
			if (appServer.getPhysicHost()==null){
                map.put("SERVER_ID", appServer.getPhysicHost().getHostId());
                map.put("SERVER_IP", appServer.getPhysicHost().getHostIp());
                map.put("SERVER_NAME", appServer.getPhysicHost().getHostName());
			}
			
			map.put("GROUP_ID",new Long(appServer.getNodeConfig().getGroupConfig().getGroup_Id()));
			map.put("GROUP_NAME",appServer.getNodeConfig().getGroupConfig().getGroup_Name());
			map.put("STATE",appServer.getState());
			result.add(map);
		}catch(Exception e){
			log.error("Call ServerAction's Method getServerInfo has Exception :"+e.getMessage());
		}
		return result;
	}
}
