package com.asiainfo.deploy.api.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.api.interfaces.IPackageDispatcherSVProvider;
import com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelationBean;
import com.asiainfo.deploy.common.bo.BODeployNodeVersionBean;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.deploy.common.bo.BODeployVersionBean;
import com.asiainfo.deploy.common.constants.Category.FtpType;
import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.common.constants.DeployConstants.MapKey;
import com.asiainfo.deploy.common.utils.CompressUtils;
import com.asiainfo.deploy.common.utils.DeployUtils;
import com.asiainfo.deploy.dao.interfaces.IDeployStrategyDAO;
import com.asiainfo.deploy.dao.interfaces.INodeRefDeployStrategyDAO;
import com.asiainfo.deploy.dao.interfaces.INodeRefVersionDAO;
import com.asiainfo.deploy.dao.interfaces.IVersionDAO;
import com.asiainfo.deploy.exception.ErrorCode;
import com.asiainfo.deploy.exception.ExecuteResult;
import com.asiainfo.deploy.filetransfer.FtpFactory;
import com.asiainfo.deploy.filetransfer.Operator;
import com.asiainfo.deploy.installpackage.HostInitializeTask;
import com.asiainfo.deploy.installpackage.InstallRuleParser;
import com.asiainfo.deploy.installpackage.InstallRuleParser.InstallRule;
import com.asiainfo.deploy.installpackage.PackageInstallTask;
import com.asiainfo.deploy.installpackage.PackageManager;
import com.asiainfo.deploy.version.VersionManager;

/**
 * 提供版本包分发的实现类
 * 
 * @author 孙德东(24204)
 */
public class PackageDispatcherSVProviderImpl implements IPackageDispatcherSVProvider {
	private static transient Log LOG = LogFactory.getLog(PackageDispatcherSVProviderImpl.class);
    //系统中最多同时存在两个版本
	private static final int MAX_VERSIONS = 2;
	
