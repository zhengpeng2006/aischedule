package com.asiainfo.deploy.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.deploy.api.interfaces.IAppOperationSVProvider;
import com.asiainfo.deploy.app.operation.TemplateScriptGenerator;
import com.asiainfo.deploy.authenticate.OperationAuthenticator;
import com.asiainfo.deploy.common.bo.BODeployAppParamsBean;
import com.asiainfo.deploy.common.constants.Category.OperationType;
import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.common.constants.PatternConstants;
import com.asiainfo.deploy.dao.interfaces.IAppParamDAO;
import com.asiainfo.deploy.exception.ErrorCode;
import com.asiainfo.deploy.exception.ErrorCode.RemoteExecute;
import com.asiainfo.deploy.exception.ExecuteResult;
import com.asiainfo.deploy.execute.remoteshell.RemoteCmdExecUtils;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.service.ExternalSvUtil;
import com.asiainfo.schedule.core.po.ServerBean;
import com.asiainfo.schedule.core.utils.Constants.ServerType;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;
import com.asiainfo.schedule.util.HeartBeatException;
import com.asiainfo.socket.client.ClientUtils;
import com.asiainfo.socket.future.OperationFuture;
import com.asiainfo.socket.pool.SocketConstants.Cmd;
import com.asiainfo.socket.pool.SocketConstants.Common;
import com.asiainfo.socket.request.Request;
import com.asiainfo.socket.request.RequestConstructor;

/**
 * 提供应用启动、停止、监控操作
 *
 * @author 孙德东(24204)
 */
public class AppOperationSVProviderImpl implements IAppOperationSVProvider {

	private static final Log LOG = LogFactory.getLog(AppOperationSVProviderImpl.class);

	@Override
	public void startViaSSH(String appCode) throws Exception {
		// 20150304 增加进程心跳逻辑的校验。当心跳存在时，不允许重复启动。
		ISchedulerSV schedSV = (ISchedulerSV) ServiceFactory.getService(ISchedulerSV.class);
		ServerBean s = schedSV.getRegistServerInfo(appCode, ServerType.processor);
		if (s != null) {
			throw new HeartBeatException("appcode:" + appCode + " start error.存在心跳信息,请操作员20秒后重试。");
		}
		s = schedSV.getRegistServerInfo(appCode, ServerType.manager);
		if (s != null) {
			throw new HeartBeatException("appcode:" + appCode + " start error.存在心跳信息,请操作员20秒后重试。");
		}

		// 启动
		ExecuteResult result = go(appCode, OperationType.START);
		// 失败，抛出异常
		if (!result.isSuccess()) {
			throw new Exception("" + result.getErrorCode());
		}

		// 判断是否已经启动，不允许重复启停
		// 20150304：执行结果不能保证一定是准确的，没有必要。批量启动时对性能损耗较大。
		// if (!isRunningViaSSH(appCode)) {
		// LOG.error("application appcode:" + appCode + " start error.");
		// throw new Exception("application appcode:" + appCode + " start error.return:" + result.getMessage());
		// }
	}

	@Override
	public void stopViaSSH(String appCode) throws Exception {
		ExecuteResult result = go(appCode, OperationType.STOP);
		if (result.isSuccess() || result.getErrorCode() == ErrorCode.RemoteExecute.EXEC_CMD_ERROR) {
			return;
		}

		/**
		 * pkill命令返回码的含义 0 One or more processes matched the criteria. 1 No processes matched. 2 Syntax error in the command line. 3 Fatal error: out of memory
		 * etc.
		 */
		throw new Exception("" + result.getErrorCode());
	}

	@Override
	public boolean isRunningViaSSH(String appCode) throws Exception {
		ExecuteResult result = go(appCode, OperationType.MONITOR);

		/**
		 * pgrep命令返回码的含义 0 One or more processes matched the criteria. 1 No processes matched. 2 Syntax error in the command line. 3 Fatal error: out of memory
		 * etc.
		 */
		String message = StringUtils.trim((String) result.getMessage());
		// 命令执行成功通常已经意味着程序已经在运行了，这里再增加对输出的判断
		if (result.isSuccess()) {
			return StringUtils.isEmpty(message) ? false : true;
		}

		boolean isRunning = true;
		// 执行命令失败（退出码不为0），可能是程序没有在运行（即没有结果）
		if (result.getErrorCode() == ErrorCode.RemoteExecute.EXEC_CMD_ERROR) {
			String exitCode = StringUtils.substring(message, 0, StringUtils.indexOf(message, ","));
			if (Integer.valueOf(exitCode) == 1) {
				isRunning = false;
			} else {
				throw new Exception("" + result.getErrorCode());
			}
		} else {
			throw new Exception("" + result.getErrorCode());
		}
		return isRunning;
	}

