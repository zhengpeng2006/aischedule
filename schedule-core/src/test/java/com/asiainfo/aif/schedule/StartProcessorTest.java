package com.asiainfo.aif.schedule;

import com.asiainfo.schedule.core.ScheduleMain;
import com.asiainfo.schedule.core.utils.Constants.ServerType;

public class StartProcessorTest {
	public static void main(String[] args) throws Exception {
		System.setProperty("appframe.server.name", "demo_server2");
		ScheduleMain.main(new String[] { ServerType.processor.name() });
	}

}
