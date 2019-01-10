package com.asiainfo.monitor.exeframe.configmgr.view;

import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonConModeValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonConModeSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class ConModeInfoDetail extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public abstract void setConDetail(JSONObject conDetail);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("conDetail", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonConMode");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus conDetail = createData("conDetail");
        if (conDetail != null && !conDetail.getDataObject().isEmpty())
        {
            setConDetail(conDetail.getDataObject());
        }
        initExtend(cycle);
    }
    
    public void qryConModeInfoDetail(IRequestCycle cycle)
        throws Exception
    {
        String conId = getContext().getParameter("conId");
        IAIMonConModeSV cmSv = (IAIMonConModeSV)ServiceFactory.getService(IAIMonConModeSV.class);
        IBOAIMonConModeValue value = cmSv.qryConModeInfoByConId(conId);
        
        IDataBus dataBus = null;
        if (value == null)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(value, IBOAIMonConModeValue.class);
        }
        
        this.setConDetail(dataBus.getDataObject());
    }
}
