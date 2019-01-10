package com.asiainfo.monitor.busi.service.impl;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerRouteSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlTraceSV;
import com.asiainfo.monitor.busi.stat.control.ServerStatControl;
import com.asiainfo.monitor.busi.stat.control.ServerStatControl.TraceControlEnum;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonServerRouteValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

public class APIControlTraceSVImpl implements IAPIControlTraceSV {

	private static transient Log log=LogFactory.getLog(APIControlTraceSVImpl.class);
	
	/**
	 * 启动选择web应用的webtrace，并且根据路由信息，找到路由到的app服务器，并且打开apptrace
	 * 联动操作/机动操作
	 * @param ids
	 * @param driveflag:联动标志，为Y时，代表如果是web应用，必须找出路由的App应用，并启动Apptrace
	 * @param time
	 * @param userCode
	 * @param url
	 * @param custIp
	 * @param code
	 * @param className
	 * @param methodName
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public List startTraceByDrive(Object[] ids,String driveflag,String time,String userCode,String url,String custIp,String code,String className,String methodName) throws RemoteException,Exception{
		List result = new ArrayList();
		try{
			if (ids == null || ids.length < 1){
				// "启动AppTrace请提供操作应用"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000136"));
			}
			
			if (StringUtils.isBlank(code) || StringUtils.isBlank(className) || StringUtils.isBlank(methodName)){
				// 请提供code,class,mehtod参数
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000138"));
			}
			
			//需要启动WebTrace的标识列表
			List webIdList = new ArrayList();
			//需要启动AppTrace的标识列表
			List appIdList = new ArrayList();
			
			
			IAIMonServerSV serverSV = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			IAIMonServerRouteSV routeSV = (IAIMonServerRouteSV)ServiceFactory.getService(IAIMonServerRouteSV.class);
			for (int i=0;i<ids.length;i++){
				ServerConfig appServer = serverSV.getServerByServerId(String.valueOf(ids[i]));
				if (appServer.getTemp_Type().equals(TypeConst.WEB)){
					if (StringUtils.isNotBlank(userCode) && !webIdList.contains(appServer.getApp_Id())){
						webIdList.add(appServer.getApp_Id());
					}
					if (!StringUtils.isBlank(driveflag) && (driveflag.equalsIgnoreCase("Y") || driveflag.equals("1"))){
						IBOAIMonServerRouteValue[] routeAppValues = routeSV.getMonServerRouteBySServId(appServer.getApp_Id());
						if (routeAppValues != null && routeAppValues.length > 0){
							for (int j=0;j<routeAppValues.length;j++){
								if (!appIdList.contains(routeAppValues[j].getDServId()+"")){
									appIdList.add(routeAppValues[j].getDServId()+"");
								}
							}
						}
					}
				}else if (appServer.getTemp_Type().equals(TypeConst.APP)){
					if (!appIdList.contains(appServer.getApp_Id())){
						appIdList.add(appServer.getApp_Id());
					}
				}else if (appServer.getTemp_Type().equals(TypeConst.BOTH)){
					if (!webIdList.contains(appServer.getApp_Id())){
						webIdList.add(appServer.getApp_Id());
					}
					if (!appIdList.contains(appServer.getApp_Id())){
						appIdList.add(appServer.getApp_Id());
					}
				}
			}
			
			String[] webIds = (String[])webIdList.toArray(new String[0]);
			String[] appIds = (String[])appIdList.toArray(new String[0]);
			if (webIds != null && webIds.length > 0){
				int threadCount = (webIds.length>>1)+1;
				if (StringUtils.isBlank(url)){
					url = null;
				}
				if (StringUtils.isBlank(custIp)){
					custIp = null;
				}
				result.addAll(ServerStatControl.controlTraceStatus(threadCount,-1,webIds,userCode,custIp,url,null,null,time,TraceControlEnum.START_WEBTRACE));
			}
			if (appIds != null && appIds.length>0){
				int threadCount = (appIds.length>>1)+1;
				int cpuCount = Runtime.getRuntime().availableProcessors();
				if (threadCount > (cpuCount*3)){
					threadCount = cpuCount*3;
				}
				result.addAll(ServerStatControl.controlTraceStatus(threadCount,-1,appIds,code,null,null,className,methodName,"0",TraceControlEnum.START_APPTRACE));
			}
		}catch (Exception e) {
			log.error("Call APIControlTraceSVImpl's Method startTraceByDrive has Exception :"+e.getMessage());	
		}
		return result;
	}
	
	/**
	 * 联动停止Trace，在停止webtrace的过程中，可以同时停止路由的app应用的apptrace
	 * ids不限制为web应用的标识，也可以是app应用的标识或者类型为BOTH的应用标识
	 * @param ids
	 * @param driveflag
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public List stopTraceByDrive(Object[] ids,String driveflag) throws RemoteException,Exception{
		List result=new ArrayList();
		try{
			if (ids==null || ids.length<1)
				// "启动AppTrace请提供操作应用"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000136"));
			
			//需要启动WebTrace的标识列表
			List webIdList=new ArrayList();
			//需要启动AppTrace的标识列表
			List appIdList=new ArrayList();
			
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			IAIMonServerRouteSV routeSV=(IAIMonServerRouteSV)ServiceFactory.getService(IAIMonServerRouteSV.class);
			for (int i=0;i<ids.length;i++){
				
				ServerConfig appServer=serverSV.getServerByServerId(String.valueOf(ids[i]));
				if (appServer.getTemp_Type().equals(TypeConst.WEB)){
					if (!webIdList.contains(appServer.getApp_Id()))
						webIdList.add(appServer.getApp_Id());
					if (!StringUtils.isBlank(driveflag) && (driveflag.equalsIgnoreCase("Y") || driveflag.equals("1"))){
						IBOAIMonServerRouteValue[] routeAppValues=routeSV.getMonServerRouteBySServId(appServer.getApp_Id());
						if (routeAppValues!=null && routeAppValues.length>0){
							for (int j=0;j<routeAppValues.length;j++){
								if (!appIdList.contains(routeAppValues[j].getDServId()+"")){
									appIdList.add(routeAppValues[j].getDServId()+"");
								}
							}
						}
					}
					
				}else if (appServer.getTemp_Type().equals(TypeConst.BOTH)){
					if (!webIdList.contains(appServer.getApp_Id()))
						webIdList.add(appServer.getApp_Id());
					if (!appIdList.contains(appServer.getApp_Id())){
						appIdList.add(appServer.getApp_Id());
					}
				}else if (appServer.getTemp_Type().equals(TypeConst.APP)){					
					if (!appIdList.contains(appServer.getApp_Id())){
						appIdList.add(appServer.getApp_Id());
					}
				}
			}
			
			String[] webIds=(String[])webIdList.toArray(new String[0]);
			String[] appIds=(String[])appIdList.toArray(new String[0]);
			//停止WebTrace
			if (webIds!=null && webIds.length>0){
				int threadCount=(webIds.length>>1)+1;				
				result.addAll(ServerStatControl.controlTraceStatus(threadCount,-1,webIds,null,null,null,null,null,"0",TraceControlEnum.STOP_WEBTRACE));
			}
			//停止AppTrace
			if (appIds!=null && appIds.length>0){
				int threadCount=(appIds.length>>1)+1;
				int cpuCount=Runtime.getRuntime().availableProcessors();
				if (threadCount> (cpuCount*3))
					threadCount=cpuCount*3;
				result.addAll(ServerStatControl.controlTraceStatus(threadCount,-1,appIds,null,null,null,null,null,"0",TraceControlEnum.STOP_APPTRACE));
			}
			
		}catch (Exception e) {
			log.error("Call APIControlTraceSVImpl's Method stopTraceByDrive has Exception :"+e.getMessage());	
		}
		return result;
	}
	
	/**
	 * 启动Trace
	 * @param ids
	 * @param code
	 * @param className
	 * @param methodName
	 * @return
	 */
	public List startAppTrace(Object[] ids,String code,String className,String methodName) throws RemoteException,Exception{
		List result=null;
		try{
			if (ids==null || ids.length<1)
				// "启动AppTrace请提供操作应用"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000136"));
			if (StringUtils.isBlank(code) || StringUtils.isBlank(className) || StringUtils.isBlank(methodName))
				// 请提供code,class,mehtod参数
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000138"));
			int threadCount=(ids.length>>1)+1;
			int cpuCount=Runtime.getRuntime().availableProcessors();
			if (threadCount> (cpuCount*3))
				threadCount=cpuCount*3;
			result=ServerStatControl.controlTraceStatus(threadCount,-1,ids,code,null,null,className,methodName,"0",TraceControlEnum.START_APPTRACE);
			
		}catch (Exception e) {
			log.error("Call APIControlTraceSVImpl's Method startAppTrace has Exception :"+e.getMessage());	
		}
		return result;
	}
	
