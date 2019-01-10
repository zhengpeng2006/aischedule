package com.asiainfo.schedule.core.client.interfaces;

import java.util.Comparator;
import java.util.List;

/**
 * 复杂任务处理接口类：主框架对任务分片轮询取数、多线程处理任务数据，支持无数据退出模式。
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年9月1日
 * @modify
 * @param <T>
 *          业务处理对象
 */
public interface IComplexTaskDeal<T> extends ITaskDeal {

	/**
	 * 获取不到数据时是否退出执行
	 * 
	 * @return true:finish
	 */
	public boolean isFinishNoData();

	/**
	 * 获取任务需要处理的总数
	 * 
	 * @return -1：无固定数（动态工单数据）
	 * @throws Exception
	 */
	public long getTaskDataTotal() throws Exception;

	/**
	 * 获取任务数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<T> selectTasks() throws Exception;

	/**
	 * 处理任务数据
	 * 
	 * @param tasks
	 *          任务数据
	 * @return
	 * @throws Exception
	 */
	public void execute(List<T> tasks) throws Exception;

	public Comparator<T> getComparator();

}
