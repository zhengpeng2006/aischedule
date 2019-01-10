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
import com.asiainfo.monitor.busi.service.interfaces.IAIMonParamValuesSV;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonParamValuesValue;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class ParamValueCfg extends AppPage
{
    private static Logger LOGGER = Logger.getLogger(ParamValueCfg.class);
    
    /** 默认参数定义配置类型：1.进程 */
    private static final String REGIST_TYPE = "1";
    
    /** 保存参数定义的map */
    private Map<String, String> map = null;
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public void initParamValueTable()
        throws Exception
    {
        
    }
    
    public void auto_deleteParamValues_deleteOnclick(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        long id = Long.parseLong(getContext().getParameter("vId"));
        IAIMonParamValuesSV sv =
            (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
        JSONObject result = new JSONObject();
        try
        {
            sv.deleteParamValues(id);
        }
        catch (Exception e)
        {
            retMap.put("flag", ResultConstants.ResultCodeValue.ERROR);
            
            LOGGER.error("save time failed,reason is " + e.getMessage());
        }
        finally
        {
            IBOAIMonParamValuesValue value = sv.getParamValuesById(id + "");
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_DELETE,
                CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM,
                value.getParamCode() + ":" + id,
                "删除告警任务参数");
        }
        result.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("flag"));
        result.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(result);
    }
    
    public void auto_getParamValuesById_updateOnclick(IRequestCycle cycle)
        throws Exception
    {
        String id = getContext().getParameter("vId");
        IAIMonParamValuesSV sv =
            (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
        IBOAIMonParamValuesValue result = sv.getParamValuesById(id);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyDataBus();
        else
            bus =
                DataBusHelper.getDataBusByBeans(result,
                    IBOAIMonParamValuesValue.class);
        
        /* setParamInput(bus.getDataObject()); */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("paramInput", bus.getDataObject());
        this.setAjax(jsonObject);
        
    }
    
    public void auto_saveOrUpdate_paramSaveOnclick(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        IDataBus paramInputBus = createData("paramInput");
        JSONObject paramInputCondition = (JSONObject)paramInputBus.getData();
        IBOAIMonParamValuesValue value =
            (IBOAIMonParamValuesValue)DataBusHelper.getBOBean(paramInputBus);
        IAIMonParamValuesSV sv =
            (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
        // 若这两项没有，则是新增，添加任务关联信息
        if (value.getRegisteType() == 0 || value.getRegisteId() == 0)
        {
            String infoId = getContext().getParameter("infoId");
            String registeType = getContext().getParameter("registeType");
            value.setRegisteId(Long.parseLong(infoId));
            value.setRegisteType(Integer.parseInt(registeType));
        }
        JSONObject result = new JSONObject();
        try
        {
            // 保存参数前进行校验
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
            
            if (e.getMessage().contains("ORA-00001"))
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", "\u8be5\u5b9a\u4e49\u4e0b\u53c2\u6570\u5df2\u7ecf\u5b8c\u6210\u914d\u7f6e");
            }
            else
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.ERROR);
            }
            LOGGER.error("save time failed,reason is " + e.getMessage());
        }
        
        result.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("flag"));
        result.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(result);
    }
    
    public void auto_getParamValuesByType_queryButtonOnclick(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String infoId = getContext().getParameter("infoId");
        String registeType = getContext().getParameter("registeType");
        IAIMonParamValuesSV sv = (IAIMonParamValuesSV)ServiceFactory.getService(IAIMonParamValuesSV.class);
        IDataBus bus = null;
        IBOAIMonParamValuesValue[] result = sv.getParamValuesByType(registeType, infoId);
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonParamValuesValue.class);
        JSONArray dataArray = bus.getDataArray();
        /*
         * setParamValuesItems(dataArray); setParamValuesCount(dataArray.size());
         */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("paramValuesItems", PagingUtil.convertArray2Page(dataArray, offset, linage));
        jsonObject.put("total", dataArray.size());
        this.setAjax(jsonObject);
        
    }
    
    public void changeOrg(IRequestCycle cycle)
        throws Exception
    {
        String infoId = getContext().getData().getString("infoId");
        JSONObject org = new JSONObject();
        org.put("infoId", infoId);
    }
    
    public abstract void setParamValuesCount(long paramValuesCount);
    
    public abstract void setParamValuesRowIndex(int paramValuesRowIndex);
    
    public abstract void setParamValuesItems(JSONArray paramValuesItems);
    
    public abstract void setParamValuesItem(JSONObject paramValuesItem);
    
    public abstract void setParamInput(JSONObject paramInput);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("paramValues", "com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamValues");
        
        bindBoName("paramInput", "com.asiainfo.monitor.exeframe.config.bo.BOAIMonParamValues");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus paramValuesItem = createData("paramValuesItem");
        if (paramValuesItem != null && !paramValuesItem.getDataObject().isEmpty())
        {
            setParamValuesItem(paramValuesItem.getDataObject());
        }
        IDataBus paramInput = createData("paramInput");
        if (paramInput != null && !paramInput.getDataObject().isEmpty())
        {
            setParamInput(paramInput.getDataObject());
        }
        initExtend(cycle);
    }
    
}
