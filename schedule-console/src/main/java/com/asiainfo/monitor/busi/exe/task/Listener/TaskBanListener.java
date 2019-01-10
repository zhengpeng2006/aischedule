package com.asiainfo.monitor.busi.exe.task.Listener;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.ContainerEvent;
import com.asiainfo.monitor.tools.model.interfaces.ICmdType;
import com.asiainfo.monitor.tools.model.interfaces.IContainerListener;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;

public class TaskBanListener implements IContainerListener, Serializable {

	private static transient Log log=LogFactory.getLog(TaskBanListener.class);
	
	/**
	 * 1、控制命令如启动、停止、发布等只支持SHell类型的命令
	 * 
	 */
	public void containerEvent(ContainerEvent event)  throws Exception{
		if (event.getType().equals(BaseContainer._DO_BEFORE_EVENT)){
			try{
				if (event.getContainer() instanceof ITaskCmdContainer){
					if ( ((ITaskCmdContainer)event.getContainer()).getCmdType()==null )
						// "具体命令为空"
						throw new Exception(AIMonLocaleFactory.getResource("MOS0000172"));
					if ( !((ITaskCmdContainer)event.getContainer()).getCmdType().getType().equals(ICmdType.SHELL) )
						// 控制命令类型暂不支持
						throw new Exception(AIMonLocaleFactory.getResource("MOS0000173")+ ":"+((ITaskCmdContainer)event.getContainer()).getCmdType().getType());					
				}else{
					// 命令大类只支持ITaskCmdContainer
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000174") +":"+event.getContainer().getName());
				}
			}catch(Exception e){
				log.error("Call TaskBanListener's method containerEvent has Exception :",e);
			}
		}
	}

}
