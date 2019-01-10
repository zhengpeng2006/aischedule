package com.asiainfo.aif.schedule;

import com.asiainfo.schedule.core.utils.Constants.ServerType;

public class StartManagerTest {
	public static void main(String[] args) throws Exception {
		System.setProperty("appframe.server.name", "managerTest");
		com.asiainfo.schedule.core.ScheduleMain.main(new String[] { ServerType.manager.name() });
	}

}
