package com.asiainfo.monitor.exeframe.monitorItem.view;

import org.apache.tapestry.IRequestCycle;

import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IData;
import com.ailk.common.data.impl.TreeBean;
import com.ailk.common.data.impl.TreeHelper;
import com.ailk.common.data.impl.TreeItem;
import com.ailk.web.view.component.tree.TreeFactory;
import com.ailk.web.view.component.tree.TreeParam;
import com.asiainfo.monitor.common.CommonSvUtil;


/**
 * Created by SMT 
 * Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class SqlStatInfo extends AppPage {

    /**
     * 加载组织树
     * 
     * @param request
     * @throws Exception
     */
    public void loadTreeData(IRequestCycle request) throws Exception
    {
        // 获取角色标识
        TreeParam param = TreeParam.getTreeParam(request);

        // 同步全量加载树
        TreeBean[] treeBeanArr = CommonSvUtil.getTreeItems(5);

        TreeItem[] root = TreeHelper.getWholeTreeData(treeBeanArr);
        IData treeNodes = TreeFactory.buildTreeData(param, root);

        getContext().setAjax(treeNodes);
    }

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
	

}
