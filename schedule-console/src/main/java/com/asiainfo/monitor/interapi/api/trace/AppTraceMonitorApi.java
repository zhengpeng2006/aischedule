package com.asiainfo.monitor.interapi.api.trace;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.trace.AppTraceMonitorMBean;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;


public class AppTraceMonitorApi {

	private static transient Log log=LogFactory.getLog(AppTraceMonitorApi.class);
	
	private static final String S_MBEAN_NAME="appframe:name=AppTraceMonitor";
	
	
	
	
	/**
	 * 获得是否Trace的状态
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 */
	public static boolean isEnable(String locatorType,String locator) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator)){
			return false;
		}
		boolean result = false;		
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,AppTraceMonitorMBean.class);
			if (proxyObject != null){
				try{
					result = ((AppTraceMonitorMBean)proxyObject).isEnable();
				}catch(NoClassDefFoundError ex){
					log.error(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			log.error("Call AppTraceMonitorApi'Method isEnable has Exception:"+e.getMessage());
			throw new Exception("Call AppTraceMonitorApi'Method isEnable has Exception:"+e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 启用AppTrace
	 * @param appSrvCfg
	 * @param code
	 * @param className
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static boolean enable(String locatorType,String locator,String code, String className, String methodName) throws Exception{
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator)){
			return false;
		}
		boolean result = false;		
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,AppTraceMonitorMBean.class);
			if (proxyObject != null){
				try{
					((AppTraceMonitorMBean)proxyObject).enable(code,className,methodName);
				}catch(NoClassDefFoundError ex){
					log.error(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppTraceMonitorMBean");
//					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			log.error("Call AppTraceMonitorApi'Method enable has Exception:"+e.getMessage());
			throw new Exception("Call AppTraceMonitorApi'Method enable has Exception:"+e.getMessage());
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
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,AppTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					((AppTraceMonitorMBean)proxyObject).disable();
				}catch(NoClassDefFoundError ex){
					log.error(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			log.error("Call AppTraceMonitorApi'Method enable has Exception:"+e.getMessage());
			throw new Exception("Call AppTraceMonitorApi'Method enable has Exception:"+e.getMessage());
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
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,AppTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result.append( ((AppTraceMonitorMBean)proxyObject).getCode());
					result.append(TypeConst._SPLIT_CHAR);
					result.append(((AppTraceMonitorMBean)proxyObject).getClassName());
					result.append(TypeConst._SPLIT_CHAR);
					result.append(((AppTraceMonitorMBean)proxyObject).getMethodName());
					result.append(TypeConst._SPLIT_CHAR);
					result.append(((AppTraceMonitorMBean)proxyObject).isEnable());
				}catch(NoClassDefFoundError ex){
					log.error(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppTraceMonitorMBean");
				}				
			}
		}catch(Exception e){
			log.error("Call AppTraceMonitorApi'Method getAllSetInfo has Exception:"+e.getMessage());
			throw new Exception("Call AppTraceMonitorApi'Method getAllSetInfo has Exception:"+e.getMessage());
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
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,AppTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((AppTraceMonitorMBean)proxyObject).getCode();
				}catch(NoClassDefFoundError ex){
					log.error(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			log.error("Call AppTraceMonitorApi'Method getCode has Exception:"+e.getMessage());
			throw new Exception("Call AppTraceMonitorApi'Method getCode has Exception:"+e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 获取类名
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 */
	public static String getClassName(String locatorType,String locator) throws Exception{
		String result="";
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return result;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,AppTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((AppTraceMonitorMBean)proxyObject).getClassName();
				}catch(NoClassDefFoundError ex){
					log.error(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			log.error("Call AppTraceMonitorApi'Method getClassName has Exception:"+e.getMessage());
			throw new Exception("Call AppTraceMonitorApi'Method getClassName has Exception:"+e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 获取方法名
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 */
	public static String getMethodName(String locatorType,String locator) throws Exception{
		String result="";
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return result;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,AppTraceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((AppTraceMonitorMBean)proxyObject).getMethodName();
				}catch(NoClassDefFoundError ex){
					log.error(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppTraceMonitorMBean");
				}
			}
		}catch(Exception e){
			log.error("Call AppTraceMonitorApi'Method getMethodName has Exception:"+e.getMessage());
			throw new Exception("Call AppTraceMonitorApi'Method getMethodName has Exception:"+e.getMessage());
		}
		
		return result;
	}
}
