package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;

/**
 * AppMonitor 静态数据查询dao
 * @author lisong
 *
 */
public interface IAIMonStaticDataDAO {
	/**
	 * 通过CodeType查询静态数据集
	 * @param codeType
	 * @return 
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] queryByCodeType(String codeType) throws Exception;
	
	/**
	 * 通过ExternCodeType查询静态数据集
	 * @param externCodeType
	 * @return 
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] queryByECT(String externCodeType) throws Exception;
	
	/**
	 * 通过CodeType和CodeValue查询具体的静态数据条目
	 * @param codeType
	 * @param codeValue
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue queryByCodeTypeAndValue(String codeType, String codeValue) throws Exception;
	
	/**
	 * 通过查询条件查询
	 * @param cond
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonStaticDataValue[] query(String cond, Map parameter) throws Exception;
	
	/**
	 * 保存静态数据
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonStaticDataValue value) throws Exception;
	
	/**
	 * 批量保存静态数据
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonStaticDataValue[] values) throws Exception;

	public IBOAIMonStaticDataValue[] queryByEct(String externCodeType)
			throws Exception;
	
	/**
     * 通过静态表查询配置的任务信息
     * @param codeType（taskType）
     * @param codeValue（taskCode）
     * @return
     */
    public IBOAIMonStaticDataValue[] qryTaskInfos(String codeType, String codeValue, String taskCode1) throws Exception;
}
