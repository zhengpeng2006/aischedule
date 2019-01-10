package com.asiainfo.monitor.busi.common.impl;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonLogMapSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonLogBean;
import com.asiainfo.monitor.tools.common.interfaces.IWorker;

public class LogStrategyDBImpl implements LogStrategy {

	public void recordLog(final BOAIMonLogBean qlogBean) throws Exception {
		ChannelExecutor.getInstance().work(new IWorker(){
			public void action() throws Exception{
				IAIMonLogMapSV logSV = (IAIMonLogMapSV) ServiceFactory.getService(IAIMonLogMapSV.class);
				logSV.recordLog(qlogBean);
			}
		});
		
	}

}
