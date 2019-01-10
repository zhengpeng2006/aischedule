package com.asiainfo.monitor.exeframe.monitorPortal.view;

import com.ailk.appengine.web.view.AppPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.common.DataStructInterface;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.bo.PagingBeansContainer;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.ailk.appengine.complex.abo.values.interfaces.IValueInterface;

import com.ailk.common.data.IData;
import com.ailk.common.data.IDataset;
import com.ailk.common.data.impl.DataMap;
import com.ailk.common.data.impl.DatasetList;

import com.ailk.common.data.impl.TreeBean;
import com.ailk.common.data.impl.TreeHelper;
import com.ailk.common.data.impl.TreeItem;
import com.ailk.web.view.component.tree.TreeFactory;
import com.ailk.web.view.component.tree.TreeParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Created by SMT 
 * Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class EchartTest extends AppPage {

    protected void initPageAttrs()
    {
        initExtend();
    }

    public void initPage(IRequestCycle cycle)
    {
    
        initExtend(cycle);
    }

    private void initExtend(IRequestCycle cycle)
    {
    
    }

    private void initExtend()
    {
    
    }
	

}
