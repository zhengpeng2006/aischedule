package com.asiainfo.monitor.exeframe.configmgr.view;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IData;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.impl.TreeBean;
import com.ailk.common.data.impl.TreeHelper;
import com.ailk.common.data.impl.TreeItem;
import com.ailk.web.view.component.tree.TreeFactory;
import com.ailk.web.view.component.tree.TreeParam;
import com.asiainfo.deploy.api.external.AppParamUtils;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2015 Asiainfo-Linkage
 */
public abstract class NodeTreeInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
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
     * 将应用copy到指定节点上
     * 
     * @param request
     * @throws Exception
     */
    public void copyServer(IRequestCycle request)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        IDataBus dataBus = this.createData("serverDetail");
        JSONObject deploy = (JSONObject)dataBus.getData();
        String serverCode = (String)deploy.get("serverCode");
        // 当serverCode为空的时候，提示serverCode为空，不能复制
        if (StringUtils.isBlank(serverCode))
        {
            retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
            retMap.put("msg",
                "\u0073\u0065\u0072\u0076\u0065\u0072\u0043\u006f\u0064\u0065\u4e3a\u7a7a\uff0c\u4e0d\u80fd\u590d\u5236");
        }
        else
        {
            // 节点标识
            String nodeId = getContext().getParameter("treeNodeId").split("_")[0];
            // 应用标识
            String serverId = getContext().getParameter("serverId");
            
            // 根据应用标识查询该应用的信息
            IAIMonServerSV serverSV = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
            IBOAIMonServerValue serverValue = serverSV.qryServerByServerId(serverId);
            
            // 根据节点标识查出该节点信息
            IAIMonNodeSV nodeSV = (IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
            IBOAIMonNodeValue nodeValue = nodeSV.qryNodeInfoByNodeId(nodeId);
            
            if (null != serverValue && null != nodeValue)
            {
                long serverIdOld = serverValue.getServerId();
                serverValue.setStsToNew();
                serverValue.setServerId(0);
                serverValue.setNodeId(Long.parseLong(nodeId));
                
                boolean isExist = serverSV.isExistServerByCode(serverCode);
                if (!isExist)
                {
                    serverValue.setServerCode(serverCode);
                    long serverIdNew = serverSV.saveOrUpdate(serverValue);
                    // 前面是老的，后面是新的
                    AppParamUtils.copyAppParams(serverIdOld, serverIdNew);
                    // 复制成功
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
                }
                else
                {
                    // 该应用编码已经存在，请修改应用编码
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg",
                        "\u8be5\u5e94\u7528\u7f16\u7801\u5df2\u7ecf\u5b58\u5728\uff0c\u8bf7\u4fee\u6539\u5e94\u7528\u7f16\u7801");
                }
            }
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("SAVE_FLAG"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(jsonObj);
    }
    
    public abstract void setServerDetail(JSONObject serverDetail);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("serverDetail", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonServer");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus serverDetail = createData("serverDetail");
        if (serverDetail != null && !serverDetail.getDataObject().isEmpty())
        {
            setServerDetail(serverDetail.getDataObject());
        }
        initExtend(cycle);
    }
    
}
