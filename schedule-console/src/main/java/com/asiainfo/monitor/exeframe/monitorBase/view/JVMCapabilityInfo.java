package com.asiainfo.monitor.exeframe.monitorBase.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.complex.cache.CacheFactory;
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
import com.asiainfo.monitor.busi.service.interfaces.IAPIJvmCapabilitySV;
import com.asiainfo.monitor.interapi.config.AIThreadInfo;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class JVMCapabilityInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public void auto_getThreadInfo_queryOnclick(IRequestCycle cycle)
        throws Exception
    {
        
        String appId = getContext().getParameter("appId");
        String name = getContext().getParameter("jvmName");
        
        IAPIJvmCapabilitySV sv = (IAPIJvmCapabilitySV)ServiceFactory.getService(IAPIJvmCapabilitySV.class);
        AIThreadInfo[] threadInfo = sv.getThreadInfo(appId, name);
        
        IDataBus bus = null;
        
        if (threadInfo == null)
        {
            bus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            List rest = new ArrayList();
            for (int i = 0; i < threadInfo.length; i++)
            {
                if (StringUtils.isNotBlank(name))
                {
                    if (threadInfo[i].getName().indexOf(name) >= 0)
                    {
                        rest.add(threadInfo[i]);
                    }
                }
                else
                {
                    rest.add(threadInfo[i]);
                }
            }
            
            bus =
                new DataBus(new DataContext(), JsonVOBeanHelper.voBeansToJson(rest.toArray(new AIThreadInfo[] {}),
                    AIThreadInfo.class));
        }
        
        setJvmInfoItems(bus.getDataArray());
        
        setJvmInfoCount(bus.getContext().getDataCount());
        
    }
    
    public abstract void setJvmQuery(JSONObject jvmQuery);
    
    public abstract void setJvmInfoRowIndex(int jvmInfoRowIndex);
    
    public abstract void setJvmInfoItems(JSONArray jvmInfoItems);
    
    public abstract void setJvmInfoItem(JSONObject jvmInfoItem);
    
    public abstract void setJvmInfoCount(long jvmInfoCount);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus jvmQuery = createData("jvmQuery");
        if (jvmQuery != null && !jvmQuery.getDataObject().isEmpty())
        {
            setJvmQuery(jvmQuery.getDataObject());
        }
        IDataBus jvmInfoItem = createData("jvmInfoItem");
        if (jvmInfoItem != null && !jvmInfoItem.getDataObject().isEmpty())
        {
            setJvmInfoItem(jvmInfoItem.getDataObject());
        }
        initExtend(cycle);
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
