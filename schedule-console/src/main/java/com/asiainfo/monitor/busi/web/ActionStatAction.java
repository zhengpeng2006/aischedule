package com.asiainfo.monitor.busi.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCtRecordSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowStatSV;


public class ActionStatAction extends BaseAction {
	
	private static transient Log log=LogFactory.getLog(ActionStatAction.class);
	
	/**
	 * 读取服务器列表的在线服务调用情况
	 * @param appIds
	 * @param clazz
	 * @param method
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getActionInfoByIds(Object[] appIds,String clazz,String method,Integer start,Integer end) throws Exception {
		if (appIds == null || appIds.length<1){
			return null;
		}
		List result = null;
		try{
			IAPIShowStatSV showStatSV = (IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			result = showStatSV.getActionInfoByIds(appIds,clazz,method,start,end);
		}catch(Exception e){
			log.error("Call ActionStaticAction's Method getActionInfoByIds has Exception :"+e.getMessage());
		}
		return result;
	}
	/**
	 * 根据条件返回
	 * @param appId
	 * @param clazz
	 * @param method
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getActionInfo(String appId,String clazz,String method,Integer start,Integer end) throws Exception {
		if (StringUtils.isBlank(appId))
			return null;
		List result=null;
		try{
			IAPIShowStatSV showStatSV=(IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			result=showStatSV.getActionInfo(appId,clazz,method,start,end);
		}catch(Exception e){
			log.error("Call ActionStaticAction's Method getActionInfo has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 读取应用的在线Action统计的状态
	 * @param appId
	 * @return
	 */
	public boolean fecthActionMonitorState(String appId){
		boolean result = false;

		try{
			IAPIShowStatSV showStatSV=(IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			result=showStatSV.fecthActionMonitorState(appId);
		}catch(Exception e){
			log.error("Call ActionStaticAction's Method fecthActionMonitorState has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 检查多应用的Action统计状态
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List checkActionStatByAppIds(Object[] appIds) throws Exception{
		List result=null;
		try{
			IAPIShowStatSV showStatSV=(IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			result=showStatSV.checkActionStatByAppIds(appIds);
		}catch(Exception e){
			log.error("Call ActionStaticAction's Method checkActionStatByAppIds has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取监控状态信息
	 * @return
	 * @throws Exception
	 */
	public String getAllControlInfo() throws Exception{
		String result=null;
		try{
			IAIMonCtRecordSV ctRecordSV=(IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
			result=ctRecordSV.getAllControlInfo();
		}catch(Exception e){
			log.error("Call ActionStaticAction's Method getAllControlInfo has Exception :" + e.getMessage());
		}
		return result;
	}
}
