package com.asiainfo.monitor.busi.exe.task.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonThresholdEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPThresholdDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonThresholdValue;

public class AIMonPThresholdDAOImpl implements IAIMonPThresholdDAO {

	/**
	 * 根据条件查询进程阀值配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonThresholdValue[] query(String condition, Map parameter, Integer startNum, Integer endNum) throws Exception {
		return BOAIMonThresholdEngine.getBeans(null, condition, parameter, startNum, endNum, false);
	}
	
	/**
	 * 根据主键取得进程阀值配置信息
	 * 
	 * @param thresholdId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonThresholdValue getBeanById(long thresholdId) throws Exception {
		return BOAIMonThresholdEngine.getBean(thresholdId);
	}

	/**
	 * 根据表达式检索进程阀值配置信息
	 * 
	 * @param expr
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonThresholdValue[] getThresholdByExpr(String name, Integer startNum, Integer endNum) throws Exception {
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(name)) {
			condition.append(IBOAIMonThresholdValue.S_ThresholdName + " like :name");
			parameter.put("name", "%" + name + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		IBOAIMonThresholdValue[] values = query(condition.toString(), parameter, startNum, endNum);
		return values;
	}
	
	/**
	 * 根据表达式检索取得总件数
	 * 
	 * @param expr
	 * @return
	 * @throws Exception
	 */
	public int getThresholdCount(String name) throws Exception {
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(name)) {
			condition.append(IBOAIMonThresholdValue.S_ThresholdName + " like :name");
			parameter.put("name", "%" + name + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return BOAIMonThresholdEngine.getBeansCount(condition.toString(), parameter);
	}
	
	/**
	 * 批量保存或修改进程阀值配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonThresholdValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setThresholdId(BOAIMonThresholdEngine.getNewId().longValue());
					values[i].setState("U");
				}
			}
		} else {
			throw new Exception();
		}
		BOAIMonThresholdEngine.saveBatch(values);
	}
	
	/**
	 * 保存或修改进程阀值配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonThresholdValue value) throws Exception {
		if (value.isNew()) {
			value.setThresholdId(BOAIMonThresholdEngine.getNewId().longValue());
			value.setState("U");
			OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG, 
					CommonConstants.OPERATE_TYPE_ADD,
					CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM, 
					value.getThresholdName(), "新增告警阀值");
		}else{
			OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG, 
					CommonConstants.OPERATE_TYPE_MODIFY,
					CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM, 
					value.getThresholdName()+":"+value.getThresholdId(), "修改告警阀值");
		}
		BOAIMonThresholdEngine.save(value);
	}
	
	/**
	 * 删除进程阀值配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long thresholdId) throws Exception {
		IBOAIMonThresholdValue value = BOAIMonThresholdEngine.getBean(thresholdId);
		if (null != value) {
//			value.delete();
			value.setState("E");
			BOAIMonThresholdEngine.save(value);
		}
	}
}
