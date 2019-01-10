package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.impl.WebPanelTaskFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonAttentionPanelSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonAttentionPanelValue;

public class AIMonAttentionPanelCacheImpl extends AbstractCache {

	private static final Log log=LogFactory.getLog(AIMonAttentionPanelCacheImpl.class);
	
	//面板前缀
	public static final String _PANEL_MODEL="_PS^";
	 
	//任务前缀
	public static final String _TASK_MODEL="_TS^";
	
	public AIMonAttentionPanelCacheImpl() {
		super();
		// TODO 自动生成构造函数存根
	}

	@Override
	public HashMap getData() throws Exception {
		HashMap result=new HashMap();
		IAIMonAttentionPanelSV panelSV=(IAIMonAttentionPanelSV)ServiceFactory.getService(IAIMonAttentionPanelSV.class);
		if (panelSV==null)
			throw new Exception("no defined IAIMonMainPagePanelSV");
		IBOAIMonAttentionPanelValue[] panelBeans=panelSV.getAllAttentionPanelBean();
		if (panelBeans!=null && panelBeans.length>0){
			for (int i=0;i<panelBeans.length;i++){
				Panel cell=panelSV.wrapperPanelByBean(panelBeans[i]);				
				result.put(_PANEL_MODEL+cell.getPanel_Id(),cell);
				result.put(_TASK_MODEL+cell.getPanel_Id(),WebPanelTaskFactory.getWebPanelTaskContext(cell));
			}
		}
		return result;
	}

}
