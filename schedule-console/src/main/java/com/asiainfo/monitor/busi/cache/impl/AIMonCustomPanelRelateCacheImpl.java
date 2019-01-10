package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCustomizePanelRelateSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCustomizePanelRelateValue;
import com.asiainfo.monitor.tools.util.TypeConst;

public class AIMonCustomPanelRelateCacheImpl extends AbstractCache {

	//自定义面板公共面板关系前缀
	public static final String _CPANEL_AND_PANEL_MODEL="_CP&PS^";
	
	@Override
	public HashMap getData() throws Exception {
		HashMap result=new HashMap();
		IAIMonCustomizePanelRelateSV relateSV=(IAIMonCustomizePanelRelateSV)ServiceFactory.getService(IAIMonCustomizePanelRelateSV.class);
		
		IBOAIMonCustomizePanelRelateValue[] panelBeans=relateSV.getAllCustomPanelRelateBean();
		if (panelBeans!=null && panelBeans.length>0){
			for (int i=0;i<panelBeans.length;i++){
				ConsanPanel cell=(ConsanPanel)relateSV.wrapperRelatePanelByBean(panelBeans[i]);
				result.put(_CPANEL_AND_PANEL_MODEL+cell.getC_Panel_Id()+TypeConst._INTERPRET_CHAR+cell.getPanel_Id(),cell);
			}
		}
		return result;
	}

}
