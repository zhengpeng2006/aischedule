package com.asiainfo.monitor.exeframe.configmgr.view;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupHostRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupHostRelBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class HostInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 查询主机信息
     * 
     * @param request
     * @throws Exception
     */
    public void qryHostInfo(IRequestCycle request)
        throws Exception
    {
        String hostId = getContext().getParameter("hostId");
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
        IBOAIMonPhysicHostValue hostBean = hostSv.getPhysicHostById(hostId);
        
        IDataBus dataBus = null;
        if (hostBean == null)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(hostBean, IBOAIMonPhysicHostValue.class);
        }
        
        // this.setHostDetail(dataBus.getDataObject());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostDetail", dataBus.getDataObject());
        this.setAjax(jsonObject);
    }
    
    /**
     * 查询所有主机id、主机名称
     * 
     * @param request
     * @throws Exception
     */
    public void qryAllHostInfo(IRequestCycle request)
        throws Exception
    {
        
        IDataBus dataBus = getHostList();
        
        // this.setHostDetail(dataBus.getDataObject());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostList", dataBus.getDataArray());
        this.setAjax(jsonObject);
    }
    
    public IDataBus getHostList()
        throws Exception
    {
        IAIMonPhysicHostSV phSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
        return phSv.getHostList();
    }
    
    /**
     * 新增分组
     * 
     * @param request
     * @throws Exception
     */
    public void saveOrUpdate(IRequestCycle request)
        throws Exception
    {
        String treeNodeId = getContext().getParameter("treeNodeId");
        String[] arr = treeNodeId.split("_");
        String groupId = arr[0];
        IDataBus dataBus = this.createData("hostDetail");
        IBOAIMonPhysicHostValue value = (IBOAIMonPhysicHostValue)DataBusHelper.getBOBean(dataBus);
        String id = Long.toString(value.getHostId());
        
        String hostCode = value.getHostCode();
        String hostName = value.getHostName();
        
        IAIMonPhysicHostSV hostSv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
        boolean code = hostSv.isExistHostByCode(groupId, hostCode);
        boolean name = hostSv.isExistHostByName(groupId, hostName);
        
        Map<String, String> retMap = new HashMap<String, String>();
        if (value.getHostId() == 0)
        {
            
            if (code)
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPCODE_EXISTS);
            }
            if (name)
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPNAME_EXISTS);
            }
            if (name && code)
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPNAME_CODE_ALLEXISTS);
            }
            if (!code && !name)
            {
                // 添加主机操作日志
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                    CommonConstants.OPERATE_TYPE_ADD,
                    CommonConstants.OPERTATE_OBJECT_HOST,
                    value.getHostId() + ":" + value.getHostName(),
                    "添加主机，" + "主机名称：" + value.getHostName());
                // 保存主机信息前进行校验
                boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                if (b)
                {
                    long hostId = hostSv.saveOrUpdate(value);
                    BOAIMonGroupHostRelBean groupHostBean = new BOAIMonGroupHostRelBean();
                    groupHostBean.setGroupId(Long.parseLong(groupId));
                    groupHostBean.setHostId(hostId);
                    IAIMonGroupHostRelSV ghSv =
                        (IAIMonGroupHostRelSV)ServiceFactory.getService(IAIMonGroupHostRelSV.class);
                    // 添加主机分组操作日志
                    OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                        CommonConstants.OPERATE_TYPE_ADD,
                        CommonConstants.OPERTATE_OBJECT_HOSTGROUP,
                        groupHostBean.getGroupId() + ":" + groupHostBean.getHostId(),
                        "新增分组主机关系，" + "分组标识" + groupHostBean.getGroupId() + ":主机标识 " + groupHostBean.getHostId());
                    ghSv.save(groupHostBean);
                    retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                }
                else
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                }
            }
        }
        else
        {
            String hostId = Long.toString(value.getHostId());
            IBOAIMonPhysicHostValue hostValue = hostSv.getPhysicHostById(hostId);
            // 编码和名称都不变，直接进行编辑
            if (hostValue.getHostCode().equals(hostCode) && hostValue.getHostName().equals(hostName))
            {
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                    CommonConstants.OPERATE_TYPE_MODIFY,
                    CommonConstants.OPERTATE_OBJECT_HOST,
                    value.getHostId() + ":" + value.getHostName(),
                    "编辑主机，主机标识" + value.getHostId() + ":主机名称" + value.getHostName());
                // 保存主机信息前进行校验
                boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                if (b)
                {
                    hostSv.saveOrUpdate(value);
                    retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                }
                else
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                }
            }
            // 编码不变，名称变了，判断名称是否已存在
            if (hostValue.getHostCode().equals(hostCode) && !hostValue.getHostName().equals(hostName))
            {
                if (name)
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPNAME_EXISTS);
                }
                else
                {
                    OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                        CommonConstants.OPERATE_TYPE_MODIFY,
                        CommonConstants.OPERTATE_OBJECT_HOST,
                        value.getHostId() + ":" + value.getHostName(),
                        "编辑主机，主机标识" + value.getHostId() + ":主机名称" + value.getHostName());
                    // 保存主机信息前进行校验
                    boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                    if (b)
                    {
                        hostSv.saveOrUpdate(value);
                        retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                    }
                    else
                    {
                        retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                    }
                }
            }
            // 编码变了，名称不变，判断编码是否已存在
            if (!hostValue.getHostCode().equals(hostCode) && hostValue.getHostName().equals(hostName))
            {
                if (code)
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPCODE_EXISTS);
                }
                else
                {
                    OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                        CommonConstants.OPERATE_TYPE_MODIFY,
                        CommonConstants.OPERTATE_OBJECT_HOST,
                        value.getHostId() + ":" + value.getHostName(),
                        "编辑主机，主机标识" + value.getHostId() + ":主机名称" + value.getHostName());
                    // 保存主机信息前进行校验
                    boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                    if (b)
                    {
                        hostSv.saveOrUpdate(value);
                        retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                    }
                    else
                    {
                        retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                    }
                }
            }
            // 编码和名称都变了，同时判断编码和名称是否存在
            if (!hostValue.getHostCode().equals(hostCode) && !hostValue.getHostName().equals(hostName))
            {
                if (!name && !code)
                {
                    OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                        CommonConstants.OPERATE_TYPE_MODIFY,
                        CommonConstants.OPERTATE_OBJECT_HOST,
                        value.getHostId() + ":" + value.getHostName(),
                        "编辑主机，主机标识" + value.getHostId() + ":主机名称" + value.getHostName());
                    // 保存主机信息前进行校验
                    boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                    if (b)
                    {
                        hostSv.saveOrUpdate(value);
                        retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                    }
                    else
                    {
                        retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                    }
                }
                if (code)
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPCODE_EXISTS);
                }
                if (name)
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPNAME_EXISTS);
                }
                if (name && code)
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPNAME_CODE_ALLEXISTS);
                }
            }
            
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("flag"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(jsonObj);
        
    }
    
    public abstract void setHostDetail(JSONObject hostDetail);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("hostDetail", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHost");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus hostDetail = createData("hostDetail");
        if (hostDetail != null && !hostDetail.getDataObject().isEmpty())
        {
            setHostDetail(hostDetail.getDataObject());
        }
        initExtend(cycle);
    }
    
}
