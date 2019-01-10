package com.asiainfo.monitor.busi.exe.task.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPExecEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPExecDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPExecValue;

public class AIMonPExecDAOImpl implements IAIMonPExecDAO {

	/**
	 * 根据条件查询进程监控配置
	 * 
	 * @param name
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPExecValue[] query(String condition, Map parameter, Integer startNum, Integer endNum) throws Exception {
		return BOAIMonPExecEngine.getBeans(null, condition, parameter, startNum, endNum, false);
	}

	/**
	 * 根据主键取得进程监控配置信息
	 * 
	 * @param execId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPExecValue getBeanById(long execId) throws Exception {
		return BOAIMonPExecEngine.getBean(execId);
	}
	
	public long getExecId() throws Exception {
		return BOAIMonPExecEngine.getNewId().longValue();
	}
	
	public IBOAIMonPExecValue[] getExecByName(String execName, Integer startNum, Integer endNum) throws Exception {
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(execName)) {
			condition.append(IBOAIMonPExecValue.S_Name).append(" like :execName");
			parameter.put("execName", "%" + execName + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return query(condition.toString(), parameter, startNum, endNum);
	}
	
	public int getExecCount(String execName) throws Exception {
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(execName)) {
			condition.append(IBOAIMonPExecValue.S_Name).append(" like :execName");
			parameter.put("execName", "%" + execName + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return BOAIMonPExecEngine.getBeansCount(condition.toString(), parameter);
	}
	
	/**
	 * 批量保存或修改进程监控配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPExecValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setExecId(BOAIMonPExecEngine.getNewId().longValue());
					values[i].setState("U");
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate....");
		}
		BOAIMonPExecEngine.saveBatch(values);
	}
	
	/**
	 * 保存或修改进程监控配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public long saveOrUpdate(IBOAIMonPExecValue value) throws Exception {
		long execId = BOAIMonPExecEngine.getNewId().longValue();
		if (value.isNew()) {
			value.setExecId(execId);
			value.setState("U");
			OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG, 
					CommonConstants.OPERATE_TYPE_ADD,
					CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM, 
					value.getName(), "新增告警监控脚本");
		}else{
			OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG, 
					CommonConstants.OPERATE_TYPE_MODIFY,
					CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM, 
					value.getName()+":"+value.getExecId(), "修改告警监控脚本");
		}
		BOAIMonPExecEngine.save(value);
		return execId;
	}
	
	/**
	 * 删除进程监控配置
	 * 
	 * @param execId
	 * @throws Exception
	 */
	public void delete(long execId) throws Exception {
		IBOAIMonPExecValue value = BOAIMonPExecEngine.getBean(execId);
		if (null != value) {
//			value.delete();
			value.setState("E");
			BOAIMonPExecEngine.save(value);
		}
	}
}
