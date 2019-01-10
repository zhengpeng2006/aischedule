package com.asiainfo.schedule.core.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.schedule.core.data.DataManagerFactory;
import com.asiainfo.schedule.core.po.TaskItemBackupInfo;

/**
 * 主备切换工具类
 *
 * @author dingpk 75499
 * @date 2017年1月4日 上午9:59:58
 */
public class SwitchUtils {

	/**
	 * 获取任务执行错误日志等级；主进程执行失败等级3，备进程执行失败等级4
	 * 
	 * @param serverId
	 * @param taskCode
	 * @param taskItemId
	 * @return
	 */
	public static String getTaskErrorLevel(String serverId, String taskCode, String taskItemId) {
		Map<String,Object> result =getBackupSwitchInfo(serverId, taskCode, taskItemId);
		if (null != result && null != result.get("masterServerId")) {
			return Constants.TaskErrLevel.LEVEL_4;
		}
		return Constants.TaskErrLevel.LEVEL_3;
	}
	
	/**
	 * 判断任务是否在后备进程执行
	 * 
	 * @param serverId
	 * @param taskCode
	 * @param taskItemId
	 * @return
	 */
	public static Map<String,Object> getBackupSwitchInfo(String serverId, String taskCode, String taskItemId){
		try {
			DataManagerFactory dmFactory = DataManagerFactory.getDataManager();
			List<String> masterServerIds = dmFactory.qryHandledBackupRunningServer();
			for (String masterServerId : masterServerIds) {
				List<TaskItemBackupInfo> backupRunningTaskItemList = dmFactory.qryBackupRunningTaskItem(masterServerId);
				if (backupRunningTaskItemList != null) {
					for (TaskItemBackupInfo taskItemBackupInfo : backupRunningTaskItemList) {
						if (taskCode.equals(taskItemBackupInfo.getTaskCode()) && taskItemId.equals(taskItemBackupInfo.getItemCode())
								&& serverId.equals(taskItemBackupInfo.getBackupServerName())) {
							Map<String,Object> result = new HashMap<String,Object>(2);
							result.put("masterServerId", masterServerId);
							return result;
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return null;
	}
}
