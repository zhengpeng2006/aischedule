package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonParamValuesDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamDefineEngine;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamValuesEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;

public class AIMonParamValuesDAOImpl implements IAIMonParamValuesDAO {

	/**
	 * 根据条件，查询参数信息
	 * @param cond
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue[] queryParamValues(String cond,Map para) throws Exception{
		return BOAIMonParamValuesEngine.getBeans(cond,para);
	}
	
	/**
	 * 根据参数标识，获取参数信息
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue getParamValuesById(String id) throws Exception {		
		IBOAIMonParamValuesValue paramValue=BOAIMonParamValuesEngine.getBean(Long.parseLong(id));
		return paramValue;
	}
	
	/**
	 * 根据类型读取所有符合的参数记录
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamValuesValue[] getParamValuesByRegiste(String type,String registeId) throws Exception{
		StringBuilder condition = new StringBuilder("");		
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(type)){
			condition.append(IBOAIMonParamValuesValue.S_RegisteType).append("= :type");
			parameter.put("type",type);
		}
		if (StringUtils.isNotBlank(registeId)){
			if (StringUtils.isNotBlank(condition.toString())){
				condition.append(" and ");
			}
			condition.append(IBOAIMonParamValuesValue.S_RegisteId).append(" = :registeId");
			parameter.put("registeId",registeId);
		}
		condition.append(" ORDER BY "+IBOAIMonParamValuesValue.S_SortId);
		
		return queryParamValues(condition.toString(),parameter);
	}
	
	/**
	 * 批量保存或修改参数设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamValuesValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setVId(BOAIMonParamValuesEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate...");
		}
		BOAIMonParamValuesEngine.saveBatch(values);
	}

	/**
	 * 保存或修改参数设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamValuesValue value) throws Exception {
		if (value.isNew()) {
			value.setVId(BOAIMonParamValuesEngine.getNewId().longValue());
            value.setParamId(BOAIMonParamDefineEngine.getNewId().longValue());
        	OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG, 
					CommonConstants.OPERATE_TYPE_DELETE,
					CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM, 
					value.getParamCode(), "删除告警任务参数");
		}else{
			OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG, 
					CommonConstants.OPERATE_TYPE_DELETE,
					CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM, 
					value.getParamCode()+":"+value.getParamId(), "删除告警任务参数");
		}
		BOAIMonParamValuesEngine.save(value);
	}
	
	/**
	 * 删除参数
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteParamValues (long id) throws Exception{
		IBOAIMonParamValuesValue value = BOAIMonParamValuesEngine.getBean(id);
		if (null != value) {
			value.delete();
			BOAIMonParamValuesEngine.save(value);
		}
	}
	
	public IBOAIMonParamValuesValue[] getParamValuesByParamId(long paramId) throws Exception{
		StringBuilder condition=new StringBuilder("");		
		Map parameter=new HashMap();
		condition.append(IBOAIMonParamValuesValue.S_ParamId).append(" = :sParamId");
		parameter.put("sParamId",paramId);
		return queryParamValues(condition.toString(),parameter);
	}
}
