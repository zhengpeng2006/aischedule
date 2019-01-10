package com.asiainfo.monitor.busi.exe.task.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPImgDataResolveEngine;
import com.asiainfo.monitor.busi.exe.task.dao.interfaces.IAIMonPImgDataResolveDAO;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPImgDataResolveValue;

public class AIMonPImgDataResolveDAOImpl implements IAIMonPImgDataResolveDAO {
	/**
	 * 查询
	 * 
	 * @param condition
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue[] query(String condition, Map parameter) throws Exception {
		return BOAIMonPImgDataResolveEngine.getBeans(condition, parameter);
	}
	
	/**
	 * 根据条件查询
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue[] query(String condition, Map parameter, Integer startNum, Integer endNum) throws Exception {
		return BOAIMonPImgDataResolveEngine.getBeans(null, condition, parameter, startNum, endNum, false);
	}
	
	/**
	 * 根据ID查询
	 * 
	 * @param grpId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue getMonPImgDataResolveById(long resolveId) throws Exception {
		return BOAIMonPImgDataResolveEngine.getBean(resolveId);
	}
	
	
	
	/**
	 * 根据名称取得图形展示分组配置信息
	 * 
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPImgDataResolveValue[] getImgDataResolveByName(String name, Integer startNum, Integer endNum) throws Exception {
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(name)) {
			condition.append(IBOAIMonPImgDataResolveValue.S_Name).append(" like :groupName");
			parameter.put("groupName", name + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return query(condition.toString(), parameter, startNum, endNum);
	}
	
	public int getImgDataResolveCount(String name) throws Exception {
		StringBuilder condition=new StringBuilder("");
		Map parameter=new HashMap();
		if (StringUtils.isNotBlank(name)) {
			condition.append(IBOAIMonPImgDataResolveValue.S_Name).append(" like :groupName");
			parameter.put("groupName",name + "%");
		}
		if (StringUtils.isNotBlank(condition.toString())){
			condition.append(" and ");
		}
		condition.append(" state = 'U' ");
		return BOAIMonPImgDataResolveEngine.getBeansCount(condition.toString(), parameter);
	}
	
	/**
	 * 批量保存或修改
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPImgDataResolveValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setResolveId(BOAIMonPImgDataResolveEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("");
		}
		BOAIMonPImgDataResolveEngine.saveBatch(values);
	}
	
	/**
	 * 保存或修改
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonPImgDataResolveValue value) throws Exception {
		if (value.isNew()) {
			value.setResolveId(BOAIMonPImgDataResolveEngine.getNewId().longValue());
		}
		BOAIMonPImgDataResolveEngine.save(value);
	}
	
	/**
	 * 删除
	 * 
	 * @param groupId
	 * @throws Exception
	 */
	public void delete(long groupId) throws Exception {
		IBOAIMonPImgDataResolveValue value = BOAIMonPImgDataResolveEngine.getBean(groupId);
		if(null != value) {
			value.setState("E");
			BOAIMonPImgDataResolveEngine.save(value);
		}
	}
	
}
