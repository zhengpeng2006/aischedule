package com.asiainfo.monitor.busi.common.impl;

import com.asiainfo.monitor.exeframe.config.bo.BOAIMonLogBean;

public interface LogStrategy {

	public void recordLog(BOAIMonLogBean qlogBean) throws Exception;
	
}
