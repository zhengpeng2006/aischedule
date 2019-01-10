package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.impl.CustomPanelTaskFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCustomizePanelSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelValue;

public class AIMonCustomPanelCacheImpl extends AbstractCache {

	//自定义面板前缀
	public static final String _CPANEL_MODEL="_CPS^";
	 
	//任务前缀
	public static final String _CTASK_MODEL="_CTS^";
	
	@Override
	public HashMap getData() throws Exception {
		HashMap result=new HashMap();
		IAIMonCustomizePanelSV panelSV=(IAIMonCustomizePanelSV)ServiceFactory.getService(IAIMonCustomizePanelSV.class);
		if (panelSV==null)
			throw new Exception("no defined IAIMonMainPagePanelSV");

		IBOAIMonCustomizePanelValue[] panelBeans=panelSV.getAllCustomPanelBean();
		if (panelBeans!=null && panelBeans.length>0){
			for (int i=0;i<panelBeans.length;i++){
				CustomPanel cell=panelSV.wrapperPanelByBean(panelBeans[i]);				
				result.put(_CPANEL_MODEL+cell.getPanel_Id(),cell);
				result.put(_CTASK_MODEL+cell.getPanel_Id(),CustomPanelTaskFactory.getCustomPanelTaskContext(cell));
			}
		}
		return result;
	}

}
