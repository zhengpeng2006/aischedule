package com.asiainfo.monitor.busi.exe.task.impl;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCmdSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdSetValue;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class WebConsoleTaskFactory extends AbstractWebConsoleTaskFactory {

	public IBOAIMonCmdSetValue getCmdSetValue(ConstructType type,String value) throws Exception{
		IBOAIMonCmdSetValue cmdSetValue=null;
		try{
			if (StringUtils.isBlank(value))
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000160"));
			IAIMonCmdSV cmdSV=(IAIMonCmdSV)ServiceFactory.getService(IAIMonCmdSV.class);
			if (type==ConstructType.CODE){				
				cmdSetValue=cmdSV.getCmdSetByCode(value);//发布
			}else if (type==ConstructType.ID){
				cmdSetValue=cmdSV.getCmdSetById(Long.parseLong(value));
			}
		}catch(Exception e){
			throw new Exception("Call WebConsoleTaskFactory's method getCmdSetValue has Exception :"+e.getMessage());
		}
		return cmdSetValue;
	}
}
