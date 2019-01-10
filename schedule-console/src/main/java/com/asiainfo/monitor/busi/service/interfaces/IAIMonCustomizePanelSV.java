package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;

import com.asiainfo.monitor.busi.cache.impl.CustomPanel;
import com.asiainfo.monitor.busi.config.PanelContainer;
import com.asiainfo.monitor.busi.config.PanelShape;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelValue;
import com.asiainfo.monitor.tools.model.CustomPanelTaskContext;

public interface IAIMonCustomizePanelSV {

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
	 * 查询出所有自定义面板
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue[] getAllCustomPanelBean() throws Exception;
	
	/**
	 * 根据自定义面板获取面板图形对象
	 * @param id
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
//	public PanelShape getCustomPanelById(long id) throws RemoteException,Exception;
	
	/**
	 * 根据自定义面板标识，从缓存中读取任务信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CustomPanelTaskContext getTaskContextById(String id) throws RemoteException,Exception;
	
	/**
	 * 根据标识读取自定义面板信息并简单封装
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CustomPanel getCustomPanelByIdFromDb(String id) throws RemoteException,Exception;
	
	/**
	 * 将自定义面板值对象简单封装
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public CustomPanel wrapperPanelByBean(IBOAIMonCustomizePanelValue value) throws RemoteException,Exception;
	
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
	
	/**
	 * 根据EntityId获取自定义面板
	 * 
	 * @param entityId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
//	public PanelContainer getCustomPanelByEntityId(String entityId) throws RemoteException, Exception;
	
}
