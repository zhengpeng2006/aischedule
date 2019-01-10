package com.asiainfo.monitor.busi.exe.task.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoFilterEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPInfoFilterDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoFilterValue;

public class AIMonPInfoFilterDAOImpl implements IAIMonPInfoFilterDAO {

	/**
	 * 根据主键取得任务过滤信息
	 * @param filterId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoFilterValue getFilterById(long filterId) throws Exception {
		return BOAIMonPInfoFilterEngine.getBean(filterId);
	}
	
	/**
	 * 根据条件取得任务过滤信息
	 * @param condition
	 * @param parameter
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoFilterValue[] query(String condition, Map parameter, Integer startNum, Integer endNum) throws Exception {
		return BOAIMonPInfoFilterEngine.getBeans(null, condition, parameter, startNum, endNum, false);
	}
	
	/**
	 * 根据条件取得任务过滤信息
	 * 
	 * @param expr
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoFilterValue[] getFilterByCond(String filterName, Integer startNum, Integer endNum) throws Exception {
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(filterName)) {
			condition.append(IBOAIMonPInfoFilterValue.S_FilterName + " like :name");
			parameter.put("name", "%" + filterName + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		IBOAIMonPInfoFilterValue[] values = query(condition.toString(), parameter, startNum, endNum);
		return values;
	}
	
	/**
	 * 取得总件数
	 * 
	 * @param expr
	 * @return
	 * @throws Exception
	 */
	public int getFilterCount(String filterName) throws Exception {
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(filterName)) {
			condition.append(IBOAIMonPInfoFilterValue.S_FilterName + " like :name");
			parameter.put("name", "%" + filterName + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");

		return BOAIMonPInfoFilterEngine.getBeansCount(condition.toString(), parameter);
	}

	/**
	 * 保存或修改任务过滤信息
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoFilterValue value) throws Exception {
		if (value.isNew()) {
			value.setFilterId(BOAIMonPInfoFilterEngine.getNewId().longValue());
		}
		BOAIMonPInfoFilterEngine.save(value);
	}
	
	/**
	 * 批量保存或修改任务过滤信息
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoFilterValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setFilterId(BOAIMonPInfoFilterEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception();
		}
		BOAIMonPInfoFilterEngine.saveBatch(values);
	}
	
	/**
	 * 删除任务过滤信息
	 * @param filterId
	 * @throws Exception
	 */
	public void delete(long filterId) throws Exception {
		IBOAIMonPInfoFilterValue value = BOAIMonPInfoFilterEngine.getBean(filterId);
		if (null != value) {
			value.setState("E");
			BOAIMonPInfoFilterEngine.save(value);
		}
	}
}
