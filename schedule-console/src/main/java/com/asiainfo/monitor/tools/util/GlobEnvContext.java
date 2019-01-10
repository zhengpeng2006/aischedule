package com.asiainfo.monitor.tools.util;

import org.apache.commons.lang.StringUtils;

public class GlobEnvContext {

	private static String reqdebug;
	
	static{
		reqdebug=System.getProperty("request.debug");
	}
	
	public static boolean isDebug(){
		if (StringUtils.isNotBlank(reqdebug) && (reqdebug.equalsIgnoreCase("Y") || reqdebug.equalsIgnoreCase("1")) )
			return true;
		return false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
