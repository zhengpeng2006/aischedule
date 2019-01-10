package com.asiainfo.monitor.busi.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowStatSV;

public class SVMethodStatAction extends BaseAction {

	private static transient Log log=LogFactory.getLog(SVMethodStatAction.class);
	
	
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
	public List getSVMehtodInfoByIds(Object[] appIds,String clazz,String method,Integer start,Integer end) throws Exception {
		if (appIds==null || appIds.length<1)
			return null;
		List result=null;
		try{
			IAPIShowStatSV showStatSV=(IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			result=showStatSV.getSVMehtodInfoByIds(appIds,clazz,method,start,end);
		}catch(Exception e){
			log.error("Call SVMethodAction's Method getSVMehtodInfoByIds has Exception :"+e.getMessage());
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
	public List getSVMehtodInfo(String appId,String clazz,String method,Integer start,Integer end) throws Exception {
		if (StringUtils.isBlank(appId)){
			return null;
		}
		List result = null;
		try{
			IAPIShowStatSV showStatSV=(IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			result = showStatSV.getSVMehtodInfo(appId,clazz,method,start,end);
		}catch(Exception e){
			log.error("Call SVMethodAction's Method getSVMehtodInfo has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据条件返回(AppFrame)
	 * @param appId
	 * @param clazz
	 * @param method
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getSVMehtodInfoForAppFrame(String appId,String clazz,String method,Integer start,Integer end) throws Exception {
		if (StringUtils.isBlank(appId)){
			return null;
		}
		DataContainerInterface[] retDBInfo = null;
		try{
			IAPIShowStatSV showStatSV=(IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			retDBInfo = showStatSV.getSVMehtodInfoForAppFrame(appId, clazz, method, start, end);
		}catch(Exception e){
			log.error("Call SVMethodAction's Method getSVMehtodInfo has Exception :"+e.getMessage());
		}
		return retDBInfo;
	}
	
	
	/**
	 * 读取在线服务统计的状态
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public boolean fetchSVMethodState(String appId) throws Exception{
		if (StringUtils.isBlank(appId))
			return false;
		boolean result=false;
		try{
			IAPIShowStatSV showStatSV=(IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			result=showStatSV.fetchSVMethodState(appId);
		}catch(Exception e){
			log.error("Call SVMethodAction's Method getSVMehtodInfo has Exception :"+e.getMessage());
			return false;
		}
		return result;
	}
	
	/**
	 * 检查多应用的SVMethod统计状态
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List checkSVMethodStatByAppIds(Object[] appIds) throws Exception{
		List result=null;
		try{
			IAPIShowStatSV showStatSV=(IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			result=showStatSV.checkSVMethodStatByAppIds(appIds);
		}catch(Exception e){
			log.error("Call SVMethodAction's Method checkSVMethodStatByAppIds has Exception :"+e.getMessage());
		}
		return result;
	}
	
}
