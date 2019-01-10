package com.asiainfo.common.abo.dao.interfaces;

import java.util.Date;

import com.asiainfo.common.abo.bo.BOABGMonBusiErrorLogBean;

public interface IABGMonBusiErrorLogDAO {

	public long getBeanId() throws Exception;

	public BOABGMonBusiErrorLogBean getBeanById(long id) throws Exception;

	public void save(BOABGMonBusiErrorLogBean value) throws Exception;
	
	public BOABGMonBusiErrorLogBean getBeanById(long id, Date acqDate) throws Exception;
}
