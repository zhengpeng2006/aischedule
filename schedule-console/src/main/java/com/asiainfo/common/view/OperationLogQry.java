package com.asiainfo.common.view;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import com.asiainfo.common.abo.ivalues.IBOSchedulerOperationsValue;
import com.asiainfo.common.service.interfaces.ISchedulerOperationsSV;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.exeframe.configmgr.util.ConfigPageUtil;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class OperationLogQry extends AppPage
{
    
    private static final Logger LOGGER = Logger.getLogger(OperationLogQry.class);
    
    /** 静态数据 */
    private static Map<String, Map<String, String>> staticMap = null;
    
    /** 操作类型 */
    private static final String OPER_TYPE = "operType";
    
    /** 操作对象类型 */
    private static final String OPER_OBJ = "operObj";
    
    /** 模块 */
    private static final String OPER_MODULE = "operModule";
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 查询方法
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryLog(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        JSONObject result = new JSONObject();
        IDataBus queryFormBus = createData("queryForm");
        JSONObject queryFormCondition = (JSONObject)queryFormBus.getData();
        String startTime = queryFormCondition.getString("startTime");
        String endTime = queryFormCondition.getString("endTime");
        ISchedulerOperationsSV sv = (ISchedulerOperationsSV)ServiceFactory.getService(ISchedulerOperationsSV.class);
        int start = Integer.parseInt(offset) + 1;
        int end = Integer.parseInt(offset) + Integer.parseInt(linage);
        try
        {
            Map<String, Object> condition = getParams(queryFormCondition, startTime, endTime);
            IBOSchedulerOperationsValue[] values = sv.getBeanByCondition(condition, start, end);
            int total = sv.getBeansCount(condition);
            IDataBus bus = null;
            if (values == null)
                bus = DataBusHelper.getEmptyArrayDataBus();
            else
                bus = DataBusHelper.getDataBusByBeans(values, IBOSchedulerOperationsValue.class);
            JSONArray array = bus.getDataArray();
            for (Object object : array)
            {
                JSONObject obj = (JSONObject)object;
                if (StringUtils.isNotBlank(obj.getString("operationObjectContent"))
                    && obj.getString("operationObjectContent").length() >= 50)
                {
                    obj.put("operationObjectContentShort", obj.getString("operationObjectContent").substring(0, 50)
                        + "...");
                }
                else
                {
                    obj.put("operationObjectContentShort", obj.getString("operationObjectContent"));
                }
            }
            /*
             * this.setOperateLogsItems(array); this.setOperateLogsCount(total);
             */
            result.put("operateLogsItems", array);
            result.put("total", total);
        }
        catch (Exception e)
        {
            result.put("flag", "F");
            result.put("msg", e.getMessage());
            LOGGER.error("qryLog failed", e);
        }
        this.setAjax(result);
    }
    
    /**
     * 获取模块下拉菜单
     * 
     * @return
     */
    public IDataBus getModuleList()
    {
        IDataBus bus = ConfigPageUtil.getSelectList(getStaticMap().get(OPER_MODULE));
        return bus;
    }
    
    /**
     * 获取操作类型下拉菜单
     * 
     * @return
     */
    public IDataBus getTypeList()
    {
        IDataBus bus = ConfigPageUtil.getSelectList(getStaticMap().get(OPER_TYPE));
        return bus;
    }
    
    /**
     * 获取操作对象下拉菜单
     * 
     * @return
     */
    public IDataBus getObjList()
    {
        IDataBus bus = ConfigPageUtil.getSelectList(getStaticMap().get(OPER_OBJ));
        return bus;
    }
    
    /**
     * 拼查询条件
     * 
     * @param formData
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    private Map<String, Object> getParams(JSONObject formData, String startTime, String endTime)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<String, Object>();
        Timestamp start = null;
        Timestamp end = null;
        if (StringUtils.isNotBlank(startTime))
        {
            start = new Timestamp(sdf.parse(startTime).getTime());
        }
        if (StringUtils.isNotBlank(endTime))
        {
            end = new Timestamp(sdf.parse(endTime).getTime());
        }
        if (start != null && end != null)
        {
            if (start.after(end))
            {
                throw new Exception("开始时间要小于结束时间");
            }
        }
        // 拼接表单数据
        if (StringUtils.isNotBlank(formData.getString("module")))
        {
            map.put(IBOSchedulerOperationsValue.S_OperationModel, formData.getString("module"));
        }
        if (StringUtils.isNotBlank(formData.getString("operType")))
        {
            map.put(IBOSchedulerOperationsValue.S_OperationType, formData.getString("operType"));
        }
        if (StringUtils.isNotBlank(formData.getString("operObj")))
        {
            map.put(IBOSchedulerOperationsValue.S_OperationObjectType, formData.getString("operObj"));
        }
        if (StringUtils.isNotBlank(formData.getString("content")))
        {
            map.put(IBOSchedulerOperationsValue.S_OperationObjectContent, "%" + formData.getString("content") + "%");
        }
        if (StringUtils.isNotBlank(formData.getString("operationClientIp")))
        {
            map.put(IBOSchedulerOperationsValue.S_OperationClientIp, "%" + formData.getString("operationClientIp")
                + "%");
        }
        if (StringUtils.isNotBlank(formData.getString("operator")))
        {
            map.put(IBOSchedulerOperationsValue.S_Operator, formData.getString("operator"));
        }
        if (start != null)
        {
            map.put(CommonConstants.OPERATION_QRY_START, start);
        }
        if (end != null)
        {
            map.put(CommonConstants.OPERATION_QRY_END, end);
        }
        return map;
    }
    
    /**
     * 初始化静态数据
     * 
     * @return
     */
    private Map<String, Map<String, String>> getStaticMap()
    {
        if (staticMap == null)
        {
            staticMap = new HashMap<String, Map<String, String>>();
            Map<String, String> moduleMap = new HashMap<String, String>();
            moduleMap.put(CommonConstants.OPERATE_MODULE_CONFIG, CommonConstants.OPERATE_MODULE_CONFIG);
            moduleMap.put(CommonConstants.OPERATE_MODULE_DEPLOY, CommonConstants.OPERATE_MODULE_DEPLOY);
            moduleMap.put(CommonConstants.OPERATE_MODULE_MONITOR, CommonConstants.OPERATE_MODULE_MONITOR);
            moduleMap.put(CommonConstants.OPERATE_MODULE_SCHED, CommonConstants.OPERATE_MODULE_SCHED);
            moduleMap.put(CommonConstants.OPERATE_MODULE_START_STOP, CommonConstants.OPERATE_MODULE_START_STOP);
            staticMap.put(OPER_MODULE, moduleMap);
            Map<String, String> typeMap = new HashMap<String, String>();
            typeMap.put(CommonConstants.OPERATE_TYPE_ADD, CommonConstants.OPERATE_TYPE_ADD);
            typeMap.put(CommonConstants.OPERATE_TYPE_DELETE, CommonConstants.OPERATE_TYPE_DELETE);
            typeMap.put(CommonConstants.OPERATE_TYPE_HANGON, CommonConstants.OPERATE_TYPE_HANGON);
            typeMap.put(CommonConstants.OPERATE_TYPE_HOST_INIT, CommonConstants.OPERATE_TYPE_HOST_INIT);
            typeMap.put(CommonConstants.OPERATE_TYPE_MODIFY, CommonConstants.OPERATE_TYPE_MODIFY);
            typeMap.put(CommonConstants.OPERATE_TYPE_RECOVER, CommonConstants.OPERATE_TYPE_RECOVER);
            typeMap.put(CommonConstants.OPERATE_TYPE_SYCHRONIZE, CommonConstants.OPERATE_TYPE_SYCHRONIZE);
            typeMap.put(CommonConstants.OPERATE_TYPE_START, CommonConstants.OPERATE_TYPE_START);
            typeMap.put(CommonConstants.OPERATE_TYPE_STOP, CommonConstants.OPERATE_TYPE_STOP);
            typeMap.put(CommonConstants.OPERATE_TYPE_DEPLOY, CommonConstants.OPERATE_TYPE_DEPLOY);
            typeMap.put(CommonConstants.OPERATE_TYPE_ROLLBACK, CommonConstants.OPERATE_TYPE_ROLLBACK);
            staticMap.put(OPER_TYPE, typeMap);
            Map<String, String> objMap = new HashMap<String, String>();
            objMap.put(CommonConstants.OPERTATE_OBJECT_APP, CommonConstants.OPERTATE_OBJECT_APP);
            objMap.put(CommonConstants.OPERTATE_OBJECT_HOST, CommonConstants.OPERTATE_OBJECT_HOST);
            objMap.put(CommonConstants.OPERTATE_OBJECT_NODE, CommonConstants.OPERTATE_OBJECT_NODE);
            objMap.put(CommonConstants.OPERTATE_OBJECT_STRATEGY, CommonConstants.OPERTATE_OBJECT_STRATEGY);
            objMap.put(CommonConstants.OPERTATE_OBJECT_TASK, CommonConstants.OPERTATE_OBJECT_TASK);
            objMap.put(CommonConstants.OPERTATE_OBJECT_UESR, CommonConstants.OPERTATE_OBJECT_UESR);
            objMap.put(CommonConstants.OPERTATE_OBJECT_TASKGROUP, CommonConstants.OPERTATE_OBJECT_TASKGROUP);
            objMap.put(CommonConstants.OPERTATE_OBJECT_HOSTGROUP, CommonConstants.OPERTATE_OBJECT_HOSTGROUP);
            objMap.put(CommonConstants.OPERTATE_OBJECT_MONITOR_GROUP, CommonConstants.OPERTATE_OBJECT_MONITOR_GROUP);
            objMap.put(CommonConstants.OPERTATE_OBJECT_MONITOR_TASK, CommonConstants.OPERTATE_OBJECT_MONITOR_TASK);
            objMap.put(CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM,
                CommonConstants.OPERTATE_OBJECT_MONITOR_TASK_PARAM);
            staticMap.put(OPER_OBJ, objMap);
        }
        return staticMap;
    }
    
    public abstract void setOperateLogsItem(JSONObject operateLogsItem);
    
    public abstract void setOperateLogsCount(long operateLogsCount);
    
    public abstract void setOperateLogsItems(JSONArray operateLogsItems);
    
    public abstract void setOperateLogsRowIndex(int operateLogsRowIndex);
    
    public abstract void setQueryForm(JSONObject queryForm);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("operateLogs", "com.asiainfo.common.abo.bo.BOSchedulerOperations");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus operateLogsItem = createData("operateLogsItem");
        if (operateLogsItem != null && !operateLogsItem.getDataObject().isEmpty())
        {
            setOperateLogsItem(operateLogsItem.getDataObject());
        }
        IDataBus queryForm = createData("queryForm");
        if (queryForm != null && !queryForm.getDataObject().isEmpty())
        {
            setQueryForm(queryForm.getDataObject());
        }
        initExtend(cycle);
    }
    
}
