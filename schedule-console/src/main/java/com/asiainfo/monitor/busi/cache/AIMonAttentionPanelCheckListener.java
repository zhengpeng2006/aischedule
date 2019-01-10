package com.asiainfo.monitor.busi.cache;


import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonAttentionPanelCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.cache.interfaces.IPanel;
import com.asiainfo.monitor.busi.exe.task.impl.WebPanelTaskFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonAttentionPanelSV;
import com.asiainfo.monitor.tools.model.WebPanelTaskContext;

public class AIMonAttentionPanelCheckListener implements ICacheListener {

	public AIMonAttentionPanelCheckListener(){
		
	}
	
	public void validityCheck(ICheckCache cacheObj) throws Exception {
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._PANEL_MODEL+cacheObj.getKey());
			MonCacheManager.remove(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._TASK_MODEL+cacheObj.getKey());
		}
		IAIMonAttentionPanelSV panelSV=(IAIMonAttentionPanelSV)ServiceFactory.getService(IAIMonAttentionPanelSV.class);		
		cacheObj=panelSV.getPanelByIdFromDb(cacheObj.getKey());
		
		if (cacheObj!=null && cacheObj.getEnable()){
			MonCacheManager.put(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._PANEL_MODEL+cacheObj.getKey(),cacheObj);
			if (cacheObj!=null){
				WebPanelTaskContext taskContext=WebPanelTaskFactory.getWebPanelTaskContext((IPanel)cacheObj);
				if (taskContext!=null)
					MonCacheManager.put(AIMonAttentionPanelCacheImpl.class,AIMonAttentionPanelCacheImpl._TASK_MODEL+cacheObj.getKey(),taskContext);
			}
		}
	}

}
