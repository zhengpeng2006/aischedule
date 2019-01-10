package com.asiainfo.monitor.busi.exe.task.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWDtlEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonWDtlDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWDtlValue;

public class AIMonWDtlDAOImpl implements IAIMonWDtlDAO{

	/**
	 * 根据条件查询监控信息发送人员关联配置信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue[] query(String condition, Map parameter, Integer startNum, Integer endNum) throws Exception {
		return BOAIMonWDtlEngine.getBeans(null, condition, parameter, startNum, endNum, false);
	}

	/**
	 * 根据主键取得监控信息发送人员关联配置信息
	 * 
	 * @param dtlId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue getBeanById(long dtlId) throws Exception {
		return BOAIMonWDtlEngine.getBean(dtlId);
	}
	
	/**
	 * 根据条件取得查询信息
	 * 
	 * @param taskId
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue[] getDtlByCond(String taskId, String personId, Integer startNum, Integer endNum) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(taskId)) {
			condition.append(IBOAIMonWDtlValue.S_InfoId).append(" =:taskId");
			parameter.put("taskId", Long.parseLong(taskId));
		}
		if (StringUtils.isNotBlank(personId)) {
			if (StringUtils.isNotBlank(taskId)) {
				condition.append(" and ");
			}
			condition.append(IBOAIMonWDtlValue.S_PersonId).append(" =:personId");
			parameter.put("personId", Long.parseLong(personId));
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return query(condition.toString(), parameter, startNum, endNum);
	}
	
	/**
	 * 根据任务读取任务警告发送人员信息
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWDtlValue[] getDtlByTaskId(String taskId) throws Exception{
		StringBuilder condition=new StringBuilder();
		Map parameter = new HashMap();
		condition.append(IBOAIMonWDtlValue.S_InfoId).append(" =:taskId");
		parameter.put("taskId", Long.parseLong(taskId));
		IBOAIMonWDtlValue[] result=BOAIMonWDtlEngine.getBeans(condition.toString(), parameter);
		return result;			
	}
	
	/**
	 * 根据条件取得总件数
	 * 
	 * @param taskId
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public int getDtlCount(String taskId, String personId) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(taskId)) {
			condition.append(IBOAIMonWDtlValue.S_InfoId).append(" =:taskId");
			parameter.put("taskId", Long.parseLong(taskId));
		}
		if (StringUtils.isNotBlank(personId)) {
			if (StringUtils.isNotBlank(taskId)) {
				condition.append(" and ");
			}
			condition.append(IBOAIMonWDtlValue.S_PersonId).append(" =:personId");
			parameter.put("personId", Long.parseLong(personId));
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return BOAIMonWDtlEngine.getBeansCount(condition.toString(), parameter);
	}
	
	/**
	 * 批量保存或修改监控信息发送人员关联配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWDtlValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setDtlId(BOAIMonWDtlEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception();
		}
		BOAIMonWDtlEngine.saveBatch(values);
	}
	
	/**
	 * 保存或修改监控信息发送人员关联配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWDtlValue value) throws Exception {
		if (value.isNew()) {
			value.setDtlId(BOAIMonWDtlEngine.getNewId().longValue());
		}
		BOAIMonWDtlEngine.save(value);
	}
	
	/**
	 * 删除监控信息发送人员关联配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long dtlId) throws Exception {
		IBOAIMonWDtlValue value = BOAIMonWDtlEngine.getBean(dtlId);
		if (null != value) {
//			value.delete();
			value.setState("E");
			BOAIMonWDtlEngine.save(value);
		}
	}
}
