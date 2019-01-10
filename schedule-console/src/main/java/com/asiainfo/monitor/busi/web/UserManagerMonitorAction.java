package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlUserSV;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowUserSV;
import com.asiainfo.monitor.tools.util.TypeConst;

public class UserManagerMonitorAction extends BaseAction {

	private final static transient Log log=LogFactory.getLog(UserManagerMonitorAction.class);
	
	/**
	 * 获取在线用户信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getOnlineUsers(String appId) throws Exception {
		if (StringUtils.isBlank(appId))
			return null;
		List result=null;
		try{
			IAPIShowUserSV showUserSV=(IAPIShowUserSV)ServiceFactory.getService(IAPIShowUserSV.class);
			result=showUserSV.getOnlineUsers(appId);
		}catch(Exception e){
			log.error("Call UserManagerMonitorAction's Method getOnlineUsers has Exception :"+e.getMessage());
		}
		return result;
	}

	/**
	 * 根据服务器分组获取在线用户信息
	 * 
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getOnlineUsers(Object[] appIds) throws Exception {
		
		if (appIds==null || appIds.length<1)
			return null;
		List result=null;
		
		try {
			
			IAPIShowUserSV showUserSV=(IAPIShowUserSV)ServiceFactory.getService(IAPIShowUserSV.class);
			result=showUserSV.getOnlineUsers(appIds);
		} catch (Exception e) {
			log.error("Call UserManagerMonitorAction's Method getOnlineUsers has Exception :"+e.getMessage());
		}
		return result;
	}

	/**
	 * 根据服务器分组,地区、组织查询在线用户信息
	 * @param appId
	 * @param regionId:查询全部为0
	 * @param orgName:组织名称，模糊匹配
	 * @return
	 * @throws Exception
	 */
	public List getOnlineUsersByRegionAndOrgName(Object[] appIds,String regionId,String orgName,String ipRep) throws Exception{
		if (appIds == null || appIds.length < 1){
			return null;
		}
		List result = null;		
		try {			
			IAPIShowUserSV showUserSV = (IAPIShowUserSV)ServiceFactory.getService(IAPIShowUserSV.class);
			result = showUserSV.getOnlineUsersByRegionAndOrgName(appIds, regionId, orgName,ipRep);
		} catch (Exception e) {
			log.error("Call UserManagerMonitorAction's Method getOnlineUsers has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据服务器分组,汇总各组织的在线用户信息
	 * 
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getCollectOnlineUsers(Object[] appIds) throws Exception {		
		if (appIds==null || appIds.length<1)
			return null;
		List result=null;
		
		try {
			
			IAPIShowUserSV showUserSV=(IAPIShowUserSV)ServiceFactory.getService(IAPIShowUserSV.class);
			result=showUserSV.getCollectOnlineUsers(appIds);
		} catch (Exception e) {
			log.error("Call UserManagerMonitorAction's Method getOnlineUsers has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * session按应用统计
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	public List getUsersStatistisc(Object[] appIds) throws Exception {
		if (appIds==null || appIds.length<1)
			return null;
		List result=null;
		try{
			IAPIShowUserSV showUserSV=(IAPIShowUserSV)ServiceFactory.getService(IAPIShowUserSV.class);
			result=showUserSV.getServerOnlineUsers(appIds);
		}catch(Exception e){
			log.error("Call UserManagerMonitorAction's Method getUsersStatistisc has Exception :"+e.getMessage());
		}
		return result;
	}

	/**
	 * 返回在线用户个数
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List getOnlineUserCount(String appId) throws Exception {
		List result=null;
		try{
			List onlineUsersresult=this.getOnlineUsers(appId);
			if (onlineUsersresult!=null && onlineUsersresult.size()>0){
				result=new ArrayList(1);
				java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("hh:mm:ss");
				Date date = new Date();
				Map map=new HashMap();
				map.put(TypeConst.M_TIME,formatter.format(date));
				map.put(TypeConst.M_COUNT,new Integer(onlineUsersresult.size()));
				result.add(map);
			}
		}catch(Exception e){
			log.error("Call CacheMonitorAction's Method getOnlineUserCount has Exception :"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 强制某用户退出
	 * @param appId
	 * @param serialId
	 * @throws Exception
	 */
	public void forceLogoutUser(String appId, String serialId) throws Exception{
		if (StringUtils.isBlank(appId) || StringUtils.isBlank(serialId))
			return;
		try{
			IAPIControlUserSV controlUserSV=(IAPIControlUserSV)ServiceFactory.getService(IAPIControlUserSV.class);
			controlUserSV.forceLogoutUser(appId,serialId);
		}catch(Exception e){
			log.error("Call UserManagerMonitorAction's Method forceLogoutUser has Exception :"+e.getMessage());
		}
	}
}
