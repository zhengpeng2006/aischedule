package com.asiainfo.monitor.exeframe.configmgr.view;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.complex.util.e.K;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.monitor.busi.cache.impl.AIMonStaticDataCacheImpl;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostUserValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.vo.interfaces.IAIMonHostUserValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonHostUserSV;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivUtils;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class HostUserInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 前台状态翻译
     * 
     * @param type
     * @param key
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public String doTranslate(String type, String val)
        throws RemoteException, Exception
    {
        String text = null;
        if ("USER_STATE".equals(type))
        {
            text = UserPrivUtils.doTranslate(type, val);
        }
        else
        {
            
            // 通过codeType,codeValue,从表ai_mon_static_data得到codeName展示到前台页面
            IAIMonStaticDataSV sdSv = (IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
            IBOAIMonStaticDataValue value = sdSv.queryByCodeTypeAndValue(type, val);
            text = value.getCodeName();
        }
        return text;
    }
    
    public IDataBus getSelectList(String constCode)
        throws Exception
    {
        HashMap all = CacheFactory.getAll(AIMonStaticDataCacheImpl.class);
        IBOAIMonStaticDataValue[] result = null;
        if ("USER_TYPE".equals(constCode))
        {
            result = (IBOAIMonStaticDataValue[])all.get("_CT^USER_TYPE");
        }
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < result.length; i++)
        {
            JSONObject obj = new JSONObject();
            String key = result[i].getCodeName();
            String val = result[i].getCodeValue();
            obj.put("TEXT", key);
            obj.put("VALUE", val);
            jsonArray.add(obj);
        }
        return new DataBus(context, jsonArray);
    }
    
    public void qryHostUserInfo(IRequestCycle request)
        throws Exception
    {
        String hostId = getContext().getParameter("hostId");
        
        IAIMonHostUserSV huSv = (IAIMonHostUserSV)ServiceFactory.getService(IAIMonHostUserSV.class);
        
        IBOAIMonHostUserValue[] result = huSv.qryByHostId(hostId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IAIMonHostUserValue.class);
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
        this.setUserTabItems(dataArray);
        this.setUserTabCount(dataArray.size());
    }
    
    /**
     * 
     * @Function: HostUserInfo.java
     * @Description: 通过用户标识删除用户
     *
     * @param:参数描述
     * @return：返回结果描述
     * @throws：异常描述
     * @author: szh
     */
    public void delHostUser(IRequestCycle cycle)
        throws Exception
    {
        String hostUserId = getContext().getParameter("hostUserId");
        IAIMonHostUserSV huSv = (IAIMonHostUserSV)ServiceFactory.getService(IAIMonHostUserSV.class);
        IBOAIMonHostUserValue value = huSv.qryHostUserById(hostUserId);
        huSv.delete(value);
    }
    
    public void qryHostUserInfoDetail(IRequestCycle cycle)
        throws Exception
    {
        String hostUserId = getContext().getParameter("hostUserId");
        IAIMonHostUserSV huSv = (IAIMonHostUserSV)ServiceFactory.getService(IAIMonHostUserSV.class);
        IBOAIMonHostUserValue value = huSv.qryHostUserById(hostUserId);
        
        IDataBus dataBus = null;
        if (value == null)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(value, IBOAIMonHostUserValue.class);
        }
        
        this.setUserDetail(dataBus.getDataObject());
    }
    
    public void saveOrUpdate(IRequestCycle request)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        String hostId = getContext().getParameter("hostId");
        IDataBus dataBus = this.createData("userDetail");
        IBOAIMonHostUserValue value = (IBOAIMonHostUserValue)DataBusHelper.getBOBean(dataBus);
        IAIMonHostUserSV huSv = (IAIMonHostUserSV)ServiceFactory.getService(IAIMonHostUserSV.class);
        // String userName = value.getUserName();
        
        if (value.getHostUserId() == 0)
        {
            
            value.setHostId(Long.parseLong(hostId));
            value.setUserPwd(K.j(value.getUserPwd()));// 加密密码
            huSv.save(value);
            retMap.put("SAVE_FLAG", "A");
            
        }
        else
        {
            value.setUserPwd(K.j(value.getUserPwd()));// 加密密码
            huSv.save(value);
            retMap.put("SAVE_FLAG", "E");
        }
        /*
         * boolean b = huSv.isExistByUserName(hostId, userName); //已存在此用户名，不可添加 if(value.getHostUserId() == 0) { if(b) {
         * retMap.put("SAVE_FLAG", "N"); } else { value.setHostId(Long.parseLong(hostId));
         * value.setUserPwd(K.j(value.getUserPwd()));//加密密码 huSv.save(value); retMap.put("SAVE_FLAG", "A"); } } else {
         * value.setUserPwd(K.j(value.getUserPwd()));//加密密码
         * 
         * String hostUserId = Long.toString(value.getHostUserId()); IBOAIMonHostUserValue userValue =
         * huSv.qryHostUserById(hostUserId);
         * 
         * if(userValue.getUserName().equals(userName)) { huSv.save(value); retMap.put("SAVE_FLAG", "E"); } else {
         * boolean userNameIsExist = huSv.isExistByUserName(hostId, userName); if(userNameIsExist) {
         * retMap.put("SAVE_FLAG", "N"); } else { huSv.save(value); retMap.put("SAVE_FLAG", "E"); } } }
         */
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("SAVE_FLAG", retMap.get("SAVE_FLAG"));
        this.setAjax(jsonObj);
        
    }
    
    public abstract void setUserDetail(JSONObject userDetail);
    
    public abstract void setUserTabRowIndex(int userTabRowIndex);
    
    public abstract void setUserTabItem(JSONObject userTabItem);
    
    public abstract void setUserTabCount(long userTabCount);
    
    public abstract void setUserTabItems(JSONArray userTabItems);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("userTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostUser");
        
        bindBoName("userDetail", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostUser");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus userDetail = createData("userDetail");
        if (userDetail != null && !userDetail.getDataObject().isEmpty())
        {
            setUserDetail(userDetail.getDataObject());
        }
        IDataBus userTabItem = createData("userTabItem");
        if (userTabItem != null && !userTabItem.getDataObject().isEmpty())
        {
            setUserTabItem(userTabItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
