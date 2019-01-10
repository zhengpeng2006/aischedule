package com.asiainfo.monitor.tools.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.SCPClient;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;

public class SSHUtil2 {
	
	public SSHUtil2() {
	}

	/**
	 * 采集文件
	 * @param ip:主机IP
	 * @param sshPort:端口
	 * @param username:用户名
	 * @param password:密码
	 * @param shellFile:Shell文件，要求全路径,例如:/tmp/aitrc_tmp/collect_trc.sh
	 * @param localPath:采集后存储本地路径:
	 * @param remotePath:采集远程文件路径:/tmp/
	 * @return
	 * @throws Exception
	 */
	public static int collectFile(String ip,int sshPort,String username,String password,String shellFile,String localPath,String remotePath,String type, String code) throws Exception {
		int rtn = 0;
		Connection conn = null;
		Session sess = null;
		try {

			conn = new Connection(ip, sshPort);

			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username,password);

			if (isAuthenticated == false) {
				// "认证失败"
				throw new IOException(AIMonLocaleFactory.getResource("MOS0000271"));
			}

			//上传文件
			String fileName = "";//collect_trc.sh
			if (StringUtils.isNotBlank(shellFile)) {
				fileName = shellFile.substring(shellFile.lastIndexOf("/") + 1);
			}

			String shell = "";// /tmp/aitrc_tmp/collect_trc.sh
			if (StringUtils.isNotBlank(shellFile)) {
				shell = shellFile;
			}
			
			
			byte[] bb = FileUtils.readFileToByteArray(new File(shell));//"/tmp/aitrc_tmp/collect_trc.sh"

			String remoteTmp = "/tmp/";
			if (StringUtils.isNotBlank(remotePath)) {
				remoteTmp = remotePath;
			}
			upload(conn, ip, sshPort, username, password, bb, remoteTmp,fileName);//"/tmp/"
			
			sess = conn.openSession();

			if (StringUtils.isNotEmpty(code)) {
				sess.execCommand(" chmod +x " + remoteTmp + fileName + " && "+ remoteTmp + fileName + " " + code + " && rm -rf " + remoteTmp+ fileName);
			} else {
				sess.execCommand(" chmod +x " + remoteTmp + fileName + " && "+ remoteTmp + fileName + " && rm -rf " + remoteTmp+ fileName);
			}

			List list = new ArrayList();
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				} else {
					if (StringUtils.isNotBlank(type) && (StringUtils.contains(line, type) || StringUtils.contains(line.toUpperCase(),type.toUpperCase()))) {
						list.add(line);
					}else if (StringUtils.isBlank(type)){
						list.add(line);
					}
				}
			}

			rtn = list.size();
			String localTmp = "/tmp/aitrc_tmp/data/";
			if (StringUtils.isNotBlank(localPath)) {
				localTmp = localPath;
			}
			download(conn, ip, sshPort, username, password, (String[]) list.toArray(new String[0]), localTmp);

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
		return rtn;
	}

	/**
	 *
	 * @param conn Connection
	 * @param ip String
	 * @param sshPort int
	 * @param username String
	 * @param password String
	 * @param bytes byte[]
	 * @param destPath String
	 * @param fileName String
	 * @throws Exception
	 */
	public static void upload(Connection conn, String ip, int sshPort,
			String username, String password, byte[] bytes, String destPath,
			String fileName) throws Exception {
		try {
			SCPClient scp = conn.createSCPClient();
			scp.put(bytes, fileName, destPath);
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 *
	 * @param conn Connection
	 * @param ip String
	 * @param sshPort int
	 * @param username String
	 * @param password String
	 * @param remoteFileName String[]
	 * @param localPath String
	 * @throws Exception
	 */
	public static void download(Connection conn, String ip, int sshPort,
			String username, String password, String[] remoteFileName,
			String localPath) throws Exception {
		try {
			SCPClient scp = conn.createSCPClient();
			scp.get(remoteFileName, localPath);
		} catch (Exception ex) {
			throw ex;
		}
	}

}
