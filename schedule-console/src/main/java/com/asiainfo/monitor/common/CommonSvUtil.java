package com.asiainfo.monitor.common;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.ai.appframe2.common.SessionManager;
import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.privilege.UserManagerFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.common.data.impl.TreeBean;
import com.asiainfo.monitor.acquire.AcquireConst;
import com.asiainfo.monitor.acquire.dto.AcqParamBean;
import com.asiainfo.monitor.busi.cache.impl.PhysicHost;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonCmdSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.common.combin.CmbHostBean;
import com.asiainfo.monitor.common.service.interfaces.IBaseCommonSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonCmdValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;

/**
 * 公共接口服务提供类，如获取系统结构菜单树数据等
 * 
 * @author wsk
 * 
 */
public class CommonSvUtil
{
    private static Logger logger = Logger.getLogger(CommonSvUtil.class);
    public static final String WBS_USER_ATTR = "USERINFO_ATTR";

    /**
     * 获取系统结构菜单树数据
     * 
     * @param treeLevel,标识获取到数据的层级2：分组层级，3：主机层级，4：节点层级，5，应用层级，暂时没有5
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static TreeBean[] getTreeItems(int treeLevel) throws Exception
    {
        // 通用服务
        IBaseCommonSV commonService = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        
        // 获取所有监控分组信息
        //Map<String, Group> groupMap = CacheFactory.getAll(AIMonGroupCacheImpl.class);
        IAIMonGroupSV monGroupSV=(IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
    //        IBOAIMonGroupValue[] groupValues=monGroupSV.getAllGroupBean();
        
        //获取当前用户信息
        UserInfoInterface userInfo=getUserInfo();
        IBOAIMonGroupValue[] groupValues=null;
        //当用户信息不为空的时候，获取分组信息
        if(userInfo!=null) {
            groupValues=monGroupSV.getGroupByPriv(String.valueOf(userInfo.getID())+"_"+userInfo.getCode());
        }
        // 树节点list
        List<TreeBean> treeList = new ArrayList<TreeBean>();
        if(groupValues!=null&&groupValues.length>0) {
            // 主机节点Map
            Map<String, List<IBOAIMonNodeValue>> hostNodeMap = commonService.fetchHostNodeMap();
            // 根节点初始化
            TreeBean rootNode = new TreeBean();
            rootNode.setCode("root");
            rootNode.setId("-1");
            rootNode.setLabel("系统节点");
            rootNode.setLevel("1");
            rootNode.setParentId("0");
            rootNode.setValue("root");
            treeList.add(rootNode);

            // 循环遍历生成树节点数据
            // Group[] grpArr = groupMap.values().toArray(new Group[0]);
            TreeBean treeNode2 = null;
            for(int i = 0; i < groupValues.length; i++) {
                String level = "2";
                treeNode2 = new TreeBean();
                treeNode2.setId(groupValues[i].getGroupId() + "_" + level);
                treeNode2.setLabel(groupValues[i].getGroupName());
                treeNode2.setLevel(level);
                treeNode2.setParentId(rootNode.getId());
                treeList.add(treeNode2);

                //只查询到分组节点
                if(treeLevel < 3) {
                    continue;
                }

                // 获得二级主机节点
                IBOAIMonPhysicHostValue[] values=commonService.qryHostInfosByGroupId(String.valueOf(groupValues[i].getGroupId()));
                if(values!=null&&values.length>0) {
                    TreeBean treeNode3 = null;
                    for(IBOAIMonPhysicHostValue hostBean : values) {
                        level = "3";
                        treeNode3 = new TreeBean();
                        treeNode3.setId(hostBean.getHostId() + "_" + level);
                        treeNode3.setLabel(hostBean.getHostName());
                        treeNode3.setLevel(level);
                        treeNode3.setParentId(treeNode2.getId());
                        treeList.add(treeNode3);

                        //子查询到主机节点
                        if(treeLevel < 4) {
                            continue;
                        }

                        // 获得3级节点，主机节点
                        List<IBOAIMonNodeValue> list = hostNodeMap.get(""+hostBean.getHostId());
                        TreeBean treeNode4 = null;
                        if(null != list) {
                            for(IBOAIMonNodeValue node : list) {
                                level = "4";
                                treeNode4 = new TreeBean();
                                treeNode4.setId(node.getNodeId() + "_" + level);
                                treeNode4.setLabel(node.getNodeName());
                                treeNode4.setLevel(level);
                                treeNode4.setParentId(treeNode3.getId());
                                treeList.add(treeNode4);
                            }
                        }
                    }
                }
            }
        }
        return (TreeBean[]) treeList.toArray(new TreeBean[0]);
    }
    /**
     * 获取当前用户信息
     * @param request
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public static UserInfoInterface getUserInfo() throws RemoteException, Exception{
      //获取用户信息
        UserInfoInterface[] userInfos =UserManagerFactory.getUserManager().getLogedUsers();
        UserInfoInterface userInfo=null;
        String serialID= (String) SessionManager.getRequest().getSession().getAttribute(WBS_USER_ATTR);
        System.out.println("serialId="+serialID);
        if(userInfos!=null&&userInfos.length>0) {
            for(UserInfoInterface userInfoInter:userInfos) {
                System.out.println("userInfoInter.getSessionID()="+userInfoInter.getSerialID());
                if(userInfoInter.getSerialID().equals(serialID)) {
                    userInfo=userInfoInter;
                }
            }
        }
        return userInfo;
    }
    /**
     * 根据主机标识查询主机信息，包含主机属性
     * @param hostId
     * @return
     * @throws Exception
     */
    public static CmbHostBean qryCmbHostInfo(String hostId) throws Exception
    {
        IBaseCommonSV commonService = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonService.qryCmbHostInfo(hostId);
    }

