package com.asiainfo.schedule.core.utils;

import java.lang.reflect.Method;

import com.ai.appframe2.service.ServiceFactory;

public class OutsideService {

	private static String DEPLOY_SV_ID = "com.asiainfo.deploy.api.interfaces.IAppOperationSVProvider";
	private static String MONITOR_SV_ID = "com.asiainfo.monitor.service.interfaces.IHostBackSV";

	public static void startServer(String serverId) throws Exception {
		Object sv = ServiceFactory.getService(DEPLOY_SV_ID);
		Method m = sv.getClass().getMethod("startViaSSH", new Class<?>[] { String.class });
		m.invoke(sv, serverId);
	}
	
	public static void createServerBak(String serverId) throws Exception {
		Object sv = ServiceFactory.getService(MONITOR_SV_ID);
		Method m = sv.getClass().getMethod("hostServerBack", new Class<?>[] { String.class });
		m.invoke(sv, serverId);
	}
	
	public static void main(String[] args) throws Exception{
		createServerBak("fault_test_server");

	}
}
