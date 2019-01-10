package com.asiainfo.monitor.busi.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.sql.SQLSummary;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.interapi.api.statistics.SQLMonitorApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class SqlMultitaskStat extends MultitaskStat {

	private static transient Log log = LogFactory.getLog(SqlMultitaskStat.class);

	
	public SqlMultitaskStat(int threadCount, int timeOut, Object[] ids,String condition) {
		super(threadCount, timeOut, ids,condition);
	}

	@Override
	public void summary(List reqVector, Map summaryMap) throws Exception {
		if (reqVector!=null && reqVector.size()>0){
			SQLSummary sqlItem=null;
			for (int j=0;j<reqVector.size();j++){
				String key=((SQLSummary)reqVector.get(j)).getSql();
				if (!summaryMap.containsKey(key)){
					summaryMap.put(key,reqVector.get(j));
				}else{
					sqlItem=(SQLSummary)summaryMap.get(key);
					
					if (((SQLSummary)reqVector.get(j)).getLast()>sqlItem.getLast()){
						sqlItem.setLast(((SQLSummary)reqVector.get(j)).getLast());
					}
					if (((SQLSummary)reqVector.get(j)).getLastUseTime()>sqlItem.getLastUseTime()){
						sqlItem.setLastUseTime(((SQLSummary)reqVector.get(j)).getLastUseTime());
					}
					
					long totTime=sqlItem.getTotalUseTime()+((SQLSummary)reqVector.get(j)).getTotalUseTime();
					sqlItem.setTotalUseTime(totTime);
					
					long totCount=sqlItem.getCount()+((SQLSummary)reqVector.get(j)).getCount();
					
					sqlItem.setCount(totCount);
					
					sqlItem.setAvg(sqlItem.getTotalUseTime()/totCount);
					
					if (((SQLSummary)reqVector.get(j)).getMax()>sqlItem.getMax()){
						sqlItem.setMax(((SQLSummary)reqVector.get(j)).getMax());
					}
					if (((SQLSummary)reqVector.get(j)).getMin()<sqlItem.getMin()){
						sqlItem.setMin(((SQLSummary)reqVector.get(j)).getMin());
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
			if (!appServer.supportSql()){
				//该应用不支持Sql检查和统计
				log.error(AIMonLocaleFactory.getResource("MOS0000313",id));
				return result;
			}
			boolean status=SQLMonitorApi.fetchStatus(appServer.getLocator_Type(),appServer.getLocator());
			if (!status){
				// "获取服务统计信息时,应用["+id+"]没有打开SQL统计"
				log.error(AIMonLocaleFactory.getResource("MOS0000092", id));
				return result;
			}
			SQLSummary[] summarys=SQLMonitorApi.getSQLInfo(appServer.getLocator_Type(),appServer.getLocator(), condition);
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
