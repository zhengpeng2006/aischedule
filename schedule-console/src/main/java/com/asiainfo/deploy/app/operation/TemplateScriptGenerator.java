package com.asiainfo.deploy.app.operation;

import java.util.Collection;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.common.utils.DeployUtils;
import com.asiainfo.deploy.dao.interfaces.IDeployStrategyDAO;
import com.asiainfo.deploy.dao.interfaces.INodeRefDeployStrategyDAO;
import com.asiainfo.deploy.installpackage.InnerContainer;
import com.asiainfo.deploy.installpackage.ScriptCacheManager;
import com.asiainfo.deploy.installpackage.ScriptCacheManager.Script;
import com.asiainfo.deploy.installpackage.ScriptHandler;

/**
 * 生成脚本
 * 
 * @author 孙德东(24204)
 */
public class TemplateScriptGenerator {
	private TemplateScriptGenerator() {
	}

	/**
	 * 启动脚本模板需要从数据库中获取 sh /xxx/bin/start.sh a b c
	 * 
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public static String start(long nodeId) throws Exception {
		// node->strategy
		INodeRefDeployStrategyDAO nodeRefStrategyDao = (INodeRefDeployStrategyDAO) ServiceFactory.getService(INodeRefDeployStrategyDAO.class);
		long strategyId = nodeRefStrategyDao.getStrategyIdByNodeId(nodeId);
		// 获取部署策略
		IDeployStrategyDAO strategyDao = (IDeployStrategyDAO) ServiceFactory.getService(IDeployStrategyDAO.class);
		BODeployStrategyBean strategy = strategyDao.getStrategyById(strategyId);

		// 启动命令
		String homeDir = strategy.getClientHomePath();
		String binDir = strategy.getClientBinPath();
		StringBuilder rtn = new StringBuilder();
		rtn.append(DeployUtils.constructAbsoluteDirWithSlash(homeDir, binDir)).append(DeployConstants.Operation.START_SHELL_NAME);

		Script script = getStart(strategy);
		InnerContainer paramContainer = script.container;
		if (paramContainer != null) {
			Collection<String> params = paramContainer.values();
			if (params != null && params.size() > 0) {
				for (String param : params)
					rtn.append(" @@" + param);
			}
		}

		rtn.append(" >& /dev/null &");// 后台运行，关闭channel
		return rtn.toString();
	}

	private static Script getStart(BODeployStrategyBean strategy) throws Exception {
		Script s = ScriptCacheManager.get(strategy.getDeployStrategyId());
		if (s == null) {
			s = ScriptHandler.firstHandle(strategy);
		}

		return s;
	}

	private static Script getStop(BODeployStrategyBean strategy) throws Exception {
		return ScriptHandler.firstStopHandle(strategy);

	}

	public static String monitor() {
		StringBuilder sb = new StringBuilder();
		// pgrep -u sweepmonk2014 -f "appcode=a b"
		// cshell会启动一个新shell来执行远程命令，所以会返回两条，这里过滤掉csh
		/*
		 * sb.append("pgrep -u ").append(DeployConstants.InnerScriptVariables.VAR_SEPARATOR + DeployConstants.InnerScriptVariables.USER_NAME).append(" -l ")
		 * .append(" -f ").append(DeployConstants.InnerScriptVariables.VAR_SEPARATOR + DeployConstants.InnerScriptVariables.SEARCH_CONTENT)
		 * .append("|grep -v csh");
		 */

		// 不适用pgrep,在HP-UX机器上不正确
		sb.append("ps -ef|grep ").append(DeployConstants.InnerScriptVariables.VAR_SEPARATOR + DeployConstants.InnerScriptVariables.SEARCH_CONTENT)
				.append("|grep ").append(DeployConstants.InnerScriptVariables.VAR_SEPARATOR + DeployConstants.InnerScriptVariables.USER_NAME)
				.append("|grep -v grep|awk '{print $2}'");

		return sb.toString();
	}

	public static String stop(long nodeId) throws Exception {
		// node->strategy 新增：南京电信，停止脚本调用
		INodeRefDeployStrategyDAO nodeRefStrategyDao = (INodeRefDeployStrategyDAO) ServiceFactory.getService(INodeRefDeployStrategyDAO.class);
		long strategyId = nodeRefStrategyDao.getStrategyIdByNodeId(nodeId);
		// 获取部署策略
		IDeployStrategyDAO strategyDao = (IDeployStrategyDAO) ServiceFactory.getService(IDeployStrategyDAO.class);
		BODeployStrategyBean strategy = strategyDao.getStrategyById(strategyId);

		if (strategy != null && strategy.getStopTemplateId() != 0) {
			// 停止命令
			String homeDir = strategy.getClientHomePath();
			String binDir = strategy.getClientBinPath();
			StringBuilder rtn = new StringBuilder();
			rtn.append(DeployUtils.constructAbsoluteDirWithSlash(homeDir, binDir)).append(DeployConstants.Operation.STOP_SHELL_NAME);

			Script script = getStop(strategy);
			InnerContainer paramContainer = script.container;
			if (paramContainer != null) {
				Collection<String> params = paramContainer.values();
				if (params != null && params.size() > 0) {
					for (String param : params)
						rtn.append(" @@" + param);
				}
			}

			rtn.append(" >& /dev/null &");// 后台运行，关闭channel
			return rtn.toString();
		}

		StringBuilder sb = new StringBuilder();
		sb.append(monitor()).append("|xargs kill -9");
		// 不使用pkill
		// sb.append("pkill -u ").append(DeployConstants.InnerScriptVariables.VAR_SEPARATOR +
		// DeployConstants.InnerScriptVariables.USER_NAME).append(" -f ").append(DeployConstants.InnerScriptVariables.VAR_SEPARATOR +
		// DeployConstants.InnerScriptVariables.SEARCH_CONTENT);
		return sb.toString();
	}
}