	private ExecuteResult go(String appCode, OperationType oType) throws Exception {
		// 认证失败
		if (!OperationAuthenticator.authenticate()) {
			return ExecuteResult.errorResult(ErrorCode.Auth.AUTH_FAIL, "authentication error.");
		}

		// 根据appcode获取节点信息
		Map<String, String> node = null;
		try {
			node = ExternalSvUtil.qryAppInfoByCondition(appCode, CommonConst.CON_TYPE_SSH);
			if (node == null || node.isEmpty()) {
				throw new Exception("result is null.");
			}
		} catch (Exception e) {
			return ExecuteResult.errorResult(ErrorCode.FetchInfoFromMonitor.FETCH_NODE_ERROR, "get node information from monitor error.");
		}

		// 获取模板脚本
		String templateScript = getTemplateScript(node, oType);

		// 获取实例化参数值
		Map<String, String> values = new HashMap<String, String>();
		switch (oType) {
			case START :
				values.putAll(fetchInstancedAppParams(node));
				values.putAll(setNodeRelatedParams(appCode, node));
				break;
			case STOP :
				values.putAll(fetchInstancedAppParams(node));
				values.putAll(setNodeRelatedParams(appCode, node));
				break;
			default :
				values.putAll(setNodeRelatedParams(appCode, node));
				break;
		}

		// 获取变量替换后的启动脚本
		String finalScript = getFinalScript(templateScript, values);

		// 执行脚本
		String userName = node.get(DeployConstants.MapKey.KEY_USER_NAME);
		String passwd = node.get(DeployConstants.MapKey.KEY_USER_PASSWD);
		String hostIP = node.get(DeployConstants.MapKey.KEY_HOST_IP);
		String port = node.get(CommonConst.CON_TYPE_SSH);
		return RemoteCmdExecUtils.execRomoteCmd(userName, passwd, hostIP, port, finalScript);
	}

	/**
	 * 获取模板脚本
	 *
	 * @return
	 * @throws Exception
	 */
	private String getTemplateScript(Map<String, String> node, OperationType oType) throws Exception {
		String templateScript = StringUtils.EMPTY;
		switch (oType) {
			case MONITOR :
				templateScript = TemplateScriptGenerator.monitor();
				break;
			case STOP :
				long stopNodeId = Long.valueOf(node.get(DeployConstants.MapKey.KEY_NODE_ID));
				templateScript = TemplateScriptGenerator.stop(stopNodeId);
				break;
			case START :
				long nodeId = Long.valueOf(node.get(DeployConstants.MapKey.KEY_NODE_ID));
				templateScript = TemplateScriptGenerator.start(nodeId);
				break;
			default :
				LOG.error("unknown application operation type.");
				templateScript = StringUtils.EMPTY;
				break;
		}

		if (StringUtils.isEmpty(templateScript)) {
			LOG.error("no template script is fetched.");
			throw new Exception("" + ErrorCode.FetchInfoFromMonitor.TEMPLATE_SCRIPT_IS_EMPTY);
		}

		return templateScript;
	}

