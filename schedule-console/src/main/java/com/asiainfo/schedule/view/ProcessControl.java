package com.asiainfo.schedule.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.deploy.api.external.AppOperationUtils;
import com.asiainfo.deploy.exception.ExecuteResult;
import com.asiainfo.monitor.busi.cache.impl.Group;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.util.ConfigPageUtil;
import com.asiainfo.monitor.service.ExternalSvUtil;
import com.asiainfo.schedule.core.po.TaskGroupBean;
import com.asiainfo.schedule.core.po.TaskView;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;
import com.asiainfo.schedule.util.HeartBeatException;
import com.asiainfo.schedule.util.ScheduleConstants;
import com.asiainfo.schedule.util.ScheduleUtil;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class ProcessControl extends AppPage
{
    private static final Logger LOGGER = Logger.getLogger(ProcessControl.class);
    
    private Map<String, String> taskGroupMap;
    
    private Map<String, String> HostGroupMap;
    
    private Map<String, String> taskStateMap;
    
    static
    {
        
    }
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 根据条件查询进程信息
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryProcess(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String tableName = getContext().getParameter("formName").trim();
        if (StringUtils.isNotBlank(tableName))
        {
            IDataBus queryFormBus = createData(tableName);
            JSONObject groupInputCondition = (JSONObject)queryFormBus.getData();
            JSONObject result = new JSONObject();
            if (tableName.startsWith("Busi"))
            {
                // 调用业务查询接口
                handdleBusiQry(groupInputCondition, result, offset, linage);
            }
            else if (tableName.startsWith("host"))
            {
                // 调用主机查询接口
                handleHostQry(groupInputCondition, result, offset, linage);
            }
            else
            {
                LOGGER.error("查询出错，传入表单名错误,为：" + tableName);
            }
            this.setAjax(result);
        }
        
    }
    
    /**
     * 操作进程
     * 
     * @param cycle
     * @throws Exception
     */
    public void operateProcess(IRequestCycle cycle)
        throws Exception
    {
        String appCodes = getContext().getParameter("appCodes").trim();
        String operateId = getContext().getParameter("operationId").trim();
        // 记录成功数，失败数和失败编码
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("successNum", 0);
        map.put("failedNum", 0);
        map.put("failedCode", "");
        String heartBeatMsg = "";
        // 获取错误码对应描述
        Map<String, String> errMap = ScheduleUtil.getErrMap();
        
        if (StringUtils.isNotBlank(appCodes))
        {
            String[] appCodeArray = null;
            if (appCodes.indexOf(",") > 0)
            {// 多个则劈开
                appCodeArray = appCodes.split(",");
            }
            else
            {// 单个的话直接放入
                appCodeArray = new String[] {appCodes};
            }
            if (ScheduleConstants.PROCESS_OPERATION_START.equals(operateId))
            {
                // 记录操作日志
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_START_STOP,
                    CommonConstants.OPERATE_TYPE_START,
                    CommonConstants.OPERTATE_OBJECT_NODE,
                    appCodes,
                    null);
                
                for (String appCode : appCodeArray)
                {
                    // 调用启动进程接口
                    try
                    {
                        AppOperationUtils.startViaSSH(appCode);
                        map.put("successNum", (Integer)map.get("successNum") + 1);
                    }
                    catch (Exception e)
                    {
                        if (e instanceof HeartBeatException)
                        {
                            heartBeatMsg += appCode;
                            heartBeatMsg += "; ";
                        }
                        LOGGER.error("start process failed:" + e + ";appCode is " + appCode);
                        map.put("failedNum", (Integer)map.get("failedNum") + 1);
                        String msg = (String)map.get("failedCode");
                        msg += appCode;
                        msg += ":";
                        msg +=
                            StringUtils.isBlank(errMap.get(e.getMessage())) ? e.getMessage()
                                : errMap.get(e.getMessage());
                        msg += ";";
                        map.put("failedCode", msg);
                    }
                }
            }
            else if (ScheduleConstants.PROCESS_OPERATION_STOP.equals(operateId))
            {
                // 记录操作日志
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_START_STOP,
                    CommonConstants.OPERATE_TYPE_STOP,
                    CommonConstants.OPERTATE_OBJECT_NODE,
                    appCodes,
                    null);
                for (String appCode : appCodeArray)
                {
                    // 调用关闭进程接口
                    try
                    {
                        AppOperationUtils.stopViaSSH(appCode);
                        map.put("successNum", (Integer)map.get("successNum") + 1);
                    }
                    catch (Exception e)
                    {
                        LOGGER.error("stop process failed:" + e + ";appCode is " + appCode);
                        map.put("failedNum", (Integer)map.get("failedNum") + 1);
                        String msg = (String)map.get("failedCode");
                        msg += appCode;
                        msg += ":";
                        msg +=
                            StringUtils.isBlank(errMap.get(e.getMessage())) ? e.getMessage()
                                : errMap.get(e.getMessage());
                        msg += ";";
                        map.put("failedCode", msg);
                    }
                }
            }
            else if (ScheduleConstants.PROCESS_OPERATION_CHECK.equals(operateId))
            {
                for (String appCode : appCodeArray)
                {
                    // 调用检查进程接口
                    try
                    {
                        AppOperationUtils.isRunningViaSSH(appCode);
                        map.put("successNum", (Integer)map.get("successNum") + 1);
                    }
                    catch (Exception e)
                    {
                        LOGGER.error("checking process failed:" + e + ";appCode is " + appCode);
                        map.put("failedNum", (Integer)map.get("failedNum") + 1);
                        String msg = (String)map.get("failedCode");
                        msg += appCode;
                        msg += ":";
                        msg +=
                            StringUtils.isBlank(errMap.get(e.getMessage())) ? e.getMessage()
                                : errMap.get(e.getMessage());
                        msg += ";";
                        map.put("failedCode", msg);
                    }
                }
            }
            else
            {
                LOGGER.error("进程操作错误，操作ID为：" + operateId);
            }
            JSONObject obj = new JSONObject();
            obj.put("successNum", map.get("successNum"));
            obj.put("failedNum", map.get("failedNum"));
            obj.put("failedCode", map.get("failedCode"));
            // 心跳存在，不允许启动的应用编码，暂时不显示在页面上
            obj.put("checkHeartBeat", heartBeatMsg);
            this.setAjax(obj);
        }
    }
    
    /**
     * 显示任务详情
     * 
     * @param cycle
     * @throws Exception
     */
    public void showTaskDetail(IRequestCycle cycle)
        throws Exception
    {
        
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String appCode = getContext().getParameter("appCode").trim();
        if (null != appCode)
        {
            ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
            List<String> appCodes = new ArrayList<String>();
            appCodes.add(appCode);
            TaskView[] views = sv.getTaskViewByAppCode(appCodes);
            if (null != views && views.length > 0)
            {
                JSONArray array = new JSONArray();
                for (TaskView view : views)
                {
                    JSONObject obj = new JSONObject();
                    obj.put("appCode", appCode);
                    obj.put("groupCode", view.getGroupCode());
                    obj.put("groupName", view.getGroupName());
                    obj.put("taskCode", view.getTaskCode());
                    obj.put("taskName", view.getTaskName());
                    obj.put("version", view.getVersion());
                    obj.put("state", StringUtils.isBlank(view.getState()) ? "\u672a\u914d\u7f6e\u5b8c\u6210"
                        : getTaskStateMap().get(view.getState()));
                    obj.put("runState", view.getTaskRunSts());
                    obj.put("splitRegion", view.isSplitRegion() ? "\u662f" : "\u5426");
                    array.add(obj);
                }
                /* this.setBusiDetailItems(array); */
                
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("busiDetailItems", PagingUtil.convertArray2Page(array, offset, linage));
                jsonObject.put("total", array == null ? 0 : array.size());
                this.setAjax(jsonObject);
                
            }
        }
        else
        {
            throw new Exception("应用编码为空");
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
     * 获取主机集群下拉菜单
     * 
     * @return
     */
    public IDataBus getHostGroupList()
    {
        initHostGroupMap();
        IDataBus bus = ConfigPageUtil.getSelectList(HostGroupMap);
        return bus;
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
     * 关联展示主机
     * 
     * @param cycle
     * @throws Exception
     */
    public void showHosts(IRequestCycle cycle)
        throws Exception
    {
        String groupId = getContext().getParameter("GroupId");
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
     * 处理业务查询
     */
    private void handdleBusiQry(Map<String, String> conditions, JSONObject result, String offset, String linage)
    {
        ISchedulerSV schSv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONArray array = new JSONArray();
        List<String> tempList = new ArrayList<String>();// 记录应用编码，防止重复
        Map<String, JSONObject> tempMap = new HashMap<String, JSONObject>();// 记录数据，方便后来处理
        try
        {
            TaskView[] views =
                schSv.getTaskView(conditions.get(ScheduleConstants.PARAM_TASK_GROUP),
                    conditions.get(ScheduleConstants.PARAM_TASK_CODE),
                    conditions.get(ScheduleConstants.PARAM_TASK_NAME));
            if (views != null)
            {
                for (TaskView view : views)
                {
                    Map<String, String> map = view.getItemAppCodes();
                    // 遍历值（应用编码）
                    if (!map.isEmpty())
                    {
                        for (String s : map.values())
                        {
                            if (StringUtils.isBlank(s))
                            {
                                LOGGER.error("handdleBusiQry failed,appCode is null,taskCode is:" + view.getTaskCode());
                                continue;
                            }
                            // 若已包含应用编码
                            if (tempList.contains(s))
                            {
                                JSONObject obj = tempMap.get(s);
                                String temp = obj.getString(ScheduleConstants.PARAM_TASK_CODE);
                                if (temp != null && !temp.endsWith("..."))
                                {
                                    temp += "...";
                                    obj.put(ScheduleConstants.PARAM_TASK_CODE, temp);
                                    tempMap.put(s, obj);
                                }
                            }
                            else
                            {// 不包含则新生成放入map
                                tempList.add(s);
                                JSONObject obj = new JSONObject();
                                obj.put(ScheduleConstants.PARAM_TASK_CODE, view.getTaskCode());
                                obj.put(ScheduleConstants.PARAM_SERVER_CODE, s);
                                tempMap.put(s, obj);
                            }
                        }
                    }
                }
            }
            if (!tempMap.isEmpty())
            {
                for (JSONObject obj : tempMap.values())
                {// 遍历已有业务数据，查询添加主机数据
                    Map<String, String> param = new HashMap<String, String>();
                    param.put(ScheduleConstants.PARAM_TASK_CODE, obj.getString(ScheduleConstants.PARAM_SERVER_CODE));
                    JSONArray dataArray = ExternalSvUtil.qryServerInfoByCondition(param);
                    // 根据应用编码时只有一个主机数据（多个时取第一个）
                    if (null != dataArray && dataArray.size() > 0)
                    {
                        JSONObject tempObj = (JSONObject)dataArray.get(0);
                        String appCode = (String)obj.get(ScheduleConstants.PARAM_SERVER_CODE);
                        tempObj.put(ScheduleConstants.PARAM_SERVER_CODE, appCode);
                        tempObj.put(ScheduleConstants.PARAM_TASK_CODE, obj.get(ScheduleConstants.PARAM_TASK_CODE));
                        // 查询状态
                        Map<String, List<String>> params = new HashMap<String, List<String>>();
                        List<String> list = new ArrayList<String>();// 保存查询用的所有应用编码
                        list.add(appCode);
                        params.put(tempObj.getString(ScheduleConstants.PARAM_HOST_ID), list);
                        
                        array.add(tempObj);
                    }
                    else
                    {
                        array.add(obj);
                    }
                }
            }
            if (!tempList.isEmpty())
            {
                // 批量查询进程状态
                Map<String, ExecuteResult> stateMap = AppOperationUtils.batchIsRunningViaSocket(tempList);
                String state = null;
                for (Object tempObj : array)
                {
                    JSONObject tempJson = (JSONObject)tempObj;
                    ExecuteResult stateResult = stateMap.get(tempJson.getString(ScheduleConstants.PARAM_SERVER_CODE));
                    if (stateResult == null || !stateResult.isSuccess())
                    {
                        state = "\u67e5\u8be2\u5f02\u5e38";
                        if (stateResult == null)
                        {
                            LOGGER.error("qry state failed,result is null");
                        }
                        else
                        {
                            LOGGER.error("qry state failed,error code is " + stateResult.getErrorCode()
                                + ",message is " + stateResult.getMessage());
                        }
                    }
                    else
                    {
                        state = (Boolean)stateResult.getMessage() ? "\u8fd0\u884c\u4e2d" : "\u5df2\u505c\u6b62";
                    }
                    tempJson.put(ScheduleConstants.PARAM_PROCESS_STATE, state);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("handdleBusiQry failed", e);
        }
        result.put("total", array == null ? 0 : array.size());
        /* this.setProcessTableItems(array); */
        result.put("processTableItems", PagingUtil.convertArray2Page(array, offset, linage));
    }
    
    /**
     * 处理主机查询
     * 
     * @throws Exception
     */
    private void handleHostQry(Map<String, String> conditions, JSONObject result, String offset, String linage)
        throws Exception
    {
        ISchedulerSV schSv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONArray dataArray = null;
        List<String> appCodes = new ArrayList<String>();
        dataArray = ExternalSvUtil.qryServerInfoByCondition(conditions);
        if (dataArray != null)
        {
            for (Object obj : dataArray)
            {
                JSONObject jsObj = (JSONObject)obj;
                appCodes.add(jsObj.getString(ScheduleConstants.PARAM_SERVER_CODE));
            }
        }
        TaskView[] views = schSv.getTaskViewByAppCode(appCodes);
        Map<String, String> transMap = new HashMap<String, String>();// 转化view以便下面拼接属性
        if (views != null)
        {
            for (TaskView view : views)
            {
                Map<String, String> map = view.getItemAppCodes();
                // 遍历view后将同APPCODE的整合
                for (String str : map.values())
                {
                    if (transMap.containsKey(str))
                    {
                        String temp = transMap.get(str);
                        if (temp != null && !temp.endsWith("..."))
                        {
                            temp += "...";
                            transMap.put(str, temp);
                        }
                    }
                    else
                    {
                        transMap.put(str, view.getTaskCode());
                    }
                }
            }
        }
        
        if (dataArray != null)
        {
            for (Object obj : dataArray)
            {
                JSONObject jsObj = (JSONObject)obj;
                String appCode = jsObj.getString(ScheduleConstants.PARAM_SERVER_CODE);
                jsObj.put(ScheduleConstants.PARAM_TASK_CODE, transMap.get(appCode));
            }
        }
        if (!appCodes.isEmpty())
        {
            // 批量查询进程状态
            Map<String, ExecuteResult> stateMap = AppOperationUtils.batchIsRunningViaSocket(appCodes);
            String state = null;
            for (Object tempObj : dataArray)
            {
                JSONObject tempJson = (JSONObject)tempObj;
                ExecuteResult stateResult = stateMap.get(tempJson.getString(ScheduleConstants.PARAM_SERVER_CODE));
                if (stateResult == null || !stateResult.isSuccess())
                {
                    state = "\u67e5\u8be2\u5f02\u5e38";
                    if (stateResult == null)
                    {
                        LOGGER.error("qry state failed,result is null");
                    }
                    else
                    {
                        LOGGER.error("qry state failed,error code is " + stateResult.getErrorCode() + ",message is "
                            + stateResult.getMessage());
                    }
                }
                else
                {
                    state = (Boolean)stateResult.getMessage() ? "\u8fd0\u884c\u4e2d" : "\u5df2\u505c\u6b62";
                }
                tempJson.put(ScheduleConstants.PARAM_PROCESS_STATE, state);
            }
        }
        result.put("total", dataArray == null ? 0 : dataArray.size());
        /* this.setProcessTableItems(dataArray); */
        result.put("processTableItems", PagingUtil.convertArray2Page(dataArray, offset, linage));
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
     * 初始化主机集群map
     */
    private void initHostGroupMap()
    {
        HostGroupMap = new HashMap<String, String>();
        Map<String, Group> map = null;
        try
        {
            // map = CacheFactory.getAll(AIMonGroupCacheImpl.class);
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
                    HostGroupMap.put(String.valueOf(group.getGroupId()), group.getGroupName());
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("get host group failed,exception is " + e);
        }
    }
    
    private Map<String, String> getTaskStateMap()
    {
        if (null == taskStateMap)
        {
            taskStateMap = new HashMap<String, String>();
            taskStateMap.put("U", "\u53ef\u7528");
            taskStateMap.put("E", "\u672a\u914d\u7f6e\u5b8c\u6210");
        }
        return taskStateMap;
    }
    
    public abstract void setHostQueryForm(JSONObject hostQueryForm);
    
    public abstract void setBusiDetailItem(JSONObject busiDetailItem);
    
    public abstract void setProcessTableRowIndex(int processTableRowIndex);
    
    public abstract void setProcessTableCount(long processTableCount);
    
    public abstract void setProcessTableItems(JSONArray processTableItems);
    
    public abstract void setBusiQueryForm(JSONObject BusiQueryForm);
    
    public abstract void setBusiDetailItems(JSONArray busiDetailItems);
    
    public abstract void setBusiDetailRowIndex(int busiDetailRowIndex);
    
    public abstract void setBusiDetailCount(long busiDetailCount);
    
    public abstract void setProcessTableItem(JSONObject processTableItem);
    
    @Override
    protected void initPageAttrs()
    {
        
        bindBoName("busiDetail", "com.asiainfo.common.abo.bo.BOABGMonBusiErrorLog");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus hostQueryForm = createData("hostQueryForm");
        if (hostQueryForm != null && !hostQueryForm.getDataObject().isEmpty())
        {
            setHostQueryForm(hostQueryForm.getDataObject());
        }
        IDataBus busiDetailItem = createData("busiDetailItem");
        if (busiDetailItem != null && !busiDetailItem.getDataObject().isEmpty())
        {
            setBusiDetailItem(busiDetailItem.getDataObject());
        }
        IDataBus BusiQueryForm = createData("BusiQueryForm");
        if (BusiQueryForm != null && !BusiQueryForm.getDataObject().isEmpty())
        {
            setBusiQueryForm(BusiQueryForm.getDataObject());
        }
        IDataBus processTableItem = createData("processTableItem");
        if (processTableItem != null && !processTableItem.getDataObject().isEmpty())
        {
            setProcessTableItem(processTableItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
