package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;

public class AIMonServerCacheImpl extends AbstractCache {

	public AIMonServerCacheImpl() {
		super();
	}

	@Override
	public HashMap getData() throws Exception {
		HashMap result=new HashMap();
		IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
		if (serverSV!=null){
			IBOAIMonServerValue[] serverValues=serverSV.getAllServerBean();
			if (serverValues!=null && serverValues.length>0){
				for (int i=0;i<serverValues.length;i++){
					Server item=serverSV.wrapperServerByBean(serverValues[i]);
					if (item!=null)
						result.put(item.getApp_Id(),item);
				}
			}
		}
		return result;
	}

}
