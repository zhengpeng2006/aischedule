package com.asiainfo.monitor.exeframe.controlmgr.view;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivUtils;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class UserGroup extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public abstract void setUserGroupTabCount(long userGroupTabCount);
    
    public abstract void setUserGroupTabItem(JSONObject userGroupTabItem);
    
    public abstract void setUserGroupTabItems(JSONArray userGroupTabItems);
    
    public abstract void setUserGroupDetail(JSONObject userGroupDetail);
    
    public abstract void setUserGroupTabRowIndex(int userGroupTabRowIndex);
    
    public abstract void setQryfrom(JSONObject qryfrom);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("userGroupTab", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUserGroup");
        
        bindBoName("userGroupDetail", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUserGroup");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus userGroupTabItem = createData("userGroupTabItem");
        if (userGroupTabItem != null && !userGroupTabItem.getDataObject().isEmpty())
        {
            setUserGroupTabItem(userGroupTabItem.getDataObject());
        }
        IDataBus userGroupDetail = createData("userGroupDetail");
        if (userGroupDetail != null && !userGroupDetail.getDataObject().isEmpty())
        {
            setUserGroupDetail(userGroupDetail.getDataObject());
        }
        IDataBus qryfrom = createData("qryform");
        if (qryfrom != null && !qryfrom.getDataObject().isEmpty())
        {
            setQryfrom(qryfrom.getDataObject());
        }
        initExtend(cycle);
    }
    
    public void auto_saveOrUpdate_btn_saveOnclick(IRequestCycle cycle)
        throws Exception
    {
        IDataBus dataBus = createData("userGroupDetail");
        
        IBOAIMonUserGroupValue value = (IBOAIMonUserGroupValue)DataBusHelper.getBOBean(dataBus);
        IAIMonUserGroupSV sv = (IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
        sv.saveOrUpdate(value);
    }
    
    /**
     * 删除用户
     * 
     * @param type
     * @param val
     * @return
     */
    public void auto_delete_btn_DelOnclick(IRequestCycle cycle)
        throws Exception
    {
        String userGroupId = getContext().getParameter("userGroupId");
        if (StringUtils.isBlank(userGroupId))
        {
            return;
        }
        try
        {
            IAIMonUserGroupSV userGroupSV = (IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
            userGroupSV.delete(Long.parseLong(userGroupId));
        }
        catch (Exception e)
        {
            log.error("Call Permission's Method deleteUserInfo has Exception :" + e.getMessage());
            throw e;
        }
        
    }
    
    public void loadUserGropuData(IRequestCycle cycle)
        throws Exception
    {
        IDataBus qryfromBus = createData("qryform");
        JSONObject qryfromCondition = (JSONObject)qryfromBus.getData();
        
        int start = Integer.parseInt(qryfromBus.getContext().getPaginStart() + "");
        int end = Integer.parseInt(qryfromBus.getContext().getPaginEnd() + "");
        
        String groupCode = (String)qryfromCondition.get("groupCode");
        String groupName = (String)qryfromCondition.get("groupName");
        IAIMonUserGroupSV sv = (IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
        int totalCnt = sv.getUserGroupCntByCond(groupCode, groupName);
        IBOAIMonUserGroupValue[] result = sv.getUserGroupByCond(groupCode, groupName, start, end);
        
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserGroupValue.class);
        
        JSONArray dataArray = bus.getDataArray();
        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext())
        {
            JSONObject obj = (JSONObject)iterator.next();
            if (obj.get("createDate").toString().length() > 8)
            {
                obj.put("createDate", obj.get("createDate").toString().substring(0, 10));
            }
            if (obj.get("doneDate").toString().length() > 8)
            {
                obj.put("doneDate", obj.get("doneDate").toString().substring(0, 10));
            }
        }
        setUserGroupTabItems(dataArray);
        setUserGroupTabCount(totalCnt);
    }
    
    public void auto_getBeanById_btn_editOnclick(IRequestCycle cycle)
        throws Exception
    {
        long userGroupId = Long.parseLong(getContext().getParameter("userGroupId"));
        IAIMonUserGroupSV sv = (IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
        IBOAIMonUserGroupValue result = sv.getBeanById(userGroupId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserValue.class);
        
        this.setUserGroupDetail(bus.getDataObject());
        
    }
    
    /**
     * 前台状态翻译
     * 
     * @param type
     * @param key
     * @return
     */
    public String doTranslate(String type, String val)
    {
        return UserPrivUtils.doTranslate(type, val);
    }
    
}
