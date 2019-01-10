package com.asiainfo.deploy.filetransfer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.exception.ErrorCode;
import com.asiainfo.deploy.exception.ExecuteResult;
import com.asiainfo.monitor.common.CommonConst;

/**
 * 使用Ftp上传\下载
 * 
 * @author 孙德东(24204)
 */
public class FtpOperator implements Operator {
	private static Log LOG = LogFactory.getLog(FtpOperator.class);

	@Override
	public ExecuteResult put(Map<String, String> node, String src, String dst) {
		InputStream input = null;
		try {
			input = new FileInputStream(src);
		} catch (FileNotFoundException e) {
			LOG.error("file not found, src=" + src, e);
			return ExecuteResult.errorResult(ErrorCode.Ftp.FILE_NOT_FOUND, "file not found, src=" + src);
		}
		return put(node, input, dst);
	}

	@Override
	public ExecuteResult get(Map<String, String> node, String src, String dst) {
		String ftpUserName = node.get(DeployConstants.MapKey.KEY_USER_NAME);
		String ftpPassword = node.get(DeployConstants.MapKey.KEY_USER_PASSWD);
		String ftpHost = node.get(DeployConstants.MapKey.KEY_HOST_IP);
		String port = node.get(CommonConst.CON_TYPE_FTP);

		int ftpPort = DeployConstants.JSch.DEFAULT_FTP_PORT;
		if (StringUtils.isNotEmpty(port)) {
			ftpPort = Integer.valueOf(port);
		}

		FTPClient ftp = new FTPClient();
		OutputStream os = null;
		try {
			int reply;
			ftp.connect(ftpHost, ftpPort);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(ftpUserName, ftpPassword);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return ExecuteResult.errorResult(ErrorCode.Ftp.LOGIN_ERROR, "login get reply:" + reply);
			}

			os = new FileOutputStream(dst);
			boolean result = ftp.retrieveFile(src, os);
			if (!result) {
				return ExecuteResult.errorResult(ErrorCode.Ftp.GET_ERROR, "get file error，with normal return.");
			}
		} catch (IOException e) {
			return ExecuteResult.errorResult(ErrorCode.Ftp.GET_ERROR, "get file error:" + e.getLocalizedMessage());
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					LOG.error("close local outputstream error:" + e.getLocalizedMessage());
				}
			try {
				ftp.logout();
			} catch (IOException e) {
				LOG.error("ftp log out error:" + e.getLocalizedMessage());
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					LOG.error("ftp disconnect error:" + ioe.getLocalizedMessage());
				}
			}
		}
		return ExecuteResult.successResult("success");
	}

	@Override
	public ExecuteResult batchPut(Map<String, String> node, List<InputStream> src, List<String> dst) {
		String ftpUserName = node.get(DeployConstants.MapKey.KEY_USER_NAME);
		String ftpPassword = node.get(DeployConstants.MapKey.KEY_USER_PASSWD);
		String ftpHost = node.get(DeployConstants.MapKey.KEY_HOST_IP);
		String port = node.get(DeployConstants.MapKey.KEY_CON_TYPE_FTP);

		int ftpPort = DeployConstants.JSch.DEFAULT_FTP_PORT;
		if (StringUtils.isNotEmpty(port)) {
			ftpPort = Integer.valueOf(port);
		}

		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(ftpHost, ftpPort);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(ftpUserName, ftpPassword);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return ExecuteResult.errorResult(ErrorCode.Ftp.LOGIN_ERROR, "login get reply:" + reply);
			}

			for (int i = 0; i < src.size(); i++) {
				boolean result = ftp.storeFile(dst.get(i), src.get(i));
				if (!result) {
					return ExecuteResult.errorResult(ErrorCode.Ftp.PUT_ERROR, "put file error,with normol return.");
				}
			}
		} catch (IOException e) {
			return ExecuteResult.errorResult(ErrorCode.Ftp.PUT_ERROR, "put file error:" + e.getLocalizedMessage());
		} finally {
			closeInputStream(src);
			try {
				ftp.logout();
			} catch (IOException e) {
				LOG.error("ftp log out error:" + e.getLocalizedMessage());
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					LOG.error("ftp disconnect error:" + ioe.getLocalizedMessage());
				}
			}
		}
		return ExecuteResult.successResult("success");
	}

	private void closeInputStream(List<InputStream> inList) {
		for (InputStream in : inList) {
			try {
				if (in != null)
				in.close();
			} catch (IOException e) {
				LOG.error("close inputstream error.", e);
			}
		}
	}
	
	@Override
	public ExecuteResult batchGet(Map<String, String> node, List<String> src, List<String> dst) {
		// 源文件数目和目标文件数目一致
		if (!batchValid(src, dst)) {
			LOG.error("batch file list unvalid.srcList=" + StringUtils.join(src.toArray()) + ",dstList=" + StringUtils.join(dst.toArray()));
			return ExecuteResult.errorResult(ErrorCode.Sftp.BATCH_FILE_LIST_UNVALID, "batch file list unvalid.");
		}
		
		// 连接参数
		String ftpUserName = node.get(DeployConstants.MapKey.KEY_USER_NAME);
		String ftpPassword = node.get(DeployConstants.MapKey.KEY_USER_PASSWD);
		String ftpHost = node.get(DeployConstants.MapKey.KEY_HOST_IP);
		String port = node.get(CommonConst.CON_TYPE_FTP);
		int ftpPort = DeployConstants.JSch.DEFAULT_FTP_PORT;
		if (StringUtils.isNotEmpty(port)) {
			ftpPort = Integer.valueOf(port);
		}

		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(ftpHost, ftpPort);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(ftpUserName, ftpPassword);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				LOG.error("login get reply:" + reply);
				return ExecuteResult.errorResult(ErrorCode.Ftp.LOGIN_ERROR, "login get reply:" + reply);
			}

			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			for (int i = 0; i < src.size(); i++) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("get file from src=" + src.get(i) + ",dst=" + dst.get(i));
				}
				
				OutputStream os = null;
				try {
					os = new FileOutputStream(dst.get(i));
					boolean result = ftp.retrieveFile(src.get(i), os);
					if (!result) {
						if (os != null) {
							os.close();
						}
						LOG.error("get file from src=" + src.get(i) + ",dst=" + dst.get(i));
						return ExecuteResult.errorResult(ErrorCode.Ftp.GET_ERROR, "get file error，with return:" + result);
					}
				} finally {
					if (os != null) {
						os.flush();
						os.close();
					}
				}
			}
		} catch (IOException e) {
			LOG.error("fetch files error.", e);
			return ExecuteResult.errorResult(ErrorCode.Ftp.GET_ERROR, "get file error:" + e.getLocalizedMessage());
		} finally {
			try {
				ftp.logout();
			} catch (IOException e) {
				LOG.error("ftp log out error.", e);
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					LOG.error("ftp disconnect error.", ioe);
				}
			}
		}
		return ExecuteResult.successResult("success");
	}

	@SuppressWarnings("rawtypes")
	private boolean batchValid(List srcList, List dstList) {
		if (srcList == null || dstList == null || srcList.isEmpty() || dstList.isEmpty()) {
			return false;
		}

		if (srcList.size() != dstList.size()) {
			return false;
		}

		return true;
	}
	
	@Override
	public ExecuteResult put(Map<String, String> node, InputStream input, String dst) {
		String ftpUserName = node.get(DeployConstants.MapKey.KEY_USER_NAME);
		String ftpPassword = node.get(DeployConstants.MapKey.KEY_USER_PASSWD);
		String ftpHost = node.get(DeployConstants.MapKey.KEY_HOST_IP);
		String port = node.get(DeployConstants.MapKey.KEY_CON_TYPE_FTP);

		int ftpPort = DeployConstants.JSch.DEFAULT_FTP_PORT;
		if (StringUtils.isNotEmpty(port)) {
			ftpPort = Integer.valueOf(port);
		}

		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(ftpHost, ftpPort);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(ftpUserName, ftpPassword);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return ExecuteResult.errorResult(ErrorCode.Ftp.LOGIN_ERROR, "login get reply:" + reply);
			}

			boolean result = ftp.storeFile(dst, input);
			if (!result) {
				return ExecuteResult.errorResult(ErrorCode.Ftp.PUT_ERROR, "put file error,with normol return.");
			}
		} catch (IOException e) {
			LOG.error("put file error.", e);
			return ExecuteResult.errorResult(ErrorCode.Ftp.PUT_ERROR, "put file error:" + e.getLocalizedMessage());
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					LOG.error("close local inputsteam error.", e);
				}
			try {
				ftp.logout();
			} catch (IOException e) {
				LOG.error("ftp log out error.", e);
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					LOG.error("ftp disconnect error.", ioe);
				}
			}
		}
		return ExecuteResult.successResult("success");
	}

}
