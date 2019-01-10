package com.asiainfo.schedule.core;

/**
 * 调度中心版本号
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月29日
 * @modify
 */
public class Version {

	public final static String version = "1.0";

	public static String getVersion() {
		return version;
	}

	public static boolean isCompatible(String dataVersion) {
		if (version.compareTo(dataVersion) >= 0) {
			return true;
		} else {
			return false;
		}
	}

}
