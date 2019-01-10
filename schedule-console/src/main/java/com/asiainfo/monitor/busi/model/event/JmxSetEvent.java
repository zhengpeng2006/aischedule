package com.asiainfo.monitor.busi.model.event;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.MonSet;
import com.asiainfo.monitor.busi.model.interfaces.IDomainEvent;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonSetSV;

public class JmxSetEvent implements IDomainEvent {

	private final static transient Log log=LogFactory.getLog(JmxSetEvent.class);
	
	public Object getEventResult(Object obj) {
		if (obj==null || StringUtils.isBlank(String.valueOf(obj)))
			return null;
		MonSet result=null;
		try{
			IAIMonSetSV setSV=(IAIMonSetSV)ServiceFactory.getService(IAIMonSetSV.class);
			result=setSV.getMonSetByAppIdAndVCode(obj.toString(),"JMX_STATE");
		}catch(Exception e){
			log.error("Call JmxSetEvent's method getEventResult has Exception :"+e.getMessage());
		}
		return result;
	}

}
