package com.asiainfo.monitor.busi.cache.impl;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV;

public class AIMonPInfoGroupCacheImpl extends AbstractCache {

	public AIMonPInfoGroupCacheImpl() {
		super();
		// TODO 自动生成构造函数存根
	}

	@Override
	public HashMap getData() throws Exception {
		HashMap result=new HashMap();
		IAIMonPInfoGroupSV infoGroupSV = (IAIMonPInfoGroupSV)ServiceFactory.getService(IAIMonPInfoGroupSV.class);
		if (infoGroupSV==null)
			throw new Exception("no defined IAIMonPInfoGroupSV");
		IBOAIMonPInfoGroupValue[] groupValues=infoGroupSV.getAllMonPInfoGroupBean();
		if (groupValues!=null && groupValues.length>0){
			
			for (int i=0;i<groupValues.length;i++){
				MonPInfoGroup group=infoGroupSV.wrapperMonPInfoGroupByBean(groupValues[i]);
				if (group!=null)
					result.put(group.getId(),group);
			}
		}
		return result;
	}

}
