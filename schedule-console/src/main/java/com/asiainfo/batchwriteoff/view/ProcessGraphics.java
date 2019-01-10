package com.asiainfo.batchwriteoff.view;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.common.i18n.CrmLocaleFactory;
import com.ai.common.util.ExceptionUtil;
import com.ai.common.util.TimeUtil;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfFlowMonValue;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBillingCycleValue;
import com.asiainfo.batchwriteoff.service.interfaces.IBWFCommonBillingSV;
import com.asiainfo.batchwriteoff.util.AmsUtil;
import com.asiainfo.batchwriteoff.util.BWFBillingCycleUtil;
import com.asiainfo.batchwriteoff.util.GraphicsUtil;
import com.asiainfo.monitor.exeframe.configmgr.util.ConfigPageUtil;

/**
 * 批销流程图相关 Created by SMT Copyright: Copyright(c) 2015 Asiainfo-Linkage
 */
public abstract class ProcessGraphics extends AppPage
{
    
    private static final Logger LOGGER = Logger.getLogger(ProcessGraphics.class);
    
    /** 静态数据 */
    private static Map<String, Map<String, String>> staticMap = null;
    
    /** 静态数据key：地市 */
    private static final String KEY_CITY = "city";
    
    /** 静态数据key：账期 */
    private static final String KEY_BILL_DATE = "billDate";
    
    /** 静态数据key：运行模式 */
    private static final String KEY_MODE = "mode";
    
    public IDataBus getCityList()
    {
        Map<String, String> cityMap = getStaticMap().get(KEY_CITY);
        IDataBus bus = ConfigPageUtil.getSelectListSort(cityMap);
        return bus;
    }
    
    public IDataBus getBillDateList()
    {
        Map<String, String> billDateMap = getStaticMap().get(KEY_BILL_DATE);
        IDataBus bus = ConfigPageUtil.getSelectListSort(billDateMap);
        return bus;
    }
    
    public IDataBus getModeList()
    {
        Map<String, String> modeMap = getStaticMap().get(KEY_MODE);
        IDataBus bus = ConfigPageUtil.getSelectListSort(modeMap);
        return bus;
    }
    
    /**
     * 根据前台参数生成节点xml
     * 
     * @param cycle
     * @throws Exception
     */
    public void createXml(IRequestCycle cycle)
        throws Exception
    {
        JSONObject result = new JSONObject();
        String regionId = getContext().getParameter("regionId");
        long billDay = Long.parseLong(getContext().getParameter("billDay"));
        String runMode = getContext().getParameter("runMode");
        try
        {
            String processXml = GraphicsUtil.createBatchWriteOffFlow(regionId, billDay, runMode);
            // String processXml =
            // "<?xml version='1.0' encoding='UTF-8'?><process name='批销'><start name='START' value='100000' left='20' top='100' width='110' height='50' isDtl='N'><transition to='100100' left='20' top='180'/></start><task name='初始化环境' value='100100' isDtl='N' left='20' top='180' width='110' height='50'><transition left='145' top='180' to='100200'/></task><task name='置出账标识开始' value='100200' isDtl='N' left='220' top='180' width='110' height='50'><transition left='345' top='180' to='100300'/></task><task name='停实时销账kill' value='100300' isDtl='N' left='420' top='180' width='110' height='50'><transition left='545' top='120' to='100600'/><transition left='545' top='240' to='100500'/></task><task name='批量销账' value='100500' isDtl='Y' dtlUrl='module.batchWriteOff.QueryDetail' left='620' top='240' width='110' height='50'><transition left='745' top='180' to='100700'/></task>  <task name='SQLLOAD数据' value='100600' isDtl='Y' dtlUrl='module.batchWriteOff.QueryDetail' left='620' top='120' width='110' height='50'><transition left='745' top='180' to='100700'/></task>  <task name='稽核' value='100700' isDtl='Y' dtlUrl='module.batchWriteOff.Detail' left='820' top='180' width='110' height='50'><transition left='945' top='180' to='100800'/></task>  <task name='换表切换账期' value='100800' isDtl='N' left='1020' top='180' width='110' height='50'><transition left='1020' top='360' to='100900'/></task>  <task name='数据进入历史表' value='100900' isDtl='Y' dtlUrl='module.batchWriteOff.QueryDetail' left='1020' top='360' width='110' height='50'><transition left='895' top='360' to='101000'/></task>  <task name='起实时销账' value='101000' isDtl='N' left='820' top='360' width='110' height='50'><transition left='695' top='360' to='101100'/></task>  <task name='置出账标识完成' value='101100' isDtl='N' left='620' top='360' width='110' height='50'><transition left='495' top='360' to='101200'/></task>  <task name='表分析' value='101200' isDtl='Y' dtlUrl='module.batchWriteOff.QueryDetail' left='420' top='360' width='110' height='50'><transition left='295' top='360' to='101300'/></task>  <task name='批销结束' value='101300' isDtl='N' left='220' top='360' width='110' height='50'><transition top='410' left='220' to='200000'/></task>  <end name='END' value='200000' top='450' left='220' width='110' height='50' isDtl='N'/></process>";
            if (StringUtils.isBlank(processXml))
            {
                result.put("retVal", "N");
                result.put("retMsg", CrmLocaleFactory.getResource("AMS2300330"));// 批销结点配置不全，请检查！
            }
            else
            {
                result.put("retVal", "Y");
                result.put("processXml", processXml);
                IBOBsBillingCycleValue billingCycleValue = BWFBillingCycleUtil.getCurBillingCycle(regionId, billDay);
                if (billingCycleValue == null)
                {
                    // 未获取到当前帐期！
                    ExceptionUtil.throwBusinessException("AMS2300219");
                }
                long billingCycleId = billingCycleValue.getBillingCycleId();
                Timestamp currDate = AmsUtil.getDBSysdate();
                // long billingCycleId = 0;
                // Timestamp currDate=new Timestamp(System.currentTimeMillis());
                long tempBillingCycleId = Long.parseLong(TimeUtil.getYYYYMM(currDate));
                if (billingCycleId == tempBillingCycleId)
                {
                    billingCycleId =
                        Long.parseLong(TimeUtil.getYYYYMM(TimeUtil.addOrMinusMonth(currDate.getTime(), -1)));
                }
                result.put("billingCycleId", "" + billingCycleId);
            }
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                e.printStackTrace();
                log.debug(e);
            }
            result.put("retVal", "N");
            result.put("retMsg", e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
        }
        finally
        {
            this.setAjax(result);
        }
    }
    
