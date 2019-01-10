package com.asiainfo.common.abo.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ai.appframe2.bo.IBOMask;
import com.asiainfo.common.abo.bo.BOABGMonBusiErrorLogBean;
import com.asiainfo.common.abo.bo.BOABGMonBusiErrorLogEngine;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogBean;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogEngine;
import com.asiainfo.common.abo.dao.interfaces.IABGMonBusiErrorLogDAO;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiErrorLogValue;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiLogValue;

public class ABGMonBusiErrorLogDAOImpl implements IABGMonBusiErrorLogDAO {

	public long getBeanId() throws Exception {
		return BOABGMonBusiErrorLogEngine.getNewId().longValue();
	}

	public BOABGMonBusiErrorLogBean getBeanById(long id) throws Exception {
		
		return BOABGMonBusiErrorLogEngine.getBean(id);
	}

	public void save(BOABGMonBusiErrorLogBean value) throws Exception {
		if (value.isNew() && value.getErrOrderId() <= 0)
			value.setErrOrderId(getBeanId());
		BOABGMonBusiErrorLogEngine.save(value);
	}

	@Override
	public BOABGMonBusiErrorLogBean getBeanById(long id, Date acqDate)
			throws Exception {
		StringBuilder querySb = new StringBuilder();
		Map map = new HashMap();
		querySb.append(IBOABGMonBusiErrorLogValue.S_ErrOrderId).append("= :id");
		map.put("id", id);
		map.put(IBOABGMonBusiErrorLogValue.S_AcqDate, acqDate);
		BOABGMonBusiErrorLogBean[] values = BOABGMonBusiErrorLogEngine.getBeans(querySb.toString(), map);
		if(values != null && values.length > 0)
		{
			return values[0];
		}
		return null;
	}

}
