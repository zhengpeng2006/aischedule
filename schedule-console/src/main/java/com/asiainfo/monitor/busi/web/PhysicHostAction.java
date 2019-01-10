package com.asiainfo.monitor.busi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.appframe2.web.action.BaseAction;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHostBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;

public class PhysicHostAction extends BaseAction {

	private static transient Log log=LogFactory.getLog(AppConfigAction.class);
	
	/**
	 * 根据物理主机名称查询主机信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List getWholePhysicHost() throws Exception {
		List result = null;
		IAIMonPhysicHostSV hostSV = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
		List list = hostSV.getAllPhost(); 
		if (list!=null && list.size() > 0) {
			result = new ArrayList(list.size());
			Map item=null;
			for (int i=0;i<list.size();i++) {
				item=new HashMap();
				item.put("HOST",list.get(i));
				item.put("CHK","false");
				result.add(item);
			}
		}
		return result;
	}
	
	/**
	 * 根据物理主机名称查询主机信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public IBOAIMonPhysicHostValue[] getPhysicHostByName(String name) throws Exception {
		IBOAIMonPhysicHostValue[] value = null;
		try {
			IAIMonPhysicHostSV hostSV = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
			value = hostSV.getPhysicHostByName(name);
			
		} catch (Exception e) {
			log.error("Call PhysicHostAction's Method getPhysicHostByName has Exception :"+e.getMessage());
			throw e;
		}
		return value;
	}
	
	/**
	 * 根据物理主机名称和IP查询主机信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List getPhysicHostByNameAndIp(String name,String ip) throws Exception {
		List result = null;
		try {
			IAIMonPhysicHostSV hostSV = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
			IBOAIMonPhysicHostValue[] value = hostSV.getPhysicHostByNameAndIp(name,ip);
			if (value != null && value.length > 0) {
				result = new ArrayList();
				Map hm = null;
				for (int i = 0; i < value.length; i++) {
					hm = new HashMap();
                    hm.put(IBOAIMonPhysicHostValue.S_HostId, value[i].getHostId());
                    //					hm.put(IBOAIMonPhysicHostValue.S_AttendMode, value[i].getAttendMode());
                    hm.put(IBOAIMonPhysicHostValue.S_HostIp, value[i].getHostIp());
                    hm.put(IBOAIMonPhysicHostValue.S_HostCode, value[i].getHostCode());
                    hm.put(IBOAIMonPhysicHostValue.S_HostDesc, value[i].getHostDesc());
                    //                    hm.put(IBOAIMonPhysicHostValue.S_HostPort, value[i].getHostPort());
                    //                    hm.put(IBOAIMonPhysicHostValue.S_HostPwd, value[i].getHostPwd());
                    hm.put(IBOAIMonPhysicHostValue.S_HostName, value[i].getHostName());
                    //                    hm.put(IBOAIMonPhysicHostValue.S_HostUser, value[i].getHostUser());
					hm.put(IBOAIMonPhysicHostValue.S_Remarks, value[i].getRemarks());
					hm.put("CHK","false");
					result.add(hm);
				}
			}
		} catch (Exception e) {
			log.error("Call PhysicHostAction's Method getPhysicHostByName has Exception :"+e.getMessage());
			throw e;
		}
		return result;
	}
	
	/**
	 * 保存或修改物理主机信息
	 * 
	 * @param physicHost
	 * @throws Exception
	 */
	public void saveOrUpdatePhysicHost(Object[] physicHostInfo) throws Exception {
		if (physicHostInfo == null && physicHostInfo.length < 0) {
			return;
		}
		try {
			IBOAIMonPhysicHostValue hostValue = new BOAIMonPhysicHostBean();
			if (StringUtils.isBlank(physicHostInfo[0].toString())) {
				hostValue.setStsToNew();
			} else {
                hostValue.setHostId(Long.parseLong(physicHostInfo[0].toString()));
				hostValue.setStsToOld();
			}
            hostValue.setHostName(physicHostInfo[1].toString().trim());
            //			hostValue.setAttendMode(physicHostInfo[2].toString().trim());
            hostValue.setHostIp(physicHostInfo[3].toString());
            //            hostValue.setHostPort(Short.parseShort(physicHostInfo[4].toString()));
            //            hostValue.setHostUser(physicHostInfo[5].toString());
            //            hostValue.setHostPwd(K.j(physicHostInfo[6].toString()));
			hostValue.setState(physicHostInfo[7].toString());
			hostValue.setRemarks(physicHostInfo[8].toString());
            hostValue.setHostDesc(physicHostInfo[9].toString());
			IAIMonPhysicHostSV hostSV = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
			hostSV.saveOrUpdate(hostValue);
			
		} catch (Exception e) {
			log.error("Call PhysicHostAction's Method saveOrUpdatePhysicHost has Exception :"+e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 删除物理主机信息
	 * 
	 * @param hostId
	 * @throws Exception
	 */
	public void deletePhysicHost(String hostId) throws Exception {
		if (StringUtils.isBlank(hostId)) {
			return;
		}
		try {
			IAIMonPhysicHostSV hostSV = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
			hostSV.deletePhysicHost(Long.parseLong(hostId));
		} catch (Exception e) {
			log.error("Call PhysicHostAction's Method saveOrUpdatePhysicHost has Exception :"+e.getMessage());
			throw e;
		}
	}

}
