package com.asiainfo.monitor.busi.service.interfaces;



import java.rmi.RemoteException;
import java.util.List;

import com.ai.appframe2.bo.DataContainer;
import com.asiainfo.monitor.busi.cache.impl.Panel;
import com.asiainfo.monitor.busi.config.PanelContainer;
import com.asiainfo.monitor.busi.config.PanelShape;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAttentionPanelValue;
import com.asiainfo.monitor.tools.model.WebPanelTaskContext;


public interface IAIMonAttentionPanelSV {


	/**
	 * 根据主键取得任务信息
	 * 
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
//	public PanelShape getAttentionPanelById(long id) throws RemoteException,Exception;
	
	/**
	 * 根据层以及执行方式读取面板信息
	 * @param layer
	 * @param execMethod
	 * @return
	 * @throws Exception
	 */
//	public PanelContainer getAttentionPanelByIds(Object[] panelIds) throws RemoteException,Exception;
	
	/**
	 * 根据面板标识，从缓存中读取任务信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WebPanelTaskContext getTaskContextById(String id) throws RemoteException,Exception;
	
	/**
	 * 根据条件查询任务信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List getAttentionPanelByName(String name, boolean matchWhole) throws RemoteException,Exception;
	/**
	 * 根据条件查询任务信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List getAttentionPanelByNameNocache(String name, boolean matchWhole) throws RemoteException,Exception;
	
	
	/**
	 * 返回所有主面板信息值对象
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAttentionPanelValue[] getAllAttentionPanelBean() throws RemoteException,Exception;


	/**
	 * 根据层以及执行方式读取面板信息
	 * @param layer
	 * @param execMethod
	 * @return
	 * @throws Exception
	 */
//	public PanelContainer getAttentionPanelByEntityIdAndExecMethod(String entityCode,String execMethod) throws RemoteException,Exception;
	
	
	/**
	 * 根据标识读取面板信息并简单封装
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Panel getPanelByIdFromDb(String id) throws RemoteException,Exception;
	/**
	 * 将面板值对象简单封装
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Panel wrapperPanelByBean(IBOAIMonAttentionPanelValue value) throws RemoteException,Exception;
	
	/**
	 * 批量保存或修改任务信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAttentionPanelValue[] values) throws RemoteException,Exception;
	
	/**
	 * 保存或修改任务信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAttentionPanelValue value) throws RemoteException,Exception;
	
	/**
	 * 删除任务信息
	 * 
	 * @param infoId
	 * @throws Exception
	 */
	public void delete(long infoId) throws RemoteException,Exception;


	/**
	 * 根据对象类型与类型标识获取面板
	 * @param type
	 * @param typeId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonAttentionPanelValue[] getPanelByType(String type, long typeId) throws RemoteException,Exception;

	/**
	 * 保存面板以及参数
	 * @param panelValue
	 * @param paramDcs
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAttentionPanelValue panelValue, DataContainer[] paramDcs) throws RemoteException,Exception;
	
	/**
	 * 根据EntityId取得所有面板信息
	 * 
	 * @param entityId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
//	public PanelContainer getAllPanelByEntityId(String entityId) throws RemoteException, Exception;
}
