package com.asiainfo.monitor.busi.web;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonI18nResourceAction extends BaseAction {

	private static transient Log log=LogFactory.getLog(AIMonI18nResourceAction.class);
	
	/**
	 * 根据key获取信息
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getI18nResourceByKey(String key) throws Exception {
		if (StringUtils.isBlank(key))
			return "";
		String str="";
		try{
			str = AIMonLocaleFactory.getResource(key);
        }catch(Exception e){
            log.error("Call AIMonI18nResourceAction's Method getI18nResourceByKey has Exception :"+e.getMessage());
        }
        return str;	
	}
}
