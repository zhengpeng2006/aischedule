package com.asiainfo.monitor.exeframe.monitorItem.view;

import java.util.HashMap;
import java.util.Iterator;
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
import com.asiainfo.monitor.busi.service.interfaces.IAPIControlStatSV;
import com.asiainfo.monitor.busi.web.SVMethodStatAction;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.tools.common.SimpleResult;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class SvInvokeStatic extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public void loadTreeData(IRequestCycle cycle)
        throws Exception
    {
        try
        {
            // 获取角色标识
            TreeParam param = TreeParam.getTreeParam(cycle);
            
            // 同步全量加载树
            TreeBean[] treeBeanArr = CommonSvUtil.getTreeItems(4);
            
            TreeItem[] root = TreeHelper.getWholeTreeData(treeBeanArr);
            IData treeNodes = TreeFactory.buildTreeData(param, root);
            
            getContext().setAjax(treeNodes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public abstract void setServiceInfoTabCount(long serviceInfoTabCount);
    
    public abstract void setServiceInfoTabItems(JSONArray serviceInfoTabItems);
    
    public abstract void setQrySvForm(JSONObject qrySvForm);
    
    public abstract void setServiceInfoTabRowIndex(int serviceInfoTabRowIndex);
    
    public abstract void setServiceInfoTabItem(JSONObject serviceInfoTabItem);
    
    @Override
    protected void initPageAttrs()
    {
        // bindBoName("serviceInfoTab", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonGroup");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus qrySvForm = createData("qrySvForm");
        if (qrySvForm != null && !qrySvForm.getDataObject().isEmpty())
        {
            setQrySvForm(qrySvForm.getDataObject());
        }
        IDataBus serviceInfoTabItem = createData("serviceInfoTabItem");
        if (serviceInfoTabItem != null && !serviceInfoTabItem.getDataObject().isEmpty())
        {
            setServiceInfoTabItem(serviceInfoTabItem.getDataObject());
        }
        initExtend(cycle);
    }
    
    /**
     * 启动监控服务
     * 
     * @param cycle
     * @throws Exception
     * @throws
     */
    public void startMonitor(IRequestCycle cycle)
        throws Exception
    {
        // 启动周期时间
        String second = getContext().getParameter("second");
        String appId = getContext().getParameter("appId");
        IAPIControlStatSV controlStatSv = (IAPIControlStatSV)ServiceFactory.getService(IAPIControlStatSV.class);
        String[] appIdArr = {appId};
        // 启动统计服务
        List retList = controlStatSv.startSVMethod(appIdArr, second);
        
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("SUCC_FLAG", "F");// 默认启动失败
        
        // 查看是否启动成功
        if (null != retList && retList.size() > 0)
        {
            SimpleResult result = (SimpleResult)retList.get(0);
            if (result.isSucc())
            {
                jsonObj.put("SUCC_FLAG", "T");
            }
        }
        // 结果返回到前台
        this.setAjax(jsonObj);
        
    }
    
    /**
     * 启动监控服务
     * 
     * @param cycle
     */
    public void stopMonitor(IRequestCycle cycle)
        throws Exception
    {
        // 启动周期时间
        String appId = getContext().getParameter("appId");
        IAPIControlStatSV controlStatSv = (IAPIControlStatSV)ServiceFactory.getService(IAPIControlStatSV.class);
        String[] appIdArr = {appId};
        List retList = controlStatSv.stopSVMethod(appIdArr);
        
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("SUCC_FLAG", "F");// 默认关闭失败
        
        // 查看是否关闭成功
        if (null != retList && retList.size() > 0)
        {
            SimpleResult result = (SimpleResult)retList.get(0);
            if (result.isSucc())
            {
                jsonObj.put("SUCC_FLAG", "T");
            }
        }
        // 结果返回到前台
        this.setAjax(jsonObj);
        
    }
    
    /**
     * 获取监控服务状态
     * 
     * @param cycle
     */
    public void fetchSVMethodState(IRequestCycle cycle)
        throws Exception
    {
        String appId = getContext().getParameter("appId");
        SVMethodStatAction svMethodAction = new SVMethodStatAction();
        
        JSONObject retObj = new JSONObject();
        retObj.put("code", "F");
        if (!StringUtils.isBlank(appId))
        {
            boolean state = svMethodAction.fetchSVMethodState(appId);
            if (state)
            {
                retObj.put("code", "T");
            }
        }
        
        this.setAjax(retObj);
    }
    
    /**
     * 根据条件查询统计服务信息
     * 
     * @param cycle
     */
    public void qryServiceInfo(IRequestCycle cycle)
        throws Exception
    {
        String appId = getContext().getParameter("appId");
        String className = getContext().getParameter("className");
        String methodName = getContext().getParameter("methodName");
        
        IDataBus qryformBus = createData("qrySvForm");
        
        int start = Integer.parseInt(qryformBus.getContext().getPaginStart() + "");
        int end = Integer.parseInt(qryformBus.getContext().getPaginEnd() + "");
        
        SVMethodStatAction svAction = new SVMethodStatAction();
        List methodInfoList = svAction.getSVMehtodInfo(appId, className, methodName, start, end);
        
        // 测试数据
        // Map itemContainer = new HashMap();
        // itemContainer.put("SERVER_ID", "25");
        // itemContainer.put("SERVER_NAME", "sex-test-monitor");
        // itemContainer.put("APPSERVER_ID", "25");
        // itemContainer.put("APPSERVER_NAME", "sex-text-monitor");
        // itemContainer.put("RMIURL", "xxxx");
        // itemContainer.put("CLASSNAME", "ai.com.test");
        // itemContainer.put("METHODNAME", "getServices");
        // itemContainer.put("MIN", 12);
        // itemContainer.put("MAX", 14);
        // itemContainer.put("AVG", 13);
        // itemContainer.put("TOTALCOUNT", 129);
        // itemContainer.put("SUCCESSCOUNT", 1290);
        // itemContainer.put("FAILCOUNT", 2);
        // itemContainer.put("LASTUSETIME", new Long(123000));
        // itemContainer.put("TOTALUSETIME", new Long(125000));
        // itemContainer.put("LAST", Util.formatDateFromLog(126571000));
        // itemContainer.put("PAGE_TOTAL_CNT", 1);
        // methodInfoList.add(itemContainer);
        
        JSONArray jsonArr = new JSONArray();
        JSONObject jsonObj = null;
        Map itemMap = null;
        int pageTotalCnt = 1;
        for (Object object : methodInfoList)
        {
            itemMap = (HashMap)object;
            pageTotalCnt = Integer.parseInt(itemMap.get("PAGE_TOTAL_CNT").toString());
            Iterator it = itemMap.keySet().iterator();
            jsonObj = new JSONObject();
            while (it.hasNext())
            {
                String key = it.next().toString();
                jsonObj.put(key, itemMap.get(key));
            }
            jsonArr.add(jsonObj);
        }
        
        this.setServiceInfoTabItems(jsonArr);
        
        this.setServiceInfoTabCount(pageTotalCnt);
        
    }
    
}