    /**
     * 获取主机信息，以及执行的脚本信息
     * 
     * @param hostId
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public static AcqParamBean getHostExeShellInfo(String hostId, String cmdId) throws Exception, RemoteException
    {
        // 获取主机信息
        CmbHostBean hostBean = CommonSvUtil.qryCmbHostInfo(hostId);

        // 获取主机用户信息
        Map<String, String> userInfoMap = qryMonitorNodeUserInfoByHostId(hostId);

        //获取当前主机下监控节点的部署策略
        String homePath = qryMonNodeDeployPathByHostId(hostId);

        //当监控节点不存在时，需要抛出异常
        if(userInfoMap.isEmpty()) {
            logger.error(" monitor user is not find,please check the config ...");
            //throw new BaseException("主机：" + hostId + " 没有找到监控用户(监控节点下面的用户)，请检查配置");
        }
        /*   Map<String, String> pubUserInfo = hostBean.getPubUserInfo();
           if(pubUserInfo == null) {
               logger.error("publish application user is not find, please check the config...");
               throw new BaseException("主机：" + hostId + " 没有找到发布应用部署用户，请检查配置");
           }
           String lookupUserName = pubUserInfo.get(CommonConst.USER_NAME);*/

        // 获取脚本信息
        IAIMonCmdSV cmdSv = (IAIMonCmdSV) ServiceFactory.getService(IAIMonCmdSV.class);
        IBOAIMonCmdValue cmdVal = cmdSv.getCmdById(Long.parseLong(cmdId));

        AcqParamBean paramBean = new AcqParamBean();
        paramBean.setIp(hostBean.getHostIp());
        paramBean.setUsername(userInfoMap.get(CommonConst.USER_NAME));
        paramBean.setPassword(userInfoMap.get(CommonConst.USER_PASSWD));
        //paramBean.setPort(hostBean.getConPort(CommonConst.CON_TYPE_SSH));
        paramBean.setAcqType(AcquireConst.ACQ_TYPE_SHELL);
        paramBean.setKpiType(cmdVal.getCmdName()); // 注意这里相当于编码，数据库不能更改
        paramBean.setShell(cmdVal.getCmdExpr());
        //  paramBean.setLookupUserName(lookupUserName);//查找用户名

