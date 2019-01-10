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
import com.asiainfo.appframe.ext.flyingserver.util.e.K;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivUtils;
import com.asiainfo.monitor.exeframe.controlmgr.service.interfaces.IUserPriConstDataSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
/**
 * @author wsk
 * 
 */
public abstract class UserInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
    }
    
    private void initExtend()
    {
    }
    
    public void loadUserData(IRequestCycle cycle)
        throws Exception
    {
        IDataBus qryformBus = createData("qryform");
        JSONObject qryformCondition = (JSONObject)qryformBus.getData();
        
        int start = Integer.parseInt(qryformBus.getContext().getPaginStart() + "");
        int end = Integer.parseInt(qryformBus.getContext().getPaginEnd() + "");
        
        String userCode = (String)qryformCondition.get("userCode");
        String userName = (String)qryformCondition.get("userName");
        IAIMonUserSV sv = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
        // 分页和取得分页数据
        IBOAIMonUserValue[] result = sv.getUserInfoByCond(userCode, userName, start, end);
        int totalCnt = sv.getUserCntByCond(userCode, userName);
        
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserValue.class);
        
        JSONArray dataArray = bus.getDataArray();
        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext())
        {
            JSONObject obj = (JSONObject)iterator.next();
            if (obj.get("effectDate").toString().length() > 8)
            {
                obj.put("effectDate", obj.get("effectDate").toString().substring(0, 10));
            }
            if (obj.get("expireDate").toString().length() > 8)
            {
                obj.put("expireDate", obj.get("expireDate").toString().substring(0, 10));
            }
            if (obj.get("createDate").toString().length() > 8)
            {
                obj.put("createDate", obj.get("createDate").toString().substring(0, 10));
            }
            if (obj.get("doneDate").toString().length() > 8)
            {
                obj.put("doneDate", obj.get("doneDate").toString().substring(0, 10));
            }
        }
        setUserInfoTabItems(bus.getDataArray());
        setUserInfoTabCount(totalCnt);
    }
    
    public void auto_saveOrUpdate_btn_saveOnclick(IRequestCycle cycle)
        throws Exception
    {
        IDataBus dataBus = createData("userDetail");
        
        IBOAIMonUserValue value = (IBOAIMonUserValue)DataBusHelper.getBOBean(dataBus);
        IAIMonUserSV sv = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
        sv.saveOrUpdate(value);
    }
    
    /**
     * auto_
     */
    public IDataBus auto_getUserPrivConstByCode(String constCode)
        throws Exception
    {
        IUserPriConstDataSV sv = (IUserPriConstDataSV)ServiceFactory.getService(IUserPriConstDataSV.class);
        IDataBus result = sv.getUserPrivConstByCode(constCode);
        return result;
    }
    
    public void auto_getBeanById_btn_editOnclick(IRequestCycle cycle)
        throws Exception
    {
        long userId = Long.parseLong(getContext().getParameter("userId"));
        IAIMonUserSV sv = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
        IBOAIMonUserValue result = sv.getBeanById(userId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserValue.class);
        JSONObject dataObject = bus.getDataObject();
        dataObject.put("userPass", K.k(dataObject.get("userPass").toString()));
        
        this.setUserDetail(bus.getDataObject());
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
        String userId = getContext().getParameter("userId");
        if (StringUtils.isBlank(userId))
        {
            return;
        }
        try
        {
            IAIMonUserSV userSV = (IAIMonUserSV)ServiceFactory.getService(IAIMonUserSV.class);
            userSV.delete(Long.parseLong(userId));
        }
        catch (Exception e)
        {
            log.error("Call Permission's Method deleteUserInfo has Exception :" + e.getMessage());
            throw e;
        }
        
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
    
    public abstract void setUserDetail(JSONObject userDetail);
    
    public abstract void setUserInfoTabCount(long userInfoTabCount);
    
    public abstract void setUserInfoTabItems(JSONArray userInfoTabItems);
    
    public abstract void setUserInfoTabRowIndex(int userInfoTabRowIndex);
    
    public abstract void setQryform(JSONObject qryform);
    
    public abstract void setUserInfoTabItem(JSONObject userInfoTabItem);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("userInfoTab", "com.asiainfo.monitor.exeframe.config.bo.BOAIMonUser");
        
        bindBoName("userDetail", "com.asiainfo.monitor.exeframe.config.bo.BOAIMonUser");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus userDetail = createData("userDetail");
        if (userDetail != null && !userDetail.getDataObject().isEmpty())
        {
            setUserDetail(userDetail.getDataObject());
        }
        IDataBus qryform = createData("qryform");
        if (qryform != null && !qryform.getDataObject().isEmpty())
        {
            setQryform(qryform.getDataObject());
        }
        IDataBus userInfoTabItem = createData("userInfoTabItem");
        if (userInfoTabItem != null && !userInfoTabItem.getDataObject().isEmpty())
        {
            setUserInfoTabItem(userInfoTabItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
