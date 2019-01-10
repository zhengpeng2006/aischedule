package com.asiainfo.schedule.view;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.monitor.exeframe.configmgr.util.ConfigPageUtil;
import com.asiainfo.resultConstant.ResultConstants;
import com.asiainfo.schedule.core.po.TaskBean;
import com.asiainfo.schedule.core.po.TaskGroupBean;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;
import com.asiainfo.schedule.util.ScheduleConstants;
import com.asiainfo.schedule.util.ScheduleUtil;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
/**
 * 
 *
 * @author dingpk 75499
 * @date 2016年9月19日 上午11:28:10
 */
public abstract class ScheduleTaskCfg extends AppPage
{
    
    private static final Logger LOGGER = Logger.getLogger(ScheduleTaskCfg.class);
    
    /** 页面操作类型：修改 */
    private static final String OPERATION_UPDATE = "update";
    
    /** 页面操作类型：新增 */
    private static final String OPERATION_ADD = "add";
    
    /** 页面静态数据map */
    private static Map<String, Map<String, String>> staticDataMap;
    
    private Map<String, String> taskGroupMap = new HashMap<String, String>();;
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
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
     * 获取任务类型下拉菜单
     * 
     * @return
     */
    public IDataBus getTaskTypeList()
    {
        IDataBus bus = ConfigPageUtil.getSelectList(getStaticDataMap().get("taskType"));
        return bus;
    }
    
    /**
     * 获取处理方式下拉菜单
     * 
     * @return
     */
    public IDataBus getFaultProcessMethodList()
    {
        IDataBus bus = ConfigPageUtil.getSelectList(getStaticDataMap().get("faultProcessMethod"));
        return bus;
    }
    
    /**
     * 获取任务类型下拉菜单 现在用于此页面 是\否 类型的下来
     * 
     * @return
     */
    public IDataBus getSplitRegionList()
    {
        IDataBus bus = ConfigPageUtil.getSelectList(getStaticDataMap().get("splitRegion"));
        return bus;
    }
    
    /**
     * 重新初始化分组
     * 
     * @param request
     * @throws Exception
     */
    public void reInitGroup(IRequestCycle request)
        throws Exception
    {
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        TaskGroupBean[] beans = sv.getAllTaskGroup();
        JSONArray array = new JSONArray();
        if (beans != null)
        {
            for (TaskGroupBean bean : beans)
            {
                JSONObject obj = new JSONObject();
                obj.put("key", bean.getGroupCode());
                obj.put("value", bean.getGroupName());
                array.add(obj);
            }
        }
        this.setAjax(array);
    }
    
    /**
     * 查询任务，详情，分片应用
     * 
     * @param request
     * @throws Exception
     */
    public void qryTask(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        IDataBus queryFormBus = createData("queryForm");
        JSONObject conditions = (JSONObject)queryFormBus.getData();
        ISchedulerSV schSv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        // 查询任务
        TaskBean[] tasks =
            schSv.getAllTasks(conditions.getString(ScheduleConstants.PARAM_TASK_GROUP),
                conditions.getString(ScheduleConstants.PARAM_TASK_CODE),
                conditions.getString(ScheduleConstants.PARAM_TASK_NAME),
                conditions.getString(ScheduleConstants.PARAM_TASK_TYPE),
                conditions.getString(ScheduleConstants.PARAM_TASK_STATE));
        JSONArray array = new JSONArray();
        
        // 状态
        if (tasks != null)
        {
            // 初始化一次分组map
            initTaskGroupMap();
			if (StringUtils.isNotBlank(conditions.getString("taskSplit"))) {
				for (TaskBean view : tasks) {
					if (hasTaskSplitCode(schSv.getAllServersByTaskCode(view.getTaskCode()), conditions.getString("taskSplit"))) {
						array.add(trans2JSON(view));
					}
				}
			} else {
				for (TaskBean view : tasks) {
					view.setItems(ScheduleUtil.sortItemsByIntger(view.getItems()));
					array.add(trans2JSON(view));
				}
			}
        }
        // this.setTaskTableItems(array);
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("taskTableItems", PagingUtil.convertArray2Page(array, offset, linage));
        jsonObject.put("taskTableItemsTotal", array.size());
        this.setAjax(jsonObject);
    }
	
