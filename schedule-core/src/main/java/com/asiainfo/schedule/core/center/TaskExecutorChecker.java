package com.asiainfo.schedule.core.center;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.ScheduleThreadFactory;

public class TaskExecutorChecker {
	private static final transient Logger logger = LoggerFactory.getLogger(TaskExecutorChecker.class);
	public static final TaskExecutorChecker INSTANCE = new TaskExecutorChecker();
	private ExecutorService executors;
	private String checker;

	private TaskExecutorChecker() {
		int poolSize = 1;
		try {
			poolSize = DataManagerFactory.getDataManager().getScheduleConfig().getScheduleCheckThreadPoolSize();
		} catch (Exception ex) {
			poolSize = 1;
		}
		executors = Executors.newFixedThreadPool(poolSize, new ScheduleThreadFactory("TaskExecutorChecker"));
		this.checker = CommonUtils.getServerName();
	}

	public void check() throws Exception {

		String flag = DataManagerFactory.getDataManager().getFaultProcessFlag();

		if (logger.isDebugEnabled()) {
			logger.debug("故障处理开关:" + flag);
		}

		List<String> servers = DataManagerFactory.getDataManager().getAllServerChannles();

		if (servers == null || servers.size() == 0) {
			if (logger.isInfoEnabled()) {
				logger.info("不存在可执行的任务，结束服务进程状态检测！");
			}
			return;
		}

		for (String s : servers) {
			executors.submit(new ServerCheck(s, checker, flag));
		}

	}
}
