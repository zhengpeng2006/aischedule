package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.interfaces.IPhysicHost;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;

public class AIMonPhostCacheImpl extends AbstractCache {

	@Override
	public HashMap getData() throws Exception {
		HashMap result=new HashMap();
		IAIMonPhysicHostSV phostSV=(IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
		
		if (phostSV!=null){
			IBOAIMonPhysicHostValue[] phosts=phostSV.getAllPhostBean();
			if (phosts!=null && phosts.length>0){
				for (int i=0;i<phosts.length;i++){
					IPhysicHost phost=phostSV.wrapperPhysicHostByBean(phosts[i]);					
					if (phost!=null){
                        result.put(phost.getHostId(), phost);
					}
				}
			}
		}
		return result;
	}

}
