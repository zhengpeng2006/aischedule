package com.asiainfo.monitor.interapi.api.trace;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.trace.WebTraceMonitorMBean;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;


public class WebTraceMonitorApi {

	private static transient Log log=LogFactory.getLog(WebTraceMonitorApi.class);
	
	private static String S_MBEAN_NAME="appframe:name=WebTraceMonitor";
	
	/**
	 * 获得是否Trace的状态
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 */
	public static boolean isEnable(String locatorType,String locator) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return false;
		boolean result=false;		
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,WebTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((WebTraceMonitorMBean)proxyObject).isEnable();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "WebTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 启用WebTrace
	 * @param appSrvCfg
	 * @param code
	 * @param className
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static boolean enable(String locatorType,String locator,String code, String url, String clientIp, int duration) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return false;
		boolean result=false;		
		try{;
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,WebTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					((WebTraceMonitorMBean)proxyObject).enable(code,url,clientIp,duration);
					result=true;
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "WebTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 禁用AppTrace
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 */
	public static boolean disable(String locatorType,String locator) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return false;
		boolean result=false;		
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,WebTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					((WebTraceMonitorMBean)proxyObject).disable();
					result=true;
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "WebTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 获取所有信息
	 * @param locatorType
	 * @param locator
	 * @return
	 * @throws Exception
	 */
	public static String getAllSetInfo(String locatorType,String locator) throws Exception{
		StringBuilder result=new StringBuilder("");
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return result.toString();
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,WebTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result.append( ((WebTraceMonitorMBean)proxyObject).getCode());
					result.append(TypeConst._SPLIT_CHAR);
					result.append(((WebTraceMonitorMBean)proxyObject).getUrl());
					result.append(TypeConst._SPLIT_CHAR);
					result.append(((WebTraceMonitorMBean)proxyObject).getClientIp());
					result.append(TypeConst._SPLIT_CHAR);
					result.append(((WebTraceMonitorMBean)proxyObject).getDuration());
					result.append(TypeConst._SPLIT_CHAR);
					result.append(((WebTraceMonitorMBean)proxyObject).isEnable());
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "WebTraceMonitorMBean");
				}
				
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result.toString();
	}
	
	
	/**
	 * 获取员工号
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 */
	public static String getCode(String locatorType,String locator) throws Exception{
		String result="";
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return result;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,WebTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((WebTraceMonitorMBean)proxyObject).getCode();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "WebTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 获取URL
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 */
	public static String getUrl(String locatorType,String locator) throws Exception{
		String result="";
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return result;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,WebTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((WebTraceMonitorMBean)proxyObject).getUrl();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "WebTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 获取客户端IP
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 */
	public static String getClientIp(String locatorType,String locator) throws Exception{
		String result="";
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return result;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,WebTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((WebTraceMonitorMBean)proxyObject).getClientIp();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "WebTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 获取延时时间
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 */
	public static String getDuration(String locatorType,String locator) throws Exception{
		String result="";
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return result;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,WebTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((WebTraceMonitorMBean)proxyObject).getDuration()+"";
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "WebTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
}
