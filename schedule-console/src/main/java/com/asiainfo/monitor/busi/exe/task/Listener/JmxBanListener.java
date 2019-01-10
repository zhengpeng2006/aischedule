package com.asiainfo.monitor.busi.exe.task.Listener;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonSetSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonSetValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.ContainerEvent;
import com.asiainfo.monitor.tools.model.interfaces.IContainerListener;

public class JmxBanListener implements IContainerListener, Serializable {

	private static transient Log log=LogFactory.getLog(JmxBanListener.class);
	
	/**
	 * 更改应用的JMX_STATE的注册状态
	 */
	public void containerEvent(ContainerEvent event)  throws Exception{
		if (event.getType().equals(BaseContainer._DO_BEFORE_EVENT)){
			try{
				BaseContainer context=event.getContainer();
				IAIMonSetSV setSV=(IAIMonSetSV)ServiceFactory.getService(IAIMonSetSV.class);
				if (StringUtils.isBlank(context.getServerId())){
					log.error(AIMonLocaleFactory.getResource("MOS0000171"));
					return;
				}
				IBOAIMonSetValue[] setValues=setSV.getMonSetBeanByAppIdAndVCode(context.getServerId(),"JMX_STATE");
				if (setValues!=null && setValues.length==1){
					setValues[0].setSetValue("OFF");
					setSV.saveOrUpdate(setValues[0]);
				}
			}catch(Exception e){
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000322",event.getContainer().getServerId(),e.getMessage()));
			}
		}
	}

}
