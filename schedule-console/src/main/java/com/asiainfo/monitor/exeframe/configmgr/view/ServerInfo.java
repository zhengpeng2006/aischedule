package com.asiainfo.monitor.exeframe.configmgr.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class ServerInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public IDataBus getSelectList(String constCode)
        throws Exception
    {
        IAIMonServerSV serverSv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
        return serverSv.getSelectList(constCode);
    }
    
    public void qryServerInfo(IRequestCycle request)
        throws Exception
    {
        String serverId = getContext().getParameter("serverId");
        IAIMonServerSV serverSv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
        IBOAIMonServerValue serverBean = serverSv.getServerBeanById(serverId);
        
        IDataBus dataBus = null;
        if (serverBean == null)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(serverBean, IBOAIMonServerValue.class);
        }
        
        this.setServerDetail(dataBus.getDataObject());
        
    }
    
    public void qryServerInfosByHostId(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String hostIdStr = getContext().getParameter("hostId");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        IAIMonServerSV serverSv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
        if (StringUtils.isBlank(hostIdStr))
        {
            jsonObject.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
            jsonObject.put(ResultConstants.ResultKey.RESULT_MSG, "hostid is empty!");
            this.setAjax(jsonObject);
            return;
        }
        
        List<IBOAIMonServerValue> serverBeans = serverSv.qryServerByHostId(hostIdStr);
        
        if (serverBeans != null)
        {
            for (IBOAIMonServerValue serverBean : serverBeans)
            {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("serverCode", serverBean.getServerCode() + "");
                jsonObject1.put("serverName", serverBean.getServerName());
                jsonArray.add(jsonObject1);
            }
        }
        jsonObject.put("serverInfos", PagingUtil.convertArray2Page(jsonArray, offset, linage));
        jsonObject.put("total", jsonArray.size());
        this.setAjax(jsonObject);
        
    }
    
    /**
     * 新增分组
     * 
     * @param request
     * @throws Exception
     */
    public void saveOrUpdate(IRequestCycle request)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        String treeNodeId = getContext().getParameter("treeNodeId");
        String[] arr = treeNodeId.split("_");
        String nodeId = arr[0];
        IDataBus dataBus = this.createData("serverDetail");
        IBOAIMonServerValue value = (IBOAIMonServerValue)DataBusHelper.getBOBean(dataBus);
        
        String serverCode = value.getServerCode();
        IAIMonServerSV serverSv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
        boolean code = serverSv.isExistServerByCode(serverCode);
        
        if (value.getNodeId() == 0)
        {
            
            if (code)
            {
                retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", "\u5e94\u7528\u7f16\u7801\u5df2\u5b58\u5728");
            }
            else
            {
                value.setNodeId(Long.parseLong(nodeId));
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                    CommonConstants.OPERATE_TYPE_MODIFY,
                    CommonConstants.OPERTATE_OBJECT_APP,
                    value.getServerId() + ":" + value.getServerName(),
                    "新增应用，" + ":应用名称" + value.getServerName());
                // 保存信息之前要先进行校验
                boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                if (b)
                {
                    serverSv.saveOrUpdate(value);
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
                }
                else
                {
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                }
            }
        }
        else
        {
            String serverId = Long.toString(value.getServerId());
            IBOAIMonServerValue serverValue = serverSv.qryServerByServerId(serverId);
            if (serverValue.getServerCode().equals(serverCode))
            {
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                    CommonConstants.OPERATE_TYPE_MODIFY,
                    CommonConstants.OPERTATE_OBJECT_APP,
                    value.getServerId() + ":" + value.getServerName(),
                    "编辑应用，应用标识" + value.getServerId() + ":应用名称" + value.getServerName());
                // 保存信息之前要先进行校验
                boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                if (b)
                {
                    serverSv.saveOrUpdate(value);
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
                }
                else
                {
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                }
            }
            if (!serverValue.getServerCode().equals(serverCode))
            {
                if (code)
                {
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", "\u5e94\u7528\u7f16\u7801\u5df2\u5b58\u5728");
                }
                else
                {
                    OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                        CommonConstants.OPERATE_TYPE_MODIFY,
                        CommonConstants.OPERTATE_OBJECT_APP,
                        value.getServerId() + ":" + value.getServerName(),
                        "编辑应用，应用标识" + value.getServerId() + ":应用名称" + value.getServerName());
                    // 保存信息之前要先进行校验
                    boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                    if (b)
                    {
                        serverSv.saveOrUpdate(value);
                        retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
                    }
                    else
                    {
                        retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                    }
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
