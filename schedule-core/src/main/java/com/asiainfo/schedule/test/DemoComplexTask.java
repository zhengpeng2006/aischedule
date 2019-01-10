package com.asiainfo.schedule.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.schedule.core.client.TaskDealParam;
import com.asiainfo.schedule.core.client.interfaces.IComplexTaskDeal;

public class DemoComplexTask implements IComplexTaskDeal<String> {
	private static transient Log log = LogFactory.getLog(DemoComplexTask.class);

	TaskDealParam param;
	int total = 10050;
	int dealCount = 0;

	public Comparator<String> getComparator() {
		return new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		};
	}

	@Override
	public void init(TaskDealParam param) throws Exception {
		this.param = param;
		
	}

	@Override
	public boolean isFinishNoData() {
		return true;
	}

	@Override
	public long getTaskDataTotal() throws Exception {
		return total;
	}

	@Override
	public List<String> selectTasks() throws Exception {
		int num = param.getEachFetchDataNum();
		if (dealCount == total) {
			return null;
		}
		if (dealCount + num > total) {
			num = total - dealCount;
		}
		List<String> result = new ArrayList<String>(num);
		for (int i = 0; i < num; i++) {
			result.add("" + (dealCount + i));
		}
		dealCount += num;
		return result;
	}

	@Override
	public void execute(List<String> tasks) throws Exception {
		log.info(tasks);

	}

}
