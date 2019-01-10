package com.asiainfo.monitor.busi.model.event;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.impl.GroupRole;
import com.asiainfo.monitor.busi.model.interfaces.IDomainEvent;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupSV;

public class UserGroupEvent implements IDomainEvent {

	private final static transient Log log=LogFactory.getLog(UserGroupEvent.class);
	
	public Object getEventResult(Object obj) {
		GroupRole result=null;
		if (obj==null || StringUtils.isBlank(obj.toString()))
			return result;
		try{
			IAIMonUserGroupSV userGroupSV=(IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
			result=userGroupSV.getUserGroupById(String.valueOf(obj));			
		}catch(Exception e){
			log.error("Call UserGroupEvent's method getEventResult has Exception :"+e.getMessage());
		}
		return result;
	}

}
