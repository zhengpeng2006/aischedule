package com.asiainfo.deploy.dao.interfaces;

import com.asiainfo.deploy.common.bo.BODeployAppTemplateBean;
import com.asiainfo.deploy.common.ivalues.IBODeployAppTemplateValue;

/**
 * 应用模板数据库接口
 * @author 孙德东(24204)
 */
public interface IAppTemplateDAO 
{
	/**
	 * 获取所有的应用模板
	 * @return
	 * @throws Exception 
	 */
	BODeployAppTemplateBean[] getAllAppTemplate() throws Exception;
	BODeployAppTemplateBean getAppTemplateById(long templateId) throws Exception;
	
	/**
	 * 删除模板
	 * @param templateId
	 * @throws Exception
	 */
	void deleteAppTemplate(long templateId) throws Exception;
	
	/**
	 * 保存模板
	 * @throws Exception
	 */
	void saveAppTemplate(IBODeployAppTemplateValue value) throws Exception;
}
