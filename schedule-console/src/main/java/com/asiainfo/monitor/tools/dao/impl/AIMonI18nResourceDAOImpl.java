package com.asiainfo.monitor.tools.dao.impl;


import com.asiainfo.monitor.tools.bo.BOAIMonI18nResourceEngine;
import com.asiainfo.monitor.tools.dao.interfaces.IAIMonI18nResourceDAO;
import com.asiainfo.monitor.tools.ivalues.IBOAIMonI18nResourceValue;

public class AIMonI18nResourceDAOImpl implements IAIMonI18nResourceDAO{
	
	public IBOAIMonI18nResourceValue[] getI18nResource() throws Exception {
		return BOAIMonI18nResourceEngine.getBeans(" state = 'U' ", null);
	}
}
