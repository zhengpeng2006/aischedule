package com.asiainfo.batchwriteoff.util;

import com.trilead.ssh2.Connection;
import com.trilead.ssh2.SCPClient;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <p> @Description </p>
 * <p> @company Asiainfo-linkage (NJ) </p>
 * <p> @author 闫瑞国 </p>
 * <p> @Email yanrg@asiainfo-linkage.com </p>
 * <p> @createDate 2012-6-23 下午10:40:53 </p>
 * <p> @modifyDate </p>
 * <p> @version 1.0 </p>
 */
public final class SSHUtil {

	public static String ssh4Shell(String ip, int sshPort, String username, String password, String shellName, String shell) throws Exception {
		return ssh4Shell(ip, sshPort, username, password, shellName, null, shell);
	}

	public static String ssh4Shell(String ip, int sshPort, String username, String password, String shellName, String parameters, String shell) throws Exception {
		String rtn = null;
		Connection conn = null;
		Session sess = null;
		try {
			conn = new Connection(ip, sshPort);

			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);

			if (!(isAuthenticated)) {
				throw new IOException("认证失败");
			}

			String fileName = shellName + ".sh";

			upload(conn, ip, sshPort, username, password, shell.getBytes(), "/tmp/", fileName);
			sess = conn.openSession();

			if (StringUtils.isBlank(parameters)) {
				sess.execCommand(" chmod +x /tmp/" + fileName + " && /tmp/" + fileName + " && rm -rf /tmp/" + fileName);
			} else {
				sess.execCommand(" chmod +x /tmp/" + fileName + " && /tmp/" + fileName + " " + parameters + "  && rm -rf /tmp/" + fileName);
			}
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}

				rtn = line;
			}

		} catch (Exception ex) {
		} finally {
			if (sess != null) {
				sess.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return rtn;
	}

	public static String[] ssh4Shell(String ip, int sshPort, String username, String password, String[] shellName, String[] shell) throws Exception {
		return ssh4Shell(ip, sshPort, username, password, shellName, null, shell);
	}

	public static String[] ssh4Shell(String ip, int sshPort, String username, String password, String[] shellName, String[] parameters, String[] shell) throws Exception {
		String[] rtn = new String[shellName.length];
		Connection conn = null;
		try {
			conn = new Connection(ip, sshPort);

			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);

			if (!(isAuthenticated)) {
				throw new IOException("认证失败");
			}

			String[] fileName = new String[shellName.length];

			byte[][] b = new byte[shellName.length][];
			for (int i = 0; i < shellName.length; ++i) {
				fileName[i] = shellName[i] + ".sh";
				b[i] = shell[i].getBytes();
			}
			upload(conn, ip, sshPort, username, password, b, "/tmp/", fileName);

			for (int i = 0; i < shellName.length; i++) {
				Session sess = conn.openSession();
				try {
					if ((parameters == null) || (StringUtils.isBlank(parameters[i]))) {
						sess.execCommand(" chmod +x /tmp/" + fileName[i] + " && /tmp/" + fileName[i] + " && rm -rf /tmp/" + fileName[i]);
					} else {
						sess.execCommand(" chmod +x /tmp/" + fileName[i] + " && /tmp/" + fileName[i] + " " + parameters[i] + "  && rm -rf /tmp/" + fileName[i]);
					}
					InputStream stdout = new StreamGobbler(sess.getStdout());
					BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
					while (true) {
						String line = br.readLine();
						if (line == null) {
							break;
						}

						rtn[i] = line;
					}

				} catch (Exception ex) {
				} finally {
					if (sess != null)
						sess.close();
				}
			}
		} catch (Exception ex) {
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return rtn;
	}

	public static String ssh4Command(String ip, int sshPort, String username, String password, String command,boolean isSynch) throws Exception {
		
		StringBuilder rtn = new StringBuilder();
		Connection conn = null;
		Session sess = null;
		try {
			conn = new Connection(ip, sshPort);

			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);

			if (!(isAuthenticated)) {
				throw new IOException("认证失败");
			}

			sess = conn.openSession();

			sess.execCommand(command);

			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}else {
					if(StringUtils.isNotBlank(line)){
						rtn.append(line);
						if(!isSynch && "---------------------".equals(line)){
							break;
						}
					}
					
				}
				
			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			if (sess != null) {
				sess.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return rtn.toString();
	}
	

	public static void upload(Connection conn, String ip, int sshPort, String username, String password, byte[] bytes, String destPath, String fileName) throws Exception {
		try {
			SCPClient scp = conn.createSCPClient();
			scp.put(bytes, fileName, destPath);
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static void upload(Connection conn, String ip, int sshPort, String username, String password, byte[][] bytes, String destPath, String[] fileName) throws Exception {
		try {
			SCPClient scp = conn.createSCPClient();
			for (int i = 0; i < fileName.length; ++i)
				scp.put(bytes[i], fileName[i], destPath);
		} catch (Exception ex) {
			throw ex;
		}
	}
}