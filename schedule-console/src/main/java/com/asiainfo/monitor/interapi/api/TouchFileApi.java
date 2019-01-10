package com.asiainfo.monitor.interapi.api;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.monitor.tools.util.SSHUtil;

/**
 * 该类用于获取服务器上监控文件，通过上传服务器脚本，转移文件，并
 * 下载到本地机器。
 * @date	2010-5-13
 * @version	1.1
 */
public class TouchFileApi {
	private static transient Log log = LogFactory.getLog(TouchFileApi.class);

	/**
	 * 上传服务器脚本，备份文件并下载到本地目录
	 * @param ip		服务器ip
	 * @param port		服务器端口
	 * @param userName	用户名
	 * @param password	密码
	 * @param shellFile	服务器执行脚本文件
	 * @param toDir		本地保存目录
	 * @return			下载文件数
	 * @throws Exception
	 */
	public static int loadFiles(String ip, int port, String userName,
			String password, File shellFile, File toDir) throws Exception {
		return SSHUtil.collectFile(ip, port, userName, password, shellFile, toDir);
	}
}
