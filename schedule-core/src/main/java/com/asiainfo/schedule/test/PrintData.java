package com.asiainfo.schedule.test;

import java.io.StringWriter;

import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.data.ZKDataManagerFactory;
import com.asiainfo.schedule.core.utils.DateUtils;

public class PrintData {

	public static void main(String[] args) throws Exception {
		ZKDataManagerFactory zkdm = (ZKDataManagerFactory) DataManagerFactory.getDataManager();
	
		 StringWriter sw = new StringWriter();
		 zkdm.printTree("/ai-schedule-center-QQ", sw, "\n");
		 System.out.println(sw.toString());
		// zkdm.deleteTree("/ai-schedule-center-ams/server");
		// ServerBean bean = zkdm.getServerRegistry("ams_task_q08_C_exe07",
		// ServerType.processor);
		// System.out.println(zkdm.getScheduleConfig().getDeadInterval());
		// System.out.println((System.currentTimeMillis() -
		// bean.getHeartbeatTime().getTime()));

	}
}