	private boolean hasTaskSplitCode(Map<String, String> serverMap, String taskSplitCode) {
		if (null != serverMap) {
			for (String item : serverMap.keySet()) {
				if (StringUtils.isNotBlank(serverMap.get(item)) && serverMap.get(item).contains(taskSplitCode))
					return true;
			}
		}
		return false;
	}
    
    public void qryTaskName(IRequestCycle request)
            throws Exception
        {
            IDataBus queryFormBus = createData("queryForm");
            JSONObject conditions = (JSONObject)queryFormBus.getData();
            ISchedulerSV schSv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
            // 查询任务
            TaskBean[] tasks =
                schSv.getAllTasks(conditions.getString(ScheduleConstants.PARAM_TASK_GROUP),
                    conditions.getString(ScheduleConstants.PARAM_TASK_CODE),
                    conditions.getString(ScheduleConstants.PARAM_TASK_NAME),
                    conditions.getString(ScheduleConstants.PARAM_TASK_TYPE),
                    conditions.getString(ScheduleConstants.PARAM_TASK_STATE));
            JSONArray array = new JSONArray();
            // 状态
            if (tasks != null)
            {
                for (TaskBean view : tasks)
                {
                    JSONObject obj = trans2JSONTaskName(view);
                    array.add(obj);
                }
            }
            JSONObject jsonObject = new JSONObject();
            
            jsonObject.put("taskNames", array);
            this.setAjax(jsonObject);
        }
    
