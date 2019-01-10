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
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupRelBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivUtils;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class UserGroupRelation extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
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
    public void qryUserListByGroupId(IRequestCycle cycle)
        throws Exception
    {
        String groupId = getContext().getParameter("userGroupId");
        if (StringUtils.isBlank(groupId))
        {
            return;
        }
        IAIMonUserGroupSV userGrpSv = (IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
        IBOAIMonUserValue[] result = userGrpSv.getUserListByGroupId(Long.parseLong(groupId));
        IDataBus dataBus = null;
        if (null == result)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserGroupValue.class);
        }
        this.setUserTabItems(dataBus.getDataArray());
        this.setUserTabCount(dataBus.getDataArray().size());
    }
    
    /**
     * 查询不属于该用户组的用户信息列表
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryRelUserListByGroupId(IRequestCycle cycle)
        throws Exception
    {
        IDataBus pageData = createData(null);
        String groupId = getContext().getParameter("userGroupId");
        if (StringUtils.isBlank(groupId))
        {
            return;
        }
        
        IAIMonUserGroupSV userGrpSv = (IAIMonUserGroupSV)ServiceFactory.getService(IAIMonUserGroupSV.class);
        IBOAIMonUserValue[] result = userGrpSv.getRelUserListByGroupId(Long.parseLong(groupId));
        IDataBus dataBus = null;
        if (null == result)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserGroupValue.class);
        }
        this.setRelUserTabItems(dataBus.getDataArray());
        this.setRelUserTabCount(dataBus.getDataArray().size());
    }
    
    /**
     * 保存用户关联信息
     * 
     * @param cycle
     * @throws Exception
     */
    public void userGroupRelSave(IRequestCycle cycle)
        throws Exception
    {
        
        IDataBus dataBus = this.createData("paramJson");
        JSONObject dataObject = dataBus.getDataObject();
        String userGroupId = dataObject.get("userGroupId").toString();
        if (StringUtils.isBlank(userGroupId))
        {
            return;
        }
        
        IAIMonUserGroupRelSV relSv = (IAIMonUserGroupRelSV)ServiceFactory.getService(IAIMonUserGroupRelSV.class);
        JSONArray jsonArray = (JSONArray)dataObject.get("userList");
        BOAIMonUserGroupRelBean bean = null;
        String userId = "";
        for (Object object : jsonArray)
        {
            JSONObject jsonObj = (JSONObject)object;
            bean = new BOAIMonUserGroupRelBean();
            bean.setUserGroupId(Long.parseLong(userGroupId));
            userId = jsonObj.get("userId").toString();
            bean.setUserId(Long.parseLong(userId));
            
            // 如果该组下已存在该用户，则不录入数据库
            if (!relSv.checkUserGroupRelByUserId(userId, userGroupId))
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
    public void deleteUserGroupRel(IRequestCycle cycle)
        throws Exception
    {
        IDataBus dataBus = this.createData("paramJson");
        JSONObject dataObject = dataBus.getDataObject();
        String userGroupId = dataObject.get("userGroupId").toString();
        if (StringUtils.isBlank(userGroupId))
        {
            return;
        }
        JSONArray jsonArray = (JSONArray)dataObject.get("userList");
        BOAIMonUserGroupRelBean bean = null;
        IAIMonUserGroupRelSV relSv = (IAIMonUserGroupRelSV)ServiceFactory.getService(IAIMonUserGroupRelSV.class);
        for (Object object : jsonArray)
        {
            bean = new BOAIMonUserGroupRelBean();
            JSONObject jsonObj = (JSONObject)object;
            long userId = Long.parseLong(jsonObj.get("userId").toString());
            
            relSv.delete(Long.parseLong(userGroupId), userId);
        }
        
    }
    
    public abstract void setUserTabRowIndex(int userTabRowIndex);
    
    public abstract void setUserGroupTabItem(JSONObject userGroupTabItem);
    
    public abstract void setRelUserTabItems(JSONArray relUserTabItems);
    
    public abstract void setRelUserTabRowIndex(int relUserTabRowIndex);
    
    public abstract void setUserTabItem(JSONObject userTabItem);
    
    public abstract void setRelUserTabItem(JSONObject relUserTabItem);
    
    public abstract void setQryform(JSONObject qryform);
    
    public abstract void setRelUserTabCount(long relUserTabCount);
    
    public abstract void setUserTabItems(JSONArray userTabItems);
    
    public abstract void setUserTabCount(long userTabCount);
    
    public abstract void setUserGroupTabCount(long userGroupTabCount);
    
    public abstract void setUserGroupTabItems(JSONArray userGroupTabItems);
    
    public abstract void setUserGroupTabRowIndex(int userGroupTabRowIndex);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("userGroupTab", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUserGroup");
        
        bindBoName("userTab", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUser");
        
        bindBoName("relUserTab", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUser");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus userGroupTabItem = createData("userGroupTabItem");
        if (userGroupTabItem != null && !userGroupTabItem.getDataObject().isEmpty())
        {
            setUserGroupTabItem(userGroupTabItem.getDataObject());
        }
        IDataBus userTabItem = createData("userTabItem");
        if (userTabItem != null && !userTabItem.getDataObject().isEmpty())
        {
            setUserTabItem(userTabItem.getDataObject());
        }
        IDataBus relUserTabItem = createData("relUserTabItem");
        if (relUserTabItem != null && !relUserTabItem.getDataObject().isEmpty())
        {
            setRelUserTabItem(relUserTabItem.getDataObject());
        }
        IDataBus qryform = createData("qryform");
        if (qryform != null && !qryform.getDataObject().isEmpty())
        {
            setQryform(qryform.getDataObject());
        }
        initExtend(cycle);
    }
    
}
