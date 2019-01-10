package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowTraceSV;
import com.asiainfo.monitor.interapi.api.trace.AppTraceMonitorApi;
import com.asiainfo.monitor.interapi.api.trace.WebTraceMonitorApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

public class APIShowTraceSVImpl implements IAPIShowTraceSV {

	private static transient Log log=LogFactory.getLog(APIShowTraceSVImpl.class);
	
	/**
	 * 根据应用标识读取AppTrace的状态、内容
	 * @param appServerIds
	 * @return
	 */
	public List getAppTraces(Object[] appServerIds) throws RemoteException,Exception{
		List result = new ArrayList();
		try{
			if (appServerIds==null || appServerIds.length<1)
				return result;
			String className="";
			String userCode="";
			String methodName="";
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			for(int i=0;i<appServerIds.length;i++){
				ServerConfig appServer=serverSV.getServerByServerId(String.valueOf(appServerIds[i]));
				if (appServer==null){
					// "没有定义应用[appId:"+appServerIds[i]+"]"
					log.error(AIMonLocaleFactory.getResource("MOS0000070", (String)appServerIds[i]));
					continue;
				}
				if (appServer.getJmxSet()==null || appServer.getJmxSet().getValue().equals("OFF") || 
						appServer.getJmxSet().getValue().equals("FALSE")){
					// "指定应用[appId:"+appServerIds[i]+"]没有启动jmx注册"
					log.error(AIMonLocaleFactory.getResource("MOS0000071", (String)appServerIds[i]));
					continue;
				}
				boolean isConnect= false;
				boolean isEnable = false;
				try{
					String setInfo = AppTraceMonitorApi.getAllSetInfo(appServer.getLocator_Type(),appServer.getLocator());
					String[] sl=StringUtils.split(setInfo, TypeConst._SPLIT_CHAR);
					if (sl==null || sl.length<1 || sl.length<4)
						continue;
					userCode= sl[0];
					className=sl[1];
					methodName= sl[2];
					if (Boolean.valueOf(sl[3])){
						isEnable=true;
					}else{
						isEnable=false;
					}
					isConnect = true;
				}catch(Exception e){
					isConnect = false;
					// "连接失败:"
					log.error(AIMonLocaleFactory.getResource("MOS0000143")+e.getMessage());
				}
				HashMap tmpMap = new HashMap();
				tmpMap.put("CLASS_NAME", className.equals("null")?"":className);
				tmpMap.put("USER_CODE", userCode.equals("null")?"":userCode);
				if(isEnable){
					tmpMap.put("IS_ENABLE", AIMonLocaleFactory.getResource("MOS0000144"));
				}else if(isConnect==false){
					tmpMap.put("IS_ENABLE", AIMonLocaleFactory.getResource("MOS0000145"));
				}else{
					tmpMap.put("IS_ENABLE", AIMonLocaleFactory.getResource("MOS0000146"));
				}
				tmpMap.put("METHOD_NAME", methodName.equals("null")?"":methodName);
				tmpMap.put("APP_ID", appServer.getApp_Id());
				tmpMap.put("SERVER_IP", appServer.getApp_Ip());
				tmpMap.put("APP_NAME", appServer.getApp_Name());
				result.add(tmpMap);
			}

		}catch (Exception e) {
			log.error("Call APIShowTraceSVImpl's Method getAppTraces has Exception :"+e.getMessage());			
		}
		return result;
	}
	
	/**
	 * 根据应用标识，读取WebTrace状态、信息
	 * @param appServerIds
	 * @return
	 */
	public List getWebTraces(Object[] appServerIds) throws RemoteException,Exception{
		List result = new ArrayList();
		try{
			if (appServerIds==null || appServerIds.length<1)
				return result;
			String userCode="";
			String custIp  ="";
			String url	   ="";
			String time	   ="";
			boolean isConnect= false;
			boolean isEnable = false;
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			for(int i=0;i<appServerIds.length;i++){
				ServerConfig appServer=serverSV.getServerByServerId(String.valueOf(appServerIds[i]));
				if (appServer==null){
					// "没有定义应用[appId:"+appServerIds[i]+"]"
					log.error(AIMonLocaleFactory.getResource("MOS0000070", (String)appServerIds[i]));
					continue;
				}
				if (appServer.getJmxSet()==null || appServer.getJmxSet().getValue().equals("OFF") || 
						appServer.getJmxSet().getValue().equals("FALSE")){
					// "指定应用[appId:"+appServerIds[i]+"]没有启动jmx注册"
					log.error(AIMonLocaleFactory.getResource("MOS0000071", (String)appServerIds[i]));
					continue;
				}
				try{
					String setInfo = WebTraceMonitorApi.getAllSetInfo(appServer.getLocator_Type(),appServer.getLocator());
					String[] sl=StringUtils.split(setInfo, TypeConst._SPLIT_CHAR);
					if (sl==null || sl.length<1 || sl.length<5)
						continue;
					userCode =sl[0];					
					url		 =sl[1];
					custIp	 =sl[2];
					time	 =sl[3];
					if (Boolean.valueOf(sl[4])){
						isEnable=true;
					}else{
						isEnable=false;
					}
					isConnect = true;
				}catch(Exception e){
					isConnect = false;
					log.error(AIMonLocaleFactory.getResource("MOS0000143")+e.getMessage());
				}
				HashMap tmpMap = new HashMap();

				tmpMap.put("USER_CODE", userCode);
				if(isEnable){
					tmpMap.put("IS_ENABLE", AIMonLocaleFactory.getResource("MOS0000144"));
				}else if(isConnect==false){
					tmpMap.put("IS_ENABLE", AIMonLocaleFactory.getResource("MOS0000145"));
				}else{
					tmpMap.put("IS_ENABLE", AIMonLocaleFactory.getResource("MOS0000146"));
				}
				
				tmpMap.put("USER_CODE", userCode.equals("null")?"":userCode);
				tmpMap.put("CUST_IP", custIp.equals("null")?"":custIp);
				tmpMap.put("URL", url.equals("null")?"":url);
				tmpMap.put("TIME", time);
				tmpMap.put("APP_ID", appServer.getApp_Id());
				tmpMap.put("APP_NAME", appServer.getApp_Name());
				tmpMap.put("SERVER_IP", appServer.getApp_Ip());
				result.add(tmpMap);
			}

		}catch (Exception e) {
			log.error("Call APIShowTraceSVImpl's Method getWebTraces has Exception :"+e.getMessage());
		}
		return result;
	}
	
	public  static void main(String[] args) {
		try{
			String aa="f888888|";
			String[] bb=StringUtils.split(aa,TypeConst._SPLIT_CHAR);
			for (int i=0;i<bb.length;i++){
				System.out.println(bb[i]);
			}
			System.out.println("--------------------------------");
			String[] cc=aa.split(TypeConst._SPLIT_CHAR);
			for (int i=0;i<cc.length;i++){
				System.out.println(cc[i]);
			}
		}catch(Exception e){
			
		}
	}
}
