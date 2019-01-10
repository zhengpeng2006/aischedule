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
import com.ailk.common.data.IDataContext;
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
import com.asiainfo.monitor.busi.service.interfaces.IAPIShowCacheSV;
import com.asiainfo.monitor.interapi.config.AIBusinessCacheInfo;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class BusinessCacheInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public void auto_getBusiCache_queryOnclick(IRequestCycle cycle)
        throws Exception
    {
        IDataBus businessQueryBus = createData("businessQuery");
        JSONObject businessQueryCondition = (JSONObject)businessQueryBus.getData();
        
        String appId = getContext().getParameter("appId");
        String type = "";
        if (StringUtils.isNotBlank(getContext().getParameter("cacheType")))
        {
            type = getContext().getParameter("cacheType");
        }
        
        IAPIShowCacheSV sv = (IAPIShowCacheSV)ServiceFactory.getService(IAPIShowCacheSV.class);
        List result = sv.getBusiCacheFromMultiApp(new Object[] {appId}, type);
        
        IDataBus bus = null;
        
        if (result == null || result.size() == 0)
        {
            bus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            bus =
                new DataBus(new DataContext(),
                    JsonVOBeanHelper.voBeansToJson(result.toArray(new AIBusinessCacheInfo[] {}),
                        AIBusinessCacheInfo.class));
        }
        
        setCacheInfoItems(bus.getDataArray());
        setCacheInfoCount(bus.getContext().getDataCount());
        
    }
    
    public abstract void setCacheInfoRowIndex(int cacheInfoRowIndex);
    
    public abstract void setCacheInfoCount(long cacheInfoCount);
    
    public abstract void setCacheInfoItem(JSONObject cacheInfoItem);
    
    public abstract void setCacheInfoItems(JSONArray cacheInfoItems);
    
    public abstract void setBusinessQuery(JSONObject businessQuery);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus cacheInfoItem = createData("cacheInfoItem");
        if (cacheInfoItem != null && !cacheInfoItem.getDataObject().isEmpty())
        {
            setCacheInfoItem(cacheInfoItem.getDataObject());
        }
        IDataBus businessQuery = createData("businessQuery");
        if (businessQuery != null && !businessQuery.getDataObject().isEmpty())
        {
            setBusinessQuery(businessQuery.getDataObject());
        }
        initExtend(cycle);
    }
    
    /**
     * auto_
     */
    public IDataBus auto_getBusiCacheType_cacheTypeSource(String appId)
        throws Exception
    {
        String key = "";
        String value = "";
        
        StringBuilder content = null;
        IDataContext context = new DataContext();
        JSONArray json = new JSONArray();
        
        String tempAppId = getContext().getParameter("appId");
        
        IAPIShowCacheSV sv = (IAPIShowCacheSV)ServiceFactory.getService(IAPIShowCacheSV.class);
        List result = sv.getBusiCacheType(tempAppId);
        if (result != null)
        {
            for (int i = 0; i < result.size(); i++)
            {
                content = new StringBuilder("");
                if (i == 0)
                {
                    content.append("{\"TEXT\":\"").append("全部").append("\",\"VALUE\":\"").append("").append("\"}");
                    json.add(JSONObject.fromObject(content.toString()));
                    
                    content = new StringBuilder("");
                    content.append("{\"TEXT\":\"")
                        .append(result.get(i))
                        .append("\",\"VALUE\":\"")
                        .append(result.get(i))
                        .append("\"}");
                    json.add(JSONObject.fromObject(content.toString()));
                }
                else
                {
                    content.append("{\"TEXT\":\"")
                        .append(result.get(i))
                        .append("\",\"VALUE\":\"")
                        .append(result.get(i))
                        .append("\"}");
                    json.add(JSONObject.fromObject(content.toString()));
                }
            }
        }
        
        IDataBus rtn = new DataBus(context, json);
        return rtn;
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
