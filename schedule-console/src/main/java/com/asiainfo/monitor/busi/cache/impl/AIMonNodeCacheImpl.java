package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;

public class AIMonNodeCacheImpl extends AbstractCache {

	public AIMonNodeCacheImpl() {
		super();
	}

	@Override
	public HashMap getData() throws Exception {
        LinkedHashMap result = new LinkedHashMap();
		IAIMonNodeSV hostSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
		if (hostSV!=null){
			IBOAIMonNodeValue[] hostValues=hostSV.getAllNodeBean();
			if (hostValues!=null){
				for (int i=0;i<hostValues.length;i++){
					ServerNode item=hostSV.wrapperNodeByBean(hostValues[i]);
					if (item!=null)
						result.put(item.getNode_Id(),item);
				}
			}
		}
		return result;
	}

}
