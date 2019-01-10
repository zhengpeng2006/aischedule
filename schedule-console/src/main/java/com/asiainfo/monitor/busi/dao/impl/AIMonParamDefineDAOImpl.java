package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonParamDefineDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamDefineEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamDefineValue;


public class AIMonParamDefineDAOImpl implements IAIMonParamDefineDAO {

	/**
	 * 根据条件，查询参数信息
	 * @param cond
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamDefineValue[] queryParamDefine(String cond,Map para) throws Exception{
		return BOAIMonParamDefineEngine.getBeans(cond,para);
	}
	
	/**
	 * 根据参数标识，获取参数信息
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamDefineValue getParamDefineById(String id) throws Exception {		
		IBOAIMonParamDefineValue paramDefine=BOAIMonParamDefineEngine.getBean(Long.parseLong(id));
		return paramDefine;
	}
	
	/**
	 * 根据类型读取所有符合的参数记录
	 * @param type
	 * @param kind
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonParamDefineValue[] getParamDefineByRegiste(String type,String registeId) throws Exception{
		StringBuilder condition=new StringBuilder("");		
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(type)){
			condition.append(IBOAIMonParamDefineValue.S_RegisteType).append("= :type");
			parameter.put("type",type);
		}
		if (StringUtils.isNotBlank(registeId)){
			if (StringUtils.isNotBlank(condition.toString())){
				condition.append(" and ");
			}
			condition.append(IBOAIMonParamDefineValue.S_RegisteId).append(" = :registeId");
			parameter.put("registeId",registeId);
		}
		condition.append(" ORDER BY "+IBOAIMonParamDefineValue.S_SortId);
		
		return queryParamDefine(condition.toString(),parameter);
	}
	
	/**
	 * 批量保存或修改参数设置
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamDefineValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setParamId(BOAIMonParamDefineEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate...");
		}
		BOAIMonParamDefineEngine.saveBatch(values);
	}

	/**
	 * 保存或修改参数设置
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonParamDefineValue value) throws Exception {
		if (value.isNew()) {
			value.setParamId(BOAIMonParamDefineEngine.getNewId().longValue());
		}
		BOAIMonParamDefineEngine.save(value);
	}
	
	/**
	 * 删除参数
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteParamDefine (long id) throws Exception{
		IBOAIMonParamDefineValue value = BOAIMonParamDefineEngine.getBean(id);
		if (null != value) {
			value.delete();
			BOAIMonParamDefineEngine.save(value);
		}
	} 
}
