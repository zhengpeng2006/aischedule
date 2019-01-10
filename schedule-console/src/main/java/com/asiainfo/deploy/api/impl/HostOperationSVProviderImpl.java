package com.asiainfo.deploy.api.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.appframe.ext.flyingserver.org.apache.commons.lang.StringUtils;
import com.asiainfo.deploy.api.interfaces.IHostOperationSVProvider;
import com.asiainfo.deploy.common.constants.DeployConstants;
import com.asiainfo.monitor.service.ExternalSvUtil;

public class HostOperationSVProviderImpl implements IHostOperationSVProvider{

private static transient Log LOG = LogFactory.getLog(HostOperationSVProviderImpl.class);
	
	// ping命令超时时间
	private static final int PING_TIME_OUT = 3000;
	// 重试次数
	private static final int RETRY_TIMES = 4;
	
	@Override
	public boolean ping(String appCode) {
		InetAddress dst = null;
		try {
			String hostName = getHostByAppCode(appCode);
			dst = InetAddress.getByName(hostName);
			
		} catch (Exception e) {
			LOG.error("get host by appcode error," + e.getLocalizedMessage());
			return false;
		}
		
		boolean isReachable = false;
		int num = 0;
		while(num < RETRY_TIMES) {
			try {
				isReachable = dst.isReachable(PING_TIME_OUT);
			} catch (IOException e) {
				LOG.error("exception when executing InetAddress.isReachable." + e.getLocalizedMessage());
			}
			
			if (isReachable) break;
		}
		
		return isReachable;
	}

	/**
	 * 根据appcode获取对应
	 * @return
	 * @throws Exception 
	 */
	private String getHostByAppCode(String appCode) throws Exception {
		 Map<String, String> node = ExternalSvUtil.qryAppInfoByCondition(appCode);
		 return node != null ? node.get(DeployConstants.MapKey.KEY_HOST_IP) : StringUtils.EMPTY;
	}
	
	public static void main(String[] args) {
		InetAddress dst = null;
		try {
			//String hostName = "192.168.38.128";
			String hostName = "localhost";
			dst = InetAddress.getByName(hostName);
			
		} catch (Exception e) {
			LOG.error("get host by appcode error," + e.getLocalizedMessage());
		}
		
		boolean isReachable = false;
		int num = 0;
		while(num < RETRY_TIMES) {
			try {
				isReachable = dst.isReachable(PING_TIME_OUT);
			} catch (IOException e) {
				LOG.error("exception when executing InetAddress.isReachable." + e.getLocalizedMessage());
			}
			
			if (isReachable) break;
		}
		
		System.out.println(isReachable ? "success" : "unreachable");
	}
}
