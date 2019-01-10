package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelValue;

public interface IAIMonCustomizePanelDAO {

	/**
	 * 根据条件查询自定义面板
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue[] queryCustomizePanel(String cond, Map param) throws Exception;
	
	/**
	 * 根据面板ID取得面板信息
	 * 
	 * @param panelId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue getCustomizePanelById(long panelId) throws Exception;
	
	/**
	 * 根据面板名称取得面板信息
	 * 
	 * @param panelId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue[] getCustomizePanelByName(String cpanelName) throws Exception;
	
	/**
	 * 返回所有自面板信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue[] getAllCustomPanelBean() throws Exception;
	
	/**
	 * 保存或修改面板信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCustomizePanelValue value) throws Exception;

	/**
	 * 批量保存或修改面板信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCustomizePanelValue[] values) throws Exception;
	
	/**
	 * 删除面案信息
	 * 
	 * @param panelId
	 * @throws Exception
	 */
	public void delete(long panelId) throws Exception;
	
}
