package com.asiainfo.monitor.busi.web;

import java.util.List;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlAppSwitchSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowAppSwitchSV;
import com.asiainfo.monitor.tools.common.SimpleResult;

public class ControlSwitchAppClusterAction extends BaseAction {

	/**
	 * 将选择的应用切换至目标集群
	 * @param ids:选择的应用
	 * @param newAppClusterCode:目标集群
	 * @return
	 * @throws Exception
	 */
	public String switchApp(Object[] ids,String newAppClusterCode) throws Exception{
		StringBuilder sb = new StringBuilder("");
		try {
			if (ids==null || ids.length<1){
				return sb.toString();
			}
			IAPIControlAppSwitchSV appSwtichSV=(IAPIControlAppSwitchSV)ServiceFactory.getService(IAPIControlAppSwitchSV.class);
			List result=appSwtichSV.switchApp(ids, newAppClusterCode);
			for(int i=0;i<result.size();i++){
				SimpleResult sr=(SimpleResult)result.get(i);				
				if (sr.isSucc()){
					// 应用[{0}]切换app集群成功
					sb.append(sr.getName()+"切换app集群成功");
					sb.append("\n");
				}else{
					sb.append(sr.getMsg());
					sb.append("\n");
				}
			}
		} catch (Exception e) {
			// app集群切换异常:
			throw new Exception ("app集群切换异常:"+e.getMessage());
		}
		return sb.toString(); 
	}
	
	/**
	 * 查询App集群信息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List getAppConnectInfo(Object[] ids) throws Exception{
		List result=null;
		try {
			if (ids==null || ids.length<1){
				return result;
			}
			IAPIShowAppSwitchSV appSwtichSV=(IAPIShowAppSwitchSV)ServiceFactory.getService(IAPIShowAppSwitchSV.class);
			result=appSwtichSV.getAppConnectInfo(ids);			
			
		} catch (Exception e) {
			// 查询app集群异常:
			throw new Exception ("查询app集群异常:"+e.getMessage());
		}
		return result; 
	}
	
	/**
	 * 查询选择应用对应的集群信息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List getWebConnectApp(Object[] ids) throws Exception{
		List result=null;
		try {
			if (ids==null || ids.length<1){
				return result;
			}
			IAPIShowAppSwitchSV appSwtichSV=(IAPIShowAppSwitchSV)ServiceFactory.getService(IAPIShowAppSwitchSV.class);
			result=appSwtichSV.getWebConnectApp(ids);
			
		} catch (Exception e) {
			// 查询app集群异常:
			throw new Exception ("查询app集群异常:"+e.getMessage());
		}
		return result; 
	}
}
