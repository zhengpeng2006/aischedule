package com.asiainfo.monitor.interapi.api.statistics;



import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.sv.SVMethodMonitorMBean;
import com.ai.appframe2.complex.mbean.standard.sv.SVMethodSummary;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;


public class SVMethodMonitorApi {


	private static transient Log log=LogFactory.getLog(SVMethodMonitorApi.class);
	
	private static String S_MBEAN_NAME="appframe:name=SVMethodMonitor";
		
	/**
	 * 读取实时服务方法调用统计
	 * @param rmiUrl
	 * @param condition
	 * @param startRowIndex
	 * @param endRowIndex
	 * @return
	 * @throws Exception
	 */
	public static SVMethodSummary[] getSVMehtodInfo(String locatorType,String locator,String condition) throws Exception {

		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator)){
			return null;
		}
		SVMethodSummary[] result = null;
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,SVMethodMonitorMBean.class);
			if (proxyObject != null){
				try{
					result = ((SVMethodMonitorMBean)proxyObject).fetchSVMethodSummary(condition);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "SVMethodMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 读取在线服务调用监控状态
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 */
	public static boolean fetchState(String locatorType,String locator) throws Exception {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator)){
			return false;
		}
		boolean result = false;		
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,SVMethodMonitorMBean.class);
			if (proxyObject != null){
				try{
					result=((SVMethodMonitorMBean)proxyObject).fetchStatus();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "SVMethodMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 关闭在线服务实时调用统计
	 * @param appSrvCfg
	 * @throws Exception
	 */
	public static void close(String locatorType,String locator) throws Exception {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,SVMethodMonitorMBean.class);
			if (proxyObject!=null){
				try{
					((SVMethodMonitorMBean)proxyObject).disable();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "SVMethodMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
	}
	
	/**
	 * 打开在线服务实时调用统计
	 * @param appSrvCfg
	 * @param seconds：结束时间
	 * @throws Exception
	 */
	public static void open(String locatorType,String locator,long seconds) throws Exception {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator)){
			return;
		}
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,SVMethodMonitorMBean.class);
			if (proxyObject != null){
				try{
					((SVMethodMonitorMBean)proxyObject).enable(seconds);
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "SVMethodMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}		
	}
	
	public static void main(String[] args){
		System.out.println(Boolean.parseBoolean("false"));
		System.out.println("21");
	}
}
