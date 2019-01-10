package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.bo.DataContainerFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.AIMonAttentionPanelCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonAttentionPanelCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.AIMonCustomPanelCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.Panel;
import com.asiainfo.monitor.busi.cache.interfaces.IPanel;
import com.asiainfo.monitor.busi.config.PanelContainer;
import com.asiainfo.monitor.busi.config.PanelShape;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonAttentionPanelDAO;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonPanelRelatEntityDAO;
import com.asiainfo.monitor.busi.exe.task.impl.WebPanelTaskFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonAttentionPanelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamValuesSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamValuesBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAttentionPanelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonPanelRelatEntityValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.WebPanelTaskContext;

public class AIMonAttentionPanelSVImpl implements IAIMonAttentionPanelSV {
	
	
	private static transient Log log=LogFactory.getLog(AIMonAttentionPanelSVImpl.class);
	
	private static final String COMMON_TYPE_PANEL = "COMMON";
	
	private static final String CUSTOM_TYPE_PANEL = "CUSTOM";
	
	/**
	 * 根据主键取得任务信息
	 * 
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
//	public PanelShape getAttentionPanelById(long id) throws RemoteException,Exception {
//		Panel result=null;
//		try{
//			result=(Panel)MonCacheManager.get(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._PANEL_MODEL+id);
//			if (result==null){
//				result=this.getPanelByIdFromDb(id+"");
//				if (!MonCacheManager.getCacheReadOnlySet() && result!=null){
//					MonCacheManager.put(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._PANEL_MODEL+result.getPanel_Id(),result);
//					WebPanelTaskContext taskContext=WebPanelTaskFactory.getWebPanelTaskContext(result);
//					if (taskContext!=null)
//						MonCacheManager.put(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._TASK_MODEL+result.getPanel_Id(),taskContext);
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
	 * 根据层以及执行方式读取面板信息
	 * @param layer
	 * @param execMethod
	 * @return
	 * @throws Exception
	 */
//	public PanelContainer getAttentionPanelByIds(Object[] panelIds) throws RemoteException,Exception{
//		if (panelIds==null || panelIds.length==0)
//			// 面板类型不能为空
//			throw new Exception(AIMonLocaleFactory.getResource("MOS0000301"));
//		PanelContainer result=new PanelContainer();
//		for (int i=0;i<panelIds.length;i++){
//			PanelShape item=this.getAttentionPanelById(Long.parseLong(panelIds[i]+""));
//			if (item!=null)
//				result.addShape(item);
//		}
//		return result;
//	}
	
	/**
	 * 根据面板标识，从缓存中读取任务信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WebPanelTaskContext getTaskContextById(String id) throws RemoteException,Exception{
		WebPanelTaskContext result=null;
		try{
			result=(WebPanelTaskContext)MonCacheManager.get(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._TASK_MODEL+id);
			if (result==null){
				Panel panel=this.getPanelByIdFromDb(id+"");
				if (panel!=null){
					result=WebPanelTaskFactory.getWebPanelTaskContext(panel);
				}
				if (!MonCacheManager.getCacheReadOnlySet() && result!=null){
					MonCacheManager.put(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._TASK_MODEL+result.getId(),result);
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000095", id+"")+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 返回所有主面板信息
	 * 不考虑缓存未加载情况
	 * @return
	 * @throws Exception
	 */
//	public List getAllPanel() throws RemoteException,Exception {
//		List result=null;
//		HashMap panelMap=MonCacheManager.getAll(AIMonAttentionPanelCacheImpl.class);
//		if (panelMap!=null && panelMap.size()>0){
//			result=new ArrayList();
//			Iterator it=panelMap.entrySet().iterator();
//			while (it !=null && it.hasNext()){
//				Entry item=(Entry)it.next();
//				if (item.getKey().toString().indexOf(AIMonAttentionPanelCacheImpl._PANEL_MODEL)>=0){
//					IPanel panel=(IPanel)item.getValue();
//					if ( ((Panel)panel).getEnable())
//						result.add(PanelShapeFactory.getTaskShape(panel));
//				}
//				
//			}
//		}
//		return result;
//	}
	
