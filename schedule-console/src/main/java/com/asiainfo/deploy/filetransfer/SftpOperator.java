package com.asiainfo.deploy.filetransfer;

import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.exception.ErrorCode;
import com.asiainfo.deploy.exception.ExecuteResult;
import com.jcraft.jsch.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * JSch实现的sftp
 * 
 * @author 孙德东(24204)
 */
public class SftpOperator implements Operator {
	private static transient Log LOG = LogFactory.getLog(SftpOperator.class);

	private Session getSession(Map<String, String> node, int timeout) throws JSchException {
		String ftpUserName = node.get(DeployConstants.MapKey.KEY_USER_NAME);
		String ftpPassword = node.get(DeployConstants.MapKey.KEY_USER_PASSWD);
		String ftpHost = node.get(DeployConstants.MapKey.KEY_HOST_IP);
		String port = node.get(DeployConstants.MapKey.KEY_CON_TYPE_SSH);

		int ftpPort = DeployConstants.JSch.DEFAULT_SFTP_PORT;
		if (StringUtils.isNotEmpty(port)) {
			ftpPort = Integer.valueOf(port);
		}

		Session session = null;
		JSch jsch = new JSch(); // 创建JSch对象
		session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
		LOG.debug("Session created.");
		if (ftpPassword != null) {
			session.setPassword(ftpPassword); // 设置密码
		}
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config); // 为Session对象设置properties
		session.setTimeout(timeout); // 设置timeout时间
		session.connect(); // 通过Session建立链接

