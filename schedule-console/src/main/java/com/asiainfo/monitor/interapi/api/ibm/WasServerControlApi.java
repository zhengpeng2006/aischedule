package com.asiainfo.monitor.interapi.api.ibm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.util.ibm.ByteArray;


public class WasServerControlApi {

	private final static transient Log log=LogFactory.getLog(WasServerControlApi.class);
	
	/**
	 * 计算ORB的ID
	 * @param appServerName
	 * @return
	 * @throws Exception
	 */
	public static String comptuerOrbId(String appServerName) throws Exception {
		String result="";
		try{
			ByteArray bytearray = new ByteArray(appServerName.getBytes());
			int j = bytearray.hashCode();
			result=Integer.toHexString(j & 2147483647);
		}catch(Exception e){
			log.error("Call WasServerControlApi's Method comptuerOrbId has Exception :"+e.getMessage());
			throw new Exception("Call WasServerControlApi's Method comptuerOrbId has Exception :"+e.getMessage());
		}
		return result;
	}
}
