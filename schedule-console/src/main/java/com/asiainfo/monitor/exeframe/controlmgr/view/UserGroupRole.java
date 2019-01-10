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
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupRoleSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRelBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRoleRelBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivUtils;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class UserGroupRole extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public abstract void setUserGroupTabItem(JSONObject userGroupTabItem);
    
    public abstract void setUserRoleTabItems(JSONArray userRoleTabItems);
    
    public abstract void setRelUserRoleTabItem(JSONObject relUserRoleTabItem);
    
    public abstract void setUserRoleTabCount(long userRoleTabCount);
    
    public abstract void setRelUserRoleTabCount(long relUserRoleTabCount);
    
    public abstract void setQryform(JSONObject qryform);
    
    public abstract void setRelUserRoleTabItems(JSONArray relUserRoleTabItems);
    
    public abstract void setUserGroupTabCount(long userGroupTabCount);
    
    public abstract void setUserRoleTabRowIndex(int userRoleTabRowIndex);
    
    public abstract void setUserGroupTabItems(JSONArray userGroupTabItems);
    
    public abstract void setRelUserRoleTabRowIndex(int relUserRoleTabRowIndex);
    
    public abstract void setUserGroupTabRowIndex(int userGroupTabRowIndex);
    
    public abstract void setUserRoleTabItem(JSONObject userRoleTabItem);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("userGroupTab", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUserGroup");
        
        bindBoName("userRoleTab", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUserRole");
        
        bindBoName("relUserRoleTab", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUserRole");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus userGroupTabItem = createData("userGroupTabItem");
        if (userGroupTabItem != null && !userGroupTabItem.getDataObject().isEmpty())
        {
            setUserGroupTabItem(userGroupTabItem.getDataObject());
        }
        IDataBus relUserRoleTabItem = createData("relUserRoleTabItem");
        if (relUserRoleTabItem != null && !relUserRoleTabItem.getDataObject().isEmpty())
        {
            setRelUserRoleTabItem(relUserRoleTabItem.getDataObject());
        }
        IDataBus qryform = createData("qryform");
        if (qryform != null && !qryform.getDataObject().isEmpty())
        {
            setQryform(qryform.getDataObject());
        }
        IDataBus userRoleTabItem = createData("userRoleTabItem");
        if (userRoleTabItem != null && !userRoleTabItem.getDataObject().isEmpty())
        {
            setUserRoleTabItem(userRoleTabItem.getDataObject());
        }
        initExtend(cycle);
    }
    
    public void loadUserGropuData(IRequestCycle cycle)
        throws Exception
    {
        IDataBus qryfromBus = createData("qryform");
        JSONObject qryfromCondition = (JSONObject)qryfromBus.getData();
        String groupCode = (String)qryfromCondition.get("groupCode");
        String groupName = (String)qryfromCondition.get("groupName");
        IAIMonUserGroupSV sv = (IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
        IBOAIMonUserGroupValue[] result = sv.getUserGroupByCond(groupCode, groupName);
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
        setUserGroupTabCount(bus.getDataArray().size());
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
    
    /**
     * 根据用户组标识查询用户信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public void qryUserRoleListByGroupId(IRequestCycle cycle)
        throws Exception
    {
        String groupId = getContext().getParameter("userGroupId");
        if (StringUtils.isBlank(groupId))
        {
            return;
        }
        IAIMonUserRoleSV userRoleSv = (IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
        IBOAIMonUserRoleValue[] result = userRoleSv.getUserRoleListByGroupId(groupId);
        IDataBus dataBus = null;
        if (null == result)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserGroupValue.class);
        }
        this.setUserRoleTabItems(dataBus.getDataArray());
        this.setUserRoleTabCount(dataBus.getDataArray().size());
    }
    
    /**
     * 查询不属于该用户组的用户信息列表
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryRelUserRoleListByGroupId(IRequestCycle cycle)
        throws Exception
    {
        String groupId = getContext().getParameter("userGroupId");
        if (StringUtils.isBlank(groupId))
        {
            return;
        }
        IAIMonUserRoleSV userRoleSv = (IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
        IBOAIMonUserRoleValue[] result = userRoleSv.getRelUserRoleListByGroupId(groupId);
        IDataBus dataBus = null;
        if (null == result)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserGroupValue.class);
        }
        this.setRelUserRoleTabItems(dataBus.getDataArray());
        this.setRelUserRoleTabCount(dataBus.getDataArray().size());
        
    }
    
    /**
     * 保存用户关联信息
     * 
     * @param cycle
     * @throws Exception
     */
    public void userGroupRoleRelSave(IRequestCycle cycle)
        throws Exception
    {
        
        IDataBus dataBus = this.createData("paramJson");
        JSONObject dataObject = dataBus.getDataObject();
        String userGroupId = dataObject.get("userGroupId").toString();
        if (StringUtils.isBlank(userGroupId))
        {
            return;
        }
        
        IAIMonUserGroupRoleSV relSv = (IAIMonUserGroupRoleSV)ServiceFactory.getService(IAIMonUserGroupRoleSV.class);
        JSONArray jsonArray = (JSONArray)dataObject.get("userRoleList");
        BOAIMonUserGroupRoleRelBean bean = null;
        String userRoleId = "";
        for (Object object : jsonArray)
        {
            JSONObject jsonObj = (JSONObject)object;
            bean = new BOAIMonUserGroupRoleRelBean();
            bean.setUserGroupId(Long.parseLong(userGroupId));
            userRoleId = jsonObj.get("userRoleId").toString();
            bean.setUserRoleId(Long.parseLong(userRoleId));
            
            // 如果该组下已存在该用户，则不录入数据库
            if (!relSv.checkUserGroupRoleRelByUserId(userRoleId, userGroupId))
            {
                relSv.saveOrUpdate(bean);
            }
        }
    }
    
    /**
     * 删除用户组用户关联信息
     * 
     * @param cycle
     * @throws Exception
     */
    public void deleteUserGroupRoleRel(IRequestCycle cycle)
        throws Exception
    {
        IDataBus dataBus = this.createData("paramJson");
        JSONObject dataObject = dataBus.getDataObject();
        String userGroupId = dataObject.get("userGroupId").toString();
        if (StringUtils.isBlank(userGroupId))
        {
            return;
        }
        JSONArray jsonArray = (JSONArray)dataObject.get("userRoleList");
        BOAIMonUserGroupRelBean bean = null;
        IAIMonUserGroupRoleSV relSv = (IAIMonUserGroupRoleSV)ServiceFactory.getService(IAIMonUserGroupRoleSV.class);
        for (Object object : jsonArray)
        {
            bean = new BOAIMonUserGroupRelBean();
            JSONObject jsonObj = (JSONObject)object;
            long userRoleId = Long.parseLong(jsonObj.get("userRoleId").toString());
            
            relSv.delete(Long.parseLong(userGroupId), userRoleId);
        }
        
    }
    
}
