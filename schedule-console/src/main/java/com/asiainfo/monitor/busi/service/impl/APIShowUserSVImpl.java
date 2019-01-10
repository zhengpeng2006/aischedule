package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowUserSV;
import com.asiainfo.monitor.busi.stat.UserMultitaskStat;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.interapi.api.user.UserManagerMonitorApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class APIShowUserSVImpl implements IAPIShowUserSV {

	private final static transient Log log=LogFactory.getLog(APIShowUserSVImpl.class);
	
	/**
	 * 获取在线用户信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getOnlineUsers(String appId) throws RemoteException,Exception {
		if (StringUtils.isBlank(appId))
			return null;
		List result=null;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=serverSV.getServerByServerId(appId);
			if (appServer==null)
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
			if (appServer.getJmxSet()==null ){
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
			if (appServer.getJmxSet().getValue().equalsIgnoreCase("ON") ||
					appServer.getJmxSet().getValue().equalsIgnoreCase("TRUE")){
				result=UserManagerMonitorApi.getOnlineUsers(appServer.getLocator_Type(),appServer.getLocator());
			}else{
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
						
		}catch(Exception e){
			log.error("Call UserManagerMonitorAction's Method getOnlineUsers has Exception :"+e.getMessage());
		}
		return result;
	}

	/**
	 * 根据服务器分组获取在线用户信息
	 * 
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getOnlineUsers(Object[] appIds) throws RemoteException,Exception {
		List result = null;		
		try {
			if (appIds != null && appIds.length>0){
				result = new ArrayList();
				int threadCount = (appIds.length>>1)+1;
				int cpuCount = Runtime.getRuntime().availableProcessors();
				if (threadCount > (cpuCount*3)){
					threadCount = cpuCount*3;
				}
				UserMultitaskStat userStat = new UserMultitaskStat(threadCount,-1,appIds);
				Map userInfos = userStat.getSummary();
				if (userInfos != null && userInfos.size() > 0){
					Iterator it = userInfos.entrySet().iterator();
					while (it.hasNext()){
						Entry entry = (Entry)it.next();
						List serverUsers = (List)entry.getValue();
						result.addAll(serverUsers);
					}
				}
			}
		} catch (Exception e) {
			log.error("Call APIShowUserSVImpl Method getOnlineUsers has Exception :"+e.getMessage());
		}

		return result;
	}
	
	
	/**
	 * 根据服务器分组,地区、组织查询在线用户信息
	 * @param appId
	 * @param regionId:查询全部为0
	 * @param orgName:组织名称，模糊匹配
	 * @return
	 * @throws Exception
	 */
	public List getOnlineUsersByRegionAndOrgName(Object[] appIds,String regionId,String orgName,String ipRep) throws RemoteException,Exception {
		List result = null;
		try {
			List userArray = getOnlineUsers(appIds);
			//如果查全部地区，不分组织，则直接返回所有在线用户记录
			if (regionId.equals("0") && StringUtils.isBlank(orgName) && StringUtils.isBlank(ipRep)){
				return userArray;
			}
			
			if (userArray != null){
				result = new ArrayList();
				for (int i=0;i<userArray.size();i++){
					Map attrMap = (Map)((Map)userArray.get(i)).get("ATTRS");
				    String realRegionId = ((String)attrMap.get("REGION_ID")).trim().toUpperCase();
				    String org = String.valueOf(((Map)userArray.get(i)).get("ORG_NAME"));
				    String ip = String.valueOf(((Map)userArray.get(i)).get("IP"));
				    if (regionId.equals("0")){
				    	if (!StringUtils.isBlank(orgName)){
				    		if (org.indexOf(orgName)>=0){
					    		if (StringUtils.isBlank(ipRep)){
					    			result.add(userArray.get(i));
					    		}else{
					    			if (ip.indexOf(ipRep)>=0){
					    				result.add(userArray.get(i));
					    		
					    			}
					    		}
					    	}
				    	}else{
				    		if (StringUtils.isBlank(ipRep)){
					    		result.add(userArray.get(i));
					    	}else if (ip.indexOf(ipRep)>=0)
			    				result.add(userArray.get(i));
				    	}
				    	
				    }else{
				    	if (StringUtils.isBlank(orgName)){
				    		if (realRegionId.equals(regionId.toUpperCase())){
				    			if (StringUtils.isBlank(ipRep)){
				    				result.add(userArray.get(i));
				    			}else if (ip.indexOf(ipRep)>=0){
				    				result.add(userArray.get(i));
				    			}
				    		}
				    	}else{
				    		if (realRegionId.equals(regionId.toUpperCase()) && org.indexOf(orgName)>=0){
				    			if (StringUtils.isBlank(ipRep)){
				    				result.add(userArray.get(i));
				    			}else if (ip.indexOf(ipRep)>=0){
				    				result.add(userArray.get(i));
				    			}
				    		}
				    	}
				    }				    
				}
			}
		} catch (Exception e) {
			log.error("Call APIShowUserSVImpl Method getOnlineUsersByRegionAndOrgName has Exception :"+e.getMessage());
		}

		return result;
	}
	
	/**
	 * 得到用户汇总信息,地区、终端数、用户数
	 * @param appIds
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public List getCollectOnlineUsers(Object[] appIds)  throws RemoteException,Exception{
		List result=new ArrayList();
		try {
			List userArray=getOnlineUsers(appIds);
			IAIMonStaticDataSV staticDataSV=(IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
			IBOAIMonStaticDataValue[] regionValues=staticDataSV.queryByCodeType("REGION_MAP");
			Map regionDefineMap=new HashMap();
			for (int i=0;i<regionValues.length;i++){
				regionDefineMap.put(regionValues[i].getCodeValue().toUpperCase(), regionValues[i].getCodeName());
			}
			Map regionUsersMap=new HashMap();
			Map regionIpMap=new HashMap();
			if (userArray!=null){
				for (int i=0;i<userArray.size();i++){
					Map attrMap = (Map)((Map)userArray.get(i)).get("ATTRS");
				    String realRegionId = ((String)attrMap.get("REGION_ID")).trim();
				    String ip=String.valueOf(((Map)userArray.get(i)).get("IP"));
				    //设置地区、人员总数
				    if (regionUsersMap.containsKey(realRegionId)){
				    	Integer uCount=(Integer)regionUsersMap.get(realRegionId);
				    	uCount=uCount+1;
				    	regionUsersMap.put(realRegionId,uCount);				    	
				    }else{
				    	Integer uCount=new Integer(1);
				    	regionUsersMap.put(realRegionId,uCount);				    	
				    }
				    //设置地区、终端总数
				    if (regionIpMap.containsKey(realRegionId)){				    	
				    	List ipList=(List)regionIpMap.get(realRegionId);
				    	if (ipList==null){
				    		ipList=new ArrayList();
				    		ipList.add(ip);
				    		regionIpMap.put(realRegionId, ipList);
				    	}else{
				    		if (!ipList.contains(ip)){
				    			ipList.add(ip);
				    			regionIpMap.put(realRegionId, ipList);
				    		}
				    	}
				    }else{
				    	List ipList=new ArrayList();
				    	ipList.add(ip);
				    	regionIpMap.put(realRegionId, ipList );
				    }
				}
			}
			
			for (Iterator it=regionUsersMap.keySet().iterator();it.hasNext();){
				String regionId=String.valueOf(it.next());
				String regionName="";
				if (regionDefineMap.containsKey(regionId.toUpperCase())){
					regionName=String.valueOf(regionDefineMap.get(regionId.toUpperCase()));
				}
				int userSize=((Integer)regionUsersMap.get(regionId)).intValue();
				List ipList=(List)regionIpMap.get(regionId);
				int ipSize=ipList!=null && ipList.size()>0?ipList.size():userSize;
				Map item=new HashMap();
				item.put("REGION_ID", regionId);
				item.put("REGION_NAME", regionName);
				item.put("USERCOUNT", userSize);
				item.put("IPCOUNT", ipSize);
				result.add(item);
			}
			
		} catch (Exception e) {
			log.error("Call APIShowUserSVImpl Method getCollectOnlineUsers has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取多应用的在线用户统计
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getServerOnlineUsers(Object[] appIds) throws RemoteException,Exception{
		List result=null;		
		try {
			if (appIds!=null && appIds.length>0){
				result = new ArrayList();
				int threadCount=(appIds.length>>1)+1;
				int cpuCount=Runtime.getRuntime().availableProcessors();
				if (threadCount> (cpuCount*3))
					threadCount=cpuCount*3;
				UserMultitaskStat userStat=new UserMultitaskStat(threadCount,-1,appIds);
				Map userInfos=userStat.getSummary();
				if (userInfos!=null && userInfos.size()>0){
					Iterator it=userInfos.entrySet().iterator();
					while (it.hasNext()){
						Map itemStat=new HashMap();
						Entry entry=(Entry)it.next();
						itemStat.put("SERVER_NAME",entry.getKey());
						itemStat.put("USERCOUNT",((List)entry.getValue()).size());
						List ipList=new ArrayList();
						for (int uCount=0;uCount<((List)entry.getValue()).size();uCount++){
							String ip=String.valueOf(((Map)((List)entry.getValue()).get(uCount)).get("IP"));
							if (!ipList.contains(ip)){
								ipList.add(ip);
							}
						}
						itemStat.put("IPCOUNT", ipList.size());
						result.add(itemStat);
					}
				}
			}
			
		} catch (Exception e) {
			log.error("Call APIShowUserSVImpl Method getServerOnlineUsers has Exception :"+e.getMessage());
		}

		return result;
	}
}
