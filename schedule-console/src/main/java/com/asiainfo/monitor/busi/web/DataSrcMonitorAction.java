package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowDsTsSV;
import com.asiainfo.monitor.interapi.config.AITmSummary;

public class DataSrcMonitorAction extends BaseAction {

	private static transient Log log=LogFactory.getLog(DataSrcMonitorAction.class);
	
	/**
	 * 读取事务概况信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getTransactions(Object[] appIds) throws Exception {
		if (appIds == null || appIds.length < 1){
			return null;
		}
		List result = null;
		try{
			IAPIShowDsTsSV showDsTsSV = (IAPIShowDsTsSV)ServiceFactory.getService(IAPIShowDsTsSV.class);
			result = showDsTsSV.getTmStatInfo(appIds);
		}catch(Exception e){
			log.error("Call DataSrcMonitorAction's Method getTransactions has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 读取数据源配置文件
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getDataSourceConfig(String appId)throws Exception{
		List result = new ArrayList();
		try {
			IAPIShowDsTsSV showDsTsSV = (IAPIShowDsTsSV)ServiceFactory.getService(IAPIShowDsTsSV.class);
			result = showDsTsSV.getDataSourceConfig(appId);
		} catch (Exception e) {
			log.error("Call DataSrcMonitorAction's Method getDataSourceConfig has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 读取数据源配置文件(AppFrame)
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public DataContainerInterface[] getDataSourceConfigForAppFrame(String appId)throws Exception{
		DataContainerInterface[] retDBInfo = null;
		try {
			IAPIShowDsTsSV showDsTsSV = (IAPIShowDsTsSV)ServiceFactory.getService(IAPIShowDsTsSV.class);
			retDBInfo = showDsTsSV.getDataSourceConfigForAppFrame(appId);
		} catch (Exception e) {
			log.error("Call DataSrcMonitorAction's Method getDataSourceConfig has Exception :"+e.getMessage());
		}
		return retDBInfo;
	}
	
	/**
	 * 获取运行时数据源信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getDataSourceRuntime(String appId)throws Exception{
		List result= new ArrayList();
		try {
			IAPIShowDsTsSV showDsTsSV=(IAPIShowDsTsSV)ServiceFactory.getService(IAPIShowDsTsSV.class);
			result=showDsTsSV.getDataSourceRuntime(appId);
		} catch (Exception e) {
			log.error("Call DataSrcMonitorAction's Method getDataSourceRuntime has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取运行时数据源信息
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getDataSourceRuntimes(Object[] appIds)throws Exception{
		List result= new ArrayList();
		try {
			IAPIShowDsTsSV showDsTsSV=(IAPIShowDsTsSV)ServiceFactory.getService(IAPIShowDsTsSV.class);
			result=showDsTsSV.getDataSourceRuntimes(appIds);
		} catch (Exception e) {
			log.error("Call DataSrcMonitorAction's Method getDataSourceRuntimes has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 读取事务概况信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public AITmSummary getTransaction(String appId) throws Exception {
		if (StringUtils.isBlank(appId))
			return null;
		AITmSummary result=null;
		try{
			IAPIShowDsTsSV showDsTsSV=(IAPIShowDsTsSV)ServiceFactory.getService(IAPIShowDsTsSV.class);
			result=showDsTsSV.getTransaction(appId);
		}catch(Exception e){
			log.error("Call DataSrcMonitorAction's Method getTransactions has Exception :"+e.getMessage());
		}
		return result;
	}
	
}