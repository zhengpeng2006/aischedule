package com.asiainfo.monitor.busi.model.event;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.GroupConfig;
import com.asiainfo.monitor.busi.model.interfaces.IDomainEvent;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;

public class GroupEvent implements IDomainEvent {

	private final static transient Log log=LogFactory.getLog(GroupEvent.class);
	
	/**
	 * 读取组信息
	 */
	public Object getEventResult(Object id) {
		GroupConfig groupConfig=null;
		if (id==null || StringUtils.isBlank(id.toString()))
			return groupConfig;
		try{
			IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
			groupConfig=groupSV.getGroupByGroupId(id.toString());
		}catch(Exception e){
			log.error("Call GroupEvent's method getEventResult has Exception :"+e.getMessage());
		}
		return groupConfig;
	}

}
