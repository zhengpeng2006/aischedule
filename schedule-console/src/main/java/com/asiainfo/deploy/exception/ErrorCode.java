package com.asiainfo.deploy.exception;

/**
 * 错误码
 * @author 孙德东(24204)
 */
public interface ErrorCode 
{
	Long SUCCESS = 0L;
	
	//远程执行的错误码
	interface RemoteExecute
	{
	    Long SESSION_OPEN_ERROR = 1L;
	    Long SESSION_CONNECT_ERROR = 2L;
	    Long CHANNEL_OPEN_ERROR = 3L;
	    Long CHANNEL_GET_INPUTSTREAM_ERROR = 4L;
	    Long CHANNEL_CONNNECT_ERROR = 5L;
	    Long WAIT_CMD_RESULT_TIMEOUT_ERROR = 6L;
	    Long READ_CMD_RESULT_ERROR = 7L;
	    Long EXEC_CMD_ERROR = 8L;
	    Long THREAD_SLEEP_ERROR = 9L;
	    Long APP_IS_RUNNING = 10L;
	    Long EXECUTE_REMOTE_SHELL_ERROR = 11L;
	}
    
	//和监控之间交互出错
	interface FetchInfoFromMonitor
	{
		Long FETCH_NODE_ERROR = 21L;
		Long FETCH_NODE_STRATEGY_ERROR = 22L;
		Long FETCH_TEMPLATE_ERROR = 23L;
		Long FETCH_TEMPLATE_SCRIPT_ERROR = 24L;
		Long FETCH_APP_ERROR = 25L;
		Long QUERY_APP_PARAMS_ERROR = 26L;
		Long PARAMS_REPLACE_ERROR = 27L;
		Long FINAL_SCRIPT_IS_EMPTY = 28L;
		Long TEMPLATE_SCRIPT_IS_EMPTY = 29L;
	}
	
	//认证错误
	interface Auth
	{
		Long AUTH_FAIL = 31L;
	}
	
	//sftp异常
	interface Sftp
	{
		Long GET_CHANNEL_ERROR = 41L;
		Long PUT_ERROR = 42L;
		Long GET_ERROR = 43L;
		Long GET_SESSION_ERROR = 44L;
		Long BATCH_FILE_LIST_UNVALID = 45L;
	}
	
	//ftp异常
	interface Ftp
	{
		Long LOGIN_ERROR = 51L;
		Long PUT_ERROR = 52L;
		Long GET_ERROR = 53L;
		Long FILE_NOT_FOUND = 54L;
	}
	
	/**
	 * 安装包分发
	 * @author 孙德东(24204)
	 */
	interface Publish {
		Long NO_NODE_IS_FOUND = 61L;
		Long NODES_HAVE_DIFF_STRATEGY = 62L;
		Long FINISH_GET_INSTALL_PKG_BUT_NOT_FOUND_IN_SERVER = 63L;
		Long INVALID_PACKAGE = 64L;
		Long APPEND_VERSION_TO_PKG_ERROR = 65L;
		Long TOO_MANY_VERSION = 66L;
		Long CREATE_SERVER_DIR_ERROR = 67L;
	}
	
	interface Common {
		Long PARSE_INSTALL_RULE_ERROR = 71L; //解析安装规则失败
		Long NOT_ALL_VARIABLE_HAS_VALUE = 72L;//模板中有变量没有实例化值
	}
}