	/**
	 * 返回所有主面板信息值对象
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonAttentionPanelValue[] getAllAttentionPanelBean() throws RemoteException,Exception {
		IAIMonAttentionPanelDAO panelDAO=(IAIMonAttentionPanelDAO)ServiceFactory.getService(IAIMonAttentionPanelDAO.class);
		return panelDAO.getAllAttentionPanelBean();
	}
	
	
	/**
	 * 根据条件查询任务信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List getAttentionPanelByName(String name,boolean matchWhole) throws RemoteException,Exception {
		List result=null;
		HashMap parameter=MonCacheManager.getAll(AIMonAttentionPanelCacheImpl.class);
		if (parameter!=null && parameter.size()>0){
			result=new ArrayList();
			Iterator it=parameter.entrySet().iterator();
			boolean finded=false;
			while (it.hasNext()){
				Entry item=(Entry)it.next();
				finded=false;
				if (item.getKey().toString().indexOf(AIMonAttentionPanelCacheImpl._PANEL_MODEL)>=0){
					IPanel panel=(IPanel)item.getValue();
					if (matchWhole){
						if (panel.getPanel_Name().equals(name)){
							finded=true;
						}
					}else{
						if (panel.getPanel_Name().toLowerCase().indexOf(name.toLowerCase())>=0){
							finded=true;
						}
					}
					if (finded){
						result.add(panel);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据条件查询任务信息
	 * 
	 * @param cond
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List getAttentionPanelByNameNocache(String name,boolean matchWhole) throws RemoteException,Exception {
		List result=new ArrayList();
		IBOAIMonAttentionPanelValue[] panelBeans=getAllAttentionPanelBean();
		if (panelBeans!=null && panelBeans.length>0){
			for (int i=0;i<panelBeans.length;i++){
				Panel cell=new Panel();
				cell.setPanel_Id(panelBeans[i].getPanelId()+"");
				cell.setPanel_Name(panelBeans[i].getPanelName());
				cell.setPanel_Desc(panelBeans[i].getPanelDesc());
				cell.setLayer(panelBeans[i].getLayer());
				cell.setExec_Method(panelBeans[i].getExecMethod());
				cell.setObj_Id(panelBeans[i].getObjId()+"");
				cell.setObj_Type(panelBeans[i].getObjType());
				cell.setThreshold_Id(panelBeans[i].getThresholdId()+"");
				cell.setView_strategy(panelBeans[i].getViewStrategy());
				cell.setTemp_Type(panelBeans[i].getTempType());
				cell.setView_Type(panelBeans[i].getViewType());
				cell.setView_transform(panelBeans[i].getViewTransform());
				cell.setState(panelBeans[i].getState());
				cell.setRemarks(panelBeans[i].getRemarks());
				cell.setTime_Id(panelBeans[i].getTimeId()+"");
				cell.setContrail(panelBeans[i].getContrail());
				if (matchWhole){
					if (cell.getPanel_Name().equals(name)){
						result.add(cell);
					}
				}else{
					if (cell.getPanel_Name().toLowerCase().indexOf(name.toLowerCase())>=0){
						result.add(cell);
					}
				}
			}
		}
		
		return result;
	}

	
	/**
	 * 根据层以及执行方式读取面板信息
	 * @param layer
	 * @param execMethod
	 * @return
	 * @throws Exception
	 */
//	public PanelContainer getAttentionPanelByEntityIdAndExecMethod(String entityId,String execMethod) throws RemoteException,Exception{
//		if (StringUtils.isBlank(entityId) && StringUtils.isBlank(execMethod))
//			// 面板类型不能为空
//			throw new Exception(AIMonLocaleFactory.getResource("MOS0000301"));
//		PanelContainer result=null;		
//		IAIMonPanelRelatEntityDAO relatDao = (IAIMonPanelRelatEntityDAO)ServiceFactory.getService(IAIMonPanelRelatEntityDAO.class);
//		IBOAIMonPanelRelatEntityValue[] entityValues=relatDao.getRelatByEntityId(entityId);
//		if (entityValues!=null && entityValues.length>0){
//			result=new PanelContainer();
//			for (int i=0;i<entityValues.length;i++){
//				if (COMMON_TYPE_PANEL.equals(entityValues[i].getPanelType())) {
//					IPanel panel=(IPanel)MonCacheManager.get(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._PANEL_MODEL+entityValues[i].getPanelId()+"");
//					if (panel!=null ){
//						if (StringUtils.isNotBlank(execMethod)){
//							if ( panel.getExec_Method()!=null && panel.getExec_Method().equals(execMethod) )
//								result.addShape(PanelShapeFactory.getTaskShape(panel));
//						}else{
//							result.addShape(PanelShapeFactory.getTaskShape(panel));
//						}
//						
//					}
//				}
//			}
//		}
//		return result;
//	}
	
