package com.asiainfo.monitor.busi.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.interapi.api.transaction.TransactionMonitorApi;
import com.asiainfo.monitor.interapi.config.AITmSummary;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class TmMultitaskStat extends MultitaskStat {

	private static transient Log log = LogFactory.getLog(TmMultitaskStat.class);
	
	public TmMultitaskStat(int threadCount, int timeOut, Object[] ids) {
		super(threadCount, timeOut, ids,null);
		// TODO 自动生成构造函数存根
	}

	@Override
	public void summary(List reqVector, Map summaryMap) throws Exception {
		if (reqVector!=null && reqVector.size()>0){
			AITmSummary tmItem=null;
			for (int j=0;j<reqVector.size();j++){
				AITmSummary key=(AITmSummary)reqVector.get(j);
				if (!summaryMap.containsKey("TM")){
					summaryMap.put("TM",key);
				}else{
					tmItem=(AITmSummary)summaryMap.get("TM");
					tmItem.setCommitCount(key.getCommitCount()+tmItem.getCommitCount());
					tmItem.setResumeCount(key.getResumeCount()+tmItem.getResumeCount());
					tmItem.setRollbackCount(key.getRollbackCount()+tmItem.getRollbackCount());
					tmItem.setStartCount(key.getStartCount()+tmItem.getStartCount());
					tmItem.setSuspendCount(key.getSuspendCount()+tmItem.getSuspendCount());
				}
			}
		}
	}

	@Override
	public List getSummary(String id) throws Exception {
		List result = null;
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
			AITmSummary summary = TransactionMonitorApi.getOpenTransaction(appServer.getLocator_Type(),appServer.getLocator());
			if (summary != null){
				result = new ArrayList(1);
				summary.setId(appServer.getApp_Id());
				summary.setCode(appServer.getApp_Code());
				summary.setName(appServer.getApp_Name());				
				result.add(summary);
			}
		}catch(Exception e){
			log.error(AIMonLocaleFactory.getResource("MOS0000049")+e.getMessage());
		}
		return result;
	}

}
