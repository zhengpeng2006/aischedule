package com.asiainfo.schedule.test;

import java.util.Date;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.schedule.core.po.TaskRunView;
import com.asiainfo.schedule.core.utils.CronExpression;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

public class SchedulerSVTest {

	public static void main(String[] args) throws Exception {
		ISchedulerSV sv = (ISchedulerSV) ServiceFactory.getService(ISchedulerSV.class);
		TaskRunView view[] = sv.getTaskRunView("DemoGroup", "", "");
		for (TaskRunView v : view) {
			System.out.println(v.getTaskBean().getTaskCode());
			System.out.println(v.getTaskBean().getTaskName());
			System.out.println(v.getTaskBean().getTaskGroupCode());
			System.out.println(v.getNextSchedStartTime());
		}
	}

	public void test() throws Exception {
	}
}
