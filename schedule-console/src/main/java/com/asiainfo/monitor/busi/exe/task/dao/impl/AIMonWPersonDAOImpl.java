package com.asiainfo.monitor.busi.exe.task.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonWPersonEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonWPersonDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonWPersonValue;

public class AIMonWPersonDAOImpl implements IAIMonWPersonDAO {

	/**
	 * 根据条件查询告警短信发送人员配置信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWPersonValue[] query(String condition, Map parameter, Integer startNum, Integer endNum) throws Exception {
		return BOAIMonWPersonEngine.getBeans(null,condition, parameter,startNum,endNum,false);
	}
	
	/**
	 * 根据主键取得告警短信发送人员配置信息
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWPersonValue getBeanById(long personId) throws Exception {
		return BOAIMonWPersonEngine.getBean(personId);
	}
	
	/**
	 * 根据条件查询告警短信发送人员配置信息
	 * 
	 * @param personName
	 * @param region
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonWPersonValue[] getPersonByCond(String personName, String region, Integer startNum, Integer endNum) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(personName)) {
			condition.append(IBOAIMonWPersonValue.S_Name).append(" like :personName ");
			parameter.put("personName", "%" + personName + "%");
		}
		if (StringUtils.isNotBlank(region)) {
			if (StringUtils.isNotBlank(personName)) {
				condition.append(" and ");
			}
			condition.append(IBOAIMonWPersonValue.S_Region).append(" like :region");
			parameter.put("region", "%" + region + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		IBOAIMonWPersonValue[] values = query(condition.toString(), parameter, startNum, endNum);
		return values;
	}
	
	/**
	 * 根据条件取得总件数
	 * 
	 * @param personName
	 * @param region
	 * @return
	 * @throws Exception
	 */
	public int getPersonCount(String personName, String region) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(personName)) {
			condition.append(IBOAIMonWPersonValue.S_Name).append(" like :personName ");
			parameter.put("personName", "%" + personName + "%");
		}
		if (StringUtils.isNotBlank(region)) {
			if (StringUtils.isNotBlank(personName)) {
				condition.append(" and ");
			}
			condition.append(IBOAIMonWPersonValue.S_Region).append(" like :region");
			parameter.put("region", "%" + region + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return BOAIMonWPersonEngine.getBeansCount(condition.toString(), parameter);
	}

	/**
	 * 批量保存或修改告警短信发送人员配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWPersonValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setPersonId(BOAIMonWPersonEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception();
		}
		BOAIMonWPersonEngine.saveBatch(values);
	}
	
	/**
	 * 保存或修改告警短信发送人员配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonWPersonValue value) throws Exception {
		if (value.isNew()) {
			value.setPersonId(BOAIMonWPersonEngine.getNewId().longValue());
		}
		BOAIMonWPersonEngine.save(value);
	}
	
	/**
	 * 删除告警短信发送人员配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long personId) throws Exception {
		IBOAIMonWPersonValue value = BOAIMonWPersonEngine.getBean(personId);
		if (null != value) {
//			value.delete();
			value.setState("E");
			BOAIMonWPersonEngine.save(value);
		}
	}
}
