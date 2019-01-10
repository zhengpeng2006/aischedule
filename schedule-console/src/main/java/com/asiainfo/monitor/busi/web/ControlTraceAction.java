package com.asiainfo.monitor.busi.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlTraceSV;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class ControlTraceAction  extends BaseAction {
	
	private static transient Log log = LogFactory.getLog(ControlTraceAction.class);

	/**
	 * 启动选择web应用的webtrace，并且根据路由信息，找到路由到的app服务器，并且打开apptrace
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
	 * @throws Exception
	 */
	public String startTraceByDrive(Object[] ids,String driveflag,String time,String userCode,String url,String custIp,String code,String className,String methodName) throws Exception{
		StringBuilder sb = new StringBuilder("");
		try{
			if (ids == null || ids.length < 1){
				return sb.toString();
			}
			IAPIControlTraceSV controlTraceSV = (IAPIControlTraceSV)ServiceFactory.getService(IAPIControlTraceSV.class);
			List result = controlTraceSV.startTraceByDrive(ids, driveflag, time, userCode, url, custIp, code, className, methodName);
			if (result != null && result.size() > 0){
				for (int i=0;i<result.size();i++){
					SimpleResult sr = (SimpleResult)result.get(i);					
					if (sr.isSucc()){
						// "应用[{0}]成功启动WebTrace"
						sb.append(sr.getMsg());
						sb.append("\n");
					}else{
						sb.append(sr.getMsg());
						sb.append("\n");
					}
				}
			}
		}catch(Exception e){
			log.error(AIMonLocaleFactory.getResource("MOS0000032")+e.getMessage());
		}
		return sb.toString();
	}
	
	/**
	 * 启动Trace
	 * @param appServerIds
	 * @param code
	 * @param className
	 * @param methodName
	 * @return
	 */
	public String startAppTrace(Object[] ids,String code,String className,String methodName) throws Exception{
		StringBuilder sb=new StringBuilder("");		
		try{
			if (ids==null || ids.length<1)
				return sb.toString();			
			IAPIControlTraceSV controlTraceSV=(IAPIControlTraceSV)ServiceFactory.getService(IAPIControlTraceSV.class);
			List result=controlTraceSV.startAppTrace(ids,code,className,methodName);
			if (result!=null && result.size()>0){
				for (int i=0;i<result.size();i++){
					SimpleResult sr=(SimpleResult)result.get(i);					
					if (sr.isSucc()){
						// "应用[{0}]成功启动AppTrace"
						sb.append(AIMonLocaleFactory.getResource("MOS0000027", sr.getName()));
						sb.append("\n");
					}else{
						sb.append(sr.getMsg());
						sb.append("\n");
					}
				}
			}
		}catch (Exception e) {
			// "启动AppTrace失败！"
			log.error(AIMonLocaleFactory.getResource("MOS0000028")+e.getMessage());
		}
		return sb.toString();
	}
	
	/**
	 * 联动关闭Trace
	 * @param ids
	 * @param driveflag:联动标志，为Y时，代表如果是web应用，必须找出路由的App应用，并停止Apptrace
	 * @return
	 */
	public String stopTraceByDrive(Object[] ids,String driveflag) throws Exception{
		StringBuilder sb=new StringBuilder("");
		try{
			if (ids==null || ids.length<1)
				return sb.toString();			
			
			IAPIControlTraceSV controlTraceSV=(IAPIControlTraceSV)ServiceFactory.getService(IAPIControlTraceSV.class);
			List result=controlTraceSV.stopTraceByDrive(ids, driveflag);
			if (result!=null && result.size()>0){
				for (int i=0;i<result.size();i++){
					SimpleResult sr=(SimpleResult)result.get(i);					
					if (sr.isSucc()){
						// "应用[{0}]成功停止AppTrace"
						sb.append(sr.getMsg());
						sb.append("\n");
					}else{
						sb.append(sr.getMsg());
						sb.append("\n");
					}
				}
			}
		}catch (Exception e) {
			// "停止AppTrace失败！"
			log.error(AIMonLocaleFactory.getResource("MOS0000030")+e.getMessage());
		}
		return sb.toString();
	}
	
	/**
	 * 关闭Trace
	 * @param appServerIds
	 * @return
	 */
	public String stopAppTrace(Object[] ids) throws Exception{
		StringBuilder sb=new StringBuilder("");
		try{
			if (ids==null || ids.length<1)
				return sb.toString();			
			
			IAPIControlTraceSV controlTraceSV=(IAPIControlTraceSV)ServiceFactory.getService(IAPIControlTraceSV.class);
			List result=controlTraceSV.stopAppTrace(ids);
			if (result!=null && result.size()>0){
				for (int i=0;i<result.size();i++){
					SimpleResult sr=(SimpleResult)result.get(i);					
					if (sr.isSucc()){
						// "应用[{0}]成功停止AppTrace"
						sb.append(AIMonLocaleFactory.getResource("MOS0000029", sr.getName()));
						sb.append("\n");
					}else{
						sb.append(sr.getMsg());
						sb.append("\n");
					}
				}
			}
		}catch (Exception e) {
			// "停止AppTrace失败！"
			log.error(AIMonLocaleFactory.getResource("MOS0000030")+e.getMessage());
		}
		return sb.toString();
	}

	/**
	 * 启动WebTrace
	 * @param appServerIds
	 * @param code
	 * @return
	 */
	public String startWebTrace(Object[] ids,String time,String userCode,String url,String custIp) throws Exception{
		StringBuilder sb=new StringBuilder("");
		try{
			if (ids==null || ids.length<1)
				return sb.toString();			
			
			
			IAPIControlTraceSV controlTraceSV=(IAPIControlTraceSV)ServiceFactory.getService(IAPIControlTraceSV.class);
			List result=controlTraceSV.startWebTrace(ids,time,userCode,url,custIp);
			if (result!=null && result.size()>0){
				for (int i=0;i<result.size();i++){
					SimpleResult sr=(SimpleResult)result.get(i);					
					if (sr.isSucc()){
						// "应用[{0}]成功启动WebTrace"
						sb.append(AIMonLocaleFactory.getResource("MOS0000031", sr.getName()));
						sb.append("\n");
					}else{
						sb.append(sr.getMsg());
						sb.append("\n");
					}
				}
			}
		}catch (Exception e) {
			// "启动webTrace失败！"
			log.error(AIMonLocaleFactory.getResource("MOS0000032")+e.getMessage());	
		}
		return sb.toString();
	}
	
	/**
	 * 关闭Trace
	 * @param appServerIds
	 * @return
	 */
	public String stopWebTrace(Object[] ids) throws Exception{
		StringBuilder sb=new StringBuilder("");
		try{
			if (ids==null || ids.length<1)
				return sb.toString();			
			
			
			IAPIControlTraceSV controlTraceSV=(IAPIControlTraceSV)ServiceFactory.getService(IAPIControlTraceSV.class);
			List result=controlTraceSV.stopWebTrace(ids);
			if (result!=null && result.size()>0){
				for (int i=0;i<result.size();i++){
					SimpleResult sr=(SimpleResult)result.get(i);
					
					if (sr.isSucc()){
						// "应用[{0}]成功停止WebTrace"
						sb.append(AIMonLocaleFactory.getResource("MOS0000033", sr.getName()));
						sb.append("\n");
					}else{
						sb.append(sr.getMsg());
						sb.append("\n");
					}
				}
			}
		}catch (Exception e) {
			// 停止webTrace失败！
			log.error(AIMonLocaleFactory.getResource("MOS0000034")+e.getMessage());
		}
		return sb.toString();
	}
	
}
