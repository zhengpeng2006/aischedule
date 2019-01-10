package com.asiainfo.monitor.exeframe.configmgr.view;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.common.combin.CmbHostBean;
import com.asiainfo.monitor.common.service.interfaces.IBaseCommonSV;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonConModeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonMasterSlaveRelSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class HostAllInfo extends AppPage
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
        
        // 通过codeType,codeValue,从表ai_mon_static_data得到codeName展示到前台页面
        IAIMonStaticDataSV sdSv = (IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
        IBOAIMonStaticDataValue value = sdSv.queryByCodeTypeAndValue(type, val);
        text = value.getCodeName();
        return text;
    }
    
    /**
     * 查询主机基本信息
     * 
     * @param hostId
     * @return
     * @throws Exception
     */
    public void qryAllInfo(IRequestCycle request)
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
        
        // this.setHostAllDetail(dataBus.getDataObject());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostAllDetail", dataBus.getDataObject());
        this.setAjax(jsonObject);
    }
    
    /**
     * 查询主机用户信息
     * 
     * @param hostId
     * @return
     * @throws Exception
     */
    /*
     * public void qryHostUserInfo(IRequestCycle request) throws Exception { String hostId =
     * getContext().getParameter("hostId"); IBaseCommonSV commonService = (IBaseCommonSV)
     * ServiceFactory.getService(IBaseCommonSV.class); CmbHostBean cmbHost = commonService.qryCmbHostInfo(hostId);
     * IBOAIMonHostUserValue[] result = cmbHost.getHostUserArr();
     * 
     * IDataBus bus = null; if(result == null) bus = DataBusHelper.getEmptyArrayDataBus(); else bus =
     * DataBusHelper.getDataBusByBeans(result, IBOAIMonConModeValue.class); JSONArray dataArray = bus.getDataArray();
     * Iterator iterator = dataArray.iterator(); while(iterator.hasNext()) { JSONObject obj = (JSONObject)
     * iterator.next(); if(obj.get("createDate").toString().length() > 8) { obj.put("createDate",
     * obj.get("createDate").toString().substring(0, 10)); } } this.setHostUserTabItems(dataArray);
     * 
     * }
     */
    
    public void qryHostConInfo(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String hostId = getContext().getParameter("hostId");
        IBaseCommonSV commonService = (IBaseCommonSV)ServiceFactory.getService(IBaseCommonSV.class);
        CmbHostBean cmbHost = commonService.qryCmbHostInfo(hostId);
        IBOAIMonConModeValue[] result = cmbHost.getConModeArr();
        
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonConModeValue.class);
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
        // this.setHostConTabItems(dataArray);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostConTabItems", PagingUtil.convertArray2Page(dataArray, offset, linage));
        jsonObject.put("total", dataArray.size());
        this.setAjax(jsonObject);
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
        // this.setHostSlaveTabItems(dataArray);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostSlaveTabItems", dataArray);
        jsonObject.put("total", dataArray.size());
        this.setAjax(jsonObject);
        
    }
    
    public abstract void setHostConTabRowIndex(int hostConTabRowIndex);
    
    public abstract void setHostSlaveTabRowIndex(int hostSlaveTabRowIndex);
    
    public abstract void setHostSlaveTabItem(JSONObject hostSlaveTabItem);
    
    public abstract void setHostAllDetail(JSONObject hostAllDetail);
    
    public abstract void setHostUserTabItems(JSONArray hostUserTabItems);
    
    public abstract void setHostConTabItems(JSONArray hostConTabItems);
    
    public abstract void setHostUserTabCount(long hostUserTabCount);
    
    public abstract void setHostSlaveTabCount(long hostSlaveTabCount);
    
    public abstract void setHostConTabCount(long hostConTabCount);
    
    public abstract void setHostUserTabRowIndex(int hostUserTabRowIndex);
    
    public abstract void setHostConTabItem(JSONObject hostConTabItem);
    
    public abstract void setHostSlaveTabItems(JSONArray hostSlaveTabItems);
    
    public abstract void setHostUserTabItem(JSONObject hostUserTabItem);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("hostAllDetail", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHost");
        
        bindBoName("hostUserTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostUser");
        
        bindBoName("hostConTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonConMode");
        
        bindBoName("hostSlaveTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonPhysicHost");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus hostSlaveTabItem = createData("hostSlaveTabItem");
        if (hostSlaveTabItem != null && !hostSlaveTabItem.getDataObject().isEmpty())
        {
            setHostSlaveTabItem(hostSlaveTabItem.getDataObject());
        }
        IDataBus hostAllDetail = createData("hostAllDetail");
        if (hostAllDetail != null && !hostAllDetail.getDataObject().isEmpty())
        {
            setHostAllDetail(hostAllDetail.getDataObject());
        }
        IDataBus hostConTabItem = createData("hostConTabItem");
        if (hostConTabItem != null && !hostConTabItem.getDataObject().isEmpty())
        {
            setHostConTabItem(hostConTabItem.getDataObject());
        }
        IDataBus hostUserTabItem = createData("hostUserTabItem");
        if (hostUserTabItem != null && !hostUserTabItem.getDataObject().isEmpty())
        {
            setHostUserTabItem(hostUserTabItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
