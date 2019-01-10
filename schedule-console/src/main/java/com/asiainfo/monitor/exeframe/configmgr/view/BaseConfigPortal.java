package com.asiainfo.monitor.exeframe.configmgr.view;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IData;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.impl.TreeBean;
import com.ailk.common.data.impl.TreeHelper;
import com.ailk.common.data.impl.TreeItem;
import com.ailk.web.view.component.tree.TreeFactory;
import com.ailk.web.view.component.tree.TreeParam;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.deploy.api.external.DeployStrategyUtils;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupHostRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.common.service.interfaces.IBaseCommonSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupHostRelValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostConModeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonMasterSlaveRelValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeUserValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonHostConModeSV;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonMasterSlaveRelSV;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonNodeUserSV;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivUtils;
import com.asiainfo.resultConstant.ResultConstants;
import com.asiainfo.schedule.core.po.BackupInfoBean;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class BaseConfigPortal extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 前台状态翻译
     * 
     * @param type
     * @param key
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public String doTranslate(String type, String val)
        throws RemoteException, Exception
    {
        String text = null;
        if ("USER_STATE".equals(type))
        {
            text = UserPrivUtils.doTranslate(type, val);
        }
        else
        {
            if ("IS_MONITOR_NODE".equals(type))
            {
                if ("N".equals(val))
                {
                    text = "否";
                }
                if ("Y".equals(val))
                {
                    text = "是";
                }
            }
            else
            {
                // 通过codeType,codeValue,从表ai_mon_static_data得到codeName展示到前台页面
                IAIMonStaticDataSV sdSv = (IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
                IBOAIMonStaticDataValue value = sdSv.queryByCodeTypeAndValue(type, val);
                text = value.getCodeName();
            }
        }
        return text;
    }
    
    /**
     * 加载组织树
     * 
     * @param request
     * @throws Exception
     */
    public void loadTreeData(IRequestCycle request)
        throws Exception
    {
        // 获取角色标识
        TreeParam param = TreeParam.getTreeParam(request);
        
        // 同步全量加载树
        TreeBean[] treeBeanArr = CommonSvUtil.getTreeItems(4);
        
        TreeItem[] root = TreeHelper.getWholeTreeData(treeBeanArr);
        IData treeNodes = TreeFactory.buildTreeData(param, root);
        
        getContext().setAjax(treeNodes);
    }
    
    /**
     * 查询数据
     * 
     * @param request
     * @throws Exception
     */
    public void loadTableData(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String treeNodeId = getContext().getParameter("treeNodeId");
        String level = getContext().getParameter("level");
        
        JSONObject jOb = new JSONObject();
        // 查询分组
        if (level.equals(CommonConst.SYS_LEVEL))
        {
            // 分页情况暂不考虑
            // int start = Integer.parseInt(qryformBus.getContext().getPaginStart() + "");
            // int end = Integer.parseInt(qryformBus.getContext().getPaginEnd() + "");
            // int totalCnt = sv.getUserCntByCond(userCode, userName);
            
            JSONArray groupArr = this.qryGroupInfo();
            
            // this.setGroupTabItems(groupArr);
            // this.setGroupTabCount(groupArr.size());
            jOb.put("groupTabItems", PagingUtil.convertArray2Page(groupArr, offset, linage));
            jOb.put("total", groupArr.size());
            
        }
        // 查询主机
        else if (level.equals(CommonConst.GROUP_LEVEL))
        {
            JSONArray qryHostInfo = this.qryHostInfo(treeNodeId);
            
            // this.setHostTabItems(qryHostInfo);
            // this.setHostTabCount(qryHostInfo.size());
            jOb.put("hostTabItems", PagingUtil.convertArray2Page(qryHostInfo, offset, linage));
            jOb.put("total", qryHostInfo.size());
        }
        // 查询节点
        else if (level.equals(CommonConst.HOST_LEVEL))
        {
            JSONArray qryNodeInfo = this.qryNodeInfo(treeNodeId);
            
            // this.setNodeTabItems(qryNodeInfo);
            // this.setNodeTabCount(qryNodeInfo.size());
            jOb.put("nodeTabItems", PagingUtil.convertArray2Page(qryNodeInfo, offset, linage));
            jOb.put("total", qryNodeInfo.size());
        }
        // 查询应用
        else if (level.equals(CommonConst.NODE_LEVEL))
        {
            
            JSONArray qryServerInfo = this.qryServerInfo(treeNodeId);
            
            // this.setServerTabItems(qryServerInfo);
            // this.setServerTabCount(qryServerInfo.size());
            jOb.put("serverTabItems", PagingUtil.convertArray2Page(qryServerInfo, offset, linage));
            jOb.put("total", qryServerInfo.size());
        }
        this.setAjax(jOb);
    }
    
    /**
     * 查询所有集群组信息
     * 
     * @return
     * @throws Exception
     * @throws
     */
    private JSONArray qryGroupInfo()
        throws Exception
    {
        // 查询所有分组
        IAIMonGroupSV groupSv = (IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
        UserInfoInterface userInfo = CommonSvUtil.getUserInfo();
        IBOAIMonGroupValue[] result = null;
        // 当用户信息不为空的时候，获取分组信息
        if (userInfo != null)
        {
            result = groupSv.getGroupByPriv(String.valueOf(userInfo.getID()) + "_" + userInfo.getCode());
        }
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonGroupValue.class);
        JSONArray dataArray = bus.getDataArray();
        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext())
        {
            JSONObject obj = (JSONObject)iterator.next();
            if (obj.get("createDate").toString().length() > 8)
            {
                obj.put("createDate", obj.get("createDate").toString().substring(0, 10));
            }
        }
        return bus.getDataArray();
    }
    
    /**
     * 查询分组下主机信息
     * 
     * @param groupId
     * @return
     * @throws Exception
     */
    private JSONArray qryHostInfo(String groupId)
        throws Exception
    {
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
        String[] arr = groupId.split("_");
        groupId = arr[0];
        IAIMonGroupHostRelSV ghSv = (IAIMonGroupHostRelSV)ServiceFactory.getService(IAIMonGroupHostRelSV.class);
        List list = ghSv.getHostListByGroupId(groupId);
        IBOAIMonPhysicHostValue[] result = null;
        if (list.size() > 0)
        {
            result = hostSv.qryByList(list);
        }
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonPhysicHostValue.class);
        JSONArray dataArray = bus.getDataArray();
        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext())
        {
            JSONObject obj = (JSONObject)iterator.next();
            if (obj.get("createDate").toString().length() > 8)
            {
                obj.put("createDate", obj.get("createDate").toString().substring(0, 10));
            }
        }
        return bus.getDataArray();
    }
    
    /**
     * 查询主机下节点信息
     * 
     * @param hostId
     * @return
     * @throws Exception
     */
    private JSONArray qryNodeInfo(String hostId)
        throws Exception
    {
        IAIMonNodeSV nodeSv = (IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
        String[] arr = hostId.split("_");
        hostId = arr[0];
        IBOAIMonNodeValue[] result = nodeSv.qryNodeInfo(hostId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonNodeValue.class);
        JSONArray dataArray = bus.getDataArray();
        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext())
        {
            JSONObject obj = (JSONObject)iterator.next();
            if (obj.get("createDate").toString().length() > 8)
            {
                obj.put("createDate", obj.get("createDate").toString().substring(0, 10));
            }
            BODeployStrategyBean bean =
                DeployStrategyUtils.getDeployStrategyByNodeId(Long.parseLong(obj.get("nodeId").toString()));
            if (bean != null)
            {
                long deployStrategyId = bean.getDeployStrategyId();
                if (0 != deployStrategyId)
                {
                    obj.put("deployStrategyId", deployStrategyId);
                }
                
                String deployStrategyName = bean.getDeployStrategyName();
                if (!StringUtils.isBlank(deployStrategyName))
                {
                    obj.put("deployStrategyName", deployStrategyName);
                }
            }
            
        }
        
        return bus.getDataArray();
    }
    
    /**
     * 查询节点下应用信息
     * 
     * @param nodeId
     * @return
     * @throws Exception
     */
    private JSONArray qryServerInfo(String nodeId)
        throws Exception
    {
        IAIMonServerSV serverSv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
        String[] arr = nodeId.split("_");
        nodeId = arr[0];
        IBOAIMonServerValue[] result = serverSv.qryServerInfo(nodeId);
        
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonServerValue.class);
        
        return bus.getDataArray();
    }
    
    /**
     * 删除一条记录
     * 
     * @param request
     * @throws Exception
     */
    public void delTableRec(IRequestCycle request)
        throws Exception
    {
        String recId = getContext().getParameter("recId");
        String level = getContext().getParameter("level");
        // 这个nodeId是树上节点Id
        String treeNodeId = getContext().getParameter("treeNodeId");
        
        Map<String, String> retMap = null;
        
        // 查询分组
        if (level.equals(CommonConst.SYS_LEVEL))
        {
            
            retMap = this.delGroupInfo(recId);
        }
        // 查询主机
        else if (level.equals(CommonConst.GROUP_LEVEL))
        {
            
            retMap = this.delHostInfo(treeNodeId, recId);
        }
        // 查询节点
        else if (level.equals(CommonConst.HOST_LEVEL))
        {
            
            retMap = this.delNodeInfo(recId);
        }
        // 查询应用
        else if (level.equals(CommonConst.NODE_LEVEL))
        {
            
            retMap = this.delServerInfo(recId);
        }
        
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("DEL_FLAG"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(jsonObj);
    }
    
    /**
     * 删除分组信息
     * 
     * @param groupId
     * @return
     * @throws Exception
     */
    private Map<String, String> delGroupInfo(String groupId)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        IBaseCommonSV commonSv = (IBaseCommonSV)ServiceFactory.getService(IBaseCommonSV.class);
        IBOAIMonPhysicHostValue[] hostList = commonSv.qryHostInfosByGroupId(groupId);
        if (hostList != null && hostList.length > 0)
        {
            // 抛出异常，不能删除分组
            retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.FAILED);
            retMap.put("msg", ResultConstants.ResultCodeValue.DEL_FAILED);
            return retMap;
        }
        
        // 查询所有分组
        IAIMonGroupSV groupSv = (IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
        
        // 分组删除操作日志
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
            CommonConstants.OPERATE_TYPE_DELETE,
            CommonConstants.OPERTATE_OBJECT_HOSTGROUP,
            groupId,
            null);
        
        groupSv.deleteGroup(Long.parseLong(groupId));
        
        retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
        return retMap;
    }
    
    /**
     * 删除主机信息
     * 
     * @param hostId
     * @return
     * @throws Exception
     */
    private Map<String, String> delHostInfo(String treeNodeId, String hostId)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        IAIMonNodeSV nodeSv = (IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
        IBOAIMonNodeValue[] nodeValue = nodeSv.qryNodeInfo(hostId);
        if (nodeValue.length > 0)
        {
            // 抛出异常，不能删除主机
            retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.FAILED);
            retMap.put("msg", ResultConstants.ResultCodeValue.DEL_FAILED);
            return retMap;
        }
        
        // 删除主机
        IAIMonPhysicHostSV groupSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
        
        // 删除主机操作日志
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
            CommonConstants.OPERATE_TYPE_DELETE,
            CommonConstants.OPERTATE_OBJECT_HOST,
            hostId,
            "删除主机，主机标识：" + hostId);
        
        groupSv.deletePhysicHost(Long.parseLong(hostId));
        
        // 删除组与主机关系
        String[] arr = treeNodeId.split("_");
        String groupId = arr[0];
        IAIMonGroupHostRelSV ghSv = (IAIMonGroupHostRelSV)ServiceFactory.getService(IAIMonGroupHostRelSV.class);
        IBOAIMonGroupHostRelValue[] values = ghSv.qryGroupHost(groupId, hostId);
        
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
            CommonConstants.OPERATE_TYPE_DELETE,
            CommonConstants.OPERTATE_OBJECT_HOSTGROUP,
            groupId + ":" + hostId,
            "删除分组主机关系,分组标识" + groupId + ":主机标识" + hostId);
        
        ghSv.delete(values);
        
        // 删除主备机关系
        IAIMonMasterSlaveRelSV msrSv = (IAIMonMasterSlaveRelSV)ServiceFactory.getService(IAIMonMasterSlaveRelSV.class);
        IBOAIMonMasterSlaveRelValue[] msValues = msrSv.qryMasterSlaveRelByMasterHostId(hostId);
        if (msValues.length > 0)
        {
            // 删除主备机关系操作日志
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_MONITOR,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_HOST,
                msValues[0].getMasterHostId() + ":" + msValues[0].getSlaveHostId(),
                "主备机关系删除，主机ID为" + msValues[0].getMasterHostId() + ",备机ID为" + msValues[0].getSlaveHostId());
            msrSv.delete(msValues);
        }
        
        // 删除主机连接方式
        IAIMonHostConModeSV hcSv = (IAIMonHostConModeSV)ServiceFactory.getService(IAIMonHostConModeSV.class);
        IBOAIMonHostConModeValue[] conValues = hcSv.qryBuHostId(hostId);
        if (conValues.length > 0)
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_MONITOR,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_HOST,
                hostId + ":" + conValues[0].getConId(),
                "删除主机连接方式，主机ID为" + hostId + ",连接方式ID为" + conValues[0].getConId());
            hcSv.delete(conValues);
        }
        retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
        return retMap;
        
    }
    
    /**
     * 删除节点
     * 
     * @param nodeId
     * @return
     * @throws Exception
     */
    private Map<String, String> delNodeInfo(String nodeId)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        IAIMonServerSV serSv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
        IBOAIMonServerValue[] values = serSv.qryServerInfo(nodeId);
        if (values.length > 0)
        {
            // 抛出异常，不能删除节点
            retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.FAILED);
            retMap.put("msg", ResultConstants.ResultCodeValue.DEL_FAILED);
            return retMap;
        }
        
        // 删除节点
        IAIMonNodeSV nodeSv = (IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
        // 删除节点操作日志
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
            CommonConstants.OPERATE_TYPE_DELETE,
            CommonConstants.OPERTATE_OBJECT_NODE,
            nodeId,
            "删除节点,节点标识：" + nodeId);
        
        nodeSv.deleteNode(Long.parseLong(nodeId));
        
        IAIMonNodeUserSV userSv = (IAIMonNodeUserSV)ServiceFactory.getService(IAIMonNodeUserSV.class);
        IBOAIMonNodeUserValue[] userValues = userSv.qryNodeUserByNodeId(nodeId);
        if (userValues.length > 0)
        {
            // 删除节点用户操作日志
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_MONITOR,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_NODE,
                userValues[0].getNodeId() + ":" + userValues[0].getNodeUserId(),
                "删除节点用户关系，节点ID为" + userValues[0].getNodeId() + ",用户ID为" + userValues[0].getNodeUserId());
            userSv.delete(userValues[0]);
        }
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_MONITOR,
            CommonConstants.OPERATE_TYPE_MODIFY,
            CommonConstants.OPERTATE_OBJECT_NODE,
            nodeId,
            "删除节点引用的部署策略，节点标识：" + nodeId);
        DeployStrategyUtils.deleteNodeDeployStrategy(Long.parseLong(nodeId));
        
        retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
        return retMap;
    }
    
    /**
     * 删除应用
     * 
     * @param serverId
     * @return
     * @throws Exception
     */
    private Map<String, String> delServerInfo(String serverId)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        // 删除应用
        IAIMonServerSV serverSv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        
        // 存在备用进程，不能删除
        IBOAIMonServerValue serverValue = serverSv.qryServerByServerId(serverId);
        if (null != serverValue && StringUtils.isNotBlank(sv.getBackupConfig(serverValue.getServerCode())))
        {
            retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.FAILED);
            retMap.put("msg", "\u8bf7\u5148\u5220\u9664\u5907\u7528\u8fdb\u7a0b\uff01");
            return retMap;
        }
        
        if (null != serverValue)
        {
            // 解除serverid作为备进程的主备关系
            List<String> masterServerCode = sv.getBackupConfigByBackupServerCode(serverValue.getServerCode());
            if (null != masterServerCode && masterServerCode.size() > 0)
            {
                for (String s : masterServerCode)
                {
                    BackupInfoBean backupInfo = new BackupInfoBean();
                    backupInfo.setServerCode(s);
                    sv.deleteBackupConfig(backupInfo);
                }
            }
        }
        
        // 删除用户操作日志
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
            CommonConstants.OPERATE_TYPE_DELETE,
            CommonConstants.OPERTATE_OBJECT_APP,
            serverId,
            "删除应用，应用标识：" + serverId);
        serverSv.deleteServer(Long.parseLong(serverId));
        
        retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
        return retMap;
        
    }
    
    public abstract void setServerTabItems(JSONArray serverTabItems);
    
    public abstract void setServerTabItem(JSONObject serverTabItem);
    
    public abstract void setHostTabItem(JSONObject hostTabItem);
    
    public abstract void setGroupTabRowIndex(int groupTabRowIndex);
    
    public abstract void setServerTabCount(long serverTabCount);
    
    public abstract void setGroupTabItems(JSONArray groupTabItems);
    
    public abstract void setServerTabRowIndex(int serverTabRowIndex);
    
    public abstract void setHostTabCount(long hostTabCount);
    
    public abstract void setHostTabRowIndex(int hostTabRowIndex);
    
    public abstract void setNodeTabCount(long nodeTabCount);
    
    public abstract void setNodeTabItems(JSONArray nodeTabItems);
    
    public abstract void setNodeTabRowIndex(int nodeTabRowIndex);
    
    public abstract void setHostTabItems(JSONArray hostTabItems);
    
    public abstract void setGroupTabCount(long groupTabCount);
    
    public abstract void setNodeTabItem(JSONObject nodeTabItem);
    
    public abstract void setGroupTabItem(JSONObject groupTabItem);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("groupTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroup");
        
        bindBoName("hostTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHost");
        
        bindBoName("nodeTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonNode");
        
        bindBoName("serverTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonServer");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus serverTabItem = createData("serverTabItem");
        if (serverTabItem != null && !serverTabItem.getDataObject().isEmpty())
        {
            setServerTabItem(serverTabItem.getDataObject());
        }
        IDataBus hostTabItem = createData("hostTabItem");
        if (hostTabItem != null && !hostTabItem.getDataObject().isEmpty())
        {
            setHostTabItem(hostTabItem.getDataObject());
        }
        IDataBus nodeTabItem = createData("nodeTabItem");
        if (nodeTabItem != null && !nodeTabItem.getDataObject().isEmpty())
        {
            setNodeTabItem(nodeTabItem.getDataObject());
        }
        IDataBus groupTabItem = createData("groupTabItem");
        if (groupTabItem != null && !groupTabItem.getDataObject().isEmpty())
        {
            setGroupTabItem(groupTabItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
