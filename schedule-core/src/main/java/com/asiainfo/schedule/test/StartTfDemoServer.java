package com.asiainfo.schedule.test;

import com.asiainfo.schedule.core.utils.Constants.ServerType;

public class StartTfDemoServer {

	public static void main(String[] args) throws Exception{

		System.setProperty("appframe.server.name", "local_test_server");
		com.asiainfo.schedule.core.ScheduleMain.main(new String[] { ServerType.processor.name() });

	}

}
