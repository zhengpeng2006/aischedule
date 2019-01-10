package com.asiainfo.deploy.execute.remoteshell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.exception.ErrorCode;
import com.asiainfo.deploy.exception.ExecuteResult;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 远程执行命令
 * 
 * @author 孙德东(24204)
 */
public class RemoteCmdExecUtils {
	private static final Log LOG = LogFactory.getLog(RemoteCmdExecUtils.class);
	// 等待命令返回结果的间隔0.1秒
	private static final int WAIT_RESULT_INTERNAL = 100;

	/*
	 * //session保持 private static ConcurrentMap<String, Future<Session>> sessionCache = new ConcurrentHashMap<String, Future<Session>>(); // session清理 private
	 * static ConcurrentMap<String, Long> sessionLastAccessTimeCache = new ConcurrentHashMap<String, Long>(); // 会话保持时间20min private static long
	 * DEFAULT_SESSION_VALID_TIME = 20L * 60L *1000L; // 会话清理间隔5min private static long DEFAULT_SESSION_CLEAR_INTERNAL = 5L * 60L *1000L; private static
	 * AtomicBoolean monitorStarted = new AtomicBoolean(false);
	 */

	private RemoteCmdExecUtils() {
	}

	/**
	 * 清理过期的session
	 * 
	 * @author 孙德东(24204)
	 */
	/*
	 * private static class SshSessionMonitorTask extends TimerTask {
	 * 
	 * @Override public void run() { for (Map.Entry<String, Long> entry : sessionLastAccessTimeCache.entrySet()) { String key = entry.getKey(); long
	 * lastAccessTime = entry.getValue();
	 * 
	 * if (System.currentTimeMillis() - lastAccessTime > DEFAULT_SESSION_VALID_TIME) { try { LOG.debug("clearing ssh session of key:" + key);
	 * disconnectSession(sessionCache.get(key).get()); } catch (Exception e) { LOG.error("clear ssh session of key:" + key + " error.", e); } } } } }
	 */

