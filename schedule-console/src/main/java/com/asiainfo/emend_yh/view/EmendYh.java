package com.asiainfo.emend_yh.view;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBillingCycleValue;
import com.asiainfo.batchwriteoff.util.BWFBillingCycleUtil;
import com.asiainfo.emend_yh.ivalues.IBOAmTaskRelyValue;
import com.asiainfo.emend_yh.util.EmendYhUtil;

/**
 * Created by SMT 
 * Copyright: Copyright(c) 2015 Asiainfo-Linkage
 */
/**
 * 
 * @Title: 月对账页面
 * @Description:
 * @Author:szh
 * @Since:2015年8月31日
 * @Version:1.1.0
 */
public abstract class EmendYh extends AppPage
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
    
    /**
     * 前台状态翻译
     * 
     * @param type
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public String doTranslate(String type, String val)
        throws RemoteException, Exception
    {
        String text = null;
        if ("STATE".equals(type))
        {
            if ("X".equals(val))
            {
                text = "异常";
            }
            else if ("C".equals(val))
            {
                text = "执行中";
            }
            else if ("F".equals(val))
            {
                text = "正确结束";
            }
        }
        return text;
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
    
    public void qryEmendYh(IRequestCycle cycle)
        throws Exception
    {
        IBOBsBillingCycleValue billingCycleValue = BWFBillingCycleUtil.getCurBillingCycle("570", -1);
        LinkedHashMap<String, IBOAmTaskRelyValue> map = new LinkedHashMap<String, IBOAmTaskRelyValue>();
        map = EmendYhUtil.qryTaskInfo();
        if (map != null && map.size() > 0)
        {
            
        }
        JSONArray array = new JSONArray();
        for (int m = 0; m < 33; m++)
        {
            JSONObject obj = new JSONObject();
            array.add(obj);
        }
        ((JSONObject)array.get(0)).put("column0", "地市");
        ((JSONObject)array.get(1)).put("column0", "账单表清理");
        ((JSONObject)array.get(2)).put("column0", "对账单预处理");
        ((JSONObject)array.get(3)).put("column0", "资金封存");
        ((JSONObject)array.get(4)).put("column0", "资金封存2，异地入账明细修复");
        ((JSONObject)array.get(5)).put("column0", "月基本费详单 ");
        ((JSONObject)array.get(6)).put("column0", "月基本费详单文件生成");
        ((JSONObject)array.get(7)).put("column0", "月基本费详单文件传输");
        ((JSONObject)array.get(8)).put("column0", "三级帐单融合");
        ((JSONObject)array.get(9)).put("column0", "群组消费明细生成");
        ((JSONObject)array.get(10)).put("column0", "群组消费明细2,异地群组消费明细修复");
        ((JSONObject)array.get(11)).put("column0", "对账单处理");
        ((JSONObject)array.get(12)).put("column0", "流量账单");
        ((JSONObject)array.get(13)).put("column0", "群组优惠明细");
        ((JSONObject)array.get(14)).put("column0", "文件生成_个人");
        ((JSONObject)array.get(15)).put("column0", "139邮箱用户列表生成");
        ((JSONObject)array.get(16)).put("column0", "邮寄对账单文件生成_除575");
        ((JSONObject)array.get(17)).put("column0", "邮寄对账单文件生成_仅575");
        ((JSONObject)array.get(18)).put("column0", "邮寄对账单文件压缩");
        ((JSONObject)array.get(19)).put("column0", "邮寄对账单文件传送");
        ((JSONObject)array.get(20)).put("column0", "139对账单文件生成");
        ((JSONObject)array.get(21)).put("column0", "139对账单文件压缩");
        ((JSONObject)array.get(22)).put("column0", "139对账单文件传送");
        ((JSONObject)array.get(23)).put("column0", "半年话费分析");
        ((JSONObject)array.get(24)).put("column0", "个人客户价税分离月对账单");
        ((JSONObject)array.get(25)).put("column0", "个人客户价税分离虚用户分摊");
        ((JSONObject)array.get(26)).put("column0", "个人客户价税分离异地统一支付用户转移");
        ((JSONObject)array.get(27)).put("column0", "PBX分机账单生成");
        ((JSONObject)array.get(28)).put("column0", "集团对账单");
        ((JSONObject)array.get(29)).put("column0", "文件生成_集团");
        ((JSONObject)array.get(30)).put("column0", "集团客户价税分离月对账单");
        ((JSONObject)array.get(31)).put("column0", "集团客户价税分离虚用户分摊");
        ((JSONObject)array.get(32)).put("column0", "集团客户价税分离异地统一支付用户转移");
        
        // 显示：地市名（地市编号）
        for (int i = 0; i < 11; i++)
        {
            String regionId = "" + (570 + i);
            ((JSONObject)array.get(0)).put("column" + (i + 1), getStaticMap().get(KEY_CITY).get(regionId) + "("
                + regionId + ")");
        }
        // 显示地市状态
        if (map != null && map.size() > 0)
        {
            int j = 0;
            for (String key : map.keySet())
            {
                for (int i = 0; i < 11; i++)
                {
                    String regionId = "" + (570 + i);
                    ((JSONObject)array.get(j + 1)).put("column" + (i + 1),
                        EmendYhUtil.qryTaskStateByCondition(key, regionId, billingCycleValue.getBillingCycleId()));
                }
                j++;
            }
        }
        this.setEmendYhItems(array);
    }
    
    public abstract void setEmendYhCount(long emendYhCount);
    
    public abstract void setEmendYhItem(JSONObject emendYhItem);
    
    public abstract void setEmendYhItems(JSONArray emendYhItems);
    
    public abstract void setEmendYhRowIndex(int emendYhRowIndex);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("emendYh", "com.asiainfo.emend_yh.bo.BOAmTaskRely");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus emendYhItem = createData("emendYhItem");
        if (emendYhItem != null && !emendYhItem.getDataObject().isEmpty())
        {
            setEmendYhItem(emendYhItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
