package com.asiainfo.monitor.busi.stat;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.interapi.api.JVM5Api;
import com.asiainfo.monitor.interapi.config.AIMemoryInfo;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class JvmMultitaskStat extends MultitaskStat {

	private static transient Log log = LogFactory.getLog(JvmMultitaskStat.class);
	
	public JvmMultitaskStat(int threadCount, int timeOut, Object[] ids) {
		super(threadCount, timeOut, ids,null);
		// TODO 自动生成构造函数存根
	}

	@Override
	public void summary(List reqVector, Map summaryMap) throws Exception {
		if (reqVector!=null && reqVector.size()>0){
			summaryMap.put( ((AIMemoryInfo)reqVector.get(0)).getIdCode(),(AIMemoryInfo)reqVector.get(0));
		}
	}
	

	@Override
	public List getSummary(String id) throws Exception {
		List result=null;
		try{
			IAIMonServerSV serverSV = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer = serverSV.getServerByServerId(id);
			if (appServer == null){
				// "获取服务统计信息时,应用["+id+"]没找到定义信息"
				log.error(AIMonLocaleFactory.getResource("MOS0000089", id));
				return result;
			}
			if (appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equalsIgnoreCase("OFF") || appServer.getJmxSet().getValue().equalsIgnoreCase("FALSE")){
				// "指定应用[appId:"+id+"]没有启动jmx注册"
				log.error(AIMonLocaleFactory.getResource("MOS0000071", id));
				return result;
			}
			AIMemoryInfo jvmInfo = JVM5Api.getMemoryInfo(appServer.getLocator_Type(),appServer.getLocator());
			if (jvmInfo != null){
				result = new ArrayList(1);
				
				jvmInfo.setIdCode(id);
				jvmInfo.setName(appServer.getApp_Name());
				long total = new Long(jvmInfo.getTotal()/(1024*1024));
				jvmInfo.setTotal(total);
				long used = new Long(jvmInfo.getUsed()/(1024*1024));
				jvmInfo.setUsed(used);
				result.add(jvmInfo);
			}
			
		}catch(Exception e){
			log.error(AIMonLocaleFactory.getResource("MOS0000049")+e.getMessage());
		}
		return result;
	}

}
