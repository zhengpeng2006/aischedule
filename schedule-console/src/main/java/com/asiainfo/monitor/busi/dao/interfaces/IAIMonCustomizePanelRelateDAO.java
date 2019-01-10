package com.asiainfo.monitor.busi.dao.interfaces;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelRelateValue;

public interface IAIMonCustomizePanelRelateDAO {

	/**
	 * 根据自定义面板ID取得公共面板信息
	 * 
	 * @param cpanelId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelRelateValue[] getCustomizePanelByCId(long cpanelId) throws Exception;
	
	/**
	 * 根据自定义面板和关联面板查找关联记录信息
	 * @param cpanelId
	 * @param panelId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelRelateValue getCustomizePanelByCIdAndPId(long cpanelId, long panelId) throws Exception;
	
	/**
	 * 返回所有自定义面板关联信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelRelateValue[] getAllCustomPanelRelateBean() throws Exception;
	
	/**
	 * 保存或修改自定义面板关系
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCustomizePanelRelateValue value) throws Exception;
	
	/**
	 * 批量保存或修改自定义面板关系
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCustomizePanelRelateValue[] values) throws Exception;
	
	/**
	 * 删除自定义面板关系
	 * 
	 * @param relateId
	 * @throws Exception
	 */
	public void delete(long relateId) throws Exception;
	
}
