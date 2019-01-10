package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBAcctValue;

public interface IAIMonDBAcctDAO {

	/**
	 * 根据条件查询数据源配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBAcctValue[] query(String cond, Map param) throws Exception;
	
	/**
	 * 根据主键取得数据源配置信息
	 * 
	 * @param acctCode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBAcctValue getBeanByCode(String acctCode) throws Exception;
	
	/**
	 * 根据数据源配置CODE检索信息
	 * 
	 * @param acctCode
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBAcctValue[] getDBAcctByCode(String acctCode) throws Exception;

	/**
	 * 批量保存或修改数据源配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBAcctValue[] values) throws Exception;
	
	/**
	 * 保存或修改数据源配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBAcctValue value) throws Exception;
	
	/**
	 * 删除数据源配置
	 * 
	 * @param acctCode
	 * @throws Exception
	 */
	public void delete(String acctCode) throws Exception;
	
	/**
	 * 检查数据源代码是否存在
	 * 
	 * @param acctCode
	 * @return
	 * @throws Exception
	 */
	public boolean checkCodeExists(String acctCode) throws Exception;
}
