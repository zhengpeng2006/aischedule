package com.asiainfo.monitor.busi.exe.task.Listener;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonSetSV;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.ContainerEvent;
import com.asiainfo.monitor.tools.model.interfaces.IContainerListener;
import com.asiainfo.monitor.tools.model.interfaces.ITaskContext;


public class JmxInspireListener implements IContainerListener, Serializable {

	private static transient Log log=LogFactory.getLog(JmxInspireListener.class);
	
	public void containerEvent(ContainerEvent event)  throws Exception{
		
		if (event.getType().equals(BaseContainer._DO_AFTER_EVENT)){
			try{
				if (event.getContainer() instanceof ITaskContext){
					ITaskContext task=(ITaskContext)event.getContainer();					
					if (StringUtils.isNotBlank(task.getServerId())){
						IAIMonSetSV setSV=(IAIMonSetSV)ServiceFactory.getService(IAIMonSetSV.class);
						setSV.synchroJmxState(new String[]{task.getServerId()});
					}
				}
			}catch(Exception e){
				log.error("Call JmxInspireListener's method containerEvent has Exception :",e);
			}
		}
	}

}
