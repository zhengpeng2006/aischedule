package com.asiainfo.batchwriteoff.view;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.common.ivalues.IBOBsDistrictValue;
import com.ai.common.util.ExceptionUtil;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.batchwriteoff.common.BatchWriteOffConsts;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfFlowMonValue;
import com.asiainfo.batchwriteoff.ivalues.IBOBsBatchWfPsValue;
import com.asiainfo.batchwriteoff.service.interfaces.IBWFCommonBillingSV;
import com.asiainfo.batchwriteoff.util.BWFUtil;
import com.asiainfo.batchwriteoff.util.DistrictUtil;
import com.asiainfo.batchwriteoff.util.GraphicsUtil;
import com.asiainfo.batchwriteoff.util.SSHUtil;

/**
 * Created by SMT Copyright: Copyright(c) 2015 Asiainfo-Linkage
 */
public abstract class OperateArea extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 获取开始结束结点的taskId
     * 
     * @param request
     * @throws Exception
     */
    public void qryStartEndInfo(IRequestCycle request)
        throws Exception
    {
        int start = Integer.parseInt(GraphicsUtil.getCfgInfo(BatchWriteOffConsts.Common.PARA_CODE_START));
        int end = Integer.parseInt(GraphicsUtil.getCfgInfo(BatchWriteOffConsts.Common.PARA_CODE_END));
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("start", start);
        jsonObj.put("end", end);
        JSONObject object = new JSONObject();
        object.put("startEndInfo", jsonObj);
        this.setAjax(object);
    }
    
    public void runProcess(IRequestCycle request)
        throws Exception
    {
        JSONObject object = new JSONObject();
        try
        {
            String regionId = getContext().getParameter("regionId");
            String runMode = getContext().getParameter("runMode");
            long billDay = Long.parseLong(getContext().getParameter("billDay"));
            int taskId = Integer.parseInt(getContext().getParameter("taskId"));
            int billingCycleId = Integer.parseInt(getContext().getParameter("billingCycleId"));
            String password = getContext().getParameter("password");
            
            BWFUtil.checkPassword(password, regionId);
            
            IBWFCommonBillingSV commonSV =
                (IBWFCommonBillingSV)ServiceFactory.getCrossCenterService(IBWFCommonBillingSV.class);
            
            IBOBsDistrictValue[] allRegions = null;
            
            if ("-1".equalsIgnoreCase(regionId))
            {
                allRegions = DistrictUtil.getRegion();
            }
            else
            {
                IBOBsDistrictValue tmpDistrictValue = DistrictUtil.getDistrictByRegionId(regionId);
                allRegions = new IBOBsDistrictValue[] {tmpDistrictValue};
            }
            for (int i = 0; i < allRegions.length; i++)
            {
                
                String tmpRegionId = allRegions[i].getRegionId();
                
                // CenterFactory.setCenterInfoByTypeAndValue("RegionId",tmpRegionId);
                
                IBOBsBatchWfPsValue batchWfPsValue =
                    commonSV.getBatchWfPsValue(BatchWriteOffConsts.Common.SCHEDULE_PREFIX + tmpRegionId);
                if (batchWfPsValue == null)
                {
                    // 节点-[{0}]-没有配置AM_BATCH_WF_PS，无法完成
                    ExceptionUtil.throwBusinessException("AMS0700505", BatchWriteOffConsts.Common.SCHEDULE_PREFIX
                        + tmpRegionId);
                }
                StringBuilder command = new StringBuilder();
                
                command.delete(0, command.length());
                
                command.append("cd ")
                    .append(batchWfPsValue.getShellCatalog())
                    .append(" && ")
                    .append(batchWfPsValue.getMonitorShell());
                
                String rtn =
                    SSHUtil.ssh4Command(batchWfPsValue.getHostIp(),
                        batchWfPsValue.getPort(),
                        batchWfPsValue.getUserName(),
                        K.k_s(batchWfPsValue.getPassword()),
                        command.toString(),
                        false);
                
                if (StringUtils.isNotBlank(rtn) && rtn.equals("PROCESS_EXIST"))
                {
                    String[] args =
                        new String[] {batchWfPsValue.getHostIp(), Long.toString(batchWfPsValue.getPort()),
                            batchWfPsValue.getUserName(), batchWfPsValue.getWfProcessName(),
                            batchWfPsValue.getShellCatalog() + "/" + batchWfPsValue.getMonitorShell()};
                    ExceptionUtil.throwBusinessException("AMS0700545", args);
                }
                command.delete(0, command.length());
                command.append("cd ")
                    .append(batchWfPsValue.getShellCatalog())
                    .append(" && ")
                    .append(batchWfPsValue.getStartShell())
                    .append(" ")
                    .append(tmpRegionId)
                    .append(" ")
                    .append(billDay)
                    .append(" ")
                    .append(runMode)
                    .append(" ")
                    .append(password);
                SSHUtil.ssh4Command(batchWfPsValue.getHostIp(),
                    batchWfPsValue.getPort(),
                    batchWfPsValue.getUserName(),
                    K.k_s(batchWfPsValue.getPassword()),
                    command.toString(),
                    false);
            }
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
    
    /**
     * 校验流程图是否开启
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    public void checkProcess(IRequestCycle request)
        throws Exception
    {
        JSONObject object = new JSONObject();
        try
        {
            String regionId = getContext().getParameter("regionId");
            long billDay = Long.parseLong(getContext().getParameter("billDay"));
            int billingCycleId = Integer.parseInt(getContext().getParameter("billingCycleId"));
            int wfFlowId = Integer.parseInt(GraphicsUtil.getCfgInfo(BatchWriteOffConsts.Common.PARA_CODE_START));
            
            IBWFCommonBillingSV commonSV = (IBWFCommonBillingSV)ServiceFactory.getService(IBWFCommonBillingSV.class);
            IBOBsBatchWfFlowMonValue flowMonValue =
                commonSV.getBatchWfFlowMonValue(wfFlowId, regionId, billDay, billingCycleId);
            if (flowMonValue != null && BatchWriteOffConsts.Common.FLOW_MON_STATE_Y.equals(flowMonValue.getState()))
            {
                object.put("retVal", "Y");
            }
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
    
    /**
     * 确认或者重启结点
     * 
     * @Function confirmNodeSts
     * @Description
     *
     * @param request
     * @param response
     * @throws Exception
     *
     * @version v1.0.0
     * @author yandong2
     * @date 2012-10-22 下午07:59:14
     * 
     *       <strong>Modification History:</strong><br>
     *       Date Author Version Description<br>
     *       ---------------------------------------------------------<br>
     *       2012-10-22 yandong2 v1.0.0 修改原因<br>
     */
    public void confirmNodeSts(IRequestCycle request)
        throws Exception
    {
        JSONObject object = new JSONObject();
        try
        {
            String regionId = getContext().getParameter("regionId");
            long billDay = Long.parseLong(getContext().getParameter("billDay"));
            long billingCycleId = Long.parseLong(getContext().getParameter("billingCycleId"));
            long wfFlowId = Long.parseLong(getContext().getParameter("taskId"));
            String state = getContext().getParameter("state");
            String password = getContext().getParameter("password");
            
            if (StringUtils.isNotBlank(password))
            {
                BWFUtil.checkPassword(password, regionId);
            }
            if (StringUtils.isNotBlank(state))
            {
                BWFUtil.confirmWfFlowNode(wfFlowId, regionId, billDay, billingCycleId, state);
            }
            else
            {
                BWFUtil.resetWfFlowNode(wfFlowId, regionId, billDay, billingCycleId);
            }
            object.put("retVal", "Y");
            object.put("retMsg", "操作成功");// 操作成功！
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
    
    public abstract void setPwdInput(JSONObject pwdInput);
    
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
        initExtend(cycle);
    }
    
}
