package com.asiainfo.monitor.busi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.complex.mbean.standard.datasource.DataSourceRuntime;
import com.ai.appframe2.complex.mbean.standard.datasource.DataSourceSummary;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.config.ServerConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowDsTsSV;
import com.asiainfo.monitor.busi.stat.DataSourceMultitaskStat;
import com.asiainfo.monitor.busi.stat.TmMultitaskStat;
import com.asiainfo.monitor.interapi.api.transaction.TransactionMonitorApi;
import com.asiainfo.monitor.interapi.config.AITmSummary;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class APIShowDsTsSVImpl implements IAPIShowDsTsSV {

	private static transient Log log=LogFactory.getLog(APIShowDsTsSVImpl.class);
	
	/**
	 * 读取事务概况信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public AITmSummary getTransaction(String appId) throws Exception {
		if (StringUtils.isBlank(appId))
			return null;
		AITmSummary result = null;
		try {
			IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory
					.getService(IAIMonServerSV.class);
			ServerConfig appServer = serverSV.getServerByServerId(appId);
			if (appServer == null) {
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070",
						appId));
			}
			if (appServer.getJmxSet() == null
					|| appServer.getJmxSet().getValue().equals("OFF")
					|| appServer.getJmxSet().getValue().equals("FALSE")) {
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071",
						appId));
			}
			result = TransactionMonitorApi.getOpenTransaction(
					appServer.getLocator_Type(), appServer.getLocator());
	
		} catch (Exception e) {
			log.error("Call APIShowDsTsSVImpl's Method getTransactions has Exception :"
					+ e.getMessage());
		}
		return result;
	}

	/**
	 * 获取运行时数据源信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getDataSourceRuntime(String appId) throws Exception {
		List result = new ArrayList();
		try {
			IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory
					.getService(IAIMonServerSV.class);
			ServerConfig appServer = serverSV.getServerByServerId(appId);
			if (appServer == null) {
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070",
						appId));
			}
			if (appServer.getJmxSet() == null
					|| appServer.getJmxSet().getValue().equals("OFF")
					|| appServer.getJmxSet().getValue().equals("FALSE")) {
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071",
						appId));
			}
			DataSourceRuntime[] dsr = TransactionMonitorApi
					.fetchAllDataSourceRuntime(appServer.getLocator_Type(),
							appServer.getLocator());
			for (int i = 0; i < dsr.length; i++) {
				result.add(dsr[i]);
			}
		} catch (Exception e) {
			log.error("Call APIShowDsTsSVImpl's Method getDataSourceRuntime has Exception :"
					+ e.getMessage());
		}
		return result;
	}

	/**
	 * 读取多应用的事务统计信息
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getTmStatInfo(Object[] appIds) throws Exception {
		List result = null;
		try {
			result = new ArrayList();
			int threadCount = (appIds.length >> 1) + 1;
			int cpuCount = Runtime.getRuntime().availableProcessors();
			if (threadCount > (cpuCount * 3)) {
				threadCount = cpuCount * 3;
			}
			TmMultitaskStat tmStat = new TmMultitaskStat(threadCount, -1, appIds);
			Map tmMap = tmStat.getSummary();
			if (tmMap != null && tmMap.size() > 0) {
				Iterator it = tmMap.entrySet().iterator();
				while (it.hasNext()) {
					Map item = new HashMap();
					Entry entry = (Entry) it.next();
					item.put("COMMITCOUNT",
							((AITmSummary) entry.getValue()).getCommitCount());
					item.put("RESUMECOUNT",
							((AITmSummary) entry.getValue()).getResumeCount());
					item.put("ROLLBACKCOUNT",
							((AITmSummary) entry.getValue()).getRollbackCount());
					item.put("STARTCOUNT",
							((AITmSummary) entry.getValue()).getStartCount());
					item.put("SUSPENDCOUNT",
							((AITmSummary) entry.getValue()).getSuspendCount());
					result.add(item);
				}
			}
		} catch (Exception e) {
			log.error("Call APIShowDsTsSVImpl's Method getTransactions has Exception :"
					+ e.getMessage());
		}
		return result;
	}

	/**
	 * 读取数据源配置文件(AppFrame)
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getDataSourceConfigForAppFrame(String appId)
			throws Exception {
		List result = new ArrayList();
		try {
			IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory
					.getService(IAIMonServerSV.class);
			ServerConfig appServer = serverSV.getServerByServerId(appId);
			if (appServer == null) {
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070",
						appId));
			}
			if (appServer.getJmxSet() == null
					|| appServer.getJmxSet().getValue().equals("OFF")
					|| appServer.getJmxSet().getValue().equals("FALSE")) {
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071",
						appId));
			}
			DataSourceSummary[] cs = TransactionMonitorApi
					.fetchAllDataSourceConfig(appServer.getLocator_Type(),
							appServer.getLocator());
			for (int i = 0; i < cs.length; i++) {
				DataContainerInterface item = new DataContainer();
				item.set("DRIVER_CLASS_NAME", cs[i].getDataSource());
				item.set("URL", cs[i].getUrl());
				item.set("USER_NAME", cs[i].getUsername());
				item.set("MAX_ACTIVE", cs[i].getMaxActive());
				item.set("MAX_IDLE", cs[i].getMaxIdle());
				item.set("MIN_IDLE", cs[i].getMinIdle());
				item.set("INITIAL_SIZE", cs[i].getInitialSize());
				item.set("MAX_WAIT", cs[i].getMaxWait());
				item.set("DATA_SOURCE", cs[i].getDataSource());
	
				result.add(item);
			}
		} catch (Exception e) {
			log.error("Call APIShowDsTsSVImpl's Method getDataSourceConfig has Exception :"
					+ e.getMessage());
		}
		return (DataContainerInterface[]) result
				.toArray(new DataContainerInterface[0]);
	}

	/**
	 * 读取数据源配置文件
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getDataSourceConfig(String appId) throws Exception {
		List result = new ArrayList();
		try {
			IAIMonServerSV serverSV = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
			ServerConfig appServer = serverSV.getServerByServerId(appId);
			if (appServer == null) {
				// 没找到应用实例[{0}]
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000070", appId));
			}
			if (appServer.getJmxSet() == null || appServer.getJmxSet().getValue().equals("OFF") || appServer.getJmxSet().getValue().equals("FALSE")) {
				// "应用端[appId:"+appId+"]未启动Jmx注册"
				throw new Exception(AIMonLocaleFactory.getResource("MOS0000071", appId));
			}
			DataSourceSummary[] cs = TransactionMonitorApi.fetchAllDataSourceConfig(appServer.getLocator_Type(), appServer.getLocator());
			for (int i = 0; i < cs.length; i++) {
				result.add(cs[i]);
			}
		} catch (Exception e) {
			log.error("Call APIShowDsTsSVImpl's Method getDataSourceConfig has Exception :" + e.getMessage());
		}
		return result;
	}

	/**
	 * 获取多应用运行时数据源统计信息
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getDataSourceRuntimes(Object[] appIds) throws Exception {
		List result = new ArrayList();
		try {
			int threadCount = (appIds.length >> 1) + 1;
			int cpuCount = Runtime.getRuntime().availableProcessors();
			if (threadCount > (cpuCount * 3))
				threadCount = cpuCount * 3;
			DataSourceMultitaskStat dsStat = new DataSourceMultitaskStat(
					threadCount, -1, appIds);
			Map dsMap = dsStat.getSummary();
			if (dsMap != null && dsMap.size() > 0) {
				Iterator it = dsMap.entrySet().iterator();
				while (it.hasNext()) {
					Entry entry = (Entry) it.next();
					Map item = new HashMap();
					item.put("DATASOURCE",
							((DataSourceRuntime) entry.getValue()).getDataSource());
					item.put("NUMACTIVE",
							((DataSourceRuntime) entry.getValue()).getNumActive());
					item.put("NUMIDLE",
							((DataSourceRuntime) entry.getValue()).getNumIdle());
					item.put("NUMPHYSICAL",
							((DataSourceRuntime) entry.getValue()).getNumPhysical());
					result.add(item);
				}
			}
		} catch (Exception e) {
			log.error("Call APIShowDsTsSVImpl's Method getDataSourceRuntime has Exception :"
					+ e.getMessage());
		}
		return result;
	}
}
