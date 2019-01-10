package com.asiainfo.mainFrame.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.privilege.UserInfoInterface;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IData;
import com.ailk.common.data.impl.TreeBean;
import com.ailk.common.data.impl.TreeHelper;
import com.ailk.common.data.impl.TreeItem;
import com.ailk.web.view.component.tree.TreeFactory;
import com.ailk.web.view.component.tree.TreeParam;
import com.asiainfo.mainFrame.util.MenuUtil;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class Mainframe extends AppPage
{
    
    private static final Logger LOGGER = Logger.getLogger(Mainframe.class);
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 加载树
     * 
     * @param request
     * @throws Exception
     */
    public void loadTreeData(IRequestCycle request)
        throws Exception
    {
        // 获取角色标识
        TreeParam param = TreeParam.getTreeParam(request);
        UserInfoInterface user = ServiceManager.getUser();
        
        if (user == null)
        {
            LOGGER.error("no auth, user have not login");
            return;
        }
        
        // 同步全量加载树
        TreeBean[] treeBeanArr = MenuUtil.getTreeItems(user, request.getRequestContext().getRequest());
        
        TreeItem[] root = TreeHelper.getWholeTreeData(treeBeanArr);
        IData treeNodes = TreeFactory.buildTreeData(param, root);
        getContext().setAjax(treeNodes);
    }
    
    /**
     * 获取系统结构菜单树数据
     * 
     * @return
     * @throws Exception
     */
    public static TreeBean[] getTreeItems()
        throws Exception
    {
        
        // 树节点list
        List<TreeBean> treeList = new ArrayList<TreeBean>();
        // 根节点初始化
        TreeBean rootNode = new TreeBean();
        rootNode.setCode("root");
        rootNode.setId("-1");
        rootNode.setLabel("x86平台");
        rootNode.setLevel("1");
        rootNode.setParentId("0");
        rootNode.setValue("root");
        treeList.add(rootNode);
        
        String[] str = new String[] {"1-监控", "2-配置", "3-发布"};
        // 循环遍历生成树节点数据
        TreeBean treeNode2 = null;
        for (int i = 0; i < str.length; i++)
        {
            String level = "2";
            String[] temp = str[i].split("-");
            treeNode2 = new TreeBean();
            treeNode2.setId(temp[0] + "_" + level);
            treeNode2.setLabel(temp[1]);
            treeNode2.setLevel(level);
            treeNode2.setParentId(rootNode.getId());
            treeList.add(treeNode2);
            
        }
        
        return treeList.toArray(new TreeBean[0]);
    }
    
    @Override
    protected void initPageAttrs()
    {
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        
        initExtend(cycle);
    }
    
}
