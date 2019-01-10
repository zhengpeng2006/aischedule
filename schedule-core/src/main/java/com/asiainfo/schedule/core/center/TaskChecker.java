package com.asiainfo.schedule.core.center;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.ScheduleThreadFactory;

public class TaskChecker {

	private ExecutorService executors;
	private String checker;

	public TaskChecker() {
		this(1);
	}

	public TaskChecker(int poolSize) {
		executors = Executors.newFixedThreadPool(poolSize, new ScheduleThreadFactory("TaskChecker"));
		this.checker = CommonUtils.getServerName();
	}

	public void check(List<TaskBean> checkTasks) throws Exception {
		if (checkTasks == null || checkTasks.size() == 0) {
			return;
		}

		for (TaskBean task : checkTasks) {
			// 检查任务作业流水是否处理完成，完成则转历史
			executors.submit(new JobCheck(task, checker));
		}
	}

}
