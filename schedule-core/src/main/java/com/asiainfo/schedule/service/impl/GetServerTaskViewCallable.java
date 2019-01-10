package com.asiainfo.schedule.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskJobBean;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.Constants.KeyCodes;
import com.asiainfo.schedule.core.utils.Constants.RunSts;

public class GetServerTaskViewCallable implements Callable<List<Map<String, String>>> {

	TaskBean task;

	GetServerTaskViewCallable(TaskBean _task) {
		this.task = _task;
	}

	@Override
	public List<Map<String, String>> call() throws Exception {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<String> items = DataManagerFactory.getDataManager().getAllTaskItem(task.getTaskCode());
		for (String it : items) {
			String serverCode = DataManagerFactory.getDataManager().getTaskItemServerId(task.getTaskCode(), it);
			if (!CommonUtils.isBlank(serverCode)) {
				Map<String, String> jo = new HashMap<String, String>();
				jo.put(KeyCodes.TaskCode.name(), task.getTaskCode());
				jo.put(KeyCodes.TaskType.name(), task.getTaskType());
				jo.put(KeyCodes.TaskVersion.name(), task.getVersion() + "");
				jo.put(KeyCodes.TaskName.name(), task.getTaskName());
				jo.put(KeyCodes.TaskGroup.name(), task.getTaskGroupCode());
				jo.put(KeyCodes.TaskItemId.name(), it);
				jo.put(KeyCodes.ServerId.name(), serverCode);
				String sts = DataManagerFactory.getDataManager().getTaskRunSts(task.getTaskCode());
				if (CommonUtils.isBlank(sts)) {
					sts = RunSts.stop.name();
				}
				jo.put(KeyCodes.TaskRunSts.name(), sts);

				String jobId = DataManagerFactory.getDataManager().getTaskJobId(task.getTaskCode());
				if (!CommonUtils.isBlank(jobId)) {
					TaskJobBean bean = DataManagerFactory.getDataManager().getTaskJobByJobId(task.getTaskCode(), jobId);
					jo.put(KeyCodes.TaskJobId.name(), jobId);
					jo.put(KeyCodes.CreateTime.name(), bean.getCreateTime());
					jo.put(KeyCodes.Creator.name(), bean.getCreator());
				}

				result.add(jo);
			}
		}
		return result;
	}

}
