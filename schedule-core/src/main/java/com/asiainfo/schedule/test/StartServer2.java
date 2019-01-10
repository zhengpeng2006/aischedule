package com.asiainfo.schedule.test;

import com.asiainfo.schedule.core.ScheduleMain;
import com.asiainfo.schedule.core.utils.Constants.ServerType;

public class StartServer2 {
	public static void main(String[] args) throws Exception {
		System.setProperty("appframe.server.name", "HostProcessor");
		ScheduleMain.main(new String[] { ServerType.processor.name() });
	}

}
