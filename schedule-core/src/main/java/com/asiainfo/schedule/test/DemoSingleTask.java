package com.asiainfo.schedule.test;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.schedule.core.client.TaskDealParam;
import com.asiainfo.schedule.core.client.interfaces.ISingleTaskDeal;

public class DemoSingleTask implements ISingleTaskDeal {
	private static final transient Logger logger = LoggerFactory.getLogger(DemoSingleTask.class);

	TaskDealParam param;

	@Override
	public void init(TaskDealParam param) throws Exception {
		this.param = param;
	}

	@Override
	public void execute() throws Exception {
		logger.info("Hello World![][]" + param.getTaskCode() + "[] " + new Date());

		Thread.sleep(10000);
		logger.info("sleep 10s end[][]" + param.getTaskCode()+"[]end");

	}
	
	
}
