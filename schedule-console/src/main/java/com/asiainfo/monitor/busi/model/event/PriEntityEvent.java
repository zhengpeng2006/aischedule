package com.asiainfo.monitor.busi.model.event;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.PriEntity;
import com.asiainfo.monitor.busi.model.interfaces.IDomainEvent;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserPriEntitySV;

public class PriEntityEvent implements IDomainEvent {

	private final static transient Log log=LogFactory.getLog(PriEntityEvent.class);
	
	public Object getEventResult(Object obj) {
		PriEntity result=null;
		if (obj==null || StringUtils.isBlank(obj.toString()))
			return result;
		try{
			IAIMonUserPriEntitySV priEntitySV=(IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
			result=priEntitySV.getEntityByEntityId(String.valueOf(obj));
		}catch(Exception e){
			log.error("Call PriEntityEvent's method getEventResult has Exception :"+e.getMessage());
		}
		return result;
	}

}
