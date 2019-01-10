package com.asiainfo.deploy.api.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.common.AIException;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.api.interfaces.IAppParamSVProvider;
import com.asiainfo.deploy.common.bo.BODeployAppParamsBean;
import com.asiainfo.deploy.common.bo.BODeployAppTemplateBean;
import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.common.ivalues.IBODeployAppTemplateValue;
import com.asiainfo.deploy.dao.interfaces.IAppParamDAO;
import com.asiainfo.deploy.dao.interfaces.IAppTemplateDAO;
import com.asiainfo.deploy.dao.interfaces.IStrategyRefAppTemplateDAO;

/**
 * 应用参数对外Api
 * 
 * @author 孙德东(24204)
 */
public class AppParamSVProviderImpl implements IAppParamSVProvider {

	private static final Pattern variablePattern = Pattern.compile("@@(\\w+)", Pattern.MULTILINE);
	@Override
	public BODeployAppParamsBean[] getAppParams(long deployStragetyId) throws Exception {
		IStrategyRefAppTemplateDAO strategyRefTemplateDao = (IStrategyRefAppTemplateDAO) ServiceFactory.getService(IStrategyRefAppTemplateDAO.class);
		// 获取模板id
		long templateId = strategyRefTemplateDao.getTemplateIdByStrategyId(deployStragetyId);
		// 取到模板对象
		IAppTemplateDAO templateDao = (IAppTemplateDAO) ServiceFactory.getService(IAppTemplateDAO.class);
		BODeployAppTemplateBean template = templateDao.getAppTemplateById(templateId);

		String scriptContent = template.getContents();
		if (StringUtils.isEmpty(scriptContent)) {
			return new BODeployAppParamsBean[0];
		}

		List<BODeployAppParamsBean> list = new ArrayList<BODeployAppParamsBean>();
		list.addAll(getParams(scriptContent));

		return list.toArray(new BODeployAppParamsBean[0]);
	}

	private List<BODeployAppParamsBean> getParams(String str) throws AIException {
		List<BODeployAppParamsBean> list = new ArrayList<BODeployAppParamsBean>();

		if (StringUtils.isEmpty(str))
			return list;

		Set<String> set = new HashSet<String>();
		Matcher matcher = variablePattern.matcher(str);
		while (matcher.find()) {
			set.add(matcher.group(1));
		}

		// 排除内置参数
		set.remove(DeployConstants.InnerScriptVariables.SEARCH_CONTENT);
		set.remove(DeployConstants.InnerScriptVariables.SERVER_NAME);
		set.remove(DeployConstants.InnerScriptVariables.USER_NAME);
		set.remove(DeployConstants.InnerScriptVariables.HOME_DIR);
		set.remove(DeployConstants.InnerScriptVariables.BIN_DIR);
		set.remove(DeployConstants.InnerScriptVariables.SBIN_DIR);
		set.remove(DeployConstants.InnerScriptVariables.LOG_DIR);
		set.remove(DeployConstants.InnerScriptVariables.CUSTOM_PARAMS);

		for (String s : set) {
			BODeployAppParamsBean one = new BODeployAppParamsBean();
			one.setKey(s);
			list.add(one);
		}
		return list;
	}

	@Override
	public BODeployAppParamsBean[] getConfigedAppParams(long applicationId) throws Exception {
		IAppParamDAO appParamDao = (IAppParamDAO) ServiceFactory.getService(IAppParamDAO.class);
		return appParamDao.getInstancedPramsByAppId(applicationId);
	}

	@Override
	public void saveAppParams(BODeployAppParamsBean[] beans) throws Exception {
		IAppParamDAO appParamDao = (IAppParamDAO) ServiceFactory.getService(IAppParamDAO.class);
		appParamDao.batchSave(beans);
	}

	@Override
	public BODeployAppParamsBean getAppParam(long applicationParamId) throws Exception {
		IAppParamDAO appParamDao = (IAppParamDAO) ServiceFactory.getService(IAppParamDAO.class);
		return appParamDao.getAppParam(applicationParamId);
	}

	@Override
	public boolean isExistKey(long applicationId, String key) throws Exception {
		IAppParamSVProvider sv = (IAppParamSVProvider) ServiceFactory.getService(IAppParamSVProvider.class);
		BODeployAppParamsBean[] beans = sv.getConfigedAppParams(applicationId);
		for (int i = 0; i < beans.length; i++) {
			if (key.equals(beans[i].getKey())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean copyAppParams(long srcAppId, long dstAppId) throws Exception {
		IAppParamDAO appParamDao = (IAppParamDAO) ServiceFactory.getService(IAppParamDAO.class);

		BODeployAppParamsBean[] srcBeans = getConfigedAppParams(srcAppId);
		for (BODeployAppParamsBean src : srcBeans) {
			src.setStsToNew();
			src.setApplicationId(dstAppId);
			src.setApplicationParamId(appParamDao.getBeanId());
		}
		appParamDao.batchSave(srcBeans);
		return true;
	}

	@Override
	public BODeployAppTemplateBean[] getAllAppTemplate() throws Exception {
		IAppTemplateDAO templateDao = (IAppTemplateDAO) ServiceFactory.getService(IAppTemplateDAO.class);
		return templateDao.getAllAppTemplate();
	}

	@Override
	public BODeployAppTemplateBean getAppTemplateById(long templateId) throws Exception {
		IAppTemplateDAO templateDao = (IAppTemplateDAO) ServiceFactory.getService(IAppTemplateDAO.class);
		return templateDao.getAppTemplateById(templateId);
	}

	@Override
	public void deleteAppTemplate(long templateId) throws Exception {
		IAppTemplateDAO templateDao = (IAppTemplateDAO) ServiceFactory.getService(IAppTemplateDAO.class);
		templateDao.deleteAppTemplate(templateId);
	}

	@Override
	public void saveAppTemplate(IBODeployAppTemplateValue value) throws Exception {
		IAppTemplateDAO templateDao = (IAppTemplateDAO) ServiceFactory.getService(IAppTemplateDAO.class);
		templateDao.saveAppTemplate(value);
	}

	@Override
	public void delete(BODeployAppParamsBean bean) throws Exception {
		IAppParamDAO paramDAO = (IAppParamDAO) ServiceFactory.getService(IAppParamDAO.class);
		paramDAO.deleteAppParam(bean);
	}
}
