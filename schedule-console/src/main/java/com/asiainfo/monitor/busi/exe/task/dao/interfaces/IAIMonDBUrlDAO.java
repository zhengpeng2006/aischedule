package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonDBURLValue;

public interface IAIMonDBUrlDAO {

	/**
	 * 根据条件查询数据库节点URL配置
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBURLValue[] query(String cond, Map param) throws Exception;
	
	/**
	 * 根据主键取得数据库节点URL配置信息
	 * 
	 * @param urlName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBURLValue getBeanByName(String urlName) throws Exception;
	
	/**
	 * 根据节点URL名称检索配置信息
	 * 
	 * @param urlName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonDBURLValue[] getDBUrlByName(String urlName) throws Exception;
	
	/**
	 * 批量保存或修改数据库节点URL配置
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBURLValue[] values) throws Exception;
	
	/**
	 * 保存或修改数据库节点URL配置
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonDBURLValue value) throws Exception;
	
	/**
	 * 删除数据库节点URL配置
	 * 
	 * @param urlName
	 * @throws Exception
	 */
	public void delete(String urlName) throws Exception;
	
	/**
	 * 检查节点URL名称是否存在
	 * 
	 * @param urlName
	 * @return
	 * @throws Exception
	 */
	public boolean checkUrlNameExists(String urlName) throws Exception;
}
