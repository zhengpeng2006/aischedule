package com.asiainfo.monitor.tools.dao.interfaces;

import com.asiainfo.monitor.tools.ivalues.IBOAIMonI18nResourceValue;



public interface IAIMonI18nResourceDAO {

    /**
	 * 获得所有的语言配置数据
	 * @return IBOAIMonI18nResourceValue[]
	 * @throws Exception
	 */
    public IBOAIMonI18nResourceValue[] getI18nResource() throws Exception;
}
