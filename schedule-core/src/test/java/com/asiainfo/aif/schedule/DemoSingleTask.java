package com.asiainfo.aif.schedule;

import com.asiainfo.schedule.core.client.TaskDealParam;
import com.asiainfo.schedule.core.client.interfaces.ISingleTaskDeal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoSingleTask implements ISingleTaskDeal {
	private static final transient Logger logger = LoggerFactory.getLogger(com.asiainfo.schedule.test.DemoSingleTask.class);

	TaskDealParam param;

	@Override
	public void init(TaskDealParam param) throws Exception {
		this.param = param;
	}

	@Override
	public void execute() throws Exception {
		if (param.isSplitRegion()) {
			logger.info("regionCode:" + param.getRegionCode());
		}

		logger.info("Hello World!" + param);
	}

}
