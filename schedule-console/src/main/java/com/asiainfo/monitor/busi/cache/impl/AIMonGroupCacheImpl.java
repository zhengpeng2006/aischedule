package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;

public class AIMonGroupCacheImpl extends AbstractCache {

	public AIMonGroupCacheImpl() {
		super();
	}

	@Override
	public HashMap getData() throws Exception {
        LinkedHashMap result = new LinkedHashMap();
		IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
		
		if (groupSV!=null){
			IBOAIMonGroupValue[] groups=groupSV.getAllGroupBean();
			if (groups!=null && groups.length>0){
				for (int i=0;i<groups.length;i++){
					Group group=groupSV.wrapperGroupConfigByBean(groups[i]);					
					if (group!=null){
						result.put(group.getGroup_Id()+"",group);
					}
				}
			}
		}
		return result;
	}

}
