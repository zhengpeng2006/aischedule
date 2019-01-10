package com.asiainfo.deploy.installpackage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.biz.util.Assert;
import com.asiainfo.deploy.app.operation.CmdGenerator;
import com.asiainfo.deploy.common.bo.BODeployAppTemplateBean;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.deploy.common.constants.Category.FtpType;
import com.asiainfo.deploy.common.constants.Category.NodeStatus;
import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.common.utils.DeployFileUtils;
import com.asiainfo.deploy.common.utils.DeployUtils;
import com.asiainfo.deploy.common.utils.OperationUtils;
import com.asiainfo.deploy.dao.interfaces.IAppTemplateDAO;
import com.asiainfo.deploy.dao.interfaces.IDeployStrategyDAO;
import com.asiainfo.deploy.exception.ExecuteResult;
import com.asiainfo.deploy.execute.remoteshell.RemoteCmdExecUtils;
import com.asiainfo.deploy.filetransfer.FtpFactory;
import com.asiainfo.deploy.filetransfer.Operator;
import com.asiainfo.deploy.installpackage.InstallRuleParser.InstallRule;
import com.asiainfo.deploy.installpackage.NodePublishStatusManager.NodePublishStatus;
import com.asiainfo.deploy.installpackage.ScriptCacheManager.Script;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.service.ExternalSvUtil;

/**
 * 执行主机初始化任务
 * 
 * @author 孙德东(24204)
 */
public class HostInitializeTask implements Runnable {
	// 日志记录
	private static Log LOG = LogFactory.getLog(HostInitializeTask.class);
	private long nodeId;
	private long deployStrategy;
	private Map<String, String> node = null;

	public HostInitializeTask(long nodeId, long deployStrategy) {
		this.nodeId = nodeId;
		this.deployStrategy = deployStrategy;
	}

