package com.asiainfo.common.abo.dao.interfaces;

import java.util.Date;

import com.asiainfo.common.abo.bo.BOABGMonBusiLogBean;

public interface IABGMonBusiLogDAO {

	public long getBeanId() throws Exception;

	public BOABGMonBusiLogBean getBeanById(long id) throws Exception;

	public void save(BOABGMonBusiLogBean value) throws Exception;
	
	public BOABGMonBusiLogBean getBeanById(long id, Date acqDate) throws Exception;

//	public BOABGMonBusiLogBean[] get() throws Exception;
}
