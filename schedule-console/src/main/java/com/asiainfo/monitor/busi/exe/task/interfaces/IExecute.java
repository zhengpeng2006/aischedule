package com.asiainfo.monitor.busi.exe.task.interfaces;


import com.asiainfo.monitor.tools.model.interfaces.ITaskContext;

public interface IExecute {

	/**
	 * 执行任务
	 * @param task
	 * @return
	 * @throws Exception
	 */
	public void execute(ITaskContext task) throws Exception;
}
