package com.asiainfo.monitor.busi.dao.interfaces;

import com.asiainfo.monitor.exeframe.config.bo.BOAIMonLogBean;


public interface IAIMonLogMapDAO {

	void recordLogToDB(BOAIMonLogBean qlogBean);

}
