package com.asiainfo.monitor.busi.stat.control;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCtRecordSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowCacheSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCtRecordValue;
import com.asiainfo.monitor.interapi.api.cache.BusiCacheMonitorApi;
import com.asiainfo.monitor.interapi.api.cache.CacheMonitorApi;
import com.asiainfo.monitor.interapi.api.statistics.ActionMonitorApi;
import com.asiainfo.monitor.interapi.api.statistics.SQLMonitorApi;
import com.asiainfo.monitor.interapi.api.statistics.SVMethodMonitorApi;
import com.asiainfo.monitor.interapi.api.trace.AppTraceMonitorApi;
import com.asiainfo.monitor.interapi.api.trace.WebTraceMonitorApi;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TimeUtil;
import com.asiainfo.monitor.tools.util.TypeConst;

public class ServerStatControl {

	private static transient Log log=LogFactory.getLog(ServerStatControl.class);
	
	public ServerStatControl() {
		super();
		// TODO 自动生成构造函数存根
	}

	/**
	 * 状态控制,SV、SQL、Action
	 * @param threadCount
	 * @param timeout:默认5秒
	 * @param ids
	 * @param seconds
	 * @param controlEnum
	 * @return
	 * @throws Exception
	 */
	public static List controlStatStatus(int threadCount,int timeout,Object[] ids,long seconds,StatControlEnum controlEnum) throws Exception {
		List result = new ArrayList();
//		String actor = FlexSessionManager.getUser().getName();
		String actor = "";
		ExecutorService exec = Executors.newFixedThreadPool(threadCount);
		try {
			CompletionService<String> serv = new ExecutorCompletionService<String>(exec);
			for (int i = 0; i < ids.length; i++) {
				StatCallable callable = controlEnum.takeStatCallable();
				callable.setId(String.valueOf(ids[i]));
				callable.setSeconds(seconds);
				callable.setActor(actor);
				serv.submit(callable);
			}
			Future task=null;
			
			for (int index = 0; index < ids.length; index++) {
				if (timeout<0){
					task = serv.take();
				}else{
					task = serv.poll(timeout, TimeUnit.SECONDS);
				}
				
				if (task == null) {
//					SimpleResult item=new SimpleResult();
					// 状态控制任务为空
					log.error(AIMonLocaleFactory.getResource("MOS0000069") + "[app:"+ids[index]+",stat:"+controlEnum.toString()+"]");
				} else {
					SimpleResult item = (SimpleResult) task.get();
					result.add(item);
				}
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			// 关闭线程池
			if (exec != null) {
				exec.shutdownNow();
			}
		}
		return result;
	}
	
	
	/******************************************************************************************
	 * 服务、SQL、Action统计系列
	 * @author Guocx
	 *
	 */
	public abstract static class StatCallable implements Callable{
		
		protected String id;
		
		protected long seconds=0;
		
		protected String actor;
		
		protected String tempType;
		
		public StatCallable(){
		}
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public long getSeconds() {
			return seconds;
		}

		public void setSeconds(long seconds) {
			this.seconds = seconds;
		}

		public void setActor(String actor) {
			this.actor = actor;
		}
		
		public String getActor() {
			return actor;
		}		

		public String getTempType() {
			return tempType;
		}

		public void setTempType(String tempType) {
			this.tempType = tempType;
		}

		public SimpleResult call() throws Exception{
			SimpleResult sr = new SimpleResult();
			try{
				IAIMonServerSV serverSV = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
				ServerConfig appServer = serverSV.getServerByServerId(id);
				if (appServer == null){
					return null;
				}
				sr.setKey(appServer.getApp_Id()+"");
				sr.setName(appServer.getApp_Name());
				this.tempType = appServer.getTemp_Type().toUpperCase();
				if(appServer == null){
					// "没找到应用实例[{0}]"
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000070", id));
					sr.setValue(false);
				}else{
					sr.setSucc(true);
					if (appServer.getJmxSet() == null ){
						// "应用端[appid:"+id+"]未启动Jmx注册"
						sr.setMsg(AIMonLocaleFactory.getResource("MOS0000071",id));
						sr.setSucc(false);
						return sr;
					}
					if (appServer.getJmxSet().getValue().equalsIgnoreCase("ON") || appServer.getJmxSet().getValue().equalsIgnoreCase("TRUE")){
						operate(appServer.getLocator_Type(),appServer.getLocator(),sr);
//						sr.setSucc(true);
					}else{
						// "应用端[appid:"+id+"]未启动Jmx注册"
						sr.setMsg(AIMonLocaleFactory.getResource("MOS0000071",id));
						sr.setSucc(false);
					}
				}
			}catch(Exception e){
				sr.setSucc(false);
				sr.setMsg(AIMonLocaleFactory.getResource("MOS0000049")+e.getMessage());
			}
			return sr;
		}
		
		public abstract void operate(String locatorType,String locator,SimpleResult sr) throws Exception;
		
	}
	
	
	/**
	 * 停止服务统计
	 * @author Guocx
	 *
	 */
	private static class StopSVStatCallable extends StatCallable{
		
		public StopSVStatCallable(){
		}
		
		public void operate(String locatorType,String locator,SimpleResult sr) throws Exception{
			try{
				if (tempType.equals(TypeConst.WEB)){
					sr.setSucc(false);
					// 该应用[appid:{0}]不支持Method检查、统计
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000314", sr.getKey()));
					return;
				}
				if (!SVMethodMonitorApi.fetchState(locatorType,locator)){
					sr.setSucc(false);
					// "应用"+sr.getName()+"的SV统计已经停止"
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000072", sr.getName()));
					return;
				}
				SVMethodMonitorApi.close(locatorType,locator);
				sr.setSucc(true);
				
				IAIMonCtRecordSV sv=(IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
				//获取启动服务统计的控制记录,7:启动服务统计
				IBOAIMonCtRecordValue[] values=sv.getEffectCtRecordByObjId(7,3,sr.getKey());
				if (values!=null && values.length>0){
					for (int i=0;i<values.length;i++){
						values[i].setInvalidDate(new Timestamp(System.currentTimeMillis()));
					}
					sv.saveOrUpdate(values);
				}
				//8:停止服务统计,3:应用
				sv.saveOrUpdate(8,3,Integer.parseInt(sr.getKey()),sr.getName(),new Timestamp(System.currentTimeMillis()),1,actor);
			}catch(Exception e){
				log.error("Call ServerStatControl's inner Class StopSVStatCallable has Exception :"+e.getMessage());
				sr.setSucc(false);
				sr.setMsg(sr.getName()+e.getMessage());
			}
		}
	}
	
	/**
	 * 打开服务统计
	 * @author Guocx
	 *
	 */
	private static class StartSVStatCallable extends StatCallable{
		
		public StartSVStatCallable(){}
		
		public void operate(String locatorType,String locator,SimpleResult sr) throws Exception{
			try{
				if (tempType.equals(TypeConst.WEB)){
					sr.setSucc(false);
					// 该应用[appid:{0}]不支持Method检查、统计
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000314", sr.getKey()));
					return;
				}
				if (SVMethodMonitorApi.fetchState(locatorType,locator)){
					sr.setSucc(false);
					// "应用{0}的SV统计已经启动"
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000073", sr.getName()));
					return;
				}
				
				SVMethodMonitorApi.open(locatorType,locator, seconds);
				long invalid = System.currentTimeMillis() + (seconds * 1000);					
				IAIMonCtRecordSV sv = (IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
				//7:启动SV统计,3:应用
				sv.saveOrUpdate(7,3,Integer.parseInt(sr.getKey()),sr.getName(),new Timestamp(invalid),Integer.parseInt(seconds+""),actor);
			}catch(Exception e){
				log.error("Call ServerStatControl's inner Class StartSVStatCallable has Exception :"+e.getMessage());
				sr.setSucc(false);
				sr.setMsg(sr.getName()+e.getMessage());
			}
		}
		
	}
	
	/**
	 * 停止Action统计
	 * @author Guocx
	 *
	 */
	private static class StopActionStatCallable extends StatCallable{
		
		public StopActionStatCallable(){
		}
		
		public void operate(String locatorType,String locator,SimpleResult sr) throws Exception{
			try{
				if (tempType.equals(TypeConst.APP)){
					sr.setSucc(false);
					// 该应用[appid:{0}]不支持Action检查、统计
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000315", sr.getKey()));
					return;
				}
				if (!ActionMonitorApi.fetchStatus(locatorType,locator)){
					sr.setSucc(false);
					// "应用{0}的Action统计已经停止"
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000074", sr.getName()));
					return;
				}
				ActionMonitorApi.close(locatorType,locator);
				IAIMonCtRecordSV sv=(IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
				//获取启动Action统计的控制记录
				IBOAIMonCtRecordValue[] values=sv.getEffectCtRecordByObjId(5,3,sr.getKey());
				if (values!=null && values.length>0){
					for (int i=0;i<values.length;i++){
						values[i].setInvalidDate(new Timestamp(System.currentTimeMillis()));
					}
					sv.saveOrUpdate(values);
				}
				//6:停止Action统计,3:应用
				sv.saveOrUpdate(6,3,Integer.parseInt(sr.getKey()),sr.getName(),new Timestamp(System.currentTimeMillis()),1,actor);
			}catch(Exception e){
				log.error("Call ServerStatControl's inner Class StopActionStatCallable has Exception :"+e.getMessage());
				sr.setSucc(false);
				sr.setMsg(sr.getName()+e.getMessage());
			}
		}
	}
	
	/**
	 * 打开Action统计
	 * @author Guocx
	 *
	 */
	private static class StartActionStatCallable extends StatCallable{
		
		public StartActionStatCallable(){
		}
		
		public void operate(String locatorType,String locator,SimpleResult sr) throws Exception{
			try{
				if (tempType.equals(TypeConst.APP)){
					sr.setSucc(false);
					// 该应用[appid:{0}]不支持Action检查、统计
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000315", sr.getKey()));
					return;
				}
				if (ActionMonitorApi.fetchStatus(locatorType,locator)){
					sr.setSucc(false);
					// "应用{0}的Action统计已经启动"
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000075", sr.getName()));
					return;
				}
				ActionMonitorApi.open(locatorType,locator,seconds);
				
				long invalid=System.currentTimeMillis() + (seconds * 1000);					
				IAIMonCtRecordSV sv=(IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
				//5:启动Action统计,3:应用
				sv.saveOrUpdate(5,3,Integer.parseInt(sr.getKey()),sr.getName(),new Timestamp(invalid),Integer.parseInt(seconds+""),actor);
				
			}catch(Exception e){
				log.error("Call ServerStatControl's inner Class StartActionStatCallable has Exception :"+e.getMessage());
				sr.setSucc(false);
				sr.setMsg(sr.getName()+e.getMessage());
			}
		}
		
	}
	
	
	/**
	 * 停止Sql统计
	 * @author Guocx
	 *
	 */
	private static class StopSqlStatCallable extends StatCallable{
		
		public StopSqlStatCallable(){
		}
		
		public void operate(String locatorType,String locator,SimpleResult sr) throws Exception{
			try{
				if (tempType.equals(TypeConst.WEB)){
					sr.setSucc(false);
					// 该应用[appid:{0}]不支持SQL检查、统计
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000313", sr.getKey()));
					return;
				}
				if (!SQLMonitorApi.fetchStatus(locatorType,locator)){
					sr.setSucc(false);
					// "应用"+sr.getName()+"的Sql统计已经停止"
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000076", sr.getName()));
					return;
				}
				SQLMonitorApi.close(locatorType,locator);
				
				IAIMonCtRecordSV sv=(IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
				//获取启动SQL统计的控制记录
				IBOAIMonCtRecordValue[] values=sv.getEffectCtRecordByObjId(9,3,sr.getKey());
				if (values!=null && values.length>0){
					for (int i=0;i<values.length;i++){
						values[i].setInvalidDate(new Timestamp(System.currentTimeMillis()));
					}
					sv.saveOrUpdate(values);
				}
				//10:停止SQL统计,3:应用
				sv.saveOrUpdate(10,3,Integer.parseInt(sr.getKey()),sr.getName(),new Timestamp(System.currentTimeMillis()),1,actor);
				
			}catch(Exception e){
				log.error("Call ServerStatControl's inner Class StopSqlStatCallable has Exception :"+e.getMessage());
				sr.setSucc(false);
				sr.setMsg(sr.getName()+e.getMessage());
			}
		}
	}
	
	/**
	 * 打开Sql统计
	 * @author Guocx
	 *
	 */
	private static class StartSqlStatCallable extends StatCallable{
		
		public StartSqlStatCallable(){
		}
		
		public void operate(String locatorType,String locator,SimpleResult sr) throws Exception{
			try{
				if (tempType.equals(TypeConst.WEB)){
					sr.setSucc(false);
					// 该应用[appid:{0}]不支持SQL检查、统计
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000313", sr.getKey()));
					return;
				}
				if (SQLMonitorApi.fetchStatus(locatorType,locator)){
					sr.setSucc(false);
					// "应用{0}的Sql统计已经启动"
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000077", sr.getName()));
					return;
				}
				SQLMonitorApi.open(locatorType,locator,seconds);
				
				long invalid=System.currentTimeMillis() + (seconds * 1000);					
				IAIMonCtRecordSV sv=(IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
				//9:启动SQL统计,3:应用
				sv.saveOrUpdate(9,3,Integer.parseInt(sr.getKey()),sr.getName(),new Timestamp(invalid),Integer.parseInt(seconds+""),actor);
				
			}catch(Exception e){
				log.error("Call ServerStatControl's inner Class StartSqlStatCallable has Exception :"+e.getMessage());
				sr.setSucc(false);
				sr.setMsg(sr.getName()+e.getMessage());
			}
		}
		
	}
	
	public static enum StatControlEnum {
		START_SV{
			public StatCallable takeStatCallable(){
				return new StartSVStatCallable();
			}
		},
		STOP_SV{
			public StatCallable takeStatCallable(){
				return new StopSVStatCallable();
			}
		},
		START_ACTION{
			public StatCallable takeStatCallable(){
				return new StartActionStatCallable();
			}
		},
		STOP_ACTION{
			public StatCallable takeStatCallable(){
				return new StopActionStatCallable();
			}
		},
		START_SQL{
			public StatCallable takeStatCallable(){
				return new StartSqlStatCallable();
			}
		},
		STOP_SQL{
			public StatCallable takeStatCallable(){
				return new StopSqlStatCallable();
			}
		};
		
		
		private StatControlEnum(){
		}
		
		public abstract StatCallable takeStatCallable();
		
	}
	
	
	/*********************************************************************************************
	 * Trace控制系列
	 * @author Guocx
	 *
	 */
	
	public static List controlTraceStatus(int threadCount,int timeout,Object[] ids,String code,String ip,String url,String clazz,String method,String duration,TraceControlEnum controlEnum) throws Exception {
		List result = new ArrayList();
//		String actor = FlexSessionManager.getUser().getName();
		String actor = "";
		ExecutorService exec = Executors.newFixedThreadPool(threadCount);
		try {
			CompletionService<String> serv = new ExecutorCompletionService<String>(exec);
			for (int i = 0; i < ids.length; i++) {
				TraceStatCallable callable = controlEnum.takeStatCallable();
				callable.setId(String.valueOf(ids[i]));
				callable.setCode(code);
				callable.setIp(ip);
				callable.setUrl(url);
				callable.setSeconds(Long.parseLong(duration));
				callable.setClazz(clazz);
				callable.setMethod(method);
				callable.setActor(actor);
				serv.submit(callable);
			}
			Future task = null;
			for (int index = 0; index < ids.length; index++) {
				if (timeout<0){
					task = serv.take();
				}else{
					task = serv.poll(timeout, TimeUnit.SECONDS);
				}				
				if (task == null) {
					// Trace状态控制任务为空
					log.error(AIMonLocaleFactory.getResource("MOS0000078") + "[app:"+ids[index]+",stat:"+controlEnum.toString()+"]");
				} else {
					SimpleResult item = (SimpleResult) task.get();
					result.add(item);
				}
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			// 关闭线程池
			if (exec != null) {
				exec.shutdownNow();
			}
		}
		return result;
	}
	
	private abstract static class TraceStatCallable extends StatCallable{
		protected String code;
		protected String ip;
		protected String url;
		protected String clazz;
		protected String method;
		
		
		public TraceStatCallable(){
			super();
		}

		public String getClazz() {
			return clazz;
		}

		public void setClazz(String clazz) {
			this.clazz = clazz;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
		
	}
	
	/**
	 * 关闭AppTrace
	 * @author Guocx
	 *
	 */
	private static class StopAppTraceCallable extends TraceStatCallable{
		
		public StopAppTraceCallable(){
			
		}
		
		public void operate(String locatorType,String locator,SimpleResult sr) throws Exception{
			try{
				if (tempType.equals(TypeConst.WEB)){
					sr.setSucc(false);
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000348", sr.getName()));
					return;
				}
				if (!AppTraceMonitorApi.isEnable(locatorType,locator)){
					sr.setSucc(false);
					// "应用{0}的AppTrace已经停止"
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000079", sr.getName()));
					return;
				}
				AppTraceMonitorApi.disable(locatorType,locator);
				
				sr.setSucc(true);
				sr.setMsg(AIMonLocaleFactory.getResource("MOS0000029", sr.getName()));
				
				IAIMonCtRecordSV sv=(IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
				IBOAIMonCtRecordValue[] values=sv.getEffectCtRecordByObjId(3,3,sr.getKey());
				if (values!=null && values.length>0){
					for (int i=0;i<values.length;i++){
						values[i].setInvalidDate(new Timestamp(System.currentTimeMillis()));
					}
					sv.saveOrUpdate(values);
				}
				//4:停止AppTrace,3:应用
				sv.saveOrUpdate(4,3,Integer.parseInt(sr.getKey()),sr.getName(),new Timestamp(System.currentTimeMillis()),1,actor);
				
			}catch(Exception e){
//				log.error("Call ServerStatControl's inner Class StopAppTraceCallable has Exception :"+e.getMessage());
				sr.setSucc(false);
				sr.setMsg(sr.getName()+e.getMessage());
			}
		}
	}
	

	/**
	 * 启动AppTrace
	 * @author Guocx
	 *
	 */
	private static class StartAppTraceCallable extends TraceStatCallable{		
		
		public StartAppTraceCallable(){
		}
		
		public void operate(String locatorType,String locator,SimpleResult sr) throws Exception{
			try{
				if (StringUtils.isBlank(code) || StringUtils.isBlank(clazz) || StringUtils.isBlank(method)){
					sr.setSucc(false);
					// "应用{0}的AppTrace已经启动失败,类、方法不能为空"
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000080", sr.getName()));					
				}else{
					if (tempType.equals(TypeConst.WEB)){
						sr.setSucc(false);
						sr.setMsg(AIMonLocaleFactory.getResource("MOS0000349", sr.getName()));
						return;
					}
					if (AppTraceMonitorApi.isEnable(locatorType,locator)){
						sr.setSucc(false);
						// "应用{0}的AppTrace已经启动"
						sr.setMsg(AIMonLocaleFactory.getResource("MOS0000081", sr.getName()));
						return;
					}
					AppTraceMonitorApi.enable(locatorType,locator,code,clazz,method);
					
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000027", sr.getName()));
					sr.setSucc(true);
					
					IAIMonCtRecordSV sv=(IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
					Date invalid=TimeUtil.addOrMinusHours(System.currentTimeMillis(),24);//默认24小时
					//3:启动AppTrace
					sv.saveOrUpdate(3,3,Integer.parseInt(sr.getKey()),sr.getName(),new Timestamp(invalid.getTime()),86400,actor);
				}				
			}catch(Exception e){
//				log.error("Call ServerStatControl's inner Class StartAppTraceCallable has Exception :"+e.getMessage());
				sr.setSucc(false);
				sr.setMsg(sr.getName()+e.getMessage());
			}
		}
	}
	
	/**
	 * 关闭Trace
	 * @author Guocx
	 *
	 */
	private static class StopWebTraceCallable extends TraceStatCallable{
		
		public StopWebTraceCallable(){
			
		}
		
		public void operate(String locatorType,String locator,SimpleResult sr) throws Exception{
			try{
				if (tempType.equals(TypeConst.APP)){
					sr.setSucc(false);
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000347", sr.getName()));
					return;
				}
				if (!WebTraceMonitorApi.isEnable(locatorType,locator)){
					sr.setSucc(false);
					// "应用"+sr.getName()+"的WebTrace已经停止"
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000082", sr.getName()));
					return;
				}
				WebTraceMonitorApi.disable(locatorType,locator);
				
				sr.setSucc(true);
				sr.setMsg(AIMonLocaleFactory.getResource("MOS0000033", sr.getName()));
				IAIMonCtRecordSV sv=(IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
				//获取启动WebTrace的控制记录,1:启动WebTrace
				IBOAIMonCtRecordValue[] values=sv.getEffectCtRecordByObjId(1,3,sr.getKey());
				if (values!=null && values.length>0){
					for (int i=0;i<values.length;i++){
						values[i].setInvalidDate(new Timestamp(System.currentTimeMillis()));
					}
					sv.saveOrUpdate(values);
				}
				//2:停止WebTrace,3:应用
				sv.saveOrUpdate(2,3,Integer.parseInt(sr.getKey()),sr.getName(),new Timestamp(System.currentTimeMillis()),1,actor);
			}catch(Exception e){
//				log.error("Call ServerStatControl's inner Class StopWebTraceCallable has Exception :"+e.getMessage());
				sr.setSucc(false);
				sr.setMsg(sr.getName()+e.getMessage());
			}
		}
	}
	

	/**
	 * 启动Trace
	 * @author Guocx
	 *
	 */
	private static class StartWebTraceCallable extends TraceStatCallable{		
		
		public StartWebTraceCallable(){
		}
		
		public void operate(String locatorType,String locator,SimpleResult sr) throws Exception{
			try{
				if (StringUtils.isBlank(code) && StringUtils.isBlank(url) && StringUtils.isBlank(ip)){
					sr.setSucc(false);
					// "应用{0}的WebTrace启动失败,用户,类,方法不能同时为空"
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000083", sr.getName()));
				}else{
					if (tempType.equals(TypeConst.APP)){
						sr.setSucc(false);
						sr.setMsg(AIMonLocaleFactory.getResource("MOS0000346", sr.getName()));
						return;
					}
					if (WebTraceMonitorApi.isEnable(locatorType,locator)){
						sr.setSucc(false);
						// "应用{0}的WebTrace已经启动"
						sr.setMsg(AIMonLocaleFactory.getResource("MOS0000084", sr.getName()));
						return;
					}
					WebTraceMonitorApi.enable(locatorType,locator,code,url,ip,Integer.parseInt(seconds+""));
					
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000031", sr.getName()));
					sr.setSucc(true);
					
					long invalid=System.currentTimeMillis() + (seconds * 1000);					
					IAIMonCtRecordSV sv=(IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
					//1:启动WebTrace,3:应用
					sv.saveOrUpdate(1,3,Integer.parseInt(sr.getKey()),sr.getName(),new Timestamp(invalid),Integer.parseInt(seconds+""),actor);
				}
			}catch(Exception e){
				sr.setSucc(false);
				sr.setMsg(sr.getName()+e.getMessage());
				log.error("Call ServerStatControl's inner Class StartWebTraceCallable has Exception :"+e.getMessage());
			}
		}
	}
	
	public static enum TraceControlEnum {
		START_APPTRACE{
			public TraceStatCallable takeStatCallable(){
				return new StartAppTraceCallable();
			}
		},
		STOP_APPTRACE{
			public TraceStatCallable takeStatCallable(){
				return new StopAppTraceCallable();
			}
		},
		START_WEBTRACE{
			public TraceStatCallable takeStatCallable(){
				return new StartWebTraceCallable();
			}
		},
		STOP_WEBTRACE{
			public TraceStatCallable takeStatCallable(){
				return new StopWebTraceCallable();
			}
		};
		
		
		private TraceControlEnum(){
		}
		
		public abstract TraceStatCallable takeStatCallable();
		
	}
	
	/**************************************************************************************
	 * 缓存系列
	 * @author Guocx
	 *
	 */
	
	public static List controlCacheStatus(int threadCount,int timeout,Object[] ids,String type,Object key,CacheControlEnum controlEnum) throws Exception {
		List result = new ArrayList();
		ExecutorService exec = Executors.newFixedThreadPool(threadCount);
		try {
			CompletionService<String> serv = new ExecutorCompletionService<String>(exec);
			for (int i = 0; i < ids.length; i++) {
				CacheStatCallable callable=controlEnum.takeStatCallable();
				callable.setId(String.valueOf(ids[i]));
				callable.setType(type);
				callable.setKey(key);
				serv.submit(callable);
			}
			Future task=null;
			for (int index = 0; index < ids.length; index++) {
				if (timeout<0){
					task = serv.take();
				}else{
					task = serv.poll(timeout, TimeUnit.SECONDS);
				}
				if (task == null) {
					// 缓存状态控制任务为空
					log.error(AIMonLocaleFactory.getResource("MOS0000085") + "[app:"+ids[index]+",stat:"+controlEnum.toString()+"]");
				} else {
					SimpleResult item = (SimpleResult) task.get();
					result.add(item);
				}
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			// 关闭线程池
			if (exec != null) {
				exec.shutdownNow();
			}
		}
		return result;
	}
	
	
	private abstract static class CacheStatCallable extends StatCallable{
		protected Object type;
		protected Object key;
		
		public Object getKey() {
			return key;
		}
		public void setKey(Object key) {
			this.key = key;
		}
		public Object getType() {
			return type;
		}
		public void setType(Object type) {
			this.type = type;
		}
		
	}
	/**
	 * 清除平台缓存
	 * @author Guocx
	 *
	 */
	private static class ClearFrameCacheCallable extends CacheStatCallable{		
		
		public ClearFrameCacheCallable(){
		}
		
		public void operate(String locatorType,String locator,SimpleResult sr) throws Exception{
			try{
				if (type==null && key==null){
					sr.setSucc(false);
					// "清除应用{0}的平台缓存时type,key不能同时为空"
					sr.setMsg(AIMonLocaleFactory.getResource("MOS0000086"));
					return;
				}
				
				if (type!=null && StringUtils.isNotBlank(String.valueOf(type))){
					if (key!=null){
//						String[] keys=null;
//						if (String[].class.isAssignableFrom(key.getClass())){
//							keys=(String[])key;
//						}else{
//							keys=new String[]{String.valueOf(key)};
//						}
						Object[] tmp = (Object[])key;
						for (int j=0;j<tmp.length;j++){
//							String tmpkey[] = com.ai.appframe2.util.StringUtils.splitString(keys[j].toString(), "||");
//							String tmpType = ""; 
//							String key = keys[j].toString();
//							if(tmpkey!=null&&tmpkey.length==2){	
//								key = tmpkey[0];
//								tmpType = tmpkey[1];								
//							}
//							if (tmpType.equals(String.valueOf(type)))
								CacheMonitorApi.delCache(locatorType,locator,(String)type,tmp[j].toString());
						}
					}else{
						CacheMonitorApi.delCache(locatorType,locator,String.valueOf(type),null);
					}
				}else{
					String[] keys=null;
					if (String[].class.isAssignableFrom(key.getClass())){
						keys=(String[])key;
					}else{
						keys=new String[]{String.valueOf(key)};
					}
					for (int j=0;j<keys.length;j++){
						String tmpkey[] = com.ai.appframe2.util.StringUtils.splitString(keys[j].toString(), "||");
						String tmpType = ""; 
						String key = keys[j].toString();
						if(tmpkey!=null&&tmpkey.length==2){	
							key = tmpkey[0];
							tmpType = tmpkey[1];
							
						}
						CacheMonitorApi.delCache(locatorType,locator,tmpType,key);
					}
				}
				
			}catch(Exception e){
				log.error("Call ServerStatControl's inner Class ClearFrameCacheCallable has Exception :"+e.getMessage());
			}
		}
	}
	
	/**
	 * 清除业务缓存
	 * @author Guocx
	 *
	 */
	private static class ClearBusiCacheCallable extends CacheStatCallable{
		
		public ClearBusiCacheCallable(){
		}
		
		public void operate(String locatorType,String locator,SimpleResult sr) throws Exception{
			try{
				List busiCache = null;
				
				// 全部
				if(type==null || StringUtils.isBlank(String.valueOf(type)) || AIMonLocaleFactory.getResource("MOS0000007").equals(String.valueOf(type)) || "%".equals(String.valueOf(type))){
					IAPIShowCacheSV showCacheSV=(IAPIShowCacheSV)ServiceFactory.getService(IAPIShowCacheSV.class);
					busiCache = showCacheSV.getBusiCacheType(id);
				}else{
					busiCache = new ArrayList();
					busiCache.add(type);
				}
				for(int i=0;i<busiCache.size();i++){
					BusiCacheMonitorApi.refreshCache(locatorType,locator,busiCache.get(i).toString());
				}				
			}catch(Exception e){
				log.error("Call ServerStatControl's inner Class ClearBusiCacheCallable has Exception :"+e.getMessage());
			}
		}
	}
	
	public static enum CacheControlEnum {
		CLEAR_FRAMECACHE{
			public CacheStatCallable takeStatCallable(){
				return new ClearFrameCacheCallable();
			}
		},
		CLEAR_BUSICACHE{
			public CacheStatCallable takeStatCallable(){
				return new ClearBusiCacheCallable();
			}
		};
		
		
		private CacheControlEnum(){
		}
		
		public abstract CacheStatCallable takeStatCallable();
		
	}
	
}

