package com.asiainfo.schedule.view;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.common.AIException;
import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.busi.cache.impl.Group;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.util.ConfigPageUtil;
import com.asiainfo.monitor.service.ExternalSvUtil;
import com.asiainfo.resultConstant.ResultConstants;
import com.asiainfo.schedule.bo.BOAISchedTaskLogBean;
import com.asiainfo.schedule.core.center.TaskStat;
import com.asiainfo.schedule.core.po.ServerBean;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskGroupBean;
import com.asiainfo.schedule.core.po.TaskItemJobBean;
import com.asiainfo.schedule.core.po.TaskRunView;
import com.asiainfo.schedule.core.utils.Constants.ServerType;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogValue;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;
import com.asiainfo.schedule.util.ScheduleConstants;
import com.asiainfo.schedule.util.ScheduleUtil;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class TaskOperation extends AppPage
{
    private static final Logger LOGGER = Logger.getLogger(TaskOperation.class);
    
    /** 静态数据map */
    private static Map<String, Map<String, Object>> staticMap = null;
    
    /** 任务状态map在静态数据map中key */
    private static final String KEY_TASK_TYPE = "taskType";
    
    /** 状态判断map在静态数据map中key */
    private static final String KEY_FLAG = "flag";
    
    /** 故障处理方式map在静态数据map中key */
    private static final String KEY_FAULT_PROCESS_METHOD = "faultProcessMethod";
    
    /** 每页记录数量 */
    private static int PAGE_SIZE = 20;
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * @param request
     * @throws Exception
     */
    public void showDetails(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String taskCode = getContext().getParameter("taskCode");
        String jobId = getContext().getParameter("jobId");
        JSONArray array = new JSONArray();
        try
        {
            if (StringUtils.isBlank(taskCode) || StringUtils.isBlank(jobId))
            {
                return;
            }
            ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
            TaskItemJobBean[] beans = sv.getTaskItemJobs(taskCode.trim(), jobId.trim());
            if (beans == null)
            {
                throw new Exception("qry job details failed,result is null");
            }
            for (TaskItemJobBean job : beans)
            {
                JSONObject obj = new JSONObject();
                obj.put("itemJobId", job.getItemJobId());
                obj.put("taskJobId", job.getTaskJobId());
                obj.put("taskCode", job.getTaskCode());
                obj.put("taskItemId", job.getTaskItemId());
                obj.put("serverId", job.getServerId());
                obj.put("priority", job.getPriority());
                array.add(obj);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("qry job details failed", e);
        }
        /* this.setTaskBaseLogsItems(array); */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskBaseLogsItems", PagingUtil.convertArray2Page(array, offset, linage));
        jsonObject.put("total", array == null ? 0 : array.size());
        this.setAjax(jsonObject);
        
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
        JSONArray array = new JSONArray();
        IDataBus qryformBus = createData("taskQuery");
        JSONObject condition = (JSONObject)qryformBus.getData();
        List<TaskBean> resultBeans = new ArrayList<TaskBean>();
        try
        {
			TaskRunView[] runViews = sv.getTaskRunView(condition.getString("taskGroup"), condition.getString("taskCode"), null);
			TaskRunView[] hangViews = sv.getTaskHangOnView(condition.getString("taskGroup"), condition.getString("taskCode"), null);
			List<TaskRunView> views = new ArrayList<TaskRunView>();
			if (runViews != null) {
				views.addAll(Arrays.asList(runViews));
			}
			if (hangViews != null) {
				views.addAll(Arrays.asList(hangViews));
			}
            
            // 转化为jsonarray
            for (TaskRunView view : views)
            {
                if (view != null)
                {
                    JSONObject obj = trans2JSON(view.getTaskBean());
                    obj.put("flag", getStaticMap().get(KEY_FLAG).get(view.getTaskRunSts()));
                    obj.put("taskState", view.getTaskRunSts());
                    obj.put("nextSchedTime", view.getNextSchedStartTime());
                    obj.put("nextSchedEndTime", view.getNextSchedEndTime());
                    if (view.getRunningTaskJob() != null)
                    {
                        obj.put("jobId", view.getRunningTaskJob().getJobId());
                        obj.put("createTime", view.getRunningTaskJob().getCreateTime());
                    }
                    obj.put("curManagerId", view.getCurManagerId());
                    array.add(obj);
                }
            }
            
        }
        catch (Exception e)
        {
            LOGGER.error("qryTaskInfos failed", e);
        }
        /* this.setTaskInfosItems(array); */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskInfosItems", PagingUtil.convertArray2Page(array, offset, linage));
        jsonObject.put("total", array == null ? 0 : array.size());
        this.setAjax(jsonObject);
        
    }
    
    /**
     * 显示服务详情
     * 
     * @param cycle
     * @throws Exception
     */
    public void showServerInfo(IRequestCycle cycle)
        throws Exception
    {
        String serverId = getContext().getParameter("serverId").trim();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject obj = new JSONObject();
        JSONObject result = new JSONObject();
        try
        {
            ServerBean bean = sv.getRegistServerInfo(serverId, ServerType.manager);
            if (bean != null)
            {
                obj.put("serverId", bean.getServerId());
                obj.put("hostName", bean.getHostName());
                obj.put("pid", bean.getPid());
                obj.put("registTime", DateUtils.formatYYYYMMddHHmmss(bean.getRegistTime()));
                obj.put("heartbeatTime", DateUtils.formatYYYYMMddHHmmss(bean.getHeartbeatTime()));
                obj.put("heartbeatInfo", bean.getHeartbeatInfo());
                // 查询服务状态
                String serverState = sv.getTaskServerRunSts(bean.getServerId());
                // 返回null也默认开启中
                serverState = serverState == null ? "start" : serverState;
                obj.put("serverState", serverState);
                result.put("flag", "T");
            }
            else
            {
                result.put("flag", "F");
                LOGGER.error("qry serverBean by serverId failed,result is null");
            }
        }
        catch (Exception e)
        {
            result.put("flag", "F");
            LOGGER.error("qry serverBean by serverId failed", e);
        }
        this.setAjax(result);
        this.setServerDetail(obj);
    }
    
    /**
     * 获取业务分组下拉菜单
     * 
     * @return
     */
    public IDataBus getTaskGroupList()
    {
        ISchedulerSV schSv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        Map<String, String> taskGroupMap = new HashMap<String, String>();
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
        IDataBus bus = ConfigPageUtil.getSelectList(taskGroupMap);
        return bus;
    }
    
    /**
     * 获取主机分组下拉菜单
     * 
     * @return
     */
    public IDataBus getHostGroupList()
    {
        ISchedulerSV schSv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        Map<String, String> hostGroupMap = new HashMap<String, String>();
        Map<String, Group> map = null;
        try
        {
            IAIMonGroupSV monGroupSV = (IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
            // IBOAIMonGroupValue[] groupValues=monGroupSV.getAllGroupBean();
            UserInfoInterface userInfo = CommonSvUtil.getUserInfo();
            IBOAIMonGroupValue[] groupValues = null;
            // 当用户信息不为空的时候，获取分组信息
            if (userInfo != null)
            {
                groupValues = monGroupSV.getGroupByPriv(String.valueOf(userInfo.getID()) + "_" + userInfo.getCode());
            }
            if (groupValues != null && groupValues.length > 0)
            {
                for (IBOAIMonGroupValue group : groupValues)
                {
                    hostGroupMap.put(String.valueOf(group.getGroupId()), group.getGroupName());
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("get host group failed,exception is " + e);
        }
        IDataBus bus = ConfigPageUtil.getSelectList(hostGroupMap);
        return bus;
    }
    
    /**
     * 关联展示主机
     * 
     * @param cycle
     * @throws Exception
     */
    public void showHosts(IRequestCycle cycle)
        throws Exception
    {
        String groupId = getContext().getParameter("hostGroupCode");
        JSONArray array = new JSONArray();
        if (StringUtils.isBlank(groupId))
        {
            JSONObject obj = new JSONObject();
            obj.put("key", "");
            obj.put("value", "\u8bf7\u5148\u9009\u62e9\u96c6\u7fa4");
            array.add(obj);
        }
        else
        {
            IBOAIMonPhysicHostValue[] hosts = ExternalSvUtil.qryHostInfoByGroupId(groupId);
            if (hosts != null && hosts.length > 0)
            {
                for (int i = 0; i < hosts.length; i++)
                {
                    JSONObject obj = new JSONObject();
                    obj.put("key", hosts[i].getHostId());
                    obj.put("value", hosts[i].getHostName() + "(" + hosts[i].getHostIp() + ")");
                    array.add(obj);
                }
            }
        }
        this.setAjax(array);
    }
    
    /**
     * 展示当前任务的参数
     * 
     * @param cycle
     * @throws Exception
     */
    public void showParam(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String taskCode = getContext().getParameter("taskCode").trim();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject result = new JSONObject();
        Map<String, String> map = null;
        try
        {
            map = sv.getTaskParam(taskCode);
            JSONArray array = new JSONArray();
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
                /* this.setTaskParamsItems(array); */
                result.put("taskParamsItems", PagingUtil.convertArray2Page(array, offset, linage));
                result.put("total", array == null ? 0 : array.size());
            }
            else
            {
                // 没有参数时给页面特殊提示
                result.put("flag", "E");
            }
        }
        catch (Exception e)
        {
            LOGGER.error("get TaskParam failed,reason is " + e.getMessage() + ";taskcode is " + taskCode);
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
            result.put(ResultConstants.ResultKey.RESULT_MSG, e.getMessage());
        }
        
        this.setAjax(result);
    }
    
    /**
     * 立即启动进程
     * 
     * @param cycle
     * @throws Exception
     */
    public void startTaskNow(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        
        String taskCode = getContext().getParameter("taskCode").trim();
        JSONObject result = new JSONObject();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        String paramStr = getContext().getParameter("params").trim();
        try
        {
            JSONArray array = JSONArray.fromObject(paramStr);
            HashMap<String, String> map = new HashMap<String, String>();
            if (array != null)
            {
                for (Object obj : array)
                {
                    JSONObject json = (JSONObject)obj;
                    map.put((String)json.get("key"), (String)json.get("value"));
                }
            }
            String opId = ScheduleUtil.getOpId();
            sv.startTaskNow(taskCode, map, opId);
        }
        catch (Exception e)
        {
            retMap.put("flag", ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("hangOn task failed,reason is " + e.getMessage() + ";taskCode is " + taskCode);
        }
        finally
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_SCHED,
                CommonConstants.OPERATE_TYPE_START,
                CommonConstants.OPERTATE_OBJECT_APP,
                taskCode,
                null);
        }
        result.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("flag"));
        this.setAjax(result);
    }
    
    /**
     * 挂起进程
     * 
     * @param cycle
     * @throws Exception
     */
    public void hangOn(IRequestCycle cycle)
        throws Exception
    {
        String taskCode = getContext().getParameter("taskCode").trim();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject result = new JSONObject();
        try
        {
            String opId = ScheduleUtil.getOpId();
            sv.stopTask(taskCode, opId);
            //修改任务状态为挂起
            sv.taskHangOn(taskCode);
        }
        catch (Exception e)
        {
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("hangOn task failed,reason is " + e.getMessage() + ";taskCode is " + taskCode);
        }
        finally
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_SCHED,
                CommonConstants.OPERATE_TYPE_HANGON,
                CommonConstants.OPERTATE_OBJECT_APP,
                taskCode,
                null);
        }
        this.setAjax(result);
    }
    
    /**
     * @param cycle
     * @throws Exception
     */
    public void qryPro(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        IDataBus queryFormBus = createData("queryFORM");
        JSONObject conditions = (JSONObject)queryFormBus.getData();
        ISchedulerSV schSv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        
        String groupCode = (String)conditions.get(ScheduleConstants.PARAM_HOST_GROUP);
        String hostId = (String)conditions.get(ScheduleConstants.PARAM_HOST_ID);
        
        String serverCode = (String)conditions.get(ScheduleConstants.PARAM_SERVER_CODE);
        JSONArray array = new JSONArray();
        
        if (StringUtils.isNotBlank(serverCode))
        {
            serverCode = serverCode.trim();
        }
        
        // 整合应用编码 保存主机相关信息
        Map<String, IBOAIMonPhysicHostValue> map = CommonSvUtil.qryHostInfoByCondition(groupCode, hostId, serverCode);
        if (!map.isEmpty())
        {
            Set<Map.Entry<String, IBOAIMonPhysicHostValue>> entrys = map.entrySet();
            for (Map.Entry<String, IBOAIMonPhysicHostValue> entry : entrys)
            {
                JSONObject obj = new JSONObject();
                obj.put("serverId", entry.getKey());
                obj.put("hostName", entry.getValue().getHostName() + "(" + entry.getValue().getHostIp() + ")");
                array.add(obj);
            }
        }
        // 根据保存应用编码开始查询拼接数据
        if (!array.isEmpty())
        {
            for (Object obj : array)
            {
                JSONObject json = (JSONObject)obj;
                ServerBean bean = schSv.getRegistServerInfo(json.getString("serverId"), ServerType.processor);
                // 拼接任务信息，没查到即为异常
                if (bean == null)
                {
                    json.put("serverState", "\u5f02\u5e38");
                }
                else
                {
                    json.put("pid", bean.getPid());
                    json.put("registTime", DateUtils.formatYYYYMMddHHmmss(bean.getRegistTime()));
                    json.put("heartbeatTime", DateUtils.formatYYYYMMddHHmmss(bean.getHeartbeatTime()));
                    json.put("heartbeatInfo", bean.getHeartbeatInfo());
                    // 查询服务状态
                    String serverState = schSv.getTaskServerRunSts(json.getString("serverId"));
                    // 返回null也默认开启中
                    serverState = serverState == null ? "start" : serverState;
                    json.put("serverState", serverState);
                    int flag = (Integer)getStaticMap().get(KEY_FLAG).get(serverState);
                    json.put("flag", flag);
                }
            }
        }
        /* this.setTaskProcessesItems(array); */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskProcessesItems", PagingUtil.convertArray2Page(array, offset, linage));
        jsonObject.put("total", array == null ? 0 : array.size());
        this.setAjax(jsonObject);
        
    }
    
    /**
     * 进程执行详情查询
     * 
     * @param cycle
     * @throws Exception
     */
    public void showTaskPros(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String serverIdStr = getContext().getParameter("serverId");
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONArray array = new JSONArray();
        if (StringUtils.isNotBlank(serverIdStr))
        {
            TaskItemJobBean[] beans = sv.getAllTaskItemJobsByServerId(serverIdStr.trim());
            if (beans == null)
            {
                LOGGER.error("qry TaskItemJobBean failed, result is null");
            }
            for (TaskItemJobBean job : beans)
            {
                JSONObject obj = new JSONObject();
                obj.put("itemJobId", job.getItemJobId());
                obj.put("taskJobId", job.getTaskJobId());
                obj.put("taskCode", job.getTaskCode());
                obj.put("taskItemId", job.getTaskItemId());
                obj.put("serverId", job.getServerId());
                obj.put("priority", job.getPriority());
                array.add(obj);
            }
        }
        /* this.setTaskBaseLogsItems(array); */
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskBaseLogsItems", PagingUtil.convertArray2Page(array, offset, linage));
        jsonObject.put("total", array == null ? 0 : array.size());
        this.setAjax(jsonObject);
        
    }
    
    /**
     * 挂起服务
     * 
     * @param cycle
     * @throws Exception
     */
    public void hangOnServer(IRequestCycle cycle)
        throws Exception
    {
        String serverId = getContext().getParameter("serverId").trim();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject result = new JSONObject();
        try
        {
            if (StringUtils.isNotBlank(serverId))
            {
                sv.stopServer(serverId, ScheduleUtil.getOpId());
            }
        }
        catch (Exception e)
        {
            result.put("flag", "F");
            result.put("msg", e.getMessage());
            LOGGER.error("hangOnServer failed,reason is " + e.getMessage() + ";serverCode is " + serverId);
        }
        this.setAjax(result);
    }
    
    /**
     * 恢复服务
     * 
     * @param cycle
     * @throws Exception
     */
    public void resumeServer(IRequestCycle cycle)
        throws Exception
    {
        String serverId = getContext().getParameter("serverId").trim();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject result = new JSONObject();
        try
        {
            if (StringUtils.isNotBlank(serverId))
            {
                sv.resumServer(serverId, ScheduleUtil.getOpId());
            }
        }
        catch (Exception e)
        {
            result.put("flag", "F");
            result.put("msg", e.getMessage());
            LOGGER.error("resumeServer failed,reason is " + e.getMessage() + ";serverCode is " + serverId);
        }
        this.setAjax(result);
    }
    
    /**
     * 初始化静态数据
     */
    private void initStaticMap()
    {
        staticMap = new HashMap<String, Map<String, Object>>();
        Map<String, Object> flagMap = new HashMap<String, Object>();
        flagMap.put("start", 1);
        flagMap.put("stop", 2);
        staticMap.put(KEY_FLAG, flagMap);
        // 加载任务类型map
        Map<String, Object> taskTypeMap = new HashMap<String, Object>();
        taskTypeMap.put("single", "single");
        taskTypeMap.put("complex", "complex");
        taskTypeMap.put("reload", "reload");
        taskTypeMap.put("tf", "tf");
        staticMap.put(KEY_TASK_TYPE, taskTypeMap);
        // 加载故障处理方式map
        Map<String, Object> faultProcessMethodMap = new HashMap<String, Object>();
        faultProcessMethodMap.put("M", "\u4eba\u5de5\u5904\u7406");
        faultProcessMethodMap.put("A", "\u81ea\u52a8\u5904\u7406");
        staticMap.put(KEY_FAULT_PROCESS_METHOD, faultProcessMethodMap);
    }
    
    /**
     * 获取静态数据
     * 
     * @return
     */
    private Map<String, Map<String, Object>> getStaticMap()
    {
        if (staticMap == null)
        {
            initStaticMap();
        }
        return staticMap;
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
            obj.put("faultProcessMethod", getStaticMap().get(KEY_FAULT_PROCESS_METHOD)
                .get(task.getFaultProcessMethod()));
            obj.put("curVersion", task.getVersion());
            obj.put("items", task.getItems() == null ? null : array2String(task.getItems()));
            obj.put("createTime", sdf.format(task.getCreateTime()));
            obj.put("state", task.getState());
        }
        return obj;
    }
    
    /**
     * String数组转成String
     * 
     * @param array
     * @return
     */
    private String array2String(String[] array)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++)
        {
            sb.append(array[i]);
            if (i < array.length - 1)
            {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    /**
     * 获取字符串的片段
     * 
     * @param str
     * @param length
     * @return
     */
    private String getShortCut(String str, int length)
    {
        if (str != null && str.length() > length)
        {
            return str.substring(0, length) + "...";
        }
        else
        {
            return str;
        }
    }
    
    private IBOAISchedTaskLogValue[] createLogValues(int start, int length)
    {
        IBOAISchedTaskLogValue[] values = {};
        values = new IBOAISchedTaskLogValue[length];
        for (int i = 0; i < length; i++)
        {
            try
            {
                values[i] = new BOAISchedTaskLogBean();
            }
            catch (AIException e)
            {
                e.printStackTrace();
            }
            values[i].setTaskCode("aaaa" + (start + i));
        }
        return values;
    }
    
    private ServerBean[] createServers()
    {
        ServerBean[] servers = new ServerBean[3];
        for (int i = 0; i < servers.length; i++)
        {
            servers[i] = new ServerBean();
            servers[i].setServerId("aa" + i);
            servers[i].setRegistTime(new Timestamp(System.currentTimeMillis()));
            servers[i].setHeartbeatTime(new Timestamp(System.currentTimeMillis()));
        }
        return servers;
    }
    
    private Collection<TaskStat> createTaskStat()
    {
        List<TaskStat> list = new ArrayList<TaskStat>();
        for (int i = 0; i < 3; i++)
        {
            TaskStat stat = new TaskStat();
            stat.setTaskCode("aaa" + i);
            list.add(stat);
        }
        return list;
    }
    
    public abstract void setTaskBaseLogsItem(JSONObject taskBaseLogsItem);
    
    public abstract void setServerDetail(JSONObject serverDetail);
    
    public abstract void setTaskParamsRowIndex(int taskParamsRowIndex);
    
    public abstract void setTaskQuery(JSONObject taskQuery);
    
    public abstract void setTaskInfosItem(JSONObject taskInfosItem);
    
    public abstract void setQueryFORM(JSONObject queryFORM);
    
    public abstract void setTaskParamsCount(long taskParamsCount);
    
    public abstract void setTaskInfosRowIndex(int taskInfosRowIndex);
    
    public abstract void setTaskServersRowIndex(int taskServersRowIndex);
    
    public abstract void setTaskProcessesItems(JSONArray taskProcessesItems);
    
    public abstract void setTaskBaseLogsRowIndex(int taskBaseLogsRowIndex);
    
    public abstract void setTaskInfosItems(JSONArray taskInfosItems);
    
    public abstract void setTaskProcessesItem(JSONObject taskProcessesItem);
    
    public abstract void setTaskParamsItems(JSONArray taskParamsItems);
    
    public abstract void setTaskParamsItem(JSONObject taskParamsItem);
    
    public abstract void setTaskProcessesCount(long taskProcessesCount);
    
    public abstract void setTaskInfosCount(long taskInfosCount);
    
    public abstract void setTaskBaseLogsCount(long taskBaseLogsCount);
    
    public abstract void setTaskBaseLogsItems(JSONArray taskBaseLogsItems);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus taskBaseLogsItem = createData("taskBaseLogsItem");
        if (taskBaseLogsItem != null && !taskBaseLogsItem.getDataObject().isEmpty())
        {
            setTaskBaseLogsItem(taskBaseLogsItem.getDataObject());
        }
        IDataBus serverDetail = createData("serverDetail");
        if (serverDetail != null && !serverDetail.getDataObject().isEmpty())
        {
            setServerDetail(serverDetail.getDataObject());
        }
        IDataBus taskQuery = createData("taskQuery");
        if (taskQuery != null && !taskQuery.getDataObject().isEmpty())
        {
            setTaskQuery(taskQuery.getDataObject());
        }
        IDataBus taskInfosItem = createData("taskInfosItem");
        if (taskInfosItem != null && !taskInfosItem.getDataObject().isEmpty())
        {
            setTaskInfosItem(taskInfosItem.getDataObject());
        }
        IDataBus queryFORM = createData("queryFORM");
        if (queryFORM != null && !queryFORM.getDataObject().isEmpty())
        {
            setQueryFORM(queryFORM.getDataObject());
        }
        IDataBus taskProcessesItem = createData("taskProcessesItem");
        if (taskProcessesItem != null && !taskProcessesItem.getDataObject().isEmpty())
        {
            setTaskProcessesItem(taskProcessesItem.getDataObject());
        }
        IDataBus taskParamsItem = createData("taskParamsItem");
        if (taskParamsItem != null && !taskParamsItem.getDataObject().isEmpty())
        {
            setTaskParamsItem(taskParamsItem.getDataObject());
        }
        initExtend(cycle);
    }
    

}
