package com.asiainfo.monitor.busi.model.event;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.interfaces.IPhysicHost;
import com.asiainfo.monitor.busi.model.interfaces.IDomainEvent;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;

public class PhysicHostEvent implements IDomainEvent {

	private final static transient Log log=LogFactory.getLog(PhysicHostEvent.class);
	
	/**
	 * 读取组信息
	 */
	public Object getEventResult(Object id) {
		IPhysicHost phost=null;
		if (id==null || StringUtils.isBlank(id.toString()))
			return phost;
		try{
			IAIMonPhysicHostSV phostSV=(IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
			phost=phostSV.getPhostByIdFromDb(id.toString());
		}catch(Exception e){
			log.error("Call PhysicHostEvent method getEventResult has Exception :"+e.getMessage());
		}
		return phost;
	}
	
}
