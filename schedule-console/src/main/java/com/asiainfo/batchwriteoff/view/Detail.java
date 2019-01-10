package com.asiainfo.batchwriteoff.view;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.common.util.TimeUtil;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfParaValue;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfPsMonValue;
import com.asiainfo.batchwriteoff.service.interfaces.IBWFCommonBillingSV;

/**
 * Created by SMT Copyright: Copyright(c) 2015 Asiainfo-Linkage
 */
public abstract class Detail extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 获取明细信息
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public void loadDetailData(IRequestCycle request)
        throws Exception
    {
        String regionId = getContext().getParameter("regionId");
        Long billDay = Long.parseLong(getContext().getParameter("billDay"));
        Long billingCycleId = Long.parseLong(getContext().getParameter("billingCycleId"));
        
        Date temp =
            TimeUtil.addOrMinusMonth(TimeUtil.getTimstampByString(getContext().getParameter("billingCycleId"), "yyyyMM")
                .getTime(),
                -1);
        long lastBillingCycleId = Long.parseLong(TimeUtil.getYYYYMM(temp));
        
        // CenterFactory.setCenterInfoByTypeAndValue("RegionId", regionId);
        IBWFCommonBillingSV commonSV = (IBWFCommonBillingSV)ServiceFactory.getService(IBWFCommonBillingSV.class);
        IBOBsBatchWfParaValue[] wfParaValues = commonSV.getWfParaValues(regionId, "");
        long writeOffWfFlowId = 0L;
        if (wfParaValues != null && wfParaValues.length > 0)
        {
            for (int i = 0; i < wfParaValues.length; i++)
            {
                if ("WRITE_OFF_WF_FLOW_ID".equalsIgnoreCase(wfParaValues[i].getParaCode()))
                {
                    writeOffWfFlowId = Long.parseLong(wfParaValues[i].getParaValue());
                }
            }
        }
        long curTotalDueAmount = 0L;// 当月账单总费用
        long hisTotalDueAmount = 0L;// 当月历史账单总费用
        long beforTotalBaAmount = 0L;// 批销前账本总金额
        long writeOffAmount = 0L;// 销账金额
        long afterTotalBaAmount = 0L;// 批销后账本总金额
        long afterTotalDueAmount = 0L;// 批销后账单总欠费
        
        IBOBsBatchWfPsMonValue[] writeOffPsMonValues =
            commonSV.getWfPsMonValues(regionId, billingCycleId, billDay, writeOffWfFlowId);
        if (writeOffPsMonValues != null && writeOffPsMonValues.length > 0)
        {
            for (int i = 0; i < writeOffPsMonValues.length; i++)
            {
                curTotalDueAmount += writeOffPsMonValues[i].getTotalCurAmount();
                hisTotalDueAmount += writeOffPsMonValues[i].getTotalHisAmount();
                beforTotalBaAmount += writeOffPsMonValues[i].getTotalBalanceAmount();
                writeOffAmount += writeOffPsMonValues[i].getTotalBillAmount();
            }
        }
        long lastWriteOffAmount = 1L;// 上月销账金额
        IBOBsBatchWfPsMonValue[] tempMonValues =
            commonSV.getWfPsMonValues(regionId, lastBillingCycleId, billDay, writeOffWfFlowId);
        if (tempMonValues != null && tempMonValues.length > 0)
        {
            for (int i = 0; i < tempMonValues.length; i++)
            {
                lastWriteOffAmount += tempMonValues[i].getTotalBillAmount();
            }
        }
        
        afterTotalBaAmount = beforTotalBaAmount - writeOffAmount;
        afterTotalDueAmount = curTotalDueAmount + hisTotalDueAmount - writeOffAmount;
        
        JSONArray jsonArr = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("BEFORE_BALANCE", beforTotalBaAmount);
        object.put("BEFORE_BILL_AMOUNT", curTotalDueAmount);
        object.put("BEFORE_HIS_AMOUNT", hisTotalDueAmount);
        object.put("WRITEOFF_AMOUNT", writeOffAmount);
        object.put("AFTER_BALANCE", afterTotalBaAmount);
        object.put("AFTER_BILL_AMOUNT", afterTotalDueAmount);
        object.put("LAST_WRITEOFF_AMOUNT", lastWriteOffAmount);
        object.put("RATE", (writeOffAmount - lastWriteOffAmount) / lastWriteOffAmount);
        jsonArr.add(object);
        this.setProcessListItems(jsonArr);
        this.setListSize(jsonArr.size());
    }
    
    /**
     * 前台状态翻译
     * 
     * @param type
     * @param key
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public String doTranslate(String type, String val)
        throws RemoteException, Exception
    {
        String text = null;
        
        if ("RATE".equals(type))
        {
            text =
                new BigDecimal(Long.parseLong(val) * 100 * 100).divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP)
                    .toString()
                    + "%";
        }
        else
        {
            text = fmMoney(Long.parseLong(val));
        }
        return text;
    }
    
    /**
     * 格式化为金额0.00格式
     * 
     * @param amount 金额单位：分
     * @return
     * @author jiajj
     */
    public static String fmMoney(long amount)
    {
        return new BigDecimal(amount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).toString();
    }
    
    public abstract void setProcessListRowIndex(int processListRowIndex);
    
    public abstract void setProcessListItems(JSONArray processListItems);
    
    public abstract void setProcessListCount(long processListCount);
    
    public abstract void setProcessListItem(JSONObject processListItem);
    
    public abstract void setListSize(int i);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus processListItem = createData("processListItem");
        if (processListItem != null && !processListItem.getDataObject().isEmpty())
        {
            setProcessListItem(processListItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
