package com.asiainfo.monitor.busi.exe.task.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPTableEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPTableDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTableValue;

public class AIMonPTableDAOImpl implements IAIMonPTableDAO {

	/**
	 * 根据条件查询表数据监控配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTableValue[] query(String condition, Map parameter, Integer startNum, Integer endNum) throws Exception {
		return BOAIMonPTableEngine.getBeans(null, condition, parameter, startNum, endNum, false);
	}
	
	/**
	 * 根据主键取得表数据监控配置信息
	 * 
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTableValue getBeanById(long tableId) throws Exception {
		return BOAIMonPTableEngine.getBean(tableId);
	}
	
	/**
	 * 根据名称取得表数据监控配置信息
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPTableValue[] getTableInfoByName(String tableName, Integer startNum, Integer endNum) throws Exception {
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(tableName)) {
			condition.append(IBOAIMonPTableValue.S_Name).append(" like :tableName");
			parameter.put("tableName", "%" + tableName + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		IBOAIMonPTableValue[] values = query(condition.toString(), parameter, startNum, endNum);
		return values;
	}
	
	public int getTableInfoCount(String tableName) throws Exception {
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(tableName)) {
			condition.append(IBOAIMonPTableValue.S_Name + " like :tableName");
			parameter.put("tableName", "%" + tableName + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return BOAIMonPTableEngine.getBeansCount(condition.toString(), parameter);
	}
	
	/**
	 * 批量保存或修改表数据监控配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTableValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setTableId(BOAIMonPTableEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("");
		}
		BOAIMonPTableEngine.saveBatch(values);
	}
	
	/**
	 * 保存或修改表数据监控配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPTableValue value) throws Exception {
		if (value.isNew()) {
			value.setTableId(BOAIMonPTableEngine.getNewId().longValue());
		}
		BOAIMonPTableEngine.save(value);
	}
	
	/**
	 * 删除表数据监控配置
	 * 
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(long tableId) throws Exception {
		IBOAIMonPTableValue value = BOAIMonPTableEngine.getBean(tableId);
		if(null != value) {
//			value.delete();
			value.setState("E");
			BOAIMonPTableEngine.save(value);
		}
	}
}
