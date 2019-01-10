package com.asiainfo.deploy.installpackage;

import java.io.File;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.deploy.common.constants.Category.FtpType;
import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.common.constants.DeployConstants.MapKey;
import com.asiainfo.deploy.config.DeployConfigParser;
import com.asiainfo.deploy.exception.ExecuteResult;
import com.asiainfo.deploy.filetransfer.FtpFactory;
import com.asiainfo.deploy.filetransfer.Operator;

/**
 * 分布式环境下的安装包管理
 * 
 * @author 孙德东(24204)
 */
public class PackageManager {
	// 日志记录
	private static Log LOG = LogFactory.getLog(PackageInstallTask.class);
	
	private PackageManager(){}
	
	/**
	 * 获取版本包的路径,分布式环境下面需要sftp取包
	 * @throws Exception 
	 */
	public static void getPkg(String pkgpath) throws Exception {
		Properties p = DeployConfigParser.getConfig();
		if (p == null) {
			LOG.debug("没有获取到sftpserver.properties配置。");
			return;
		}
		
		Map<String, String> node = valid(p);
		if (node == null) {
			LOG.debug("sftpserver.properties配置错误或者显式指定非分布式模式，将采用非分布式模式。");
			return;
		}
		
		String ip = StringUtils.trim(p.getProperty(DeployConstants.SftpServerConfig.IP));
		if (StringUtils.equalsIgnoreCase(ip, InetAddress.getLocalHost().getHostAddress())) {
			LOG.error(ip + " 为安装包管理服务器，不需要转移安装包。");
			return;
		}
		
		Operator ftpOperator = FtpFactory.getFileOperator(FtpType.SFTP);
		ExecuteResult rtn = ftpOperator.get(node, pkgpath, pkgpath);
		if (!rtn.isSuccess()) {
			LOG.error("get file " + pkgpath + " error:" + (String) rtn.getMessage());
			throw new Exception("" + rtn.getErrorCode());
		}
	}
	
	public static void handle(String versionedPkgDst) throws Exception {
		Properties p = DeployConfigParser.getConfig();
		if (p == null) {
			LOG.error("没有获取到sftpserver.properties配置。");
			return;
		}
		
		Map<String, String> node = valid(p);
		if (node == null) {
			LOG.error("sftpserver.properties配置错误或者显式指定非分布式模式，将采用非分布式模式。");
			return;
		}
		
		String ip = StringUtils.trim(p.getProperty(DeployConstants.SftpServerConfig.IP));
		if (StringUtils.equalsIgnoreCase(ip, InetAddress.getLocalHost().getHostAddress())) {
			LOG.error(ip + " 为安装包管理服务器，不需要转移安装包。");
			return;
		}
		
		Operator ftpOperator = FtpFactory.getFileOperator(FtpType.SFTP);
		ExecuteResult rtn = ftpOperator.put(node, versionedPkgDst, versionedPkgDst);
		if (!rtn.isSuccess()) {
			LOG.error("change host:" + versionedPkgDst + " error:" + (String) rtn.getMessage());
			throw new Exception("change host:" + versionedPkgDst + " error:" + (String) rtn.getMessage());
		}
		
		//上传到服务器后删除本地文件
		//new File(versionedPkgDst).delete();
	}

	public static Map<String, String> valid(Properties p) {
		String flag = StringUtils.trim(p.getProperty(DeployConstants.SftpServerConfig.FLAG));
		//非true不校验
		if (!"true".equalsIgnoreCase(flag)) {
			return null;
		}
		
		String ip = StringUtils.trim(p.getProperty(DeployConstants.SftpServerConfig.IP));
		String username = StringUtils.trim(p.getProperty(DeployConstants.SftpServerConfig.USER_NAME));
		String passwd = StringUtils.trim(p.getProperty(DeployConstants.SftpServerConfig.PASSWD));
		String port = StringUtils.trim(p.getProperty(DeployConstants.SftpServerConfig.PORT));
		
		if (LOG.isDebugEnabled()) {
			//打印安装包管理中心
			StringBuilder sb = new StringBuilder();
			sb.append("flag=").append(flag).append(",ip=").append(ip).append(",username").append(username)
			  .append(",passwd").append(passwd);
			LOG.debug(sb.toString());
		}
		
		if (StringUtils.isEmpty(ip) || StringUtils.isEmpty(username) || StringUtils.isEmpty(passwd)) {
			return null;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put(MapKey.KEY_HOST_IP, ip);
		map.put(MapKey.KEY_USER_NAME, username);
		map.put(MapKey.KEY_USER_PASSWD, passwd);
		if (StringUtils.isNotEmpty(port)) {
			map.put(MapKey.KEY_CON_TYPE_SFTP, port);
		}
		else {
			map.put(MapKey.KEY_CON_TYPE_SFTP, "22");
		}
		
		return map;
	}
}
