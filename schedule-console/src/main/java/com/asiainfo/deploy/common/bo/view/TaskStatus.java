package com.asiainfo.deploy.common.bo.view;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.exeframe.configmgr.util.ConfigPageUtil;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskRunView;
import com.asiainfo.schedule.core.utils.CommonUtils;
import com.asiainfo.schedule.core.utils.CronExpression;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogValue;
import com.asiainfo.schedule.log.service.interfaces.ISchedulerLogSV;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

/**
 * Created by SMT Copyright: Copyright(c) 2015 Asiainfo-Linkage
 */
public abstract class TaskStatus extends AppPage
{
    
    /** 静态数据map */
    private static Map<String, Map<String, String>> staticMap = null;
    
    /** 任务状态map在静态数据map中key */
    private static final String KEY_TASK_TYPE = "taskType";
    
    private static final Logger LOGGER = Logger.getLogger(TaskStatus.class);
    
    /**
     * 任务类型列表
     * 
     * @return
     * @throws Exception
     */
    public IDataBus getTaskInfo()
        throws Exception
    {
        IDataBus bus = ConfigPageUtil.getSelectList(getStaticMap().get("taskType"));
        return bus;
    }
    
    /**
     * 状态翻译
     * 
     * @param type
     * @param val
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public String doTranslate(String type, String val)
        throws RemoteException, Exception
    {
        String text = null;
        if ("TASK_STATE".equals(type))
        {
            if ("U".equals(val))
            {
                text = "正常";
            }
            else
            {
                text = "异常";
            }
        }
        return text;
    }
    
    /**
     * 任务编码列表
     * 
     * @return
     * @throws Exception
     */
    public IDataBus getTaskCode()
        throws Exception
    {
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        IAIMonStaticDataSV dataSV = (IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
        IBOAIMonStaticDataValue[] result = dataSV.qryTaskInfos("TASK_INFO", null, null);
        for (int i = 0; i < result.length; i++)
        {
            JSONObject obj = new JSONObject();
            String key = result[i].getCodeValue();
            String value = result[i].getCodeValue();
            obj.put("TEXT", key);
            obj.put("VALUE", value);
            jsonArray.add(obj);
        }
        return new DataBus(context, jsonArray);
    }
    
    /**
     * 任务编码列表
     * 
     * @return
     * @throws Exception
     */
    public void qryTaskCode(IRequestCycle cycle)
        throws Exception
    {
        JSONArray jsonArray = new JSONArray();
        IAIMonStaticDataSV dataSV = (IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
        IBOAIMonStaticDataValue[] result = dataSV.qryTaskInfos("TASK_INFO", null, null);
        for (int i = 0; i < result.length; i++)
        {
            JSONObject obj = new JSONObject();
            String key = result[i].getCodeValue();
            obj.put("TEXT", key);
            jsonArray.add(obj);
        }
        this.setAjax(jsonArray);
        
    }
    
    public boolean compareTime(String startTime, Date nextSchedTime, Date realityStartTime)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!CommonUtils.isBlank(startTime) && startTime.indexOf("-1") >= 0)
        {
            try
            {
                CronExpression ce = new CronExpression(CommonUtils.translateCron(startTime));
                String realityNextSchedTime =
                    DateUtils.formatYYYYMMddHHmmss(ce.getNextValidTimeAfter(realityStartTime));
                Date realityNext = dateFormat.parse(realityNextSchedTime);
                if (realityNext.compareTo(nextSchedTime) == 0)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            catch (Exception ex)
            {
            }
        }
        return false;
    }
    
    /**
     * 根据条件查找task
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryTaskInfos(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        ISchedulerLogSV logSv = (ISchedulerLogSV)ServiceFactory.getService(ISchedulerLogSV.class);
        JSONArray array = new JSONArray();
        IDataBus qryformBus = createData("qryCondition");
        JSONObject condition = (JSONObject)qryformBus.getData();
        String taskType = condition.getString("taskType");
        String taskCode = condition.getString("taskCode");
        // 添加了一个手动填任务编码的框
        String taskCode1 = condition.getString("taskCode1");
        
        IAIMonStaticDataSV dataSV = (IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
        IBOAIMonStaticDataValue[] dataValues = dataSV.qryTaskInfos("TASK_INFO", taskCode, taskCode1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (dataValues != null && dataValues.length > 0)
        {
            for (IBOAIMonStaticDataValue value : dataValues)
            {
                String code = value.getCodeValue();
                if (StringUtils.isBlank(code))
                {
                    LOGGER.error("配置的任务编码为空或空格！");
                    continue;
                }
                TaskRunView[] views = sv.getTaskRunView(null, code, null);
                // TaskRunView view = getTaskRunView(views, code);
                if (views != null && views.length > 0)
                {
                    for (TaskRunView view : views)
                    {
                        if (view != null
                            && (StringUtils.isBlank(taskType) || taskType.equals(view.getTaskBean().getTaskType())))
                        {
                            JSONObject obj = trans2JSON(view.getTaskBean());
                            Date now = new Date();
                            String nDate = df.format(now);
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY, 0);
                            cal.set(Calendar.SECOND, 0);
                            cal.set(Calendar.MINUTE, 0);
                            cal.set(Calendar.MILLISECOND, 0);
                            String sDate = df.format(cal.getTime());
                            
                            // 通过任务编码查询日志，得到当天任务实际开始和结束时间
                            IBOAISchedTaskLogValue[] values =
                                logSv.getSchedTaskLogs(view.getTaskBean().getTaskCode(), sDate, nDate, null, 1, 1);
                            // IBOAISchedTaskLogValue[] values =
                            // logSv.getSchedTaskLogs(view.getTaskBean().getTaskCode(), null, null, null, 1, 1);
                            if (values != null && values.length > 0)
                            {
                                IBOAISchedTaskLogValue logValue = values[0];
                                if (logValue != null)
                                {
                                    if ("run$now".equals(obj.getString("startTime")))
                                    {
                                        if ("stop".equals(view.getTaskRunSts()))
                                        {
                                            obj.put("status", "E");
                                        }
                                        if ("start".equals(view.getTaskRunSts()))
                                        {
                                            obj.put("status", "U");
                                        }
                                    }
                                    // 当实际开始时间为空，默认为当天0点
                                    if (logValue.getStartTime() != null)
                                    {
                                        String startTime = df.format(logValue.getStartTime());
                                        obj.put("realityStartTime", startTime);
                                        // 下次计划时间判空
                                        if (view.getNextSchedStartTime() != null)
                                        {
                                            Date nextSchedTime = df.parse(view.getNextSchedStartTime());
                                            if (compareTime(obj.getString("startTime"),
                                                nextSchedTime,
                                                logValue.getStartTime()))
                                            {
                                                obj.put("status", "U");
                                            }
                                            else
                                            {
                                                obj.put("status", "E");
                                            }
                                        }
                                    }
                                    else
                                    {
                                        // 下次计划时间判空
                                        if (view.getNextSchedStartTime() != null)
                                        {
                                            Date nextSchedTime = df.parse(view.getNextSchedStartTime());
                                            if (compareTime(obj.getString("startTime"), nextSchedTime, cal.getTime()))
                                            {
                                                obj.put("status", "U");
                                            }
                                            else
                                            {
                                                obj.put("status", "E");
                                            }
                                        }
                                    }
                                    if (logValue.getFinishTime() != null)
                                    {
                                        String endTime = df.format(logValue.getFinishTime());
                                        obj.put("realityEndTime", endTime);
                                    }
                                }
                            }
                            else
                            {
                                if ("run$now".equals(obj.getString("startTime")))
                                {
                                    if ("stop".equals(view.getTaskRunSts()))
                                    {
                                        obj.put("status", "E");
                                    }
                                    if ("start".equals(view.getTaskRunSts()))
                                    {
                                        obj.put("status", "U");
                                    }
                                }
                                // 下次计划时间判空
                                if (view.getNextSchedStartTime() != null)
                                {
                                    Date nextSchedTime = df.parse(view.getNextSchedStartTime());
                                    if (compareTime(obj.getString("startTime"), nextSchedTime, cal.getTime()))
                                    {
                                        obj.put("status", "U");
                                    }
                                    else
                                    {
                                        obj.put("status", "E");
                                    }
                                }
                            }
                            /*
                             * obj.put("nextSchedTime", view.getNextSchedStartTime()); obj.put("nextSchedEndTime",
                             * view.getNextSchedEndTime());
                             */
                            array.add(obj);
                        }
                    }
                }
            }
        }
        /* this.setTaskInfosTabItems(array); */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskInfosTabItems", PagingUtil.convertArray2Page(array, offset, linage));
        jsonObject.put("total", array == null ? 0 : array.size());
        this.setAjax(jsonObject);
        
    }
    
    public TaskRunView getTaskRunView(TaskRunView[] views, String taskCode)
    {
        TaskRunView result = null;
        if (views == null || views.length <= 0)
        {
            result = null;
        }
        else if (views.length == 1)
        {
            result = views[0];
        }
        else
        {
            for (TaskRunView runView : views)
            {
                if (taskCode.equals(runView.getTaskBean().getTaskCode()))
                {
                    result = runView;
                    break;
                }
            }
        }
        return result;
    }
    
    /**
     * 将task根据操作转换个json
     * 
     * @param task
     * @param operation
     * @return
     */
    private JSONObject trans2JSON(TaskBean task)
    {
        JSONObject obj = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (null != task)
        {
            obj.put("taskCode", task.getTaskCode());
            obj.put("taskName", task.getTaskName());
            obj.put("taskDesc", task.getTaskDesc());
            obj.put("taskType", getStaticMap().get(KEY_TASK_TYPE).get(task.getTaskType()));
            obj.put("taskGroup", task.getTaskGroupCode());
            obj.put("startTime", task.getStartTime());
            obj.put("endTime", task.getEndTime());
            obj.put("scanIntervalTime", task.getScanIntervalTime());
            obj.put("scanNum", task.getScanNum());
            obj.put("executeNum", task.getExecuteNum());
            obj.put("threadNum", task.getThreadNum());
            obj.put("idleSleepTime", task.getIdleSleepTime());
            obj.put("processClass", task.getProcessClass());
            obj.put("splitRegion", task.isSplitRegion() ? "\u662f" : "\u5426");
            
            obj.put("createTime", sdf.format(task.getCreateTime()));
            obj.put("state", task.getState());
        }
        return obj;
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
            initStaticMap();
        }
        return staticMap;
    }
    
    /**
     * 初始化静态数据
     */
    private void initStaticMap()
    {
        staticMap = new HashMap<String, Map<String, String>>();
        // 加载任务类型map
        Map<String, String> taskTypeMap = new HashMap<String, String>();
        taskTypeMap.put("single", "single");
        taskTypeMap.put("complex", "complex");
        taskTypeMap.put("reload", "reload");
        taskTypeMap.put("tf", "tf");
        staticMap.put(KEY_TASK_TYPE, taskTypeMap);
    }
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public abstract void setTaskInfosTabRowIndex(int taskInfosTabRowIndex);
    
    public abstract void setTaskInfosTabCount(long taskInfosTabCount);
    
    public abstract void setQryCondition(JSONObject qryCondition);
    
    public abstract void setTaskInfosTabItems(JSONArray taskInfosTabItems);
    
    public abstract void setTaskInfosTabItem(JSONObject taskInfosTabItem);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus qryCondition = createData("qryCondition");
        if (qryCondition != null && !qryCondition.getDataObject().isEmpty())
        {
            setQryCondition(qryCondition.getDataObject());
        }
        IDataBus taskInfosTabItem = createData("taskInfosTabItem");
        if (taskInfosTabItem != null && !taskInfosTabItem.getDataObject().isEmpty())
        {
            setTaskInfosTabItem(taskInfosTabItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
