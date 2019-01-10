package com.asiainfo.logger.utils;

public interface QueryConstants {

	// 查询日志中心的key值
	public interface QueryKey {
			String APP_CODE = "APP_CODE";
			String FLAG = "FLAG";
			String BEGIN_DATE = "BEGIN_DATE";
			String END_DATE = "END_DATE";
			String TASK_ID = "TASK_ID";
			String TASK_SPLIT_ID = "TASK_SPLIT_ID";
			String REGION_CODE="REGION_CODE";
		}

	public interface Query {
			// 查询30分钟内的未读消息
			int INTERNAL = -30;
		}
}
