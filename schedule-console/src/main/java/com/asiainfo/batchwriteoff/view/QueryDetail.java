package com.asiainfo.batchwriteoff.view;

import java.text.SimpleDateFormat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.common.util.TimeUtil;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.batchwriteoff.ivalues.IBOAmBatchWfThreadMonValue;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfFlowMonValue;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfFlowValue;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfPsMonValue;
import com.asiainfo.batchwriteoff.service.interfaces.IBWFCommonBillingSV;
import com.asiainfo.batchwriteoff.util.AmsUtil;
import com.asiainfo.batchwriteoff.util.BillHelper;

/**
 * Created by SMT Copyright: Copyright(c) 2015 Asiainfo-Linkage
 */
public abstract class QueryDetail extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 同时刷新进程和线程信息
     * 
     * @param request
     * @throws Exception
     */
    public void loadQueryDetailData(IRequestCycle request)
        throws Exception
    {
        String regionId = getContext().getParameter("regionId");
        int billDay = Integer.parseInt(getContext().getParameter("billDay"));
        long wfFlowId = Long.parseLong(getContext().getParameter("taskId"));
        int billingCycleId = Integer.parseInt(getContext().getParameter("billingCycleId"));
        
        // long wfFlowId 就是taskId
        String wfProcessId = "";
        IBWFCommonBillingSV sv = (IBWFCommonBillingSV)ServiceFactory.getService(IBWFCommonBillingSV.class);
        
        // 获取进程信息
        IBOBsBatchWfPsMonValue[] psValues = sv.qryBatchWfPsMonValues(regionId, billingCycleId, billDay, wfFlowId);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONArray jsonArr = new JSONArray();
        if (psValues != null && psValues.length > 0)
        {
            wfProcessId = psValues[0].getWfProcessId();
            for (int i = 0; i < psValues.length; i++)
            {
                IBOBsBatchWfPsMonValue value = psValues[i];
                JSONObject object = new JSONObject();
                object.put("WF_FLOW_ID", value.getWfFlowId());
                object.put("REGION_ID", value.getRegionId());
                object.put("WF_PROCESS_ID", value.getWfProcessId());
                object.put("BILLING_CYCLE_ID", value.getBillingCycleId());
                object.put("BILL_DAY", value.getBillDay());
                String startDate = formatter.format(value.getStartDate());
                object.put("START_DATE", startDate);
                object.put("TOTAL_COUNT", value.getTotalCount());
                object.put("DEAL_COUNT", value.getDealCount());
                if (value.getTotalCount() > 0)
                {
                    object.put("PERCENT", BillHelper.fmPercent(value.getDealCount(), value.getTotalCount()));
                }
                String finishDate = formatter.format(value.getFinishDate());
                object.put("FINISH_DATE", finishDate);
                object.put("STATE", value.getState());
                String stateDate = formatter.format(value.getStateDate());
                object.put("STATE_DATE", stateDate);
                jsonArr.add(object);
            }
        }
        // 获取线程信息
        IBOAmBatchWfThreadMonValue[] threadValues =
            sv.qryBatchWfThreadMonValues(regionId, billingCycleId, billDay, wfFlowId, wfProcessId);
        JSONArray jsonThreadArr = new JSONArray();
        if (threadValues != null && threadValues.length > 0)
        {
            for (int i = 0; i < threadValues.length; i++)
            {
                IBOAmBatchWfThreadMonValue threadValue = threadValues[i];
                JSONObject object = new JSONObject();
                object.put("WF_FLOW_ID", threadValue.getWfFlowId());
                object.put("WF_THREAD_ID", threadValue.getWfThreadId());
                object.put("REGION_ID", threadValue.getRegionId());
                object.put("ACCT_MODE", threadValue.getAcctMode());
                object.put("MODE_VALUE", threadValue.getModeValue());
                object.put("WF_PROCESS_ID", threadValue.getWfProcessId());
                object.put("BILLING_CYCLE_ID", threadValue.getBillingCycleId());
                object.put("BILL_DAY", threadValue.getBillDay());
                String startDateThread = formatter.format(threadValue.getStartDate());
                object.put("START_DATE", startDateThread);
                object.put("TOTAL_COUNT", threadValue.getTotalCount());
                object.put("DEAL_COUNT", threadValue.getDealCount());
                if (threadValue.getTotalCount() > 0)
                {
                    object.put("PERCENT", BillHelper.fmPercent(threadValue.getDealCount(), threadValue.getTotalCount()));
                }
                String finishDateThread = formatter.format(threadValue.getFinishDate());
                object.put("FINISH_DATE", finishDateThread);
                object.put("STATE", threadValue.getState());
                String stateDateThread = formatter.format(threadValue.getStateDate());
                object.put("STATE_DATE", stateDateThread);
                jsonThreadArr.add(object);
            }
        }
        this.setProcessListTabItems(jsonArr);
        this.setThreadListItems(jsonThreadArr);
    }
    
    /**
     * 获取结点状态
     * 
     * @param request
     * @throws Exception
     */
    public void getNodeSts(IRequestCycle request)
        throws Exception
    {
        JSONObject object = new JSONObject();
        try
        {
            String regionId = getContext().getParameter("regionId");
            String runMode = getContext().getParameter("runMode");
            int billDay = Integer.parseInt(getContext().getParameter("billDay"));
            int wfFlowId = Integer.parseInt(getContext().getParameter("taskId"));
            int billingCycleId = Integer.parseInt(getContext().getParameter("billingCycleId"));
            
            IBWFCommonBillingSV commonSV = (IBWFCommonBillingSV)ServiceFactory.getService(IBWFCommonBillingSV.class);
            IBOBsBatchWfFlowValue[] flowValues = commonSV.getBatchWfFlow("", wfFlowId, regionId, billDay);
            IBOBsBatchWfFlowMonValue flowMonValue =
                commonSV.getBatchWfFlowMonValue(wfFlowId, regionId, billDay, billingCycleId);
            String startDateStr = "";
            String endDateStr = "";
            String wfFlowName = "";
            if (flowMonValue != null && flowValues != null && flowValues.length > 0)
            {
                startDateStr = AmsUtil.getFormatDateString(flowMonValue.getStartDate(), TimeUtil.YYYY_MM_DD_HH_MM_SS);
                endDateStr = AmsUtil.getFormatDateString(flowMonValue.getFinishDate(), TimeUtil.YYYY_MM_DD_HH_MM_SS);
                wfFlowName = flowValues[0].getWfFlowName();
            }
            object.put("startDate", startDateStr);
            object.put("endDate", endDateStr);
            object.put("wfFlowName", wfFlowName);
            object.put("retVal", "Y");
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e);
            }
            object.put("retVal", "N");
            object.put("retMsg", e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
        }
        finally
        {
            JSONObject object2 = new JSONObject();
            object2.put("obj", object);
            this.setAjax(object2);
        }
    }
    
    public abstract void setThreadListRowIndex(int threadListRowIndex);
    
    public abstract void setProcessListTabItems(JSONArray processListTabItems);
    
    public abstract void setProcessListTabItem(JSONObject processListTabItem);
    
    public abstract void setThreadListItems(JSONArray threadListItems);
    
    public abstract void setProcessListTabRowIndex(int processListTabRowIndex);
    
    public abstract void setThreadListItem(JSONObject threadListItem);
    
    public abstract void setProcessListTabCount(long processListTabCount);
    
    public abstract void setThreadListCount(long threadListCount);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("processListTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroup");
        
        bindBoName("threadList", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroup");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus processListTabItem = createData("processListTabItem");
        if (processListTabItem != null && !processListTabItem.getDataObject().isEmpty())
        {
            setProcessListTabItem(processListTabItem.getDataObject());
        }
        IDataBus threadListItem = createData("threadListItem");
        if (threadListItem != null && !threadListItem.getDataObject().isEmpty())
        {
            setThreadListItem(threadListItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