		return session;
	}

	private ChannelSftp getChannel(Session session) throws JSchException {
		Channel channel = null;
		channel = session.openChannel(DeployConstants.JSch.SFTP_CHANNLE_TYPE); // 打开SFTP通道
		channel.connect(); // 建立SFTP通道的连接
		return (ChannelSftp) channel;
	}

	private void closeChannel(Channel channel, Session session) {
		if (channel != null) {
			channel.disconnect();
		}

		if (session != null) {
			session.disconnect();
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param node
	 *            节点信息
	 * @param src
	 * @param dst
	 */
	public ExecuteResult put(Map<String, String> node, String src, String dst) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("put file from src=" + src + ",dst=" + dst);
		}
		
		Session session = null;
		try {
			session = getSession(node, DeployConstants.JSch.TIME_OUT);
		} catch (JSchException e) {
			LOG.error("get sftp session error.", e);
			return ExecuteResult.errorResult(ErrorCode.Sftp.GET_SESSION_ERROR,
					"get sftp session error," + e.getLocalizedMessage());
		}
		ChannelSftp channel = null;
		try {
			channel = getChannel(session);
		} catch (JSchException e1) {
			LOG.error("get sftp channel error.", e1);
			return ExecuteResult.errorResult(ErrorCode.Sftp.GET_CHANNEL_ERROR,
					"get sftp channel error," + e1.getLocalizedMessage());
		}

		try {
			// channel.put(src, dst, new SftpMonitor(), ChannelSftp.OVERWRITE);
			channel.put(src, dst, ChannelSftp.OVERWRITE);
		} catch (SftpException e) {
			LOG.debug("put file from src=" + src + ",dst=" + dst + " error.", e);
			return ExecuteResult.errorResult(ErrorCode.Sftp.PUT_ERROR, "put file error," + e.getLocalizedMessage());
		} // 代码段2

		channel.quit();
		// 关闭channel
		closeChannel(channel, session);
		return ExecuteResult.successResult("success");
	}

	public ExecuteResult put(Map<String, String> node, InputStream src, String dst) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("put file from inputstream,dst=" + dst);
		}
		Session session = null;
		try {
			session = getSession(node, DeployConstants.JSch.TIME_OUT);
		} catch (JSchException e) {
			closeInputStream(src);
			LOG.error("get sftp session error", e);
			return ExecuteResult.errorResult(ErrorCode.Sftp.GET_SESSION_ERROR,
					"get sftp session error," + e.getLocalizedMessage());
		}
		ChannelSftp channel = null;
		try {
			channel = getChannel(session);
		} catch (JSchException e1) {
			closeInputStream(src);
			LOG.error("get sftp channel error.", e1);
			return ExecuteResult.errorResult(ErrorCode.Sftp.GET_CHANNEL_ERROR,
					"get sftp channel error," + e1.getLocalizedMessage());
		}

		try {
			// channel.put(src, dst, new SftpMonitor(), ChannelSftp.OVERWRITE);
			channel.put(src, dst, ChannelSftp.OVERWRITE);
		} catch (SftpException e) {
			closeInputStream(src);
			LOG.error("put file from inputstream,dst=" + dst + " error.", e);
			return ExecuteResult.errorResult(ErrorCode.Sftp.PUT_ERROR, "put file error," + e.getLocalizedMessage());
		} // 代码段2

		channel.quit();
		// 关闭channel
		closeChannel(channel, session);
		closeInputStream(src);
		return ExecuteResult.successResult("success");
	}
	
	/**
	 * 下载文件
	 * 
	 * @param node
	 * @param src
	 * @param dst
	 * @return
	 */
	public ExecuteResult get(Map<String, String> node, String src, String dst) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("get file from src=" + src + ",dst=" + dst);
		}
		
		Session session = null;
		try {
			session = getSession(node, DeployConstants.JSch.TIME_OUT);
		} catch (JSchException e) {
			LOG.error("get sftp session error.", e);
			return ExecuteResult.errorResult(ErrorCode.Sftp.GET_SESSION_ERROR,
					"get sftp session error," + e.getLocalizedMessage());
		}
		ChannelSftp channel = null;
		try {
			channel = getChannel(session);
		} catch (JSchException e1) {
			LOG.error("get sftp channel error.", e1);
			return ExecuteResult.errorResult(ErrorCode.Sftp.GET_CHANNEL_ERROR,
					"get sftp channel error," + e1.getLocalizedMessage());
		}

		// channel.cd(arg0);
		try {
			channel.get(src, dst);
		} catch (SftpException e) {
			LOG.error("get file from src=" + src + ",dst=" + dst + " error.", e);
			return ExecuteResult.errorResult(ErrorCode.Sftp.GET_ERROR, "get file error," + e.getLocalizedMessage());
		}

		channel.quit();
		// 关闭channel
		closeChannel(channel, session);
		return ExecuteResult.successResult("success");
	}

	/**
	 * 监控进度，预留接口 do noting
	 * 
	 * @author 孙德东(24204)
	 */
	public class SftpMonitor implements SftpProgressMonitor {

		@Override
		public boolean count(long arg0) {
			return true;
		}

		@Override
		public void end() {

		}

		@Override
		public void init(int arg0, String arg1, String arg2, long arg3) {
		}
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
	
	private void closeInputStream(InputStream in) {
		try {
			if (in != null)
			in.close();
		} catch (IOException e) {
			LOG.error("close inputstream error.", e);
		}
	}
	
	@Override
	public ExecuteResult batchPut(Map<String, String> node, List<InputStream> srcList, List<String> dstList) {
		if (!batchValid(srcList, dstList)) {
			closeInputStream(srcList);
			return ExecuteResult.errorResult(ErrorCode.Sftp.BATCH_FILE_LIST_UNVALID, "batch file list unvalid.");
		}

		Session session = null;
		try {
			session = getSession(node, DeployConstants.JSch.TIME_OUT);
		} catch (JSchException e) {
			closeInputStream(srcList);
			LOG.error("get sftp session error.", e);
			return ExecuteResult.errorResult(ErrorCode.Sftp.GET_SESSION_ERROR,
					"get sftp session error," + e.getLocalizedMessage());
		}

		ChannelSftp channel = null;
		try {
			channel = getChannel(session);
		} catch (JSchException e1) {
			closeInputStream(srcList);
			LOG.error("get sftp channel error.", e1);
			return ExecuteResult.errorResult(ErrorCode.Sftp.GET_CHANNEL_ERROR,
					"get sftp channel error," + e1.getLocalizedMessage());
		}

		int i = 0;
		try {
			for (i = 0; i < srcList.size(); i++) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("put file from inputstream,dst=" + dstList.get(i));
				}
				channel.put(srcList.get(i), dstList.get(i), ChannelSftp.OVERWRITE);
			}
		} catch (SftpException e) {
			closeInputStream(srcList);
			LOG.error("put file from inputstream,dst=" + dstList.get(i) + " error.", e);
			return ExecuteResult.errorResult(ErrorCode.Sftp.PUT_ERROR, "put file error," + e.getLocalizedMessage());
		} // 代码段2

		// 关闭channel
		closeChannel(channel, session);
		closeInputStream(srcList);
		return ExecuteResult.successResult("success");
	}

	@Override
	public ExecuteResult batchGet(Map<String, String> node, List<String> srcList, List<String> dstList) {
		if (!batchValid(srcList, dstList)) {
			LOG.error("batch file list unvalid.srcList=" + StringUtils.join(srcList.toArray()) + ",dstList=" + StringUtils.join(dstList.toArray()));
			return ExecuteResult.errorResult(ErrorCode.Sftp.BATCH_FILE_LIST_UNVALID, "batch file list unvalid.");
		}

		Session session = null;
		try {
			session = getSession(node, DeployConstants.JSch.TIME_OUT);
		} catch (JSchException e) {
			LOG.error("get sftp session error.", e);
			return ExecuteResult.errorResult(ErrorCode.Sftp.GET_SESSION_ERROR,
					"get sftp session error," + e.getLocalizedMessage());
		}

		ChannelSftp channel = null;
		try {
			channel = getChannel(session);
		} catch (JSchException e1) {
			LOG.error("get sftp channel error.", e1);
			return ExecuteResult.errorResult(ErrorCode.Sftp.GET_CHANNEL_ERROR,
					"get sftp channel error," + e1.getLocalizedMessage());
		}

		int i = 0;
		try {
			for (i = 0; i < srcList.size(); i++) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("get file from src=" + srcList.get(i) + ",dst=" + dstList.get(i));
				}
				channel.get(srcList.get(i), dstList.get(i));
			}
		} catch (SftpException e) {
			LOG.error("get file from src=" + srcList.get(i) + ",dst=" + dstList.get(i), e);
			return ExecuteResult.errorResult(ErrorCode.Sftp.GET_ERROR, "get file error," + e.getLocalizedMessage());
		}

		// 关闭channel
		closeChannel(channel, session);
		return ExecuteResult.successResult("success");
	}
}
