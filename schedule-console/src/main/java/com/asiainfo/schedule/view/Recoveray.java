package com.asiainfo.schedule.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.service.ExternalSvUtil;
import com.asiainfo.schedule.core.po.FaultBean;
import com.asiainfo.schedule.core.po.TaskView;
import com.asiainfo.schedule.core.utils.DateUtils;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;
import com.asiainfo.schedule.util.ScheduleConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class Recoveray extends AppPage
{
    private static final Logger LOGGER = Logger.getLogger(Recoveray.class);
    
    private static Map<String, Map<String, Object>> staticMap;
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 初始化页面
     * 
     * @param cycle
     * @throws Exception
     */
    public void init(IRequestCycle cycle)
        throws Exception
    {
        qrySwitchState(cycle);
        qryErrors(cycle);
    }
    
    /**
     * 更改开关
     * 
     * @param cycle
     * @throws Exception
     */
    public void changeSwitch(IRequestCycle cycle)
        throws Exception
    {
        String oper = getContext().getParameter("oper").trim();
        JSONObject result = new JSONObject();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        
        try
        {
            if ("on".equalsIgnoreCase(oper))
            {
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_SCHED,
                    CommonConstants.OPERATE_TYPE_MODIFY,
                    CommonConstants.OPERTATE_OBJECT_TASK,
                    "Automatic switching",
                    "打开自动处理开关");
                sv.enableAutoFault();
                qrySwitchState(cycle);
            }
            else if ("off".equalsIgnoreCase(oper))
            {
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_SCHED,
                    CommonConstants.OPERATE_TYPE_MODIFY,
                    CommonConstants.OPERTATE_OBJECT_TASK,
                    "Automatic switching",
                    "关闭自动处理开关");
                sv.diableAutoFault();
                qrySwitchState(cycle);
            }
            else
            {
                LOGGER.error("changeSwitch failed, wrong operation;operation is " + oper);
                result.put("flag", "F");
                result.put("msg", "wrong operation");
            }
        }
        catch (Exception e)
        {
            LOGGER.error("changeSwitch failed", e);
            result.put("flag", "F");
            result.put("msg", e.getMessage());
        }
        this.setAjax(result);
    }
    
    /**
     * 查询故障
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryErrors(IRequestCycle cycle)
        throws Exception
    {
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONArray array = new JSONArray();
        JSONObject result = new JSONObject();
        try
        {
            FaultBean[] faults = sv.getAllFaultServer();
            if (faults != null)
            {
                Set<String> serverCodes = new HashSet<String>();
                for (FaultBean faultBean : faults)
                {
                    JSONObject obj = new JSONObject();
                    obj.put("faultServerId", transStr(faultBean.getFaultServerId()));
                    obj.put("faultTime", transStr(faultBean.getFaultTime()));
                    if (transStr(faultBean.getMsg()).length() > 50)
                    {
                        obj.put("msgShort", transStr(faultBean.getMsg()).substring(0, 50) + "...");
                    }
                    else
                    {
                        obj.put("msgShort", transStr(faultBean.getMsg()));
                    }
                    obj.put("msg", transStr(faultBean.getMsg()));
                    obj.put("bakServerId", transStr(faultBean.getBakServerId()));
                    serverCodes.add(obj.getString("faultServerId"));
                    serverCodes.add(obj.getString("bakServerId"));
                    obj.put("lastProcessTime", faultBean.getLastProcessTime());
                    obj.put("processCount", faultBean.getProcessCount());
                    obj.put("processType", transFaultProcessMethod(faultBean.getProcessType()));
                    array.add(obj);
                }
                
                JSONArray hostInfos = ExternalSvUtil.qryServersInfoByServerCodes(serverCodes);
                Map<String, String> hostMap = new HashMap<String, String>();
                for (Object object : hostInfos)
                {
                    JSONObject obj = (JSONObject)object;
                    hostMap.put(obj.getString(ScheduleConstants.PARAM_SERVER_CODE),
                        obj.getString(ScheduleConstants.PARAM_HOST_NAME));
                }
                // 转换主机信息
                for (Object object : array)
                {
                    JSONObject obj = (JSONObject)object;
                    String faultServerId = obj.getString("faultServerId");
                    String bakServerId = obj.getString("bakServerId");
                    obj.put("faultServerName", hostMap.get(faultServerId) == null ? "" : hostMap.get(faultServerId));
                    obj.put("bakServerName", hostMap.get(bakServerId) == null ? "" : hostMap.get(bakServerId));
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("qryErrors failed", e);
            result.put("flag", "F");
            result.put("msg", e.getMessage());
        }
        this.setErrorsItems(array);
        this.setAjax(result);
    }
    
    /**
     * 查询开关状态
     */
    public void qrySwitchState(IRequestCycle cycle)
        throws Exception
    {
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        String state = sv.getFaultFlag();
        JSONObject obj = new JSONObject();
        obj.put("state", getStaticMap().get("switchState").get(state));
        obj.put("flag", getStaticMap().get("switch").get(state));
        this.setSwitchFalg(obj);
    }
    
    /**
     * 恢复一条故障
     * 
     * @param cycle
     * @throws Exception
     */
    public void recover(IRequestCycle cycle)
        throws Exception
    {
        String faultServerId = getContext().getParameter("faultServerId").trim();
        JSONObject result = new JSONObject();
        if (StringUtils.isNotBlank(faultServerId))
        {
            ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_SCHED,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_TASK,
                "faultServerId:" + faultServerId,
                "恢复故障");
            try
            {
                sv.faultRecovery(faultServerId);
            }
            catch (Exception e)
            {
                LOGGER.error("recover failed", e);
                result.put("flag", "F");
                result.put("msg", e.getMessage());
            }
        }
        this.setAjax(result);
    }
    
    /**
     * 展示故障下任务切换情况
     * 
     * @param cycle
     * @throws Exception
     */
    public void showTaskInfo(IRequestCycle cycle)
        throws Exception
    {
        String serverIds = getContext().getParameter("serverIds").trim();
        JSONArray array = new JSONArray();
        List<String> appCodes = new ArrayList<String>();
        if (StringUtils.isNotBlank(serverIds))
        {
            ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
            // 正常数据是两个serverId
            if (serverIds.indexOf(",") > 0)
            {
                String[] ids = serverIds.split(",");
                for (String str : ids)
                {
                    if (StringUtils.isNotBlank(str))
                    {
                        appCodes.add(str.trim());
                    }
                }
            }
            else
            {
                appCodes.add(serverIds.trim());
            }
            TaskView[] views = sv.getTaskViewByAppCode(appCodes);
            array = view2json(views, array);
        }
        this.setTaskInfosItems(array);
    }
    
    /**
     * 转换处理方式
     * 
     * @param str
     * @return
     */
    public String transFaultProcessMethod(String str)
    {
        return (String)getStaticMap().get("faultProcessMethod").get(str);
    }
    
    /**
     * 转换属性 空转为“”
     * 
     * @param str
     * @return
     */
    private String transStr(String str)
    {
        return str == null ? "" : str;
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
            staticMap = new HashMap<String, Map<String, Object>>();
            Map<String, Object> switchMap = new HashMap<String, Object>();
            switchMap.put("on", 1);
            switchMap.put("off", 2);
            staticMap.put("switch", switchMap);
            Map<String, Object> switchStateMap = new HashMap<String, Object>();
            switchStateMap.put("on", "\u5f00");
            switchStateMap.put("off", "\u5173");
            staticMap.put("switchState", switchStateMap);
            Map<String, Object> faultProcessMethodMap = new HashMap<String, Object>();
            faultProcessMethodMap.put("M", "\u4eba\u5de5\u5904\u7406");
            faultProcessMethodMap.put("A", "\u81ea\u52a8\u5904\u7406");
            faultProcessMethodMap.put("F", "\u5904\u7406\u7ed3\u675f");
            staticMap.put("faultProcessMethod", faultProcessMethodMap);
        }
        return staticMap;
    }
    
    /**
     * 将taskViews转换成JSONArray
     * 
     * @param views
     * @param array
     * @param serverId
     * @return
     */
    private JSONArray view2json(TaskView[] views, JSONArray array)
    {
        if (views != null)
        {
            for (TaskView view : views)
            {
                Map<String, String> map = view.getItemAppCodes();
                if (map != null && !map.isEmpty())
                {
                    List<String> keys = new ArrayList<String>(map.keySet());
                    // 所有应用编码都一样 故只取一个
                    String serverId = map.get(keys.get(0));
                    for (String str : keys)
                    {
                        JSONObject obj = new JSONObject();
                        obj.put("serverId", serverId);
                        obj.put("taskCode", view.getTaskCode());
                        obj.put("version", view.getVersion());
                        obj.put("item", str);
                        obj.put("createTime", DateUtils.formatYYYYMMddHHmmss(view.getCreateTime()));
                        obj.put("startTime", view.getStartTime());
                        obj.put("endTime", view.getEndTime());
                        obj.put("taskRunSts", view.getTaskRunSts());
                        obj.put("faultProcessMethod",
                            getStaticMap().get("faultProcessMethod").get(view.getFaultProcessMethod()));
                        array.add(obj);
                    }
                }
            }
        }
        return array;
    }
    
    public abstract void setTaskInfosRowIndex(int TaskInfosRowIndex);
    
    public abstract void setErrorsRowIndex(int errorsRowIndex);
    
    public abstract void setTaskInfosItems(JSONArray TaskInfosItems);
    
    public abstract void setErrorsCount(long errorsCount);
    
    public abstract void setErrorsItem(JSONObject errorsItem);
    
    public abstract void setTaskInfosCount(long TaskInfosCount);
    
    public abstract void setTaskInfosItem(JSONObject TaskInfosItem);
    
    public abstract void setErrorsItems(JSONArray errorsItems);
    
    public abstract void setSwitchFalg(JSONObject switchFalg);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus errorsItem = createData("errorsItem");
        if (errorsItem != null && !errorsItem.getDataObject().isEmpty())
        {
            setErrorsItem(errorsItem.getDataObject());
        }
        IDataBus TaskInfosItem = createData("TaskInfosItem");
        if (TaskInfosItem != null && !TaskInfosItem.getDataObject().isEmpty())
        {
            setTaskInfosItem(TaskInfosItem.getDataObject());
        }
        IDataBus switchFalg = createData("switchFalg");
        if (switchFalg != null && !switchFalg.getDataObject().isEmpty())
        {
            setSwitchFalg(switchFalg.getDataObject());
        }
        initExtend(cycle);
    }
}