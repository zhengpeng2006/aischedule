package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCtRecordSV;

/**
 * @author yangbin
 *
 */
public class CtRecordAction extends BaseAction {

	private static transient Log log = LogFactory.getLog(AppTraceAction.class);
	
	private final static String WEB_TRACE_TYPE = "1";
	private final static String APP_TRACE_TYPE = "3";
	private final static String ACTION_STATIC = "5";
	private final static String SERVICE_STATIC = "7";
	private final static String SQL_STATIC = "9";
	
	public String getAllControlInfo() throws Exception {
		IAIMonCtRecordSV recordSV = (IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
		return recordSV.getAllControlInfo();
	}
	
	/**
	 * 获取启动的WebTrace信息
	 * @return
	 * @throws Exception
	 */
	public List getWebTraces() throws Exception {
		List result = new ArrayList();
		try {
			IAIMonCtRecordSV recordSV = (IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
			result = recordSV.getCtRecordByCtType(WEB_TRACE_TYPE);
		} catch (Exception e) {
			log.error("Call CtRecordAction's Method getWebTraces has Exception :" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取启动的AppTrace的信息
	 * @return
	 * @throws Exception
	 */
	public List getAppTrace() throws Exception {
		List result = new ArrayList();
		try {
			IAIMonCtRecordSV recordSV = (IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
			result = recordSV.getCtRecordByCtType(APP_TRACE_TYPE);
		} catch (Exception e) {
			log.error("Call CtRecordAction's Method getAppTrace has Exception :" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取启动的统计信息
	 * @return
	 * @throws Exception
	 */
	public List getActionStatistics(String ctType) throws Exception {
		List result = new ArrayList();
		try {
			IAIMonCtRecordSV recordSV = (IAIMonCtRecordSV)ServiceFactory.getService(IAIMonCtRecordSV.class);
			if ("action".equals(ctType)) {
				result = recordSV.getCtRecordByCtType(ACTION_STATIC);
			} else if ("service".equals(ctType)) {
				result = recordSV.getCtRecordByCtType(SERVICE_STATIC);
			} else if ("sql".equals(ctType)) {
				result = recordSV.getCtRecordByCtType(SQL_STATIC);
			} else {
				result = null;
			}
		} catch (Exception e) {
			log.error("Call CtRecordAction's Method getActionStatistics has Exception :" + e.getMessage());
		}
		return result;
	}
}
