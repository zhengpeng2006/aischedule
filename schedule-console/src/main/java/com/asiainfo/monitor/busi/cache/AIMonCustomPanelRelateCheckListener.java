package com.asiainfo.monitor.busi.cache;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.AIMonCustomPanelRelateCacheImpl;
import com.asiainfo.monitor.busi.cache.interfaces.ICacheListener;
import com.asiainfo.monitor.busi.cache.interfaces.ICheckCache;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCustomizePanelRelateSV;
import com.asiainfo.monitor.tools.util.TypeConst;

public class AIMonCustomPanelRelateCheckListener implements ICacheListener {

	public void validityCheck(ICheckCache cacheObj) throws Exception {
		if (MonCacheManager.getCacheReadOnlySet())
			return;
		if (cacheObj!=null){
			MonCacheManager.remove(AIMonCustomPanelRelateCacheImpl.class, AIMonCustomPanelRelateCacheImpl._CPANEL_AND_PANEL_MODEL+cacheObj.getKey());
		}
		IAIMonCustomizePanelRelateSV relateSV=(IAIMonCustomizePanelRelateSV)ServiceFactory.getService(IAIMonCustomizePanelRelateSV.class);
		
		String cpanelId = "";
		String panelId = "";
		if (StringUtils.isNotBlank(cacheObj.getKey())) {
			String tmp[] = cacheObj.getKey().split(TypeConst._INTERPRET_CHAR);
			cpanelId = tmp[0];
			panelId = tmp[1];
		}
		
		cacheObj = relateSV.getPanelRelateByCpIdAndPIdFromDb(cpanelId, panelId);
		
		if (cacheObj!=null && cacheObj.getEnable()){
			MonCacheManager.put(AIMonCustomPanelRelateCacheImpl.class, AIMonCustomPanelRelateCacheImpl._CPANEL_AND_PANEL_MODEL+cacheObj.getKey(), cacheObj);
		}
	}

}
