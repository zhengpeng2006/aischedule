package com.asiainfo.monitor.exeframe.configmgr.view;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTimeValue;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class PTimeConfig extends AppPage
{
    
    private static Logger LOGGER = Logger.getLogger(PTimeConfig.class);
    
    private Map<String, String> typeMap;
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    private void initMap()
    {
        typeMap = new HashMap<String, String>();
        typeMap.put("CRON", "周期执行");
        typeMap.put("I", "立即执行");
        typeMap.put("F", "固定时间");
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
    public String doTranslate(String type)
        throws RemoteException, Exception
    {
        String text = null;
        if (!StringUtils.isBlank(type))
        {
            if (null == typeMap)
            {
                initMap();
            }
            text = typeMap.get(type);
        }
        return text;
    }
    
    public IDataBus getSelectList()
        throws Exception
    {
        if (null == typeMap)
        {
            initMap();
        }
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, String> entry : typeMap.entrySet())
        {
            JSONObject obj = new JSONObject();
            String key = entry.getValue();
            String val = entry.getKey();
            obj.put("TEXT", key);
            obj.put("VALUE", val);
            jsonArray.add(obj);
        }
        return new DataBus(context, jsonArray);
    }
    
    public void auto_getTimeInfoByExpr_queryOnclick(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        IDataBus queryFormBus = createData("queryForm");
        JSONObject queryFormCondition = (JSONObject)queryFormBus.getData();
        String expr = (String)queryFormCondition.get("expr");
        int startNum = Integer.parseInt(queryFormBus.getContext().getPaginStart() + "");
        int endNum = Integer.parseInt(queryFormBus.getContext().getPaginEnd() + "");
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV.class);
        IBOAIMonPTimeValue[] result = sv.getTimeInfoByExpr(expr, -1, -1);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus =
                DataBusHelper.getDataBusByBeans(result,
                    IBOAIMonPTimeValue.class);
        
        JSONArray dataArray = bus.getDataArray();
        /*
         * setTimeInfosItems(dataArray); setTimeInfosCount(dataArray.size());
         */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timeInfosItems", PagingUtil.convertArray2Page(dataArray, offset, linage));
        jsonObject.put("total", dataArray.size());
        this.setAjax(jsonObject);
    }
    
    public void auto_delete_deleteOnclick(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        long timeId = Long.parseLong(getContext().getParameter("timeId"));
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV.class);
        JSONObject jsonObj = new JSONObject();
        try
        {
            sv.delete(timeId);
        }
        catch (Exception e)
        {
            retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("delete exception=" + e.getMessage());
        }
        finally
        {
            IBOAIMonPTimeValue value = sv.getBeanById(timeId);
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_DELETE,
                CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM,
                (StringUtils.isNotBlank(value.getRemarks()) ? value.getRemarks() : value.getExpr()) + ":"
                    + value.getTimeId(),
                "删除告警时间");
        }
        
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("DEL_FLAG"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(jsonObj);
    }
    
    public void auto_getBeanById_updateOnclick(IRequestCycle cycle)
        throws Exception
    {
        long timeId = Long.parseLong(getContext().getParameter("timeId"));
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV.class);
        IBOAIMonPTimeValue result = sv.getBeanById(timeId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyDataBus();
        else
            bus =
                DataBusHelper.getDataBusByBeans(result,
                    IBOAIMonPTimeValue.class);
        
        /* setTimeInput(bus.getDataObject()); */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timeInput", bus.getDataObject());
        this.setAjax(jsonObject);
    }
    
    public void auto_saveOrUpdate_saveOnclick(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        IDataBus timeInputBus = createData("timeInput");
        JSONObject timeInputCondition = (JSONObject)timeInputBus.getData();
        IBOAIMonPTimeValue value =
            (IBOAIMonPTimeValue)DataBusHelper.getBOBean(timeInputBus);
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPTimeSV.class);
        JSONObject result = new JSONObject();
        try
        {
            boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
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
            LOGGER.error("save time failed,reason is " + e.getMessage());
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
        long timeId = Long.parseLong(getContext().getParameter("timeId"));
        value = sv.getMonPInfoValue(infoId);
        if (null != value)
        {
            value.setTimeId(timeId);
        }
        sv.saveOrUpdate(value);
    }
    
    public abstract void setTimeInput(JSONObject timeInput);
    
    public abstract void setTimeInfosItem(JSONObject timeInfosItem);
    
    public abstract void setTimeInfosCount(long timeInfosCount);
    
    public abstract void setTimeInfosRowIndex(int timeInfosRowIndex);
    
    public abstract void setTimeInfosItems(JSONArray timeInfosItems);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("timeInfos", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPTime");
        
        bindBoName("timeInput", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPTime");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus timeInput = createData("timeInput");
        if (timeInput != null && !timeInput.getDataObject().isEmpty())
        {
            setTimeInput(timeInput.getDataObject());
        }
        IDataBus timeInfosItem = createData("timeInfosItem");
        if (timeInfosItem != null && !timeInfosItem.getDataObject().isEmpty())
        {
            setTimeInfosItem(timeInfosItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
