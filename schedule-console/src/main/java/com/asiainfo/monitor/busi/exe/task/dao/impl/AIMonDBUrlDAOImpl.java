package com.asiainfo.monitor.busi.exe.task.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonDBURLEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonDBUrlDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBURLValue;

public class AIMonDBUrlDAOImpl implements IAIMonDBUrlDAO {

	/**
	 * 根据条件查询数据库节点URL配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBURLValue[] query(String cond, Map param) throws Exception {
		return BOAIMonDBURLEngine.getBeans(cond, param);
	}
	
	/**
	 * 根据主键取得数据库节点URL配置信息
	 * 
	 * @param urlName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBURLValue getBeanByName(String urlName) throws Exception {
	  return BOAIMonDBURLEngine.getBean(urlName);
	}
	
	/**
	 * 根据节点URL名称检索配置信息
	 * 
	 * @param urlName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBURLValue[] getDBUrlByName(String urlName) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(urlName)) {
			condition.append(IBOAIMonDBURLValue.S_Name).append(" like :urlName");
			parameter.put("urlName", "%" + urlName + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return query(condition.toString(), parameter);
	}
	
	/**
	 * 批量保存或修改数据库节点URL配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBURLValue[] values) throws Exception {
		BOAIMonDBURLEngine.saveBatch(values);
	}
	
	/**
	 * 保存或修改数据库节点URL配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBURLValue value) throws Exception {
		BOAIMonDBURLEngine.save(value);
	}
	
	/**
	 * 删除数据库节点URL配置
	 * 
	 * @param urlName
	 * @throws Exception
	 */
	public void delete(String urlName) throws Exception {
	  IBOAIMonDBURLValue urlValue = BOAIMonDBURLEngine.getBean(urlName);
		if (null != urlValue) {
		  urlValue.delete();
			BOAIMonDBURLEngine.save(urlValue);
		}
	}
	
	/**
	 * 检查节点URL名称是否存在
	 * 
	 * @param urlName
	 * @return
	 * @throws Exception
	 */
	public boolean checkUrlNameExists(String urlName) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(urlName)) {
			condition.append(IBOAIMonDBURLValue.S_Name).append(" = :urlName");
			parameter.put("urlName", urlName);
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		IBOAIMonDBURLValue[] values = query(condition.toString(), parameter);
		if (null != values && values.length >0) {
			return true;
		} else {
			return false;
		}
	}
}
