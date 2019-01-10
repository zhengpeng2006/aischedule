package com.asiainfo.monitor.busi.model.event;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.Domain;
import com.asiainfo.monitor.busi.model.interfaces.IDomainEvent;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonDomainSV;

public class DomainEvent implements IDomainEvent {

	private final static transient Log log=LogFactory.getLog(DomainEvent.class);
	
	
	public Object getEventResult(Object obj) {
		Domain result=null;
		if (obj==null || StringUtils.isBlank(obj.toString()))
			return result;
		try{
			IAIMonDomainSV domainSV=(IAIMonDomainSV)ServiceFactory.getService(IAIMonDomainSV.class);
			result=domainSV.getDomainById(String.valueOf(obj));			
		}catch(Exception e){
			log.error("Call DomainEvent's method getEventResult has Exception :"+e.getMessage());
		}
		return result;
	}

}
