package com.asiainfo.monitor.tools;

import org.apache.commons.lang.StringUtils;

public class TransporterClientFactory {

	
	
	public static Object getProxyObject(String type,String locator,String mBeanName,Class interfaceClass) throws Exception{
		
		if (StringUtils.isNotBlank(type) && type.toUpperCase().equals("REMOTE")){
			return RemoteClientProxy.getObject(locator,interfaceClass);
		}else {
			return RMIClientProxy.getObject(locator,mBeanName,interfaceClass);
		}
	}
	
}
