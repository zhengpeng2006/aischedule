package com.asiainfo.monitor.busi.web;



import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlServerReleaseSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlServerStatusSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowServerStatusSV;
import com.asiainfo.monitor.interapi.config.CallResult;
import com.asiainfo.monitor.tools.common.SimpleResult;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;


public class ControlServerStatusAction extends BaseAction {

	private static transient Log log=LogFactory.getLog(ControlServerStatusAction.class);

	/**
	 * 启动服务器
	 * @param appIds 应用id
	 * @param timeOut 设置启动超时时间。timeOut<=0不设置超时，否则超时后抛出异常
	 * @return
	 * @throws Exception
	 */
	public String startServer(Object[] appIds,Integer timeOut, String clientID) throws Exception{
		String result = "";
		try{
			if (appIds == null || appIds.length<0){
				return result;
			}
			IAPIControlServerStatusSV controlServerStatusSV = (IAPIControlServerStatusSV)ServiceFactory.getService(IAPIControlServerStatusSV.class);
			
			CallResult[] rtn = controlServerStatusSV.startServer(appIds, timeOut, clientID);
			StringBuilder sb = new StringBuilder("");
			if (rtn != null && rtn.length > 0){
				for (int i=0;i<rtn.length;i++){
					sb.append(rtn[i].getRemark());
					sb.append("\n");
				}
			}
			
			result = sb.toString();
			
		}catch(Exception e){
			// "启动服务器失败！"
			result = AIMonLocaleFactory.getResource("MOS0000012");
			log.error("Call ControlServerStatusAction's Method startServer has Exception :"+e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 停止服务器
	 * @param appIds 应用id
	 * @param timeOut 设置启动超时时间。timeOut<=0不设置超时，否则超时后抛出异常
	 * @return
	 * @throws Exception
	 */
	public String stopServer(Object[] appIds,Integer timeOut, String clientID) throws Exception{
		String result = "";
		try{
			if (appIds == null || appIds.length < 0){
				return result;
			}
			
			IAPIControlServerStatusSV controlServerStatusSV = (IAPIControlServerStatusSV)ServiceFactory.getService(IAPIControlServerStatusSV.class);
			
			CallResult[] rtn = controlServerStatusSV.stopServer(appIds, timeOut, clientID);
			
			StringBuilder sb = new StringBuilder("");
			if (rtn != null && rtn.length > 0){
				for (int i=0;i<rtn.length;i++){
					sb.append(rtn[i].getRemark());
					sb.append("\n");
				}
			}
			result = sb.toString();
		}catch(Exception e){
			result = e.getMessage();
			log.error("Call ControlServerStatusAction's Method stopServer has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 重新启动服务器,暂时只允许当前只有一台操作
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public String restartServer(Object[] appIds, Integer timeOut, String clientId) throws Exception{
		String result="";
		try{
			if (appIds==null || appIds.length<0){
				return result;
			}
			IAPIControlServerStatusSV controlServerStatusSV=(IAPIControlServerStatusSV)ServiceFactory.getService(IAPIControlServerStatusSV.class);
			CallResult[] rtn=controlServerStatusSV.reStartServer(appIds, timeOut, clientId);


			StringBuilder sb=new StringBuilder("");
			if (rtn!=null && rtn.length>0){
				for (int i=0;i<rtn.length;i++){
					sb.append(rtn[i].getRemark());
					sb.append("\n");
				}
			}
			result=sb.toString();			
		}catch(Exception e){
			// "重启服务器异常！"
			result = AIMonLocaleFactory.getResource("MOS0000013");
			log.error("Call ControlServerStatusAction's Method reStartServer has Exception :"+e.getMessage());
		}
		return result;
	}
	
	public String release(Object[] appIds, Integer timeOut, String clientId) throws Exception {
		String result="";
		try{
			if (appIds==null || appIds.length<0){
				return result;
			}
			IAPIControlServerReleaseSV releaseServerSV=(IAPIControlServerReleaseSV)ServiceFactory.getService(IAPIControlServerReleaseSV.class);
			CallResult[] rtn=releaseServerSV.releaseServer(appIds, timeOut, clientId);


			StringBuilder sb=new StringBuilder("");
			if (rtn!=null && rtn.length>0){
				for (int i=0;i<rtn.length;i++){
					sb.append(rtn[i].getRemark());
					sb.append("\n");
				}
			}
			result=sb.toString();			
		}catch(Exception e){
			// "发布服务异常！"
			result = AIMonLocaleFactory.getResource("MOS0000014");
			log.error("Call ControlServerStatusAction's Method releaseServer has Exception :"+e.getMessage());
		}
		return result;
		
	}
	
	public String versionBack(Object[] appIds, Integer timeOut, String clientId, String cmdSetId) throws Exception {
		String result="";
		try{
			if (appIds==null || appIds.length<0){
				return result;
			}
			IAPIControlServerReleaseSV releaseServerSV=(IAPIControlServerReleaseSV)ServiceFactory.getService(IAPIControlServerReleaseSV.class);
			CallResult[] rtn=releaseServerSV.versionBack(appIds, timeOut, clientId, cmdSetId);


			StringBuilder sb=new StringBuilder("");
			if (rtn!=null && rtn.length>0){
				for (int i=0;i<rtn.length;i++){
					sb.append(rtn[i].getRemark());
					sb.append("\n");
				}
			}
			result=sb.toString();			
		}catch(Exception e){
			// "发布服务异常！"
			result = AIMonLocaleFactory.getResource("MOS0000014");
			log.error("Call ControlServerStatusAction's Method releaseServer has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 检查版本一致
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public SimpleResult checkUniteVersion(Object[] ids) throws Exception{
		SimpleResult result=null;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			result=serverSV.checkUniteVersion(ids);
		}catch (Exception e){
			log.error("Call ControlServerStatusAction's Method checkUniteVersion has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 计算ORB的ID
	 * 
	 * @param appNames
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public List comptuerOrbId(Object[] appNames) throws RemoteException,Exception {
		List result = null;
		try {
			IAPIShowServerStatusSV showStateSv = (IAPIShowServerStatusSV)ServiceFactory.getService(IAPIShowServerStatusSV.class);
			result = showStateSv.comptuerOrbId(appNames);
		} catch (Exception e) {
			log.error("Call ControlServerStatusAction's Method comptuerOrbId has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 读取，保存，检查版本信息
	 * S
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public SimpleResult checkVersion(Object[] ids) throws Exception{
		SimpleResult result=null;
		try{
			IAIMonServerSV serverSV=(IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
			result=serverSV.checkVersion(ids);
		}catch (Exception e){
			log.error("Call ControlServerStatusAction's Method checkUniteVersion has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 解析Log日志信息
	 * @param logInfo
	 * @param serverCode
	 * @return
	 * @throws Exception
	 */
	public String parseLogInfo(String logInfo, String serverCode) throws Exception {
		StringBuilder rtnValue = new StringBuilder("");
		String logInfos[] = logInfo.split("\r");
		String logger = null;
		for (int i = 0; i < logInfos.length; i++) {
			logger = logInfos[i];
			if (logger.indexOf("(") != -1) {
				String code = logger.substring(logger.indexOf("(") + 1, logger.indexOf(")"));
				if (serverCode.equals(code)) {
					rtnValue.append(logger).append("\r");
				}
			}
		}
		return rtnValue.toString();
	}
	
	/**
	 * 获取客户端ID
	 * @return
	 */
//	public String getClientID() {
//		String clientID = null;
//		clientID = FlexContext.getFlexClient().getId();
//		return clientID;
//	}
//	
//	public String getUUID() {
//		return UUIDUtils.createUUID();
//	}
	
}
