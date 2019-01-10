package com.asiainfo.monitor.exeframe.monitorPortal.view;

import java.text.Collator;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;
import org.dom4j.Element;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IData;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.impl.TreeBean;
import com.ailk.common.data.impl.TreeHelper;
import com.ailk.common.data.impl.TreeItem;
import com.ailk.web.view.component.tree.TreeFactory;
import com.ailk.web.view.component.tree.TreeParam;
import com.asiainfo.common.abo.bo.BOABGMonBusiLogBean;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.logger.utils.QueryConstants.QueryKey;
import com.asiainfo.logger.utils.QueryUtils;
import com.asiainfo.monitor.acquire.dto.BusiErrLogBean;
import com.asiainfo.monitor.acquire.dto.BusiLogBean;
import com.asiainfo.monitor.acquire.service.interfaces.IBusiLogSV;
import com.asiainfo.monitor.acquire.service.interfaces.IBusiProcessSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.common.service.interfaces.IBaseCommonSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.schedule.core.utils.Constants;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class MonBusiInfoPortal extends AppPage
{
    private static final Logger LOGGER = Logger.getLogger(MonBusiInfoPortal.class);
    
    private static final String RULE_NUMBER = "^\\d+$";
    
    /** 树节点ID分隔符 */
    private static final String SPLIT = "__";
    
    /** 二级分片分隔符 */
    private static final String SEPRATOR_SECOND_MENU = ";";
    
    /** 是否有二级菜单标识 */
    private static boolean hasSecond = false;
    
    /** 是否有默认分片-0 */
    private static boolean hasDefaultSplit = false;
    
    /** 默认分片-0 */
    private static String DEFAULT_SPLIT = "0";
    
    /** 保存下级列表 */
    private static Map<String, JSONArray> secondList = new HashMap<String, JSONArray>();
    
    private static Object lock = new Object();
    
    /** 应用编码的分隔符 "^" */
    private static String SEPERATOR = "^";
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
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
        // 获取角色标识
        TreeParam param = TreeParam.getTreeParam(request);
        
        // 同步全量加载树
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        Element e = sv.getTaskGroupTree(true);
        // 没有业务
        if (e == null)
        {
            return;
        }
        
        TreeBean[] treeBeanArr = transTree(e);
        
        TreeItem[] root = TreeHelper.getWholeTreeData(treeBeanArr);
        IData treeNodes = TreeFactory.buildTreeData(param, root);
        
        getContext().setAjax(treeNodes);
    }
    
    /**
     * 根据taskcode获得关联一级菜单列表
     * 
     * @return
     */
    public void getFirstList(IRequestCycle request)
        throws Exception
    {
        hasSecond = false;// 重置标识
        hasDefaultSplit = false;// 重置标识
        secondList.clear();// 首先清空上次记录的二级菜单列表
        String taskCode = getContext().getParameter("taskCode");
        taskCode = taskCode.split(SPLIT)[0];
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject obj = new JSONObject();
        JSONArray firstArray = new JSONArray();
        Map<String, String> cityCode = sv.getAllServersByTaskCode(taskCode);
        firstArray = transList(cityCode);
        Collections.sort(firstArray);
        obj.put("hasSecond", hasSecond ? "Y" : "N");
        obj.put("firstArray", firstArray);
        this.setAjax(obj);
        
    }
    
    /**
     * 根据一级菜单编号获得关联二级菜单列表
     * 
     * @return
     */
    public void getSecondList(IRequestCycle request)
        throws Exception
    {
        String taskCode = getContext().getParameter("firstCode");
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IBaseCommonSV baseSv = (IBaseCommonSV)ServiceFactory.getService(IBaseCommonSV.class);
        JSONArray jsonSecondArr = new JSONArray();
        String[] strArray = null;
        if (!secondList.isEmpty())
        {
            JSONArray dataArray = secondList.get(taskCode);
            Collections.sort(dataArray);
            String tempStr = null;
            for (int i = 0; i < dataArray.size(); i++)
            {
                JSONObject obj = new JSONObject();
                tempStr = dataArray.getString(i);
                strArray = tempStr.split(SEPRATOR_SECOND_MENU);
                obj.put("itemCodeAll", strArray[0]);
                
                if (strArray[0].indexOf("^") > 0)
                {
                    obj.put("itemCode", strArray[0].split("\\^")[1]);
                }
                else
                {
                    obj.put("itemCode", strArray[0]);
                }
                // 如果没配severCode则后续数据都查不到
                if (strArray.length > 1)
                {
                    obj.put("appCode", strArray[1]);
                    List<String> list = new ArrayList<String>();
                    list.add(strArray[1]);
                    Map<String, IBOAIMonPhysicHostValue> hostMap = hostSv.qryHostByAppCode(list);
                    IBOAIMonPhysicHostValue host = hostMap.get(strArray[1]);
                    if (host != null)
                    {
                        obj.put("hostName", host.getHostName() + "(" + host.getHostIp() + ")");
                        BOAIMonGroupBean bean = baseSv.queryGroupByHostId(host.getHostId() + "");
                        obj.put("hostGroup", bean == null ? "" : bean.getGroupName());
                    }
                }
                jsonSecondArr.add(obj);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("secondArray", jsonSecondArr);
        this.setAjax(jsonObject);
    }
    
    /**
     * 获得应用编码
     * 
     * @param request
     * @throws Exception
     */
    public void getServerCode(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String taskCode = getContext().getParameter("taskCode");
        String itemCode = getContext().getParameter("itemCode");
        String secondFlag = getContext().getParameter("secondFlag");
        String serverCodeStr = getContext().getParameter("serverCode");
        String serverCode = null;
        JSONObject jsonObj = new JSONObject();
        if ("T".equals(secondFlag))
        {
            serverCode = serverCodeStr;
        }
        else
        {
            ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
            serverCode = sv.getServerCode(taskCode, itemCode);
        }
        
        if (StringUtils.isNotBlank(serverCode))
        {
            String hostId = null;
            if (StringUtils.isNotBlank(serverCode))
            {
                IAIMonServerSV serverSv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
                hostId = serverSv.qryHostIdByServerCode(serverCode);
            }
            if (StringUtils.isNotBlank(hostId))
            {
                // queryProcess(serverCode);
                queryProcess(serverCode, jsonObj, offset, linage);
            }
            else
            {
                jsonObj.put("flag", "F");
                jsonObj.put("msg", "\u8be5\u5206\u7247\u5e94\u7528\u7f16\u7801\u4e3a\uff1a" + serverCode
                    + "\uff0c\u672a\u627e\u5230\u4e3b\u673a");
            }
            jsonObj.put("serverCode", serverCode);
        }
        else
        {
            jsonObj.put("flag", "F");
            jsonObj.put("msg", "\u8be5\u5206\u7247\u672a\u914d\u7f6e\u5e94\u7528");
        }
        this.setAjax(jsonObj);
        
    }
    
    /**
     * 获取实时数据
     * 
     * @param request
     * @throws Exception
     */
    public void getBusiData(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        SimpleDateFormat sdfOra = new SimpleDateFormat("HH:mm");
        String serverCode = getContext().getParameter("serverCode");
        String itemCode = getContext().getParameter("itemCode");
        String taskCode = getContext().getParameter("taskCode");
        // 调用接口查询数据+++++++++++++++++++++++++++++++++++++++++++++++
        IBusiLogSV sv = (IBusiLogSV)ServiceFactory.getService(IBusiLogSV.class);
        BusiLogBean[] busiLogs = sv.acquireBusiLog(serverCode, taskCode, itemCode);
        JSONArray handleArray = new JSONArray();
        JSONArray leftArray = new JSONArray();
        JSONArray speedArray = new JSONArray();
        JSONArray xArray = new JSONArray();
        
        for (BusiLogBean bean : busiLogs)
        {
            handleArray.add(this.convertPointValue((float)(bean.getHandleCnt() * 100.0 / bean.getTotalCnt())));
            leftArray.add(this.convertPointValue((float)((bean.getTotalCnt() - bean.getHandleCnt()) * 100.0 / bean.getTotalCnt())));
            speedArray.add(this.convertPointValue((float)(bean.getPerHandleCnt() * 1000.0 / bean.getConsumeTime())));
            // handleArray.add((int)(Math.random()*100));
            // leftArray.add((int)(Math.random()*100));
            // speedArray.add((int)(Math.random()*1000));
            xArray.add(sdfOra.format(bean.getAcqDate()));
        }
        JSONObject obj = new JSONObject();
        obj.put("xArray", xArray);
        obj.put("handleArray", handleArray);
        obj.put("leftArray", leftArray);
        obj.put("speedArray", speedArray);
        this.setAjax(obj);
        
        // 同时查询进程相关信息
        queryProcess(serverCode, obj, offset, linage);
        
    }
    
    /**
     * 获取实时错单数据
     * 
     * @param request
     * @throws Exception
     */
    public void getBusiErrorData(IRequestCycle request)
        throws Exception
    {
        SimpleDateFormat sdfOra = new SimpleDateFormat("HH:mm");
        String serverCode = getContext().getParameter("serverCode");
        String itemCode = getContext().getParameter("itemCode");
        String taskCode = getContext().getParameter("taskCode");
        
        // 调用接口查询数据+++++++++++++++++++++++++++++++++++++++++++++++
        IBusiLogSV sv = (IBusiLogSV)ServiceFactory.getService(IBusiLogSV.class);
        JSONArray errorArray = new JSONArray();// 错单量
        JSONArray errorXArray = new JSONArray();// 错单处理X轴
        BusiErrLogBean[] busiErrLogs = sv.acquireBusiErrLog(serverCode, taskCode, itemCode);
        for (BusiErrLogBean bean : busiErrLogs)
        {
            // errorArray.add((int)(Math.random()*1000));
            errorArray.add(bean.getErrCnt());
            errorXArray.add(sdfOra.format(bean.getAcqDate()));
        }
        JSONObject obj = new JSONObject();
        obj.put("errorArray", errorArray);
        obj.put("errorXArray", errorXArray);
        this.setAjax(obj);
    }
    
    /**
     * 查询历史数据
     * 
     * @param request
     * @throws Exception
     */
    public void queryHisData(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 页面传入日期格式
        SimpleDateFormat sdfHis = null;// 历史日期格式
        SimpleDateFormat sdfOra = null;// 页面初始化记录格式
        IDataBus queryFormBus = createData("queryForm");
        JSONObject queryFormCondition = (JSONObject)queryFormBus.getData();
        String taskCode = getContext().getParameter("taskCode");
        String itemCode = getContext().getParameter("itemCode");
        String startTime = (String)queryFormCondition.get("startTime");
        String endTime = (String)queryFormCondition.get("endTime");
        Date start = null;
        Date end = null;
        
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime))
        {
            start = sdf.parse(startTime);
            end = sdf.parse(endTime);
            sdfHis = new SimpleDateFormat("MM-dd HH:mm");// 历史记录
        }
        else
        // 如果没传开始结束时间，说明是页面初始化,初始化时开始时间为当前时间6小时前,结束时间为当前时间
        {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, -6);
            start = cal.getTime();
            end = new Date();
            sdfOra = new SimpleDateFormat("HH:mm");
        }
        String serverCode = getContext().getParameter("serverCode");
        IBusiLogSV sv = (IBusiLogSV)ServiceFactory.getService(IBusiLogSV.class);
        
        try
        {
            BusiLogBean[] busiLogs = sv.acquireBusiHisLog(serverCode, taskCode, itemCode, start, end);
            BusiErrLogBean[] busiErrLogs = sv.acquireBusiHisErrLog(serverCode, taskCode, itemCode, start, end);
            JSONObject obj = new JSONObject();
            if (null == busiLogs || busiLogs.length == 0)
            {
                obj.put("busiFlag", "T");
            }
            if (null == busiErrLogs || busiErrLogs.length == 0)
            {
                obj.put("errorFlag", "T");
            }
            JSONArray xArray = new JSONArray();// 业务处理X轴
            JSONArray handleArray = new JSONArray();// 业务处理量
            JSONArray leftArray = new JSONArray();// 业务积压量
            JSONArray speedArray = new JSONArray();// 业务处理速率
            for (BusiLogBean bean : busiLogs)
            {
                handleArray.add(this.convertPointValue((float)(bean.getHandleCnt() * 100.0 / bean.getTotalCnt())));
                leftArray.add(this.convertPointValue((float)((bean.getTotalCnt() - bean.getHandleCnt()) * 100.0 / bean.getTotalCnt())));
                speedArray.add(this.convertPointValue((float)(bean.getPerHandleCnt() * 1000.0 / bean.getConsumeTime())));
                // handleArray.add((int)(Math.random()*100));
                // leftArray.add((int)(Math.random()*100));
                // speedArray.add((int)(Math.random()*1000));
                if (sdfHis != null)
                {
                    xArray.add(sdfHis.format(bean.getAcqDate()));
                }
                else
                {
                    xArray.add(sdfOra.format(bean.getAcqDate()));
                }
            }
            JSONArray errorArray = new JSONArray();// 错单量
            JSONArray errorXArray = new JSONArray();// 错单处理X轴
            for (BusiErrLogBean bean : busiErrLogs)
            {
                // errorArray.add((int)(Math.random()*1000));
                errorArray.add(bean.getErrCnt());
                if (sdfHis != null)
                {
                    errorXArray.add(sdfHis.format(bean.getAcqDate()));
                }
                else
                {
                    errorXArray.add(sdfOra.format(bean.getAcqDate()));
                }
            }
            obj.put("xArray", xArray);
            obj.put("handleArray", handleArray);
            obj.put("leftArray", leftArray);
            obj.put("speedArray", speedArray);
            obj.put("errorArray", errorArray);
            obj.put("errorXArray", errorXArray);
            this.setAjax(obj);
            
            // 同时查询进程相关信息
            queryProcess(serverCode, obj, offset, linage);
        }
        catch (Exception e)
        {
            LOGGER.error("查询历史数据失败：" + e);
        }
    }
    
    private String convertPointValue(float value)
    {
        DecimalFormat df = new DecimalFormat("0.00"); // 保留几位小数
        return df.format(value);
    }
    
    /**
     * 将传过来的树解析成页面所能接受的树(只取到task为止)
     * 
     * @param root
     * @return
     */
    private TreeBean[] transTree(Element root)
    {
        // 树节点list
        List<TreeBean> treeList = new ArrayList<TreeBean>();
        String level = "1";
        // 根节点初始化
        TreeBean rootNode = new TreeBean();
        rootNode.setCode("root");
        rootNode.setId("-1");
        rootNode.setLabel("业务分组");
        rootNode.setLevel(level);
        rootNode.setParentId("0");
        rootNode.setValue("root");
        treeList.add(rootNode);
        // 取2级节点(group)
        List<Element> e2s = root.elements();
        if (null != e2s && !e2s.isEmpty())
        {
            for (Element e2 : e2s)
            {
                level = "2";
                TreeBean treeNode2 = new TreeBean();
                treeNode2.setId(e2.attributeValue("code") + SPLIT + level);
                treeNode2.setLabel(e2.attributeValue("name"));
                treeNode2.setLevel(level);
                treeNode2.setParentId(rootNode.getId());
                treeList.add(treeNode2);
                // 取三级节点(task)
                List<Element> e3s = e2.elements();
                if (null != e3s && !e3s.isEmpty())
                {
                    for (Element e3 : e3s)
                    {
                        level = "3";
                        TreeBean treeNode3 = new TreeBean();
                        treeNode3.setId(e3.attributeValue("code") + SPLIT + level);
                        treeNode3.setLabel(e3.attributeValue("name"));
                        treeNode3.setLevel(level);
                        treeNode3.setParentId(treeNode2.getId());
                        treeList.add(treeNode3);
                    }
                }
            }
        }
        Collections.sort(treeList, new Comparator<TreeBean>()
        {
            @Override
            public int compare(TreeBean t1, TreeBean t2)
            {
                Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
                if (cmp.compare(t1.getLabel(), t2.getLabel()) > 0)
                {
                    return 1;
                }
                else if (cmp.compare(t1.getLabel(), t2.getLabel()) < 0)
                {
                    return -1;
                }
                return 0;
            }
            
        });
        return treeList.toArray(new TreeBean[0]);
    }
    
    /**
     * 转化菜单的编码
     * 
     * @param cityCode
     * @return
     */
    private static synchronized JSONArray transList(Map<String, String> cityCode)
    {
        JSONArray array = new JSONArray();
        secondList.clear();
        if (null != cityCode && !cityCode.isEmpty())
        {
            Set<Map.Entry<String, String>> entrys = cityCode.entrySet();
            String tempKey = null;
            JSONArray tempArray = null;
            String tempMenu = null;
            String tempValue = null;
            for (Map.Entry<String, String> entry : entrys)
            {
                tempKey = entry.getKey();
                tempValue = entry.getValue();
                if (tempKey == null)
                {
                    LOGGER.error("region code and spilt is null");
                    return array;
                }
                if (tempKey.contains(SEPERATOR))// 含有^的是两级分片 需要处理二级列表
                {
                    hasSecond = true;// 更新下二级菜单标识
                    String[] strArray = tempKey.split("\\^");
                    tempMenu = strArray[0];
                    array = addContent(array, tempMenu);
                    
                    // 处理二级列表
                    if (secondList.containsKey(tempMenu))// 有的话就更新原有记录
                    {
                        tempArray = secondList.get(tempMenu);
                        tempArray.add(tempKey + SEPRATOR_SECOND_MENU + tempValue);// 传过来的key和value拼接记录下来
                        secondList.put(tempMenu, tempArray);
                    }
                    else
                    {
                        tempArray = new JSONArray();
                        tempArray.add(tempKey + SEPRATOR_SECOND_MENU + tempValue);// 传过来的key和value拼接记录下来
                        secondList.put(tempMenu, tempArray);
                    }
                }
                else if (tempValue != null && tempValue.indexOf(Constants.ONLY_SPLIT_REGION_FLAG) > 0)
                {
                    hasDefaultSplit = true;
                    array = addContent(array, tempKey);
                }
                else
                {
                    array = addContent(array, tempKey);
                }
            }
        }
        return array;
    }
    
    /**
     * 给数组按类型添加内容
     * 
     * @param array
     * @param content
     * @return
     */
    private static JSONArray addContent(JSONArray array, String content)
    {
        if (StringUtils.isNotBlank(content) && content.matches(RULE_NUMBER))
        {
            if (!array.contains(Long.parseLong(content)))
            {
                array.add(Long.parseLong(content));
            }
        }
        else
        {
            if (!array.contains(content))
            {
                array.add(content);
            }
        }
        return array;
    }
    
    /**
     * 获取实时数据
     * 
     * @param request
     * @throws Exception
     */
    public void getBusiDataQry(IRequestCycle request)
        throws Exception
    {
        SimpleDateFormat sdfOra = new SimpleDateFormat("HH:mm:ss");
        String serverCode = getContext().getParameter("serverCode").trim();
        String itemCode = getContext().getParameter("itemCode").trim();
        String taskCode = getContext().getParameter("taskCode").trim();
        if (StringUtils.isBlank(serverCode))
        {
            return;
        }
        
        // 调用接口查询数据+++++++++++++++++++++++++++++++++++++++++++++++
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put(QueryKey.APP_CODE, serverCode);
        conditions.put(QueryKey.TASK_ID, taskCode);
        // 若有^分割符 则为两级 把地市和分片劈开传
        if (StringUtils.isNotBlank(itemCode))
        {
            if (itemCode.indexOf(SEPERATOR) > 0)
            {
                String[] temp = itemCode.split("\\^");
                if (temp.length == 2)
                {
                    conditions.put(QueryKey.REGION_CODE, temp[0]);
                    conditions.put(QueryKey.TASK_SPLIT_ID, temp[1]);
                }
                // 若没有^分隔符 但默认分片标识为true,则将itemCode作为地市,添加默认分片0
            }
            else if (hasDefaultSplit)
            {
                conditions.put(QueryKey.REGION_CODE, itemCode);
                conditions.put(QueryKey.TASK_SPLIT_ID, DEFAULT_SPLIT);
                // 若不满足前两个条件 则将itemCode作为分片标识传入
            }
            else
            {
                conditions.put(QueryKey.TASK_SPLIT_ID, itemCode);
            }
        }
        conditions.put(QueryKey.FLAG, "T");
        BOABGMonBusiLogBean[] busiLogs = QueryUtils.qryBusinessLogByCondition(conditions);
        JSONArray handleArray = new JSONArray();
        JSONArray leftArray = new JSONArray();
        JSONArray speedArray = new JSONArray();
        JSONArray xArray = new JSONArray();
        JSONArray errorArray = new JSONArray();// 错单量
        
        for (BOABGMonBusiLogBean bean : busiLogs)
        {
            handleArray.add(bean.getHandleCnt());
            leftArray.add((bean.getTotalCnt() - bean.getHandleCnt()));
            // 耗时为0的默认为1
            long timeCost = bean.getConsumeTime() == 0 ? 1 : bean.getConsumeTime();
            speedArray.add(this.convertPointValue((float)(bean.getPerHandleCnt() * 1000.0 / timeCost)));
            // handleArray.add((int)(Math.random()*100));
            // leftArray.add((int)(Math.random()*100));
            // speedArray.add((int)(Math.random()*1000));
            xArray.add(sdfOra.format(bean.getCreateDate()));
            errorArray.add(bean.getErrCnt());
        }
        JSONObject obj = new JSONObject();
        obj.put("xArray", xArray);
        obj.put("handleArray", handleArray);
        obj.put("leftArray", leftArray);
        obj.put("speedArray", speedArray);
        obj.put("errorArray", errorArray);
        
        this.setAjax(obj);
    }
    
    // /**
    // * 获取实时错单数据
    // * @param request
    // * @throws Exception
    // */
    // public void getBusiErrorDataQry (IRequestCycle request) throws Exception
    // {
    // SimpleDateFormat sdfOra = new SimpleDateFormat("HH:mm");
    // String serverCode = getContext().getParameter("serverCode");
    // String itemCode = getContext().getParameter("itemCode");
    // String taskCode = getContext().getParameter("taskCode");
    // if (StringUtils.isBlank(serverCode)){
    // return;
    // }
    // //调用接口查询数据+++++++++++++++++++++++++++++++++++++++++++++++
    // Map<String, Object> conditions = new HashMap<String, Object>();
    // conditions.put(QueryKey.APP_CODE,serverCode);
    // conditions.put(QueryKey.TASK_ID,taskCode);
    // conditions.put(QueryKey.TASK_SPLIT_ID,itemCode);
    // conditions.put(QueryKey.FLAG, "T");
    // BOABGMonBusiErrorLogBean[] busiErrLogs = QueryUtils.qryBusinessErrLogByCondition(conditions);
    // JSONArray errorArray = new JSONArray();//错单量
    // JSONArray errorXArray = new JSONArray();//错单处理X轴
    // for (BOABGMonBusiErrorLogBean bean : busiErrLogs) {
    // // errorArray.add((int)(Math.random()*1000));
    // errorArray.add(bean.getErrCnt());
    // errorXArray.add(sdfOra.format(bean.getAcqDate()));
    // }
    // JSONObject obj = new JSONObject();
    // obj.put("errorArray", errorArray);
    // obj.put("errorXArray", errorXArray);
    // this.setAjax(obj);
    // }
    
    /**
     * 查询历史数据
     * 
     * @param request
     * @throws Exception
     */
    public void queryHisDataQry(IRequestCycle request)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 页面传入日期格式
        SimpleDateFormat sdfHis = null;// 历史日期格式
        SimpleDateFormat sdfOra = null;// 页面初始化记录格式
        IDataBus queryFormBus = createData("queryForm");
        JSONObject queryFormCondition = (JSONObject)queryFormBus.getData();
        String taskCode = getContext().getParameter("taskCode").trim();
        String itemCode = getContext().getParameter("itemCode").trim();
        String startTime = getContext().getParameter("startTime");
        String endTime = getContext().getParameter("endTime");
        
        JSONObject result = new JSONObject();
        Date start = null;
        Date end = null;
        try
        {
            if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime))
            {
                start = sdf.parse(startTime.trim());
                end = sdf.parse(endTime.trim());
                // 开始时间必须小于结束时间
                if (start.after(end))
                {
                    throw new Exception("\u5f00\u59cb\u65f6\u95f4\u5fc5\u987b\u5c0f\u4e8e\u7ed3\u675f\u65f6\u95f4");
                }
                sdfHis = new SimpleDateFormat("MM-dd HH:mm:ss");// 历史记录
            }
            else
            // 如果没传开始结束时间，说明是页面初始化,初始化时开始时间为当前时间6小时前,结束时间为当前时间
            {
                Calendar cal = Calendar.getInstance();
                end = cal.getTime();
                cal.add(Calendar.HOUR, -6);
                start = cal.getTime();
                sdfOra = new SimpleDateFormat("HH:mm:ss");
            }
            String serverCode = getContext().getParameter("serverCode").trim();
            if (StringUtils.isBlank(serverCode))
            {
                return;
            }
            Map<String, Object> conditions = new HashMap<String, Object>();
            conditions.put(QueryKey.APP_CODE, serverCode);
            conditions.put(QueryKey.TASK_ID, taskCode);
            // 若有^分割符 则为两级 把地市和分片劈开传
            if (StringUtils.isNotBlank(itemCode))
            {
                if (itemCode.indexOf(SEPERATOR) > 0)
                {
                    String[] temp = itemCode.split("\\^");
                    if (temp.length == 2)
                    {
                        conditions.put(QueryKey.REGION_CODE, temp[0]);
                        conditions.put(QueryKey.TASK_SPLIT_ID, temp[1]);
                    }
                    // 若没有^分隔符 但默认分片标识为true,则将itemCode作为地市,添加默认分片0
                }
                else if (hasDefaultSplit)
                {
                    conditions.put(QueryKey.REGION_CODE, itemCode);
                    conditions.put(QueryKey.TASK_SPLIT_ID, DEFAULT_SPLIT);
                    // 若不满足前两个条件 则将itemCode作为分片标识传入
                }
                else
                {
                    conditions.put(QueryKey.TASK_SPLIT_ID, itemCode);
                }
            }
            conditions.put(QueryKey.BEGIN_DATE, start);
            conditions.put(QueryKey.END_DATE, end);
            
            // BOABGMonBusiErrorLogBean[] busiErrLogs = QueryUtils.qryBusinessErrLogByCondition(conditions);
            BOABGMonBusiLogBean[] busiLogs = QueryUtils.qryBusinessLogByCondition(conditions);
            if (null == busiLogs || busiLogs.length == 0)
            {
                result.put("busiFlag", "T");
            }
            // if(null == busiErrLogs || busiErrLogs.length == 0) {
            // obj.put("errorFlag", "T");
            // }
            JSONArray xArray = new JSONArray();// 业务处理X轴
            JSONArray handleArray = new JSONArray();// 业务处理量
            JSONArray leftArray = new JSONArray();// 业务积压量
            JSONArray speedArray = new JSONArray();// 业务处理速率
            JSONArray errorArray = new JSONArray();// 错单量
            for (BOABGMonBusiLogBean bean : busiLogs)
            {
                handleArray.add(bean.getHandleCnt());
                leftArray.add((bean.getTotalCnt() - bean.getHandleCnt()));
                long timeCost = bean.getConsumeTime() == 0 ? 1 : bean.getConsumeTime();
                speedArray.add(this.convertPointValue((float)(bean.getPerHandleCnt() * 1000.0 / timeCost)));
                // handleArray.add((int)(Math.random()*100));
                // leftArray.add((int)(Math.random()*100));
                // speedArray.add((int)(Math.random()*1000));
                errorArray.add(bean.getErrCnt());
                if (sdfHis != null)
                {
                    xArray.add(sdfHis.format(bean.getCreateDate()));
                }
                else
                {
                    xArray.add(sdfOra.format(bean.getCreateDate()));
                }
            }
            // JSONArray errorArray = new JSONArray();//错单量
            // JSONArray errorXArray = new JSONArray();//错单处理X轴
            // for(BOABGMonBusiErrorLogBean bean : busiErrLogs) {
            // // errorArray.add((int)(Math.random()*1000));
            // errorArray.add(bean.getErrCnt());
            // if(sdfHis != null) {
            // errorXArray.add(sdfHis.format(bean.getAcqDate()));
            // }
            // else {
            // errorXArray.add(sdfOra.format(bean.getAcqDate()));
            // }
            // }
            result.put("xArray", xArray);
            result.put("handleArray", handleArray);
            result.put("leftArray", leftArray);
            result.put("speedArray", speedArray);
            result.put("errorArray", errorArray);
            // obj.put("errorXArray", errorXArray);
            
            // 同时查询进程相关信息
            // queryProcess(serverCode);
            
        }
        catch (Exception e)
        {
            result.put("flag", "F");
            result.put("msg", e.getMessage());
            LOGGER.error("查询历史数据失败：", e);
        }
        this.setAjax(result);
    }
    
    /**
     * 查询进程相关信息
     * 
     * @param serverCode
     * @throws Exception
     */
    private void queryProcess(String serverCode, JSONObject jsonObject, String offset, String linage)
        throws Exception
    {
        if (StringUtils.isNotBlank(serverCode))
        {
            IAIMonServerSV serverSv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
            String hostId = serverSv.qryHostIdByServerCode(serverCode);
            // IAIMonPhysicHostSV pHostSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
            // PhysicHost host = pHostSv.getPhostByPhostId(hostId);
            IAIMonPhysicHostSV sv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
            IBOAIMonPhysicHostValue host = sv.getPhysicHostById(hostId);
            if (host == null)
            {
                throw new Exception("host is null");
            }
            IBusiProcessSV isv = (IBusiProcessSV)ServiceFactory.getService(IBusiProcessSV.class);
            JSONArray jsonProcessArr = isv.acqOneProcessKPiInfo(host, serverCode);
            // this.setProcessTabItems(jsonProcessArr);
            jsonObject.put("processTabItems", PagingUtil.convertArray2Page(jsonProcessArr, offset, linage));
            jsonObject.put("total", jsonProcessArr.size());
        }
        else
        {
            throw new Exception("serverCode is null");
        }
    }
    
    public abstract void setProcessTabItem(JSONObject processTabItem);
    
    public abstract void setSplitTableItems(JSONArray splitTableItems);
    
    public abstract void setSplitTableRowIndex(int splitTableRowIndex);
    
    public abstract void setSplitTableItem(JSONObject splitTableItem);
    
    public abstract void setProcessTabCount(long processTabCount);
    
    public abstract void setProcessTabItems(JSONArray processTabItems);
    
    public abstract void setProcessTabRowIndex(int processTabRowIndex);
    
    public abstract void setQueryForm(JSONObject queryForm);
    
    public abstract void setSplitTableCount(long splitTableCount);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus processTabItem = createData("processTabItem");
        if (processTabItem != null && !processTabItem.getDataObject().isEmpty())
        {
            setProcessTabItem(processTabItem.getDataObject());
        }
        IDataBus splitTableItem = createData("splitTableItem");
        if (splitTableItem != null && !splitTableItem.getDataObject().isEmpty())
        {
            setSplitTableItem(splitTableItem.getDataObject());
        }
        IDataBus queryForm = createData("queryForm");
        if (queryForm != null && !queryForm.getDataObject().isEmpty())
        {
            setQueryForm(queryForm.getDataObject());
        }
        initExtend(cycle);
    }
    
}