    /**
     * 监控节点状态
     * 
     * @param cycle
     * @throws Exception
     */
    public void monNode(IRequestCycle cycle)
        throws Exception
    {
        JSONObject result = new JSONObject();
        String regionId = getContext().getParameter("regionId");
        String billDay = getContext().getParameter("billDay");
        String billingCycleIdStr = getContext().getParameter("billingCycleId");
        if (StringUtils.isBlank(billingCycleIdStr))
        {
            result.put("retValue", "N");
            result.put("retMsg", "billingCycleId is null");
            return;
        }
        long billingCycleId = Long.parseLong(billingCycleIdStr);
        try
        {
            IBWFCommonBillingSV commonSV = (IBWFCommonBillingSV)ServiceFactory.getService(IBWFCommonBillingSV.class);
            IBOBsBatchWfFlowMonValue[] batchWfFlowMonValues =
                commonSV.getBatchWfFlowMonValues(regionId, Long.parseLong(billDay), billingCycleId);
            if (batchWfFlowMonValues != null && batchWfFlowMonValues.length > 0)
            {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0, j = batchWfFlowMonValues.length; i < j; i++)
                {
                    map.put("" + batchWfFlowMonValues[i].getWfFlowId(), batchWfFlowMonValues[i].getState());
                }
                result.put("jsonStr", JSONObject.fromObject(map));
                result.put("jsonStrCount", "" + batchWfFlowMonValues.length);
            }
            result.put("retVal", "Y");
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e);
            }
            result.put("retVal", "N");
            result.put("retMsg", e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
        }
        finally
        {
            this.setAjax(result);
        }
    }
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    private static Map<String, Map<String, String>> getStaticMap()
    {
        if (staticMap == null)
        {
            initStaticMap();
        }
        return staticMap;
    }
    
    private static void initStaticMap()
    {
        staticMap = new HashMap<String, Map<String, String>>();
        Map<String, String> cityMap = new HashMap<String, String>();
        cityMap.put("570", "\u8862\u5dde");// 衢州
        cityMap.put("571", "\u676d\u5dde");// 杭州
        cityMap.put("572", "\u6e56\u5dde");// 湖州
        cityMap.put("573", "\u5609\u5174");// 嘉兴
        cityMap.put("574", "\u5b81\u6ce2");// 宁波
        cityMap.put("575", "\u7ecd\u5174");// 绍兴
        cityMap.put("576", "\u53f0\u5dde");// 台州
        cityMap.put("577", "\u6e29\u5dde");// 温州
        cityMap.put("578", "\u4e3d\u6c34");// 丽水
        cityMap.put("579", "\u91d1\u534e");// 金华
        cityMap.put("580", "\u821f\u5c71");// 舟山
        staticMap.put(KEY_CITY, cityMap);
        Map<String, String> modeDateMap = new HashMap<String, String>();
        modeDateMap.put("CONFIG", "\u914d\u7f6e");// 配置
        modeDateMap.put("HAND", "\u624b\u52a8");// 手动
        staticMap.put(KEY_MODE, modeDateMap);
        Map<String, String> billDateMap = new HashMap<String, String>();
        for (int i = 1; i <= 31; i++)
        {
            billDateMap.put(String.valueOf(i), i + "\u65e5");
        }
        staticMap.put(KEY_BILL_DATE, billDateMap);
        
    }
    
    public abstract void setPwdInput(JSONObject pwdInput);
    
    public abstract void setChangPWD(JSONObject changPWD);
    
    @Override
    protected void initPageAttrs()
    {
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus pwdInput = createData("pwdInput");
        if (pwdInput != null && !pwdInput.getDataObject().isEmpty())
        {
            setPwdInput(pwdInput.getDataObject());
        }
        IDataBus changPWD = createData("changPWD");
        if (changPWD != null && !changPWD.getDataObject().isEmpty())
        {
            setChangPWD(changPWD.getDataObject());
        }
        initExtend(cycle);
    }
    
}
