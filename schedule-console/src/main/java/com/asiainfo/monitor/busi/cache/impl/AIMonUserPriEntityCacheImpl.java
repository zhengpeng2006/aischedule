package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserPriEntitySV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserPriEntityValue;

public class AIMonUserPriEntityCacheImpl extends AbstractCache {

	private static final Log log=LogFactory.getLog(AIMonUserPriEntityCacheImpl.class);
	
	@Override
	public HashMap getData() throws Exception {
		HashMap result=new HashMap();
		IAIMonUserPriEntitySV entirySV=(IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
		if (entirySV==null)
			throw new Exception("no defined IAIMonMainPagePanelSV");
		IBOAIMonUserPriEntityValue[] entityBeans=entirySV.getAllEntityBean();
		if (entityBeans!=null && entityBeans.length>0){
			
			for (int i=0;i<entityBeans.length;i++){
				PriEntity entity=entirySV.wrapperPriEntityByBean(entityBeans[i]);
				if (entity!=null)
					result.put(entity.getId(),entity);
			}
		}
		return result;
	}

}
