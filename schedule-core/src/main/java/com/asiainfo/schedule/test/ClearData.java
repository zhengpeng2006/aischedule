package com.asiainfo.schedule.test;

import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.data.ZKDataManagerFactory;
import com.asiainfo.schedule.core.utils.DateUtils;

public class ClearData {

	public static void main(String[] args) throws Exception {
		ZKDataManagerFactory zkdm = (ZKDataManagerFactory) DataManagerFactory.getDataManager();
		zkdm.deleteTree("/ai-schedule-center-QQ");
		
		System.out.println(System.currentTimeMillis() - DateUtils.parseYYYYMMddHHmmss("2015-04-09 11:26:30").getTime());
	}
}
