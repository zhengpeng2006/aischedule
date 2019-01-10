package com.asiainfo.monitor.busi.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.interapi.api.user.UserManagerMonitorApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class UserMultitaskStat extends MultitaskStat {

	private final static transient Log log=LogFactory.getLog(UserMultitaskStat.class);
	
	
	public UserMultitaskStat(int threadCount, int timeOut, Object[] ids) {
		super(threadCount, timeOut, ids,null);
		// TODO 自动生成构造函数存根
	}

	@Override
	public void summary(List reqVector, Map summaryMap) throws Exception {
		if (reqVector!=null && reqVector.size()>0){
			Map userInfo=(Map)reqVector.get(0);
			String server=String.valueOf(userInfo.get("SERVER_NAME"));
			summaryMap.put(server,reqVector);
		}
	}

	@Override
	public List getSummary(String id) throws Exception {
		List result = null;		
		try {
			if (StringUtils.isNotBlank(id)){
				result = new ArrayList();
				IAIMonServerSV serverSV = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
				ServerConfig appServer = serverSV.getServerByServerId(id);
				if (appServer == null){ 
					return null;
				}
				
				if (appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equalsIgnoreCase("OFF") || appServer.getJmxSet().getValue().equalsIgnoreCase("FALSE")){
					// "指定应用[appId:"+id+"]没有启动jmx注册"
					log.error(AIMonLocaleFactory.getResource("MOS0000071", id));
					return result;
				}
				if (appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equalsIgnoreCase("OFF") || appServer.getJmxSet().getValue().equalsIgnoreCase("FALSE")){
					return null;
				}
				List userInfos = UserManagerMonitorApi.getOnlineUsers(appServer.getLocator_Type(),appServer.getLocator());
				result.addAll(userInfos);
			}
		} catch (Exception e) {
			log.error("Call UserMultitaskStat Method getSummary has Exception App["+id+"] :"+e.getMessage());
		}
		return result;
	}

}
