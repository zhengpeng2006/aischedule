package com.asiainfo.schedule.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.schedule.core.client.TaskDealParam;
import com.asiainfo.schedule.core.client.interfaces.IComplexTaskDeal;

public class DemoComplexTask2 implements IComplexTaskDeal<List<Long>> {
	private static transient Log log = LogFactory.getLog(DemoComplexTask.class);

	TaskDealParam param;
	int total = 359;
	int dealCount = 0;
	LinkedList<List<Long>> allDatas = new LinkedList<List<Long>>();

	public Comparator<List<Long>> getComparator() {
		return new Comparator<List<Long>>() {
			public int compare(List<Long> o1, List<Long> o2) {
				return -1;
			}
		};
	}

	@Override
	public void init(TaskDealParam param) throws Exception {
		this.param = param;
		Random r = new Random();
		int j = 2;
		for (int i = 0; i < 200; i++) {
			j = 2;
			ArrayList list = new ArrayList();
			while (true) {
				if (j <= 0) {
					break;
				}
				list.add(r.nextLong());
				j--;
			}
			if (i == 0) {
				list.add(1L);
			}
			allDatas.add(list);
		}

		log.info("gen data over!!!" + allDatas.size());

			Thread.currentThread().sleep(5000);
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
	public List<List<Long>> selectTasks() throws Exception {
		List<List<Long>> selects = new ArrayList<List<Long>>();
		if (allDatas != null && allDatas.size() > 0) {
			int size = this.param.getEachFetchDataNum();
			while (size > 0 && allDatas.size() > 0) {
				selects.add(allDatas.removeFirst());
				size--;
			}
		}

		return selects;
	}

	@Override
	public void execute(List<List<Long>> tasks) throws Exception {
		for (List<Long> t : tasks) {
			if (t.size() == 3) {
				log.info("sleep!!!!!!!!!!!!!!!!!!!!!!");
				Thread.currentThread().sleep(30000);
			}
		}
		log.info(tasks);
	}

}
