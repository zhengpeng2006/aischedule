package com.asiainfo.monitor.common.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.deploy.common.bo.BODeployStrategyEngine;
import com.asiainfo.deploy.common.ivalues.IBODeployStrategyValue;
import com.asiainfo.monitor.busi.cache.impl.AIMonPhostCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.PhysicHost;
import com.asiainfo.monitor.busi.config.ServerNodeConfig;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupHostRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.common.combin.CmbHostBean;
import com.asiainfo.monitor.common.service.interfaces.IBaseCommonSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupHostRelBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonGroupDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonGroupHostRelDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonNodeDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonServerDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonConModeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostConModeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonMasterSlaveRelValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeUserValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonUsersValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonConModeSV;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonHostConModeSV;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonMasterSlaveRelSV;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonNodeUserSV;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonUsersSV;

/**
 * 通用查询服务
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: AI(NanJing)</p>
 * @author wu songkai
 * @version 3.0
 */
public class BaseCommonSVImpl implements IBaseCommonSV
{
    private Logger LOG = Logger.getLogger(this.getClass());

    /**
     * 根据组标识查询改组下主机
     * 
     * @param groupId
     * @throws Exception
     */
    public List<PhysicHost> qryGroupHostInfo(String groupId) throws Exception
    {
        IAIMonGroupHostRelDAO relDao = (IAIMonGroupHostRelDAO) ServiceFactory.getService(IAIMonGroupHostRelDAO.class);
        BOAIMonGroupHostRelBean[] relArr = relDao.getBeansByGroupId(Long.parseLong(groupId));

        List<PhysicHost> hostList = new ArrayList<PhysicHost>();
        for(BOAIMonGroupHostRelBean relBean : relArr) {
            Object object = CacheFactory.get(AIMonPhostCacheImpl.class, String.valueOf(relBean.getHostId()));
            if(null != object) {
                hostList.add((PhysicHost) object);
            }
        }

        return hostList;
    }

    /**
     * 查询所有主机信息的缓存
     * 
     * @param request
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public Map<String, PhysicHost> qryHostInfo() throws Exception
    {
        // 获取所有服务节点信息
        Map<String, PhysicHost> hostMap = CacheFactory.getAll(AIMonPhostCacheImpl.class);
        return hostMap;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Map<String, List<IBOAIMonNodeValue>> fetchHostNodeMap() throws Exception
    {

        // 获取所有服务节点信息
        //Map<String, ServerNode> nodeMap = CacheFactory.getAll(AIMonNodeCacheImpl.class);

        // 主机节点MapList
        Map<String, List<IBOAIMonNodeValue>> hostNodeMap = new HashMap<String, List<IBOAIMonNodeValue>>();

        IAIMonNodeSV nodeSv = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
        IBOAIMonNodeValue[] nodeArr = nodeSv.getAllNodeBean();

        //  ServerNode[] nodeArr = nodeMap.values().toArray(new ServerNode[0]);
        for(IBOAIMonNodeValue node : nodeArr) {
            String key = Long.toString(node.getHostId());
            if(hostNodeMap.containsKey(key)) {
                hostNodeMap.get(key).add(node);
            }
            else {
                List<IBOAIMonNodeValue> nodeList = new ArrayList<IBOAIMonNodeValue>();
                nodeList.add(node);
                hostNodeMap.put(key, nodeList);
            }
        }

        return hostNodeMap;
    }

    @Override
    public CmbHostBean qryCmbHostInfo(String hostId) throws Exception
    {
        CmbHostBean cmbHost = null;//组合hostBean

        //查询主机信息,改为从缓存查
        //PhysicHost monHost = (PhysicHost) CacheFactory.get(AIMonPhostCacheImpl.class, hostId);
        //去除缓存
        IAIMonPhysicHostSV hostSV=(IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IBOAIMonPhysicHostValue monHost=hostSV.getPhysicHostById(hostId);

        if(null == monHost) {
            LOG.error("---not find the host by  hostId:" + hostId);
            return null;
        }
        cmbHost = new CmbHostBean();
        cmbHost.setHostId(monHost.getHostId());
        cmbHost.setHostCode(monHost.getHostCode());
        cmbHost.setHostIp(monHost.getHostIp());
        cmbHost.setHostName(monHost.getHostName());

        //查询主机连接方式
        IAIMonConModeSV conModeSv = (IAIMonConModeSV) ServiceFactory.getService(IAIMonConModeSV.class);
        IAIMonHostConModeSV hostConModeSv = (IAIMonHostConModeSV) ServiceFactory.getService(IAIMonHostConModeSV.class);
        IBOAIMonHostConModeValue[] conModeRelArr = hostConModeSv.qryBuHostId(hostId);
        if(null != conModeRelArr && conModeRelArr.length > 0) {
            List<String> conIdList = new ArrayList<String>();
            for(IBOAIMonHostConModeValue hostModeRel : conModeRelArr) {
                conIdList.add(String.valueOf(hostModeRel.getConId()));
            }
            IBOAIMonConModeValue[] conModeArr = conModeSv.qryByConIdList(conIdList);
            cmbHost.setConModeArr(conModeArr);
        }

        //查询主机用户
        /*   IAIMonHostUserSV hostUserSv = (IAIMonHostUserSV) ServiceFactory.getService(IAIMonHostUserSV.class);
           IBOAIMonHostUserValue[] hostUserArr = hostUserSv.qryByHostId(hostId);
           if(null != hostUserArr && hostUserArr.length > 0) {
               cmbHost.setHostUserArr(hostUserArr);
           }*/

