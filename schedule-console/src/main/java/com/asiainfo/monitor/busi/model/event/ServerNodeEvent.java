package com.asiainfo.monitor.busi.model.event;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerNodeConfig;
import com.asiainfo.monitor.busi.model.interfaces.IDomainEvent;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;

public class ServerNodeEvent implements IDomainEvent {

	private final static transient Log log=LogFactory.getLog(ServerNodeEvent.class);
	
	/**
	 * 读取主机对象
	 */
	public Object getEventResult(Object id) {
		ServerNodeConfig nodeConfig=null;
		if (id==null || StringUtils.isBlank(id.toString()))
			return nodeConfig;
		try{
			IAIMonNodeSV nodeSV=(IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
			nodeConfig=nodeSV.getNodeByNodeId(id.toString());
		}catch(Exception e){
			log.error("Call ServerNodeEvent's method getEventResult has Exception :"+e.getMessage());
		}
		return nodeConfig;
	}

}
