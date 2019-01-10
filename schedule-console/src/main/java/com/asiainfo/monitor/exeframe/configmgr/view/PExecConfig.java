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
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPExecValue;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class PExecConfig extends AppPage
{
    private static Logger LOGGER = Logger.getLogger(PExecConfig.class);
    
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
        typeMap.put("SHELL", "shell脚本");
        typeMap.put("JAVACOMMAND", "命令");
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
    
    public void auto_getExecByNameAndExpr_queryOnclick(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        IDataBus queryFormBus = createData("queryForm");
        JSONObject queryFormCondition = (JSONObject)queryFormBus.getData();
        String execName = (String)queryFormCondition.get("name");
        String expr = (String)queryFormCondition.get("expr");
        int startNum = Integer.parseInt(queryFormBus.getContext().getPaginStart() + "");
        int endNum = Integer.parseInt(queryFormBus.getContext().getPaginEnd() + "");
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV.class);
        IBOAIMonPExecValue[] result =
            sv.getExecByNameAndExpr(execName, expr, -1, -1);
        // 转换过长的表达式
        /*
         * for (IBOAIMonPExecValue value : result) { String oExpr = value.getExpr(); if (oExpr != null && oExpr.length()
         * > 50) { value.setExpr(oExpr.substring(0, 50) + "..."); } }
         */
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus =
                DataBusHelper.getDataBusByBeans(result,
                    IBOAIMonPExecValue.class);
        
        JSONArray dataArray = bus.getDataArray();
        /*
         * setExecInfosItems(dataArray); setExecInfosCount(dataArray.size());
         */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("execInfosItems", PagingUtil.convertArray2Page(dataArray, offset, linage));
        jsonObject.put("total", dataArray.size());
        this.setAjax(jsonObject);
    }
    
    public void auto_delete_deleteOnclick(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        long execId = Long.parseLong(getContext().getParameter("execId"));
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV.class);
        JSONObject jsonObj = new JSONObject();
        try
        {
            sv.delete(execId);
        }
        catch (Exception e)
        {
            retMap.put("DEL_FLAG", ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("delete execption=" + e.getMessage());
            
        }
        finally
        {
            IBOAIMonPExecValue value = sv.getBeanById(execId);
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_DELETE,
                CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM,
                value.getName(),
                "删除告警监控脚本");
        }
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("DEL_FLAG"));
        this.setAjax(jsonObj);
        
    }
    
    public void auto_getBeanById_updateOnclick(IRequestCycle cycle)
        throws Exception
    {
        long execId = Long.parseLong(getContext().getParameter("execId"));
        // String detail = getContext().getParameter("detail");// 判断是否是显示详情调用
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV.class);
        IBOAIMonPExecValue result = sv.getBeanById(execId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyDataBus();
        else
            bus =
                DataBusHelper.getDataBusByBeans(result,
                    com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPTimeValue.class);
        
        /*
         * if (detail != null)// 详情调用则显示详情 { setErprDetail(bus.getDataObject());
         * 
         * } else { setDataInput(bus.getDataObject());
         * 
         * }
         */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("erprDetail", bus.getDataObject());
        this.setAjax(jsonObject);
    }
    
    public void auto_saveOrUpdate_saveOnclick(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        
        IDataBus dataInputBus = createData("dataInput");
        JSONObject dataInputCondition = (JSONObject)dataInputBus.getData();
        IBOAIMonPExecValue value =
            (IBOAIMonPExecValue)DataBusHelper.getBOBean(dataInputBus);
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPExecSV.class);
        JSONObject result = new JSONObject();
        try
        {
            // 保存监控配置前进行校验
            boolean b = XssUtil.checkBeforeSave(value, value.getClass(), "Expr");
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
    
    public void auto_saveOrUpdate_dataSaveOnclick(IRequestCycle cycle)
        throws Exception
    {
        com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue value = null;
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoSV.class);
        long infoId = Long.parseLong(getContext().getParameter("infoId"));
        long typeId = Long.parseLong(getContext().getParameter("typeId"));
        value = sv.getMonPInfoValue(infoId);
        if (null != value)
        {
            value.setTypeId(typeId);
        }
        sv.saveOrUpdate(value);
    }
    
    public abstract void setDataInput(JSONObject dataInput);
    
    public abstract void setExecInfosItem(JSONObject execInfosItem);
    
    public abstract void setErprDetail(JSONObject erprDetail);
    
    public abstract void setExecInfosRowIndex(int execInfosRowIndex);
    
    public abstract void setExecInfosItems(JSONArray execInfosItems);
    
    public abstract void setExecInfosCount(long execInfosCount);
    
    public abstract void setQueryForm(JSONObject queryForm);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("execInfos", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPExec");
        
        bindBoName("dataInput", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPExec");
        
        bindBoName("erprDetail", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPExec");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus dataInput = createData("dataInput");
        if (dataInput != null && !dataInput.getDataObject().isEmpty())
        {
            setDataInput(dataInput.getDataObject());
        }
        IDataBus execInfosItem = createData("execInfosItem");
        if (execInfosItem != null && !execInfosItem.getDataObject().isEmpty())
        {
            setExecInfosItem(execInfosItem.getDataObject());
        }
        IDataBus erprDetail = createData("erprDetail");
        if (erprDetail != null && !erprDetail.getDataObject().isEmpty())
        {
            setErprDetail(erprDetail.getDataObject());
        }
        IDataBus queryForm = createData("queryForm");
        if (queryForm != null && !queryForm.getDataObject().isEmpty())
        {
            setQueryForm(queryForm.getDataObject());
        }
        initExtend(cycle);
    }
    
}
