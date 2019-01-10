package com.asiainfo.monitor.interapi.api.log;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.log4j.Log4jCategoryMBean;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AppLogSetApi {

	private final static transient Log log=LogFactory.getLog(AppLogSetApi.class);
	
	private static String S_MBEAN_NAME="appframe:name=Log4jCategory";
	/**
	 * 设置log级别
	 * @param appSrvCfg
	 * @param logPkg
	 * @param logLevel
	 * @throws Exception
	 * @throws RemoteException
	 */
	public static int setLogLevel(String locatorType,String locator,String logPkg,String logLevel) throws Exception, RemoteException {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return 0;
		try {
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,Log4jCategoryMBean.class);
			if (proxyObject!=null){
				try{
					if (logLevel.toUpperCase().equals("DEBUG"))
						((Log4jCategoryMBean)proxyObject).activateDebug(logPkg);
					else if (logLevel.toUpperCase().equals("ERROR"))
						((Log4jCategoryMBean)proxyObject).activateError(logPkg);
					else if (logLevel.toUpperCase().equals("FATAL"))
						((Log4jCategoryMBean)proxyObject).activateFatal(logPkg);
					else if (logLevel.toUpperCase().equals("INFO"))
							((Log4jCategoryMBean)proxyObject).activateInfo(logPkg);
					else if (logLevel.toUpperCase().equals("WARN"))
						((Log4jCategoryMBean)proxyObject).activateWarn(logPkg);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "Log4jCategoryMBean");
				}
				
			}
		} catch (Exception e) {
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return 1;
	}
	
	/**
	 * 读取日志级别信息
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public static Object[] getLogLevels(String locatorType,String locator) throws Exception, RemoteException{		
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		Object[] result=null;
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,Log4jCategoryMBean.class);
			if (proxyObject!=null){
				try{
					result=((Log4jCategoryMBean)proxyObject).getCurrentLogger();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "Log4jCategoryMBean");
				}
			}
		}catch(Exception e){
			// 读取当前日志级别信息发生异常:MOS0000274
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return result;
	}
}
