package com.asiainfo.monitor.exeframe.monitorItem.view;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.monitor.exeframe.monitorItem.ivalues.IBOAIMonBatchValue;
import com.asiainfo.monitor.exeframe.monitorItem.service.interfaces.IAIMonBatchSV;
import com.asiainfo.schedule.ivalues.IBOAISchedTaskLogValue;
import com.asiainfo.schedule.log.service.interfaces.ISchedulerLogSV;
import com.asiainfo.schedule.util.ScheduleConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2015 Asiainfo-Linkage
 */
public abstract class GuaranteeInfo extends AppPage
{
    
    private static Map<String, Map<String, String>> staticMap = null;
    
    private static final String KEY_LOG_TYPE = "logType";
    
    /**
     * 获取批次列表
     * 
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public IDataBus getBatchList()
        throws RemoteException, Exception
    {
        // 根据批次查看任务编码
        IAIMonBatchSV batchSv = (IAIMonBatchSV)ServiceFactory.getService(IAIMonBatchSV.class);
        IBOAIMonBatchValue[] values = batchSv.getAllInfos();
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        List<Long> list = new ArrayList<Long>();
        if (values != null && values.length > 0)
        {
            for (IBOAIMonBatchValue value : values)
            {
                list.add(value.getBatchId());
            }
        }
        if (list != null && list.size() > 0)
        {
            list = removeDuplicate(list);
            for (int i = 0; i < list.size(); i++)
            {
                if (list.get(i) == 1)
                {
                    JSONObject obj = new JSONObject();
                    String text = "月末月初保障批次";
                    String val = String.valueOf(list.get(i));
                    obj.put("TEXT", text);
                    obj.put("VALUE", val);
                    jsonArray.add(obj);
                }
                else
                {
                    // 当批次不为1的时候，暂时不翻译
                    JSONObject obj = new JSONObject();
                    String val = String.valueOf(list.get(i));
                    obj.put("TEXT", val);
                    obj.put("VALUE", val);
                    jsonArray.add(obj);
                }
            }
        }
        return new DataBus(context, jsonArray);
    }
    
    /**
     * 去除list中的重复元素
     * 
     * @param list
     * @return
     */
    public static List<Long> removeDuplicate(List list)
    {
        for (int i = 0; i < list.size() - 1; i++)
        {
            for (int j = list.size() - 1; j > i; j--)
            {
                if (list.get(j).equals(list.get(i)))
                {
                    list.remove(j);
                }
            }
        }
        return list;
    }
    
