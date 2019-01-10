package com.asiainfo.monitor.busi.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.DataStructInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowServerStatusSV;
import com.asiainfo.monitor.interapi.ServerControlInfo;

public class ServerControlAction extends BaseAction {

	private static transient Log log=LogFactory.getLog(ServerControlAction.class);
	
	/**
	 * 根据服务器组读取服务器信息
	 * @param groupId
	 * @param appServerName
	 * @return
	 * @throws Exception
	 */
	public DataStructInterface[] getAppServerByGroupId(String groupId,String appServerName,Integer threadCount,Integer timeOut,String isSelected) throws Exception {
		if (StringUtils.isBlank(groupId))
			return null;
		DataStructInterface[] result = null;		
		try{
			IAPIShowServerStatusSV showServerStatusSV=(IAPIShowServerStatusSV)ServiceFactory.getService(IAPIShowServerStatusSV.class);			
			result=showServerStatusSV.getAppServerByGroupId(groupId,appServerName,threadCount,timeOut,isSelected);
		}catch(Exception e){
			log.error("Call ServerControlAction's Method getAppServerByGroupId has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 计算ORB的ID
	 * @param appNames
	 * @return
	 * @throws Exception
	 */
	public List comptuerOrbId(Object[] appNames) throws Exception {
		List result=null;
		try{
			IAPIShowServerStatusSV showServerStatusSV=(IAPIShowServerStatusSV)ServiceFactory.getService(IAPIShowServerStatusSV.class);	
		    result=showServerStatusSV.comptuerOrbId(appNames);
		}catch(Exception e){
			log.error("Call ServerControlAction's Method comptuerOrbId has Exception :"+e.getMessage());
		}
		return result;
	}
	
	
	public List getAppState(Object[] appIds,Integer threadCount,Integer timeOut) throws Exception {
		List result=null;
		try{
			if (appIds==null || appIds.length<0){
				return result;
			}
			IAPIShowServerStatusSV showServerStatusSV=(IAPIShowServerStatusSV)ServiceFactory.getService(IAPIShowServerStatusSV.class);	
			result=showServerStatusSV.getAppServerState(appIds,threadCount,timeOut);
			
		}catch(Exception e){
			log.error("Call ServerControlAction's Method getAppState has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 检查服务器状态
	 * @param appId
	 * @param threadCount
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public int getAppServerState(String appId,Integer threadCount,Integer timeOut) throws Exception {
		int result=0;
		try{			
			IAPIShowServerStatusSV showServerStatusSV=(IAPIShowServerStatusSV)ServiceFactory.getService(IAPIShowServerStatusSV.class);	
			ServerControlInfo info=showServerStatusSV.getAppServerState(appId,threadCount,timeOut);
			if (info.getStatus()!=null && info.getStatus().equalsIgnoreCase("OK")){
				result=1;
			}else{
				result=-1;
			}
		}catch(Exception e){
			log.error("Call ServerControlAction's Method getAppServerState has Exception :"+e.getMessage());
		}
		return result;
	}
	

	
}
