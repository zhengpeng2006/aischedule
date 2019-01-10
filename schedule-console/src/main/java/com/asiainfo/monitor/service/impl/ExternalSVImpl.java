package com.asiainfo.monitor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.acquire.service.interfaces.IBusiProcessSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupHostRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.common.combin.CmbHostBean;
import com.asiainfo.monitor.common.service.interfaces.IBaseCommonSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonServerDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.service.interfaces.IExternalSV;

public class ExternalSVImpl implements IExternalSV
{
    @Override
    public IBOAIMonNodeValue qryNodeInfoByServerCode(String serverCode) throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.qryNodeInfoByServerCode(serverCode);
    }

    @Override
    public IBOAIMonServerValue qryServerByServerCode(String serverCode) throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.qryServerByServerCode(serverCode);
    }

    @Override
    public Map<String, String> qryAppInfoByCondition(String serverCode) throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
        IBOAIMonServerValue serverInfo = serverSv.qryServerByServerCode(serverCode);
        if(null != serverInfo) {
            String serverId = Long.toString(serverInfo.getServerId());
            String nodeId = Long.toString(serverInfo.getNodeId());

            CmbHostBean hostBean = CommonSvUtil.qryCmbHostInfoByServerId(serverId);
            Map<String, String> userInfoMap = CommonSvUtil.qryNodeUserInfoByServerId(serverId);
            retMap.put("SERVER_ID", serverId);
            retMap.put("HOST_IP", hostBean.getHostIp());
            retMap.put("USER_NAME", userInfoMap.get(CommonConst.USER_NAME));
            retMap.put("USER_PASSWD", K.k(userInfoMap.get(CommonConst.USER_PASSWD)));
            retMap.put("NODE_ID", nodeId);
        }
        return retMap;
    }

    @Override
    public Map<String, String> qryAppInfoByCondition(String servCode, String connType) throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
        IBOAIMonServerValue serverInfo = serverSv.qryServerByServerCode(servCode);
        if(null != serverInfo) {
            String serverId = Long.toString(serverInfo.getServerId());
            String nodeId = Long.toString(serverInfo.getNodeId());

            CmbHostBean hostBean = CommonSvUtil.qryCmbHostInfoByServerId(serverId);
            Map<String, String> userInfoMap = CommonSvUtil.qryNodeUserInfoByServerId(serverId);
            retMap.put("SERVER_ID", serverId);
            retMap.put("HOST_IP", hostBean.getHostIp());
            retMap.put("USER_NAME", userInfoMap.get(CommonConst.USER_NAME));
            retMap.put(connType, hostBean.getConPort(connType));
            retMap.put("USER_PASSWD", K.k(userInfoMap.get(CommonConst.USER_PASSWD)));
            retMap.put("NODE_ID", nodeId);
        }
        return retMap;
    }

    @Override
    public List<IBOAIMonServerValue> qryServerInfo(String nodeId) throws Exception
    {
        List<IBOAIMonServerValue> list = new ArrayList<IBOAIMonServerValue>();
        IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
        IBOAIMonServerValue[] values = serverSv.qryServerInfo(nodeId);
        for(int i = 0; i < values.length; i++) {
            list.add(values[i]);
        }
        return list;
    }

    @Override
    public Map<String, String> qryNodeRelInfoByCondition(String nodeId, String connType) throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        CmbHostBean hostBean = CommonSvUtil.qryCmbHostInfoByNodeId(nodeId);
        if(null != hostBean) {
            Map<String, String> userInfoMap = CommonSvUtil.qryNodeUserInfoByNodeId(nodeId);
            if (userInfoMap == null) {
            	throw new Exception("no user info is found of node:" + nodeId);
            }
            retMap.put("HOST_IP", hostBean.getHostIp());
            retMap.put("USER_NAME", userInfoMap.get(CommonConst.USER_NAME));
            retMap.put(connType, hostBean.getConPort(connType));
            retMap.put("USER_PASSWD", K.k(userInfoMap.get(CommonConst.USER_PASSWD)));
            retMap.put("NODE_ID", nodeId);
        }
        return retMap;
    }

    /**
     * 通过分组名称、主机名、主机Ip、节点名称、应用编码查询应用信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public JSONArray qryServerInfoByCondition(Map<String, String> map) throws Exception
    {   
    	IAIMonServerDAO dao = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
        return dao.qryServerByConditions(map);
    }

    
    /**
     * 获取指定主机下，指定进程的状态
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map<String, String> acqProcState(Map<String, List<String>> paramMap, int timeout) throws Exception
    {
        IBusiProcessSV processSv = (IBusiProcessSV) ServiceFactory.getService(IBusiProcessSV.class);
        return processSv.acqProcState(paramMap, timeout);
    }

	@Override
	public JSONArray qryServersInfoByServerCodes(Set<String> serverCodes)
			throws Exception {
    	IAIMonServerDAO dao = (IAIMonServerDAO)ServiceFactory.getService(IAIMonServerDAO.class);
		return dao.qryServersInfoByServerCodes(serverCodes);
	}

    @Override
    public IBOAIMonPhysicHostValue[] qryHostInfoByGroupId(String groupId) throws Exception
    {
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IAIMonGroupHostRelSV ghSv = (IAIMonGroupHostRelSV) ServiceFactory.getService(IAIMonGroupHostRelSV.class);
        List list = ghSv.getHostListByGroupId(groupId);
        IBOAIMonPhysicHostValue[] result = null;
        if(list.size() > 0) {
            result = hostSv.qryByList(list);
        }
        return result;
    }

	@Override
	public Map<Long, String> getHostGroups() throws Exception{
        IAIMonGroupHostRelSV ghSv = (IAIMonGroupHostRelSV) ServiceFactory.getService(IAIMonGroupHostRelSV.class);
		return ghSv.getHostGroups() ;
	}

	@Override
	public Map<String, Long> getServerHosts() throws Exception {
		IAIMonServerSV sv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
		return sv.getServerHosts();
	}

	@Override
	public JSONObject getServerHostsMap() throws Exception {
		IAIMonServerSV sv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
		return sv.getServerHostsMap();
	}

}
