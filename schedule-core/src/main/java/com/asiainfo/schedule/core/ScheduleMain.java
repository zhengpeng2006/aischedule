package com.asiainfo.schedule.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.schedule.core.center.ScheduleManager;
import com.asiainfo.schedule.core.client.TaskProcessorManager;
import com.asiainfo.schedule.core.utils.Constants.ServerType;

/**
 * 调度管理服务器启动类
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月11日
 * @modify
 */
public final class ScheduleMain {
	private static final transient Logger logger = LoggerFactory.getLogger(ScheduleMain.class);

	public static void main(String[] args) throws Exception {
		if (logger.isInfoEnabled() && args != null) {
			for (String s : args)
				logger.info("调度服务器开始运行,参数为：" + s);
		}

		try {
			// 传入参数验证
			if (args == null || args.length == 0) {
				throw new Exception("参数为空，无法确认启动的服务类型，请检查启动脚本。");
			}
			String serverType = args[0];
			// 服务主线程
			Thread serverMainThread;
			if (serverType.equals(ServerType.manager.name())) {
				serverMainThread = new ScheduleManager();
			} else if (serverType.equals(ServerType.processor.name())) {
				serverMainThread = new TaskProcessorManager();
			} else {
				throw new Exception("不支持的服务类型参数！" + serverType);
			}
			// 开始服务主线程
			serverMainThread.start();
			serverMainThread.join();
		} catch (Exception ex) {
			logger.error("start exception!", ex);
			System.exit(1);
		}
		logger.info("Exiting normally");
		System.exit(0);
	}

}
