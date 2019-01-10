package com.asiainfo.monitor.exeframe.configmgr.view;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonThresholdValue;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class PThresholdConfig extends AppPage
{
    
    private static Logger LOGGER = Logger.getLogger(PThresholdConfig.class);
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public void auto_getThresholdByExprAndName_queryOnclick(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        IDataBus queryFormBus = createData("queryForm");
        JSONObject queryFormCondition = (JSONObject)queryFormBus.getData();
        String expr = (String)queryFormCondition.get("expr");
        String name = (String)queryFormCondition.get("name");
        int startNum = Integer.parseInt(queryFormBus.getContext().getPaginStart() + "");
        int endNum = Integer.parseInt(queryFormBus.getContext().getPaginEnd() + "");
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV.class);
        IBOAIMonThresholdValue[] result =
            sv.getThresholdByExprAndName(expr, name, -1, -1);
        
        // 表达式长度大于50时转换内容为前50字加省略号
        /*
         * for (IBOAIMonThresholdValue value : result) { String OExpr = value.getExpr1(); if (OExpr != null &&
         * OExpr.length() > 50) { value.setExpr1(OExpr.substring(0, 50) + "..."); } }
         */
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus =
                DataBusHelper.getDataBusByBeans(result,
                    IBOAIMonThresholdValue.class);
        JSONArray dataArray = bus.getDataArray();
        /*
         * setThresholdInfosItems(dataArray); setThresholdInfosCount(dataArray.size());
         */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("thresholdInfosItems", PagingUtil.convertArray2Page(dataArray, offset, linage));
        jsonObject.put("total", dataArray.size());
        this.setAjax(jsonObject);
    }
    
    public void auto_delete_deleteOnclick(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        
        long thresholdId = Long.parseLong(getContext().getParameter("thresholdId"));
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV.class);
        JSONObject jsonObj = new JSONObject();
        try
        {
            sv.delete(thresholdId);
        }
        catch (Exception e)
        {
            retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("delete exception=" + e.getMessage());
        }
        finally
        {
            IBOAIMonThresholdValue value = sv.getBeanById(thresholdId);
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_DELETE,
                CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM,
                value.getThresholdName() + ":" + value.getThresholdId(),
                "删除告警阀值");
        }
        
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("DEL_FLAG"));
        this.setAjax(jsonObj);
    }
    
    public void auto_getBeanById_updateOnclick(IRequestCycle cycle)
        throws Exception
    {
        long thresholdId = Long.parseLong(getContext().getParameter("thresholdId"));
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV.class);
        IBOAIMonThresholdValue result = sv.getBeanById(thresholdId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyDataBus();
        else
            bus =
                DataBusHelper.getDataBusByBeans(result,
                    IBOAIMonThresholdValue.class);
        
        setThresholdInput(bus.getDataObject());
    }
    
    public void auto_saveOrUpdate_saveOnclick(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        IDataBus thresholdInputBus = createData("thresholdInput");
        JSONObject thresholdInputCondition = (JSONObject)thresholdInputBus.getData();
        IBOAIMonThresholdValue value =
            (IBOAIMonThresholdValue)DataBusHelper.getBOBean(thresholdInputBus);
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV.class);
        JSONObject result = new JSONObject();
        try
        {
            // 保存监控阀值前进行校验
            boolean b = XssUtil.checkBeforeSave(value, value.getClass(), "Expr1");
            if (b)
            {
                sv.saveOrUpdate(value);
            }
            else
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
            }
        }
        catch (Exception e)
        {
            retMap.put("flag", ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("save threshold failed,reason is " + e.getMessage());
        }
        result.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("flag"));
        result.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(result);
    }
    
    public void auto_saveOrUpdate_dataSelectOnclick(IRequestCycle cycle)
        throws Exception
    {
        com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue value = null;
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV.class);
        long infoId = Long.parseLong(getContext().getParameter("infoId"));
        long thresholdId = Long.parseLong(getContext().getParameter("thresholdId"));
        value = sv.getMonPInfoValue(infoId);
        if (null != value)
        {
            value.setThresholdId(thresholdId);
        }
        sv.saveOrUpdate(value);
    }
    
    public void auto_getBeanById_detailOnclick(IRequestCycle cycle)
        throws Exception
    {
        long thresholdId = Long.parseLong(getContext().getParameter("thresholdId"));
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPThresholdSV.class);
        IBOAIMonThresholdValue result = sv.getBeanById(thresholdId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyDataBus();
        else
            bus =
                DataBusHelper.getDataBusByBeans(result,
                    IBOAIMonThresholdValue.class);
        
        /* setExprDetail(bus.getDataObject()); */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("exprDetail", bus.getDataObject());
        this.setAjax(jsonObject);
        
    }
    
    public abstract void setThresholdInfosItems(JSONArray thresholdInfosItems);
    
    public abstract void setThresholdInfosRowIndex(int thresholdInfosRowIndex);
    
    public abstract void setExprDetail(JSONObject exprDetail);
    
    public abstract void setThresholdInput(JSONObject thresholdInput);
    
    public abstract void setThresholdInfosCount(long thresholdInfosCount);
    
    public abstract void setThresholdInfosItem(JSONObject thresholdInfosItem);
    
    public abstract void setQueryForm(JSONObject queryForm);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("thresholdInfos", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonThreshold");
        
        bindBoName("thresholdInput", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonThreshold");
        
        bindBoName("exprDetail", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonThreshold");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus exprDetail = createData("exprDetail");
        if (exprDetail != null && !exprDetail.getDataObject().isEmpty())
        {
            setExprDetail(exprDetail.getDataObject());
        }
        IDataBus thresholdInput = createData("thresholdInput");
        if (thresholdInput != null && !thresholdInput.getDataObject().isEmpty())
        {
            setThresholdInput(thresholdInput.getDataObject());
        }
        IDataBus thresholdInfosItem = createData("thresholdInfosItem");
        if (thresholdInfosItem != null && !thresholdInfosItem.getDataObject().isEmpty())
        {
            setThresholdInfosItem(thresholdInfosItem.getDataObject());
        }
        IDataBus queryForm = createData("queryForm");
        if (queryForm != null && !queryForm.getDataObject().isEmpty())
        {
            setQueryForm(queryForm.getDataObject());
        }
        initExtend(cycle);
    }
    
}
