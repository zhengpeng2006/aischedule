package com.asiainfo.monitor.exeframe.monitorItem.view;

import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IData;
import com.ailk.common.data.impl.TreeBean;
import com.ailk.common.data.impl.TreeHelper;
import com.ailk.common.data.impl.TreeItem;
import com.ailk.web.view.component.tree.TreeFactory;
import com.ailk.web.view.component.tree.TreeParam;
import com.asiainfo.monitor.busi.service.interfaces.IAPIJvmCapabilitySV;
import com.asiainfo.monitor.common.CommonSvUtil;
import com.asiainfo.monitor.interapi.config.AIMemoryInfo;


/**
 * Created by SMT 
 * Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class MemoryInfo extends AppPage {

    private void initExtend(IRequestCycle cycle)
    {
    
    }

    private void initExtend()
    {
    
    }

    protected void initPageAttrs()
    {
        initExtend();
    }

    public void initPage(IRequestCycle cycle)
    {
    
        initExtend(cycle);
    }

    public void loadTreeData(IRequestCycle cycle) throws Exception
    {
        try {
            // 获取角色标识
            TreeParam param = TreeParam.getTreeParam(cycle);

            // 同步全量加载树
            TreeBean[] treeBeanArr = CommonSvUtil.getTreeItems(4);

            TreeItem[] root = TreeHelper.getWholeTreeData(treeBeanArr);
            IData treeNodes = TreeFactory.buildTreeData(param, root);

            getContext().setAjax(treeNodes);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    
    
    /**
     * 查询监控服务器内存信息
     * 
     * @param request
     */
    public void qryServerMemInfo(IRequestCycle request) throws Exception
    {
        // 获取要查询的应用服务器标识
        String appServerId = getContext().getParameter("appServerId");
        
        IAPIJvmCapabilitySV apiSv = (IAPIJvmCapabilitySV)ServiceFactory.getService(IAPIJvmCapabilitySV.class);
        AIMemoryInfo memInfo = apiSv.getJVM5MemoryInfo(appServerId);

        // 计算内存使用百分比
        float percent = Float.parseFloat(String.valueOf(memInfo.getUsed())) / memInfo.getTotal();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("X", System.currentTimeMillis());
        jsonObj.put("Y", percent * 100);
        this.setAjax(jsonObj);
    }
    
}
