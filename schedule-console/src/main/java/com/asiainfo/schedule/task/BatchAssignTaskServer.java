package com.asiainfo.schedule.task;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.data.ZKDataManagerFactory;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

public class BatchAssignTaskServer {

	public static void main(String[] args) throws Exception {

		if (args == null || args.length == 0) {
			throw new Exception("未传入导入的文件名！");
		}
		String fileName = args[0];

		if (fileName.equals("clearDirtyData")) {
			clearDirtyData();
			System.out.println("clear sus!!!");
			return;
		}

		File f = new File(fileName);
		if (!f.exists() && !f.isFile()) {
			throw new Exception("传入的文件不存在！");
		}

		Properties p = new Properties();
		p.load(new FileInputStream(f));

		List<String> sus = new ArrayList<String>();
		List<String> fail = new ArrayList<String>();
		IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
		ISchedulerSV ssv = (ISchedulerSV) ServiceFactory.getService(ISchedulerSV.class);
		for (Entry<Object, Object> e : p.entrySet()) {
			String key = (String) e.getKey();
			String value = (String) e.getValue();
			try {
				if (key.indexOf("@@") < 0) {
					throw new Exception("编码配置格式有误.." + key);
				}
				String[] tmp = key.split("@@");
				if (tmp.length != 2) {
					throw new Exception("编码配置格式有误.." + key);
				}
				String taskCode = tmp[0];
				String taskItemCode = tmp[1];
				String serverId = value.trim();

				TaskBean task = ssv.getTaskInfoByTaskCode(taskCode);
				if (task == null) {
					throw new Exception("任务不存在！！");
				}

				String[] aa = ssv.getTaskItemsByTaskCode(taskCode);
				boolean isFound = false;
				if (aa != null && aa.length > 0) {
					for (String a : aa) {
						if (a.equals(taskItemCode)) {
							isFound = true;
							break;
						}
					}
				}
				if (!isFound) {
					throw new Exception("任务拆分项不存在！！！" + taskCode + "," + taskItemCode);
				}

				boolean code = serverSv.isExistServerByCode(serverId);
				if (!code) {
					throw new Exception("配置的服务编码不存在！！！" + serverId);
				}
				ssv.assignServer2TaskItem(taskCode, taskItemCode, serverId);

				sus.add(key);
			} catch (Throwable t) {
				System.err.println(key + ",导入失败！");
				t.printStackTrace();
				fail.add(key);
			}

		}

		StringBuilder result = new StringBuilder(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
		result.append("批量导入的总条数=").append(p.size()).append("\n");
		result.append("更新成功数：").append(sus.size()).append("\n");
		for (String s : sus) {
			result.append(s).append("\n");
		}
		result.append("更新失败数：").append(fail.size()).append("\n");
		for (String s : fail) {
			result.append(s).append("\n");
		}
		result.append("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
		System.out.println(result.toString());
	}

	private static void clearDirtyData() {
		try {
			ZKDataManagerFactory zkdm = (ZKDataManagerFactory) DataManagerFactory.getDataManager();
			String rootPath = zkdm.getScheduleConfig().getZkRootPath();
			zkdm.deleteTree( rootPath + "/server");
			List<String> ll = zkdm.getAllTaskCodes();
			if (ll != null && ll.size() > 0) {
				for (String taskCode : ll) {
					List<String> items = zkdm.getAllTaskItem(taskCode);
					if (items != null && items.size() > 0) {
						for (String item : items) {
							String ppp = rootPath + "/task/" + taskCode + "/item/" + item + "/jobId";
							if (zkdm.getZooKeeper().exists(ppp, false) != null) {
								System.out.println(">>>>>>>>>开始删除目录：" + ppp);
								zkdm.deleteTree(ppp);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
