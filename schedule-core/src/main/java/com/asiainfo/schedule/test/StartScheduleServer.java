package com.asiainfo.schedule.test;

import junit.framework.TestCase;

import com.asiainfo.schedule.core.ScheduleMain;
import com.asiainfo.schedule.core.utils.Constants.ServerType;

public class StartScheduleServer extends TestCase {
	
	public static void main(String[] args) throws Exception{
		ScheduleMain.main(new String[] { ServerType.manager.name() });
	}

}
