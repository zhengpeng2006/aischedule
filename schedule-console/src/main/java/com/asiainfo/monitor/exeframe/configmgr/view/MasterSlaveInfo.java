package com.asiainfo.monitor.exeframe.configmgr.view;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonMasterSlaveRelBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonMasterSlaveRelValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonMasterSlaveRelSV;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivUtils;
import com.asiainfo.resultConstant.ResultConstants;
import com.asiainfo.schedule.core.po.BackupInfoBean;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class MasterSlaveInfo extends AppPage
{
    private static final Logger LOGGER = Logger.getLogger(MasterSlaveInfo.class);
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public String doTranslate(String type, String val)
        throws RemoteException, Exception
    {
        String text = null;
        if ("USER_STATE".equals(type))
        {
            text = UserPrivUtils.doTranslate(type, val);
        }
        
        return text;
    }
    
    public IDataBus getHostList()
        throws Exception
    {
        IAIMonPhysicHostSV phSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
        return phSv.getHostList();
    }
    
    public void qryMasterSlaveInfo(IRequestCycle request)
        throws Exception
    {
        String hostId = getContext().getParameter("hostId");
        
        IAIMonMasterSlaveRelSV msrSv = (IAIMonMasterSlaveRelSV)ServiceFactory.getService(IAIMonMasterSlaveRelSV.class);
        IAIMonPhysicHostSV phSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
        
        List list = msrSv.getSlaveHostId(hostId);
        IBOAIMonPhysicHostValue[] result = null;
        if (list.size() > 0)
        {
            result = phSv.qryByList(list);
        }
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonPhysicHostValue.class);
        JSONArray dataArray = bus.getDataArray();
        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext())
        {
            JSONObject obj = (JSONObject)iterator.next();
            if (obj.get("createDate").toString().length() > 8)
            {
                obj.put("createDate", obj.get("createDate").toString().substring(0, 10));
            }
        }
        /*
         * this.setSlaveTabItems(dataArray); this.setSlaveTabCount(dataArray.size());
         */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("slaveTabItems", dataArray);
        jsonObject.put("total", dataArray.size());
        this.setAjax(jsonObject);
    }
    
    public void delSlave(IRequestCycle cycle)
        throws Exception
    {
        String masterHostId = getContext().getParameter("masterHostId");
        String slaveHostId = getContext().getParameter("slaveHostId");
        IAIMonMasterSlaveRelSV msrSv = (IAIMonMasterSlaveRelSV)ServiceFactory.getService(IAIMonMasterSlaveRelSV.class);
        IBOAIMonMasterSlaveRelValue[] value = msrSv.qryMasterSlaveByCondition(masterHostId, slaveHostId);
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_MONITOR,
            CommonConstants.OPERATE_TYPE_MODIFY,
            CommonConstants.OPERTATE_OBJECT_HOST,
            masterHostId + ":" + slaveHostId,
            "主备机关系删除，主机ID为" + masterHostId + ",备机ID为" + slaveHostId);
        msrSv.delete(value);
    }
    
    public void saveOrUpdate(IRequestCycle request)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        String masterHostId = getContext().getParameter("masterHostId");
        IDataBus dataBus = this.createData("slaveDetail");
        IBOAIMonPhysicHostValue value = (IBOAIMonPhysicHostValue)DataBusHelper.getBOBean(dataBus);
        
        BOAIMonMasterSlaveRelBean masterSlaveBean = new BOAIMonMasterSlaveRelBean();
        String slaveHostId = Long.toString(value.getHostId());
        masterSlaveBean.setMasterHostId(Long.parseLong(masterHostId));
        masterSlaveBean.setSlaveHostId(Long.parseLong(slaveHostId));
        
        IAIMonMasterSlaveRelSV msSv = (IAIMonMasterSlaveRelSV)ServiceFactory.getService(IAIMonMasterSlaveRelSV.class);
        List slaveList = msSv.getSlaveHostId(masterHostId);
        
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_MONITOR,
            CommonConstants.OPERATE_TYPE_MODIFY,
            CommonConstants.OPERTATE_OBJECT_HOST,
            masterHostId + ":" + slaveHostId,
            "配置主备机关系，主机ID为" + masterHostId + ",备机ID为" + slaveHostId);
        JSONObject jsonObj = new JSONObject();
        if (slaveList.size() > 0)
        {
            jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
            // 主机下已有一台备机，不可以再添加
            jsonObj.put(ResultConstants.ResultKey.RESULT_MSG,
                "\u4e3b\u673a\u4e0b\u5df2\u6709\u4e00\u53f0\u5907\u673a\uff0c\u4e0d\u53ef\u4ee5\u518d\u6dfb\u52a0");
        }
        else
        {
            if (masterHostId.equals(slaveHostId))
            {
                jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                // 主备机不可以是同一台
                jsonObj.put(ResultConstants.ResultKey.RESULT_MSG,
                    "\u4e3b\u5907\u673a\u4e0d\u53ef\u4ee5\u662f\u540c\u4e00\u53f0");
            }
            else
            {
                msSv.save(masterSlaveBean);
            }
        }
        
        this.setAjax(jsonObj);
        
    }
    
    /**
     * 保存备用进程
     * 
     * @param request
     * @throws Exception
     */
    public void saveBackupServerCode(IRequestCycle request)
        throws Exception
    {
        
        String serverCode = getContext().getParameter("serverCode").trim();
        String backupServerCode = getContext().getParameter("backupServerCode").trim();
        JSONObject result = new JSONObject();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        try
        {
            if (StringUtils.isBlank(serverCode) || StringUtils.isBlank(backupServerCode))
            {
                result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                result.put(ResultConstants.ResultKey.RESULT_MSG, "please check param!");
            }
            else
            {
                String backupServerCodeExist = sv.getBackupConfig(serverCode);
                if (StringUtils.isBlank(backupServerCodeExist))
                {
                    
                    BackupInfoBean backupInfo = new BackupInfoBean();
                    backupInfo.setServerCode(serverCode);
                    backupInfo.setBackupServerCode(backupServerCode);
                    
                    sv.createBackupConfig(backupInfo);
                    
                    result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
                }
                else
                {
                    result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                    // 备进程只能有一个
                    result.put(ResultConstants.ResultKey.RESULT_MSG, "\u5907\u8fdb\u7a0b\u53ea\u80fd\u6709\u4e00\u4e2a");
                }
            }
        }
        catch (Exception e)
        {
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("saveBackupServerCode failed,error is " + e);
        }
        
        this.setAjax(result);
    }
    
    /**
     * 删除备用进程
     * 
     * @param request
     * @throws Exception
     */
    public void deleteBackupServerCode(IRequestCycle request)
        throws Exception
    {
        String serverCode = getContext().getParameter("serverCode").trim();
        JSONObject result = new JSONObject();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        try
        {
            if (StringUtils.isBlank(serverCode))
            {
                result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                result.put(ResultConstants.ResultKey.RESULT_MSG, "please check param!");
            }
            else
            {
                
                BackupInfoBean backupInfo = new BackupInfoBean();
                backupInfo.setServerCode(serverCode);
                
                sv.deleteBackupConfig(backupInfo);
                
                result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
                
            }
        }
        catch (Exception e)
        {
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("saveBackupServerCode failed,error is " + e);
        }
        
        this.setAjax(result);
        
    }
    
    /**
     * 查询备用进程
     * 
     * @param request
     * @throws Exception
     */
    public void qryBackupServerCode(IRequestCycle request)
        throws Exception
    {
        String serverCode = getContext().getParameter("serverCode").trim();
        JSONObject result = new JSONObject();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        try
        {
            if (StringUtils.isBlank(serverCode))
            {
                result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                result.put(ResultConstants.ResultKey.RESULT_MSG, "please check param!");
            }
            else
            {
                
                String backupServerCode = sv.getBackupConfig(serverCode);
                
                if (!StringUtils.isBlank(backupServerCode))
                {
                    
                    IAIMonServerSV serverSv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
                    IBOAIMonServerValue serverValue = serverSv.qryServerByServerCode(backupServerCode);
                    if (null != serverValue)
                    {
                        result.put("backupServerName", serverValue.getServerName());
                        
                        IAIMonNodeSV nodeSv = (IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
                        IBOAIMonNodeValue nodeValue =
                            nodeSv.qryNodeInfoByNodeId(Long.toString(serverValue.getNodeId()));
                        
                        if (nodeValue != null)
                        {
                            
                            IAIMonPhysicHostSV hostSv =
                                (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
                            IBOAIMonPhysicHostValue hostBean =
                                hostSv.getPhysicHostById(Long.toString(nodeValue.getHostId()));
                            
                            result.put("hostName", null != hostBean ? hostBean.getHostName() : "");
                        }
                        
                        result.put("backupServerCode", backupServerCode);
                    }
                }
                
            }
        }
        catch (Exception e)
        {
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("saveBackupServerCode failed,error is " + e);
        }
        
        this.setAjax(result);
        
    }
    
    public abstract void setSlaveTabCount(long slaveTabCount);
    
    public abstract void setSlaveTabItem(JSONObject slaveTabItem);
    
    public abstract void setSlaveTabRowIndex(int slaveTabRowIndex);
    
    public abstract void setSlaveTabItems(JSONArray slaveTabItems);
    
    public abstract void setSlaveDetail(JSONObject slaveDetail);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("slaveTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHost");
        
        bindBoName("slaveDetail", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHost");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus slaveTabItem = createData("slaveTabItem");
        if (slaveTabItem != null && !slaveTabItem.getDataObject().isEmpty())
        {
            setSlaveTabItem(slaveTabItem.getDataObject());
        }
        IDataBus slaveDetail = createData("slaveDetail");
        if (slaveDetail != null && !slaveDetail.getDataObject().isEmpty())
        {
            setSlaveDetail(slaveDetail.getDataObject());
        }
        initExtend(cycle);
    }
    
}
