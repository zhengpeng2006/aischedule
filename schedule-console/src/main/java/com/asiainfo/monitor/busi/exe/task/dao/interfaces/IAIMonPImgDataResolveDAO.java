package com.asiainfo.monitor.busi.exe.task.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPImgDataResolveValue;

public interface IAIMonPImgDataResolveDAO {

	/**
	 * 查询
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue[] query(String condition, Map parameter) throws Exception;
	
	/**
	 * 根据条件查询
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue[] query(String condition, Map parameter, Integer startNum, Integer endNum) throws Exception ;
	
	/**
	 * 根据ID查询
	 * 
	 * @param grpId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue getMonPImgDataResolveById(long resolveId) throws Exception;
	
	/**
	 * 根据名称取得图形展示分组配置信息
	 * 
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue[] getImgDataResolveByName(String name, Integer startNum, Integer endNum) throws Exception;
	
	public int getImgDataResolveCount(String name) throws Exception ;
	
	/**
	 * 批量保存或修改
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPImgDataResolveValue[] values) throws Exception;
	
	/**
	 * 保存或修改
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPImgDataResolveValue value) throws Exception;
	
	/**
	 * 删除
	 * 
	 * @param groupId
	 * @throws Exception
	 */
	public void delete(long groupId) throws Exception;
	
}
