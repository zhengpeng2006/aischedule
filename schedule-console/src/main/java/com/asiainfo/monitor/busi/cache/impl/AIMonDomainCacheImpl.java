package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonDomainSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonDomainValue;

public class AIMonDomainCacheImpl extends AbstractCache {

	/**
	 * 简单封装域信息
	 */
	public HashMap getData() throws Exception {
		HashMap map=new HashMap();
		IAIMonDomainSV domainSV=(IAIMonDomainSV)ServiceFactory.getService(IAIMonDomainSV.class);
		IBOAIMonDomainValue[] values=domainSV.getAllDomainBean();
		if (values!=null && values.length>0){
			for (int i=0;i<values.length;i++){
				Domain item=domainSV.wrapperDomainByBean(values[i]);
				if (item!=null)
					map.put(item.getId(),item);
			}
		}
		return map;
	}

}
