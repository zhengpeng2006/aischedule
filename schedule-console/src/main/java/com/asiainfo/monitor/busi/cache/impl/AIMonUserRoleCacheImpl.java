package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleValue;

public class AIMonUserRoleCacheImpl extends AbstractCache {

	/**
	 * 
	 */
	public HashMap getData() throws Exception {
		HashMap map=new HashMap();
		IAIMonUserRoleSV userRoleSV=(IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
		IBOAIMonUserRoleValue[] values=userRoleSV.getAllUserRoleBean();
		if (values!=null && values.length>0){
			for (int i=0;i<values.length;i++){
				RoleDomain item=userRoleSV.wrapperRoleGuildByBean(values[i]);
				if (item!=null){					
					map.put(item.getId(),item);
				}
			}
		}
		return map;
	}

}