	/**
	 * 远程执行命令(只能执行单行命令,可以包含管道符）
	 * 
	 * @param userName
	 * @param passwd
	 * @param hostIP
	 * @param port
	 * @param command
	 * @return
	 */
	public static ExecuteResult execRomoteCmd(String userName, String passwd, String hostIP, String port, String command) {

		if (LOG.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("Execute Remote Command[userName=").append(userName).append(",password=").append(passwd).append(",ip=").append(hostIP).append(",port=")
					.append(port).append(",command={").append(command).append("}]");
			LOG.debug(sb.toString());
		}

		/*
		 * if (monitorStarted.compareAndSet(false, true)) { new Timer(true).schedule(new SshSessionMonitorTask(), DEFAULT_SESSION_CLEAR_INTERNAL,
		 * DEFAULT_SESSION_CLEAR_INTERNAL); }
		 */

		int sshPort = DeployConstants.JSch.DEFAULT_SSH_PROT;
		if (StringUtils.isNotEmpty(port)) {
			sshPort = Integer.valueOf(port);
		}

		Session session = getSession(userName, passwd, hostIP, sshPort);
		if (session == null) {
			return ExecuteResult.errorResult(ErrorCode.RemoteExecute.SESSION_CONNECT_ERROR, "JSch session connect error.");
		}

		// 修改session最近使用时间
		/*
		 * String key = key(userName, hostIP, sshPort); sessionLastAccessTimeCache.put(key, System.currentTimeMillis());
		 */

		// JSch channel open
		Channel channel = null;
		try {
			channel = session.openChannel(DeployConstants.JSch.REMOTE_EXEC_CHANNEL_TYPE);
		} catch (JSchException e) {
			LOG.error("JSch channel open error.", e);
			disconnectSessionAndChannel(session, channel);
			return ExecuteResult.errorResult(ErrorCode.RemoteExecute.CHANNEL_OPEN_ERROR, "JSch channel open error.\r\n" + e.getMessage());
		}

		// 设置要执行的命令并将输入流设置为空（command实际上就是输入流了，jsch内部处理）
		((ChannelExec) channel).setCommand(command);
		channel.setInputStream(null);

		// 设置异常流
		// OutputStream output = new FileOutputStream("H:\\error.txt");
		// ((ChannelExec)channel).setErrStream(output);
		//((ChannelExec) channel).setErrStream(System.err);     //ChannelExec、tomcat6、System输出存在兼容问题，导致log4j不打印

		// 获取命令的输出，也就是我们这里输入流
		InputStream in = null;
		try {
			in = channel.getInputStream();
		} catch (IOException e) {
			LOG.error("JSch channel get inputstream error.", e);
			disconnectSessionAndChannel(session, channel);
			return ExecuteResult.errorResult(ErrorCode.RemoteExecute.CHANNEL_GET_INPUTSTREAM_ERROR, "JSch channel get inputstream error.\r\n" + e.getMessage());
		}

		// grep -ef做实验，卡住后超时返回
		try {
			channel.connect(DeployConstants.JSch.TIME_OUT);
		} catch (JSchException e) {
			LOG.error("JSch channel connect error.", e);
			disconnectSessionAndChannel(session, channel);
			return ExecuteResult.errorResult(ErrorCode.RemoteExecute.CHANNEL_CONNNECT_ERROR, "JSch channel connect error.\r\n" + e.getMessage());
		}

		byte[] tmp = new byte[1024];
		int waitTimes = DeployConstants.JSch.EXEC_TIME_OUT / WAIT_RESULT_INTERNAL;
		int num = 1;

		// 命令执行输出结果
		StringBuilder cmdOut = new StringBuilder();
		// 执行结果状态
		int exitStatus = -1;
		ExecuteResult result = null;

		try {
			while (true) {
				// 等待命令返回结果超时
				if (num > waitTimes) {
					result = ExecuteResult.errorResult(ErrorCode.RemoteExecute.WAIT_CMD_RESULT_TIMEOUT_ERROR, "wait command executed result time out.");
					break;
				}

				// 读取命令返回结果
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					cmdOut.append(new String(tmp, 0, i));
				}

				if (channel.isClosed()) {
					if (in.available() > 0)
						continue;

					exitStatus = channel.getExitStatus();
					// 命令被解析，并返回了退出码（不一定执行不成功）
					if (exitStatus != 0) {
						result = ExecuteResult.errorResult(ErrorCode.RemoteExecute.EXEC_CMD_ERROR, exitStatus + "," + cmdOut.toString());
					} else {
						// 命令被解析且返回成功
						result = ExecuteResult.successResult(cmdOut.toString());
					}
					break;
				}
				Thread.sleep(WAIT_RESULT_INTERNAL);
				num++;
			}
		} catch (IOException e) {
			LOG.error("read command executed result error.", e);
			result = ExecuteResult.errorResult(ErrorCode.RemoteExecute.READ_CMD_RESULT_ERROR, "read command executed result error.\r\n" + e.getMessage());
		} catch (InterruptedException e) {
			LOG.error("thread sleep error.", e);
			result = ExecuteResult.errorResult(ErrorCode.RemoteExecute.THREAD_SLEEP_ERROR, "thread sleep error.\r\n" + e.getMessage());
		} finally {
			disconnectSessionAndChannel(session, channel);
		}

