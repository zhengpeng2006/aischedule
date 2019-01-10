package com.asiainfo.batchwriteoff.view;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.common.util.ExceptionUtil;
import com.ai.common.util.TimeUtil;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBillingCycleValue;
import com.asiainfo.batchwriteoff.ivalues.IQBOAmBatchWfAllRegionInfoValue;
import com.asiainfo.batchwriteoff.service.interfaces.IBWFCommonBillingSV;
import com.asiainfo.batchwriteoff.util.AmsUtil;
import com.asiainfo.batchwriteoff.util.BWFBillingCycleUtil;

/**
 * Created by SMT Copyright: Copyright(c) 2015 Asiainfo-Linkage
 */
public abstract class AllRegionDisplay extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /** 静态数据 */
    private static Map<String, Map<String, String>> staticMap = null;
    
    /** 静态数据key：地市 */
    private static final String KEY_CITY = "city";
    
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
    }
    
    public void qryWriteOffInfo(IRequestCycle cycle)
        throws Exception
    {
        String regionId = getContext().getParameter("regionId");
        long billDay = Long.parseLong(getContext().getParameter("billDay"));
        IBOBsBillingCycleValue billingCycleValue = BWFBillingCycleUtil.getCurBillingCycle(regionId, billDay);
        if (billingCycleValue == null)
        {
            // 未获取到当前帐期！
            ExceptionUtil.throwBusinessException("AMS2300219");
        }
        long billingCycleId = billingCycleValue.getBillingCycleId();
        Timestamp currDate = AmsUtil.getDBSysdate();
        long tempBillingCycleId = Long.parseLong(TimeUtil.getYYYYMM(currDate));
        if (billingCycleId == tempBillingCycleId)
        {
            billingCycleId = Long.parseLong(TimeUtil.getYYYYMM(TimeUtil.addOrMinusMonth(currDate.getTime(), -1)));
        }
        IBWFCommonBillingSV commonSV = (IBWFCommonBillingSV)ServiceFactory.getService(IBWFCommonBillingSV.class);
        IQBOAmBatchWfAllRegionInfoValue[] wfAllRegionInfo =
            commonSV.getAllBatchWfAllRegionInfoValues(Integer.parseInt(billingCycleId + ""));
        JSONArray array = new JSONArray();
        for (int m = 0; m < 15; m++)
        {
            JSONObject obj = new JSONObject();
            array.add(obj);
        }
        ((JSONObject)array.get(0)).put("column0", "地市");
        ((JSONObject)array.get(1)).put("column0", "流程开始");
        ((JSONObject)array.get(2)).put("column0", "初始化环境");
        ((JSONObject)array.get(3)).put("column0", "置出账标识开始");
        ((JSONObject)array.get(4)).put("column0", "停实时销账");
        ((JSONObject)array.get(5)).put("column0", "批量销账");
        ((JSONObject)array.get(6)).put("column0", "SQLLOAD销账");
        ((JSONObject)array.get(7)).put("column0", "稽核");
        ((JSONObject)array.get(8)).put("column0", "换表切换账期");
        ((JSONObject)array.get(9)).put("column0", "数据进入历史表");
        ((JSONObject)array.get(10)).put("column0", "置出账标识完成");
        ((JSONObject)array.get(11)).put("column0", "起实时销账");
        ((JSONObject)array.get(12)).put("column0", "表分析");
        ((JSONObject)array.get(13)).put("column0", "批销结束");
        ((JSONObject)array.get(14)).put("column0", "流程结束");
        for (int i = 0; i < wfAllRegionInfo.length; i++)
        {
            ((JSONObject)array.get(0)).put("column" + (i + 1),
                getStaticMap().get(KEY_CITY).get(wfAllRegionInfo[i].getRegionId()) + "("
                    + wfAllRegionInfo[i].getRegionId() + ")");
            ((JSONObject)array.get(1)).put("column" + (i + 1), wfAllRegionInfo[i].getStart());
            ((JSONObject)array.get(2)).put("column" + (i + 1), wfAllRegionInfo[i].getInit());
            ((JSONObject)array.get(3)).put("column" + (i + 1), wfAllRegionInfo[i].getSetCtrlflagBegin());
            ((JSONObject)array.get(4)).put("column" + (i + 1), wfAllRegionInfo[i].getKillRealWf());
            ((JSONObject)array.get(5)).put("column" + (i + 1), wfAllRegionInfo[i].getBatchWriteoff());
            ((JSONObject)array.get(6)).put("column" + (i + 1), wfAllRegionInfo[i].getSqlloadData());
            ((JSONObject)array.get(7)).put("column" + (i + 1), wfAllRegionInfo[i].getAudit());
            ((JSONObject)array.get(8)).put("column" + (i + 1), wfAllRegionInfo[i].getRenameTable());
            ((JSONObject)array.get(9)).put("column" + (i + 1), wfAllRegionInfo[i].getInsertData());
            ((JSONObject)array.get(10)).put("column" + (i + 1), wfAllRegionInfo[i].getSetCtrlflagFinish());
            ((JSONObject)array.get(11)).put("column" + (i + 1), wfAllRegionInfo[i].getRunRealWf());
            ((JSONObject)array.get(12)).put("column" + (i + 1), wfAllRegionInfo[i].getTableAnalyse());
            ((JSONObject)array.get(13)).put("column" + (i + 1), wfAllRegionInfo[i].getBatchWriteoffFinish());
            ((JSONObject)array.get(14)).put("column" + (i + 1), wfAllRegionInfo[i].getEnd());
        }
        this.setWriteOffInfoItems(array);
    }
    
    public abstract void setWriteOffInfoItem(JSONObject writeOffInfoItem);
    
    public abstract void setWriteOffInfoItems(JSONArray writeOffInfoItems);
    
    public abstract void setWriteOffInfoCount(long writeOffInfoCount);
    
    public abstract void setWriteOffInfoRowIndex(int writeOffInfoRowIndex);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus writeOffInfoItem = createData("writeOffInfoItem");
        if (writeOffInfoItem != null && !writeOffInfoItem.getDataObject().isEmpty())
        {
            setWriteOffInfoItem(writeOffInfoItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
