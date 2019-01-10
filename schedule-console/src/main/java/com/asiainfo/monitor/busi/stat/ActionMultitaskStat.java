package com.asiainfo.monitor.busi.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.action.ActionSummary;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.interapi.api.statistics.ActionMonitorApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class ActionMultitaskStat extends MultitaskStat {

	private static transient Log log = LogFactory.getLog(ActionMultitaskStat.class);
	
	
	public ActionMultitaskStat(int threadCount, int timeOut, Object[] ids,String condition) {
		super(threadCount, timeOut, ids,condition);
	}

	@Override
	public void summary(List reqVector, Map summaryMap) throws Exception {
		if (reqVector!=null && reqVector.size()>0){
			ActionSummary actionItem=null;
			for (int j=0;j<reqVector.size();j++){
				String key=((ActionSummary)reqVector.get(j)).getClassName()+"|"+((ActionSummary)reqVector.get(j)).getMethodName();
				if (!summaryMap.containsKey(key)){
					summaryMap.put(key,(ActionSummary)reqVector.get(j));
				}else{
					actionItem=(ActionSummary)summaryMap.get(key);
					
					if (((ActionSummary)reqVector.get(j)).getLast()>actionItem.getLast()){
						actionItem.setLast(((ActionSummary)reqVector.get(j)).getLast());
					}
					if (((ActionSummary)reqVector.get(j)).getLastUseTime()>actionItem.getLastUseTime()){
						actionItem.setLastUseTime(((ActionSummary)reqVector.get(j)).getLastUseTime());
					}
					long failCount=actionItem.getFailCount()+((ActionSummary)reqVector.get(j)).getFailCount();
					actionItem.setFailCount(failCount);
					
					long succCount=actionItem.getSuccessCount()+((ActionSummary)reqVector.get(j)).getSuccessCount();
					actionItem.setSuccessCount(succCount);
					
					long totalCount=actionItem.getTotalCount()+((ActionSummary)reqVector.get(j)).getTotalCount();
					actionItem.setTotalCount(totalCount);
					
					long totTime=actionItem.getTotalUseTime()+((ActionSummary)reqVector.get(j)).getTotalUseTime();
					actionItem.setTotalUseTime(totTime);
					
					
					actionItem.setAvg(actionItem.getTotalUseTime()/actionItem.getTotalCount());
					
					if (((ActionSummary)reqVector.get(j)).getMax()>actionItem.getMax()){
						actionItem.setMax(((ActionSummary)reqVector.get(j)).getMax());
					}
					if (((ActionSummary)reqVector.get(j)).getMin()<actionItem.getMin()){
						actionItem.setMin(((ActionSummary)reqVector.get(j)).getMin());
					}							
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
				// "获取Action统计信息时,应用[{0}]没找到定义信息"
				log.error(AIMonLocaleFactory.getResource("MOS0000087", id));
				return result;
			}
			if (appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equalsIgnoreCase("OFF") || appServer.getJmxSet().getValue().equalsIgnoreCase("FALSE")){
				// "指定应用[appId:"+id+"]没有启动jmx注册"
				log.error(AIMonLocaleFactory.getResource("MOS0000071", id));
				return result;
			}
			if (!appServer.supportAction()){
				//该应用不支持Action检查和统计
				log.error(AIMonLocaleFactory.getResource("MOS0000315",id));
				return result;
			}
			
			boolean status = ActionMonitorApi.fetchStatus(appServer.getLocator_Type(),appServer.getLocator());
			if (!status){
				// "获取服务统计信息时,应用["+id+"]没有打开Action统计"
				log.error(AIMonLocaleFactory.getResource("MOS0000088", id));
				return result;
			}
			ActionSummary[] summarys = ActionMonitorApi.getActionInfo(appServer.getLocator_Type(),appServer.getLocator(),condition);
			if (summarys != null && summarys.length > 0){
				result = new ArrayList(summarys.length);
				for (int i=0;i<summarys.length;i++){
					result.add(summarys[i]);
				}
			}
			
		}catch(Exception e){
			log.error(AIMonLocaleFactory.getResource("MOS0000049")+e.getMessage());
		}
		return result;
	}

}