    /**
     * 获取月末月初任务信息
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryInfo(IRequestCycle cycle)
        throws Exception
    {
        IDataBus qryformBus = createData("qryCondition");
        JSONObject condition = (JSONObject)qryformBus.getData();
        String batchId = condition.getString("batchId");
        String startTime = condition.getString("beginDate");
        String endTime = condition.getString("endDate");
        
        JSONArray array = new JSONArray();
        if ("1".equals(batchId))
        {
            Calendar calendar = Calendar.getInstance();
            Calendar calendar1 = Calendar.getInstance();
            String flag = "";
            // 根据批次查看任务编码
            IAIMonBatchSV batchSv = (IAIMonBatchSV)ServiceFactory.getService(IAIMonBatchSV.class);
            IBOAIMonBatchValue[] values = batchSv.getAllBatchInfo(Long.parseLong(batchId));
            
            Map<Long, String> snMap = new HashMap<Long, String>();
            // 从月末月初表ai_mon_batch中取到批次为1的数据
            if (values != null && values.length > 0)
            {
                // 通过循环获取所有顺序
                for (IBOAIMonBatchValue value : values)
                {
                    if (snMap.containsKey(value.getSn()))
                    {
                        continue;
                    }
                    else
                    {
                        snMap.put(value.getSn(), value.getTaskCode().split("_")[0]);
                    }
                }
            }
            ISchedulerLogSV sv = (ISchedulerLogSV)ServiceFactory.getService(ISchedulerLogSV.class);
            
            Map<String, String> map = new HashMap<String, String>();
            if (values != null && values.length > 0)
            {
                for (IBOAIMonBatchValue value : values)
                {
                    String taskCode = value.getTaskCode();
                    if (StringUtils.isNotBlank(taskCode))
                    {
                        IBOAISchedTaskLogValue logValue = null;
                        // 当开始时间和结束时间都为空的时候
                        if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime))
                        {
                            // 不做操作，表格内的内容为空
                            flag = "N";
                        }
                        // 当开始时间不为空，结束时间为空的时候
                        if (StringUtils.isNotBlank(startTime) && StringUtils.isBlank(endTime))
                        {
                            Date sDate = longSdf.parse(startTime);
                            calendar.setTime(sDate);
                            calendar.add(Calendar.MONTH, +1);
                            logValue = sv.getSchedTaskLog(taskCode, startTime, longSdf.format(calendar.getTime()));
                        }
                        // 当开始时间为空，结束时间不为空的时候
                        if (StringUtils.isBlank(startTime) && StringUtils.isNotBlank(endTime))
                        {
                            Date eDate = longSdf.parse(endTime);
                            calendar1.setTime(eDate);
                            calendar1.add(Calendar.MONTH, -1);
                            logValue = sv.getSchedTaskLog(taskCode, longSdf.format(calendar1.getTime()), endTime);
                        }
                        // 当开始时间和结束时间都不为空的时候
                        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime))
                        {
                            Date sDate = longSdf.parse(startTime);
                            Date eDate = longSdf.parse(endTime);
                            // 当开始时间大于结束时间的时候，给予提示
                            if (sDate.compareTo(eDate) > 0)
                            {
                                flag = "D";
                            }
                            calendar.setTime(sDate);
                            calendar1.setTime(eDate);
                            calendar.add(Calendar.MONTH, +1);
                            if (calendar.before(calendar1))
                            {
                                // 超过一个月的跨度，做出提示
                                flag = "C";
                            }
                            else
                            {
                                logValue = sv.getSchedTaskLog(taskCode, startTime, endTime);
                            }
                        }
                        if (logValue != null)
                        {
                            map.put(taskCode, logValue.getState());
                            String startEndStr = "";
                            if (logValue.getStartTime() != null)
                            {
                                startEndStr += "开始时间：";
                                startEndStr += longSdf.format(logValue.getStartTime());
                                startEndStr += " ";
                            }
                            if (logValue.getFinishTime() != null)
                            {
                                startEndStr += "结束时间：";
                                startEndStr += longSdf.format(logValue.getFinishTime());
                            }
                            map.put("startEndTime_" + taskCode, startEndStr);
                        }
                    }
                }
            }
            if (snMap != null && snMap.size() > 0)
            {
                Set<Entry<Long, String>> set = snMap.entrySet();
                List<Entry<Long, String>> list = new ArrayList<Entry<Long, String>>(set);
                Collections.sort(list, new Comparator<Entry<Long, String>>()
                {
                    @Override
                    public int compare(Entry<Long, String> o1, Entry<Long, String> o2)
                    {
                        return (int)(o1.getKey() - o2.getKey());
                    }
                });
                for (int i = 0; i < list.size(); i++)
                {
                    Entry<Long, String> entry = list.get(i);
                    long sn = entry.getKey();
                    JSONObject object = new JSONObject();
                    if (sn == 1)
                    {
                        object.put("column0", "[" + sn + "]" + "停实时销账进程");
                    }
                    else if (sn == 2)
                    {
                        object.put("column0", "[" + sn + "]" + "月末月初出账");
                    }
                    else if (sn == 3)
                    {
                        object.put("column0", "[" + sn + "]" + "积分镜像备份");
                    }
                    else
                    {
                        object.put("column0", sn);
                    }
                    String code = entry.getValue();
                    for (int j = 570; j < 581; j++)
                    {
                        String str0 = code + "_" + j;
                        String str00 = "startEndTime_" + str0;
                        if (map.containsKey(str0))
                        {
                            object.put("column" + (j - 569), map.get(str0));
                        }
                        if (map.containsKey(str00))
                        {
                            object.put("startEndTime" + (j - 569), map.get(str00));
                        }
                    }
                    array.add(object);
                }
            }
            if (StringUtils.isNotBlank(flag))
            {
                JSONObject tipsObj = new JSONObject();
                tipsObj.put("FLAG", flag);
                this.setAjax(tipsObj);
            }
            else
            {
                this.setBatchTabItems(array);
            }
        }
        else
        {
            this.setBatchTabItems(array);
        }
    }
    
    private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
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
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public abstract void setBatchTabCount(long batchTabCount);
    
    public abstract void setBatchTabItems(JSONArray batchTabItems);
    
    public abstract void setBatchTabItem(JSONObject batchTabItem);
    
    public abstract void setQryCondition(JSONObject qryCondition);
    
    public abstract void setBatchTabRowIndex(int batchTabRowIndex);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus batchTabItem = createData("batchTabItem");
        if (batchTabItem != null && !batchTabItem.getDataObject().isEmpty())
        {
            setBatchTabItem(batchTabItem.getDataObject());
        }
        IDataBus qryCondition = createData("qryCondition");
        if (qryCondition != null && !qryCondition.getDataObject().isEmpty())
        {
            setQryCondition(qryCondition.getDataObject());
        }
        initExtend(cycle);
    }
    
}
