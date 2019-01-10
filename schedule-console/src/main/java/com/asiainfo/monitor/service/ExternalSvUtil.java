package com.asiainfo.monitor.service;

import com.ai.appframe2.complex.util.e.K;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.common.combin.CmbHostBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonServerDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.service.interfaces.IExternalSV;
import com.asiainfo.monitor.service.interfaces.IHostBackSV;

import java.util.HashMap;

/**
 * 外部接口辅助类，给外部接口调用
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: AI(NanJing)
 * </p>
 * 
 * @author wu songkai
 * @version 3.0
 */
public class ExternalSvUtil
{

    /**
     * 通过应用编码查询应用相关信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public static IBOAIMonNodeValue qryAppInfoByServerCode(String serverCode) throws Exception
    {
        IExternalSV eSv = (IExternalSV) ServiceFactory.getService(IExternalSV.class);
        return eSv.qryNodeInfoByServerCode(serverCode);
    }

    /**
     * 通过应用编码查询应用信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public static Map<String, String> qryAppInfoByCondition(String serverCode) throws Exception
    {
        IExternalSV eSv = (IExternalSV) ServiceFactory.getService(IExternalSV.class);
        return eSv.qryAppInfoByCondition(serverCode);
    }

    /**
     * 批量通过应用编码查询应用信息,只需要appid,nodeId,ip这两个信息
     * @param serverCodeList
     * @return 
     */
    public static Map<String, Map<String, String>> batchQryAppInfo(List<String> serverCodeList) throws Exception {
        if (serverCodeList == null || serverCodeList.isEmpty()) {
            return null;
        }

        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        for (String serverCode : serverCodeList) {
            Map<String, String> retMap = new HashMap<String, String>();
            
            IAIMonServerDAO serDAO = (IAIMonServerDAO) ServiceFactory.getService(IAIMonServerDAO.class);
            IBOAIMonServerValue serverInfo = serDAO.qryServerByServerCode(serverCode);
            if (null != serverInfo) {
                String serverId = Long.toString(serverInfo.getServerId());
                String nodeId = Long.toString(serverInfo.getNodeId());

                CmbHostBean hostBean = CommonSvUtil.qryCmbHostInfoByServerId(serverId);
                Map<String, String> userInfoMap = CommonSvUtil.qryNodeUserInfoByNodeId(nodeId);
                retMap.put("USER_NAME", userInfoMap.get(CommonConst.USER_NAME));
                retMap.put("SERVER_ID", serverId);
                retMap.put("HOST_IP", hostBean.getHostIp());
                retMap.put("NODE_ID", nodeId);
            }
            
            result.put(serverCode, retMap);
        }

        return result;
    }
    /**
     * 通过应用编码和连接类型查询应用信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public static Map<String, String> qryAppInfoByCondition(String serverCode, String connType) throws Exception
    {
        IExternalSV eSv = (IExternalSV) ServiceFactory.getService(IExternalSV.class);
        return eSv.qryAppInfoByCondition(serverCode, connType);
    }

    /**
     * 根据节点标识查询应用信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public static List<IBOAIMonServerValue> qryServerInfo(String nodeId) throws Exception
    {
        IExternalSV eSv = (IExternalSV) ServiceFactory.getService(IExternalSV.class);
        return eSv.qryServerInfo(nodeId);
    }

    /**
     * 根据节点标识查询应用信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    public static Map<String, String> qryNodeRelInfoByCondition(String nodeId, String connType) throws Exception
    {
        IExternalSV eSv = (IExternalSV) ServiceFactory.getService(IExternalSV.class);
        return eSv.qryNodeRelInfoByCondition(nodeId, connType);
    }

    public static boolean hostBack(String hostId) throws Exception
    {
        IHostBackSV backSv = (IHostBackSV) ServiceFactory.getService(IHostBackSV.class);
        return backSv.hostBack(hostId);
    }

    public static boolean hostServerBack(String serverCode) throws Exception
    {
        IHostBackSV backSv = (IHostBackSV) ServiceFactory.getService(IHostBackSV.class);
        return backSv.hostServerBack(serverCode);
    }

    /**
     * 获取对应主机下，指定的应用进程是否存在，即是否已经运行
     * 
     * @param paramMap
     *            Map<hostId,List<appCode>>
     * @return
     * @throws Exception
     */
    public static Map<String, String> acqProcState(Map<String, List<String>> paramMap, int timeout) throws Exception
    {
        IExternalSV eSv = (IExternalSV) ServiceFactory.getService(IExternalSV.class);
        return eSv.acqProcState(paramMap, timeout);
    }

