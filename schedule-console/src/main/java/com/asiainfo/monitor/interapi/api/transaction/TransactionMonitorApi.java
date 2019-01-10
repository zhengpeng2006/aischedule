package com.asiainfo.monitor.interapi.api.transaction;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.mbean.standard.datasource.DataSourceMonitorMBean;
import com.ai.appframe2.complex.mbean.standard.datasource.DataSourceRuntime;
import com.ai.appframe2.complex.mbean.standard.datasource.DataSourceSummary;
import com.ai.appframe2.complex.mbean.standard.tm.TmSummary;
import com.ai.appframe2.complex.mbean.standard.tm.TransactionMonitorMBean;
import com.asiainfo.monitor.interapi.config.AITmSummary;
import com.asiainfo.monitor.tools.TransporterClientFactory;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class TransactionMonitorApi {

	
	private static transient Log log=LogFactory.getLog(TransactionMonitorApi.class);
	
	private static String S_TM_MBEAN_NAME="appframe:name=TransactionMonitor";
	
	private static String S_DS_MBEAN_NAME="appframe:name=DataSourceMonitor";
	public TransactionMonitorApi() {
		super();
		// TODO 自动生成构造函数存根
	}

	/**
	 * 获取的事务调用统计信息
	 * @param rmiUrl:应用注册Url
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public static AITmSummary getOpenTransaction(String locatorType,String locator) throws Exception, RemoteException {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator)){
			return null;
		}
		AITmSummary result = null;
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_TM_MBEAN_NAME,TransactionMonitorMBean.class);
			if (proxyObject != null){
				try{
					TmSummary tm = ((TransactionMonitorMBean)proxyObject).fetchTmSummary();
					if (tm != null){
						result = new AITmSummary();
						result.setCommitCount(tm.getCommitCount());
						result.setResumeCount(tm.getResumeCount());
						result.setRollbackCount(tm.getRollbackCount());
						result.setStartCount(tm.getStartCount());
						result.setSuspendCount(tm.getSuspendCount());
					}
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "TransactionMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;
	}
	
	/**
	 * 获取所有数据源
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public static String[] getAllDataSource(String locatorType,String locator) throws Exception, RemoteException {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		
		String[] result =null;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_DS_MBEAN_NAME,DataSourceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((DataSourceMonitorMBean)proxyObject).getAllDataSource();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "DataSourceMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;		

	}
	
	/**
	 * 获取数据源配置
	 * @param appSrvCfg
	 * @param ds
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public static DataSourceSummary[] fetchAllDataSourceConfig(String locatorType,String locator) throws Exception, RemoteException {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator)){
			return null;
		}
		
		DataSourceSummary[] result = null;
		try{
			Object proxyObject = TransporterClientFactory.getProxyObject(locatorType,locator,S_DS_MBEAN_NAME,DataSourceMonitorMBean.class);
			if (proxyObject != null){
				try{
					result = ((DataSourceMonitorMBean)proxyObject).fetchAllDataSourceConfig();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "DataSourceMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;		

	}
	
	/**
	 * 获得所有数据源的运行时信息
	 * @param appSrvCfg
	 * @return
	 * @throws Exception
	 * @throws RemoteException
	 */
	public static DataSourceRuntime[] fetchAllDataSourceRuntime(String locatorType,String locator) throws Exception, RemoteException {
		if (StringUtils.isBlank(locatorType) || StringUtils.isBlank(locator))
			return null;
		
		DataSourceRuntime[] result =null;
		try{
			Object proxyObject=TransporterClientFactory.getProxyObject(locatorType,locator,S_DS_MBEAN_NAME,DataSourceMonitorMBean.class);
			if (proxyObject!=null){
				try{
					result=((DataSourceMonitorMBean)proxyObject).fetchAllDataSourceRuntime();
				}catch(NoClassDefFoundError ex){
					throw new Exception(AIMonLocaleFactory.getResource("MOS0000273", locator) + "DataSourceMonitorMBean");
				}
			}
		}catch(Exception e){
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000327"));
		}
		
		return result;		

	}
	
}
