package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.asyn.operation.impl.WebConnectAppAsynOperate;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowAppSwitchSV;
import com.asiainfo.monitor.interapi.config.AppConnectInfo;
import com.asiainfo.monitor.interapi.config.WebConnectApp;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.SimpleResult;

public class APIShowAppSwitchSVImpl implements IAPIShowAppSwitchSV{

	private static transient Log log=LogFactory.getLog(APIShowAppSwitchSVImpl.class);
	
	
	public List getWebConnectApp(Object[] appIds) throws RemoteException,Exception{
		List result=new ArrayList();		
		try{
			WebConnectAppAsynOperate asynOperate=new WebConnectAppAsynOperate();
			int threadCount=(appIds.length>>1)+1;
			int cpuCount=Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3))
				threadCount=cpuCount*3;
			List list=asynOperate.asynOperation(threadCount,-1,appIds,-1);
			if (list!=null && list.size()>0){
				for (int i=0;i<list.size();i++){
					SimpleResult sr=(SimpleResult)list.get(i);
					if (sr!=null && sr.isSucc()){
						if (sr.getValue()!=null){
							result.add(sr.getValue());
						}
					}
				}
			}
		}catch(Exception e){
			log.error("Call APIShowAppSwitchSVImpl Method getWebConnectApp has Exception :"+e.getMessage());
		}
		return result;
	}
	
	
	public List getAppConnectInfo(Object[] appIds) throws RemoteException,Exception{
		List result=new ArrayList();		
		try{
			WebConnectAppAsynOperate asynOperate=new WebConnectAppAsynOperate();
			int threadCount=(appIds.length>>1)+1;
			int cpuCount=Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3))
				threadCount=cpuCount*3;
			List list=asynOperate.asynOperation(threadCount,-1,appIds,-1);
			Map clusterMap=new HashMap();
			Map canSetClusterMap=new HashMap();
			if (list!=null && list.size()>0){
				for (int i=0;i<list.size();i++){
					SimpleResult sr=(SimpleResult)list.get(i);
					if (sr!=null && sr.isSucc()){
						if (sr.getValue()!=null){
							String oldClusterAppCode=((WebConnectApp)sr.getValue()).getOldAppClusterCode();
							if (StringUtils.isBlank(oldClusterAppCode))
								continue;
							List serverList=null;
							if (clusterMap.containsKey(oldClusterAppCode)){
								serverList=(List)clusterMap.get(oldClusterAppCode);								
							}else{
								serverList=new ArrayList();
							}
							serverList.add(new KeyName(((WebConnectApp)sr.getValue()).getServerId(),((WebConnectApp)sr.getValue()).getServerName()));
							clusterMap.put(oldClusterAppCode, serverList);
							Map newClusterMap=null;
							if (canSetClusterMap.containsKey(oldClusterAppCode)){
								newClusterMap=(Map)canSetClusterMap.get(oldClusterAppCode);
							}else{
								newClusterMap=new HashMap();
							}
							String[] canSetClusterAppCodes=((WebConnectApp)sr.getValue()).getCanSetAppClusterCode();
							String[] canSetClusterAppEnvs=((WebConnectApp)sr.getValue()).getCanSetAppClusterEnv();
							for (int canCount=0;canSetClusterAppCodes!=null && canCount<canSetClusterAppCodes.length;canCount++){
								newClusterMap.put(canSetClusterAppCodes[canCount], canSetClusterAppEnvs[canCount]);
							}
							canSetClusterMap.put(oldClusterAppCode, newClusterMap);
						}
					}
				}
			}
			
			for (Iterator it=clusterMap.keySet().iterator();it.hasNext();){
				String oldClusterAppCode=(String)it.next();
				List serverList=(List)clusterMap.get(oldClusterAppCode);
				AppConnectInfo appConnectInfo=new AppConnectInfo();
				appConnectInfo.setAppClusterCode(oldClusterAppCode);
				appConnectInfo.setConnectServers((KeyName[])serverList.toArray(new KeyName[0]));
				Map newClusterMap=(Map)canSetClusterMap.get(oldClusterAppCode);
				
				if (newClusterMap!=null){
					appConnectInfo.setCanSetAppClusterCode((String[])newClusterMap.keySet().toArray(new String[0]));
					appConnectInfo.setCanSetAppClusterEnv((String[])newClusterMap.values().toArray(new String[0]));					
				}
				result.add(appConnectInfo);
			}
		}catch(Exception e){
			log.error("Call APIShowAppSwitchSVImpl Method getAppConnectInfo has Exception :"+e.getMessage());
		}
		return result;
	}
}
