package com.asiainfo.monitor.busi.exe.task.Listener;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.busi.exe.task.impl.TaskPostionStategyFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.model.BaseContainer;
import com.asiainfo.monitor.tools.model.ContainerEvent;
import com.asiainfo.monitor.tools.model.interfaces.IContainerListener;
import com.asiainfo.monitor.tools.model.interfaces.ITaskCmdContainer;
import com.asiainfo.monitor.tools.model.interfaces.ITaskContext;

public class TaskPostionListener implements IContainerListener, Serializable {

	private static transient Log log=LogFactory.getLog(TaskPostionListener.class);
	
	public void containerEvent(ContainerEvent event)  throws Exception{
		if (event.getType().equals(BaseContainer._DO_AFTER_EVENT)){
			try{
				if (event.getContainer() instanceof ITaskCmdContainer){
					if (event.getContainer().getParent() instanceof ITaskContext){
						ITaskContext task=(ITaskContext)event.getContainer().getParent();
						if (task.getRtnModel()==null || task.getRtnModel().getRtns()==null || task.getRtnModel().getRtns().size()<1){
							// "任务["+task.getId()+"]执行没有返回值"
							log.error(AIMonLocaleFactory.getResource("MOS0000158", task.getId()));
							return;
						}
						TaskPostionStategyFactory.process(task);
					}					
				}
			}catch(Exception e){
				log.error("Call TaskPostionListener's method containerEvent has Exception :",e);
			}
		}
	}

}
