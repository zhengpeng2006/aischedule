package com.asiainfo.monitor.busi.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.datasource.DataSourceRuntime;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.interapi.api.transaction.TransactionMonitorApi;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class DataSourceMultitaskStat extends MultitaskStat {

	private static transient Log log = LogFactory.getLog(DataSourceMultitaskStat.class);
	
	public DataSourceMultitaskStat(int threadCount, int timeOut, Object[] ids) {
		super(threadCount, timeOut, ids,null);
		// TODO 自动生成构造函数存根
	}

	@Override
	public void summary(List reqVector, Map summaryMap) throws Exception {
		if (reqVector!=null && reqVector.size()>0){
			DataSourceRuntime dsItem=null;
			for (int i=0;i<reqVector.size();i++){
				String key=((DataSourceRuntime)reqVector.get(i)).getDataSource();
				if (!summaryMap.containsKey(key)){
					summaryMap.put(key,(DataSourceRuntime)reqVector.get(i));
				}else{
					dsItem=(DataSourceRuntime)summaryMap.get(key);
					dsItem.setNumActive(dsItem.getNumActive()+((DataSourceRuntime)reqVector.get(i)).getNumActive());
					dsItem.setNumIdle(dsItem.getNumIdle()+((DataSourceRuntime)reqVector.get(i)).getNumIdle());
					dsItem.setNumPhysical(dsItem.getNumPhysical()+((DataSourceRuntime)reqVector.get(i)).getNumPhysical());
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
			if (!appServer.supportDataSource()){
				log.error(AIMonLocaleFactory.getResource("MOS0000316",id));
				return result;
			}
			DataSourceRuntime[] summarys = TransactionMonitorApi.fetchAllDataSourceRuntime(appServer.getLocator_Type(),appServer.getLocator());
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
