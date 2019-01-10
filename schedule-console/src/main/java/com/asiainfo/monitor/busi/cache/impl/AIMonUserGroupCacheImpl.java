package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupValue;

public class AIMonUserGroupCacheImpl extends AbstractCache {

	/**
	 * 缓存用户组信息
	 * 
	 */
	public HashMap getData() throws Exception {
		HashMap map=new HashMap();
		IAIMonUserGroupSV userGroupSV=(IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
		IBOAIMonUserGroupValue[] values=userGroupSV.getAllUserGroupBean();
		if (values!=null && values.length>0){
			for (int i=0;i<values.length;i++){
				GroupRole group=userGroupSV.wrapperUserGroupByBean(values[i]);
				if (group!=null){					
					map.put(group.getId(),group);
				}
			}
		}
		return map;
	}

}