	/**
	 * 根据标识读取面板信息并简单封装
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Panel getPanelByIdFromDb(String id) throws RemoteException,Exception{
		if (StringUtils.isBlank(id))
			return null;
		IAIMonAttentionPanelDAO infoDao = (IAIMonAttentionPanelDAO)ServiceFactory.getService(IAIMonAttentionPanelDAO.class);
		IBOAIMonAttentionPanelValue value=infoDao.getAttentionPanelById(Long.valueOf(id));
		Panel result=this.wrapperPanelByBean(value);
		return result;
	}
	
	/**
	 * 将面板值对象简单封装
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Panel wrapperPanelByBean(IBOAIMonAttentionPanelValue value) throws RemoteException,Exception{
		if (value==null || StringUtils.isBlank(value.getState())  || value.getState().equals("E"))
			return null;
		Panel cell=new Panel();
		cell.setPanel_Id(value.getPanelId()+"");
		cell.setPanel_Name(value.getPanelName());
		cell.setPanel_Desc(value.getPanelDesc());
		cell.setLayer(value.getLayer());
		cell.setExec_Method(value.getExecMethod());
		cell.setObj_Id(value.getObjId()+"");
		cell.setObj_Type(value.getObjType());
		cell.setThreshold_Id(value.getThresholdId()+"");
		cell.setView_strategy(value.getViewStrategy());
		cell.setTemp_Type(value.getTempType());
		cell.setView_Type(value.getViewType());
		cell.setView_transform(value.getViewTransform());
		cell.setState(value.getState());
		cell.setRemarks(value.getRemarks());
		cell.setTime_Id(value.getTimeId()+"");
		cell.setContrail(value.getContrail());
		cell.setCacheListener(new AIMonAttentionPanelCheckListener());
		return cell;
	}
	
	/**
	 * 批量保存或修改任务信息
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAttentionPanelValue[] values) throws RemoteException,Exception {
		boolean modify=values[0].isModified();
		IAIMonAttentionPanelDAO infoDao = (IAIMonAttentionPanelDAO)ServiceFactory.getService(IAIMonAttentionPanelDAO.class);
		infoDao.saveOrUpdate(values);
		for (int i=0;i<values.length;i++){
			if (modify){
				Panel item=(Panel)MonCacheManager.get(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._PANEL_MODEL+values[i].getPanelId());
				item.setEnable(false);
			}else{
				Panel item=this.wrapperPanelByBean(values[i]);
				MonCacheManager.put(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._TASK_MODEL+values[i].getPanelId(),item);
				WebPanelTaskContext taskContext=WebPanelTaskFactory.getWebPanelTaskContext(item);
				if (taskContext!=null)
					MonCacheManager.put(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._TASK_MODEL+item.getKey(),taskContext);
			}
		}
	}
	
	/**
	 * 保存或修改任务信息
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAttentionPanelValue value ) throws RemoteException,Exception {
		boolean modify=value.isModified();
		IAIMonAttentionPanelDAO infoDao = (IAIMonAttentionPanelDAO)ServiceFactory.getService(IAIMonAttentionPanelDAO.class);
		infoDao.saveOrUpdate(value);
		if (modify){
			Panel item=(Panel)MonCacheManager.get(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._PANEL_MODEL+value.getPanelId());
			if (item!=null)
				item.setEnable(false);
		}else{
			Panel item=this.wrapperPanelByBean(value);
			MonCacheManager.put(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._TASK_MODEL+value.getPanelId(),item);
			WebPanelTaskContext taskContext=WebPanelTaskFactory.getWebPanelTaskContext(item);
			if (taskContext!=null)
				MonCacheManager.put(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._TASK_MODEL+item.getKey(),taskContext);
		}
	}
	
	/**
	 * 保存面板以及参数
	 * @param panelValue
	 * @param paramDcs
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonAttentionPanelValue panelValue, DataContainer[] paramDcs) throws RemoteException,Exception{
		this.saveOrUpdate(panelValue);
		// 删除参数
		IAIMonParamValuesSV valSV = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
		valSV.deleteParamValues(20, panelValue.getPanelId());
		// 保存参数
		if(paramDcs == null || paramDcs.length == 0)
			return;
		IBOAIMonParamValuesValue[] values = new IBOAIMonParamValuesValue[paramDcs.length];
		for(int i = 0; i< paramDcs.length; i++){
			
			values[i] = new BOAIMonParamValuesBean();
			if (StringUtils.isBlank(paramDcs[i].getAsString(IBOAIMonParamValuesValue.S_VId))) {
				paramDcs[i].setStsToNew();
			} else {
				long value = paramDcs[i].getAsLong(IBOAIMonParamValuesValue.S_VId);
				paramDcs[i].clearProperty(IBOAIMonParamValuesValue.S_VId);
				paramDcs[i].initPropertyOld(IBOAIMonParamValuesValue.S_VId, value);

			}
			DataContainerFactory.copy(paramDcs[i], values[i]);
			values[i].setRegisteId(panelValue.getPanelId());
			values[i].setRegisteType(20);
		}
	
		valSV.saveOrUpdate(values);
	}
	
	/**
	 * 删除任务信息
	 * 
	 * @param infoId
	 * @throws Exception
	 */
	public void delete(long panelId) throws RemoteException,Exception {		
		IAIMonAttentionPanelDAO infoDao = (IAIMonAttentionPanelDAO)ServiceFactory.getService(IAIMonAttentionPanelDAO.class);
		infoDao.delete(panelId);
		Panel item=(Panel)MonCacheManager.get(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._PANEL_MODEL+panelId);
		if (item!=null)
			item.setEnable(false);
		// delete ai_mon_param_values
		IAIMonParamValuesSV valSV = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
		valSV.deleteParamValues(20, panelId);
	}
	
