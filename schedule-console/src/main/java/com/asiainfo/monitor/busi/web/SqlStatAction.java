package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowStatSV;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class SqlStatAction extends BaseAction {

	private static transient Log log = LogFactory.getLog(SqlStatAction.class);
	
	
	/**
	 * 根据应用标识，读取SQL统计信息
	 * @param appIds
	 * @param condition
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getSqlInfoByIds(Object[] appIds,String condition,Integer start,Integer end) throws Exception{
		List result = new ArrayList();
		try{
			IAPIShowStatSV showStatSV=(IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			result=showStatSV.getSqlInfoByIds(appIds,condition,start,end);
		}catch(Exception e){
			log.error("Call SqlStatAction's Method getSqlInfoByIds has Exception :"+e.getMessage());
		}
		return result;	
	}
	/**
	 * 查询实时sql统计信息
	 * @param appId
	 * @param condition
	 * @return
	 */
	public List getSqlInfo(String appId,String condition) throws Exception{
		List result = new ArrayList();
		try{
			IAPIShowStatSV showStatSV = (IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			result = showStatSV.getSqlInfo(appId,condition);
		}catch(Exception e){
			log.error("Call SqlStatAction's Method getSqlInfo has Exception :"+e.getMessage());
		}
		return result;		
	}
	
	/**
	 * 查询实时sql统计信息(AppFrame)
	 * @param appId
	 * @param condition
	 * @return
	 */	
	public DataContainerInterface[] getSqlInfoForAppFrame(String appId,String condition) throws Exception{
		DataContainerInterface[] retDBInfo = null;
		try{
			IAPIShowStatSV showStatSV = (IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			retDBInfo = showStatSV.getSqlInfoForAppFrame(appId, condition);
		}catch(Exception e){
			log.error("Call SqlStatAction's Method getSqlInfo has Exception :"+e.getMessage());
		}
		return retDBInfo;		
	}
	
	
	
	public boolean fetchSqlMonitorState(String appId) throws Exception{
		boolean result = false;
		try{
			IAPIShowStatSV showStatSV = (IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			
			result = showStatSV.fetchSqlMonitorState(appId);
		}catch(Exception e){
			log.error("Call SqlStatAction's Method fetchSqlMonitorState has Exception :"+e.getMessage());
			// "异常:"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000049")+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 检查多应用的SQL统计状态
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List checkSqlStatByAppIds(Object[] appIds) throws Exception{
		List result=null;
		try{
			IAPIShowStatSV showStatSV=(IAPIShowStatSV)ServiceFactory.getService(IAPIShowStatSV.class);
			result=showStatSV.checkSqlStatByAppIds(appIds);
		}catch(Exception e){
			log.error("Call SqlStatAction's Method checkSqlStatByAppIds has Exception :"+e.getMessage());
		}
		return result;
	}
}
