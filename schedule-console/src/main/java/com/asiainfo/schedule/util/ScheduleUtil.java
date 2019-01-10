package com.asiainfo.schedule.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.deploy.exception.ErrorCode;

public class ScheduleUtil {
	/** 进程启停及部署返回错误码对应描述map */
	private static Map<String,String> errMap;
	
	/**
	 * 获取错误码对应描述错误
	 * @return
	 */
	public static Map<String,String> getErrMap()
	{
		if (null == errMap)
		{
			errMap = new HashMap<String, String>();
			errMap.put(String.valueOf(ErrorCode.RemoteExecute.SESSION_OPEN_ERROR), "JSch open session error");
			errMap.put(String.valueOf(ErrorCode.RemoteExecute.SESSION_CONNECT_ERROR), "JSch session connect error");
			errMap.put(String.valueOf(ErrorCode.RemoteExecute.CHANNEL_OPEN_ERROR), "JSch channel open error");
			errMap.put(String.valueOf(ErrorCode.RemoteExecute.CHANNEL_GET_INPUTSTREAM_ERROR), "JSch channel get inputstream error");
			errMap.put(String.valueOf(ErrorCode.RemoteExecute.CHANNEL_CONNNECT_ERROR), "JSch channel connect error");
			errMap.put(String.valueOf(ErrorCode.RemoteExecute.WAIT_CMD_RESULT_TIMEOUT_ERROR), "wait command executed result time out");
			errMap.put(String.valueOf(ErrorCode.RemoteExecute.READ_CMD_RESULT_ERROR), "read command executed result error");
			errMap.put(String.valueOf(ErrorCode.RemoteExecute.EXEC_CMD_ERROR), "command executed failed");
			errMap.put(String.valueOf(ErrorCode.RemoteExecute.THREAD_SLEEP_ERROR), "thread sleep error");
			errMap.put(String.valueOf(ErrorCode.RemoteExecute.APP_IS_RUNNING), "application is already running.");
			errMap.put(String.valueOf(ErrorCode.FetchInfoFromMonitor.FETCH_NODE_ERROR), "get node information from monitor error");
			errMap.put(String.valueOf(ErrorCode.FetchInfoFromMonitor.FETCH_NODE_STRATEGY_ERROR), "fetch strategy of node error");
			errMap.put(String.valueOf(ErrorCode.FetchInfoFromMonitor.FETCH_TEMPLATE_ERROR), "fetch application template by strategy error");
			errMap.put(String.valueOf(ErrorCode.FetchInfoFromMonitor.FETCH_TEMPLATE_SCRIPT_ERROR), "fetch cached template script of template error");
			errMap.put(String.valueOf(ErrorCode.FetchInfoFromMonitor.FETCH_APP_ERROR), "fetch application id by  appCode error");
			errMap.put(String.valueOf(ErrorCode.FetchInfoFromMonitor.QUERY_APP_PARAMS_ERROR), "query application params by applicationId error");
			errMap.put(String.valueOf(ErrorCode.FetchInfoFromMonitor.PARAMS_REPLACE_ERROR), "Fetch Info From Monitor:params replace error");
			errMap.put(String.valueOf(ErrorCode.FetchInfoFromMonitor.FINAL_SCRIPT_IS_EMPTY), "script is empty");
			errMap.put(String.valueOf(ErrorCode.Auth.AUTH_FAIL), "authentication error");
			errMap.put(String.valueOf(ErrorCode.Sftp.GET_CHANNEL_ERROR), "get sftp channel error");
			errMap.put(String.valueOf(ErrorCode.Sftp.PUT_ERROR), "sftp put file error");
			errMap.put(String.valueOf(ErrorCode.Sftp.GET_ERROR), "sftp get file error");
			errMap.put(String.valueOf(ErrorCode.Ftp.LOGIN_ERROR),"ftp login error");
			errMap.put(String.valueOf(ErrorCode.Ftp.PUT_ERROR), "ftp put file error");
			errMap.put(String.valueOf(ErrorCode.Ftp.GET_ERROR), "ftp get file error");
			errMap.put(String.valueOf(ErrorCode.Publish.NO_NODE_IS_FOUND), "no node is found");
			errMap.put(String.valueOf(ErrorCode.Publish.NODES_HAVE_DIFF_STRATEGY), "nodes have different strategy");
			errMap.put(String.valueOf(ErrorCode.Publish.FINISH_GET_INSTALL_PKG_BUT_NOT_FOUND_IN_SERVER), "finish get ftp install package,but is not found in server");
			errMap.put(String.valueOf(ErrorCode.Publish.INVALID_PACKAGE), "invalid ftp package");
			errMap.put(String.valueOf(ErrorCode.Publish.APPEND_VERSION_TO_PKG_ERROR), "append version to install package error");
			errMap.put(String.valueOf(ErrorCode.Publish.TOO_MANY_VERSION), "more than two version now in use");
		}
		return errMap;
	}
	
	/**
	 * 获取当前操作员
	 * @return
	 */
	public static String getOpId ()
	{
		return ServiceManager.getUser() == null? null:ServiceManager.getUser().getCode();
	}
	
	/**
	 * 整型类型分片数组排序
	 * 
	 * @param items
	 * @return
	 */
	public static String[] sortItemsByIntger(String[] items) {
		if (items == null) {
			return null;
		}
		String pattern = "^[\\d,]+$";
		if (Pattern.matches(pattern, StringUtils.join(items, ","))) {
			int length = items.length;
			int[] itemsInteger = new int[length];
			for (int i = 0; i < length; i++) {
				itemsInteger[i] = Integer.valueOf(items[i]);
			}
			Arrays.sort(itemsInteger);
			String[] items_back = new String[length];
			for (int i = 0; i < length; i++) {
				items_back[i] = itemsInteger[i] + "";
			}
			return items_back;
		}
		return items;
	}

}
