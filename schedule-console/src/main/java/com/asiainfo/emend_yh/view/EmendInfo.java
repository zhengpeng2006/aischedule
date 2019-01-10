package com.asiainfo.emend_yh.view;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;

/**
 * Created by SMT Copyright: Copyright(c) 2015 Asiainfo-Linkage
 */
public abstract class EmendInfo extends AppPage
{
    
    public abstract void setTableCount(long tableCount);
    
    public abstract void setTableItems(JSONArray tableItems);
    
    public abstract void setForm(JSONObject form);
    
    public abstract void setTableRowIndex(int tableRowIndex);
    
    public abstract void setTableItem(JSONObject tableItem);
    
    public abstract void setQryCondition(JSONObject qryCondition);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("table", "com.asiainfo.emend_yh.bo.BOAmTaskRely");
        
        bindBoName("form", "com.asiainfo.emend_yh.bo.BOAmTaskRely");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus form = createData("form");
        if (form != null && !form.getDataObject().isEmpty())
        {
            setForm(form.getDataObject());
        }
        IDataBus tableItem = createData("tableItem");
        if (tableItem != null && !tableItem.getDataObject().isEmpty())
        {
            setTableItem(tableItem.getDataObject());
        }
        IDataBus qryCondition = createData("qryCondition");
        if (qryCondition != null && !qryCondition.getDataObject().isEmpty())
        {
            setQryCondition(qryCondition.getDataObject());
        }
        initExtend(cycle);
    }
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
}
