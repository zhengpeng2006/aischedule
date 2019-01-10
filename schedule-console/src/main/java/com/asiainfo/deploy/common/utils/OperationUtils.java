package com.asiainfo.deploy.common.utils;

import java.util.List;

import com.asiainfo.deploy.api.external.AppOperationUtils;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.service.ExternalSvUtil;

/**
 * 工具类
 * 
 * @author 孙德东(24204)
 */
public class OperationUtils {
	private OperationUtils() {}
	
	public static boolean checkAllAppsStoped(long nodeId) throws Exception {
		List<IBOAIMonServerValue> apps = ExternalSvUtil.qryServerInfo(String.valueOf(nodeId));

		if (apps != null && !apps.isEmpty()) {
			 return checkAllAppsStoped(apps);
		}
		
		return true;
	}

	/**
	 * 检查应用是否全部停止
	 * @param apps
	 * @return
	 * @throws Exception 
	 */
	private static boolean checkAllAppsStoped(List<IBOAIMonServerValue> apps) throws Exception {
		boolean isAllAppStopped = true;

		for (IBOAIMonServerValue app : apps) {
			boolean	isRunning = AppOperationUtils.isRunningViaSSH(app.getServerCode());
			
			// 命令成功执行，且返回了pid
			if (isRunning) {
				isAllAppStopped = false;
				break;
			}
		}
		return isAllAppStopped;
	}
}
