package com.asiainfo.monitor.busi.exe.task.service.interfaces;


import java.rmi.RemoteException;
import java.util.List;

import com.asiainfo.monitor.busi.cache.impl.MonPInfoGroup;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;



public interface IAIMonPInfoGroupSV {
	
	
	/**
	 * 修改或保存所属区域再分组信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoGroupValue value) throws Exception;

	/**
	 * 根据代码读取任务分组信息并封装
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public MonPInfoGroup getMonPInfoGroupByCodeFromDb(String code) throws Exception;

	/**
	 * 将任务分组简单封装
	 * @param value
	 * @return
	 */
	public MonPInfoGroup wrapperMonPInfoGroupByBean(IBOAIMonPInfoGroupValue value)
			throws Exception;

	/**
	 * 删除所属区域再分组信息(若该分组关联有任务则报内容为FAIL的异常)
	 * 
	 * @param areaId
	 * @throws Exception
	 */
	public void delete(long areaId) throws Exception;

	/**
	 * 根据代码读取归属区域再分组信息
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List getMonPInfoGroupByCode(String code) throws Exception;

	/**
	 * 获取所有任务分组
	 * @return
	 * @throws Exception
	 */
	public List getAllMonPInfoGroup() throws Exception;

	/**
	 * 根据归属区域标识读取再分组信息
	 * @param busiAreaId
	 * @return
	 * @throws Exception
	 */
	public List getMonPInfoGroupByParentId(long parentId) throws Exception;

	/**
	 * 据组编码和名称获取任务分组信息数
	 * @param groupCode
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public int getPInfoGroupCount(String groupCode, String groupName, String layer)
			throws Exception;

	/**
	 * 获取所有分组信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue[] getAllMonPInfoGroupBean() throws Exception;

	/**
	 * 批量保存或修改所属区域再分组信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoGroupValue[] values) throws Exception;

	/**
	 * 根据组编码和名称获取任务分组信息
	 * @param groupCode
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue[] getPInfoGroupByCodeAndName(String groupCode,
                                                                String groupName, String layer, Integer startNum, Integer endNum)
			throws Exception;

	/**
	 * 根据标识读取归属区域再分组信息
	 * 
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue getMonPInfoGroupById(long itemId)
			throws Exception;
	
}
