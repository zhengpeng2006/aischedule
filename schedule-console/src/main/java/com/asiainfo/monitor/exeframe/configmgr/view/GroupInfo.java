package com.asiainfo.monitor.exeframe.configmgr.view;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class GroupInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public abstract void setGroupDetail(JSONObject groupDetail);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("groupDetail", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroup");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus groupDetail = createData("groupDetail");
        if (groupDetail != null && !groupDetail.getDataObject().isEmpty())
        {
            setGroupDetail(groupDetail.getDataObject());
        }
        initExtend(cycle);
    }
    
    /**
     * 获取权限信息
     * 
     * @param request
     * @throws Exception
     */
    public void qryPrivInfo(IRequestCycle request)
        throws Exception
    {
        UserInfoInterface userInfo = CommonSvUtil.getUserInfo();
        String priv = "";
        if (userInfo != null)
        {
            priv = String.valueOf(userInfo.getID());
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("priv", priv);
        this.setAjax(jsonObj);
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
        
        IDataBus dataBus = this.createData("groupDetail");
        
        IBOAIMonGroupValue value = (IBOAIMonGroupValue)DataBusHelper.getBOBean(dataBus);
        
        IAIMonGroupSV groupSv = (IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
        
        String groupCode = value.getGroupCode();
        String groupName = value.getGroupName();
        
        UserInfoInterface userInfo = CommonSvUtil.getUserInfo();
        String priv = "";
        if (userInfo != null)
        {
            priv = String.valueOf(userInfo.getID() + "_" + userInfo.getCode());
        }
        
        boolean code = groupSv.isExistGroupByCode(groupCode);
        boolean name = groupSv.isExistGroupByName(groupName);
        
        Map<String, String> retMap = new HashMap<String, String>();
        if (value.getGroupId() == 0)
        {
            
            if (code)
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                // 分组编码已存在
                retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPCODE_EXISTS);
            }
            if (name)
            {
                
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                // 分组名称已存在
                retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPNAME_EXISTS);
                
            }
            if (name && code)
            {
                
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                // 分组名称和分组编码均已存在
                retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPNAME_CODE_ALLEXISTS);
            }
            if (!code && !name)
            {
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                    CommonConstants.OPERATE_TYPE_ADD,
                    CommonConstants.OPERTATE_OBJECT_HOSTGROUP,
                    value.getGroupName(),
                    "添加分组，分组名称:" + value.getGroupName());
                value.setPriv(priv);
                // 保存分组信息之前要先进行校验
                boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                if (b)
                {
                    groupSv.saveOrUpdate(value);
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
            String groupId = Long.toString(value.getGroupId());
            IBOAIMonGroupValue groupValue = groupSv.getGroupBean(groupId);
            // 编码和名称都不变，直接进行编辑
            if (groupValue.getGroupCode().equals(groupCode) && groupValue.getGroupName().equals(groupName))
            {
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                    CommonConstants.OPERATE_TYPE_MODIFY,
                    CommonConstants.OPERTATE_OBJECT_HOSTGROUP,
                    value.getGroupId() + ":" + value.getGroupName(),
                    "编辑分组，分组标识" + value.getGroupId() + ":分组名称" + value.getGroupName());
                boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                if (b)
                {
                    groupSv.saveOrUpdate(value);
                    retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                }
                else
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                }
            }
            // 编码不变，名称变了，判断名称是否已存在
            if (groupValue.getGroupCode().equals(groupCode) && !groupValue.getGroupName().equals(groupName))
            {
                if (name)
                {
                    
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    // 分组名称已存在
                    retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPNAME_EXISTS);
                }
                else
                {
                    OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                        CommonConstants.OPERATE_TYPE_MODIFY,
                        CommonConstants.OPERTATE_OBJECT_HOSTGROUP,
                        value.getGroupId() + ":" + value.getGroupName(),
                        "编辑分组，分组标识" + value.getGroupId() + ":分组名称" + value.getGroupName());
                    boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                    if (b)
                    {
                        groupSv.saveOrUpdate(value);
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
            if (!groupValue.getGroupCode().equals(groupCode) && groupValue.getGroupName().equals(groupName))
            {
                if (code)
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    // 分组编码已存在
                    retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPCODE_EXISTS);
                    
                }
                else
                {
                    OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                        CommonConstants.OPERATE_TYPE_MODIFY,
                        CommonConstants.OPERTATE_OBJECT_HOSTGROUP,
                        value.getGroupId() + ":" + value.getGroupName(),
                        "编辑分组，分组标识" + value.getGroupId() + ":分组名称" + value.getGroupName());
                    boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                    if (b)
                    {
                        groupSv.saveOrUpdate(value);
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
            if (!groupValue.getGroupCode().equals(groupCode) && !groupValue.getGroupName().equals(groupName))
            {
                if (!name && !code)
                {
                    OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                        CommonConstants.OPERATE_TYPE_MODIFY,
                        CommonConstants.OPERTATE_OBJECT_HOSTGROUP,
                        value.getGroupId() + ":" + value.getGroupName(),
                        "编辑分组，分组标识" + value.getGroupId() + ":分组名称" + value.getGroupName());
                    boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                    if (b)
                    {
                        groupSv.saveOrUpdate(value);
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
                    
                    // 分组编码已存在
                    retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPCODE_EXISTS);
                    
                }
                if (name)
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    
                    // 分组名称已存在
                    retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPNAME_EXISTS);
                    
                }
                if (name && code)
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    // 分组名称和分组编码均已存在
                    retMap.put("msg", ResultConstants.ResultCodeValue.HOST_GROUPNAME_CODE_ALLEXISTS);
                }
            }
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("flag"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(jsonObj);
    }
    
    /**
     * 查询组信息
     * 
     * @param request
     * @throws Exception
     */
    public void qryGroupInfo(IRequestCycle request)
        throws Exception
    {
        String groupId = getContext().getParameter("groupId");
        IAIMonGroupSV groupSv = (IAIMonGroupSV)ServiceFactory.getService(IAIMonGroupSV.class);
        IBOAIMonGroupValue groupBean = groupSv.getGroupBean(groupId);
        
        IDataBus dataBus = null;
        if (groupBean == null)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(groupBean, IBOAIMonGroupValue.class);
        }
        
        // this.setGroupDetail(dataBus.getDataObject());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupDetail", dataBus.getDataObject());
        this.setAjax(jsonObject);
    }
    
}
