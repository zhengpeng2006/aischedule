package com.asiainfo.deploy.common.constants;

/**
 * 常量类
 * 
 * @author 孙德东(24204)
 */
public interface DeployConstants {
	/**
	 * sftp配置
	 * 
	 * @author 孙德东(24204)
	 */
	interface SftpServerConfig {
		String FLAG = "sftpserver.flag";
		String IP = "sftpserver.ip";
		String USER_NAME = "sftpserver.username";
		String PASSWD = "sftpserver.passwd";
		String PORT = "sftpserver.port";
	}
	/**
	 * 内置变量名
	 * 
	 * @author 孙德东(24204)
	 */
	interface InnerScriptVariables {
		String VAR_SEPARATOR = "@@";
		// 下面6个内置变量对外开放，可以在脚本中使用
		String SERVER_NAME = "server_name";
		String HOME_DIR = "home_dir";
		String BIN_DIR = "bin_dir";
		String SBIN_DIR = "sbin_dir";
		String LOG_DIR = "log_dir";
		String CUSTOM_PARAMS = "custom_params"; // 自定义的变量

		// 下面2个内置变量内部使用，不对外开放
		String USER_NAME = "user_name";
		String SEARCH_CONTENT = "search_content";
	}

	/**
	 * 使用Ftp或者Sftp时，保存数据的map（监控返回的信息也是此格式）
	 * 
	 * @author 孙德东(24204)
	 */
	interface MapKey {
		String KEY_SERVER_ID = "SERVER_ID";
		String KEY_HOST_IP = "HOST_IP";
		String KEY_USER_NAME = "USER_NAME";
		String KEY_USER_PASSWD = "USER_PASSWD";
		String KEY_NODE_ID = "NODE_ID";
		String KEY_CON_TYPE_FTP = "FTP";
		String KEY_CON_TYPE_SFTP = "SFTP";
		String KEY_CON_TYPE_SSH = "SSH";
		String KEY_CON_TYPE_TELNET = "Telnet";
	}

	/**
	 * jsch相关的常量
	 * 
	 * @author 孙德东(24204)
	 */
	interface JSch {
		// FTP默认端口
		int DEFAULT_FTP_PORT = 21;
		// SFTP默认端口
		int DEFAULT_SFTP_PORT = 22;
		// SSH默认端口
		int DEFAULT_SSH_PROT = 22;
		// 10秒连接建立超时时间
		int TIME_OUT = 10000;
		// 120秒本地超时时间
		int EXEC_TIME_OUT = 120000;
		// 远程命令执行的通道类型，JSCH字符串
		String REMOTE_EXEC_CHANNEL_TYPE = "exec";
		// sftp的通道类型，JSCH字符串
		String SFTP_CHANNLE_TYPE = "sftp";
	}

	/**
	 * 重用
	 * 
	 * @author 孙德东(24204)
	 */
	interface Common {
		// 约定，要分发的包需要达成tar.gz
		String INSTALL_PKG_SUFIX = ".tar.gz";
		// 例如，从服务器下载的文件名叫做 a.tar.gz,加入版本文件后的文件名叫做versioned_a.tar.gz
		String VERSIONED_PKG_PREFIX = "versioned_";
		// 版本文件的名称
		String VERSION_FILE_NAME = "version.record";

		String FTP_DIR = "ftpDir";
		String BACKUP_PATH = FTP_DIR + "/autobackup.tar";
	}

	/**
	 * 系统预置的sbin脚本
	 * 
	 * @author 孙德东(24204)
	 */
	interface SbinScripts {
		String DIR = "templates/";
		String CRONOLOG = "cronolog";
		String MONITOR_PROCESS = "monitor_process.sh";
		String SET_ENV = "setEnv.sh";
	}

	/**
	 * 启停相关
	 * 
	 * @author 孙德东(24204)
	 */
	interface Operation {
		String START_SHELL_NAME = "start.sh";
		String STOP_SHELL_NAME = "stop.sh";
	}
	/**
	 * 发布相关
	 * 
	 * @author 孙德东(24204)
	 */
	interface Publish {
		/**
		 * 主机检查脚本
		 */
		String CHECK_HOST_SHELL_NAME = "check_host.sh";
		String CHECK_HOST_SHELL_PATH = "templates/check_host.sh";

		/**
		 * 发布过程中失败回滚脚本
		 */
		String ROLL_BACK_SHELL_NAME = "roll_back.sh";
		String ROLL_BACK_SHELL_PATH = "templates/roll_back.sh";

		String START_SCRIPT_NAME = "start.sh";
		String STOP_SCRIPT_NAME = "stop.sh";
		/**
		 * 备份文件的名称
		 */
		String BACKUP_FILE_NAME = "backup.tar.gz";
	}
}
