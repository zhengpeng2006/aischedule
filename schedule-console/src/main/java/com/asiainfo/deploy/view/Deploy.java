package com.asiainfo.deploy.view;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

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
import com.asiainfo.deploy.api.external.PackageDispatcherUtils;
import com.asiainfo.deploy.api.interfaces.IDeployStrategySVProvider;
import com.asiainfo.deploy.api.interfaces.IVersionSVProvider;
import com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelationBean;
import com.asiainfo.deploy.common.bo.BODeployNodeVersionBean;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.deploy.common.bo.BODeployVersionBean;
import com.asiainfo.deploy.common.constants.Category.NodeStatus;
import com.asiainfo.deploy.dao.interfaces.IVersionDAO;
import com.asiainfo.deploy.installpackage.NodePublishStatusManager;
import com.asiainfo.deploy.installpackage.NodePublishStatusManager.NodePublishStatus;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;
import com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.service.ExternalSvUtil;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class Deploy extends AppPage
{
    
    private static final Logger LOGGER = Logger.getLogger(Deploy.class);
    
    /** 操作结果 */
    private static final String FLAG = "flag";
    
    /** 操作结果：成功 */
    private static final String FLAG_SUCCESS = "T";
    
    /** 操作结果：失败 */
    private static final String FLAG_FAILED = "F";
    
    /** 操作结果：失败详情 */
    private static final String RESULT_MESSAGE = "msg";
    
    /** 记录操作结果的map */
    private static Map<NodeStatus, String> statusMap = null;
    
    /** 静态数据map */
    private static Map<String, Map<String, Object>> staticMap = null;
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 加载树
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
        TreeBean[] treeBeanArr = getTreeItems();
        
        TreeItem[] root = TreeHelper.getWholeTreeData(treeBeanArr);
        IData treeNodes = TreeFactory.buildTreeData(param, root);
        
        getContext().setAjax(treeNodes);
    }
    
    public void loadTableData(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String strategyName = getContext().getParameter("strategyName");
        String strategyIdStr = getContext().getParameter("strategyId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONArray array = new JSONArray();
        if (strategyIdStr != null)
        {
            long strategyId = Long.parseLong(strategyIdStr);
            BODeployNodeStrategyRelationBean[] beans = DeployStrategyUtils.getNodesByStrategyId(strategyId);
            Map<Long, IBOAIMonNodeValue> nodeMap = getNodeMap();
            Map<Long, IBOAIMonPhysicHostValue> hostMap = getHostMap();
            // 第一次循环为了提取出所有节点ID，便于批量查询节点的版本号
            List<Long> nodes = new ArrayList<Long>();
            if (beans != null)
            {
                for (BODeployNodeStrategyRelationBean bean : beans)
                {
                    nodes.add(bean.getNodeId());
                }
                Map<Long, BODeployNodeVersionBean> versionMap = getVersionMap(nodes);
                Map<Long, String> hostGroupMap = ExternalSvUtil.getHostGroups();
                for (BODeployNodeStrategyRelationBean bean : beans)
                {
                    JSONObject obj = new JSONObject();
                    obj.put("stragety", strategyName);
                    obj.put("nodeId", bean.getNodeId());
                    IBOAIMonNodeValue node = nodeMap.get(bean.getNodeId());
                    obj.put("nodeCode", node.getNodeCode());
                    obj.put("nodeName", node.getNodeName());
                    IBOAIMonPhysicHostValue host = hostMap.get(node.getHostId());
                    obj.put("hostName", host.getHostName() + "(" + host.getHostIp() + ")");
                    String temp = hostGroupMap.get(host.getHostId());
                    obj.put("hostGroup", StringUtils.isBlank(temp) ? "" : temp);
                    String createDate = sdf.format(node.getCreateDate());
                    if (!versionMap.isEmpty() && versionMap.get(bean.getNodeId()) != null)
                    {
                        obj.put("versionId", versionMap.get(bean.getNodeId()).getVersionId());
                    }
                    obj.put("createDate", createDate);
                    obj.put("remark", node.getRemark());
                    // 查询操作状态
                    NodePublishStatus status = NodePublishStatusManager.getNodeStatus(bean.getNodeId());
                    if (status == null)
                    {
                        obj.put("operationState", "");
                    }
                    else
                    {
                        String str = getStatusMap().get(status.status());
                        str = str == null ? "" : str;
                        obj.put("operationState", status.status() + ":" + str);
                    }
                    obj.put("operationStateMsg", status == null ? "" : status.message());
                    
                    array.add(obj);
                }
            }
        }
        else
        {
            LOGGER.error("init node table failed,strategyId is null");
        }
        /* this.setNodeTableItems(array); */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nodeTableItems", PagingUtil.convertArray2Page(array, offset, linage));
        jsonObject.put("total", array.size());
        this.setAjax(jsonObject);
    }
    
    /**
     * 检查版本一致性
     * 
     * @param request
     * @throws Exception
     */
    public void checkVerion(IRequestCycle request)
        throws Exception
    {
        IVersionSVProvider sv = (IVersionSVProvider)ServiceFactory.getService(IVersionSVProvider.class);
        boolean flag = false;
        String data = getContext().getParameter("data");
        if (StringUtils.isNotBlank(data))
        {
            // 当传入参数中带“，”时，说明是节点ID；否则为部署策略ID
            if (data.indexOf(",") > 0)
            {
                List<Long> nodeIds = new ArrayList<Long>();
                String[] ids = data.split(",");
                for (String string : ids)
                {
                    // 由于前台传入参数为 节点ID#版本ID，所以处理下
                    nodeIds.add(Long.parseLong(string.split("#")[0]));
                }
                flag = sv.isAllNodesHaveSameVersion(nodeIds);
            }
            else
            {
                flag = sv.isAllNodesHaveSameVersion(Long.parseLong(data.trim()));
            }
        }
        JSONObject obj = new JSONObject();
        obj.put(FLAG, flag ? FLAG_SUCCESS : FLAG_FAILED);
        this.setAjax(obj);
    }
    
    /**
     * 保存新版本(后续操作有更新和发布两种)
     * 
     * @param request
     * @throws Exception
     */
    public void saveVersion(IRequestCycle request)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        
        String param = getContext().getParameter("param");
        IDataBus versionBus = createData("versionInfo");
        JSONObject result = new JSONObject();
        BODeployVersionBean version = (BODeployVersionBean)DataBusHelper.getBOBean(versionBus);
        // 拼接其他属性+++++++++++++++++++++++++++
        IVersionSVProvider sv = (IVersionSVProvider)ServiceFactory.getService(IVersionSVProvider.class);
        try
        {
            Map<String, Object> map = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(param))
            {
                String[] inputParams = param.split(",");
                // 前台参数格式为 操作名，策略id，节点id#版本号，节点id#版本号....
                for (int i = 0; i < inputParams.length; i++)
                {
                    if (i == 0)
                    {
                        map.put("operation", inputParams[i]);
                    }
                    else if (i == 1)
                    {
                        map.put("strategyId", inputParams[i]);
                    }
                    else
                    {
                        String temp = (String)map.get("nodeIds");
                        if (StringUtils.isNotBlank(temp))
                        {
                            temp += inputParams[i];
                            if (i < inputParams.length - 1)
                            {
                                temp += ",";
                            }
                            map.put("nodeIds", temp);
                        }
                        else
                        {
                            map.put("nodeIds", inputParams[i] + ",");
                        }
                    }
                }
                
            }
            else
            {
                LOGGER.error("input param is null");
                return;
            }
            long newVersionId = sv.nextId();
            long strategyId = Long.parseLong((String)map.get("strategyId"));
            BODeployVersionBean versionBean = sv.currentVersion(strategyId);
            if (versionBean != null)
            {
                long parentId = versionBean.getVersionId();// 取策略当前版本为上个版本
                version.setParentVersionId(parentId);
            }
            version.setDeployStrategyId(strategyId);
            version.setVersionId(newVersionId);
            version.setCreateTime(new Timestamp(System.currentTimeMillis()));
            Object dataIds = handleIds((String)map.get("nodeIds"));
            
            if ("deploy".equalsIgnoreCase((String)map.get("operation")))
            {
                version.setOperateType("0");// 记录操作类型,发布
                version.setState("I");
                
                sv.saveVersion(version);
                result = deploy(result, dataIds, newVersionId, (String)map.get("nodeIds"));
            }
            else
            {
                LOGGER.error("operation is wrong: " + map.get("operation"));
                retMap.put(FLAG, ResultConstants.ResultCodeValue.FAILED);
                retMap.put(RESULT_MESSAGE, "operation is wrong");
            }
            
        }
        catch (Exception e)
        {
            
            LOGGER.error("save version info failed,reason is: " + e);
            retMap.put(FLAG, ResultConstants.ResultCodeValue.FAILED);
            retMap.put(RESULT_MESSAGE, e.getMessage());
            // String str = ScheduleUtil.getErrMap().get(e.getMessage());
            // retMap.put(RESULT_MESSAGE, StringUtils.isNotBlank(str) ? str : e.getLocalizedMessage());
        }
        result.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get(FLAG));
        result.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get(RESULT_MESSAGE));
        this.setAjax(result);
    }
    
    /**
     * 展示版本详情
     * 
     * @param request
     * @throws Exception
     */
    public void showVersionDetail(IRequestCycle request)
        throws Exception
    {
        String versionId = getContext().getParameter("versionId").trim();
        IVersionDAO dao = (IVersionDAO)ServiceFactory.getService(IVersionDAO.class);
        if (StringUtils.isNotBlank(versionId))
        {
            BODeployVersionBean version = dao.getVersionById(Long.parseLong(versionId));
            IDataBus bus = null;
            if (version == null)
            {
                bus = DataBusHelper.getEmptyDataBus();
            }
            else
            {
                bus = DataBusHelper.getDataBusByBeans(version, BODeployVersionBean.class);
            }
            /* this.setVersionDetail(bus.getDataObject()); */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("versionDetail", bus.getDataObject());
            this.setAjax(jsonObject);
            
        }
    }
    
    /**
     * 将策略写所有节点更新至策略当前节点
     * 
     * @param request
     * @throws Exception
     */
    public void updateToCurVersion(IRequestCycle request)
        throws Exception
    {
        String strategyIdStr = getContext().getParameter("strategyId");
        JSONObject result = new JSONObject();
        if (StringUtils.isNotBlank(strategyIdStr))
        {
            long strategyId = Long.parseLong(strategyIdStr);
            try
            {
                // 添加业务操作日志
                IDeployStrategySVProvider provider =
                    (IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class);
                BODeployStrategyBean strategy = provider.getStrategyById(strategyId);
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_DEPLOY,
                    CommonConstants.OPERATE_TYPE_SYCHRONIZE,
                    CommonConstants.OPERTATE_OBJECT_STRATEGY,
                    strategyId + ":" + strategy.getDeployStrategyName(),
                    null);
                PackageDispatcherUtils.unifyToCurrentVersion(strategyId);
                result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
            }
            catch (Exception e)
            {
                result = handelReturnResult(e.getMessage(), result, "updateToCurVersion");
            }
        }
        else
        {
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
            result.put(ResultConstants.ResultKey.RESULT_MSG, "strategyId is null");
            LOGGER.error("update stregaty to curVersion failed,strategyId is null");
        }
        this.setAjax(result);
    }
    
    /**
     * 展示历史版本信息
     * 
     * @param request
     * @throws Exception
     */
    public void showHisVersion(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String strategyIdStr = getContext().getParameter("stretagyId");
        if (StringUtils.isNotBlank(strategyIdStr))
        {
            IVersionDAO dao = (IVersionDAO)ServiceFactory.getService(IVersionDAO.class);
            BODeployVersionBean[] versions = dao.getValidHistoryVersionsAsc(Long.parseLong(strategyIdStr));
            IDataBus bus = null;
            if (versions == null)
                bus = DataBusHelper.getEmptyArrayDataBus();
            else
                bus = DataBusHelper.getDataBusByBeans(versions, BODeployVersionBean.class);
            /* this.setHisVersionTableItems(bus.getDataArray()); */
            
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hisVersionTableItems", PagingUtil.convertArray2Page(bus.getDataArray(), offset, linage));
            jsonObject.put("total", bus.getDataArray().size());
            this.setAjax(jsonObject);
            
        }
    }
    
    /**
     * 回滚操作
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public void rollback(IRequestCycle request)
        throws Exception
    {
        
        JSONObject result = new JSONObject();
        
        String[] paramArray = getContext().getParameter("param").split(",");
        String nodeIdsStr = getContext().getParameter("nodeIds");
        try
        {
            // 参数顺序是 版本号,部署策略ID
            long versionId = Long.parseLong(paramArray[0]);
            List<Long> nodeIds = handleIds(nodeIdsStr);
            if (nodeIds != null)
            {
                try
                {
                    // 添加业务日志 考虑批量查询节点信息(需改善)
                    OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_DEPLOY,
                        CommonConstants.OPERATE_TYPE_ROLLBACK,
                        CommonConstants.OPERTATE_OBJECT_NODE,
                        nodeIdsStr,
                        null);
                    
                    PackageDispatcherUtils.rollback(nodeIds, versionId);
                    result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
                }
                catch (Exception e)
                {
                    result = handelReturnResult(e.getMessage(), result, "rollback");
                }
            }
            else
            {
                LOGGER.error("rollback failed,reason is nodeIds and stregatyId are all null");
                result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                result.put(ResultConstants.ResultKey.RESULT_MSG,
                    "rollback failed,reason is nodeIds and stregatyId are all null");
            }
        }
        catch (Exception e)
        {
            LOGGER.error("rollback failed,reason is" + e + ";param is:" + ArrayUtils.toString(paramArray));
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
            result.put(ResultConstants.ResultKey.RESULT_MSG, e.getMessage());
        }
        this.setAjax(result);
    }
    
    /**
     * 初始化主机
     * 
     * @param result
     * @param params
     * @param versionId
     * @return
     */
    public void initHost(IRequestCycle request)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        JSONObject result = new JSONObject();
        String strategyIdStr = getContext().getParameter("strategyId").trim();
        String nodesStr = getContext().getParameter("nodes").trim();
        try
        {
            if (StringUtils.isNotBlank(strategyIdStr))
            {
                long strategyId = Long.parseLong(strategyIdStr);
                List<Long> list = getNodeIds(nodesStr);
                // 记录操作日志
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_DEPLOY,
                    CommonConstants.OPERATE_TYPE_HOST_INIT,
                    CommonConstants.OPERTATE_OBJECT_NODE,
                    nodesStr,
                    null);
                
                PackageDispatcherUtils.hostInitialization(list, strategyId);
                retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
            }
            else
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", "strategy id is null");
                LOGGER.error("initHost failed,reason is strategy id is null");
            }
            ;
        }
        catch (Exception e)
        {
            retMap.put("flag", ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("initHost failed", e);
        }
        
        result.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("flag"));
        result.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(result);
    }
    
    /**
     * 转换操作类型
     * 
     * @param operType
     * @return
     */
    public String operTypeTrans(String operType)
    {
        return (String)getStaticMap().get("operType").get(operType);
    }
    
    /**
     * 转换版本本类型
     * 
     * @param versionType
     * @return
     */
    public String versionTypeTrans(String versionType)
    {
        return (String)getStaticMap().get("version").get(versionType);
    }
    
    /**
     * 转换策略名
     * 
     * @param strategyId
     * @return
     * @throws Exception
     * @throws
     */
    public String strategyTrans(String strategyId)
        throws Exception
    {
        String strategyName = "";
        if (StringUtils.isNotBlank(strategyId))
        {
            IDeployStrategySVProvider sv =
                (IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class);
            BODeployStrategyBean strategy = sv.getStrategyById(Long.parseLong(strategyId));
            strategyName = strategy == null ? "" : strategy.getDeployStrategyName();
        }
        return strategyName;
    }
    
    /**
     * 将节点id字符串转换成节点id集合
     * 
     * @param nodes
     * @return
     */
    private List<Long> getNodeIds(String nodes)
    {
        List<Long> list = null;
        if (StringUtils.isNotBlank(nodes))
        {
            String[] temp = nodes.split(",");
            list = new ArrayList<Long>();
            for (String str : temp)
            {
                if (str.indexOf("#") > 0)
                {
                    str = str.split("#")[0];
                    list.add(Long.parseLong(str));
                }
                else
                {
                    list.add(Long.parseLong(str));
                }
            }
        }
        return list;
    }
    
    /**
     * 发布操作
     * 
     * @param result
     * @param params
     * @param versionId
     * @return
     */
    private JSONObject deploy(JSONObject result, Object param, long versionId, String nodes)
        throws Exception
    {
        try
        {
            if (param != null)
            {
                if (param instanceof List)
                {
                    try
                    {
                        // 添加业务日志 考虑批量查询节点信息(需改善)
                        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_DEPLOY,
                            CommonConstants.OPERATE_TYPE_DEPLOY,
                            CommonConstants.OPERTATE_OBJECT_NODE,
                            nodes,
                            null);
                        PackageDispatcherUtils.install((List<Long>)param, versionId);
                        result.put(FLAG, FLAG_SUCCESS);
                    }
                    catch (Exception e)
                    {
                        result = handelReturnResult(e.getMessage(), result, "deploy");
                        LOGGER.error("install error.", e);
                    }
                }
                else
                {
                    LOGGER.error("deploy failed, input param type is wrong;param is " + param.toString());
                    result.put(FLAG, FLAG_FAILED);
                }
            }
            else
            {
                LOGGER.error("deploy failed, input param is null");
                result.put(FLAG, FLAG_FAILED);
            }
            
        }
        catch (Exception e)
        {
            LOGGER.error("update failed,reason is" + e + ";input param is:" + param.toString());
            result.put(FLAG, FLAG_FAILED);
            throw e;
        }
        return result;
    }
    
    /**
     * 获取状态map
     * 
     * @return
     */
    private Map<NodeStatus, String> getStatusMap()
    {
        if (statusMap == null)
        {
            statusMap = new HashMap<NodeStatus, String>();
            statusMap.put(NodeStatus.BEGIN, "\u5f00\u59cb");
            statusMap.put(NodeStatus.CHECK_APP_RUNNING, "\u68c0\u6d4b\u5e94\u7528\u662f\u5426\u8fd0\u884c");
            statusMap.put(NodeStatus.WAIT_APP_STOP, "\u7b49\u5f85\u5e94\u7528\u505c\u6b62");
            statusMap.put(NodeStatus.HOST_INIT, "\u5e94\u7528\u521d\u59cb\u5316");
            statusMap.put(NodeStatus.CHECK_HOST_ENV, "\u9a8c\u8bc1\u4e3b\u673a\u73af\u5883");
            statusMap.put(NodeStatus.PUBLISH_INSTALL_PACKAGE, "\u5b89\u88c5\u5305\u5206\u53d1");
            statusMap.put(NodeStatus.INSTALL, "\u5b89\u88c5");
            statusMap.put(NodeStatus.CHECK_INSTALL_STATUS, "\u68c0\u67e5\u5b89\u88c5\u662f\u5426\u6210\u529f");
            statusMap.put(NodeStatus.ERROR, "\u5f02\u5e38");
            statusMap.put(NodeStatus.ROLL_BACK, "\u6b63\u5728\u56de\u6eda");
            statusMap.put(NodeStatus.SUCCESS, "\u6210\u529f");
            statusMap.put(NodeStatus.HOST_INIT, "\u4e3b\u673a\u521d\u59cb\u5316");
            statusMap.put(NodeStatus.HOST_INIT_BEGIN, "\u4e3b\u673a\u521d\u59cb\u5316\u5f00\u59cb");
            statusMap.put(NodeStatus.HOST_INIT_FAIL, "\u4e3b\u673a\u521d\u59cb\u5316\u5931\u8d25");
            statusMap.put(NodeStatus.HOST_INIT_MKDIR, "\u521d\u59cb\u5316\u76ee\u5f55");
            statusMap.put(NodeStatus.HOST_INIT_SUCCESS, "\u521d\u59cb\u5316\u6210\u529f");
            statusMap.put(NodeStatus.HOST_INIT_UPLOAD_BIN, "\u4e0a\u4f20\u542f\u52a8\u811a\u672c");
            statusMap.put(NodeStatus.HOST_INIT_UPLOAD_SBIN, "\u4e0a\u4f20sbin\u811a\u672c");
            
        }
        return statusMap;
    }
    
    /**
     * 获取所有节点map
     * 
     * @return
     */
    private Map<Long, IBOAIMonNodeValue> getNodeMap()
    {
        Map<Long, IBOAIMonNodeValue> map = new HashMap<Long, IBOAIMonNodeValue>();
        try
        {
            IBOAIMonNodeValue[] values = ExternalSvUtil.getAllNode();
            if (values != null)
            {
                for (IBOAIMonNodeValue value : values)
                {
                    map.put(value.getNodeId(), value);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("get all node info failed,reason is:" + e);
        }
        return map;
    }
    
    /**
     * 获取所有主机map
     * 
     * @return
     */
    private Map<Long, IBOAIMonPhysicHostValue> getHostMap()
    {
        Map<Long, IBOAIMonPhysicHostValue> map = new HashMap<Long, IBOAIMonPhysicHostValue>();
        try
        {
            IBOAIMonPhysicHostValue[] beans = ExternalSvUtil.getAllPhostBean();
            if (beans != null)
            {
                for (IBOAIMonPhysicHostValue val : beans)
                {
                    map.put(val.getHostId(), val);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("get all node info failed,reason is:" + e);
        }
        return map;
    }
    
    /**
     * 获取所有主机集群map
     * 
     * @return
     */
    private Map<Long, IBOAIMonPInfoGroupValue> getHostGroupMap()
    {
        Map<Long, IBOAIMonPInfoGroupValue> map = new HashMap<Long, IBOAIMonPInfoGroupValue>();
        try
        {
            IAIMonPInfoGroupSV sv = (IAIMonPInfoGroupSV)ServiceFactory.getService(IAIMonPInfoGroupSV.class);
            IBOAIMonPInfoGroupValue[] beans = sv.getAllMonPInfoGroupBean();
            if (beans != null)
            {
                for (IBOAIMonPInfoGroupValue val : beans)
                {
                    map.put(val.getGroupId(), val);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("get all node info failed,reason is:" + e);
        }
        return map;
    }
    
    /**
     * 获取传入节点对应版本号的map
     * 
     * @return
     */
    private Map<Long, BODeployNodeVersionBean> getVersionMap(List<Long> nodes)
    {
        Map<Long, BODeployNodeVersionBean> map = new HashMap<Long, BODeployNodeVersionBean>();
        try
        {
            BODeployNodeVersionBean[] beans = DeployStrategyUtils.qryNodeVersionsByNodes(nodes);
            if (beans != null)
            {
                for (BODeployNodeVersionBean bean : beans)
                {
                    map.put(bean.getNodeId(), bean);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("get all node info failed,reason is:" + e);
        }
        return map;
    }
    
    /**
     * 操作前处理页面传过来的节点id 若传入节点Id为空，则返回null 将用“，”连接的字符串转换为List<Long> 注：现在id为id#版本号 需要解析
     * 
     * @param ids
     * @return
     */
    private List<Long> handleIds(String ids)
    {
        List<Long> list = null;
        if (StringUtils.isNotBlank(ids))
        {
            list = new ArrayList<Long>();
            if (ids.indexOf(",") > 0)
            {
                String[] strs = ids.split(",");
                for (String str : strs)
                {
                    if (str.indexOf("#") > 0)
                    {
                        str = str.split("#")[0];
                        list.add(Long.parseLong(str));
                    }
                }
            }
            else
            {
                if (ids.indexOf("#") > 0)
                {
                    ids = ids.split("#")[0];
                    list.add(Long.parseLong(ids));
                }
            }
        }
        return list;
    }
    
    /**
     * 处理失败操作的返回结构
     * 
     * @param er
     * @param result
     * @param operationType
     * @return
     */
    private JSONObject handelReturnResult(String errorCode, JSONObject result, String operationType)
    {
        result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
        // result.put("msg", errorCode);
        StringBuffer sb = new StringBuffer();
        sb.append("operation failed,operation is:").append(operationType).append(",errorCode is:").append(errorCode);
        LOGGER.error(sb.toString());
        return result;
    }
    
    /**
     * 生成树节点
     * 
     * @return
     */
    private TreeBean[] getTreeItems()
    {
        
        // 树节点list
        List<TreeBean> treeList = new ArrayList<TreeBean>();
        
        // 根节点初始化
        TreeBean rootNode = new TreeBean();
        rootNode.setCode("root");
        rootNode.setId("-1");
        rootNode.setLabel("部署策略");
        rootNode.setLevel("1");
        rootNode.setParentId("0");
        rootNode.setValue("root");
        treeList.add(rootNode);
        
        Map<Long, BODeployStrategyBean> map;
        try
        {
            map = DeployStrategyUtils.getDeployStrategyList();
            // 循环遍历生成树节点数据
            TreeBean treeNode2 = null;
            if (!map.isEmpty())
            {
                for (BODeployStrategyBean bean : map.values())
                {
                    String level = "2";
                    treeNode2 = new TreeBean();
                    treeNode2.setId(bean.getDeployStrategyId() + "_" + level);
                    treeNode2.setLabel(bean.getDeployStrategyName());
                    treeNode2.setLevel(level);
                    treeNode2.setParentId(rootNode.getId());
                    treeList.add(treeNode2);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("get tree bean failed,reason is:" + e);
        }
        
        return treeList.toArray(new TreeBean[0]);
        
    }
    
    /**
     * 获取静态数据
     * 
     * @return
     */
    private Map<String, Map<String, Object>> getStaticMap()
    {
        if (staticMap == null)
        {
            staticMap = new HashMap<String, Map<String, Object>>();
            // 操作类型
            Map<String, Object> operTypeMap = new HashMap<String, Object>();
            // 发布
            operTypeMap.put("0", "\u53d1\u5e03");
            // 回滚
            operTypeMap.put("1", "\u56de\u6eda");
            staticMap.put("operType", operTypeMap);
            // 版本类型
            Map<String, Object> versionMap = new HashMap<String, Object>();
            // 当前版本
            versionMap.put("C", "\u5f53\u524d\u7248\u672c");
            // 有效历史版本
            versionMap.put("V", "\u6709\u6548\u5386\u53f2\u7248\u672c");
            // 无效历史版本
            versionMap.put("I", "\u65e0\u6548\u5386\u53f2\u7248\u672c");
            staticMap.put("version", versionMap);
        }
        return staticMap;
    }
    
    public abstract void setHisVersionTableRowIndex(int hisVersionTableRowIndex);
    
    public abstract void setHisVersionTableCount(long hisVersionTableCount);
    
    public abstract void setHisVersionTableItem(JSONObject hisVersionTableItem);
    
    public abstract void setNodeTableRowIndex(int nodeTableRowIndex);
    
    public abstract void setNodeTableItem(JSONObject nodeTableItem);
    
    public abstract void setVersionInfo(JSONObject versionInfo);
    
    public abstract void setHisVersionTableItems(JSONArray hisVersionTableItems);
    
    public abstract void setVersionDetail(JSONObject versionDetail);
    
    public abstract void setNodeTableItems(JSONArray nodeTableItems);
    
    public abstract void setNodeTableCount(long nodeTableCount);
    
    @Override
    protected void initPageAttrs()
    {
        
        bindBoName("versionInfo", "com.asiainfo.deploy.common.bo.BODeployVersion");
        
        bindBoName("versionDetail", "com.asiainfo.deploy.common.bo.BODeployVersion");
        
        bindBoName("hisVersionTable", "com.asiainfo.deploy.common.bo.BODeployVersion");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus hisVersionTableItem = createData("hisVersionTableItem");
        if (hisVersionTableItem != null && !hisVersionTableItem.getDataObject().isEmpty())
        {
            setHisVersionTableItem(hisVersionTableItem.getDataObject());
        }
        IDataBus nodeTableItem = createData("nodeTableItem");
        if (nodeTableItem != null && !nodeTableItem.getDataObject().isEmpty())
        {
            setNodeTableItem(nodeTableItem.getDataObject());
        }
        IDataBus versionInfo = createData("versionInfo");
        if (versionInfo != null && !versionInfo.getDataObject().isEmpty())
        {
            setVersionInfo(versionInfo.getDataObject());
        }
        IDataBus versionDetail = createData("versionDetail");
        if (versionDetail != null && !versionDetail.getDataObject().isEmpty())
        {
            setVersionDetail(versionDetail.getDataObject());
        }
        initExtend(cycle);
    }
    
}
