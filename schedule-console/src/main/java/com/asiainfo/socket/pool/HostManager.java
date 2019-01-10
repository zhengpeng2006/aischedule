package com.asiainfo.socket.pool;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.common.service.interfaces.IBaseCommonSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;


/**
 * 哪些主机需要放置在连接池中
 * 
 * @author 孙德东(24204)
 */
public class HostManager {
	public static String combine(String host, String port) {
		return host + "-" + port;
	}
	
	public static String[] split(String peer) {
		return StringUtils.split(peer, "-");
	}
	
	/**
	 * 获取需要维护心跳的主机
	 * 每次都从缓存中获取，新增主机后，缓存会自动更新
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> fetchAllPeers() throws Exception {
		IBaseCommonSV commonService = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
		
		Set<String> set = new HashSet<String>();
		IAIMonGroupSV monGroupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
        IBOAIMonGroupValue[] grpArr=monGroupSV.getAllGroupBean();
        if(grpArr!=null&&grpArr.length>0) {
            for(int i = 0; i < grpArr.length; i++) {
                IBOAIMonPhysicHostValue[] hostList = commonService.qryHostInfosByGroupId(String.valueOf(grpArr[i].getGroupId()));
                if(hostList!=null&&hostList.length>0) {
                    for(IBOAIMonPhysicHostValue hostBean : hostList) {
                        set.add(combine(hostBean.getHostIp(), String.valueOf(SocketConfig.getMonitorPort())));
                    }
                }
            }
        }
		return set;
	}
	
	// 测试用
	public static Set<String> fetchAllPeersTest() throws Exception {
		Set<String> set = new HashSet<String>();
		set.add(combine("10.70.165.4", "2045"));
		return set;
	}
	public static void main(String[] args) throws Exception
    {
	    fetchAllPeers();
    }
}