	/**
	 * 关闭Trace
	 * @param appServerIds
	 * @return
	 */
	public List stopAppTrace(Object[] ids) throws RemoteException,Exception{
		List result=null;
		try{
			if (ids==null || ids.length<1)
				// "停止AppTrace请提供操作应用"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000136"));
			
			int threadCount=(ids.length>>1)+1;
			result=ServerStatControl.controlTraceStatus(threadCount,-1,ids,null,null,null,null,null,"0",TraceControlEnum.STOP_APPTRACE);
			
		}catch (Exception e) {
			log.error("Call APIControlTraceSVImpl's Method stopAppTrace has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 启动WebTrace
	 * @param appServerIds
	 * @param code
	 * @return
	 */
	public List startWebTrace(Object[] ids,String time,String userCode,String url,String custIp) throws RemoteException,Exception{
		List result=null;
		try{
			if (ids==null || ids.length<1)
				// 请提供操作应用
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000136"));
			if (StringUtils.isBlank(userCode))
				// 请提供员工号参数
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000139"));
			int threadCount=(ids.length>>1)+1;
			if (StringUtils.isBlank(url))
				url=null;
			if (StringUtils.isBlank(custIp))
				custIp=null;
			result=ServerStatControl.controlTraceStatus(threadCount,-1,ids,userCode,custIp,url,null,null,time,TraceControlEnum.START_WEBTRACE);
			
			
		}catch (Exception e) {
			log.error("Call APIControlTraceSVImpl's Method startWebTrace has Exception :"+e.getMessage());	
		}
		return result;
	}
	
	/**
	 * 关闭Trace
	 * @param appServerIds
	 * @return
	 */
	public List stopWebTrace(Object[] ids) throws RemoteException,Exception{
		List result=null;
		try{
			if (ids==null || ids.length<1)
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000136"));
			
			int threadCount=(ids.length>>1)+1;
			result=ServerStatControl.controlTraceStatus(threadCount,-1,ids,null,null,null,null,null,"0",TraceControlEnum.STOP_WEBTRACE);
			
			
		}catch (Exception e) {
			log.error("Call APIControlTraceSVImpl's Method stopWebTrace has Exception :"+e.getMessage());				
		}
		return result;
	}
}