    /**
     * 根据启停页面查询条件查询应用相关信息
     * 
     * @param conditions
     * @return
     * @throws Exception
     */
    public static JSONArray qryServerInfoByCondition(Map<String, String> conditions) throws Exception
    {
        IExternalSV exSv = (IExternalSV) ServiceFactory.getService(IExternalSV.class);
        return exSv.qryServerInfoByCondition(conditions);
    }

    /**
     * 读取所有正常的节点
     * 
     * @return
     * @throws Exception
     */
    public static IBOAIMonNodeValue[] getAllNode() throws RemoteException, Exception
    {
        IAIMonNodeSV sv = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
        return sv.getAllNodeBean();
    }

    /**
     * 返回所有物理主机信息值对象
     * 
     * @return
     * @throws Exception
     */
    public static IBOAIMonPhysicHostValue[] getAllPhostBean() throws Exception
    {
        IAIMonPhysicHostSV sv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
        return sv.getAllPhostBean();
    }
    
    /**
     * 通过应用编码查询主机信息
     * 
     * @param request
     * @throws Exception
     */
    public static JSONArray qryServersInfoByServerCodes(Set<String> serverCodes) throws Exception{
        IExternalSV eSv = (IExternalSV) ServiceFactory.getService(IExternalSV.class);
        return eSv.qryServersInfoByServerCodes(serverCodes);
    }

    /**
     * 根据分组标识查询主机信息
     * 
     * @param request
     * @throws Exception
     */
    public static IBOAIMonPhysicHostValue[] qryHostInfoByGroupId(String groupId) throws Exception
    {
        IExternalSV sv = (IExternalSV) ServiceFactory.getService(IExternalSV.class);
        return sv.qryHostInfoByGroupId(groupId);
    }
    
    /**
     * 返回所有主机ID和对应的组名
     * @return
     */
    public static Map<Long,String> getHostGroups() throws Exception{
        IExternalSV sv = (IExternalSV) ServiceFactory.getService(IExternalSV.class);
        return sv.getHostGroups();
    }
    
    /**
     * 返回所有应用和对应的主机ID
     * @return
     * @throws Exception
     */
    public static Map<String,Long> getServerHosts() throws Exception{
        IExternalSV sv = (IExternalSV) ServiceFactory.getService(IExternalSV.class);
        return sv.getServerHosts();
    }
    
    /**
     * 返回所有主机和对应的应用列表
     * @return
     * @throws Exception
     */
    public static JSONObject getServerHostsMap() throws Exception{
        IExternalSV sv = (IExternalSV) ServiceFactory.getService(IExternalSV.class);
        return sv.getServerHostsMap();
    }

    public static void main(String[] args) throws Exception
    {
        /* List<String> appCodeList = new ArrayList<String>();
         appCodeList.add("TaskFrameWork");
         appCodeList.add("zookeeper-3.4.6");

         Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
         paramMap.put("4", appCodeList);

         Map<String, String> procStateMap = ExternalSvUtil.acqProcState(paramMap, 1000);
         System.out.println(procStateMap.get("TaskFrameWork"));
         System.out.println(procStateMap.get("zookeeper-3.4.6"));*/
        /*   Set<String> list = new HashSet<String>();
           list.add("sec-exe1");
           list.add("sec-exe11");
           System.out.println(qryServersInfoByServerCodes(list));*/
//        boolean b = hostBack("604");
//        System.out.println(b);
        //hostServerBack("monitor154");
    	System.out.println(ExternalSvUtil.getServerHosts().size());
    }
}
