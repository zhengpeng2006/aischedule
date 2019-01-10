package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCtRecordValue;

public interface IAIMonCtRecordDAO {

	/**
	 * 根据条件查询Group
	 * 
	 * @param cond : String
	 * @param para : Map
	 * @return IBOAIMonGroupValue[]
	 * @throws Exception, RemoteException
	 */
	public IBOAIMonCtRecordValue[] getCtRecordByCondition(String cond, Map para) throws Exception;
	

	/**
	 * 根据组标识，读取组信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCtRecordValue getCtRecordById(String id) throws Exception;
	
	/**
	 * 根据操作类型获取控制记录
	 * @param ctType
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCtRecordValue[] getCtRecordByCtType(String ctType) throws Exception;
	
	/**
	 * 查询当前有效的控制记录
	 * @param ct_Type操作类型
	 * @param obj_Type操作对象的类型
	 * @param obj_Id操作对象的标识
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCtRecordValue[] getEffectCtRecordByObjId(int ct_Type, int obj_Type, String obj_Id) throws Exception;
	
	/**
	 * 查询当前有效的控制记录总数
	 * @param ct_Type
	 * @param obj_Type
	 * @return
	 * @throws Exception
	 */
	public int getEffectCtRecordCountByObjId(int ct_Type, int obj_Type) throws Exception;
	
	/**
	 * 批量保存
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCtRecordValue[] values) throws Exception;

	/**
	 * 保存或修改
	 * @param value
	 * @throws Exception
	 */
	public long saveOrUpdate(IBOAIMonCtRecordValue value) throws Exception;
	
	/**
	 * 删除信息
	 * @param groupId
	 * @throws Exception
	 */
	public void deleteGroup(long groupId) throws Exception;
}
