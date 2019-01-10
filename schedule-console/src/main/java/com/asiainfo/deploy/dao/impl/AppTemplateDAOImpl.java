package com.asiainfo.deploy.dao.impl;

import java.sql.Timestamp;

import com.asiainfo.deploy.common.bo.BODeployAppTemplateBean;
import com.asiainfo.deploy.common.bo.BODeployAppTemplateEngine;
import com.asiainfo.deploy.common.ivalues.IBODeployAppTemplateValue;
import com.asiainfo.deploy.dao.interfaces.IAppTemplateDAO;

/**
 * 应用模板的数据库操作类
 * @author 孙德东(24204)
 */
public class AppTemplateDAOImpl implements IAppTemplateDAO {

	@Override
	public BODeployAppTemplateBean[] getAllAppTemplate() throws Exception {
		return BODeployAppTemplateEngine.getBeans(null, null);
	}

	@Override
	public BODeployAppTemplateBean getAppTemplateById(long templateId) throws Exception {
		return BODeployAppTemplateEngine.getBean(templateId);
	}

	@Override
	public void deleteAppTemplate(long templateId) throws Exception {
		BODeployAppTemplateBean bean = BODeployAppTemplateEngine.getBean(templateId);
		bean.delete();
		BODeployAppTemplateEngine.save(bean);
	}

	@Override
	public void saveAppTemplate(IBODeployAppTemplateValue value)
			throws Exception {
		BODeployAppTemplateBean bean = BODeployAppTemplateEngine.transfer(value);
		if (bean.isNew())
		{
			bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
			bean.setTemplateId(BODeployAppTemplateEngine.getNewId().longValue());
		}
		else
		{
			bean.setModifyTime(new Timestamp(System.currentTimeMillis()));
		}
		BODeployAppTemplateEngine.save(bean);		
	}

}
