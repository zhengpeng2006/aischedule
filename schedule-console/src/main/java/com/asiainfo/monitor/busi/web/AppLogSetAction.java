package com.asiainfo.monitor.busi.web;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlApplogSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowApplogSV;

public class AppLogSetAction extends BaseAction {

	private final static transient Log log=LogFactory.getLog(AppLogSetAction.class);
	
	/**
	 * 读取日志级别信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getLogLevels(String appId) throws Exception {
		if (StringUtils.isBlank(appId))
			return null;
		List result=null;
		try{
			IAPIShowApplogSV showApplogSV=(IAPIShowApplogSV)ServiceFactory.getService(IAPIShowApplogSV.class);
			result=showApplogSV.getLogLevels(appId);
		}catch(Exception e){
			log.error("Call AppLogSetAction's Method getLogLevels has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 设置日志级别
	 * @param appId
	 * @param logPkg
	 * @param logLevel
	 * @return
	 * @throws Exception
	 */
	public int setLogLevel(String appId,String logPkg,String logLevel) throws Exception{
		if (StringUtils.isBlank(appId))
			return 0;
		int result=0;
		try{
			IAPIControlApplogSV controlApplogSV=(IAPIControlApplogSV)ServiceFactory.getService(IAPIControlApplogSV.class);
			result=controlApplogSV.setLogLevel(appId,logPkg,logLevel);
		}catch(Exception e){
			log.error("Call AppLogSetAction's Method setLogLevel has Exception :"+e.getMessage());
		}
		return result;
	}
}
