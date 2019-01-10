package com.asiainfo.monitor.exeframe.monitorItem.view;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.abo.ivalues.IBOABGMonWTriggerValue;
import com.asiainfo.common.service.interfaces.IABGMonWTriggerSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class TriggerInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public void loadWTriggerInfo(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        IDataBus qryformBus = createData("qryForm");
        JSONObject qryformCondition = (JSONObject)qryformBus.getData();
        
        int start = Integer.parseInt(offset) + 1;
        int end = Integer.parseInt(offset) + Integer.parseInt(linage);
        
        String ip = (String)qryformCondition.get("ip");
        String infoName = (String)qryformCondition.get("infoName");
        String hostName = (String)qryformCondition.get("hostName");
        String beginDate = (String)qryformCondition.get("beginDate");
        String endDate = (String)qryformCondition.get("endDate");
        
        IABGMonWTriggerSV sv = (IABGMonWTriggerSV)ServiceFactory.getService(IABGMonWTriggerSV.class);
        IBOABGMonWTriggerValue[] result =
            sv.getTriggerValuesByIpInfoName(ip, infoName, hostName, beginDate, endDate, start, end);
        int totalCnt = sv.getCntByCond(ip, infoName, hostName, beginDate, endDate);
        
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOABGMonWTriggerValue.class);
        /*
         * setTriggerTabItems(bus.getDataArray()); setTriggerTabCount(totalCnt);
         */
        
        JSONObject jsonObject = new JSONObject();
        trimRemarksNull(bus.getDataArray());
        jsonObject.put("triggerTabItems", bus.getDataArray());
        jsonObject.put("total", totalCnt);
        this.setAjax(jsonObject);
    }
    
	private void trimRemarksNull(JSONArray array) {
		try {
			for (Object object : array) {
				JSONObject j = (JSONObject) object;
				if ("null".equals(j.get("remarks"))) {
					j.put("remarks", "");
				}
			}
		} catch (Exception e) {
		}
	}
    
    public abstract void setTriggerTabItem(JSONObject triggerTabItem);
    
    public abstract void setTriggerTabRowIndex(int triggerTabRowIndex);
    
    public abstract void setTriggerTabItems(JSONArray triggerTabItems);
    
    public abstract void setTriggerTabCount(long triggerTabCount);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("triggerTab", "com.asiainfo.common.abo.bo.BOABGMonWTrigger");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus triggerTabItem = createData("triggerTabItem");
        if (triggerTabItem != null && !triggerTabItem.getDataObject().isEmpty())
        {
            setTriggerTabItem(triggerTabItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