        return cmbHost;
    }

    @Override
    public CmbHostBean qryCmbHostInfoByServerId(String serverId) throws Exception
    {

        CmbHostBean hostBean = null;
        IAIMonNodeSV nodeSv = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
        IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
        IBOAIMonServerValue serverBean = serverSv.getServerBeanById(serverId);
        if(null != serverBean) {
            long nodeId = serverBean.getNodeId();
            ServerNodeConfig nodeBean = nodeSv.getNodeByNodeId(String.valueOf(nodeId));
            if(null != nodeBean) {
                String hostId = nodeBean.getHost_id();
                hostBean = this.qryCmbHostInfo(hostId);
            }
        }
        return hostBean;
    }

    /**
     *根据节点编码得出节点信息
     * 
     * @param request
     * @throws Exception
     * @author szh
     */
    @Override
    public IBOAIMonNodeValue qryNodeInfoByServerCode(String serverCode) throws Exception
    {
        IAIMonServerDAO serverDAO = (IAIMonServerDAO) ServiceFactory.getService(IAIMonServerDAO.class);
        IBOAIMonServerValue value = serverDAO.getServerByServerCode(serverCode);
        if(value != null) {
            String nodeId = Long.toString(value.getNodeId());
            IAIMonNodeDAO nodeDAO = (IAIMonNodeDAO) ServiceFactory.getService(IAIMonNodeDAO.class);
            return nodeDAO.qryNodeInfoByNodeId(nodeId);
        }
        return null;

    }

    /**
     * 通过应用编码查询应用信息
     */
    @Override
    public IBOAIMonServerValue qryServerByServerCode(String serverCode) throws Exception
    {
        IAIMonServerDAO serverDAO = (IAIMonServerDAO) ServiceFactory.getService(IAIMonServerDAO.class);
        return serverDAO.getServerByServerCode(serverCode);
    }

    
    @Override
    public List<IBOAIMonServerValue> qryServerList(String hostId) throws Exception
    {

        List<IBOAIMonServerValue> appList = new ArrayList<IBOAIMonServerValue>();

        IAIMonNodeSV nodeSv = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
        IAIMonServerSV appSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
        IBOAIMonNodeValue[] nodeInfoArr = nodeSv.qryNodeInfo(hostId);
        if(null != nodeInfoArr && nodeInfoArr.length > 0) {
            List<String> nodeIdList = new ArrayList<String>();
            for(IBOAIMonNodeValue nodeVal : nodeInfoArr) {
                nodeIdList.add(String.valueOf(nodeVal.getNodeId()));
            }

            //查询所有节点下的应用
            IBOAIMonServerValue[] qryByCondition = appSv.qryByCondition(nodeIdList);
            for(IBOAIMonServerValue appVal : qryByCondition) {
                appList.add(appVal);
            }
        }

        return appList;
    }

    @Override
    public CmbHostBean qryCmbHostInfoByNodeId(String nodeId) throws Exception
    {
        CmbHostBean hostBean = null;
        IAIMonNodeSV nodeSv = (IAIMonNodeSV) ServiceFactory.getService(IAIMonNodeSV.class);
        ServerNodeConfig nodeBean = nodeSv.getNodeByNodeId(String.valueOf(nodeId));

        if(null != nodeBean.getNode()) {
            String hostId = nodeBean.getHost_id();
            hostBean = this.qryCmbHostInfo(hostId);
        }
        return hostBean;
    }

    @Override
    public BOAIMonGroupBean queryGroupByHostId(String hostId) throws Exception
    {
        IAIMonGroupHostRelDAO relDao = (IAIMonGroupHostRelDAO) ServiceFactory.getService(IAIMonGroupHostRelDAO.class);
        BOAIMonGroupHostRelBean[] relBean = relDao.qryGroupHost(null, hostId);
        if(relBean != null && relBean.length > 0) {
            IAIMonGroupDAO groupDao = (IAIMonGroupDAO) ServiceFactory.getService(IAIMonGroupDAO.class);
            BOAIMonGroupBean groupBean = groupDao.getBeanById(relBean[0].getGroupId());
            return groupBean;
        }
        return null;
    }

    /**
      * 通过应用编码获取主机信息
      * 
      * @param request
      * @throws Exception
      */
    @Override
    public Map<String, IBOAIMonPhysicHostValue> qryHostInfoByAppCode(List<String> list) throws Exception
    {
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
        Map<String, IBOAIMonPhysicHostValue> map = new HashMap<String, IBOAIMonPhysicHostValue>();
        map = hostSv.qryHostByAppCode(list);
        return map;
    }
    
    @Override
    public Map<String, IBOAIMonPhysicHostValue> qryHostInfoByCondition(String groupCode, String hostId, String serverCode) throws Exception
    {
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
        Map<String, IBOAIMonPhysicHostValue> map = new HashMap<String, IBOAIMonPhysicHostValue>();
        map = hostSv.qryHostInfoByCondition(groupCode, hostId, serverCode);
        return map;
    }

    public static void main1(String[] args) throws Exception
    {
        IBaseCommonSV sv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        /*BOAIMonGroupBean bean = sv.queryGroupByHostId("4");
        System.out.println(bean);*/
        List<String> list = new ArrayList<String>();
        list.add("monexe");
        list.add("sec-exe");
        Map<String, IBOAIMonPhysicHostValue> map = sv.qryHostInfoByAppCode(list);
        System.out.println(map);
    }

    /**
     * 根据节点标识查询节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
	public Map<String, String> qryNodeUserInfoByNodeId(String nodeId) throws Exception {
    	IAIMonNodeUserSV nuSv = (IAIMonNodeUserSV) ServiceFactory.getService(IAIMonNodeUserSV.class);
        IAIMonUsersSV uSv = (IAIMonUsersSV) ServiceFactory.getService(IAIMonUsersSV.class);
        IBOAIMonNodeUserValue[] value = nuSv.qryNodeUserByNodeId(nodeId);
        if (value.length == 0 || value.length != 1) {
        	LOG.error("no user or more than one user is fonud of node:" + nodeId + ",num:" + value.length);
        	throw new Exception("no user or more than one user is fonud of node:" + nodeId + ",num:" + value.length);
        }
        
        String userId = Long.toString(value[0].getUserId());
        IBOAIMonUsersValue result = uSv.qryUserById(userId);
        Map<String, String> userInfoMap = new HashMap<String, String>();
        if(null != result) {
            userInfoMap.put(CommonConst.USER_NAME, result.getUserName());
            userInfoMap.put(CommonConst.USER_PASSWD, result.getUserPwd());
        }
        return userInfoMap;
	}

	/**
     * 根据应用标识查询节点发布用户信息
     * 
     * @param request
     * @throws Exception
     */
   /* @Override
    public Map<String, String> qryNodePubUserInfoByServerId(String serverId) throws Exception
    {
        IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
        IBOAIMonServerValue serverValue = serverSv.qryServerByServerId(serverId);
        if(null != serverValue) {
            return CommonSvUtil.qryNodePubUserInfoByNodeId(Long.toString(serverValue.getNodeId()));
        }
        return null;
    }*/

    
    /**
     * 根据应用标识查询节点发布用户信息
     * 
     * @param request
     * @throws Exception
     */
    public Map<String, String> qryNodeUserInfoByServerId(String serverId) throws Exception
    {
        IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
        IBOAIMonServerValue serverValue = serverSv.qryServerByServerId(serverId);
        if(null != serverValue) {
            return CommonSvUtil.qryNodeUserInfoByNodeId(Long.toString(serverValue.getNodeId()));
        }
        return null;
    }
    /**
     * 根据主机标识查询监控节点的用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public Map<String, String> qryMonitorNodeUserInfoByHostIp(String hostIp) throws Exception
    {
    	 Map<String, String> userInfoMap = new HashMap<String, String>();
    	if (StringUtils.isEmpty(hostIp)) {
    		return userInfoMap;
    	}
    	//查找该ip下面的监控节点
    	StringBuffer sql = new StringBuffer();
		Map<String,String> parameter = new HashMap<String, String>();
		sql.append("select d.user_name, d.user_pwd from ai_mon_physic_host a, ai_mon_node b,ai_mon_node_user c,ai_mon_users d")
           .append(" where a.host_ip = :ip and b.is_monitor_node = 'Y' and a.host_id = b.host_id")
           .append(" and b.node_id = c.node_id and c.user_id = d.user_id");
		parameter.put("ip", hostIp);

		Connection conn = null;
	    ResultSet resultset = null;
		try {
		conn = ServiceManager.getSession().getConnection();
		resultset =ServiceManager.getDataStore().retrieve(conn,sql.toString(),parameter);
		while(resultset.next())
		{
			userInfoMap.put(CommonConst.USER_NAME, resultset.getString("user_name"));
			userInfoMap.put(CommonConst.USER_PASSWD, resultset.getString("user_pwd"));
		}
		}catch(Exception e){
		throw e;
		}finally{
		if(resultset != null)
		    resultset.close();
		if (conn != null)
		    conn.close();
		}
		return userInfoMap;
    }

    /**
     * 根据hostid获取监控节点的用户
     * @param hostId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> qryMonitorNodeUserInfoByHostId(String hostId) throws Exception
    {
    	IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
    	IBOAIMonPhysicHostValue host = hostSv.getPhysicHostById(hostId);
		return qryMonitorNodeUserInfoByHostIp(host.getHostIp());
    }
    /**
     * 根据应用标识查询监控用户信息
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public Map<String, String> qryMonitorNodeUserInfoByServerId(String serverId) throws Exception
    {
        IAIMonServerSV serverSv = (IAIMonServerSV) ServiceFactory.getService(IAIMonServerSV.class);
        IBOAIMonServerValue serverValue = serverSv.qryServerByServerId(serverId);
        String hostId = serverSv.qryHostIdByServerCode(serverValue.getServerCode());
        return qryMonitorNodeUserInfoByHostId(hostId);
    }

    /**
     * 根据主机标识查询该主机上监控节点的部署策略路径
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public String qryMonNodeDeployPathByHostId(String hostId) throws Exception
    {
        StringBuffer sql = new StringBuffer();
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IBOAIMonPhysicHostValue host = hostSv.getPhysicHostById(hostId);
        String hostIp = host.getHostIp();
        if(StringUtils.isNotBlank(hostIp)) {
            sql.append("select t4.* from ai_mon_physic_host t1,ai_mon_node t2,ai_deploy_node_strategy_rela t3,ai_deploy_strategy t4"
                    + " where t1.host_id=t2.host_id and t2.node_id=t3.node_id and t3.deploy_strategy_id=t4.deploy_strategy_id "
                    + "and t2.is_monitor_node='Y' and t1.host_ip='" + hostIp + "'");
            IBODeployStrategyValue[] values = BODeployStrategyEngine.getBeansFromSql(sql.toString(), null);
            if(values.length > 0) {
                return values[0].getClientHomePath();
            }
        }
        return null;
    }

    /**
     * 根据主机IP查询监控节点的路径
     * 
     * @param request
     * @throws Exception
     */
    @Override
    public String qryMonNodeDeployPathByIp(String hostIp) throws Exception
    {
        StringBuffer sql = new StringBuffer();
        if(StringUtils.isNotBlank(hostIp)) {
            sql.append("select t4.* from ai_mon_physic_host t1,ai_mon_node t2,ai_deploy_node_strategy_rela t3,ai_deploy_strategy t4"
                    + " where t1.host_id=t2.host_id and t2.node_id=t3.node_id and t3.deploy_strategy_id=t4.deploy_strategy_id "
                    + "and t2.is_monitor_node='Y' and t1.host_ip='" + hostIp + "'");
            IBODeployStrategyValue[] values = BODeployStrategyEngine.getBeansFromSql(sql.toString(), null);
            if(values.length > 0) {
                return values[0].getClientHomePath();
            }
        }
        return null;
    }
    
    /**
     * 需要导出的主机信息
     */
    @Override
    public JSONArray exportHostInfo() throws Exception
    {
        JSONArray jsonArrAll = new JSONArray();
        IBaseCommonSV commonService = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        // 获取所有监控分组信息
        IAIMonGroupSV monGroupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
        //测试
        //IBOAIMonGroupValue[] grpArr=monGroupSV.getAllGroupBean();
        UserInfoInterface userInfo=CommonSvUtil.getUserInfo();
        IBOAIMonGroupValue[] grpArr=null;
        //当用户信息不为空的时候，获取分组信息
        if(userInfo!=null) {
            grpArr=monGroupSV.getGroupByPriv(String.valueOf(userInfo.getID())+"_"+userInfo.getCode());
        }
        if(grpArr!=null&&grpArr.length>0) {
            for(int i = 0; i < grpArr.length; i++) {
                IBOAIMonPhysicHostValue[] hostList = commonService.qryHostInfosByGroupId(String.valueOf(grpArr[i].getGroupId()));
                JSONObject groupObj = new JSONObject();
                JSONArray jsonArrGroup = new JSONArray();
                groupObj.put("group", grpArr[i].getGroupName());
                jsonArrGroup.add(groupObj);
                if(hostList!=null&&hostList.length>0) {
                    for(IBOAIMonPhysicHostValue hostBean : hostList) {
                        JSONArray jsonArrHost=new JSONArray();
                        JSONObject objHost=new JSONObject();
                        objHost.put("hostName", hostBean.getHostName());
                        objHost.put("hostIp", hostBean.getHostIp());
                        
                        jsonArrHost.add(objHost);
                        JSONArray jsonArrServer=new JSONArray();
                        // 获取当前主机部署的应用进程信息
                        List<IBOAIMonServerValue> appList = CommonSvUtil.qryServerList(String.valueOf(hostBean.getHostId()));
                        
                        if(appList!=null&&appList.size()>0) {
                            for(IBOAIMonServerValue serverValue:appList) {
                                JSONObject objectServer=new JSONObject();
                                objectServer.put("serverCode", serverValue.getServerCode());
                                jsonArrServer.add(objectServer);
                            }
                        }
                        if(jsonArrServer.size()>0) {
                            jsonArrHost.add(jsonArrServer);
                        }
                        jsonArrGroup.add(jsonArrHost);
                    }
                }
                jsonArrAll.add(jsonArrGroup);
            }
        }
        return jsonArrAll;
    }
    /**
     * 需要导出的主备机信息
     * @throws Exception 
     */
    @Override
    public HashMap<String, String> exportMasterSlaveInfo() throws Exception
    {
        IAIMonMasterSlaveRelSV msrSv = (IAIMonMasterSlaveRelSV) ServiceFactory.getService(IAIMonMasterSlaveRelSV.class);
        IAIMonPhysicHostSV phSv = (IAIMonPhysicHostSV) ServiceFactory.getService(IAIMonPhysicHostSV.class);
        //IBOAIMonPhysicHostValue[] hostValues=phSv.getAllPhostBean();
        
        UserInfoInterface userInfo=CommonSvUtil.getUserInfo();
        //根据分组查询主机信息
        IAIMonGroupSV groupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
        IBOAIMonGroupValue[] groupValues=null;
        //当用户信息不为空的时候，获取分组信息
        if(userInfo!=null) {
            groupValues=groupSV.getGroupByPriv(String.valueOf(userInfo.getID())+"_"+userInfo.getCode());
        }
        
        IBOAIMonPhysicHostValue[] hostValues = null;
        List<String> listAll=new ArrayList<String>();
        if(groupValues!=null&&groupValues.length>0) {
            for(IBOAIMonGroupValue groupValue:groupValues) {
                IAIMonGroupHostRelSV ghSv = (IAIMonGroupHostRelSV) ServiceFactory.getService(IAIMonGroupHostRelSV.class);
                List<String> list = ghSv.getHostListByGroupId(String.valueOf(groupValue.getGroupId()));
                //把list里的内容加入到listAll中
                if(list!=null&&list.size()>0) {
                    for(int i=0;i<list.size();i++) {
                        listAll.add(list.get(i));
                    }
                }
            } 
            if(listAll.size() > 0) {
                hostValues = phSv.qryByList(listAll);
            }
        }
        HashMap<String, String> map=new HashMap<String, String>();
        HashMap<String, IBOAIMonPhysicHostValue> hostMap=new HashMap<String, IBOAIMonPhysicHostValue>();
        if(hostValues!=null&&hostValues.length>0) {
            for(IBOAIMonPhysicHostValue hostValue:hostValues) {
                hostMap.put(Long.toString(hostValue.getHostId()),hostValue);
            }
            IBOAIMonMasterSlaveRelValue[] masterSlaveRelValues= msrSv.qryAllMasterSlaveRelInfo();
            if(masterSlaveRelValues!=null&&masterSlaveRelValues.length>0) {
                for(int i=0;i<masterSlaveRelValues.length;i++) {
                    IBOAIMonMasterSlaveRelValue masterSlaveRelValue=masterSlaveRelValues[i];
                    String masterHostId=Long.toString(masterSlaveRelValue.getMasterHostId());
                    String slaveHostId=Long.toString(masterSlaveRelValue.getSlaveHostId());
                    IBOAIMonPhysicHostValue masterHostValue=hostMap.get(masterHostId);
                    IBOAIMonPhysicHostValue slaveHostValue=hostMap.get(slaveHostId);
                    if(masterHostValue!=null&&slaveHostValue!=null) {
                        String masterInfo=masterHostValue.getHostName()+"#"+masterHostValue.getHostIp();
                        String slaveInfo=slaveHostValue.getHostName()+"#"+slaveHostValue.getHostIp();
                        map.put(masterInfo, slaveInfo);
                    }
                }
            }
        }
        return map;
    }
    
    public static void main(String[] args) throws Exception
    {
        /*IBaseCommonSV sv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        sv.qryMonNodeDeployPathByHostId("4");*/
        
        JSONArray array=new JSONArray();
        JSONObject object=new JSONObject();
        object.put("key1", "value1");
        array.add(object);
        System.out.println(((JSONObject) (array.get(0))).get("key1"));
        
    }

    @Override
    public IBOAIMonPhysicHostValue[] qryHostInfosByGroupId(String groupId) throws Exception
    {   
        IAIMonGroupHostRelDAO relDao = (IAIMonGroupHostRelDAO) ServiceFactory.getService(IAIMonGroupHostRelDAO.class);
        BOAIMonGroupHostRelBean[] relArr = relDao.getBeansByGroupId(Long.parseLong(groupId));
        IAIMonPhysicHostSV hostSV=(IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IAIMonGroupHostRelSV ghSv = (IAIMonGroupHostRelSV) ServiceFactory.getService(IAIMonGroupHostRelSV.class);
        List list = ghSv.getHostListByGroupId(groupId);
        IBOAIMonPhysicHostValue[] result = null;
        if(list.size() > 0) {
            result = hostSV.qryByList(list);
        }
        return result;
    }
}
