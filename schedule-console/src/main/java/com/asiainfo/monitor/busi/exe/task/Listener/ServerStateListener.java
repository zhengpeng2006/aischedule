package com.asiainfo.monitor.busi.exe.task.Listener;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.common.impl.ServerStateEnv;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.ContainerEvent;
import com.asiainfo.monitor.tools.model.interfaces.IContainerListener;

public class ServerStateListener implements IContainerListener, Serializable {

	private static transient Log log=LogFactory.getLog(ServerStateListener.class);
	
	/**
	 * 监听应用各种状态，暂时只提供对启动、发布、停止以及重启状态的监听
	 */
	public void containerEvent(ContainerEvent event)  throws Exception{
		BaseContainer context=event.getContainer();
		if (event.getType().equals(BaseContainer._DO_BEFORE_EVENT)){
			
			if (StringUtils.isBlank(context.getServerId())){
				log.error(AIMonLocaleFactory.getResource("MOS0000171"));
				return;
			}
			if (!ServerStateEnv.getInstance().putState(context.getServerId(),event.getData())){
				log.error("["+context.getServerId()+"]:"+event.getData());
				return;
			}
		}else if (event.getType().equals(BaseContainer._DO_AFTER_EVENT)){
			ServerStateEnv.getInstance().removeState(context.getServerId());
		}
	}

}
