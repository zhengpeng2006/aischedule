package com.asiainfo.deploy.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import junit.framework.TestCase;

import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.deploy.common.constants.Category.FtpType;
import com.asiainfo.deploy.exception.ExecuteResult;
import com.asiainfo.deploy.filetransfer.FtpFactory;
import com.asiainfo.deploy.filetransfer.Operator;
import com.asiainfo.monitor.common.CommonConst;

/**
 * sftp测试
 * @author 孙德东(24204)
 */
public class SftpOperatorTest extends TestCase {

	private Random random = new Random();
	
	public void testSftpPut() {
		Operator sftp = FtpFactory.getFileOperator(FtpType.SFTP);

		Map<String, String> node = new HashMap<String, String>();
		node.put(DeployConstants.MapKey.KEY_USER_NAME, "sweepmonk2014");
		node.put(DeployConstants.MapKey.KEY_USER_PASSWD, "sdd123");
		node.put(DeployConstants.MapKey.KEY_HOST_IP, "192.168.38.128");
		node.put(CommonConst.CON_TYPE_SFTP, "22");

		String src = "H:/netty-5.0.0.Alpha1.tar.bz2";
		String dest = "/home/sweepmonk2014/upload/netty-5.0.0.Alpha1.tar.bz2";
		ExecuteResult result = sftp.put(node, src, dest);

		System.out.println(result);
	}

	public void testSftpGet() {
		Operator sftp = FtpFactory.getFileOperator(FtpType.SFTP);

		Map<String, String> node = new HashMap<String, String>();
		node.put(DeployConstants.MapKey.KEY_USER_NAME, "sweepmonk2014");
		node.put(DeployConstants.MapKey.KEY_USER_PASSWD, "sdd123");
		node.put(DeployConstants.MapKey.KEY_HOST_IP, "192.168.38.128");
		node.put(CommonConst.CON_TYPE_SFTP, "22");

		String src = "H:/netty-5.0.0.Alpha1.tar.bz2" + random.nextInt(100);
		String dest = "/home/sweepmonk2014/upload/netty-5.0.0.Alpha1.tar.bz2";
		ExecuteResult result = sftp.get(node, dest, src);

		System.out.println(result);
	}

	public void testFtpPut() {
		Operator ftp = FtpFactory.getFileOperator(FtpType.FTP);

		Map<String, String> node = new HashMap<String, String>();
		node.put(DeployConstants.MapKey.KEY_USER_NAME, "sweepmonk2014");
		node.put(DeployConstants.MapKey.KEY_USER_PASSWD, "sdd123");
		node.put(DeployConstants.MapKey.KEY_HOST_IP, "192.168.38.128");
		node.put(CommonConst.CON_TYPE_SFTP, "21");

		String src = "H:/netty-5.0.0.Alpha1.tar.bz2";
		String dest = "/home/sweepmonk2014/upload/netty-5.0.0.Alpha1.tar.bz2" + random.nextInt(100);
		ExecuteResult result = ftp.put(node, src, dest);

		System.out.println(result);
	}

	public void testFtpGet() {
		Operator sftp = FtpFactory.getFileOperator(FtpType.FTP);

		Map<String, String> node = new HashMap<String, String>();
		node.put(DeployConstants.MapKey.KEY_USER_NAME, "sweepmonk2014");
		node.put(DeployConstants.MapKey.KEY_USER_PASSWD, "sdd123");
		node.put(DeployConstants.MapKey.KEY_HOST_IP, "192.168.38.128");
		node.put(CommonConst.CON_TYPE_SFTP, "21");

		String src = "H:/netty-5.0.0.Alpha1.tar.bz2" + random.nextInt(100);
		String dest = "/home/sweepmonk2014/upload/netty-5.0.0.Alpha1.tar.bz2";
		ExecuteResult result = sftp.get(node, dest, src);

		System.out.println(result);
	}
}
