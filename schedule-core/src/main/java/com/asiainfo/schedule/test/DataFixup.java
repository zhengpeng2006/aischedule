package com.asiainfo.schedule.test;

import java.util.List;

import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.data.ZKDataManagerFactory;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.utils.JsonUtils;

public class DataFixup {

	public static void main(String[] args) throws Exception {
		ZKDataManagerFactory dm = (ZKDataManagerFactory) DataManagerFactory.getDataManager();
		List<String> list = dm.getAllTaskCodes();
		for (String s : list) {
			byte[] b = dm.getZooKeeper().getData("/ai-schedule-center-ams/task/" + s, false, null);
			String a = new String(b,"GBK");
			try {
				TaskBean t = JsonUtils.fromJson(a, TaskBean.class);
			} catch (Exception ex) {
//				ex.printStackTrace();
				System.out.println(a);
				
				TaskBean t = JsonUtils.fromJson(new String(b,"UTF-8"), TaskBean.class);
				t.setTaskName(t.getTaskCode());
				dm.updateTask(t);
			}

		}
	}

}
