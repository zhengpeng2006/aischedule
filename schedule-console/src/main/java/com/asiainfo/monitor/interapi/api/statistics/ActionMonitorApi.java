package com.asiainfo.monitor.interapi.api.statistics;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.action.ActionMonitorMBean;
import com.ai.appframe2.complex.mbean.standard.action.ActionSummary;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;


public class ActionMonitorApi {

	private static transient Log log=LogFactory.getLog(ActionMonitorApi.class);
	
	private static String S_MBEAN_NAME="appframe:name=ActionMonitor";
	/**
	 * 获取Action统计信息对象
	 * @param appSrvCfg
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static ActionSummary[] getActionInfo(String locatorType,String locator,String condition) throws Exception {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator)){
			return null;
		}
		ActionSummary[] result = null;
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,ActionMonitorMBean.class);
			if (proxyObject != null){
				try{
					result = ((ActionMonitorMBean)proxyObject).fetchActionSummary(condition);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "ActionMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 关闭在线Action实时调用统计
	 * @param appSrvCfg
	 * @throws Exception
	 */
	public static void close(String locatorType,String locator) throws Exception {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,ActionMonitorMBean.class);
			if (proxyObject!=null){
				try{
					((ActionMonitorMBean)proxyObject).disable();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "ActionMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
	}
	
	/**
	 * 打开在线Action实时调用统计
	 * @param appSrvCfg
	 * @param seconds：结束时间
	 * @throws Exception
	 */
	public static void open(String locatorType,String locator,long seconds) throws Exception {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,ActionMonitorMBean.class);
			if (proxyObject!=null){
				try{
					((ActionMonitorMBean)proxyObject).enable(seconds);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "ActionMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}		
	}
	
	public static boolean fetchStatus(String locatorType,String locator) throws Exception {
		boolean result = false;
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator)){
			return result;
		}
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,ActionMonitorMBean.class);
			if (proxyObject != null){
				try{
					result = ((ActionMonitorMBean)proxyObject).fetchStatus();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "ActionMonitorMBean");
				}
				
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return result;
	}
}