		return result;
	}

	private static Session getSession(final String userName, final String passwd, final String hostIP, final int sshPort) {
		/*
		 * String key = key(userName, hostIP, sshPort);
		 * 
		 * Future<Session> future_session = sessionCache.get(key); if (future_session == null) { FutureTask<Session> task = new FutureTask<Session>(new
		 * Callable(){
		 * 
		 * @Override public Object call() throws Exception { return getNewSession(userName, passwd, hostIP, sshPort); } });
		 * 
		 * future_session = sessionCache.putIfAbsent(key, task); if (future_session == null) { future_session = task; task.run(); } }
		 * 
		 * Session session = null; try { session = future_session.get(); } catch (InterruptedException e) { LOG.error("get session from Future object error.",
		 * e); } catch (ExecutionException e) { LOG.error("get session from Future object error.", e); }
		 */
		return getNewSession(userName, passwd, hostIP, sshPort);
	}

	/**
	 * 获取一个新的会话
	 * 
	 * @param userName
	 * @param passwd
	 * @param hostIP
	 * @param sshPort
	 * @return
	 */
	private static Session getNewSession(String userName, String passwd, String hostIP, int sshPort) {
		JSch jsch = new JSch();
		Session session = null;
		// JSch open session
		try {
			session = jsch.getSession(userName, hostIP, sshPort);
		} catch (JSchException e) {
			LOG.error("get session of " + key(userName, hostIP, sshPort) + " error.", e);
			disconnectSession(session);
			return null;
		}
		session.setPassword(passwd);
		session.setConfig("StrictHostKeyChecking", "no");

		// JSch session connect
		try {
			session.connect(DeployConstants.JSch.TIME_OUT);
		} catch (JSchException e) {
			LOG.error("connect " + key(userName, hostIP, sshPort) + " error.", e);
			disconnectSession(session);
			return null;
		}

		return session;
	}
	/**
	 * 作为缓存的key值
	 * 
	 * @param userName
	 * @param hostIP
	 * @param port
	 * @return
	 */
	private static String key(String userName, String hostIP, int sshPort) {
		return new StringBuilder().append(userName).append("_").append(hostIP).append("_").append(sshPort).toString();
	}

	private static void disconnectSession(Session session) {
		if (session != null)
			session.disconnect();
	}

	private static void disconnectSessionAndChannel(Session session, Channel channel) {
		if (channel != null)
			channel.disconnect();
		if (session != null)
			session.disconnect();
	}

	public static void main(String[] args) throws IOException {

		// String shell = SchedulerFileUtils.readFile("templates/test.sh");
		/*
		 * String shell =
		 * "sh /app/aimon/check_host.sh /app/aimon/bin/,/app/aimon/sbin/,/app/aimon/logs/,/app/aimon/ftpDir/,/app/aimon/config/,/app/aimon/lib/,/app/aimon/configext/"
		 * ; System.out.println(execRomoteCmd("aischedweb", "Aischedweb@2015", "20.26.12.158", null, shell));
		 */
		long start = System.currentTimeMillis();
		String cmd = "tar zxf /app/aischedweb/dpk/6337.tar.gz -C /app/aischedweb/dpk/ftpDir;rm -rf /app/aischedweb/dpk/config/*;unzip /app/aischedweb/dpk/ftpDir/process_config.jar -d /app/aischedweb/dpk/config/;rm -rf /app/aischedweb/dpk/lib/*;unzip /app/aischedweb/dpk/ftpDir/process_lib.jar -d /app/aischedweb/dpk/lib/;rm -rf /app/aischedweb/dpk/configext/*;unzip /app/aischedweb/dpk/ftpDir/process_aiams_dzd_configext.jar -d /app/aischedweb/dpk/configext/";
		for (int i = 0; i < 1; i++) {
			System.out.println(execRomoteCmd("aischedweb", "Aischedweb@2015", "20.26.12.158", null, cmd));
		}
		System.out.println("cost:" + (System.currentTimeMillis() - start));

	}
	public static void main2(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String cmd = "";
		while (!".".equals(cmd = reader.readLine())) {
			long start = System.currentTimeMillis();
			System.out.println(execRomoteCmd("aiams", "aiams", "20.26.12.158", null, cmd));
			// System.out.println("tester:");
			// jschShell("aiams", "aiams", "20.26.12.158", null, cmd);
			// System.out.println(execRomoteCmd("sweepmonk2014", "sdd123", "192.168.38.128", null, cmd));
			long internal = System.currentTimeMillis() - start;
			System.out.println("cost:" + internal);
		}
	}
}
