package com.asiainfo.common.abo.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogBean;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogEngine;
import com.asiainfo.common.abo.dao.interfaces.IABGMonBusiLogDAO;
import com.asiainfo.common.abo.ivalues.IBOABGMonBusiLogValue;
import com.asiainfo.common.utils.DateUtil;
import com.asiainfo.monitor.acquire.AcquireConst;

public class ABGMonBusiLogDAOImpl implements IABGMonBusiLogDAO {

	public long getBeanId() throws Exception {
		return BOABGMonBusiLogEngine.getNewId().longValue();
	}

	public BOABGMonBusiLogBean getBeanById(long id) throws Exception {
		return BOABGMonBusiLogEngine.getBean(id);
	}

	public void save(BOABGMonBusiLogBean value) throws Exception {
		if (value.isNew() && value.getThroughputId() <= 0)
			value.setThroughputId(getBeanId());
		BOABGMonBusiLogEngine.save(value);
	}
	
	@Override
	public BOABGMonBusiLogBean getBeanById(long id, Date acqDate)
			throws Exception {
		StringBuilder querySb = new StringBuilder();
		Map map = new HashMap();
		querySb.append(IBOABGMonBusiLogValue.S_ThroughputId).append("= :id");
		map.put("id", id);
		map.put(IBOABGMonBusiLogValue.S_CreateDate, acqDate);
		BOABGMonBusiLogBean[] values = BOABGMonBusiLogEngine.getBeans(querySb.toString(), map);
		if(values != null && values.length > 0)
		{
			return values[0];
		}
		return null;
	}
	
//	public BOABGMonBusiLogBean[] get() throws Exception {
//		/* StringBuffer sqlStr = new StringBuffer();
//	        sqlStr.append(" 1 = 1 AND SERVER_CODE = :SERVER_CODE ");
//	        sqlStr.append(" AND MON_FLAG = :MON_FLAG");
//
//	        Map<String, Object> paramMap = new HashMap<String, Object>();
//	        paramMap.put("SERVER_CODE", "MON-EXE");
//	        paramMap.put("MON_FLAG", "N");
//	        paramMap.put("CREATE_DATE", DateUtil.getNowDate());*/
//	        
//	        BOABGMonBusiLogBean bean = new BOABGMonBusiLogBean();
//			//bean.setServerCode("MON-EXE");
//			//bean.setCreateDate("201409");
//			bean.initCreateDate("201409");
//			bean.initServerCode("MON-EXE");
//			
//	        return BOABGMonBusiLogEngine.getBeans(bean);
//        
//	}
}
