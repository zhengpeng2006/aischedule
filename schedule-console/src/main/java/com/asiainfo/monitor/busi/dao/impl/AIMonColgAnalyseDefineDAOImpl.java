package com.asiainfo.monitor.busi.dao.impl;


import java.util.HashMap;
import java.util.Map;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonColgAnalyseDefineDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonColgAnalyseDefineEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgAnalyseDefineValue;

public class AIMonColgAnalyseDefineDAOImpl implements IAIMonColgAnalyseDefineDAO{

	/**
	 * 根据标识读取综合分析配置
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgAnalyseDefineValue getBeanById(long id) throws Exception{
		return BOAIMonColgAnalyseDefineEngine.getBean(id);
	}

	/**
	 * 根据分析码、标识读取配置
	 * @param analyseCode：分析类型码
	 * @param analyseId:被分析对象的标识
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgAnalyseDefineValue getBeanByAnalyseCodeAndId(String analyseCode,String analyseType,String analyseId) throws Exception{
		StringBuilder sb=new StringBuilder("");
		sb.append(IBOAIMonColgAnalyseDefineValue.S_AnalyseCode+" =:analyseCode");
		sb.append(" AND ");
		sb.append(IBOAIMonColgAnalyseDefineValue.S_AnalyseType+" =:analyseType ");
		sb.append(" AND ");
		sb.append(IBOAIMonColgAnalyseDefineValue.S_AnalyseId+ "= :analyseId");
		sb.append(" AND "+IBOAIMonColgAnalyseDefineValue.S_State+" ='U' ");
		Map parameter=new HashMap();
		parameter.put("analyseCode",analyseCode);
		parameter.put("analyseType",analyseType);
		parameter.put("analyseId",analyseId);
		IBOAIMonColgAnalyseDefineValue[] values=BOAIMonColgAnalyseDefineEngine.getBeans(sb.toString(),parameter);
		if (values==null || values.length<1)
			return null;
		else
			return values[0];
	}
	
	/**
	 * 保存
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonColgAnalyseDefineValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setDefineId(BOAIMonColgAnalyseDefineEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("no data to saveOrUpdate...");
		}
		BOAIMonColgAnalyseDefineEngine.saveBatch(values);
	}

	/**
	 * 保存
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonColgAnalyseDefineValue value) throws Exception {
		if (value.isNew()) {
			value.setDefineId(BOAIMonColgAnalyseDefineEngine.getNewId().longValue());
		}
		BOAIMonColgAnalyseDefineEngine.save(value);
	}
	
	/**
	 * 删除配置
	 * @param cmdId
	 * @throws Exception
	 */
	public void deleteDefine (long defineId) throws Exception{
		IBOAIMonColgAnalyseDefineValue value = BOAIMonColgAnalyseDefineEngine.getBean(defineId);
		if (null != value) {
			value.setState("E");
			BOAIMonColgAnalyseDefineEngine.save(value);
		}
	} 
}
