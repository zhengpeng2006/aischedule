package com.asiainfo.monitor.busi.exe.task.Listener;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowServerStatusSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.interapi.ServerControlInfo;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.ContainerEvent;
import com.asiainfo.monitor.tools.model.interfaces.IContainerListener;

public class VersionChangeListener implements IContainerListener, Serializable {

	private static transient Log log=LogFactory.getLog(VersionChangeListener.class);
	
	/**
	 * 发布完成后，进行版本更新
	 */
	public void containerEvent(ContainerEvent event)  throws Exception{
		if (event.getType().equals(BaseContainer._DO_AFTER_EVENT)){
			try{
				BaseContainer context=event.getContainer();
				String serverId = context.getServerId();
				if (StringUtils.isNotBlank(serverId)){
					IAPIShowServerStatusSV showServerSV=(IAPIShowServerStatusSV)ServiceFactory.getService(IAPIShowServerStatusSV.class);
					ServerControlInfo info=showServerSV.getAppServerState(serverId, 1, -1);
					
					IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
					IBOAIMonServerValue serverBean=serverSV.getServerBeanById(serverId);
					if (serverBean==null)
						return;
					if (StringUtils.isNotBlank(info.getInfo())){
						serverBean.setSversion(info.getInfo());
						serverSV.saveOrUpdate(serverBean);
					}
				}

			}catch(Exception e){
				log.error("Call VersionChangeListener's method containerEvent has Exception :",e);
			}
		}
	}

}
