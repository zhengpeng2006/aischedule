package com.asiainfo.monitor.busi.web;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonAttentionPanelCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.Panel;
import com.asiainfo.monitor.busi.config.PanelContainer;
import com.asiainfo.monitor.busi.config.PanelShape;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCustomizePanelRelateSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCustomizePanelSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonCustomizePanelBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonCustomizePanelRelateBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelRelateValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class CustomPanelAction extends BaseAction {

	private static final Log log=LogFactory.getLog(CustomPanelAction.class);
	
	/**
	 * 读取自定义面板内容
	 * @param entityId
	 * @return
	 * @throws Exception
	 */
//	public List getCustomPanelByCpanelId(String cpanelId)throws Exception{
//		List result = new ArrayList();		
//		try{
//			IAIMonCustomizePanelSV iasv = (IAIMonCustomizePanelSV)ServiceFactory.getService(IAIMonCustomizePanelSV.class);
//			PanelShape pc = iasv.getCustomPanelById(Long.parseLong(cpanelId));
//			if (pc instanceof CustomPanelShape){
//				result.addAll( ((CustomPanelShape)pc).getChildShapes());
//			}
//		}catch (Exception e) {
//			log.error(e);
//		}
//		return result;		
//	}
	
	/**
	 * 根据EntityId读取自定义面板
	 * 
	 * @param entityId
	 * @return
	 * @throws Exception
	 */
//	public List getCustomPanelByEntityId(String entityId) throws Exception {
//		List ret = new ArrayList();
//		try {
//			IAIMonCustomizePanelSV iasv = (IAIMonCustomizePanelSV)ServiceFactory.getService(IAIMonCustomizePanelSV.class);
//			PanelContainer pc = iasv.getCustomPanelByEntityId(entityId);
//			for (int i = 0; i < pc.getShapes().size(); i++) {
//				PanelShape tmp = (PanelShape) pc.getShapes().get(i);
//				if (tmp != null)
//					ret.add(tmp.getPanel());
//			}
//		} catch (Exception e) {
//			log.error(e);
//		}
//		return ret;
//	}
	
	/**
	 * 读取自定义面板关联面板内容
	 * @param entityId
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelRelateValue[] getCustomPanelRelateByCpanelId(String cpanelId)throws Exception{
		if (StringUtils.isBlank(cpanelId)) {
			return null;
		}
		IAIMonCustomizePanelRelateSV iasv = (IAIMonCustomizePanelRelateSV)ServiceFactory.getService(IAIMonCustomizePanelRelateSV.class);
		IBOAIMonCustomizePanelRelateValue[] panelRelate =iasv.getCustomizePanelByCId(Long.parseLong(cpanelId));
		String panelName = "";
		 for(int i=0;i<panelRelate.length;i++){
		    	long id = panelRelate[i].getPanelId();
		    	panelName="";
				Panel result=null;
					result=(Panel)MonCacheManager.get(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._PANEL_MODEL+id);
					if (result!=null){
						panelName = result.getPanel_Name();
						panelRelate[i].setExtAttr("CHK",panelName);
					} else {
						panelRelate[i].setExtAttr("CHK","");
					}
					
		    }
		return panelRelate;	
	}
	
	
	/**
	 * 根据名称读取自定义面板内容
	 * @param panelName
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonCustomizePanelValue[] getCustomPanelByCpanelName(String cpanelName)throws Exception{
			IAIMonCustomizePanelSV iasv = (IAIMonCustomizePanelSV)ServiceFactory.getService(IAIMonCustomizePanelSV.class);
			return iasv.getCustomizePanelByName(cpanelName);
	}
	/**
	 * 保存面板
	 * @param panelInfo
	 * @return
	 * @throws Exception
	 */
	public boolean saveCustomPanel(Object[] customtype)throws Exception{
		if(customtype==null){
			// "没有需要保存的数据！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000048"));
		}
		boolean result=false;
		try {

			IBOAIMonCustomizePanelValue customPanel = new BOAIMonCustomizePanelBean();
	    	  if (StringUtils.isBlank(customtype[0].toString())) {
	    		  customPanel.setStsToNew();
				} else {
					customPanel.setCpanelId(Long.parseLong(customtype[0].toString().trim()));
					customPanel.setStsToOld();
				}
	    	  		customPanel.setCpanelName(customtype[1].toString().trim());
	    	  		customPanel.setCpanelDesc(customtype[2].toString().trim());
	    	  		customPanel.setCustType(customtype[3].toString().trim());
	    	  		customPanel.setLayer(customtype[4].toString().trim());
	    	  		customPanel.setTempType(customtype[5].toString().trim());
	    	  		customPanel.setState(customtype[6].toString().trim());
	    	  		customPanel.setRemarks(customtype[7].toString().trim());
	    	  		IAIMonCustomizePanelSV customSV =  (IAIMonCustomizePanelSV)ServiceFactory.getService(IAIMonCustomizePanelSV.class);
	    	  		customSV.saveOrUpdate(customPanel);
		} catch (Exception e) {			
			//log.error("保存面板内容发生异常:"+e.getMessage(),e);
			// "保存面板内容发生异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000062")+e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 删除面板
	 * @param panelInfo
	 * @return
	 * @throws Exception
	 */
	public boolean deleteCustomPanel(String panelId )throws Exception{
		boolean result=false;
		try {
			if (StringUtils.isBlank(String.valueOf(panelId))) {
				return result;
			}
		
	  		IAIMonCustomizePanelSV customSV =  (IAIMonCustomizePanelSV)ServiceFactory.getService(IAIMonCustomizePanelSV.class);
	  		customSV.delete(Long.parseLong(panelId));
	  		result=true;
		} catch (Exception e) {
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000064")+e.getMessage(),e);
		}
		return result;
		}
	
	/**
	 * 删除面板关系
	 * @param panelInfo
	 * @return
	 * @throws Exception
	 */
	public boolean deleteCustomPanelRelate(String relateId )throws Exception{
		boolean result=false;
		try {
		if (StringUtils.isBlank(String.valueOf(relateId))) {
			return result;
		}
		
			IAIMonCustomizePanelRelateSV customSV =  (IAIMonCustomizePanelRelateSV)ServiceFactory.getService(IAIMonCustomizePanelRelateSV.class);
	  		customSV.delete(Long.parseLong(relateId));
	  		result=true;
		} catch (Exception e) {
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000064")+e.getMessage(),e);
		}
		return result;
		}
	
	/**
	 * 保存面板
	 * @param panelInfo
	 * @return
	 * @throws Exception
	 */
	public boolean saveCustomPanelRelate(String flag,Object[] customRelateType)throws Exception{
		if(customRelateType==null){
			// "没有需要保存的数据！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000048"));
		}
		boolean result=false;
		try {

			IBOAIMonCustomizePanelRelateValue customPanel = new BOAIMonCustomizePanelRelateBean();
	    	  if (flag.equals("1")) {
	    		  customPanel.setStsToNew();
				} else if(flag.equals("0")){
					customPanel.setRelateId(Long.parseLong(customRelateType[0].toString().trim()));
					customPanel.setStsToOld();
				}
	    	  		customPanel.setCpanelId(Long.parseLong(customRelateType[1].toString().trim()));
	    	  		customPanel.setPanelId(Long.parseLong(customRelateType[2].toString().trim()));
	    	  		customPanel.setRelateName(customRelateType[3].toString().trim());
	    	  		customPanel.setRunType(customRelateType[4].toString().trim());
	    	  		customPanel.setThresholdId(Long.parseLong(customRelateType[5].toString().trim()));
	    	  		customPanel.setTimeId(Long.parseLong(customRelateType[6].toString().trim()));
	    	  		customPanel.setViewType(customRelateType[7].toString().trim());
	    	  		customPanel.setViewStrategy(customRelateType[8].toString().trim());
	    	  		customPanel.setViewTransform(customRelateType[9].toString().trim());
	    	  		customPanel.setState(customRelateType[10].toString().trim());
	    	  		customPanel.setRemarks(customRelateType[11].toString().trim());
	    	  		IAIMonCustomizePanelRelateSV customSV =  (IAIMonCustomizePanelRelateSV)ServiceFactory.getService(IAIMonCustomizePanelRelateSV.class);
	    	  		customSV.saveOrUpdate(customPanel);
		} catch (Exception e) {			
			//log.error("保存面板内容发生异常:"+e.getMessage(),e);
			// "保存面板内容发生异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000062")+e.getMessage(),e);
		}
		return result;
	}
}
