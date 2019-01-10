package com.asiainfo.monitor.busi.exe.task.javacommand;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonSetSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IJavaCommand;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

/**
 * 后台定时任务,同步应用的JmxState
 * @author Guocx
 *
 */
public class JmxStateSynJCommand implements IJavaCommand {

	private transient static Log log = LogFactory.getLog(JmxStateSynJCommand.class);
	
	
	public String execute(KeyName[] parameter) throws Exception {
		String result = "NAME:"+ AIMonLocaleFactory.getResource("MOS0000166")+"|SUC:TRUE";
		IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
		IBOAIMonServerValue[] serverValues=serverSV.getAllServerBean();
		List idList=new ArrayList();
		if (serverValues!=null && serverValues.length>0){
			for (int i=0;i<serverValues.length;i++){
				idList.add(serverValues[i].getServerId()+"");
			}
		}
		IAIMonSetSV setSV=(IAIMonSetSV)ServiceFactory.getService(IAIMonSetSV.class);		
	    try {
	    	setSV.synchroJmxState(idList.toArray());
	    }
	    catch (Throwable ex) {
	    	result = "NAME:"+ AIMonLocaleFactory.getResource("MOS0000166")+"|SUC:FALSE";
	    	log.error("Call JmxStateSynJCommand's method execute has Exception :",ex);
	    }
	    return result;
	}

}
