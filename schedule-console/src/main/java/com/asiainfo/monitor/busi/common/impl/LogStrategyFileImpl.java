package com.asiainfo.monitor.busi.common.impl;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.exeframe.config.bo.BOAIMonLogBean;
import com.asiainfo.monitor.tools.common.interfaces.IWorker;

public class LogStrategyFileImpl implements LogStrategy {

	Log log  = LogFactory.getLog(LogStrategyFileImpl.class);
	
	public void recordLog(final BOAIMonLogBean qlogBean) throws Exception {
		ChannelExecutor.getInstance().work(new IWorker(){
			public void action() throws Exception{
				log.debug(qlogBean.toString());
			}
		});
		
	}

}
