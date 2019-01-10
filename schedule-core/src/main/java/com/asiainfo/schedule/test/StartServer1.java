package com.asiainfo.schedule.test;

import com.asiainfo.schedule.core.utils.Constants.ServerType;

public class StartServer1 {
	public static void main(String[] args) throws Exception {
		System.setProperty("appframe.server.name", "manager01");
		com.asiainfo.schedule.core.ScheduleMain.main(new String[] { ServerType.manager.name() });
	}
}
