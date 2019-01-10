package com.asiainfo.monitor.tools.cache.impl;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.util.locale.AppframeLocaleFactory;
import com.asiainfo.monitor.tools.ivalues.IBOAIMonI18nResourceValue;
import com.asiainfo.monitor.tools.service.interfaces.IAIMonI18nResourceSV;

public class AIMonI18nResourceCacheImpl extends AbstractCache {
	
	public AIMonI18nResourceCacheImpl() {
		super();
	}
	
	public HashMap getData() throws Exception {
		HashMap result=null;
		IAIMonI18nResourceSV resourceSV = (IAIMonI18nResourceSV)ServiceFactory.getService(IAIMonI18nResourceSV.class);
		if (resourceSV == null)
			throw new Exception("no defined IAIMonI18nResourceSV");
		IBOAIMonI18nResourceValue[] values = resourceSV.getI18nResource();
		if (values != null && values.length > 0) {
			result = new HashMap();
			for (int i=0;i<values.length;i++) {
				if (IBOAIMonI18nResourceValue.S_ZhCn.equalsIgnoreCase(AppframeLocaleFactory.getCurrentLocale().toString())) {
					result.put(values[i].getResKey().trim(), values[i].getZhCn().trim());
				} else {
					result.put(values[i].getResKey().trim(), values[i].getEnUs().trim());
				}
			}
		}
		return result;
	}
}