	@Override
	public void hostInitialization(List<Long> nodeList, long deployStrategy) throws Exception {
		ExecutorService threadPool = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors() + 1);
		for (Long nodeId : nodeList) {
			threadPool.execute(new HostInitializeTask(nodeId, deployStrategy));
		}
		// 不允许提交新任务
		threadPool.shutdown();
	}
	
	@Override
	public void install(List<Long> nodeList, long versionId) throws Exception {
		// 验证节点的 部署策略是否一致
		long deployStrategy = validAllNodesHaveSaveStrategy(nodeList);
		// 验证版本
		validVersion(deployStrategy, versionId);
		// 开始安装
		executeInstall(nodeList, deployStrategy, versionId);
	}
	
	@Override
	public void unifyToCurrentVersion(long deployStrategy) throws Exception {
		IVersionDAO versionDao = (IVersionDAO)ServiceFactory.getService(IVersionDAO.class);
		long currentVersion = versionDao.currentVersion(deployStrategy).getVersionId();
		
		/**--- 判断哪些节点需要升级 ---**/
		List<Long> nodeList = fetchNodesUnderStrategy(deployStrategy);
		if (nodeList == null || nodeList.isEmpty()) {
			LOG.debug("no nodes to unify to current versioin.");
			return;
		}
		
		INodeRefVersionDAO nodeRefVersionDao = (INodeRefVersionDAO) ServiceFactory.getService(INodeRefVersionDAO.class);
		BODeployNodeVersionBean[] versions = nodeRefVersionDao.qryNodeVersionsByNodes(nodeList);
		if (versions != null && versions.length > 0) {
			for (BODeployNodeVersionBean version : versions) {
				if (version.getVersionId() == currentVersion) {
					nodeList.remove(version.getNodeId());
				}
			}
		}
		
		//升级到当前版本的步骤和回滚一致(无需远程取包）
		executeRollback(nodeList, deployStrategy, currentVersion);
	}
	
	@Override
	public void rollback(List<Long> nodeList, long versionId) throws Exception {
		// 验证节点的 部署策略是否一致
		long deployStrategy = validAllNodesHaveSaveStrategy(nodeList);
		// 验证版本
		validVersion(deployStrategy, versionId);
		// 执行回滚
		executeRollback(nodeList, deployStrategy, versionId);
	}

	/**
	 * 同一个部署策略下面的版本数不能超过MAX_VERSIONS(2)
	 * @param deployStrategy
	 * @param newVersionId
	 * @throws Exception
	 */
	private void validVersion(long deployStrategy, long newVersionId) throws Exception {
		Set<Long> versions = fetchVersionsByStrategy(deployStrategy);
		versions.add(newVersionId);
		if (versions.size() > MAX_VERSIONS) {
			LOG.error("more than two version will be in use under strategy:" + deployStrategy);
			throw new Exception("" + ErrorCode.Publish.TOO_MANY_VERSION);
		}
	}
	

	/**
	 * 安装步骤： 从ftp服务器下载包 -> 安装
	 * @param nodeList
	 * @param deployStrategy
	 * @param versionId
	 * @throws Exception 
	 */
	private void executeInstall(List<Long> nodeList, long deployStrategy, long versionId) throws Exception {
		// 部署策略
		IDeployStrategyDAO trategyDao = (IDeployStrategyDAO) ServiceFactory.getService(IDeployStrategyDAO.class);
		BODeployStrategyBean strategy = trategyDao.getStrategyById(deployStrategy);
		
		// 解析安装规则
		String installRule = strategy.getInstallRule();
		List<InstallRule> rules = InstallRuleParser.parser(installRule);
		if (rules == null || rules.isEmpty()) {
			LOG.error("install rules of " + strategy + " is empty.");
			throw new Exception("" + ErrorCode.Common.PARSE_INSTALL_RULE_ERROR);
		}
		/**--- 下载安装包 ---**/
		// ftp服务器上的路径
		String ftpDir = DeployUtils.addFileSeperatorInEnd(strategy.getFtpPackagePath());
		// 在web服务器上的保存路径(要求绝对路径）
		String serverDir = DeployUtils.addFileSeperatorInEnd(strategy.getServerPackagePath());
		// 在服务器上创建以版本号命名的临时目录
		File f = new File(serverDir);
		if (f.exists()) {
			LOG.debug(serverDir + " already existes.");
		}
		else {
			// 创建保存安装包的目录失败
			if(!f.mkdirs()) {
				LOG.error("Fatal error:create " + serverDir + " error.");
				throw new Exception("" + ErrorCode.Publish.CREATE_SERVER_DIR_ERROR);
			}
		}
		
		List<String> ftpFiles = new ArrayList<String>();
		List<String> webFiles = new ArrayList<String>();
		for (InstallRule rule : rules) {
			ftpFiles.add(ftpDir + rule.src);
			webFiles.add(serverDir + rule.src);
		}
		
		
		Operator ftpOperator = FtpFactory.getFileOperator(FtpType.getFtpType(Integer.valueOf(strategy.getFtpProtocol())));
		ExecuteResult rtn = ftpOperator.batchGet(getFtpInfo(strategy), ftpFiles, webFiles);
		if (!rtn.isSuccess()) {
			LOG.error("batch get files error." + (String)rtn.getMessage());
			throw new Exception("" + rtn.getErrorCode());
		}
		
		/**--- 将下载的安装包打包，并在其中加入版本信息 ---**/
		//生成包的路径规则：serverDir/versionId.tar.gz
		String versionedPkgDst = serverDir + versionId + DeployConstants.Common.INSTALL_PKG_SUFIX;
		CompressUtils.appendVersionToPkg(webFiles, versionId, versionedPkgDst);
		// 删除下载的安装包，保留加入版本后的包
		for (String s : webFiles) {
			new File(s).delete();
		}
		
		/**--- 启动线程池完成分发任务 ---**/
		// IO密集型任务
		ExecutorService threadPool = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors() + 1);
		for (Long nodeId : nodeList) {
			threadPool.execute(new PackageInstallTask(strategy, nodeId, versionId, versionedPkgDst, rules));
		}
		// 不允许提交新任务
		threadPool.shutdown();
		
		// 版本包管理
		PackageManager.handle(versionedPkgDst);
		// 入版本库
		VersionManager.installVersionHandler(versionId, versionedPkgDst, strategy.getDeployStrategyId(),
				strategy.getHistoryPackageNum());
	}
    
	/**
	 * 回滚步骤：使用保留的包  -> 安装
	 * @param nodeList
	 * @param deployStrategy
	 * @param versionId
	 * @throws Exception 
	 */
	private void executeRollback(List<Long> nodeList, long deployStrategy, long versionId) throws Exception {
		// 获取要回滚的安装包的路径
		IVersionDAO versionDao = (IVersionDAO)ServiceFactory.getService(IVersionDAO.class);
		BODeployVersionBean version = versionDao.getVersionById(versionId);
		String versionPath = version.getPackagePath();
		
		// 部署策略
		IDeployStrategyDAO trategyDao = (IDeployStrategyDAO) ServiceFactory.getService(IDeployStrategyDAO.class);
		BODeployStrategyBean strategy = trategyDao.getStrategyById(deployStrategy);
		// 解析安装规则
		String installRule = strategy.getInstallRule();
		List<InstallRule> rules = InstallRuleParser.parser(installRule);
		if (rules == null || rules.isEmpty()) {
			LOG.error("install rules of " + strategy + " is empty.");
			throw new Exception("" + ErrorCode.Common.PARSE_INSTALL_RULE_ERROR);
		}
		
		/** --- 获取安装包 --- **/
		// 在web服务器上的保存路径(要求绝对路径）
		String serverDir = DeployUtils.addFileSeperatorInEnd(strategy.getServerPackagePath());
		// 在服务器上创建以版本号命名的临时目录
		File f = new File(serverDir);
		if (f.exists()) {
			LOG.debug(serverDir + " already existes.");
		} else {
			// 创建保存安装包的目录失败
			if (!f.mkdirs()) {
				LOG.error("Fatal error:create " + serverDir + " error.");
				throw new Exception("" + ErrorCode.Publish.CREATE_SERVER_DIR_ERROR);
			}
		}
        PackageManager.getPkg(versionPath);

		/** --- 启动线程池完成分发任务 --- **/
		// IO密集型任务
		ExecutorService threadPool = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors() + 1);
		for (Long nodeId : nodeList) {
			threadPool.execute(new PackageInstallTask(strategy, nodeId, versionId, versionPath, rules));
		}
		// 不允许提交新任务
		threadPool.shutdown();

		// 入版本库
		VersionManager.rollbackVersionHandler(versionId, strategy.getDeployStrategyId());
	}
    

	/**
	 * 验证节点的 部署策略是否一致,如果一致返回部署策略
	 * 
	 * @return
	 * @throws Exception
	 */
	private long validAllNodesHaveSaveStrategy(List<Long> nodeList) throws Exception {
		if (nodeList == null || nodeList.isEmpty()) {
			LOG.error("no node is found.");
			throw new Exception("" + ErrorCode.Publish.NO_NODE_IS_FOUND);
		}

		INodeRefDeployStrategyDAO nodeRefStrategyDao = (INodeRefDeployStrategyDAO) ServiceFactory.getService(INodeRefDeployStrategyDAO.class);
		BODeployNodeStrategyRelationBean[] beans = nodeRefStrategyDao.getStrategysByNodes(nodeList);
		Set<Long> set = new HashSet<Long>();
		for (BODeployNodeStrategyRelationBean bean : beans) {
			set.add(bean.getDeployStrategyId());
		}
		if (set.size() != 1) {
			LOG.error("nodes have different strategy.");
			throw new Exception("" + ErrorCode.Publish.NODES_HAVE_DIFF_STRATEGY);
		}

		return set.toArray(new Long[0])[0];
	}

	/**
	 * 获取部署策略下的所有节点
	 * 
	 * @param deployStrategy
	 * @return
	 * @throws Exception
	 */
	private List<Long> fetchNodesUnderStrategy(long deployStrategy) throws Exception {
		INodeRefDeployStrategyDAO nodeRefStrategyDao = (INodeRefDeployStrategyDAO) ServiceFactory.getService(INodeRefDeployStrategyDAO.class);
		BODeployNodeStrategyRelationBean[] nodes = nodeRefStrategyDao.getNodesByStrategyId(deployStrategy);
		
		List<Long> nodeList = new ArrayList<Long>();
		if (nodes != null) {
			for (BODeployNodeStrategyRelationBean node : nodes) {
				nodeList.add(node.getNodeId());
			}
		}
		return nodeList;
	}
	
	/**
	 * 获取当前部署策略下面所有的版本
	 * @param deployStrategy
	 * @return
	 * @throws Exception
	 */
	private Set<Long> fetchVersionsByStrategy(long deployStrategy) throws Exception {
		List<Long> nodeList = fetchNodesUnderStrategy(deployStrategy);
		Set<Long> versionSet = new HashSet<Long>();
		
		if (nodeList == null || nodeList.size() == 0) {
			return versionSet;
		}
		INodeRefVersionDAO nodeRefVersionDao = (INodeRefVersionDAO) ServiceFactory.getService(INodeRefVersionDAO.class);
		BODeployNodeVersionBean[] versions = nodeRefVersionDao.qryNodeVersionsByNodes(nodeList);
		if (versions != null && versions.length > 0) {
			for (BODeployNodeVersionBean version : versions) {
				versionSet.add(version.getVersionId());
			}
		}
		return versionSet;
	}
	

	/**
	 * 校验参数
	 * 
	 * @param strategy
	 * @return
	 */
	private Map<String, String> getFtpInfo(BODeployStrategyBean strategy) {
		// 服务器保存安装包的路径
		String ftpHost = strategy.getFtpHostIp();
		int ftpPort = strategy.getFtpHostPort();
		String ftpProtocol = strategy.getFtpProtocol();
		String userName = strategy.getFtpUserName();
		String passwd = strategy.getFtpPassword();

		Map<String, String> ftpInfo = new HashMap<String, String>();
		ftpInfo.put(MapKey.KEY_HOST_IP, ftpHost);

		// 格式：（协议，端口号）
		FtpType type = FtpType.getFtpType(Integer.valueOf(ftpProtocol));
		switch (type) {
		case FTP:
			ftpInfo.put(MapKey.KEY_CON_TYPE_FTP, String.valueOf(ftpPort));
			break;
		case SFTP:
			ftpInfo.put(MapKey.KEY_CON_TYPE_SFTP, String.valueOf(ftpPort));
			break;
		default:
			LOG.error("unknown ftp protocol.");
		}
		ftpInfo.put(MapKey.KEY_USER_NAME, userName);
		ftpInfo.put(MapKey.KEY_USER_PASSWD, passwd);

		return ftpInfo;
	}
}