    /**
     * 删除任务
     * 
     * @param request
     * @throws Exception
     */
    public void deleteTask(IRequestCycle request)
        throws Exception
    {
        String taskCode = getContext().getParameter("taskCode").trim();
        JSONObject obj = new JSONObject();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        Map<String, String> retMap = new HashMap<String, String>();
        if (StringUtils.isNotBlank(taskCode))
        {
            try
            {
                sv.deleteTask(taskCode);
                retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
            }
            catch (Exception e)
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", e.getMessage());
                LOGGER.error("delete task failed,reason is" + e + ";taskCode is " + taskCode);
            }
            finally
            {
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                    CommonConstants.OPERATE_TYPE_DELETE,
                    CommonConstants.OPERTATE_OBJECT_TASK,
                    taskCode,
                    null);
            }
        }
        else
        {
            retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
            retMap.put("msg", "task code is null");
            LOGGER.error("delete task failed,task code is null");
        }
        obj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("flag"));
        obj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(obj);
    }
    
    /**
     * 保存任务 未完成
     * 
     * @param request
     * @throws Exception
     */
    public void saveTask(IRequestCycle request)
        throws Exception
    {
        IDataBus taskInputBus = createData("taskInfoData");
        JSONObject data = taskInputBus.getDataObject();
        String operation = getContext().getParameter("operation").trim();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject result = new JSONObject();
        try
        {
            TaskBean task = trans2Task(data);
            String[] items = task.getItems();
            StringBuffer strBuffer = new StringBuffer();
            String str = "";
            if (items != null && items.length > 0)
            {
                for (String item : items)
                {
                    strBuffer.append(item);
                }
                str = strBuffer.toString();
                boolean bStr = XssUtil.checkString(str);
                if (!bStr)
                {
                    result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                    result.put(ResultConstants.ResultKey.RESULT_MSG, ResultConstants.ResultCodeValue.CHECK_FAILED);
                }
            }
            if (OPERATION_UPDATE.equalsIgnoreCase(operation))
            {
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                    CommonConstants.OPERATE_TYPE_MODIFY,
                    CommonConstants.OPERTATE_OBJECT_TASK,
                    task.getTaskCode(),
                    "修改基础任务信息");
                // 如果result的值是I的话，说明item校验出错
                if (!ResultConstants.ResultCodeValue.FAILED.equals(result.get(ResultConstants.ResultKey.RESULT_CODE)))
                {
                    // 保存任务信息之前要先进行校验
                    boolean b = XssUtil.checkBeforeSave(task, task.getClass(), null);
                    if (b)
                    {
                        sv.updateTask(task);
                        result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
                    }
                    else
                    {
                        result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                        result.put(ResultConstants.ResultKey.RESULT_MSG, ResultConstants.ResultCodeValue.CHECK_FAILED);
                    }
                }
            }
            else if (OPERATION_ADD.equalsIgnoreCase(operation))
            {
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                    CommonConstants.OPERATE_TYPE_ADD,
                    CommonConstants.OPERTATE_OBJECT_TASK,
                    task.getTaskCode(),
                    "新增基础任务信息");
                if (!ResultConstants.ResultCodeValue.FAILED.equals(result.get(ResultConstants.ResultKey.RESULT_CODE)))
                {
                    // 保存任务信息之前要先进行校验
                    boolean b = XssUtil.checkBeforeSave(task, task.getClass(), null);
                    if (b)
                    {
                        sv.createTask(task);
                        result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
                    }
                    else
                    {
                        result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
                        result.put(ResultConstants.ResultKey.RESULT_MSG, ResultConstants.ResultCodeValue.CHECK_FAILED);
                    }
                }
            }
            else
            {
                LOGGER.error("save task failed,reason is wrong operation;operation is" + operation);
                result.put("msg", "operation is wrong");
                result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
            }
            
        }
        catch (Exception e)
        {
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("save task failed,reason is " + e + ";data is " + data);
        }
        this.setAjax(result);
    }
    
    /**
     * 是任务生效
     * 
     * @param request
     * @throws Exception
     */
    public void enable(IRequestCycle request)
        throws Exception
    {
        String taskCode = getContext().getParameter("taskCode").trim();
        // 调用生效接口
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject result = new JSONObject();
        try
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_TASK,
                taskCode,
                "使任务生效");
            sv.taskEffect(taskCode);
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
        }
        catch (Exception e)
        {
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("enable task failed,reason is" + e + ";taskCode is " + taskCode);
        }
        this.setAjax(result);
    }
    
    /**
     * 查询任务详细信息 备注：改用pageui后舍弃，整合在qryTask中
     * 
     * @param request
     * @throws Exception
     */
    @Deprecated
    public void qryTaskDetail(IRequestCycle request)
        throws Exception
    {
        String taskCode = getContext().getParameter("taskCode").trim();
        String operation = getContext().getParameter("operation").trim();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        TaskBean task = sv.getTaskInfoByTaskCode(taskCode);
        JSONObject obj = trans2JSON(task, operation);
        // 返回给页面的值
        JSONObject returnObj = new JSONObject();
        returnObj.put("splitRegion", task.isSplitRegion() + "");
        returnObj.put("item", task.getItems() == null ? "" : array2String(task.getItems()));
        returnObj.put("type", task.getTaskType());
        // 如果是修改操作的前置操作,目标为编辑任务信息区域;否则目标为详情区域
        if (OPERATION_UPDATE.equalsIgnoreCase(operation))
        {
            // this.setTaskInfoData(obj);
            returnObj.put("taskInfoData", obj);
        }
        else
        {
            // 先将基本信息赋值给基本信息区域
            // this.setTaskDtlView(obj);
            returnObj.put("taskDtlView", obj);
            
            // 查询分片信息后赋值给分片区域
            JSONArray splitArray = new JSONArray();
            String[] items = sv.getTaskItemsByTaskCode(taskCode);
            JSONObject splitObj = new JSONObject();
            if (items != null)
            {
                for (String item : items)
                {
                    splitObj.put("taskCode", taskCode);
                    splitObj.put("taskItem", item);
                    splitObj.put("assignServer", sv.getServerCode(taskCode, item));
                    splitArray.add(splitObj);
                }
            }
            // this.setSplitViewItems(splitArray);
            returnObj.put("splitViewItems", splitArray);
            returnObj.put("total", splitArray.size());
        }
        
        this.setAjax(returnObj);
    }
    
    /**
     * 转换是否记日志
     * 
     * @param flag
     * @return
     */
    public String transLog(String flag)
    {
        return getStaticDataMap().get("splitRegion").get(flag);
    }
    
    /**
     * 获得静态数据map
     * 
     * @return
     */
    private Map<String, Map<String, String>> getStaticDataMap()
    {
        if (null == staticDataMap)
        {
            initStaticDataMap();
        }
        return staticDataMap;
    }
    
    /**
     * 初始化任务分组map
     */
    private void initTaskGroupMap()
    {
        ISchedulerSV schSv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        taskGroupMap.clear();
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
     * 初始化静态数据map
     */
    private void initStaticDataMap()
    {
        staticDataMap = new HashMap<String, Map<String, String>>();
        // 加载任务类型map
        Map<String, String> taskTypeMap = new HashMap<String, String>();
        taskTypeMap.put("single", "single");
        taskTypeMap.put("complex", "complex");
        taskTypeMap.put("reload", "reload");
        taskTypeMap.put("tf", "tf");
        staticDataMap.put("taskType", taskTypeMap);
        // 加载故障处理方式map
        Map<String, String> faultProcessMethodMap = new HashMap<String, String>();
        faultProcessMethodMap.put("M", "\u4eba\u5de5\u5904\u7406");
        faultProcessMethodMap.put("A", "\u81ea\u52a8\u5904\u7406");
        staticDataMap.put("faultProcessMethod", faultProcessMethodMap);
        // 加载任务状态map
        Map<String, String> taskStateMap = new HashMap<String, String>();
        taskStateMap.put("U", "\u53ef\u7528");
        taskStateMap.put("E", "\u672a\u542f\u7528");
        staticDataMap.put("taskState", taskStateMap);
        // 是否地区分片map
        Map<String, String> splitRegionMap = new HashMap<String, String>();
        splitRegionMap.put("false", "\u5426");
        splitRegionMap.put("true", "\u662f");
        staticDataMap.put("splitRegion", splitRegionMap);
    }
    
    /**
     * 将前台数据转换成taskBean
     * 
     * @param obj
     * @param taskGroupCode
     * @return
     */
    private TaskBean trans2Task(JSONObject obj)
    {
        TaskBean task = null;
        if (null != obj && !obj.isEmpty())
        {
            task = new TaskBean();
            task.setTaskCode(obj.getString("taskCode"));
            task.setTaskName(obj.getString("taskName"));
            task.setTaskDesc(obj.getString("taskDesc"));
            task.setTaskGroupCode(obj.getString("taskGroup"));
            task.setTaskType(obj.getString("taskType"));
            task.setStartTime(obj.getString("startTime"));
            task.setEndTime(obj.getString("endTime"));
            task.setScanIntervalTime(obj.getInt("scanIntervalTime"));
            task.setScanNum(obj.getInt("scanNum"));
            task.setExecuteNum(obj.getInt("executeNum"));
            task.setThreadNum(obj.getInt("threadNum"));
            task.setIdleSleepTime(obj.getInt("idleSleepTime"));
            task.setProcessClass(obj.getString("processClass"));
            task.setSplitRegion(Boolean.parseBoolean(obj.getString("splitRegion")));
            String tempStr = obj.getString("items");
            String[] items = null;
            if (StringUtils.isNotBlank(tempStr))
            {
                if (tempStr.indexOf(",") > 0)
                {
                    items = tempStr.split(",");
                }
                else
                {
                    items = new String[] {tempStr};
                }
            }
            
            try
            {
                task.setState(obj.getString("state"));
            }
            catch (JSONException e)
            {
                // 新增时无state，正常场景，不抛异常
            }
            task.setItems(items);
            task.setFaultProcessMethod(obj.getString("faultProcessMethod"));
            task.setCreateTime(new Timestamp(System.currentTimeMillis()));
            if (StringUtils.isNotBlank(obj.getString("priority")))
            {
                task.setPriority(Integer.parseInt(obj.getString("priority")));
            }
            else
            {// 默认为最小 0
                task.setPriority(0);
            }
            task.setLog(obj.getBoolean("isLog"));
        }
        return task;
    }
    
    /**
     * 将task根据操作转换个json
     * 
     * @param task
     * @param operation
     * @return
     */
    private JSONObject trans2JSON(TaskBean task, String operation)
    {
        JSONObject obj = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (null != task)
        {
            obj.put("taskCode", task.getTaskCode());
            obj.put("taskName", task.getTaskName());
            obj.put("taskDesc", task.getTaskDesc());
            obj.put("taskType", getStaticDataMap().get("taskType").get(task.getTaskType()));
            obj.put("taskGroup", task.getTaskGroupCode());
            obj.put("startTime", task.getStartTime());
            obj.put("endTime", task.getEndTime());
            obj.put("scanIntervalTime", task.getScanIntervalTime());
            obj.put("scanNum", task.getScanNum());
            obj.put("executeNum", task.getExecuteNum());
            obj.put("threadNum", task.getThreadNum());
            obj.put("idleSleepTime", task.getIdleSleepTime());
            obj.put("processClass", task.getProcessClass());
            // 修改时为下拉菜单，无需转换
            if (OPERATION_UPDATE.equalsIgnoreCase(operation))
            {
                obj.put("splitRegion", task.isSplitRegion() + "");
                obj.put("faultProcessMethod", task.getFaultProcessMethod());
            }
            else
            {
                obj.put("splitRegion", task.isSplitRegion() ? "\u662f" : "\u5426");
                obj.put("faultProcessMethod",
                    getStaticDataMap().get("faultProcessMethod").get(task.getFaultProcessMethod()));
            }
            
            obj.put("items", task.getItems() == null ? null : array2String(task.getItems()));
            obj.put("createTime", sdf.format(task.getCreateTime()));
            obj.put("state", task.getState());
            obj.put("isLog", task.isLog());
            obj.put("priority", task.getPriority());
        }
        return obj;
    }
    
    /**
     * 任务转换成json
     * 
     * @param task
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
            obj.put("taskType", getStaticDataMap().get("taskType").get(task.getTaskType()));
            obj.put("taskGroup", task.getTaskGroupCode());
            obj.put("taskGroupName", taskGroupMap.get(task.getTaskGroupCode()));
            obj.put("startTime", task.getStartTime());
            obj.put("endTime", task.getEndTime());
            obj.put("scanIntervalTime", task.getScanIntervalTime());
            obj.put("scanNum", task.getScanNum());
            obj.put("executeNum", task.getExecuteNum());
            obj.put("threadNum", task.getThreadNum());
            obj.put("idleSleepTime", task.getIdleSleepTime());
            obj.put("processClass", task.getProcessClass());
            
            obj.put("splitRegion", task.isSplitRegion() + "");
            obj.put("faultProcessMethod", task.getFaultProcessMethod());
            
            obj.put("items", task.getItems() == null ? null : array2String(task.getItems()));
            obj.put("createTime", sdf.format(task.getCreateTime()));
            obj.put("state", task.getState());
            obj.put("isLog", task.isLog());
            obj.put("priority", task.getPriority());
            obj.put("version", task.getVersion());
            
        }
        return obj;
    }
    
    private JSONObject trans2JSONTaskName(TaskBean task)
    {
        JSONObject obj = new JSONObject();
        if (null != task)
        {
            obj.put("taskCode", task.getTaskCode());
            obj.put("taskName", task.getTaskName());
        }
        return obj;
    }
    
    public abstract void setSplitViewItem(JSONObject splitViewItem);
    
    public abstract void setSplitViewItems(JSONArray splitViewItems);
    
    public abstract void setTaskDtlView(JSONObject taskDtlView);
    
    public abstract void setTaskTableItems(JSONArray taskTableItems);
    
    public abstract void setTaskInfoData(JSONObject taskInfoData);
    
    public abstract void setSplitViewRowIndex(int splitViewRowIndex);
    
    public abstract void setTaskTableCount(long taskTableCount);
    
    public abstract void setTaskTableItem(JSONObject taskTableItem);
    
    public abstract void setTaskTableRowIndex(int taskTableRowIndex);
    
    public abstract void setQueryForm(JSONObject queryForm);
    
    public abstract void setSplitViewCount(long splitViewCount);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus splitViewItem = createData("splitViewItem");
        if (splitViewItem != null && !splitViewItem.getDataObject().isEmpty())
        {
            setSplitViewItem(splitViewItem.getDataObject());
        }
        IDataBus taskDtlView = createData("taskDtlView");
        if (taskDtlView != null && !taskDtlView.getDataObject().isEmpty())
        {
            setTaskDtlView(taskDtlView.getDataObject());
        }
        IDataBus taskInfoData = createData("taskInfoData");
        if (taskInfoData != null && !taskInfoData.getDataObject().isEmpty())
        {
            setTaskInfoData(taskInfoData.getDataObject());
        }
        IDataBus taskTableItem = createData("taskTableItem");
        if (taskTableItem != null && !taskTableItem.getDataObject().isEmpty())
        {
            setTaskTableItem(taskTableItem.getDataObject());
        }
        IDataBus queryForm = createData("queryForm");
        if (queryForm != null && !queryForm.getDataObject().isEmpty())
        {
            setQueryForm(queryForm.getDataObject());
        }
        initExtend(cycle);
    }
    
}
