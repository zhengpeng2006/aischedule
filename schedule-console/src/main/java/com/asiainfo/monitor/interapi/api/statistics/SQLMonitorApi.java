package com.asiainfo.monitor.interapi.api.statistics;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.sql.SQLMonitorMBean;
import com.ai.appframe2.complex.mbean.standard.sql.SQLSummary;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class SQLMonitorApi {


	private static transient Log log=LogFactory.getLog(SQLMonitorApi.class);
	
	private static String S_MBEAN_NAME="appframe:name=SQLMonitor";
	/**
	 * 获取SQL统计信息对象
	 * @param appSrvCfg
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static SQLSummary[] getSQLInfo(String locatorType,String locator,String condition) throws Exception {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator)){
			return null;
		}
		SQLSummary[] result = null;
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,SQLMonitorMBean.class);
			if (proxyObject != null){
				try{
					result = ((SQLMonitorMBean)proxyObject).fetchSQLSummary(condition);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "SQLMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 关闭在线SQL实时调用统计
	 * @param appSrvCfg
	 * @throws Exception
	 */
	public static void close(String locatorType,String locator) throws Exception {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,SQLMonitorMBean.class);
			if (proxyObject!=null){
				try{
					((SQLMonitorMBean)proxyObject).disable();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "SQLMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
	}
	
	/**
	 * 打开在线SQL实时调用统计
	 * @param appSrvCfg
	 * @param seconds：结束时间
	 * @throws Exception
	 */
	public static void open(String locatorType,String locator,long seconds) throws Exception {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,SQLMonitorMBean.class);
			if (proxyObject!=null){
				try{
					((SQLMonitorMBean)proxyObject).enable(seconds);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "SQLMonitorMBean");
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
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,SQLMonitorMBean.class);
			if (proxyObject != null){
				try{
					result = ((SQLMonitorMBean)proxyObject).fetchStatus();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "SQLMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		return result;
	}
}
