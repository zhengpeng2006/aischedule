package com.asiainfo.monitor.busi.service.interfaces;

import java.rmi.RemoteException;
import java.util.Map;

import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonPanelRelatEntityValue;


public interface IAIMonPanelRelatEntitySV {

	/**
	 * 根据实体ID获取关联的所有面板
	 * 
	 * @param entityId
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public IBOAIMonPanelRelatEntityValue[] getRelatByEntityId(String entityId) throws Exception, RemoteException;

	/**
	 * 保存实体与面板的关系数据
	 * @param relats
	 * @throws Exception
	 * @throws RemoteException
	 */
	public void saveEntityPanelRelat(IBOAIMonPanelRelatEntityValue[] relats) throws Exception, RemoteException;
	
	/**
	 * 获取所有配置的事体与面板关联配置
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public Map getAllRelat() throws Exception,RemoteException;

}
