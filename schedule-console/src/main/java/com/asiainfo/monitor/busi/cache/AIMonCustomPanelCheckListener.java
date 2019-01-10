package com.asiainfo.monitor.busi.cache;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonCustomPanelCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.cache.interfaces.ICustomPanel;
import com.asiainfo.monitor.busi.exe.task.impl.CustomPanelTaskFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCustomizePanelSV;
import com.asiainfo.monitor.tools.model.CustomPanelTaskContext;

public class AIMonCustomPanelCheckListener implements ICacheListener {

	public void validityCheck(ICheckCache cacheObj) throws Exception {
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonCustomPanelCacheImpl.class, AIMonCustomPanelCacheImpl._CPANEL_MODEL+cacheObj.getKey());
			MonCacheManager.remove(AIMonCustomPanelCacheImpl.class, AIMonCustomPanelCacheImpl._CTASK_MODEL+cacheObj.getKey());
		}
		IAIMonCustomizePanelSV panelSV=(IAIMonCustomizePanelSV)ServiceFactory.getService(IAIMonCustomizePanelSV.class);
		cacheObj = panelSV.getCustomPanelByIdFromDb(cacheObj.getKey());
		
		if (cacheObj!=null && cacheObj.getEnable()){
			MonCacheManager.put(AIMonCustomPanelCacheImpl.class, AIMonCustomPanelCacheImpl._CPANEL_MODEL+cacheObj.getKey(), cacheObj);
			if (cacheObj!=null){
				CustomPanelTaskContext taskContext = CustomPanelTaskFactory.getCustomPanelTaskContext((ICustomPanel)cacheObj);
				MonCacheManager.put(AIMonCustomPanelCacheImpl.class, AIMonCustomPanelCacheImpl._CTASK_MODEL+cacheObj.getKey(), taskContext);
			}
		}
	}

}
