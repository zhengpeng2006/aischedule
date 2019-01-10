package com.asiainfo.monitor.interapi.api.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.session.AppframeSessionMonitorMBean;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;


public class UserManagerMonitorApi {

	private final static transient Log log=LogFactory.getLog(UserManagerMonitorApi.class);
	
	private static String S_MBEAN_NAME="appframe:name=AppframeSessionMonitor";
	/**
	 * 获取在线用户信息
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 */
	public static List getOnlineUsers(String locatorType,String locator) throws Exception {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator)){
			return null;
		}
		List result = null;
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_MBEAN_NAME,AppframeSessionMonitorMBean.class);
			if (proxyObject != null){
				Map[] userInfos = null;
				try{
					userInfos = ((AppframeSessionMonitorMBean)proxyObject).fetchLogedUsers();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "AppframeSessionMonitorMBean");
				}
				if (userInfos != null && userInfos.length > 0){
					result = new ArrayList(userInfos.length);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i=0;i<userInfos.length;i++){
						if (userInfos[i] != null){
							userInfos[i].put("LOGIN_TIME", format.format((java.sql.Timestamp)userInfos[i].get("LOGIN_TIME")));
							result.add(userInfos[i]);
						}						
					}
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 强制用户退出
	 * @param appSrvCfg
	 * @param serialId
	 * @throws Exception
	 */
	public static void forceLogoutUser(String locatorType,String locator, String serialId) throws Exception {
		
	}
}
