package com.asiainfo.schedule.view;

import java.sql.Timestamp;
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
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.exeframe.configmgr.util.ConfigPageUtil;
import com.asiainfo.schedule.core.po.TaskGroupBean;
import com.asiainfo.schedule.core.po.TaskView;
import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogDTLValue;
import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogValue;
import com.asiainfo.schedule.log.service.interfaces.ISchedulerLogSV;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;
import com.asiainfo.schedule.util.ScheduleConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class QryTaskLog extends AppPage
{
    
    private static final Logger LOGGER = Logger.getLogger(QryTaskLog.class);
    
    private static Map<String, Map<String, String>> staticMap = null;
    
    private Map<String, String> taskGroupMap;
    
    private static final String KEY_LOG_TYPE = "logType";
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 根据任务分组关联任务编码
     * 
     * @param cycle
     * @throws Exception
     */
    public void showTaskCodes(IRequestCycle cycle)
        throws Exception
    {
        String groupCode = getContext().getParameter("groupCode");
        JSONArray array = new JSONArray();
        if (StringUtils.isBlank(groupCode))
        {
            JSONObject obj = new JSONObject();
            obj.put("key", "");
            obj.put("value", "\u8bf7\u5148\u9009\u62e9\u5206\u7ec4");
            array.add(obj);
        }
        else
        {
            ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
            TaskView[] views = sv.getTaskView(groupCode, null, null);
            if (views != null && views.length > 0)
            {
                for (int i = 0; i < views.length; i++)
                {
                    JSONObject obj = new JSONObject();
                    obj.put("key", views[i].getTaskCode());
                    obj.put("value", views[i].getTaskName());
                    array.add(obj);
                }
            }
        }
        this.setAjax(array);
    }
    
    /**
     * 查询日志基本信息
     * 
     * @param cycle
     */
    public void qryLogs(IRequestCycle cycle)
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
        String taskCodeName = null;
        if (StringUtils.isNotBlank(queryFormCondition.getString("taskCodeName")))
        {
            taskCodeName = queryFormCondition.getString("taskCodeName");
        }
        String taskCode = queryFormCondition.getString("taskCode");
        String state = null;
        if (StringUtils.isNotBlank(queryFormCondition.getString("state")))
        {
            state = queryFormCondition.getString("state");
        }
        int start = Integer.parseInt(offset) + 1;
        int end = Integer.parseInt(offset) + Integer.parseInt(linage);
        
        ISchedulerLogSV sv = (ISchedulerLogSV)ServiceFactory.getService(ISchedulerLogSV.class);
        try
        {
            if (!StringUtils.isNotBlank(taskCodeName) && !StringUtils.isNotBlank(taskCode))
            {
                throw new Exception("任务名称和任务编码请至少填写一项");
            }
            else if (StringUtils.isNotBlank(taskCodeName) && StringUtils.isNotBlank(taskCode)
                && !taskCodeName.contains(taskCode))
            {
                throw new Exception("任务名称和任务编码不匹配");
            }
            if (StringUtils.isNotBlank(taskCodeName))
            {
                taskCode = taskCodeName;
            }
            if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime))
            {
                Timestamp tempStart = Timestamp.valueOf(startTime);
                Timestamp tempEnd = Timestamp.valueOf(endTime);
                if (tempStart.after(tempEnd))
                {
                    throw new Exception("开始时间要小于结束时间");
                }
            }
            IBOAISchedTaskLogValue[] values = sv.getSchedTaskLogs(taskCode, startTime, endTime, state, start, end);
            int total = sv.getSchedTaskLogCount(taskCode, startTime, endTime, state);
            IDataBus bus = null;
            if (values == null)
                bus = DataBusHelper.getEmptyArrayDataBus();
            else
                bus = DataBusHelper.getDataBusByBeans(values, IBOAISchedTaskLogValue.class);
            JSONArray array = bus.getDataArray();
            /*
             * this.setLogsItems(array); this.setLogsCount(total);
             */
            
            result.put("logsItems", array);
            result.put("total", total);
            
        }
        catch (Exception e)
        {
            result.put("flag", "F");
            result.put("msg", e.getMessage());
            LOGGER.error("qryLogs failed", e);
        }
        this.setAjax(result);
    }
    
    /**
     * 查询日志详细信息
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryDetail(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String taskCode = getContext().getParameter("taskCode");
        String jobId = getContext().getParameter("jobId");
        if (StringUtils.isBlank(taskCode) || StringUtils.isBlank(jobId))
        {
            LOGGER.error("qryDetail failed,param is incomplete;taskcode is " + taskCode + ", jobId is " + jobId);
            return;
        }
        ISchedulerLogSV sv = (ISchedulerLogSV)ServiceFactory.getService(ISchedulerLogSV.class);
        try
        {
            IBOAISchedTaskLogDTLValue[] values = sv.getSchedTaskLogDtls(taskCode.trim(), jobId.trim(), null, -1, -1);
            IDataBus bus = null;
            if (values == null)
                bus = DataBusHelper.getEmptyArrayDataBus();
            else
                bus = DataBusHelper.getDataBusByBeans(values, IBOAISchedTaskLogDTLValue.class);
            JSONArray array = bus.getDataArray();
            for (Object object : array)
            {
                JSONObject json = (JSONObject)object;
                String temp = json.getString("exMsg");
                
                if (StringUtils.isNotBlank(temp) && temp.length() >= 50)
                {
                    temp = temp.substring(0, 50) + "...";
                }
                json.put("exMsgShort", temp);
            }
            /* this.setDetailsItems(array); */
            
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("detailsItems", PagingUtil.convertArray2Page(array, offset, linage));
            jsonObject.put("total", array.size());
            this.setAjax(jsonObject);
            
        }
        catch (Exception e)
        {
            LOGGER.error("qryDetail failed", e);
        }
    }
    
    /**
     * 转换记录类型
     * 
     * @param type
     * @return
     */
    public String logTypeTrans(String type)
    {
        return getStaticMap().get(KEY_LOG_TYPE).get(type);
    }
    
    /**
     * 初始化任务分组map
     */
    private void initTaskGroupMap()
    {
        taskGroupMap = new HashMap<String, String>();
        ISchedulerSV schSv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        try
        {
            TaskGroupBean[] beans = schSv.getAllTaskGroup();
            if (beans != null)
            {
                for (TaskGroupBean bean : beans)
                {
                    taskGroupMap.put(bean.getGroupCode(), bean.getGroupName());
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("get task Group failed,exception is " + e);
        }
    }
    
    /**
     * 获取业务分组下拉菜单
     * 
     * @return
     */
    public IDataBus getTaskGroupList()
    {
        initTaskGroupMap();
        IDataBus bus = ConfigPageUtil.getSelectList(taskGroupMap);
        return bus;
    }
    
    /**
     * 获取类型下拉菜单
     * 
     * @return
     */
    public IDataBus getStateSelectList()
    {
        IDataBus bus = ConfigPageUtil.getSelectList(getStaticMap().get(KEY_LOG_TYPE));
        return bus;
    }
    
    /**
     * 获取静态数据
     * 
     * @return
     */
    private Map<String, Map<String, String>> getStaticMap()
    {
        if (staticMap == null)
        {
            staticMap = new HashMap<String, Map<String, String>>();
            Map<String, String> logType = new HashMap<String, String>();
            logType.put(ScheduleConstants.TASK_LOG_STATE_C, "\u6267\u884c\u4e2d");
            logType.put(ScheduleConstants.TASK_LOG_STATE_F, "\u6267\u884c\u7ed3\u675f");
            logType.put(ScheduleConstants.TASK_LOG_STATE_E, "\u6267\u884c\u5f02\u5e38");
            staticMap.put(KEY_LOG_TYPE, logType);
        }
        return staticMap;
    }
    
    public abstract void setLogsItems(JSONArray logsItems);
    
    public abstract void setLogsItem(JSONObject logsItem);
    
    public abstract void setLogsCount(long logsCount);
    
    public abstract void setDetailsCount(long detailsCount);
    
    public abstract void setDetailsItem(JSONObject detailsItem);
    
    public abstract void setLogsRowIndex(int logsRowIndex);
    
    public abstract void setDetailsRowIndex(int detailsRowIndex);
    
    public abstract void setDetailsItems(JSONArray detailsItems);
    
    public abstract void setQueryForm(JSONObject queryForm);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("logs", "com.asiainfo.schedule.bo.BOAISchedTaskLog");
        
        bindBoName("details", "com.asiainfo.schedule.bo.BOAISchedTaskLogDTL");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus logsItem = createData("logsItem");
        if (logsItem != null && !logsItem.getDataObject().isEmpty())
        {
            setLogsItem(logsItem.getDataObject());
        }
        IDataBus detailsItem = createData("detailsItem");
        if (detailsItem != null && !detailsItem.getDataObject().isEmpty())
        {
            setDetailsItem(detailsItem.getDataObject());
        }
        IDataBus queryForm = createData("queryForm");
        if (queryForm != null && !queryForm.getDataObject().isEmpty())
        {
            setQueryForm(queryForm.getDataObject());
        }
        initExtend(cycle);
    }
    
}
