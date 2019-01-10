package com.asiainfo.monitor.busi.model.event;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.RoleDomain;
import com.asiainfo.monitor.busi.model.interfaces.IDomainEvent;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleSV;

public class RoleGuildEvent implements IDomainEvent {

	private final static transient Log log=LogFactory.getLog(RoleGuildEvent.class);
	
	public Object getEventResult(Object obj) {
		RoleDomain result=null;
		if (obj==null || StringUtils.isBlank(obj.toString()))
			return result;
		try{
			IAIMonUserRoleSV userRoleSV=(IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
			result=userRoleSV.getRoleGuildById(String.valueOf(obj));			
		}catch(Exception e){
			log.error("Call RoleGuildEvent's method getEventResult has Exception :"+e.getMessage());
		}
		return result;
	}

}
