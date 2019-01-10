package com.asiainfo.index.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IData;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.impl.TreeBean;
import com.ailk.common.data.impl.TreeHelper;
import com.ailk.common.data.impl.TreeItem;
import com.ailk.web.view.component.tree.TreeFactory;
import com.ailk.web.view.component.tree.TreeParam;
import com.asiainfo.index.advpay.bo.BOUpgMonitorDataBean;
import com.asiainfo.index.advpay.ivalues.IBOUpgMonitorDataValue;
import com.asiainfo.index.base.ivalues.IBOBsMonitorCfgValue;
import com.asiainfo.index.service.interfaces.IBsIndexSV;
import com.asiainfo.index.util.IndexConstants;
import com.asiainfo.index.util.IndexUtil;

/**
 * Created by SMT Copyright: Copyright(c) 2015 Asiainfo-Linkage
 */
public abstract class MonIndexInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /** 静态数据 */
    /** 去之前判断一下是否为空，若为空，重新点击tree上的节点 */
    private static Map<String, Map<String, String>> staticMap = null;
    
    /** 静态数据key：维度 */
    private static final String CONDITION = "condition";
    
    /** 静态数据key：标量 */
    private static final String VALUE = "value";
    
    public static void main(String[] args)
        throws Exception
    {
        initStaticMap(100);
    }
    
    private static void initStaticMap(int monitorId)
        throws Exception
    {
        staticMap = new HashMap<String, Map<String, String>>();
        Map<String, String> map1 = new LinkedHashMap<String, String>();
        Map<String, String> map2 = new LinkedHashMap<String, String>();
        
        IBsIndexSV bsIndexSV = (IBsIndexSV)ServiceFactory.getService(IBsIndexSV.class);
        // 判断nameMap是否为空
        Map<Integer, String> nameMap = bsIndexSV.getAllIndexName();
        if (nameMap == null)
        {
            return;
        }
        IBOBsMonitorCfgValue[] values = bsIndexSV.getConditions(monitorId, "0");
        
        if (values != null && values.length > 0)
        {
            for (int i = 0; i < values.length - 1; i++)
            {
                map1.put(values[i].getIndexCodeMapping(), nameMap.get(values[i].getIndexId()));
            }
            map2.put(values[values.length - 1].getIndexCodeMapping(),
                nameMap.get(values[values.length - 1].getIndexId()));
            
        }
        IBOBsMonitorCfgValue[] values2 = bsIndexSV.getConditions(monitorId, "1");
        if (values != null && values.length > 0)
        {
            for (IBOBsMonitorCfgValue value : values2)
            {
                map2.put(value.getIndexCodeMapping(), nameMap.get(value.getIndexId()));
            }
        }
        staticMap.put(CONDITION, map1);
        staticMap.put(VALUE, map2);
    }
    
    /**
     * 查询数据
     * 
     * @param request
     * @throws Exception
     */
    public void loadCondData(IRequestCycle request)
        throws Exception
    {
        String monitorId = getContext().getParameter("monitorId").split("_")[0];
        initStaticMap(Integer.valueOf(monitorId));
        JSONObject jsonObj = new JSONObject();
        Map<String, String> map = staticMap.get("condition");
        // 获取所有的枚举值
        IBsIndexSV bsIndexSV = (IBsIndexSV)ServiceFactory.getService(IBsIndexSV.class);
        Map<String, List<String[]>> map2 = bsIndexSV.getAllEnmuValue();
        
        JSONArray array = new JSONArray();
        if (map != null && map.size() > 0)
        {
            for (String key : map.keySet())
            {
                String indexCode = map.get(key).split("&")[1];
                List<String[]> list = map2.get(indexCode);
                String nameCode = "";
                if (list != null && list.size() > 0)
                {
                    for (int j = 0; j < list.size(); j++)
                    {
                        String[] strings = list.get(j);
                        for (int i = 0; i < strings.length; i++)
                        {
                            if (i < strings.length - 1)
                            {
                                nameCode += strings[i] + "_";
                            }
                            else if (i == strings.length - 1)
                            {
                                nameCode += strings[i];
                                if (j != list.size() - 1)
                                {
                                    nameCode += "%";
                                }
                            }
                        }
                        nameCode += "";
                    }
                }
                if (StringUtils.isBlank(nameCode))
                {
                    array.add(key + "@@" + map.get(key));
                }
                else
                {
                    array.add(key + "@@" + map.get(key) + "@@" + nameCode);
                }
            }
        }
        jsonObj.put("V", array);
        this.setAjax(jsonObj);
    }
    
    /**
     * 加载组织树
     * 
     * @param request
     * @throws Exception
     */
    public void loadTreeData(IRequestCycle request)
        throws Exception
    {
        TreeParam param = TreeParam.getTreeParam(request);
        TreeBean[] treeBeanArr = getTreeItems(2);
        TreeItem[] root = TreeHelper.getWholeTreeData(treeBeanArr);
        IData treeNodes = TreeFactory.buildTreeData(param, root);
        getContext().setAjax(treeNodes);
    }
    
    /**
     * 获取tree上的值
     * 
     * @param treeLevel
     * @return
     * @throws Exception
     */
    public static TreeBean[] getTreeItems(int treeLevel)
        throws Exception
    {
        IBsIndexSV bsIndexSV = (IBsIndexSV)ServiceFactory.getService(IBsIndexSV.class);
        Map<Integer, String> map = new HashMap<Integer, String>();
        map = bsIndexSV.getAllMonitors();
        List<TreeBean> treeList = new ArrayList<TreeBean>();
        if (map.size() > 0)
        {
            TreeBean rootNode = new TreeBean();
            rootNode.setCode("root");
            rootNode.setId("-1");
            rootNode.setLabel("系统节点");
            rootNode.setLevel("1");
            rootNode.setParentId("0");
            rootNode.setValue("root");
            treeList.add(rootNode);
            TreeBean treeNode2 = null;
            for (Integer key : map.keySet())
            {
                String level = "2";
                treeNode2 = new TreeBean();
                treeNode2.setId(key + "_" + level);
                treeNode2.setLabel(map.get(key));
                treeNode2.setLevel(level);
                treeNode2.setParentId(rootNode.getId());
                treeList.add(treeNode2);
                if (treeLevel < 3)
                {
                    continue;
                }
            }
        }
        return treeList.toArray(new TreeBean[0]);
    }
    
    /**
     * 初始化kpi信息
     * 
     * @param request
     * @throws Exception
     */
    public void loadKpiInitData(IRequestCycle request)
        throws Exception
    {
        String param = getContext().getParameter("param");
        String monitorId = getContext().getParameter("monitorId").split("_")[0];
        Map<String, Object> conditions = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(param))
        {
            if (param.contains(";"))
            {
                String[] strings = param.split(";");
                for (String str : strings)
                {
                    if (str.contains(","))
                    {
                        String[] ss = str.split(",");
                        if (ss.length == 2)
                        {
                            conditions.put(ss[0], ss[1]);
                        }
                    }
                }
            }
        }
        conditions.put("MONITOR_ID", Long.valueOf(monitorId));
        JSONObject jsonObj = new JSONObject();
        SimpleDateFormat temp = new SimpleDateFormat(IndexConstants.DATE_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 6);
        
        Date beginDate = calendar.getTime();
        Date endDate = new Date();
        String startTime = temp.format(beginDate);
        String endTime = temp.format(endDate);
        IBsIndexSV bsIndexSV = (IBsIndexSV)ServiceFactory.getService(IBsIndexSV.class);
        Map<String, String> map2 = staticMap.get(VALUE);
        List<String> list = new ArrayList<String>();
        if (map2.size() > 0)
        {
            for (String key : map2.keySet())
            {
                list.add(key);
            }
        }
        // 查询条件
        IBOUpgMonitorDataValue[] values = bsIndexSV.getData(conditions, list, startTime, endTime, -1);
        JSONArray array = new JSONArray();
        if (list.size() > 0)
        {
            for (int j = 0; j < list.size(); j++)
            {
                String v1 = list.get(j);
                JSONArray jsonArray = new JSONArray();
                if (values != null && values.length > 0)
                {
                    for (int i = 0; i < values.length; i++)
                    {
                        if (j == 0)
                        {
                            jsonArray.add(IndexUtil.getXFromData(IndexUtil.getProperty((BOUpgMonitorDataBean)values[i],
                                v1)));
                        }
                        else
                        {
                            jsonArray.add(IndexUtil.getProperty((BOUpgMonitorDataBean)values[i], v1));
                        }
                    }
                }
                array.add(jsonArray);
            }
        }
        if (values == null || values.length == 0)
        {
            jsonObj.put("kpiHis", "N");
        }
        else
        {
            JSONObject jsonObject = new JSONObject();
            String maxSeqId = String.valueOf(values[values.length - 1].getSeqId());
            jsonObject.put("maxSeqId", maxSeqId);
            jsonObject.put("kpiHis", array);
            jsonObj.put("kpiHis", jsonObject);
        }
        this.setAjax(jsonObj);
    }
    
    /**
     * 初始化kpi信息
     * 
     * @param request
     * @throws Exception
     */
    public void loadKpiFreshData(IRequestCycle request)
        throws Exception
    {
        String param = getContext().getParameter("param");
        String monitorId = getContext().getParameter("monitorId").split("_")[0];
        String maxSeqId = getContext().getParameter("maxSeqId");
        
        Map<String, Object> conditions = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(param))
        {
            if (param.contains(";"))
            {
                String[] strings = param.split(";");
                for (String str : strings)
                {
                    if (str.contains(","))
                    {
                        String[] ss = str.split(",");
                        if (ss.length == 2)
                        {
                            conditions.put(ss[0], ss[1]);
                        }
                    }
                }
            }
        }
        conditions.put("MONITOR_ID", Long.valueOf(monitorId));
        JSONObject jsonObj = new JSONObject();
        IBsIndexSV bsIndexSV = (IBsIndexSV)ServiceFactory.getService(IBsIndexSV.class);
        Map<String, String> map2 = staticMap.get(VALUE);
        List<String> list = new ArrayList<String>();
        if (map2.size() > 0)
        {
            for (String key : map2.keySet())
            {
                list.add(key);
            }
        }
        // 查询条件
        IBOUpgMonitorDataValue[] values = bsIndexSV.getData(conditions, list, null, null, Long.valueOf(maxSeqId));
        JSONArray array = new JSONArray();
        if (list.size() > 0)
        {
            for (int j = 0; j < list.size(); j++)
            {
                String v1 = list.get(j);
                JSONArray jsonArray = new JSONArray();
                if (values != null && values.length > 0)
                {
                    for (int i = 0; i < values.length; i++)
                    {
                        if (j == 0)
                        {
                            jsonArray.add(IndexUtil.getXFromData(IndexUtil.getProperty((BOUpgMonitorDataBean)values[i],
                                v1)));
                        }
                        else
                        {
                            jsonArray.add(IndexUtil.getProperty((BOUpgMonitorDataBean)values[i], v1));
                        }
                    }
                }
                array.add(jsonArray);
            }
        }
        if (values == null || values.length == 0)
        {
            jsonObj.put("kpiFresh", "N");
        }
        else
        {
            JSONObject jsonObject = new JSONObject();
            maxSeqId = String.valueOf(values[values.length - 1].getSeqId());
            jsonObject.put("maxSeqId", maxSeqId);
            jsonObject.put("kpiFresh", array);
            jsonObj.put("kpiFresh", jsonObject);
        }
        this.setAjax(jsonObj);
    }
    
    /**
     * 获取初始需要展示的指标
     * 
     * @param request
     * @throws Exception
     */
    public void loadIndexInitData(IRequestCycle request)
        throws Exception
    {
        String monitorId = getContext().getParameter("monitorId").split("_")[0];
        JSONObject jsonObj = new JSONObject();
        if (staticMap == null)
        {
            initStaticMap(Integer.parseInt(monitorId));
        }
        Map<String, String> map = staticMap.get(VALUE);
        JSONArray array = new JSONArray();
        if (map != null)
        {
            for (String key : map.keySet())
            {
                array.add(map.get(key).split("&")[0]);
            }
        }
        jsonObj.put("index", array);
        this.setAjax(jsonObj);
    }
    
    /**
     * 查询历史信息
     */
    public void showHisInfo(IRequestCycle cycle)
        throws Exception
    {
        String param = getContext().getParameter("param");
        String monitorId = getContext().getParameter("monitorId").split("_")[0];
        Map<String, Object> conditions = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(param))
        {
            if (param.contains(";"))
            {
                String[] strings = param.split(";");
                for (String str : strings)
                {
                    if (str.contains(","))
                    {
                        String[] ss = str.split(",");
                        if (ss.length == 2)
                        {
                            conditions.put(ss[0], ss[1]);
                        }
                    }
                }
            }
        }
        conditions.put("MONITOR_ID", Long.valueOf(monitorId));
        IDataBus qryformBus = createData("qryForm");
        JSONObject qryformCondition = (JSONObject)qryformBus.getData();
        String startTime = (String)qryformCondition.get("beginDate");
        String endTime = (String)qryformCondition.get("endDate");
        JSONObject jsonObj = new JSONObject();
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime))
        {
            // 当开始时间和结束时间有一个为空的时候
            String str = "A";
            jsonObj.put("kpiHis", str);
            this.setAjax(jsonObj);
        }
        else
        {
            SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date begin = temp.parse(startTime);// 将字符串转换成日期类型
            Date end = temp.parse(endTime);
            if (begin.compareTo(end) <= 0)
            {
                IBsIndexSV bsIndexSV = (IBsIndexSV)ServiceFactory.getService(IBsIndexSV.class);
                Map<String, String> map2 = staticMap.get(VALUE);
                List<String> list = new ArrayList<String>();
                if (map2.size() > 0)
                {
                    for (String key : map2.keySet())
                    {
                        list.add(key);
                    }
                }
                // 查询条件
                IBOUpgMonitorDataValue[] values = bsIndexSV.getData(conditions, list, startTime, endTime, -1);
                JSONArray array = new JSONArray();
                if (list.size() > 0)
                {
                    for (int j = 0; j < list.size(); j++)
                    {
                        String v1 = list.get(j);
                        JSONArray jsonArray = new JSONArray();
                        if (values != null && values.length > 0)
                        {
                            for (int i = 0; i < values.length; i++)
                            {
                                if (j == 0)
                                {
                                    jsonArray.add(IndexUtil.getHisXFromData(IndexUtil.getProperty((BOUpgMonitorDataBean)values[i],
                                        v1)));
                                }
                                else
                                {
                                    jsonArray.add(IndexUtil.getProperty((BOUpgMonitorDataBean)values[i], v1));
                                }
                            }
                        }
                        array.add(jsonArray);
                    }
                }
                if (values == null || values.length == 0)
                {
                    jsonObj.put("kpiHis", "N");
                }
                else
                {
                    jsonObj.put("kpiHis", array);
                }
                this.setAjax(jsonObj);
            }
            else if (begin.compareTo(end) > 0)
            {
                jsonObj.put("kpiHis", "C");
                this.setAjax(jsonObj);
            }
        }
    }
    
    public abstract void setQryReal(JSONObject qryReal);
    
    public abstract void setQryForm(JSONObject qryForm);
    
    @Override
    protected void initPageAttrs()
    {
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus qryReal = createData("qryReal");
        if (qryReal != null && !qryReal.getDataObject().isEmpty())
        {
            setQryReal(qryReal.getDataObject());
        }
        IDataBus qryForm = createData("qryForm");
        if (qryForm != null && !qryForm.getDataObject().isEmpty())
        {
            setQryForm(qryForm.getDataObject());
        }
        initExtend(cycle);
    }
    
}
