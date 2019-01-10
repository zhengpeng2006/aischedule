package com.asiainfo.monitor.busi.dao.interfaces;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonColgAnalyseDefineValue;

public interface IAIMonColgAnalyseDefineDAO {

	/**
	 * 根据标识读取综合分析配置
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgAnalyseDefineValue getBeanById(long id) throws Exception;
	
	/**
	 * 根据分析码、标识读取配置
	 * @param analyseCode：分析类型码
	 * @param analyseId:被分析对象的标识
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonColgAnalyseDefineValue getBeanByAnalyseCodeAndId(String analyseCode, String analyseType, String analyseId) throws Exception;
	
	/**
	 * 保存
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonColgAnalyseDefineValue[] values) throws Exception;

	/**
	 * 保存
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonColgAnalyseDefineValue value) throws Exception ;
	
	/**
	 * 删除配置
	 * @param cmdId
	 * @throws Exception
	 */
	public void deleteDefine(long defineId) throws Exception;
}