	/**
	 * 根据对象类型与类型标识获取面板
	 * @param type
	 * @param typeId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public IBOAIMonAttentionPanelValue[] getPanelByType(String type, long typeId) throws RemoteException,Exception{
		IAIMonAttentionPanelDAO infoDao = (IAIMonAttentionPanelDAO)ServiceFactory.getService(IAIMonAttentionPanelDAO.class);
		return infoDao.getPanelByType(type,typeId);
	}
	
	/**
	 * 根据EntityId取得所有面板信息
	 * 
	 * @param entityId
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
//	public PanelContainer getAllPanelByEntityId(String entityId) throws RemoteException, Exception {
//		PanelContainer result=null;		
//		IAIMonPanelRelatEntityDAO relatDao = (IAIMonPanelRelatEntityDAO)ServiceFactory.getService(IAIMonPanelRelatEntityDAO.class);
//		IBOAIMonPanelRelatEntityValue[] entityValues=relatDao.getRelatByEntityId(entityId);
//		if (entityValues!=null && entityValues.length>0){
//			result=new PanelContainer();
//			for (int i=0;i<entityValues.length;i++){
//				IPanel panel = null;
//				if (COMMON_TYPE_PANEL.equals(entityValues[i].getPanelType())) {
//					panel=(IPanel)MonCacheManager.get(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._PANEL_MODEL+entityValues[i].getPanelId()+"");
//				} else if (CUSTOM_TYPE_PANEL.equals(entityValues[i].getPanelType())) {
//					panel=(IPanel)MonCacheManager.get(AIMonCustomPanelCacheImpl.class,AIMonCustomPanelCacheImpl._CPANEL_MODEL+entityValues[i].getPanelId()+"");
//				} else {
//					panel=null;
//				}
//				if (panel!=null ){
//					result.addShape(PanelShapeFactory.getTaskShape(panel));
//				}
//			}
//		}
//		return result;
//	}

}
