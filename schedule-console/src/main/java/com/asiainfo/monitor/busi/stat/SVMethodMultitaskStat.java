package com.asiainfo.monitor.busi.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.sv.SVMethodSummary;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.interapi.api.statistics.SVMethodMonitorApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;
import com.asiainfo.monitor.tools.util.TypeConst;

public class SVMethodMultitaskStat extends MultitaskStat {

	private static transient Log log = LogFactory.getLog(SVMethodMultitaskStat.class);
	
	
	public SVMethodMultitaskStat(int threadCount, int timeOut, Object[] ids,String condition) {
		super(threadCount, timeOut, ids,condition);
	}

	@Override
	public void summary(List reqVector, Map summaryMap) throws Exception {
		if (reqVector!=null && reqVector.size()>0){
			SVMethodSummary svMethodItem=null;
			for (int j=0;j<reqVector.size();j++){
				String key=((SVMethodSummary)reqVector.get(j)).getClassName()+TypeConst._SPLIT_CHAR+((SVMethodSummary)reqVector.get(j)).getMethodName();
				if (!summaryMap.containsKey(key)){
					summaryMap.put(key,(SVMethodSummary)reqVector.get(j));
				}else{
					svMethodItem=(SVMethodSummary)summaryMap.get(key);
					
					if (((SVMethodSummary)reqVector.get(j)).getLast()>svMethodItem.getLast()){
						svMethodItem.setLast(((SVMethodSummary)reqVector.get(j)).getLast());
					}
					if (((SVMethodSummary)reqVector.get(j)).getLastUseTime()>svMethodItem.getLastUseTime()){
						svMethodItem.setLastUseTime(((SVMethodSummary)reqVector.get(j)).getLastUseTime());
					}
					long failCount=svMethodItem.getFailCount()+((SVMethodSummary)reqVector.get(j)).getFailCount();
					svMethodItem.setFailCount(failCount);
					
					long succCount=svMethodItem.getSuccessCount()+((SVMethodSummary)reqVector.get(j)).getSuccessCount();
					svMethodItem.setSuccessCount(succCount);
					
					long totCount=svMethodItem.getTotalCount()+((SVMethodSummary)reqVector.get(j)).getTotalCount();
					svMethodItem.setTotalCount(totCount);
					
					long totTime=svMethodItem.getTotalUseTime()+((SVMethodSummary)reqVector.get(j)).getTotalUseTime();
					svMethodItem.setTotalUseTime(totTime);
					
					svMethodItem.setAvg(svMethodItem.getTotalUseTime()/svMethodItem.getTotalCount());
					
					if (((SVMethodSummary)reqVector.get(j)).getMax()>svMethodItem.getMax()){
						svMethodItem.setMax(((SVMethodSummary)reqVector.get(j)).getMax());
					}
					if (((SVMethodSummary)reqVector.get(j)).getMin()<svMethodItem.getMin()){
						svMethodItem.setMin(((SVMethodSummary)reqVector.get(j)).getMin());
					}							
				}
			}
		}
	}

	@Override
	public List getSummary(String id) throws Exception {
		List result=null;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer=serverSV.getServerByServerId(id);
			if (appServer==null){
				// "获取服务统计信息时,应用["+id+"]没找到定义信息"
				log.error(AIMonLocaleFactory.getResource("MOS0000089", id));
				return result;
			}
			if (appServer.getJmxSet()==null || appServer.getJmxSet().getValue().equalsIgnoreCase("OFF") ||
					appServer.getJmxSet().getValue().equalsIgnoreCase("FALSE")){
				// "指定应用[appId:"+id+"]没有启动jmx注册"
				log.error(AIMonLocaleFactory.getResource("MOS0000071", id));
				return result;
			}
			if (!appServer.supportMethod()){
				//该应用不支持Method检查和统计
				log.error(AIMonLocaleFactory.getResource("MOS0000314",id));
				return result;
			}
			boolean status=SVMethodMonitorApi.fetchState(appServer.getLocator_Type(),appServer.getLocator());
			if (!status){
				// "获取服务统计信息时,应用["+id+"]没有打开服务统计"
				log.error(AIMonLocaleFactory.getResource("MOS0000093", id));
				return result;
			}
			SVMethodSummary[] summarys=SVMethodMonitorApi.getSVMehtodInfo(appServer.getLocator_Type(),appServer.getLocator(),condition);
			if (summarys!=null && summarys.length>0){
				result=new ArrayList(summarys.length);
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
