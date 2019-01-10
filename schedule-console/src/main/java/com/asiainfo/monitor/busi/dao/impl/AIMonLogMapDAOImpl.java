package com.asiainfo.monitor.busi.dao.impl;


import com.asiainfo.monitor.busi.dao.interfaces.IAIMonLogMapDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonLogBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonLogEngine;

public class AIMonLogMapDAOImpl implements IAIMonLogMapDAO{


	/**
	 * appmonitor日志记录到数据库
	 */
	public void recordLogToDB(BOAIMonLogBean logBean) {
		try {

			logBean.setLogId(BOAIMonLogEngine.getNewId().longValue());
			BOAIMonLogEngine.save(logBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
