package com.asiainfo.monitor.interapi.api.control;

import com.asiainfo.monitor.interapi.ServerControlInfo;
import com.asiainfo.monitor.interapi.config.CallResult;
import com.asiainfo.monitor.interapi.util.ConcurrentServerControl;
import com.asiainfo.monitor.tools.model.interfaces.ITaskContext;

public class ServerControlApi {
	/** 默认执行超时时间 */
	public static final int DEFAULT_TIMEOUT = 60 * 1000;
	/** 默认超时模式（关闭超时） */
	public static final int DEFAULT_TIMEOUT_MODEL = 0;

	public ServerControlApi() {
		super();
	}

	/**
	 * 服务器实时监控，通过访问Url来监控服务器是否正常
	 * @param threadCount
	 * @param timeOut
	 * @param infos
	 * @return
	 * @throws Exception
	 */
	public static ServerControlInfo[] monitorServerControl(int threadCount,int timeOut, ServerControlInfo[] infos) throws Exception {
		return ConcurrentServerControl.parallelCheck(threadCount, timeOut,infos);
	}

	/**
	 * 
	 * @param threadCount 	concurrent thread count
	 * @param timeOut 		0 : no timeout
	 * @param servers
	 * @return
	 * @throws Exception
	 */
	public static CallResult[] start(int threadCount, int timeOut,
			ITaskContext[] tasks) throws Exception {
		return ConcurrentServerControl.operateServer(threadCount, timeOut, tasks);
	}
	
	/**
	 * 
	 * @param tasks
	 * @return
	 * @throws Exception
	 */
	public static CallResult[] start(ITaskContext[] tasks) throws Exception {
		return start(tasks.length, DEFAULT_TIMEOUT_MODEL, tasks);
	}

	/**
	 * 
	 * @param threadCount	concurrent thread count
	 * @param timeOut 		0 : no timeout
	 * @param servers
	 * @return
	 * @throws Exception
	 */
	public static CallResult[] stop(int threadCount, int timeOut,
			ITaskContext[] tasks) throws Exception {
		return ConcurrentServerControl.operateServer(threadCount, timeOut, tasks);
	}

	/**
	 * 
	 * @param tasks
	 * @return
	 * @throws Exception
	 */
	public static CallResult[] stop(ITaskContext[] tasks) throws Exception {
		return stop(tasks.length, DEFAULT_TIMEOUT_MODEL, tasks);
	}
	
	/**
	 * 
	 * @param timeOut 		0 : no timeout
	 * @param server
	 * @return
	 * @throws Exception
	 */
	public static CallResult[] reStart(int threadCount, int timeOut, 
			ITaskContext[] tasks) throws Exception {
		return ConcurrentServerControl.operateServer(threadCount, timeOut, tasks);
	}
	
	/**
	 * 重新启动服务器
	 * @param server
	 * @return
	 * @throws Exception
	 */
	public static CallResult reStart(ITaskContext task) throws Exception {
		return ConcurrentServerControl.operateServer(task);
	}
	
	/**
	 *  重启服务器
	 * @param tasks
	 * @return
	 * @throws Exception
	 */
	public static CallResult[] reStart(ITaskContext[] tasks) throws Exception {
		return reStart(tasks.length, DEFAULT_TIMEOUT_MODEL, tasks);
	}
}
