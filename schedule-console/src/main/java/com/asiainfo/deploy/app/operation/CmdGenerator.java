package com.asiainfo.deploy.app.operation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.common.utils.DeployUtils;
import com.asiainfo.deploy.installpackage.InstallRuleParser.InstallRule;

/**
 * 生成备份、回滚、安装命令
 * 
 * @author 孙德东(24204)
 */
public class CmdGenerator {

	private CmdGenerator(){}
	
	/**
	 * 备份命令
	 * @param strategy
	 * @param rules
	 * @return
	 */
	public static String backup(BODeployStrategyBean strategy, List<InstallRule> rules) {
		String homeDir = strategy.getClientHomePath();
		StringBuilder sb = new StringBuilder();
		sb.append("rm -rf ")
		            .append(DeployConstants.Common.BACKUP_PATH)
		            .append(";");
		sb.append("tar cf ").append(DeployConstants.Common.BACKUP_PATH)
		   .append(" ");
		
		List<String> backupDirs = new ArrayList<String>();
		for (InstallRule rule : rules) {
			backupDirs.add(DeployUtils.constructAbsoluteDirWithSlash(homeDir, rule.dst) + "*");
		}
		sb.append(StringUtils.join(backupDirs, " "));
		
		return sb.toString();
	}
	
	/**
	 * 回滚命令
	 * @return
	 */
	public static String rollback() {
		return "tar xf " + DeployConstants.Common.BACKUP_PATH +  " -C /";
	}
	
	
	/**
	 * 安装命令
	 * @param remoteAbsolutePath
	 * @param strategy
	 * @param rules
	 * @return
	 */
	public static String install(String remoteAbsolutePath, BODeployStrategyBean strategy, List<InstallRule> rules) {
		String homeDir = strategy.getClientHomePath();
		String ftpDir = DeployUtils.constructAbsoluteDirWithoutSlash(homeDir, DeployConstants.Common.FTP_DIR);
		
		StringBuilder sb = new StringBuilder();
		sb.append("tar zxf ").append(remoteAbsolutePath).append(" -C ").append(ftpDir)
		  .append(";");
		
		List<String> list = new ArrayList<String>();
		//只支持jar tar tar.gz
		for (InstallRule rule : rules) {
			String unzipCmd = StringUtils.EMPTY;
			String option = StringUtils.EMPTY;
			String dstOption = " -C ";
			boolean needClearDir = false;//是否需要清空目录,解压缩的情况下需要/直接拷贝不需要
			switch(rule.compressType) {
			case JAR:
				unzipCmd = "unzip";
				option = "";
				dstOption = " -d ";
				needClearDir = true;
				break;
			case TAR:
				unzipCmd = "tar";
				option = "xf";
				needClearDir = true;
				break;
			case TAR_GZ:
				unzipCmd = "tar";
				option = "zxf";
				needClearDir = true;
				break;
			default:
				unzipCmd = "mv";
				option = StringUtils.EMPTY;
				needClearDir = false;
			    break;
			}
			
			//最后的命令
			StringBuilder tmp = new StringBuilder();
			if (needClearDir) {
				tmp.append("rm -rf ")
				.append(DeployUtils.constructAbsoluteDirWithSlash(homeDir, rule.dst))
				.append("*;");
			}
			
			tmp.append(unzipCmd)
			   .append(" ");
			if (StringUtils.isNotEmpty(option)) {
				tmp.append(option)
				   .append(" ");
			}
			tmp.append(DeployUtils.addFileSeperatorInEnd(ftpDir) + rule.src)
			   .append(dstOption)
			   .append(DeployUtils.constructAbsoluteDirWithSlash(homeDir, rule.dst));
			list.add(tmp.toString());
		}
		sb.append(StringUtils.join(list, ";"));
		return sb.toString();
	}
	
	/**
	 * 测试是否安装成功的命令
	 * @return
	 */
	public static String check(String homeDir) {
		String ftpDir = DeployUtils.constructAbsoluteDirWithSlash(homeDir, DeployConstants.Common.FTP_DIR);
		StringBuilder sb = new StringBuilder();
		sb.append("cat ")
		  .append(ftpDir + DeployConstants.Common.VERSION_FILE_NAME);
		return sb.toString();
	}
	
	/**
	 * 修改模板脚本的执行权限
	 * @return
	 */
	public static String chmod(BODeployStrategyBean strategy) {
		StringBuilder sb = new StringBuilder();
		sb.append("chmod +x ");
		
		String home = strategy.getClientHomePath();
		String binPath = strategy.getClientBinPath();
		
		List<String> list = new ArrayList<String>();
		if (StringUtils.isNotEmpty(binPath)) {
			String binDir = DeployUtils.addFileSeperatorInEnd(DeployUtils.constructAbsoluteDirWithSlash(home, binPath));
			list.add(binDir + DeployConstants.Publish.START_SCRIPT_NAME);
		}
		
		String sbinPath = strategy.getClientSbinPath();
		if (StringUtils.isNotEmpty(sbinPath)) {
			String sbinDir = DeployUtils.addFileSeperatorInEnd(DeployUtils.constructAbsoluteDirWithSlash(home, sbinPath));
			list.add(sbinDir + DeployConstants.SbinScripts.CRONOLOG);
			list.add(sbinDir + DeployConstants.SbinScripts.MONITOR_PROCESS);
			list.add(sbinDir + DeployConstants.SbinScripts.SET_ENV);
		}
		
		sb.append(StringUtils.join(list, " "));
		return sb.toString();
	}
}
