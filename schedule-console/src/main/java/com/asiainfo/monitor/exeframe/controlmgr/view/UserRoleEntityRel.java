package com.asiainfo.monitor.exeframe.controlmgr.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IData;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.impl.TreeBean;
import com.ailk.common.data.impl.TreeHelper;
import com.ailk.common.data.impl.TreeItem;
import com.ailk.common.util.Utility;
import com.ailk.web.view.component.tree.TreeFactory;
import com.ailk.web.view.component.tree.TreeParam;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserPriEntitySV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleEntityRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserPriEntityBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserRoleEntityRelBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserPriEntityValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleEntityRelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivUtils;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class UserRoleEntityRel extends AppPage
{
    
    public abstract void setUserRoleTabRowIndex(int userRoleTabRowIndex);
    
    public abstract void setUserPrivEntityTabCount(long userPrivEntityTabCount);
    
    public abstract void setUserRoleTabItems(JSONArray userRoleTabItems);
    
    public abstract void setUserRoleTabCount(long userRoleTabCount);
    
    public abstract void setUserRoleTabItem(JSONObject userRoleTabItem);
    
    public abstract void setQryform(JSONObject qryform);
    
    public abstract void setUserPrivEntityTabItem(JSONObject userPrivEntityTabItem);
    
    public abstract void setUserPrivEntityTabRowIndex(int userPrivEntityTabRowIndex);
    
    public abstract void setUserPrivEntityTabItems(JSONArray userPrivEntityTabItems);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("userRoleTab", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUserRole");
        
        bindBoName("userPrivEntityTab", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUserPriEntity");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus userRoleTabItem = createData("userRoleTabItem");
        if (userRoleTabItem != null && !userRoleTabItem.getDataObject().isEmpty())
        {
            setUserRoleTabItem(userRoleTabItem.getDataObject());
        }
        IDataBus qryform = createData("qryform");
        if (qryform != null && !qryform.getDataObject().isEmpty())
        {
            setQryform(qryform.getDataObject());
        }
        IDataBus userPrivEntityTabItem = createData("userPrivEntityTabItem");
        if (userPrivEntityTabItem != null && !userPrivEntityTabItem.getDataObject().isEmpty())
        {
            setUserPrivEntityTabItem(userPrivEntityTabItem.getDataObject());
        }
        initExtend(cycle);
    }
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public void loadUserRoleData(IRequestCycle cycle)
        throws Exception
    {
        IDataBus qryformBus = createData("qryform");
        JSONObject qryformCondition = (JSONObject)qryformBus.getData();
        String roleCode = (String)qryformCondition.get("roleCode");
        String roleName = (String)qryformCondition.get("roleName");
        IAIMonUserRoleSV sv = (IAIMonUserRoleSV)ServiceFactory.getService(IAIMonUserRoleSV.class);
        IBOAIMonUserRoleValue[] result = sv.getUserRoleByCond(roleCode, roleName);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserRoleValue.class);
        
        JSONArray dataArray = bus.getDataArray();
        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext())
        {
            JSONObject obj = (JSONObject)iterator.next();
            if (obj.get("createDate").toString().length() > 8)
            {
                obj.put("createDate", obj.get("createDate").toString().substring(0, 10));
            }
            if (obj.get("doneDate").toString().length() > 8)
            {
                obj.put("doneDate", obj.get("doneDate").toString().substring(0, 10));
            }
        }
        setUserRoleTabItems(dataArray);
        setUserRoleTabCount(dataArray.size());
    }
    
    /**
     * 根据用户组标识查询用户信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public void qryUserEntityListByRoleId(IRequestCycle cycle)
        throws Exception
    {
        String userRoleId = getContext().getParameter("userRoleId");
        if (StringUtils.isBlank(userRoleId))
        {
            return;
        }
        IAIMonUserPriEntitySV entitySv = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
        IBOAIMonUserPriEntityValue[] result = entitySv.getUserEntityListByRoleId(userRoleId);
        
        IDataBus dataBus = null;
        if (null == result)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserGroupValue.class);
        }
        this.setUserPrivEntityTabItems(dataBus.getDataArray());
        this.setUserPrivEntityTabCount(dataBus.getDataArray().size());
        
    }
    
    /**
     * 查询不属于该用户组的用户信息列表
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryRelUserEntityListByRoleId(IRequestCycle cycle)
        throws Exception
    {
        String userRoleId = getContext().getParameter("userRoleId");
        if (StringUtils.isBlank(userRoleId))
        {
            return;
        }
        IAIMonUserPriEntitySV entitySv = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
        IBOAIMonUserPriEntityValue[] result = entitySv.getRelUserEntityListByRoleId(userRoleId);
        
        IDataBus dataBus = null;
        if (null == result)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(result, IBOAIMonUserGroupValue.class);
        }
        // this.setRelUserPrivEntityTabItems(dataBus.getDataArray());
        // this.setRelUserPrivEntityTabCount(dataBus.getDataArray().size());
        
    }
    
    /**
     * 保存用户关联信息
     * 
     * @param cycle
     * @throws Exception
     */
    public void userRoleEntityRelSave(IRequestCycle cycle)
        throws Exception
    {
        
        IDataBus dataBus = this.createData("paramJson");
        JSONObject dataObject = dataBus.getDataObject();
        String userRoleId = dataObject.get("userRoleId").toString();
        if (StringUtils.isBlank(userRoleId))
        {
            return;
        }
        
        IAIMonUserRoleEntityRelSV relSv =
            (IAIMonUserRoleEntityRelSV)ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
        JSONArray jsonArray = (JSONArray)dataObject.get("userIdList");
        
        // 先删掉该角色下所有的权限实体
        relSv.delete(Long.parseLong(userRoleId));
        
        BOAIMonUserRoleEntityRelBean bean = null;
        String entityId = "";
        for (Object object : jsonArray)
        {
            JSONObject jsonObj = (JSONObject)object;
            bean = new BOAIMonUserRoleEntityRelBean();
            bean.setUserRoleId(Long.parseLong(userRoleId));
            entityId = jsonObj.get("entityId").toString();
            bean.setEntityId(Long.parseLong(entityId));
            
            // 如果该组下已存在该用户，则不录入数据库
            if (!relSv.checkUserRoleEntityRel(entityId, userRoleId))
            {
                relSv.saveOrUpdate(bean);
            }
        }
        
    }
    
    /**
     * 删除用户组用户关联信息
     * 
     * @param cycle
     * @throws Exception
     */
    public void deleteUserRoleEntityRel(IRequestCycle cycle)
        throws Exception
    {
        IDataBus dataBus = this.createData("paramJson");
        JSONObject dataObject = dataBus.getDataObject();
        String userRoleId = dataObject.get("userRoleId").toString();
        if (StringUtils.isBlank(userRoleId))
        {
            return;
        }
        JSONArray jsonArray = (JSONArray)dataObject.get("userEntityList");
        IAIMonUserRoleEntityRelSV relSv =
            (IAIMonUserRoleEntityRelSV)ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
        for (Object object : jsonArray)
        {
            JSONObject jsonObj = (JSONObject)object;
            long entityId = Long.parseLong(jsonObj.get("entityId").toString());
            
            relSv.delete(Long.parseLong(userRoleId), entityId);
        }
    }
    
    /**
     * 加载实体树
     * 
     * @param cycle
     * @throws Exception
     */
    public void loadEntityTreeData(IRequestCycle cycle)
        throws Exception
    {
        try
        {
            // 获取角色标识
            String userRoleId = getContext().getParameter("userRoleId");
            // String userRoleId = "6";
            TreeParam param = TreeParam.getTreeParam(cycle);
            String parentId = param.getParentNodeId();
            // 逐级节点加载树
            // if(StringUtils.isBlank(parentId)) {
            // parentId = "0";
            // }
            //
            // TreeBean[] treeBeanArr = getTreeItemsById(parentId, userRoleId);
            // IData treeNodes = TreeHelper.buildTreeData(param, treeBeanArr);
            
            // 同步全量加载树
            TreeBean[] treeBeanArr = getTreeItemsById(parentId, userRoleId);
            TreeItem[] root = TreeHelper.getWholeTreeData(treeBeanArr);
            IData treeNodes = TreeFactory.buildTreeData(param, root);
            
            getContext().setAjax(treeNodes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    private TreeBean[] getTreeItemsById(String parentId, String userRoleId)
        throws Exception
    {
        // 获取该角色拥有的所有节点信息
        IAIMonUserRoleEntityRelSV relSv =
            (IAIMonUserRoleEntityRelSV)ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
        IBOAIMonUserRoleEntityRelValue[] relBeans = relSv.getEntityIdByRoleId(userRoleId);
        
        // 获取参数值
        IAIMonUserPriEntitySV entitySv = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
        BOAIMonUserPriEntityBean[] entityBeanArr = entitySv.getPrivEntityPrior(parentId);
        
        List<TreeBean> retList = new ArrayList<TreeBean>();
        TreeBean bean = null;
        for (BOAIMonUserPriEntityBean entity : entityBeanArr)
        {
            
            bean = new TreeBean();
            // 暂时这里有问题，id和code不爽
            bean.setId(String.valueOf(entity.getEntityId()));
            bean.setCode(String.valueOf(entity.getEntityId()));
            bean.setLabel(entity.getEntityName());
            bean.setParentId(String.valueOf(entity.getParentId()));
            bean.setNodeCount(1);
            bean.setLevel("1");
            for (IBOAIMonUserRoleEntityRelValue relBean : relBeans)
            {
                if (relBean.getEntityId() == entity.getEntityId())
                {
                    bean.setChecked(true);
                    break;
                }
            }
            
            retList.add(bean);
        }
        return retList.toArray(new TreeBean[0]);
    }
    
    /**
     * 加载treetable数据
     * 
     * @param cycle
     * @throws Exception
     */
    public void loadRoleEntityTreeData(IRequestCycle cycle)
        throws Exception
    {
        try
        {
            /** 定义节点参数 */
            TreeParam param = TreeParam.getTreeParam(cycle);
            /** 判断是否有节点，没次点击节点时，会将treeId的值赋给parent_id */
            String parentId = param.getParentNodeId();
            
            /** 若没有上级节点，表示第一次载入，此时初始根节点 */
            if (parentId == null)
            {
                parentId = "0";
                /** 调用服务返回数据 */
                IDataBus nodes = getNodesByParentFromgetFileListByCond(parentId);
                
                if (nodes != null)
                {
                    JSONArray arr = new JSONArray();
                    arr.add(nodes.getDataArray().get(0));
                    // this.setUserPrivEntityTreeTabItems(arr);
                    // setUserPrivEntityTreeTabCount(arr.size());
                }
            }
            else
            {
                parentId = param.getNodeId();
                /** 根据上级节点获取子节点数据 */
                IDataBus nodes = getNodesByParentFromgetFileListByCond(parentId);
                /** Ajax方式返回到客户端 */
                if (nodes != null)
                {
                    getContext().setAjax(nodes);
                }
            }
        }
        catch (Exception e)
        {
            Utility.error(e.getMessage());
        }
    }
    
    public IDataBus getNodesByParentFromgetFileListByCond(String parentId)
        throws Exception
    {
        if (StringUtils.isBlank(parentId))
            return null;
        IDataBus result = null;
        IAIMonUserPriEntitySV entitySv = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
        BOAIMonUserPriEntityBean[] retArr = entitySv.getPrivEntityPrior(parentId);
        if (retArr.length > 0)
        {
            result = DataBusHelper.getDataBusByBeans(retArr, BOAIMonUserPriEntityBean.class);
            result.getContext().setDataCount(1);
        }
        else
        {
            result = DataBusHelper.getEmptyArrayDataBus();
            result.getContext().setDataCount(0);
        }
        return result;
    }
    
    /**
     * 前台状态翻译
     * 
     * @param type
     * @param key
     * @return
     */
    public String doTranslate(String type, String val)
    {
        return UserPrivUtils.doTranslate(type, val);
    }
    
}
