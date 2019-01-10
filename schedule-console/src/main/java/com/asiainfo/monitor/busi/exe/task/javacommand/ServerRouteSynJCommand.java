package com.asiainfo.monitor.busi.exe.task.javacommand;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAPICollectSysResourceSV;
import com.asiainfo.monitor.tools.common.KeyName;
import com.asiainfo.monitor.tools.common.interfaces.IJavaCommand;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

public class ServerRouteSynJCommand implements IJavaCommand {

	private transient static Log log = LogFactory.getLog(ServerRouteSynJCommand.class);
	
	public String execute(KeyName[] parameter) throws Exception {
		String result = "NAME:" + AIMonLocaleFactory.getResource("MOS0000351")+"|SUC:TRUE";
		if (parameter == null){
			return "NAME:"+ AIMonLocaleFactory.getResource("MOS0000351")+"|SUC:FAIL";
		}
		
		String appId = "";//WEB应用的标识
		for (int i=0;i<parameter.length;i++){
			if (parameter[i].getName().equals(TypeConst.SERVER+TypeConst._ID)){
				appId = parameter[i].getKey();
				break;
			}
		}
		if (StringUtils.isBlank(appId)){
			appId = parameter[0].getKey();
		}
		if (StringUtils.isNotBlank(appId)){
			IAPICollectSysResourceSV sysResourceSV=(IAPICollectSysResourceSV)ServiceFactory.getService(IAPICollectSysResourceSV.class);
			sysResourceSV.collectAppClusterResource(appId);			
		}
	    return result;
	}

}
