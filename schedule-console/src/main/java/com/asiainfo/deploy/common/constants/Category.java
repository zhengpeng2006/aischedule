package com.asiainfo.deploy.common.constants;

import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;

/**
 * 常用分类
 * 
 * @author 孙德东(24204)
 */
public class Category {
	
	/**
	 * 应用参数类型
	 * 
	 * @author 孙德东(24204)
	 */
	public static enum AppParamType {
		START_PARAM("0"),
		JVM_PARAM("1"),
		ENV_PARAM("2");
		
		private String value;
		private AppParamType(String s) {
			this.value = s;
		}
		
		public String value() {
			return this.value;
		}
	}
	/**
	 * 脚本类型：windows cmd\linux ksh\linux csh
	 * 
	 * @author 孙德东(24204)
	 */
	public static enum ScriptType {
		// 0：ksh 1：csh 2：cmd
		KSH(0), CSH(1), CMD(2), UNKNOWN(-1);

		int value = -1;

		private ScriptType(int value) {
			this.value = value;
		}

		public static ScriptType getScriptType(String type) {
			int i = Integer.valueOf(type);
			switch (i) {
			case 0:
				return KSH;
			case 1:
				return CSH;
			case 2:
				return CMD;
			default:
				return UNKNOWN;
			}
		}

		public int getValue() {
			return this.value;
		}
	}

	/**
	 * 操作类型：启动\监控\停止
	 * 
	 * @author 孙德东(24204)
	 */
	public static enum OperationType {
		START, MONITOR, STOP;
	}

	/**
	 * 启动方式
	 * 
	 * @author 孙德东(24204)
	 */
	public static enum StartMode {
		// 0：ksh 1：csh 2：cmd
		EXECUTABLE_OBJECT(0), JAVA_MAIN(1), UNKNOWN(-1);

		int value = -1;

		private StartMode(int value) {
			this.value = value;
		}

		public static StartMode getStartMode(String type) {
			int i = Integer.valueOf(type);
			switch (i) {
			case 0:
				return EXECUTABLE_OBJECT;
			case 1:
				return JAVA_MAIN;
			default:
				return UNKNOWN;
			}
		}

		public int getValue() {
			return this.value;
		}
	}

	/**
	 * 0：启动参数 1：jvm参数 2：env参数';
	 * 
	 * @author 孙德东(24204)
	 */
	public static enum ParamType {
		JVM(1), START(0), ENV(2), UNKNOWN(-1);

		private Integer type;

		private ParamType(Integer type) {
			this.type = type;
		}

		public static ParamType getParamType(int i) {
			switch (i) {
			case 0:
				return START;
			case 1:
				return JVM;
			case 2:
				return ENV;
			default:
				return UNKNOWN;
			}
		}

		public Integer value() {
			return this.type;
		}

		public String prefix() {
			switch (this.type) {
			case 0:
				return "start_";
			case 1:
				return "jvm_";
			case 2:
				return "env_";
			default:
				return StringUtils.EMPTY;
			}
		}
	}

	/**
	 * 文件上传、下载的两种方式
	 * 
	 * @author 孙德东(24204)
	 */
	public static enum FtpType {
		FTP(0), SFTP(1), UNKNOWN(-1);

		private int value;

		private FtpType(int i) {
			this.value = i;
		}

		public static FtpType getFtpType(int i) {
			switch (i) {
			case 1:
				return FTP;
			case 0:
				return SFTP;
			default:
				return UNKNOWN;

			}
		}

		public int value() {
			return this.value;
		}
	}

	/**
	 * 发布状态
	 * 
	 * @author 孙德东(24204)
	 */
	public static enum NodeStatus {
		BEGIN, // 开始
		CHECK_APP_RUNNING, // 检测应用是否运行
		CHECK_APP_STATUS_ERROR,//检测状态出错
		WAIT_APP_STOP, // 等待应用停止
		HOST_INIT, // 应用初始化
		CHECK_HOST_ENV, // 验证主机环境
		PUBLISH_INSTALL_PACKAGE, // 安装包分发
		INSTALL, // 安装
		CHECK_INSTALL_STATUS, // 检查安装是否成功
		ERROR, // 异常
		ROLL_BACK, // 正在回滚
		ROLL_BACK_SUCCESS, // 回滚成功
		SUCCESS, // 成功
		
		HOST_INIT_BEGIN,  //主机初始化开始
		HOST_INIT_MKDIR,  //创建目录
		HOST_INIT_UPLOAD_BIN,//上传bin脚本
		HOST_INIT_UPLOAD_SBIN,//上传sbin脚本
		HOST_INIT_FAIL,   //主机初始化失败
		HOST_INIT_SUCCESS;//主机初始化结束
	}

	/**
	 * 安装类型
	 * @author 孙德东(24204)
	 */
	public static enum InstallType {
		INSTALL("install"), ROLLBACK("rollback");

		private String value;

		private InstallType(String value) {
			this.value = value;
		}

		public String value() {
			return this.value;
		}
	}

	/**
	 * 版本状态
	 * @author 孙德东(24204)
	 */
	public static enum VersionState {
		CURRENT('C'), VALID_HISTORY('V'), INVALID_HISTORY('I'), UNKNOWN('U');

		private char c;

		private VersionState(char c) {
			this.c = c;
		}

		public String value() {
			return String.valueOf(this.c);
		}

		public static VersionState getVersionState(char c) {
			switch (c) {
			case 'C' | 'c':
				return CURRENT;
			case 'V' | 'v':
				return VALID_HISTORY;
			case 'I' | 'i':
				return INVALID_HISTORY;
			default:
				return UNKNOWN;
			}
		}
	}
	
	/**
	 * 支持的压缩格式
	 * 
	 * @author 孙德东(24204)
	 */
	public static enum CompressType {
		JAR("jar"),
		TAR("tar"),
		TAR_GZ("tar.gz"),
		PLAIN("plain");
		
		private String value;
		private CompressType(String value) {
			this.value = value;
		}
		
		public String value() {
			return this.value;
		}
		public static CompressType getCompressType(String str) {
			if (StringUtils.equalsIgnoreCase("jar", str)) {
				return JAR;
			}
			else if (StringUtils.equalsIgnoreCase("tar", str)) {
				return TAR;
			}
			else if (StringUtils.equalsIgnoreCase("tar.gz", str)) {
				return TAR_GZ;
			} 
			else {
				return PLAIN;
			}
		}
	}
	
	public static enum BooleanType {
		YES(true),NO(false),Unknown(false);
		private boolean value;
		private BooleanType(boolean value) {
			this.value = value;
		}
		
		public static BooleanType getBooleanType(String str) {
			if (StringUtils.equalsIgnoreCase("Y", str)) {
				return YES;
			}
			else if (StringUtils.equalsIgnoreCase("N", str)) {
				return NO;
			}
			else {
				return Unknown;
			}
		}
		
		public boolean value() {
			return this.value;
		}
	}
}