	@Override
	public void run() {
		// 主机初始化开始
		recordStatus(NodeStatus.HOST_INIT_BEGIN);

		/** -- 应用是否已经停止 -- **/
		recordStatus(NodeStatus.CHECK_APP_RUNNING);
		boolean allAppsStopped = false;
		try {
			allAppsStopped = OperationUtils.checkAllAppsStoped(nodeId);
		} catch (Exception e1) {
			LOG.error("check if all applications stopped error.", e1);
			recordStatus(NodeStatus.CHECK_APP_STATUS_ERROR, "check applications status error.");
			return;
		}
		if (!allAppsStopped) {
			// 状态： 等待应用停止
			recordStatus(NodeStatus.WAIT_APP_STOP);
			LOG.error("not all app are stopped under node " + nodeId);
			return;
		}

		// 获取部署策略
		IDeployStrategyDAO strategyDao = (IDeployStrategyDAO) ServiceFactory.getService(IDeployStrategyDAO.class);
		BODeployStrategyBean strategy = null;
		try {
			strategy = strategyDao.getStrategyById(deployStrategy);
		} catch (Exception e1) {
			recordStatus(NodeStatus.ERROR, "get deploy strategy from strategyid error.");
			return;
		}

		// 获取节点信息
		try {
			node = ExternalSvUtil.qryNodeRelInfoByCondition(String.valueOf(nodeId), CommonConst.CON_TYPE_SSH);
		} catch (Exception e) {
			recordStatus(NodeStatus.ERROR, "get node from nodeid error.");
			LOG.error("get node from nodeid error.", e);
			return;
		}

		// INSTALL_RULE:文件名 存放路径 是否需要解压 压缩格式
		String installRule = strategy.getInstallRule();
		List<InstallRule> rules = null;
		try {
			rules = InstallRuleParser.parser(installRule);
		} catch (Exception e) {
			recordStatus(NodeStatus.ERROR, "unvalid install rules.");
			LOG.error("unvalid install rules.", e);
			return;
		}

		// @@主机初始化第一步：创建目录(InstallRule中的文件目录，并创建/${home}/ftpDir目录、bin、sbin)
		recordStatus(NodeStatus.HOST_INIT_MKDIR);
		List<String> dstDirs = new ArrayList<String>();
		String home = strategy.getClientHomePath();
		if (StringUtils.isNotEmpty(strategy.getClientBinPath())) {
			dstDirs.add(DeployUtils.constructAbsoluteDirWithSlash(home, strategy.getClientBinPath()));
		}
		if (StringUtils.isNotEmpty(strategy.getClientSbinPath())) {
			dstDirs.add(DeployUtils.constructAbsoluteDirWithSlash(home, strategy.getClientSbinPath()));
		}
		if (StringUtils.isNotEmpty(strategy.getClientLogPath())) {
			dstDirs.add(DeployUtils.constructAbsoluteDirWithSlash(home, strategy.getClientLogPath()));
		}
		dstDirs.add(DeployUtils.constructAbsoluteDirWithSlash(home, DeployConstants.Common.FTP_DIR)); // ftpDir存放安装包
		for (InstallRule rule : rules) {
			dstDirs.add(DeployUtils.constructAbsoluteDirWithSlash(home, rule.dst));
		}
		String dstStr = StringUtils.join(dstDirs, ",");

		Operator operator = FtpFactory.getFileOperator(FtpType.SFTP);
		// 上传主机初始化脚本
		InputStream in1 = DeployFileUtils.loadFile(DeployConstants.Publish.CHECK_HOST_SHELL_PATH);
		String dstAbsolutePath = DeployUtils.addFileSeperatorInEnd(home) + DeployConstants.Publish.CHECK_HOST_SHELL_NAME;
		ExecuteResult rtn = operator.put(node, in1, dstAbsolutePath);
		if (!rtn.isSuccess()) {
			recordStatus(NodeStatus.ERROR, (String) rtn.getMessage());
			LOG.error("upload bin script error." + (String) rtn.getMessage());
			return;
		}
		// 执行主机初始化脚本：将所有的待初始化路径传给脚本，完成创建(主机初始化不会删除目录下面的文件，只做新建目录+上传脚本的工作)
		StringBuilder sb = new StringBuilder();
		sb.append("chmod 755 ").append(dstAbsolutePath).append("&&").append(dstAbsolutePath).append(" ").append(dstStr);
		rtn = execRemoteCommand(sb.toString());
		if (!rtn.isSuccess()) {
			recordStatus(NodeStatus.ERROR, (String) rtn.getMessage());
			LOG.error("execute checkhost script error." + (String) rtn.getMessage());
			return;
		}

		// @@主机初始化第二步：上传bin脚本（可能是自定义的脚本，存放在数据库中）
		String binPath = strategy.getClientBinPath();
		if (StringUtils.isNotEmpty(binPath)) {
			recordStatus(NodeStatus.HOST_INIT_UPLOAD_BIN);
			IAppTemplateDAO templateDao = (IAppTemplateDAO) ServiceFactory.getService(IAppTemplateDAO.class);
			try {
				BODeployAppTemplateBean template = templateDao.getAppTemplateById(strategy.getTemplateId());
				// 处理模板
				Script script = ScriptHandler.firstHandle(strategy, template.getContents());
				InputStream in = DeployFileUtils.toInputStream(script.script);
				String binDir = DeployUtils.addFileSeperatorInEnd(DeployUtils.constructAbsoluteDirWithSlash(home, binPath));
				rtn = operator.put(node, in, binDir + DeployConstants.Publish.START_SCRIPT_NAME);
				if (!rtn.isSuccess()) {
					recordStatus(NodeStatus.ERROR, (String) rtn.getMessage());
					return;
				}

				// 南京电信：新增停止模板脚本
				if (strategy.getStopTemplateId() != 0) {
					BODeployAppTemplateBean stopTemplate = templateDao.getAppTemplateById(strategy.getStopTemplateId());
					// 处理模板
					Script stopScript = ScriptHandler.firstHandle(strategy, stopTemplate.getContents());
					InputStream stopIn = DeployFileUtils.toInputStream(stopScript.script);
					rtn = operator.put(node, stopIn, binDir + DeployConstants.Publish.STOP_SCRIPT_NAME);
					if (!rtn.isSuccess()) {
						recordStatus(NodeStatus.ERROR, (String) rtn.getMessage());
						return;
					}
				}

			} catch (Exception e) {
				recordStatus(NodeStatus.ERROR, "upload bin script error:" + e.getLocalizedMessage());
				LOG.error("upload bin script error.", e);
				return;
			}
		}

		// @@主机初始化第三步：上传sbin脚本(预置在系统中，以文件的形式）
		String sbinPath = strategy.getClientSbinPath();
		if (StringUtils.isNotEmpty(sbinPath)) {
			recordStatus(NodeStatus.HOST_INIT_UPLOAD_SBIN);

			String sbinDir = DeployUtils.addFileSeperatorInEnd(DeployUtils.constructAbsoluteDirWithSlash(home, sbinPath));
			List<InputStream> srcList = new ArrayList<InputStream>();
			List<String> dstList = new ArrayList<String>();
			srcList.add(DeployFileUtils.loadFile(DeployConstants.SbinScripts.DIR + DeployConstants.SbinScripts.CRONOLOG));
			dstList.add(sbinDir + DeployConstants.SbinScripts.CRONOLOG);
			srcList.add(DeployFileUtils.loadFile(DeployConstants.SbinScripts.DIR + DeployConstants.SbinScripts.MONITOR_PROCESS));
			dstList.add(sbinDir + DeployConstants.SbinScripts.MONITOR_PROCESS);
			srcList.add(DeployFileUtils.loadFile(DeployConstants.SbinScripts.DIR + DeployConstants.SbinScripts.SET_ENV));
			dstList.add(sbinDir + DeployConstants.SbinScripts.SET_ENV);

			rtn = operator.batchPut(node, srcList, dstList);
			if (!rtn.isSuccess()) {
				recordStatus(NodeStatus.ERROR, (String) rtn.getMessage());
				return;
			}
		}

		// 修改已经上传的脚本的权限
		rtn = execRemoteCommand(CmdGenerator.chmod(strategy));
		if (!rtn.isSuccess()) {
			recordStatus(NodeStatus.ERROR, (String) rtn.getMessage());
			return;
		}

		// 初始化完成
		recordStatus(NodeStatus.HOST_INIT_SUCCESS);
	}

	/**
	 * 远程执行命令
	 * 
	 * @param cmd
	 * @return
	 */
	private ExecuteResult execRemoteCommand(String cmd) {
		Assert.notNull(node);

		String userName = node.get(DeployConstants.MapKey.KEY_USER_NAME);
		String passwd = node.get(DeployConstants.MapKey.KEY_USER_PASSWD);
		String hostIP = node.get(DeployConstants.MapKey.KEY_HOST_IP);
		String port = node.get(DeployConstants.MapKey.KEY_CON_TYPE_SSH);
		if (StringUtils.isEmpty(port)) {
			port = String.valueOf(DeployConstants.JSch.DEFAULT_SSH_PROT);
		}
		return RemoteCmdExecUtils.execRomoteCmd(userName, passwd, hostIP, port, cmd);
	}

	private void recordStatus(NodeStatus status) {
		recordStatus(status, null);
	}

	private void recordStatus(NodeStatus status, String message) {
		if (StringUtils.isEmpty(message)) {
			NodePublishStatusManager.setNodeStatus(nodeId, new NodePublishStatus(status));
		} else {
			NodePublishStatusManager.setNodeStatus(nodeId, new NodePublishStatus(status, message));
		}
	}
}
