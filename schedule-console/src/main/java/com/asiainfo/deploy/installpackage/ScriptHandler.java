package com.asiainfo.deploy.installpackage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.common.constants.PatternConstants;
import com.asiainfo.deploy.common.utils.DeployUtils;
import com.asiainfo.deploy.dao.interfaces.IAppTemplateDAO;
import com.asiainfo.deploy.dao.interfaces.IStrategyRefAppTemplateDAO;
import com.asiainfo.deploy.installpackage.ScriptCacheManager.Script;

/**
 * 处理模板脚本
 * 
 * @author 孙德东(24204)
 */
public class ScriptHandler {
	private ScriptHandler() {
	}

	/**
	 * 第一次处理，替换掉部署策略相关的内置变量(server_name此时还没有办法获取到） 并将其他变量修改为入参的形式
	 * 
	 * @param originalScript
	 * @return
	 */
	public static Script firstHandle(BODeployStrategyBean strategy, String originalScript) {
		return variableReplace(strategy.getDeployStrategyId(), originalScript, hostRelatedVariables(strategy));
	}

	public static Script firstHandle(BODeployStrategyBean strategy) throws Exception {
		// strategy->template
		IStrategyRefAppTemplateDAO strategyRefTemplateDao = (IStrategyRefAppTemplateDAO) ServiceFactory.getService(IStrategyRefAppTemplateDAO.class);
		long templateId = strategyRefTemplateDao.getTemplateIdByStrategyId(strategy.getDeployStrategyId());
		IAppTemplateDAO templateDao = (IAppTemplateDAO) ServiceFactory.getService(IAppTemplateDAO.class);
		return firstHandle(strategy, templateDao.getAppTemplateById(templateId).getContents());
	}

	public static Script firstStopHandle(BODeployStrategyBean strategy) throws Exception {
		// strategy->template
		IStrategyRefAppTemplateDAO strategyRefTemplateDao = (IStrategyRefAppTemplateDAO) ServiceFactory.getService(IStrategyRefAppTemplateDAO.class);
		long stopTemplateId = strategyRefTemplateDao.getStopTemplateIdByStrategyId(strategy.getDeployStrategyId());
		IAppTemplateDAO templateDao = (IAppTemplateDAO) ServiceFactory.getService(IAppTemplateDAO.class);
		return firstHandle(strategy, templateDao.getAppTemplateById(stopTemplateId).getContents());
	}

	/**
	 * 获取部署策略相关的内置变量
	 * 
	 * @param strategy
	 * @return
	 */
	private static Map<String, String> hostRelatedVariables(BODeployStrategyBean strategy) {
		String homeDir = strategy.getClientHomePath();
		String binDir = strategy.getClientBinPath();
		String sbinDir = strategy.getClientSbinPath();
		String logDir = strategy.getClientLogPath();

		Map<String, String> map = new HashMap<String, String>();
		map.put(DeployConstants.InnerScriptVariables.HOME_DIR, homeDir);
		map.put(DeployConstants.InnerScriptVariables.BIN_DIR, DeployUtils.constructAbsoluteDirWithoutSlash(homeDir, binDir));
		map.put(DeployConstants.InnerScriptVariables.SBIN_DIR, DeployUtils.constructAbsoluteDirWithoutSlash(homeDir, sbinDir));
		map.put(DeployConstants.InnerScriptVariables.LOG_DIR, DeployUtils.constructAbsoluteDirWithoutSlash(homeDir, logDir));

		return map;
	}

	/**
	 * 使用正则表达式进行模板中的变量替换
	 * 
	 * @param template
	 * @param values
	 * @return
	 * @throws Exception
	 */
	private static Script variableReplace(long strategyId, String template, Map<String, String> values) {
		if (values == null || values.isEmpty() || template == null || template.isEmpty()) {
			return new Script(template, null);
		}
		// 部署策略相关de内置变量
		Set<String> keys = values.keySet();
		// 查找变量
		Matcher matcher = PatternConstants.variablePattern.matcher(template);
		InnerContainer container = new InnerContainer();
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String variable = matcher.group(1);
			if (keys.contains(variable)) {
				matcher.appendReplacement(sb, values.get(variable));
			} else {
				int temp = container.put(variable);
				// 将@@var 替换成参数变量 $index
				matcher.appendReplacement(sb, "\\$" + temp);
			}
		}
		matcher.appendTail(sb);

		Script script = new Script(sb.toString(), container);
		// 缓存起来，只有在点击主机初始化之后 暂未使用
		// ScriptCacheManager.put(strategyId, script);
		return script;
	}
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "avalue");
		map.put("b", "bvalue");

		String template = "a=@@a,b=@@b,c=@@c,b=@@b";
		System.out.println(template);
		// System.out.println(variableReplace(template, map));
	}
}
