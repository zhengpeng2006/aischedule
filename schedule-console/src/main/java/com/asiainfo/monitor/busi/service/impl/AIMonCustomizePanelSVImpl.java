package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.AIMonCustomPanelCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonCustomPanelCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.CustomPanel;
import com.asiainfo.monitor.busi.cache.interfaces.IPanel;
import com.asiainfo.monitor.busi.config.PanelContainer;
import com.asiainfo.monitor.busi.config.PanelShape;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCustomizePanelDAO;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonPanelRelatEntityDAO;
import com.asiainfo.monitor.busi.exe.task.impl.CustomPanelTaskFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCustomizePanelRelateSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCustomizePanelSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelRelateValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonPanelRelatEntityValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.CustomPanelTaskContext;

public class AIMonCustomizePanelSVImpl implements IAIMonCustomizePanelSV {
	
	
	private static final String CUSTOM_TYPE_PANEL = "CUSTOM";

	/**
	 * 根据面板ID取得面板信息
	 * 
	 * @param panelId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue getCustomizePanelById(long panelId) throws RemoteException,Exception {
		IAIMonCustomizePanelDAO dao = (IAIMonCustomizePanelDAO) ServiceFactory.getService(IAIMonCustomizePanelDAO.class);
		return dao.getCustomizePanelById(panelId);
	}
	
	/**
	 * 根据面板名称取得面板信息
	 * 
	 * @param panelId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue[] getCustomizePanelByName(String cpanelName) throws RemoteException,Exception {
		IAIMonCustomizePanelDAO dao = (IAIMonCustomizePanelDAO) ServiceFactory.getService(IAIMonCustomizePanelDAO.class);
		return dao.getCustomizePanelByName(cpanelName);
	}
	
	

	/**
	 * 查询出所有自定义面板
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue[] getAllCustomPanelBean() throws RemoteException,Exception{
		IAIMonCustomizePanelDAO dao = (IAIMonCustomizePanelDAO) ServiceFactory.getService(IAIMonCustomizePanelDAO.class);
		return dao.getAllCustomPanelBean();
	}
	
	/**
	 * 根据自定义面板获取面板图形对象
	 * @param id
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
//	public PanelShape getCustomPanelById(long id) throws RemoteException,Exception {
//		CustomPanel result=null;
//		try{
//			result=(CustomPanel)MonCacheManager.get(AIMonCustomPanelCacheImpl.class,AIMonCustomPanelCacheImpl._CPANEL_MODEL+id);
//			if (result==null){
//				result=this.getCustomPanelByIdFromDb(id+"");
//				if (!MonCacheManager.getCacheReadOnlySet() && result!=null){
//					MonCacheManager.put(AIMonCustomPanelCacheImpl.class,AIMonCustomPanelCacheImpl._CPANEL_MODEL+result.getPanel_Id(),result);
//					CustomPanelTaskContext taskContext=CustomPanelTaskFactory.getCustomPanelTaskContext(result);
//					if (taskContext!=null)
//						MonCacheManager.put(AIMonCustomPanelCacheImpl.class,AIMonCustomPanelCacheImpl._CTASK_MODEL+result.getPanel_Id(),taskContext);
//				}
//			}
//		}catch(Exception e){
//			// "根据标识["+id+"]读取面板信息异常:"
//			throw new Exception(AIMonLocaleFactory.getResource("MOS0000094", id+"")+e.getMessage());
//		}
//		if (result!=null)
//			return PanelShapeFactory.getTaskShape(result);
//		else
//			return null;
//	}
	
	/**
	 * 根据自定义面板标识，从缓存中读取任务信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CustomPanelTaskContext getTaskContextById(String id) throws RemoteException,Exception{
		CustomPanelTaskContext result=null;
		try{
			result=(CustomPanelTaskContext)MonCacheManager.get(AIMonCustomPanelCacheImpl.class,AIMonCustomPanelCacheImpl._CTASK_MODEL+id);
			if (result==null){
				CustomPanel panel=this.getCustomPanelByIdFromDb(id+"");
				if (panel!=null){
					result=CustomPanelTaskFactory.getCustomPanelTaskContext(panel);
				}
				if (!MonCacheManager.getCacheReadOnlySet() && result!=null){
					MonCacheManager.put(AIMonCustomPanelCacheImpl.class,AIMonCustomPanelCacheImpl._CTASK_MODEL+result.getId(),result);
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000095", id+"")+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据标识读取自定义面板信息并简单封装
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CustomPanel getCustomPanelByIdFromDb(String id) throws RemoteException,Exception{
		if (StringUtils.isBlank(id))
			return null;
		IAIMonCustomizePanelDAO infoDao = (IAIMonCustomizePanelDAO)ServiceFactory.getService(IAIMonCustomizePanelDAO.class);
		IBOAIMonCustomizePanelValue value=infoDao.getCustomizePanelById(Long.valueOf(id));
		CustomPanel result=this.wrapperPanelByBean(value);
		return result;
	}
	
	
	/**
	 * 将自定义面板值对象简单封装
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public CustomPanel wrapperPanelByBean(IBOAIMonCustomizePanelValue value) throws RemoteException,Exception{
		if (value==null || StringUtils.isBlank(value.getState())  || value.getState().equals("E"))
			return null;
		CustomPanel cell=new CustomPanel();
		cell.setPanel_Id(value.getCpanelId()+"");
		cell.setPanel_Name(value.getCpanelName());
		cell.setPanel_Desc(value.getCpanelDesc());
		cell.setLayer(value.getLayer());
		cell.setCust_Type(value.getCustType());
		cell.setView_Type(value.getCustType());
		cell.setTemp_Type(value.getTempType());
		cell.setState(value.getState());
		cell.setRemarks(value.getRemarks());
		IAIMonCustomizePanelRelateSV relateSV=(IAIMonCustomizePanelRelateSV)ServiceFactory.getService(IAIMonCustomizePanelRelateSV.class);
		IBOAIMonCustomizePanelRelateValue[] relateBeans=relateSV.getCustomizePanelByCId(value.getCpanelId());
		if (relateBeans!=null){
			for (int i=0;i<relateBeans.length;i++){
				cell.addChildPanels(relateBeans[i].getPanelId()+"");
			}
		}
		/*
		cell.setExec_Method(null);
		cell.setObj_Id(null);
		cell.setObj_Type(null);
		cell.setThreshold_Id(null);
		cell.setView_strategy(null);		
		cell.setView_Type(null);
		cell.setView_transform(null);		
		cell.setTime_Id(null);
		cell.setContrail(null);
		*/
		cell.setCacheListener(new AIMonCustomPanelCheckListener());
		return cell;
	}
	/**
	 * 保存或修改面板信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCustomizePanelValue value) throws RemoteException,Exception {
		IAIMonCustomizePanelDAO dao = (IAIMonCustomizePanelDAO) ServiceFactory.getService(IAIMonCustomizePanelDAO.class);
		dao.saveOrUpdate(value);
	}

	/**
	 * 批量保存或修改面板信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCustomizePanelValue[] values) throws RemoteException,Exception {
		IAIMonCustomizePanelDAO dao = (IAIMonCustomizePanelDAO) ServiceFactory.getService(IAIMonCustomizePanelDAO.class);
		dao.saveOrUpdate(values);
	}
	
	/**
	 * 删除面案信息
	 * 
	 * @param panelId
	 * @throws Exception
	 */
	public void delete(long panelId) throws RemoteException,Exception {
		IAIMonCustomizePanelDAO dao = (IAIMonCustomizePanelDAO) ServiceFactory.getService(IAIMonCustomizePanelDAO.class);
		dao.delete(panelId);
	}
	
	/**
	 * 根据EntityId获取自定义面板
	 * 
	 * @param entityId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
//	public PanelContainer getCustomPanelByEntityId(String entityId) throws RemoteException, Exception {
//		PanelContainer result=null;		
//		IAIMonPanelRelatEntityDAO relatDao = (IAIMonPanelRelatEntityDAO)ServiceFactory.getService(IAIMonPanelRelatEntityDAO.class);
//		IBOAIMonPanelRelatEntityValue[] entityValues=relatDao.getRelatByEntityId(entityId);
//		if (entityValues!=null && entityValues.length>0){
//			result=new PanelContainer();
//			for (int i=0;i<entityValues.length;i++){
//				IPanel panel = null;
//				if (CUSTOM_TYPE_PANEL.equals(entityValues[i].getPanelType())) {
//					panel=(IPanel)MonCacheManager.get(AIMonCustomPanelCacheImpl.class,AIMonCustomPanelCacheImpl._CPANEL_MODEL+entityValues[i].getPanelId()+"");
//				}
//				if (panel!=null ){
//					result.addShape(PanelShapeFactory.getTaskShape(panel));
//				}
//			}
//		}
//		
//		return result;
//	}
}
