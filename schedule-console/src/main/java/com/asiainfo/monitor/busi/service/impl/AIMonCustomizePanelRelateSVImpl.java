package com.asiainfo.monitor.busi.service.impl;

import java.rmi.RemoteException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.AIMonCustomPanelRelateCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonCustomPanelRelateCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.ConsanPanel;
import com.asiainfo.monitor.busi.cache.impl.Panel;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonCustomizePanelRelateDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonAttentionPanelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCustomizePanelRelateSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelRelateValue;
import com.asiainfo.monitor.tools.util.TypeConst;

public class AIMonCustomizePanelRelateSVImpl implements IAIMonCustomizePanelRelateSV{

	/**
	 * 根据自定义面板ID取得公共面板信息
	 * 
	 * @param cpanelId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelRelateValue[] getCustomizePanelByCId(long cpanelId) throws RemoteException,Exception {
		IAIMonCustomizePanelRelateDAO dao = (IAIMonCustomizePanelRelateDAO)ServiceFactory.getService(IAIMonCustomizePanelRelateDAO.class);
		return dao.getCustomizePanelByCId(cpanelId);
	}
	
	/**
	 * 根据自定义面板和关联面板查找关联记录信息
	 * @param cpanelId
	 * @param panelId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelRelateValue getCustomizePanelByCIdAndPId(long cpanelId,long panelId) throws RemoteException,Exception{
		IAIMonCustomizePanelRelateDAO dao = (IAIMonCustomizePanelRelateDAO)ServiceFactory.getService(IAIMonCustomizePanelRelateDAO.class);
		return dao.getCustomizePanelByCIdAndPId(cpanelId, panelId);
	}
	
	/**
	 * 返回所有自定义面板的关联信息值对象
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelRelateValue[] getAllCustomPanelRelateBean() throws RemoteException,Exception {
		IAIMonCustomizePanelRelateDAO panelDAO=(IAIMonCustomizePanelRelateDAO)ServiceFactory.getService(IAIMonCustomizePanelRelateDAO.class);
		return panelDAO.getAllCustomPanelRelateBean();
	}
	
	/**
	 * 根据自定义面板和公共面板读取关系信息记录
	 * 
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	public Panel getCustomPanelRelateById(String cpanelId,String panelId) throws RemoteException,Exception {
		Panel result=null;
		try{
			result=(Panel)MonCacheManager.get(AIMonCustomPanelRelateCacheImpl.class,AIMonCustomPanelRelateCacheImpl._CPANEL_AND_PANEL_MODEL+cpanelId+TypeConst._INTERPRET_CHAR+panelId);
			if (result==null){
				result=this.getPanelRelateByCpIdAndPIdFromDb(cpanelId,panelId);
				if (!MonCacheManager.getCacheReadOnlySet() && result!=null){
					MonCacheManager.put(AIMonCustomPanelRelateCacheImpl.class,AIMonCustomPanelRelateCacheImpl._CPANEL_AND_PANEL_MODEL+cpanelId+TypeConst._INTERPRET_CHAR+panelId,result);
				}
			}
		}catch(Exception e){
			throw new Exception("读取自定义面板["+cpanelId+"]关联公共面板["+panelId+"]信息异常"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据标识读取面板信息并简单封装
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Panel getPanelRelateByCpIdAndPIdFromDb(String cpanelId,String panelId) throws RemoteException,Exception{
		if (StringUtils.isBlank(cpanelId) || StringUtils.isBlank(panelId))
			return null;
		IBOAIMonCustomizePanelRelateValue value=getCustomizePanelByCIdAndPId(Long.valueOf(cpanelId),Long.parseLong(panelId));
		Panel result=this.wrapperRelatePanelByBean(value);
		return result;
	}
	
	
	/**
	 * 将面板值对象简单封装
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Panel wrapperRelatePanelByBean(IBOAIMonCustomizePanelRelateValue value) throws RemoteException,Exception{
		if (value==null || StringUtils.isBlank(value.getState())  || value.getState().equals("E"))
			return null;
		IAIMonAttentionPanelSV panelSV=(IAIMonAttentionPanelSV)ServiceFactory.getService(IAIMonAttentionPanelSV.class);		
		Panel cell=panelSV.getPanelByIdFromDb(value.getPanelId()+"");
		ConsanPanel result=new ConsanPanel();
		BeanUtils.copyProperties(result,cell);
		
		result.setC_Panel_Id(value.getCpanelId()+"");
		if (!StringUtils.isBlank(value.getRunType()))
			result.setExec_Method(value.getRunType());
		if (value.getThresholdId()!=0)
			result.setThreshold_Id(value.getThresholdId()+"");
		if (value.getTimeId()!=0)
			result.setTime_Id(value.getTimeId()+"");
		if (StringUtils.isNotBlank(value.getViewType()))
			result.setView_Type(value.getViewType());
		if (StringUtils.isNotBlank(value.getViewStrategy()))
			result.setView_strategy(value.getViewStrategy());
		if (StringUtils.isNotBlank(value.getViewTransform()))
			result.setView_transform(value.getViewTransform());
		result.setCacheListener(new AIMonCustomPanelRelateCheckListener());
		return result;
	}
	
	 
	/**
	 * 保存或修改自定义面板关系
	 * 
	 * @param value
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCustomizePanelRelateValue value) throws RemoteException,Exception {
		IAIMonCustomizePanelRelateDAO dao = (IAIMonCustomizePanelRelateDAO)ServiceFactory.getService(IAIMonCustomizePanelRelateDAO.class);
		dao.saveOrUpdate(value);
	}
	
	/**
	 * 批量保存或修改自定义面板关系
	 * 
	 * @param values
	 * @throws Exception
	 */
	public void saveOrUpdate(IBOAIMonCustomizePanelRelateValue[] values) throws RemoteException,Exception {
		IAIMonCustomizePanelRelateDAO dao = (IAIMonCustomizePanelRelateDAO)ServiceFactory.getService(IAIMonCustomizePanelRelateDAO.class);
		dao.saveOrUpdate(values);
	}
	
	/**
	 * 删除自定义面板关系
	 * 
	 * @param relateId
	 * @throws Exception
	 */
	public void delete(long relateId) throws RemoteException,Exception {
		 IAIMonCustomizePanelRelateDAO dao = (IAIMonCustomizePanelRelateDAO)ServiceFactory.getService(IAIMonCustomizePanelRelateDAO.class);
		 dao.delete(relateId);
	}
	
}
