package com.asiainfo.monitor.exeframe.configmgr.view;

import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.monitor.busi.cache.impl.AIMonStaticDataCacheImpl;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class PInfoGroupConfig extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public void auto_getPInfoGroupByCodeAndName_queryOnclick(IRequestCycle cycle)
        throws Exception
    {
        IDataBus queryFormBus = createData("queryForm");
        JSONObject queryFormCondition = (JSONObject)queryFormBus.getData();
        String groupCode = (String)queryFormCondition.get("code");
        String groupName = (String)queryFormCondition.get("name");
        String layer = String.valueOf("  ");
        int startNum = Integer.parseInt(queryFormBus.getContext().getPaginStart() + "");
        int endNum = Integer.parseInt(queryFormBus.getContext().getPaginEnd() + "");
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV.class);
        com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue[] result =
            sv.getPInfoGroupByCodeAndName(groupCode, groupName, layer, -1, -1);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus =
                DataBusHelper.getDataBusByBeans(result,
                    com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue.class);
        
        JSONArray dataArray = bus.getDataArray();
        setGoupInfosItems(dataArray);
        setGoupInfosCount(dataArray.size());
        
    }
    
    public void auto_delete_deleteOnclick(IRequestCycle cycle)
        throws Exception
    {
        long areaId = Long.parseLong(getContext().getParameter("groupId"));
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV.class);
        try
        {
            sv.delete(areaId);
        }
        catch (Exception e)
        {
            if (e != null && "FAIL".equals(e.getMessage()))
            {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("DEL_FLAG", "F");
                this.setAjax(jsonObj);
            }
            else
            {
                throw e;
            }
        }
    }
    
    public void auto_saveOrUpdate_saveOnclick(IRequestCycle cycle)
        throws Exception
    {
        IDataBus pGroupInfoBus = createData("pGroupInfo");
        JSONObject pGroupInfoCondition = (JSONObject)pGroupInfoBus.getData();
        com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue value =
            (com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue)DataBusHelper.getBOBean(pGroupInfoBus);
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV.class);
        sv.saveOrUpdate(value);
    }
    
    public IDataBus getSelectList(String constCode)
        throws Exception
    {
        HashMap all = CacheFactory.getAll(AIMonStaticDataCacheImpl.class);
        IBOAIMonStaticDataValue[] result = (IBOAIMonStaticDataValue[])all.get("_CT^" + constCode);
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
    
    public void auto_getMonPInfoGroupById_updateOnclick(IRequestCycle cycle)
        throws Exception
    {
        long itemId = Long.parseLong(getContext().getParameter("groupId"));
        com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV sv =
            (com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV)ServiceFactory.getService(com.asiainfo.monitor.busi.exe.task.service.interfaces.IAIMonPInfoGroupSV.class);
        com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue result = sv.getMonPInfoGroupById(itemId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyDataBus();
        else
            bus =
                DataBusHelper.getDataBusByBeans(result,
                    com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue.class);
        
        setPGroupInfo(bus.getDataObject());
    }
    
    public abstract void setGoupInfosItems(JSONArray goupInfosItems);
    
    public abstract void setPGroupInfo(JSONObject pGroupInfo);
    
    public abstract void setGoupInfosRowIndex(int goupInfosRowIndex);
    
    public abstract void setGoupInfosCount(long goupInfosCount);
    
    public abstract void setGoupInfosItem(JSONObject goupInfosItem);
    
    public abstract void setQueryForm(JSONObject queryForm);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("goupInfos", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoGroup");
        
        bindBoName("pGroupInfo", "com.asiainfo.monitor.busi.exe.task.bo.BOAIMonPInfoGroup");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus pGroupInfo = createData("pGroupInfo");
        if (pGroupInfo != null && !pGroupInfo.getDataObject().isEmpty())
        {
            setPGroupInfo(pGroupInfo.getDataObject());
        }
        IDataBus goupInfosItem = createData("goupInfosItem");
        if (goupInfosItem != null && !goupInfosItem.getDataObject().isEmpty())
        {
            setGoupInfosItem(goupInfosItem.getDataObject());
        }
        IDataBus queryForm = createData("queryForm");
        if (queryForm != null && !queryForm.getDataObject().isEmpty())
        {
            setQueryForm(queryForm.getDataObject());
        }
        initExtend(cycle);
    }
    
}