	/**
	 * 获取bin目录，用来保存脚本
	 *
	 * @param node
	 * @return
	 * @throws Exception
	 */
	/*
	 * private String getBinDir(Map<String, String> node) throws Exception { long nodeId = Long.valueOf(node.get(DeployConstants.MapKey.KEY_NODE_ID)); //
	 * node->strategy INodeRefDeployStrategyDAO nodeRefStrategyDao = (INodeRefDeployStrategyDAO) ServiceFactory .getService(INodeRefDeployStrategyDAO.class);
	 * long strategyId = nodeRefStrategyDao.getStrategyIdByNodeId(nodeId);
	 * 
	 * IDeployStrategyDAO dao = (IDeployStrategyDAO)ServiceFactory.getService(IDeployStrategyDAO.class); BODeployStrategyBean bean =
	 * dao.getStrategyById(strategyId); String home = bean.getClientHomePath(); String bin = bean.getClientBinPath();
	 * 
	 * return DeployUtils.constructAbsoluteDirWithSlash(home, bin); }
	 */
	/**
	 * 设置应用实例化参数
	 *
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private Map<String, String> fetchInstancedAppParams(Map<String, String> node) throws NumberFormatException, Exception {
		Map<String, String> values = new HashMap<String, String>();
		String appId = node.get(DeployConstants.MapKey.KEY_SERVER_ID);
		if (StringUtils.isEmpty(appId)) {
			LOG.error("fetched application id is empty.");
			throw new Exception("" + ErrorCode.FetchInfoFromMonitor.FETCH_APP_ERROR);
		}

		// 根据appid,获取应用的实例化参数
		BODeployAppParamsBean[] appParams = null;
		IAppParamDAO appParamDao = (IAppParamDAO) ServiceFactory.getService(IAppParamDAO.class);
		appParams = appParamDao.getInstancedPramsByAppId(Long.valueOf(appId));

		List<String> customParams = new ArrayList<String>();
		// 应用模板参数替换
		if (appParams != null && appParams.length > 0) {
			for (BODeployAppParamsBean oneParam : appParams) {
				// 获取参数类型 T C
				if (StringUtils.endsWithIgnoreCase("C", oneParam.getParamType())) {
					// 自定义参数以key=value的格式传递给脚本
					customParams.add(oneParam.getKey() + "=" + oneParam.getValue());
				} else {
					values.put(oneParam.getKey(), oneParam.getValue());
				}
			}
		}

		// 为结算开放一个自定义参数
		if (!customParams.isEmpty()) {
			values.put(DeployConstants.InnerScriptVariables.CUSTOM_PARAMS, "'" + StringUtils.join(customParams, "|") + "'");
		}
		return values;
	}

	/**
	 * 内置的参数
	 *
	 * @return
	 */
	private Map<String, String> setNodeRelatedParams(String appCode, Map<String, String> node) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(DeployConstants.InnerScriptVariables.USER_NAME, node.get(DeployConstants.MapKey.KEY_USER_NAME));
		map.put(DeployConstants.InnerScriptVariables.SERVER_NAME, appCode);

