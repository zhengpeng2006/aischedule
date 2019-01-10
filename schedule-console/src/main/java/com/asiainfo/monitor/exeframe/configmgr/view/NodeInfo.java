package com.asiainfo.monitor.exeframe.configmgr.view;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.deploy.api.external.DeployStrategyUtils;
import com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelationBean;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class NodeInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public IDataBus getSelectList()
        throws Exception
    {
        IAIMonNodeSV nSv = (IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
        return nSv.getSelectList();
    }
    
    /**
     * 是否监控节点列表
     * 
     * @param request
     * @throws Exception
     */
    public IDataBus getMonitorSelectList()
        throws Exception
    {
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("TEXT", "否");
        obj.put("VALUE", "N");
        JSONObject obj1 = new JSONObject();
        obj1.put("TEXT", "是");
        obj1.put("VALUE", "Y");
        jsonArray.add(obj1);
        jsonArray.add(obj);
        return new DataBus(context, jsonArray);
    }
    
    public void qryNodeInfo(IRequestCycle request)
        throws Exception
    {
        String nodeId = getContext().getParameter("nodeId");
        IAIMonNodeSV nodeSv = (IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
        IBOAIMonNodeValue nodeBean = nodeSv.qryNodeInfoByNodeId(nodeId);
        
        IDataBus dataBus = null;
        if (nodeBean == null)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(nodeBean, IBOAIMonNodeValue.class);
        }
        long deployStrategyId = DeployStrategyUtils.getDeployStrategyIdByNodeId(Long.parseLong(nodeId));
        JSONObject obj = new JSONObject();
        obj = dataBus.getDataObject();
        obj.put("deployStrategyId", deployStrategyId);
        obj.put("nodeNameNew", nodeBean.getNodeName());
        /* this.setNodeDetail(dataBus.getDataObject()); */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nodeDetail", dataBus.getDataObject());
        this.setAjax(jsonObject);
    }
    
    /**
     * 新增节点
     * 
     * @param request
     * @throws Exception
     */
    public void saveOrUpdate(IRequestCycle request)
        throws Exception
    {
        String treeNodeId = getContext().getParameter("treeNodeId");
        String[] arr = treeNodeId.split("_");
        String hostId = arr[0];
        IDataBus dataBus = this.createData("nodeDetail");
        // 老值
        dataBus.getRawData();
        // 新值
        dataBus.getData();
        IBOAIMonNodeValue value = (IBOAIMonNodeValue)DataBusHelper.getBOBean(dataBus);
        JSONObject deploy = (JSONObject)dataBus.getData();
        IAIMonNodeSV nodeSv = (IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
        String nodeCode = value.getNodeCode();
        String nodeName = (String)deploy.get("nodeNameNew");
        value.setNodeName(nodeName);
        boolean code = nodeSv.isExistNodeByCode(hostId, nodeCode);
        boolean name = nodeSv.isExistNodeByName(hostId, nodeName);
        boolean isExistMonitorNode = nodeSv.isExistMonitorNode(hostId);
        
        // 修改的时候需要用到的部署id和节点id
        long nodeIdLong = value.getNodeId();
        long deployStrategyId = Long.parseLong((String)deploy.get("deployStrategyId"));
        
        // 新增节点
        // C代表: "该节点编码已经存在，请重新填写！";
        // N代表："该节点名称已经存在，请重新填写！";
        // B代表"该节点编码和名称都已经存在，请重新填写！";
        // M代表：该主机下已有监控节点
        
        String C = "\u8be5\u8282\u70b9\u7f16\u7801\u5df2\u7ecf\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u586b\u5199\uff01";
        String N = "\u8be5\u8282\u70b9\u540d\u79f0\u5df2\u7ecf\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u586b\u5199\uff01";
        String B =
            "\u8be5\u8282\u70b9\u7f16\u7801\u548c\u540d\u79f0\u90fd\u5df2\u7ecf\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u586b\u5199\uff01";
        String M = "\u8be5\u4e3b\u673a\u4e0b\u5df2\u6709\u76d1\u63a7\u8282\u70b9";
        
        Map<String, String> retMap = new HashMap<String, String>();
        
        if (value.getNodeId() == 0)
        {
            if (isExistMonitorNode && "Y".equals(value.getIsMonitorNode()))
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", M);
            }
            else
            {
                if (code)
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", C);
                }
                if (name)
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", N);
                }
                if (name && code)
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", B);
                }
                if (!code && !name)
                {
                    value.setHostId(Long.parseLong(hostId));
                    OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                        CommonConstants.OPERATE_TYPE_ADD,
                        CommonConstants.OPERTATE_OBJECT_NODE,
                        value.getNodeId() + ":" + value.getNodeName(),
                        "添加节点，" + ":节点名称" + value.getNodeName());
                    // 保存节点信息之前要先进行校验
                    boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                    if (b)
                    {
                        long nodeIdAdd = nodeSv.saveOrUpdate(value);
                        this.saveOrUpdateDeploy(nodeIdAdd, deployStrategyId);
                        retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                    }
                    else
                    {
                        retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                    }
                }
            }
        }
        else
        {
            String nodeId = Long.toString(value.getNodeId());
            IBOAIMonNodeValue nodeValue = nodeSv.qryNodeInfoByNodeId(nodeId);
            // 如果监控节点已存在，则编辑的时候是否监控就不能填是
            if (!"Y".equals(nodeValue.getIsMonitorNode()) && isExistMonitorNode
                && ("Y".equals(value.getIsMonitorNode())))
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", M);
            }
            else
            {
                // 编码和名称都不变，直接进行编辑
                if (nodeValue.getNodeCode().equals(nodeCode) && nodeValue.getNodeName().equals(nodeName))
                {
                    // 节点编辑操作日志
                    OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                        CommonConstants.OPERATE_TYPE_MODIFY,
                        CommonConstants.OPERTATE_OBJECT_NODE,
                        value.getNodeId() + ":" + value.getNodeName(),
                        "编辑节点，节点标识" + value.getNodeId() + ":节点名称" + value.getNodeName());
                    // 保存节点信息之前要先进行校验
                    boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                    if (b)
                    {
                        nodeSv.saveOrUpdate(value);
                        this.saveOrUpdateDeploy(nodeIdLong, deployStrategyId);
                        retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                    }
                    else
                    {
                        retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                    }
                }
                // 编码不变，名称变了，判断名称是否已存在
                if (nodeValue.getNodeCode().equals(nodeCode) && !nodeValue.getNodeName().equals(nodeName))
                {
                    if (name)
                    {
                        retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", N);
                    }
                    else
                    {
                        // 节点编辑操作日志
                        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                            CommonConstants.OPERATE_TYPE_MODIFY,
                            CommonConstants.OPERTATE_OBJECT_NODE,
                            value.getNodeId() + ":" + value.getNodeName(),
                            "编辑节点，节点标识" + value.getNodeId() + ":节点名称" + value.getNodeName());
                        // 保存节点信息之前要先进行校验
                        boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                        if (b)
                        {
                            nodeSv.saveOrUpdate(value);
                            this.saveOrUpdateDeploy(nodeIdLong, deployStrategyId);
                            retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                        }
                        else
                        {
                            retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                            retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                        }
                    }
                }
                // 编码变了，名称不变，判断编码是否已存在
                if (!nodeValue.getNodeCode().equals(nodeCode) && nodeValue.getNodeName().equals(nodeName))
                {
                    if (code)
                    {
                        retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", C);
                    }
                    else
                    {
                        // 节点编辑操作日志
                        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                            CommonConstants.OPERATE_TYPE_MODIFY,
                            CommonConstants.OPERTATE_OBJECT_NODE,
                            value.getNodeId() + ":" + value.getNodeName(),
                            "编辑节点，节点标识" + value.getNodeId() + ":节点名称" + value.getNodeName());
                        // 保存节点信息之前要先进行校验
                        boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                        if (b)
                        {
                            nodeSv.saveOrUpdate(value);
                            this.saveOrUpdateDeploy(nodeIdLong, deployStrategyId);
                            retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                        }
                        else
                        {
                            retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                            retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                        }
                    }
                }
                // 编码和名称都变了，同时判断编码和名称是否存在
                if (!nodeValue.getNodeCode().equals(nodeCode) && !nodeValue.getNodeName().equals(nodeName))
                {
                    if (!name && !code)
                    {
                        // 节点编辑操作日志
                        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                            CommonConstants.OPERATE_TYPE_MODIFY,
                            CommonConstants.OPERTATE_OBJECT_NODE,
                            value.getNodeId() + ":" + value.getNodeName(),
                            "编辑节点，节点标识" + value.getNodeId() + ":节点名称" + value.getNodeName());
                        // 保存节点信息之前要先进行校验
                        boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                        if (b)
                        {
                            nodeSv.saveOrUpdate(value);
                            this.saveOrUpdateDeploy(nodeIdLong, deployStrategyId);
                            retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                        }
                        else
                        {
                            retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                            retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                        }
                    }
                    if (code)
                    {
                        retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", C);
                    }
                    if (name)
                    {
                        retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", N);
                    }
                    if (name && code)
                    {
                        retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", B);
                    }
                }
            }
            
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("flag"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(jsonObj);
    }
    
    /**
     * 新增或修改节点对应的部署策略
     * 
     * @param request
     * @throws Exception
     */
    public void saveOrUpdateDeploy(long nodeId, long deployStrategyId)
        throws Exception
    {
        // 判断该节点是否存在节点策略
        BODeployNodeStrategyRelationBean relBean = DeployStrategyUtils.getNodeStrategyRelByNodeId(nodeId);
        boolean flag = true;
        if (relBean.getNodeId() <= 0)
        {
            flag = false;
        }
        if (flag)
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_NODE,
                nodeId + ":" + deployStrategyId,
                "节点部署策略修改，节点标识" + nodeId + ":部署策略标识" + deployStrategyId);
            DeployStrategyUtils.modifyNodeDeployStrategy(nodeId, deployStrategyId);
        }
        else
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_ADD,
                CommonConstants.OPERTATE_OBJECT_NODE,
                nodeId + ":" + deployStrategyId,
                "节点部署策略修改，节点标识" + nodeId + ":部署策略标识" + deployStrategyId);
            DeployStrategyUtils.addNodeDeployStrategy(nodeId, deployStrategyId);
        }
    }
    
    public abstract void setNodeDetail(JSONObject nodeDetail);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("nodeDetail", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonNode");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus nodeDetail = createData("nodeDetail");
        if (nodeDetail != null && !nodeDetail.getDataObject().isEmpty())
        {
            setNodeDetail(nodeDetail.getDataObject());
        }
        initExtend(cycle);
    }
    
}
