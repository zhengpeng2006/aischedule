package com.asiainfo.index.base.dao.impl;

import java.awt.image.IndexColorModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.common.bo.BOBsStaticDataBean;
import com.ai.common.bo.BOBsStaticDataEngine;
import com.ai.common.ivalues.IBOBsStaticDataValue;
import com.asiainfo.index.base.dao.interfaces.IBsStaticDataDAO;
import com.asiainfo.index.util.IndexConstants;
import com.asiainfo.index.util.IndexUtil;

public class BsStaticDataDAOImpl implements IBsStaticDataDAO{

	@Override
	public Map<String, List<String[]>> getAllEnmuValue() throws Exception {
		StringBuffer sql = new StringBuffer();
		Map parameter = new HashMap ();
		sql.append(IBOBsStaticDataValue.S_CodeType).append("= :codeType AND ")
		.append(IBOBsStaticDataValue.S_State).append("= :state");
		parameter.put("codeType", IndexConstants.STATIC_PRE);
		parameter.put("state", IndexConstants.STATE_NORMAL);
		BOBsStaticDataBean[] datas = BOBsStaticDataEngine.getBeans(sql.toString(), parameter);
		Map<String, List<String[]>> result = IndexUtil.getMapFromData(datas);
		return result;
	}
}
