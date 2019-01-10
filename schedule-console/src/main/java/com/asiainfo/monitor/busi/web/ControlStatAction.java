package com.asiainfo.monitor.busi.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlStatSV;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class ControlStatAction extends BaseAction {

	private static transient Log log = LogFactory.getLog(ControlStatAction.class);

	/**
	 * 启动在线服务统计监控
	 * @param appServerIds
	 * @param second
	 * @return
	 */
	public String startSVMethod(Object[] ids,String second) throws Exception {
		StringBuilder sb = new StringBuilder("");
		try {
			if (ids == null || ids.length<1){
				return sb.toString();
			}
			IAPIControlStatSV controlStatSV = (IAPIControlStatSV)ServiceFactory.getService(IAPIControlStatSV.class);
			List result = controlStatSV.startSVMethod(ids,second);
			for(int i=0;i<result.size();i++){
				SimpleResult sr = (SimpleResult)result.get(i);				
				if (sr.isSucc()){
					// 成功启动应用[{0}]在线服务统计监控
					sb.append(AIMonLocaleFactory.getResource("MOS0000015", sr.getName()));
					sb.append("\n");
				}else{
					sb.append(sr.getMsg());
					sb.append("\n");
				}
			}
		} catch (Exception e) {
			// 启动在线服务统计监控失败！
			throw new Exception (AIMonLocaleFactory.getResource("MOS0000016")+e.getMessage());
		}
		return sb.toString(); 
	}
	
	/**
	 * 停止在线服务统计监控
	 * @param appServerIds
	 * @param second
	 * @return
	 */
	public String stopSVMethod(Object[] ids) throws Exception {
		StringBuilder sb = new StringBuilder("");
		try {
			if (ids==null || ids.length<1)
				return sb.toString();
			IAPIControlStatSV controlStatSV=(IAPIControlStatSV)ServiceFactory.getService(IAPIControlStatSV.class);
			List result=controlStatSV.stopSVMethod(ids);
			for(int i=0;i<result.size();i++){
				SimpleResult sr=(SimpleResult)result.get(i);				
				if (sr.isSucc()){
					// "成功停止应用[{0}]在线服务统计监控"
					sb.append(AIMonLocaleFactory.getResource("MOS0000017", sr.getName()));
					sb.append("\n");
				}else{
					sb.append(sr.getMsg());
					sb.append("\n");
				}
			}
		} catch (Exception e) {
			// "停止在线服务统计监控失败！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000018")+e.getMessage());
		}
		return sb.toString();
	}
	
	/**
	 * 启动在线服务统计监控
	 * @param appServerIds
	 * @param second
	 * @return
	 */
	public String startSQL(Object[] ids,String second) throws Exception {
		StringBuilder sb = new StringBuilder("");
		try {
			if (ids==null || ids.length<1)
				return sb.toString();
			IAPIControlStatSV controlStatSV=(IAPIControlStatSV)ServiceFactory.getService(IAPIControlStatSV.class);
			List result=controlStatSV.startSQL(ids,second);
			for(int i=0;i<result.size();i++){
				SimpleResult sr=(SimpleResult)result.get(i);				
				if (sr.isSucc()){
					// "成功启动应用[{0}]在线SQL统计监控"
					sb.append(AIMonLocaleFactory.getResource("MOS0000019", sr.getName()));
					sb.append("\n");
				}else{
					sb.append(sr.getMsg());
					sb.append("\n");
				}
			}
		} catch (Exception e) {
			// "启动在线SQL统计监控失败！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000020")+e.getMessage());
		}
		return sb.toString(); 
	}
	/**
	 * 停止在线服务统计监控
	 * @param appServerIds
	 * @param second
	 * @return
	 */
	public String stopSQL(Object[] ids) throws Exception {
		StringBuilder sb = new StringBuilder("");
		try {
			if (ids==null || ids.length<1)
				return sb.toString();
			IAPIControlStatSV controlStatSV=(IAPIControlStatSV)ServiceFactory.getService(IAPIControlStatSV.class);
			List result=controlStatSV.stopSQL(ids);
			for(int i=0;i<result.size();i++){
				SimpleResult sr=(SimpleResult)result.get(i);				
				if (sr.isSucc()){
					// "成功停止应用[{0}]在线SQL统计监控"
					sb.append(AIMonLocaleFactory.getResource("MOS0000021", sr.getName()));
					sb.append("\n");
				}else{
					sb.append(sr.getMsg());
					sb.append("\n");
				}
			}
		} catch (Exception e) {
			// "停止在线SQL统计监控失败！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000022")+e.getMessage());
		}
		return sb.toString();
	}
	
	/**
	 * 启动在线服务统计监控
	 * @param appServerIds
	 * @param second
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String startAction(Object[] ids,String second) throws Exception {
		StringBuilder sb = new StringBuilder("");
		try {
			if (ids==null || ids.length<1)
				return sb.toString();
			IAPIControlStatSV controlStatSV=(IAPIControlStatSV)ServiceFactory.getService(IAPIControlStatSV.class);
			List result=controlStatSV.startAction(ids,second);
			for(int i=0;i<result.size();i++){
				SimpleResult sr=(SimpleResult)result.get(i);				
				if (sr.isSucc()){
					// "成功启动应用[{0}]在线Action统计监控"
					sb.append(AIMonLocaleFactory.getResource("MOS0000023", sr.getName()));
					sb.append("\n");
				}else{
					sb.append(sr.getMsg());
					sb.append("\n");
				}
			}
		} catch (Exception e) {
			// "启动在线Action统计监控失败！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000024")+e.getMessage());
		}
		return sb.toString(); 
	}
	/**
	 * 停止在线服务统计监控
	 * @param appServerIds
	 * @param second
	 * @return
	 */
	public String stopAction(Object[] ids) throws Exception {
		StringBuilder sb = new StringBuilder("");
		try {
			if (ids==null || ids.length<1)
				return sb.toString();
			IAPIControlStatSV controlStatSV=(IAPIControlStatSV)ServiceFactory.getService(IAPIControlStatSV.class);
			List result=controlStatSV.stopAction(ids);
			for(int i=0;i<result.size();i++){
				SimpleResult sr=(SimpleResult)result.get(i);				
				if (sr.isSucc()){
					// "成功停止应用[{0}]在线Action统计监控"
					sb.append(AIMonLocaleFactory.getResource("MOS0000025", sr.getName()));
					sb.append("\n");
				}else{
					sb.append(sr.getMsg());
					sb.append("\n");
				}
			}
		} catch (Exception e) {
			// "停止在线Action统计监控失败！"
			throw new Exception(AIMonLocaleFactory.getResource("MOS0000026")+e.getMessage());
		}
		return sb.toString();
	}
	
}
