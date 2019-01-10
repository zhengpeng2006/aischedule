package com.asiainfo.schedule.core.client.interfaces;

/**
 * 休眠时间计算接口类
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2015年2月5日
 * @modify
 */
public interface ISleepTimeController {

	/**
	 * @param executeTaskCount
	 *          执行任务数量
	 * @param taskDealTime
	 *          任务执行耗时
	 * @return 执行休眠时间，单位：毫秒。
	 *         返回-1为不休眠，立即执行任务取数并执行任务；返回的休眠时间如果大于任务配置的空闲休眠时间，则取任务配置的休眠时间。
	 */
	public int getSleepTime(long executeTaskCount, long taskDealTime);

}
