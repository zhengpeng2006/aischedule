package com.asiainfo.monitor.busi.exe.task.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoGroupBean;
import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoGroupEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPInfoGroupDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;

public class AIMonPInfoGroupDAOImpl implements IAIMonPInfoGroupDAO{
	
	/**
	 * 根据条件查询归属营业区域再分组
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue[] query(String cond, Map param) throws Exception {
		return BOAIMonPInfoGroupEngine.getBeans(cond, param);
	}

	/**
	 * 根据分组代码读取归属区域再分组信息
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue getMonPInfoGroupById(long groupId) throws Exception{		
		return BOAIMonPInfoGroupEngine.getBean(groupId);
	}
	
	/**
	 * 根据分组代码读取归属区域再分组信息
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPInfoGroupValue[] getMonPInfoGroupByCode(String groupCode) throws Exception {
		StringBuilder condition = new StringBuilder("");
		Map parameter = new HashMap();
		
		if (StringUtils.isNotBlank(groupCode)) {
			condition.append(IBOAIMonPInfoGroupValue.S_GroupCode).append(" = :groupCode");
			parameter.put("groupCode", groupCode);
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}

		condition.append(IBOAIMonPInfoGroupValue.S_State).append("= :pState ");
		parameter.put("pState", "U");
		return query(condition.toString(), parameter);
	}
	
	
	
	/**
	 * 修改或保存所属区域信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoGroupValue value) throws Exception {
		if (value.isNew()) {
			value.setGroupId(BOAIMonPInfoGroupEngine.getNewId().longValue());
			value.setState("U");
				OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG, 
						CommonConstants.OPERATE_TYPE_ADD,
						CommonConstants.OPERTATE_OBJECT_MONITOR_GROUP, 
						value.getGroupName(),null);
		}else{
			OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG, 
					CommonConstants.OPERATE_TYPE_MODIFY,
					CommonConstants.OPERTATE_OBJECT_MONITOR_GROUP, 
					value.getGroupName(),null);
		}
		BOAIMonPInfoGroupEngine.save(value);
	}
	
	/**
	 * 批量保存或修改所属区域信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPInfoGroupValue[] values) throws Exception {
		
	}
	
	/**
	 * 删除所属区域信息
	 * 
	 * @param areaId
	 * @throws Exception
	 */
	public void delete(long groupId) throws Exception {
		IBOAIMonPInfoGroupValue value = BOAIMonPInfoGroupEngine.getBean(groupId);
		if (null != value) {
			value.setState("E");
		}
		BOAIMonPInfoGroupEngine.save(value);
	}
	
	/**
	 * 查询大区下的分组
	 * @param busiareaId
	 * @return
	 */
	public BOAIMonPInfoGroupBean[] getMonPInfoGroupByParentId(String parentId)throws Exception{
		HashMap map = new HashMap();
		String cond= IBOAIMonPInfoGroupValue.S_ParentId+"= :parentId ";
		map.put("parentId", parentId);
		return BOAIMonPInfoGroupEngine.getBeans(cond, map);
	}

	public IBOAIMonPInfoGroupValue[] getPInfoGroupByCodeAndName(String groupCode, String groupName,String layer,Integer startNum, Integer endNum) throws Exception {
		StringBuilder condition = new StringBuilder("");
		HashMap params = new HashMap();
		if(StringUtils.isNotBlank(groupCode)){
			condition.append(IBOAIMonPInfoGroupValue.S_GroupCode).append(" = :groupCode ");
			params.put("groupCode", groupCode);
		}
		if(StringUtils.isNotBlank(groupName)){
			if(StringUtils.isNotBlank(condition.toString()))
				condition.append(" and ");
			condition.append(IBOAIMonPInfoGroupValue.S_GroupName).append(" like :groupName ");
			params.put("groupName", groupName+"%");
		}
		if(StringUtils.isNotBlank(layer)){
			if(StringUtils.isNotBlank(condition.toString()))
				condition.append(" and ");
			condition.append(IBOAIMonPInfoGroupValue.S_Layer).append(" = :layer ");
			params.put("layer", layer);
		}
		if(StringUtils.isNotBlank(condition.toString()))
			condition.append(" and ");
		condition.append(IBOAIMonPInfoGroupValue.S_State).append(" = :pState ");
		params.put("pState", "U");
		return BOAIMonPInfoGroupEngine.getBeans(null, condition.toString(), params, startNum, endNum, false);
	}

	public int getPInfoGroupCount(String groupCode, String groupName,String layer) throws Exception {
		StringBuilder condition = new StringBuilder("");
		HashMap params = new HashMap();
		if(StringUtils.isNotBlank(groupCode)){
			condition.append(IBOAIMonPInfoGroupValue.S_GroupCode).append(" = :groupCode ");
			params.put("groupCode", groupCode);
		}
		if(StringUtils.isNotBlank(groupName)){
			if(StringUtils.isNotBlank(condition.toString()))
				condition.append(" and ");
			condition.append(IBOAIMonPInfoGroupValue.S_GroupName).append(" like :groupName ");
			params.put("groupName", groupName+"%");
		}
		if(StringUtils.isNotBlank(layer)){
			if(StringUtils.isNotBlank(condition.toString()))
				condition.append(" and ");
			condition.append(IBOAIMonPInfoGroupValue.S_Layer).append(" = :layer ");
			params.put("layer", layer);
		}
		if(StringUtils.isNotBlank(condition.toString()))
			condition.append(" and ");
		condition.append(IBOAIMonPInfoGroupValue.S_State).append(" = :pState ");
		params.put("pState", "U");
		return BOAIMonPInfoGroupEngine.getBeansCount(condition.toString(), params);
	}
}