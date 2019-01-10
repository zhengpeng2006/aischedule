package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonAttentionPanelDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonAttentionPanelEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAttentionPanelValue;

public class AIMonAttentionPanelDAOImpl implements IAIMonAttentionPanelDAO {

	/**
	 * 根据条件查询任务信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAttentionPanelValue[] queryAttentionPanel(String condition, Map parameter) throws Exception {
		return BOAIMonAttentionPanelEngine.getBeans(condition, parameter);
	}
	
	/**
	 * 返回所有面板信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAttentionPanelValue[] getAllAttentionPanelBean() throws Exception {
		return this.queryAttentionPanel(IBOAIMonAttentionPanelValue.S_State+"='U' ORDER BY "+IBOAIMonAttentionPanelValue.S_PanelId,null);
	}
	
	
	/**
	 * 根据主键取得任务信息
	 * 
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAttentionPanelValue getAttentionPanelById(long infoId) throws Exception {
		return BOAIMonAttentionPanelEngine.getBean(infoId);	
	}
	
	/**
	 * 批量保存或修改任务信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAttentionPanelValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setPanelId(BOAIMonAttentionPanelEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception("");
		}
		BOAIMonAttentionPanelEngine.saveBatch(values);
	}
	
	/**
	 * 保存或修改任务信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAttentionPanelValue value ) throws Exception {
		if (value.isNew()) {
			value.setPanelId(BOAIMonAttentionPanelEngine.getNewId().longValue());
		}
		BOAIMonAttentionPanelEngine.save(value);
	}
	
	/**
	 * 删除任务信息
	 * 
	 * @param infoId
	 * @throws Exception
	 */
	public void delete(long infoId) throws Exception {
		IBOAIMonAttentionPanelValue value = BOAIMonAttentionPanelEngine.getBean(infoId);
		if (null != value) {
			value.delete();
			BOAIMonAttentionPanelEngine.save(value);
		}
	}

	/**
	 * 删除面板内容项
	 * @param value
	 * @throws Exception
	 */
	public void delete(IBOAIMonAttentionPanelValue value) throws Exception{
		if (null != value) {
			value.delete();
			BOAIMonAttentionPanelEngine.save(value);
		}
	}
	

	/**
	 * 根据对象类型与类型标识获取面板
	 * @param type
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAttentionPanelValue[] getPanelByType(String type, long typeId) throws Exception{
		StringBuilder sb =new StringBuilder("");
		sb.append(IBOAIMonAttentionPanelValue.S_State).append("= :pState").append(" and ");
		sb.append(IBOAIMonAttentionPanelValue.S_ObjType).append("= :mType").append(" and ");
		sb.append(IBOAIMonAttentionPanelValue.S_ObjId).append("= :mObjId");
		HashMap params = new HashMap();
		params.put("pState", "U");
		params.put("mType", type);
		params.put("mObjId", typeId);
		return this.queryAttentionPanel( sb.toString(),params);
	}
}
