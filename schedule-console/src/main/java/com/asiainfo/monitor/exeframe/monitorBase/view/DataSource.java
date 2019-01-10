package com.asiainfo.monitor.exeframe.monitorBase.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.complex.mbean.standard.datasource.DataSourceSummary;
import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.util.JsonVOBeanHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IData;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.ailk.common.data.impl.TreeBean;
import com.ailk.common.data.impl.TreeHelper;
import com.ailk.common.data.impl.TreeItem;
import com.ailk.web.view.component.tree.TreeFactory;
import com.ailk.web.view.component.tree.TreeParam;
import com.asiainfo.monitor.busi.cache.impl.AIMonGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.AIMonNodeCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.AIMonServerCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.Group;
import com.asiainfo.monitor.busi.cache.impl.Server;
import com.asiainfo.monitor.busi.cache.impl.ServerNode;
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowDsTsSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class DataSource extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public abstract void setDataSourceListItem(JSONObject DataSourceListItem);
    
    public abstract void setDataSourceListCount(long DataSourceListCount);
    
    public abstract void setDataSourceListItems(JSONArray DataSourceListItems);
    
    public abstract void setDataSourceListRowIndex(int DataSourceListRowIndex);
    
    @Override
    protected void initPageAttrs()
    {
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus DataSourceListItem = createData("DataSourceListItem");
        if (DataSourceListItem != null && !DataSourceListItem.getDataObject().isEmpty())
        {
            setDataSourceListItem(DataSourceListItem.getDataObject());
        }
        initExtend(cycle);
    }
    
    public void auto_getDataSourceConfig_RefreshOnclick(IRequestCycle cycle)
        throws Exception
    {
        String appId = getContext().getParameter("appId");
        IAPIShowDsTsSV sv = (IAPIShowDsTsSV)ServiceFactory.getService(IAPIShowDsTsSV.class);
        List result = sv.getDataSourceConfig(appId);
        
        IDataBus bus = null;
        
        if (result == null || result.size() == 0)
        {
            bus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            bus =
                new DataBus(new DataContext(),
                    JsonVOBeanHelper.voBeansToJson(result.toArray(new DataSourceSummary[] {}), DataSourceSummary.class));
        }
        
        setDataSourceListItems(bus.getDataArray());
        setDataSourceListCount(bus.getContext().getDataCount());
        
    }
    
    public void loadTreeData(IRequestCycle cycle)
        throws Exception
    {
        try
        {
            // 获取角色标识
            TreeParam param = TreeParam.getTreeParam(cycle);
            
            // 同步全量加载树
            TreeBean[] treeBeanArr = this.getTreeItems();
            
            TreeItem[] root = TreeHelper.getWholeTreeData(treeBeanArr);
            IData treeNodes = TreeFactory.buildTreeData(param, root);
            
            getContext().setAjax(treeNodes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private TreeBean[] getTreeItems()
        throws Exception
    {
        // 获取所有监控分组信息
        Map<String, Group> groupMap = CacheFactory.getAll(AIMonGroupCacheImpl.class);
        // 获取所有服务节点信息
        Map<String, ServerNode> serverMap = CacheFactory.getAll(AIMonNodeCacheImpl.class);
        // 获取节点应用信息
        Map<String, Server> appMap = CacheFactory.getAll(AIMonServerCacheImpl.class);
        
        // 树节点list
        List<TreeBean> treeList = new ArrayList<TreeBean>();
        
        // 根节点初始化
        TreeBean rootNode = new TreeBean();
        rootNode.setCode("root");
        rootNode.setId("-1");
        rootNode.setLabel("系统节点");
        rootNode.setLevel("1");
        rootNode.setParentId("0");
        rootNode.setValue("root");
        treeList.add(rootNode);
        
        // 获取树的一级节点
        Group[] grpArr = groupMap.values().toArray(new Group[0]);
        ServerNode[] servNodeArr = serverMap.values().toArray(new ServerNode[0]);
        Server[] appArr = appMap.values().toArray(new Server[0]);
        TreeBean treeNode = null;
        for (int i = 0; i < grpArr.length; i++)
        {
            treeNode = new TreeBean();
            treeNode.setId(grpArr[i].getGroup_Id());
            treeNode.setLabel(grpArr[i].getGroup_Name());
            treeNode.setLevel("2");
            treeNode.setParentId(rootNode.getId());
            
            treeList.add(treeNode);
            TreeBean subTreeNode = null;
            // 获取二级节点信息
            for (ServerNode serverNode : servNodeArr)
            {
                if (grpArr[i].getGroup_Id().equals(serverNode.getGroup_Id()))
                {
                    subTreeNode = new TreeBean();
                    subTreeNode.setId(serverNode.getNode_Id());
                    subTreeNode.setLabel(serverNode.getNode_Name());
                    subTreeNode.setLevel("3");
                    subTreeNode.setValue(serverNode.getNode_Code());
                    subTreeNode.setParentId(treeNode.getId());
                    
                    treeList.add(subTreeNode);
                    TreeBean sub3TreeNode = null;
                    // 获取三级应用节点信息
                    for (Server server : appArr)
                    {
                        if (server.getNode_Id().equals(subTreeNode.getId()))
                        {
                            sub3TreeNode = new TreeBean();
                            sub3TreeNode.setId(server.getApp_Id());
                            sub3TreeNode.setLabel(server.getApp_Name());
                            sub3TreeNode.setLevel("4");
                            sub3TreeNode.setValue(server.getApp_Code());
                            sub3TreeNode.setParentId(subTreeNode.getId());
                            
                            treeList.add(sub3TreeNode);
                        }
                        
                    }
                }
            }
            
        }
        
        return treeList.toArray(new TreeBean[0]);
    }
}
