package com.asiainfo.schedule.view;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.resultConstant.ResultConstants;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class TaskParamCfg extends AppPage
{
    
    private static final Logger LOGGER = Logger.getLogger(TaskParamCfg.class);
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 根据taskCode查询task相关参数
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryParam(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String taskCode = getContext().getParameter("taskCode").trim();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONArray array = new JSONArray();
        // 调用查询方法后循环创建页面对象 未完成
        Map<String, String> map = sv.getTaskParam(taskCode);
        Map<String, String> descMap = sv.getTaskParamDesc(taskCode);
        
        if (!map.isEmpty())
        {
            for (Map.Entry<String, String> entry : map.entrySet())
            {
                JSONObject obj = new JSONObject();
                obj.put("taskCode", taskCode);
                obj.put("paramKey", entry.getKey());
                obj.put("paramValue", entry.getValue());
                if (null != descMap)
                {
                    obj.put("paramDesc", descMap.get(entry.getKey()));
                }
                array.add(obj);
            }
        }
        // this.setTaskParamsItems(array);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskParamsItems", PagingUtil.convertArray2Page(array, offset, linage));
        jsonObject.put("total", array.size());
        this.setAjax(jsonObject);
    }
    
    /**
     * 删除参数
     * 
     * @param cycle
     * @throws Exception
     */
    public void deleteParam(IRequestCycle cycle)
        throws Exception
    {
        String taskCode = getContext().getParameter("taskCode").trim();
        String paramKey = getContext().getParameter("paramKey").trim();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject result = new JSONObject();
        try
        {
            
            sv.deleteTaskParam(taskCode, paramKey);
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
        }
        catch (Exception e)
        {
            LOGGER.error("deleteParam failed,reason is " + e);
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
        }
        finally
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_TASK,
                taskCode,
                "删除任务参数");
        }
        this.setAjax(result);
    }
    
    /**
     * 保存参数
     * 
     * @param cycle
     * @throws Exception
     */
    public void saveParam(IRequestCycle cycle)
        throws Exception
    {
        String taskCode = getContext().getParameter("taskCode").trim();
        IDataBus taskInputBus = createData("paramInput");
        JSONObject data = taskInputBus.getDataObject();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject result = new JSONObject();
        // 构建入参，调用保存接口后，返回结果 DELFLAG= F/T 未完成
        try
        {
            String paramStr =
                taskCode + data.getString("paramKey") + data.getString("paramValue") + data.getString("paramDesc");
            // 保存参数信息前进行校验
            boolean b = XssUtil.checkString(paramStr);
            if (b)
            {
                sv.createTaskParam(taskCode, data.getString("paramKey"), data.getString("paramValue"));
                Map<String, String> map = new HashMap<String, String>();
                String paramDesc = data.getString("paramDesc");
                map.put(data.getString("paramKey"), paramDesc);
                sv.createTaskParamDesc(taskCode, map);
                result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
            }
            else
            {
                result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                result.put(ResultConstants.ResultKey.RESULT_MSG, ResultConstants.ResultCodeValue.CHECK_FAILED);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("saveParam failed,reason is " + e);
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
        }
        finally
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_TASK,
                taskCode,
                "配置任务参数");
        }
        this.setAjax(result);
    }
    
    public abstract void setTaskParamsCount(long taskParamsCount);
    
    public abstract void setTaskParamsRowIndex(int taskParamsRowIndex);
    
    public abstract void setTaskParamsItems(JSONArray taskParamsItems);
    
    public abstract void setTaskParamsItem(JSONObject taskParamsItem);
    
    public abstract void setParamInput(JSONObject paramInput);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus taskParamsItem = createData("taskParamsItem");
        if (taskParamsItem != null && !taskParamsItem.getDataObject().isEmpty())
        {
            setTaskParamsItem(taskParamsItem.getDataObject());
        }
        IDataBus paramInput = createData("paramInput");
        if (paramInput != null && !paramInput.getDataObject().isEmpty())
        {
            setParamInput(paramInput.getDataObject());
        }
        initExtend(cycle);
    }
    
}
