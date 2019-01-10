package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoGroupBean;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;


public interface IAIMonPInfoGroupDAO {

	/**
	 * 根据条件查询
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue[] query(String cond, Map param) throws Exception;
	
	/**
	 * 根据标识读取区域再分组信息
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue getMonPInfoGroupById(long groupId) throws Exception;
	
	/**
	 * 根据归属区域代码取得区域信息
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue[] getMonPInfoGroupByCode(String groupCode) throws Exception;
	
	/**
	 * 修改或保存所属区域信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoGroupValue value) throws Exception;
	
	/**
	 * 批量保存或修改所属区域信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoGroupValue[] values) throws Exception;
	
	/**
	 * 删除所属区域信息
	 * 
	 * @param areaId
	 * @throws Exception
	 */
	public void delete(long groupId) throws Exception;

	/**
	 * 查询大区下的分组
	 * @param busiareaId
	 * @return
	 */
	public BOAIMonPInfoGroupBean[] getMonPInfoGroupByParentId(String parentId)throws Exception;

	/**
	 * 据组编码和名称获取任务分组信息数
	 * @param groupCode
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public int getPInfoGroupCount(String groupCode, String groupName, String layer) throws Exception;

	/**
	 * 根据组编码和名称获取任务分组信息
	 * @param groupCode
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue[] getPInfoGroupByCodeAndName(String groupCode, String groupName, String layer, Integer startNum, Integer endNum) throws Exception;
}