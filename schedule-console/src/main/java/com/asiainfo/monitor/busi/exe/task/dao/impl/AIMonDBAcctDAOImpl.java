package com.asiainfo.monitor.busi.exe.task.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonDBAcctEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonDBAcctDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBAcctValue;

public class AIMonDBAcctDAOImpl implements IAIMonDBAcctDAO {

	/**
	 * 根据条件查询数据源配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBAcctValue[] query(String cond, Map param) throws Exception {
		return BOAIMonDBAcctEngine.getBeans(cond, param);
	}

	/**
	 * 根据主键取得数据源配置信息
	 * 
	 * @param acctCode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBAcctValue getBeanByCode(String acctCode) throws Exception {
		return BOAIMonDBAcctEngine.getBean(acctCode);
	}
	
	/**
	 * 根据数据源配置CODE检索信息
	 * 
	 * @param acctCode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBAcctValue[] getDBAcctByCode(String acctCode) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		if (StringUtils.isNotBlank(acctCode)) {
			condition.append(IBOAIMonDBAcctValue.S_DbAcctCode).append(" like :acctCode");
			parameter.put("acctCode", "%" + acctCode + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return query(condition.toString(), parameter);
	}
	
	/**
	 * 批量保存或修改数据源配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBAcctValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					// 设置AcctCode
				}
			}
		} else {
			// 抛出异常
		}
		BOAIMonDBAcctEngine.saveBatch(values);
	}

	/**
	 * 保存或修改数据源配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBAcctValue value) throws Exception {
		BOAIMonDBAcctEngine.save(value);
	}
	
	/**
	 * 删除数据源配置
	 * 
	 * @param acctCode
	 * @throws Exception
	 */
	public void delete(String acctCode) throws Exception {
		IBOAIMonDBAcctValue value = BOAIMonDBAcctEngine.getBean(acctCode);
		if (null != value) {
			value.delete();
//			value.setState("E");
			BOAIMonDBAcctEngine.save(value);
		}
	}

	/**
	 * 检查数据源代码是否存在
	 * 
	 * @param acctCode
	 * @return true  存在
	 *         false 不存在
	 * @throws Exception
	 */
	public boolean checkCodeExists(String acctCode) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		condition.append(IBOAIMonDBAcctValue.S_DbAcctCode + " =:acctCode");
		parameter.put("acctCode", acctCode);
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		IBOAIMonDBAcctValue[] value = query(condition.toString(), parameter);
		if (null != value && value.length > 0) {
			return true;
		} else {
			return false;
		}
	}
}
