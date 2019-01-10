package com.asiainfo.common.abo.dao.interfaces;

import java.util.Map;

import com.asiainfo.common.abo.bo.BOSchedulerOperationsBean;
import com.asiainfo.common.abo.ivalues.IBOSchedulerOperationsValue;

public interface ISchedulerOperationsDAO {
	
	BOSchedulerOperationsBean getBeanById(long id) throws Exception;
	
	/**
	 * 根据条件查询日志 支持分页（不需要时传-1）
	 * @param condition 查询条件 key为IBOSchedulerOperationsValue的属性名
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	IBOSchedulerOperationsValue [] getBeanByCondition(Map<String, Object> condition, int start, int end) throws Exception;
	
	/**
	 * 根据条件查询总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	int getBeansCount(Map<String, Object> condition) throws Exception;

}