        paramBean.setTmpShellFileName(cmdId);
        paramBean.setHomePath(homePath);
        return paramBean;
    }

    /**
     * 根据应用标识查询主机信息
     * @param serverId
     * @return
     * @throws Exception
     */
    public static CmbHostBean qryCmbHostInfoByServerId(String serverId) throws Exception
    {
        IBaseCommonSV commonService = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonService.qryCmbHostInfoByServerId(serverId);
    }

    /**
     * 查询主机下所有应用信息
     * @param hostId
     * @return
     * @throws Exception
     */
    public static List<IBOAIMonServerValue> qryServerList(String hostId) throws Exception
    {
        IBaseCommonSV commonService = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonService.qryServerList(hostId);
    }

    /**
     * 通过应用编码查询主机信息
     * 
     * @param request
     * @throws Exception
     */
    public static Map<String, IBOAIMonPhysicHostValue> qryHostInfoByAppCode(List<String> list) throws Exception
    {
        IBaseCommonSV commonService = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonService.qryHostInfoByAppCode(list);
    }

    /**
     * 通过应用编码查询主机信息
     * 
     * @param request
     * @throws Exception
     */
    public static Map<String, IBOAIMonPhysicHostValue> qryHostInfoByCondition(String groupCode, String hostId, String serverCode) throws Exception
    {
        IBaseCommonSV commonService = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonService.qryHostInfoByCondition(groupCode, hostId, serverCode);
    }

    public static CmbHostBean qryCmbHostInfoByNodeId(String nodeId) throws Exception
    {
        IBaseCommonSV commonService = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonService.qryCmbHostInfoByNodeId(nodeId);
    }

    public static Map<String, PhysicHost> qryHostInfo() throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.qryHostInfo();
    }

    /**
     * 根据节点标识查询节点发布用户信息
     * 
     * 没有发布用户和监控用户的概念了目前  to use qryNodeUserInfoByNodeId
     * @param request
     * @throws Exception
     */
    /*public static Map<String, String> qryNodePubUserInfoByNodeId(String nodeId) throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.qryNodePubUserInfoByNodeId(nodeId);
    }*/

    /**
     * 根据节点标识查询节点发布用户信息
     * 
     * 没有发布用户和监控用户的概念
     * @param request
     * @throws Exception
     */
    public static Map<String, String> qryNodeUserInfoByNodeId(String nodeId) throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.qryNodeUserInfoByNodeId(nodeId);
    }

    /**
     * 根据应用标识查询节点发布用户信息
     * 没有发布用户和监控用户的概念
     * @param request
     * @throws Exception
     */
    /*
    public static Map<String, String> qryNodePubUserInfoByServerId(String serverId) throws Exception
    {
     IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
     return commonSv.qryNodePubUserInfoByServerId(serverId);
    }*/

    /**
     * 根据应用标识查询节点发布用户信息
     * 
     * @param request
     * @throws Exception
     */
    public static Map<String, String> qryNodeUserInfoByServerId(String serverId) throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.qryNodeUserInfoByServerId(serverId);
    }

    /**
     * 根据主机标识查询监控用户信息
     * 
     * @param request
     * @throws Exception
     */
    /*public static Map<String, String> qryNodeMonitorUserInfoByHostId(String hostId) throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.qryNodeMonitorUserInfoByHostId(hostId);
    }*/

    /**
     * 根据主机IP查找监控节点配置的用户
     * @param hostId
     * @return
     * @throws Exception
     */
    public static Map<String, String> qryMonitorNodeUserInfoByHostIp(String hostIp) throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.qryMonitorNodeUserInfoByHostIp(hostIp);
    }

    /**
     * 根据主机IP查找监控节点配置的用户
     * @param hostId
     * @return
     * @throws Exception
     */
    public static Map<String, String> qryMonitorNodeUserInfoByHostId(String hostId) throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.qryMonitorNodeUserInfoByHostId(hostId);
    }

    /**
     * 根据应用标识查询监控用户
     * 
     * @param request
     * @throws Exception
     */
    public static Map<String, String> qryMonitorNodeUserInfoByServerId(String serverId) throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.qryMonitorNodeUserInfoByServerId(serverId);
    }

    /**
     * 根据主机标识查询该主机上监控节点的部署策略路径
     * 
     * @param request
     * @throws Exception
     */
    public static String qryMonNodeDeployPathByHostId(String hostId) throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.qryMonNodeDeployPathByHostId(hostId);
    }

    /**
     * 根据主机IP查询监控节点的路径
     * 
     * @param request
     * @throws Exception
     */
    public static String qryMonNodeDeployPathByIp(String hostIp) throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.qryMonNodeDeployPathByIp(hostIp);
    }
    /**
     * 需要导出的主机信息
     * @param response 
     * @return
     * @throws Exception
     */
    public static JSONArray exportHostInfo() throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.exportHostInfo();
    }
    /**
     * 需要导出的主备机信息
     * @return
     * @throws Exception
     */
    public static HashMap<String,String> exportMasterSlaveInfo() throws Exception
    {
        IBaseCommonSV commonSv = (IBaseCommonSV) ServiceFactory.getService(IBaseCommonSV.class);
        return commonSv.exportMasterSlaveInfo();
    }

    public static void main(String[] args) throws Exception
    {
        /* Map<String, IBOAIMonPhysicHostValue> map = CommonSvUtil.qryHostInfoByCondition(null, null, "exe");
         System.out.println(map.size());*/
        /*String str = qryMonNodeDeployPathByHostId("609");
        System.out.println(str);*/
        //System.out.println(exportHostInfo());
    }

}
