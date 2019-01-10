package com.asiainfo.monitor.busi.exe.task.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPTimeEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPTimeDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTimeValue;

public class AIMonPTimeDAOImpl implements IAIMonPTimeDAO {

	/**
	 * 根据条件查询监控时间段配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTimeValue[] query(String condition, Map parameter, Integer startNum, Integer endNum) throws Exception {
		return BOAIMonPTimeEngine.getBeans(null, condition, parameter, startNum, endNum, false);
	}
	
	/**
	 * 根据主键取得监控时间段配置信息
	 * 
	 * @param timeId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTimeValue getBeanById(long timeId) throws Exception {
		return BOAIMonPTimeEngine.getBean(timeId);
	}
	
	/**
	 * 根据条件检索监控时间段配置信息
	 * 
	 * @param expr
	 * @param startNum
	 * @param endNum
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTimeValue[] getTimeInfoByExpr(String expr, Integer startNum, Integer endNum) throws Exception {
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(expr)) {
			condition.append(IBOAIMonPTimeValue.S_Expr).append(" like :expr");
			parameter.put("expr", "%" + expr + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return query(condition.toString(), parameter, startNum, endNum);
	}
	
	/**
	 * 根据条件检索取得总件数
	 * 
	 * @param expr
	 * @return
	 * @throws Exception
	 */
	public int getTimeInfoCount(String expr) throws Exception {
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(expr)) {
			condition.append(IBOAIMonPTimeValue.S_Expr).append(" like :expr");
			parameter.put("expr", "%" + expr + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return BOAIMonPTimeEngine.getBeansCount(condition.toString(), parameter);
	}
	
	/**
	 * 批量保存或修改监控时间段配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTimeValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setTimeId(BOAIMonPTimeEngine.getNewId().longValue());
					values[i].setState("U");
				}
			}
		} else {
			throw new Exception();
		}
		BOAIMonPTimeEngine.saveBatch(values);
	}
	
	/**
	 * 保存或修改监控时间段配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTimeValue value) throws Exception {
		if (value.isNew()) {
			value.setTimeId(BOAIMonPTimeEngine.getNewId().longValue());
			value.setState("U");
			OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG, 
					CommonConstants.OPERATE_TYPE_ADD,
					CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM, 
					StringUtils.isNotBlank(value.getRemarks())?value.getRemarks():value.getExpr(),
					"新增告警时间");
		}else{
			OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG, 
					CommonConstants.OPERATE_TYPE_MODIFY,
					CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM, 
					(StringUtils.isNotBlank(value.getRemarks())?value.getRemarks():value.getExpr())+":"+value.getTimeId(),
					"修改告警时间");
		}
		BOAIMonPTimeEngine.save(value);
	}
	
	/**
	 * 删除监控时间段配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long timeId) throws Exception {
		IBOAIMonPTimeValue value = BOAIMonPTimeEngine.getBean(timeId);
		if (null != value) {
//			value.delete();
			value.setState("E");
			BOAIMonPTimeEngine.save(value);
		}
	}
}
