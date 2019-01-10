package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.cache.impl.Panel;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelRelateValue;

public interface IAIMonCustomizePanelRelateSV {

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
	public IBOAIMonCustomizePanelRelateValue getCustomizePanelByCIdAndPId(long cpanelId, long panelId) throws RemoteException,Exception;
	
	/**
	 * 返回所有自定义面板的关联信息值对象
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelRelateValue[] getAllCustomPanelRelateBean() throws RemoteException,Exception;
	
	/**
	 * 根据自定义面板和公共面板读取关系信息记录
	 * 
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	public Panel getCustomPanelRelateById(String cpanelId, String panelId) throws RemoteException,Exception;
	
	/**
	 * 根据标识读取面板信息并简单封装
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Panel getPanelRelateByCpIdAndPIdFromDb(String cpanelId, String panelId) throws RemoteException,Exception;
	
	/**
	 * 将面板值对象简单封装
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Panel wrapperRelatePanelByBean(IBOAIMonCustomizePanelRelateValue value) throws RemoteException,Exception;
	
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
