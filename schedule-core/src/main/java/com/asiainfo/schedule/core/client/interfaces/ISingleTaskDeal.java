package com.asiainfo.schedule.core.client.interfaces;

/**
 * 简单任务处理：单线程执行对对应的任务分片.
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年9月1日
 * @modify
 */
public interface ISingleTaskDeal extends ITaskDeal {

	/**
	 * 任务执行
	 * 
	 * @return
	 * @throws Exception
	 */
	public void execute() throws Exception;

}
