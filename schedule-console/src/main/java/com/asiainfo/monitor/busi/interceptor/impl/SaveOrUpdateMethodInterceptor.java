package com.asiainfo.monitor.busi.interceptor.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ai.appframe2.complex.service.proxy.interfaces.CustomAroundMethodInterceptor;

public class SaveOrUpdateMethodInterceptor implements CustomAroundMethodInterceptor {

	private Map cacheImplMap=new HashMap();
	
	public SaveOrUpdateMethodInterceptor() {
		super();
		// TODO 自动生成构造函数存根
	}

	/**
	 * 初始化
	 * key:类名,value:方法名|缓存类名
	 */
	public void init(HashMap arg0) throws Exception {
		if (arg0!=null){
			Iterator it=arg0.entrySet().iterator();
			
		}
	}

	public void beforeInterceptor(Object arg0, String arg1, Object[] arg2) throws Exception {

	}

	public void afterInterceptor(Object arg0, String arg1, Object[] arg2) throws Exception {

	}

	public void exceptionInterceptor(Object arg0, String arg1, Object[] arg2) throws Exception {

	}

}
