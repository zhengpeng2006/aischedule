package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCustomizePanelDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonCustomizePanelEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelValue;

public class AIMonCustomizePanelDAOImpl implements IAIMonCustomizePanelDAO {

	/**
	 * 根据条件查询自定义面板
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue[] queryCustomizePanel(String cond, Map param) throws Exception {
		return BOAIMonCustomizePanelEngine.getBeans(cond, param);
	}
	
	/**
	 * 根据面板ID取得面板信息
	 * 
	 * @param panelId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue getCustomizePanelById(long panelId) throws Exception {
		return BOAIMonCustomizePanelEngine.getBean(panelId);
	}
	
	/**
	 * 根据面板名称取得面板信息
	 * 
	 * @param panelId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue[] getCustomizePanelByName(String cpanelName) throws Exception {
		StringBuilder condition = new StringBuilder("");
		HashMap paramMap = new HashMap();
		
		condition.append(IBOAIMonCustomizePanelValue.S_State).append(" !='E' ");
		if(StringUtils.isNotBlank(cpanelName)){
			condition.append(" and ").append(IBOAIMonCustomizePanelValue.S_CpanelName).append(" like :cpanelName ");
			paramMap.put("cpanelName", cpanelName + "%");
		}
		return BOAIMonCustomizePanelEngine.getBeans(condition.toString(),paramMap);
	}
	
	/**
	 * 返回所有自面板信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue[] getAllCustomPanelBean() throws Exception {
		return this.queryCustomizePanel(IBOAIMonCustomizePanelValue.S_State+"='U' ORDER BY "+IBOAIMonCustomizePanelValue.S_CpanelId,null);
	}
	
	/**
	 * 保存或修改面板信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCustomizePanelValue value) throws Exception {
		if (value.isNew()) {
			value.setCpanelId(BOAIMonCustomizePanelEngine.getNewId().longValue());
		}
		BOAIMonCustomizePanelEngine.save(value);
	}

	/**
	 * 批量保存或修改面板信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCustomizePanelValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setCpanelId(BOAIMonCustomizePanelEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception(" save or update CustomizePanel Exception");
		}
		
		BOAIMonCustomizePanelEngine.saveBatch(values);
	}
	
	/**
	 * 删除面板信息
	 * 
	 * @param panelId
	 * @throws Exception
	 */
	public void delete(long panelId) throws Exception {
		IBOAIMonCustomizePanelValue value = BOAIMonCustomizePanelEngine.getBean(panelId);
		if (value != null) {
			value.setState("E");
			BOAIMonCustomizePanelEngine.save(value);
		}
	}
}
