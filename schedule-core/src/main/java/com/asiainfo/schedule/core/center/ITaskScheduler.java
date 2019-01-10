package com.asiainfo.schedule.core.center;

import java.util.List;

import com.asiainfo.schedule.core.po.TaskBean;

/**
 * 任务时间调度实现接口定义类
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月29日
 * @modify
 */
public interface ITaskScheduler {
	
	public void setManager(String managerId);

	public void shutdown();

	/**
	 * 从调度器中移除任务
	 * 
	 * @param taskCode
	 */
	public void removeTask(String taskCode);

	/**
	 * 移除调度器中的所有任务
	 */
	public void removeAllTask();

	/**
	 * 刷新调度任务列表
	 * 
	 * @param tasks
	 * @throws Exception
	 */
	public void refreshScheduleTask(List<TaskBean> tasks) throws Exception;

	/**
	 * 调度器状态信息
	 * 
	 * @return
	 */
	public SchedulerStat stats();

}