		// map.put(DeployConstants.InnerScriptVariables.SEARCH_CONTENT, "appframe.server.name=" + appCode);
		// C++版本的应用是没有appframe.server.name的，所以这个地方只根据appcode判断应用是否存在
		map.put(DeployConstants.InnerScriptVariables.SEARCH_CONTENT, appCode);
		return map;
	}

	/**
	 * 获取变量替换后的脚本
	 *
	 * @param cachedScript
	 * @param values
	 * @param oType
	 * @return
	 * @throws Exception
	 */
	private String getFinalScript(String templateScript, Map<String, String> values) throws Exception {

		String finalScript = StringUtils.EMPTY;
		try {
			finalScript = variableReplace(templateScript, values);
		} catch (Exception e) {
			LOG.error("exception occurs when replce variables of template script.");
			throw new Exception("" + ErrorCode.FetchInfoFromMonitor.PARAMS_REPLACE_ERROR);
		}

		if (StringUtils.isEmpty(finalScript)) {
			LOG.error("final script is empty.");
			throw new Exception("" + ErrorCode.FetchInfoFromMonitor.FINAL_SCRIPT_IS_EMPTY);
		}

		return finalScript;
	}

	/**
	 * 使用正则表达式进行模板中的变量替换
	 *
	 * @param template
	 * @param values
	 * @return
	 * @throws Exception
	 */
	private String variableReplace(String template, Map<String, String> values) throws Exception {
		if (values == null || values.isEmpty()) {
			return template;
		}

		// 查找变量
		Set<String> variables = new HashSet<String>();
		Matcher matcher = PatternConstants.variablePattern.matcher(template);
		while (matcher.find()) {
			variables.add(matcher.group(1));
		}

		// 判断变量所需要的值是否全部提供
		Set<String> keys = values.keySet();
		if (!keys.containsAll(variables)) {
			LOG.error("not all variables have instanced value.");
			throw new Exception("" + ErrorCode.FetchInfoFromMonitor.FINAL_SCRIPT_IS_EMPTY);
		}

		// 变量替换
		for (String oneVariable : variables) {
			String value = values.get(oneVariable);
			template = StringUtils.replace(template, DeployConstants.InnerScriptVariables.VAR_SEPARATOR + oneVariable, value);
		}

		return template;
	}

	private Map<String, JSONObject> goViaSocket(List<String> appCodeList, OperationType oType) throws Exception {
		// 根据appcode获取节点信息
		Map<String, Map<String, String>> infos = ExternalSvUtil.batchQryAppInfo(appCodeList);
		if (infos == null || infos.isEmpty()) {
			LOG.error("应用编码列表为空。");
			return null;
		}

		List<OperationFuture> futures = new LinkedList<OperationFuture>();
		Map<String, JSONObject> rtns = new HashMap<String, JSONObject>();

		Set<Map.Entry<String, Map<String, String>>> entries = infos.entrySet();
		for (Map.Entry<String, Map<String, String>> entry : entries) {
			String appCode = entry.getKey();
			Map<String, String> node = entry.getValue();
			try {
				// 获取模板脚本
				String templateScript = getTemplateScript(node, oType);

				// 获取实例化参数值
				Map<String, String> values = new HashMap<String, String>();
				switch (oType) {
					case START :
						values.putAll(fetchInstancedAppParams(node));
						values.putAll(setNodeRelatedParams(appCode, node));
						break;
					default :
						values.putAll(setNodeRelatedParams(appCode, node));
						break;
				}

				// 获取变量替换后的启动脚本
				String finalScript = getFinalScript(templateScript, values);
				LOG.debug("exec script, finalScript is : " + finalScript);

				Request req = RequestConstructor.constructCmdRequest(node.get(DeployConstants.MapKey.KEY_HOST_IP), finalScript, appCode);
				futures.add(ClientUtils.asyncRequest(req));
			} catch (Exception e) {
				LOG.error("send request error.", e);
				rtns.put(appCode, null);
			}
		}

		for (OperationFuture future : futures) {
			try {
				JSONObject rtn = future.getResult(Common.RESPONSE_TIME_OUT);
				rtns.put((String) future.getAttachment(), rtn);
			} catch (Exception e) {
				LOG.error("future get result error.", e);
				rtns.put((String) future.getAttachment(), null);
			}
		}

		return rtns;
	}

	@Override
	public Map<String, ExecuteResult> batchIsRunningViaSocket(List<String> appCodeList) throws Exception {
		Map<String, JSONObject> temps = goViaSocket(appCodeList, OperationType.MONITOR);
		if (temps == null)
			return null;

		Map<String, ExecuteResult> results = new HashMap<String, ExecuteResult>();

		Set<Map.Entry<String, JSONObject>> entries = temps.entrySet();
		for (Map.Entry<String, JSONObject> entry : entries) {
			ExecuteResult one = null;
			String appCode = entry.getKey();
			JSONObject respJson = entry.getValue();
			try {
				if (respJson == null) {
					one = ExecuteResult.errorResult(RemoteExecute.EXEC_CMD_ERROR, "result is null.");
					results.put(appCode, one);
					continue;
				}

				// 命令执行失败
				JSONObject cmdRtn = respJson.getJSONObject("RESULT");
				String exitStatus = cmdRtn.getString(Cmd.EXIT_STATUS);
				String output = cmdRtn.getString(Cmd.OUTPUT);
				if (StringUtils.equalsIgnoreCase(Cmd.ERROR, exitStatus)) {
					one = ExecuteResult.errorResult(RemoteExecute.EXEC_CMD_ERROR, output);
					results.put(appCode, one);
					continue;
				}

				// 命令执行成功，正确返回了结果
				if (StringUtils.isEmpty(output)) { // 程序没有在运行
					one = ExecuteResult.successResult(Boolean.FALSE);
				} else { // 程序在运行
					one = ExecuteResult.successResult(Boolean.TRUE);
				}
				results.put(appCode, one);
			} catch (Exception e) {
				one = ExecuteResult.errorResult(RemoteExecute.EXEC_CMD_ERROR, e.getLocalizedMessage());
				results.put(appCode, one);
			}
		}

		return results;
	}
}
