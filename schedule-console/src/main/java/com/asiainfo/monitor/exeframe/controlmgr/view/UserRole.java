package com.asiainfo.monitor.exeframe.controlmgr.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.monitor.busi.cache.impl.AIMonDomainCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.Domain;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomainBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivUtils;
import com.asiainfo.monitor.exeframe.controlmgr.service.interfaces.IUserPriConstDataSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class UserRole extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public abstract void setUserRoleTabRowIndex(int userRoleTabRowIndex);
    
    public abstract void setUserRoleTabItems(JSONArray userRoleTabItems);
    
    public abstract void setUserRoleTabCount(long userRoleTabCount);
    
    public abstract void setUserRoleTabItem(JSONObject userRoleTabItem);
    
    public abstract void setQryform(JSONObject qryform);
    
    public abstract void setUserRoleDetail(JSONObject userRoleDetail);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("userRoleTab", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUserRole");
        
        bindBoName("userRoleDetail", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUserRole");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus userRoleTabItem = createData("userRoleTabItem");
        if (userRoleTabItem != null && !userRoleTabItem.getDataObject().isEmpty())
        {
            setUserRoleTabItem(userRoleTabItem.getDataObject());
        }
        IDataBus qryform = createData("qryform");
        if (qryform != null && !qryform.getDataObject().isEmpty())
        {
            setQryform(qryform.getDataObject());
        }
        IDataBus userRoleDetail = createData("userRoleDetail");
        if (userRoleDetail != null && !userRoleDetail.getDataObject().isEmpty())
        {
            setUserRoleDetail(userRoleDetail.getDataObject());
        }
        initExtend(cycle);
    }
    
    public void loadUserRoleData(IRequestCycle cycle)
        throws Exception
    {
        IDataBus qryformBus = createData("qryform");
        JSONObject qryformCondition = (JSONObject)qryformBus.getData();
        
        int start = Integer.parseInt(qryformBus.getContext().getPaginStart() + "");
        int end = Integer.parseInt(qryformBus.getContext().getPaginEnd() + "");
        String roleCode = (String)qryformCondition.get("roleCode");
        String roleName = (String)qryformCondition.get("roleName");
        
        IAIMonUserRoleSV sv = (IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
        int totalCnt = sv.getUserRoleCntByCond(roleCode, roleName);
        IBOAIMonUserRoleValue[] result = sv.getUserRoleByCond(roleCode, roleName, start, end);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserRoleValue.class);
        
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
        setUserRoleTabItems(dataArray);
        setUserRoleTabCount(totalCnt);
    }
    
    public void auto_saveOrUpdate_btn_saveOnclick(IRequestCycle cycle)
        throws Exception
    {
        IDataBus dataBus = createData("userRoleDetail");
        
        IBOAIMonUserRoleValue value = (IBOAIMonUserRoleValue)DataBusHelper.getBOBean(dataBus);
        // value.setOpId(Long.parseLong(FlexSessionManager.getUser().getId()));
        IAIMonUserRoleSV sv = (IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
        sv.saveOrUpdate(value);
    }
    
    public void auto_getBeanById_btn_editOnclick(IRequestCycle cycle)
        throws Exception
    {
        long userId = Long.parseLong(getContext().getParameter("userRoleId"));
        IAIMonUserRoleSV sv = (IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
        IBOAIMonUserRoleValue result = sv.getBeanById(userId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserRoleValue.class);
        
        this.setUserRoleDetail(bus.getDataObject());
    }
    
    /**
     * 获取域信息
     * 
     * @param constCode
     * @return
     * @throws Exception
     */
    public IDataBus auto_getDomainInfo()
        throws Exception
    {
        IUserPriConstDataSV sv = (IUserPriConstDataSV)ServiceFactory.getService(IUserPriConstDataSV.class);
        BOAIMonDomainBean[] domainArr = sv.getDomainInfo();
        Map<String, String> domainMap = new HashMap<String, String>();
        for (BOAIMonDomainBean domain : domainArr)
        {
            domainMap.put(domain.getDomainDesc(), String.valueOf(domain.getDomainId()));
        }
        
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        
        Iterator<String> keyIt = domainMap.keySet().iterator();
        while (keyIt.hasNext())
        {
            String key = keyIt.next();
            String val = domainMap.get(key);
            
            JSONObject obj = new JSONObject();
            obj.put("TEXT", key);
            obj.put("VALUE", val);
            jsonArray.add(obj);
        }
        
        return new DataBus(context, jsonArray);
    }
    
    public String doTranslate(String type, String val)
    {
        return UserPrivUtils.doTranslate(type, val);
    }
    
    public String dotranslate4Domain(String domainId)
    {
        String retStr = "";
        try
        {
            Domain domain = (Domain)CacheFactory.get(AIMonDomainCacheImpl.class, domainId);
            retStr = domain.getDesc();
        }
        catch (Exception ex)
        {
            
        }
        
        return retStr;
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
        String userRoleId = getContext().getParameter("userRoleId");
        if (StringUtils.isBlank(userRoleId))
        {
            return;
        }
        try
        {
            IAIMonUserRoleSV userSV = (IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
            userSV.delete(Long.parseLong(userRoleId));
        }
        catch (Exception e)
        {
            log.error("Call Permission's Method deleteUserInfo has Exception :" + e.getMessage());
            throw e;
        }
        
    }
    
}
