package com.asiainfo.deploy.installpackage;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.biz.util.Assert;
import com.asiainfo.deploy.api.interfaces.INodeRefVersionSVProvider;
import com.asiainfo.deploy.app.operation.CmdGenerator;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.deploy.common.constants.Category.FtpType;
import com.asiainfo.deploy.common.constants.Category.NodeStatus;
import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.common.utils.DeployUtils;
import com.asiainfo.deploy.common.utils.OperationUtils;
import com.asiainfo.deploy.exception.ExecuteResult;
import com.asiainfo.deploy.execute.remoteshell.RemoteCmdExecUtils;
import com.asiainfo.deploy.filetransfer.FtpFactory;
import com.asiainfo.deploy.filetransfer.Operator;
import com.asiainfo.deploy.installpackage.InstallRuleParser.InstallRule;
import com.asiainfo.deploy.installpackage.NodePublishStatusManager.NodePublishStatus;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.service.ExternalSvUtil;

/**
 * 安装应用的线程
 * 
 * @author 孙德东(24204)
 */
public class PackageInstallTask implements Runnable {
	// 节点Id
	private Long nodeId = null;
	// 版本id
	private Long versionId = null;
	private Map<String, String> node = null;
	// 加入版本信息的发布包路径
	private String versionedPkgPath = StringUtils.EMPTY;
	// 部署策略
	private BODeployStrategyBean strategy = null;
	// 安装规则
	List<InstallRule> rules = null;

	// 日志记录
	private static Log LOG = LogFactory.getLog(PackageInstallTask.class);
	// 检查应用是否运行的超时时间：3min
	// private static int CHECK_APP_RUNNING_TIMEOUT = 3;
	// 1分钟包含的毫秒数
	// private static long MILLISECONDS_PER_MIN = 60L * 1000;

	public PackageInstallTask(BODeployStrategyBean strategy, Long nodeId, Long versionId, String versionedPkgPath, List<InstallRule> rules) {
		this.strategy = strategy;
		this.nodeId = nodeId;
		this.versionId = versionId;
		this.versionedPkgPath = versionedPkgPath;
		this.rules = rules;
	}

	@Override
	public void run() {
		// 发布开始
		recordStatus(NodeStatus.BEGIN);

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
			LOG.error("not all app are stopped under node:" + nodeId);
			recordStatus(NodeStatus.WAIT_APP_STOP);
			return;
		}

		/** -- 查询节点信息 -- **/
		try {
			node = ExternalSvUtil.qryNodeRelInfoByCondition(String.valueOf(nodeId), CommonConst.CON_TYPE_SSH);
		} catch (Exception e) {
			LOG.error("get node from nodeid[" + nodeId + "] error.", e);
			recordStatus(NodeStatus.ERROR, "get node from nodeid[" + nodeId + "] error.");
			return;
		}

		/** --- 原始环境备份,生成备份/回滚脚本 --- **/
		// 生成备份命令
		String backupCmd = CmdGenerator.backup(strategy, rules);
		// 执行备份
		ExecuteResult rtn = execRemoteCommand(backupCmd);
		if (!rtn.isSuccess()) {
			LOG.error("execute back command[" + backupCmd + "] error:" + (String) rtn.getMessage());

		//注释原因：第一次备份无内容，会报错
			/*
			 * if (rtn.getErrorCode() == ErrorCode.RemoteExecute.EXEC_CMD_ERROR) { LOG.warn("not all commands[" + backupCmd + "] success:" + (String)
			 * rtn.getMessage()); } else { LOG.error("execute back command[" + backupCmd + "] error:" + (String) rtn.getMessage());
			 * recordStatus(NodeStatus.ERROR, (String) rtn.getMessage()); return; }
			 */
		}

		/** --- 安装包分发,另外包括备份脚本、解压脚本 --- **/
		recordStatus(NodeStatus.PUBLISH_INSTALL_PACKAGE);
		Operator ftpOperator = FtpFactory.getFileOperator(FtpType.SFTP);
		// 分发到主机后的路径
		String remoteFileName = DeployUtils.getFileNameFromAbsolutePath(versionedPkgPath);
		String ftpDir = DeployUtils.constructAbsoluteDirWithSlash(strategy.getClientHomePath(), DeployConstants.Common.FTP_DIR);
		String remoteFileAbsolutePath = ftpDir + remoteFileName;
		rtn = ftpOperator.put(node, versionedPkgPath, remoteFileAbsolutePath);
		if (!rtn.isSuccess()) {
			LOG.error("put file[src=" + versionedPkgPath + ",remote dst=" + remoteFileAbsolutePath + "] error:" + (String) rtn.getMessage());
			recordStatus(NodeStatus.ERROR, (String) rtn.getMessage());
			return;
		}

		/** --- 执行解压脚本 --- **/
		// 生成安装命令
		String installCmd = CmdGenerator.install(remoteFileAbsolutePath, strategy, rules);
		rtn = execRemoteCommand(installCmd);
		if (!rtn.isSuccess()) {
			LOG.error("execute install command[" + installCmd + "] error:" + (String) rtn.getMessage());

			// 11.25文件发布不全问题解决代码
			recordStatus(NodeStatus.ERROR, (String) rtn.getMessage());
			// 回滚
			rollBack();
			return;

			/*
			 * if (rtn.getErrorCode() == ErrorCode.RemoteExecute.EXEC_CMD_ERROR) { LOG.warn("not all cmmand executed success.command:" + installCmd); } else {
			 * LOG.error("execute install command[" + installCmd + "] error:" + (String) rtn.getMessage()); recordStatus(NodeStatus.ERROR, (String)
			 * rtn.getMessage()); // 回滚 rollBack(); return; }
			 */
		}

		// 检测是否分发成功
		recordStatus(NodeStatus.CHECK_INSTALL_STATUS);
		String checkCmd = CmdGenerator.check(strategy.getClientHomePath());
		rtn = execRemoteCommand(checkCmd);
		// 返回的版本号一致则成功
		if (rtn.isSuccess() && StringUtils.equals(String.valueOf(versionId), (String) rtn.getMessage())) {
			recordStatus(NodeStatus.SUCCESS);
			// 入节点版本表
			INodeRefVersionSVProvider nodeVersionDao = (INodeRefVersionSVProvider) ServiceFactory.getService(INodeRefVersionSVProvider.class);
			try {
				nodeVersionDao.saveNodeVersion(nodeId, versionId);
			} catch (Exception e) {
				LOG.error("save node version error, nodeId=" + nodeId + ",versionId:" + versionId);
			}

		} // 失败
		else {
			LOG.error("valid version error,nodeId=" + nodeId + ",versionId=" + versionId + ",valid commond error message=" + (String) rtn.getMessage());
			recordStatus(NodeStatus.ERROR, (String) rtn.getMessage());
			// 回滚
			rollBack();
		}
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

	/**
	 * 安装过程中失败，进行回滚
	 */
	private void rollBack() {
		String rollbackCmd = CmdGenerator.rollback();
		// 执行备份
		ExecuteResult rtn = execRemoteCommand(rollbackCmd);
		if (!rtn.isSuccess()) {
			LOG.error("rollback error:" + (String) rtn.getMessage());
			recordStatus(NodeStatus.ERROR, (String) rtn.getMessage());
			return;
		}

		recordStatus(NodeStatus.ROLL_BACK_SUCCESS);
	}
}
