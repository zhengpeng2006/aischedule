package com.asiainfo.monitor.busi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCustomizePanelRelateDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonCustomizePanelRelateEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelRelateValue;

public class AIMonCustomizePanelRelateDAOImpl implements IAIMonCustomizePanelRelateDAO {

	public IBOAIMonCustomizePanelRelateValue[] queryCustomizePanelRelate(String cond, Map param) throws Exception {
		return BOAIMonCustomizePanelRelateEngine.getBeans(cond, param);
	}
	
	/**
	 * 根据自定义面板ID取得公共面板信息
	 * 
	 * @param cpanelId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelRelateValue[] getCustomizePanelByCId(long cpanelId) throws Exception {
		StringBuilder condition = new StringBuilder("");
		HashMap param = new HashMap();
		
		condition.append(IBOAIMonCustomizePanelRelateValue.S_State).append(" !='E' ");
			condition.append(" and ").append(IBOAIMonCustomizePanelRelateValue.S_CpanelId).append(" = :cpanelId");
			param.put("cpanelId", cpanelId);
		
	    IBOAIMonCustomizePanelRelateValue[] panelRelate =queryCustomizePanelRelate(condition.toString(),param);
		return panelRelate;
	}
	
	
	/**
	 * 根据自定义面板和关联面板查找关联记录信息
	 * @param cpanelId
	 * @param panelId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelRelateValue getCustomizePanelByCIdAndPId(long cpanelId,long panelId) throws Exception{
		StringBuilder condition = new StringBuilder();
		condition.append(IBOAIMonCustomizePanelRelateValue.S_CpanelId).append(" = :cpanelId");
		condition.append(" and ");
		condition.append(IBOAIMonCustomizePanelRelateValue.S_PanelId).append(" = :panelId ");
		Map param = new HashMap();
		param.put("cpanelId", cpanelId);
		param.put("panelId",panelId);
		IBOAIMonCustomizePanelRelateValue[] beans= queryCustomizePanelRelate(condition.toString(),param);
		if (beans==null && beans.length>1){
			throw new Exception("自定义面板["+cpanelId+"]关联面板["+panelId+"]多于一条记录");
		}
		return beans[0];
	}
	
	/**
	 * 返回所有自定义面板关联信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelRelateValue[] getAllCustomPanelRelateBean() throws Exception {
		return this.queryCustomizePanelRelate(IBOAIMonCustomizePanelRelateValue.S_State+"='U' ORDER BY "+IBOAIMonCustomizePanelRelateValue.S_CpanelId,null);
	}
	
	/**
	 * 保存或修改自定义面板关系
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCustomizePanelRelateValue value) throws Exception {
		if (value.isNew()) {
			value.setRelateId(BOAIMonCustomizePanelRelateEngine.getNewId().longValue());
		}
		BOAIMonCustomizePanelRelateEngine.save(value);
	}
	
	/**
	 * 批量保存或修改自定义面板关系
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCustomizePanelRelateValue[] values) throws Exception {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (values[i].isNew()) {
					values[i].setRelateId(BOAIMonCustomizePanelRelateEngine.getNewId().longValue());
				}
			}
		} else {
			throw new Exception(" save or update CustomizePanelRelate Exception");
		}
		BOAIMonCustomizePanelRelateEngine.saveBatch(values);
	}
	
	/**
	 * 删除自定义面板关系
	 * 
	 * @param relateId
	 * @throws Exception
	 */
	public void delete(long relateId) throws Exception {
		IBOAIMonCustomizePanelRelateValue value = BOAIMonCustomizePanelRelateEngine.getBean(relateId);
		if (value != null) {
			value.setState("E");
			BOAIMonCustomizePanelRelateEngine.save(value);
		}
	}
}
