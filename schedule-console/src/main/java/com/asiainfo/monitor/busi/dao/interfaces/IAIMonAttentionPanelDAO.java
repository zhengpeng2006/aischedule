package com.asiainfo.monitor.busi.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAttentionPanelValue;


public interface IAIMonAttentionPanelDAO {

	/**
	 * 根据条件查询任务信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAttentionPanelValue[] queryAttentionPanel(String cond, Map param) throws Exception;
	
	/**
	 * 返回所有面板信息
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAttentionPanelValue[] getAllAttentionPanelBean() throws Exception;
	
	/**
	 * 根据主键取得任务信息
	 * 
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAttentionPanelValue getAttentionPanelById(long infoId) throws Exception;
	
	/**
	 * 批量保存或修改任务信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAttentionPanelValue[] values) throws Exception;
	
	/**
	 * 保存或修改任务信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAttentionPanelValue value) throws Exception;
	
	/**
	 * 删除任务信息
	 * 
	 * @param infoId
	 * @throws Exception
	 */
	public void delete(long panelId) throws Exception;
	
	/**
	 * 删除面板内容项
	 * @param value
	 * @throws Exception
	 */
	public void delete(IBOAIMonAttentionPanelValue value) throws Exception;

	/**
	 * 根据对象类型与类型标识获取面板
	 * @param type
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAttentionPanelValue[] getPanelByType(String type, long typeId) throws Exception;
}
