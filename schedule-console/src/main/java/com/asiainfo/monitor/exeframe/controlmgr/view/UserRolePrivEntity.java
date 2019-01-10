package com.asiainfo.monitor.exeframe.controlmgr.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
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
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserPriEntitySV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserPriEntityBean;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserPriEntityValue;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class UserRolePrivEntity extends AppPage
{
    private Logger logger = Logger.getLogger(UserRolePrivEntity.class);
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * auto_
     */
    public void loadTreeData(IRequestCycle cycle)
    {
        try
        {
            TreeParam param = TreeParam.getTreeParam(cycle);
            TreeBean[] beans = getTreeFromgetPageFrameByPId(cycle);
            TreeItem[] root = TreeHelper.getWholeTreeData(beans);
            IData treeData = TreeFactory.buildTreeData(param, root);
            getContext().setAjax(treeData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private TreeBean[] getTreeFromgetPageFrameByPId(IRequestCycle cycle)
        throws Exception
    {
        // 获取参数值
        IAIMonUserPriEntitySV entitySv = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
        BOAIMonUserPriEntityBean[] entityBeanArr = entitySv.getPrivEntityPrior(null);
        
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
            retList.add(bean);
        }
        return retList.toArray(new TreeBean[0]);
    }
    
    /**
     * 单击树节点事件
     * 
     * @param cycle
     */
    public void clickTreeNodeAction(IRequestCycle cycle)
        throws Exception
    {
        String entityId = getContext().getParameter("entityId");
        if (StringUtils.isEmpty(entityId))
        {
            logger.error(" entityId is null");
            
            return;
        }
        
        IAIMonUserPriEntitySV entitySv = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
        IBOAIMonUserPriEntityValue entity = entitySv.getPriEntityByEntityId(entityId);
        IDataBus bus = null;
        if (entity == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(entity, IBOAIMonUserPriEntityValue.class);
        
        this.setUserPrivEntityDetail(bus.getDataObject());
        
    }
    
    public IDataBus getPrivEntityConsts(String constCode)
        throws Exception
    {
        IAIMonStaticDataSV staticDataSV = (IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
        IBOAIMonStaticDataValue[] staticData = staticDataSV.queryByCodeType(constCode);
        Map<String, String> valueMap = new LinkedHashMap<String, String>();
        
        for (IBOAIMonStaticDataValue dataValue : staticData)
        {
            valueMap.put(dataValue.getCodeValue(), dataValue.getCodeName());
        }
        
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        
        Iterator<String> keyIt = valueMap.keySet().iterator();
        while (keyIt.hasNext())
        {
            String key = keyIt.next();
            String val = valueMap.get(key);
            
            JSONObject obj = new JSONObject();
            obj.put("VALUE", key);
            obj.put("TEXT", val);
            jsonArray.add(obj);
        }
        
        return new DataBus(context, jsonArray);
    }
    
    /**
     * 新增或保存操作
     * 
     * @param cycle
     * @throws Exception
     */
    public void saveOrUpdate(IRequestCycle cycle)
        throws Exception
    {
        IDataBus dataBus = createData("userPrivEntityDetail");
        String parentId = getContext().getParameter("parentId");
        
        IBOAIMonUserPriEntityValue value = (IBOAIMonUserPriEntityValue)DataBusHelper.getBOBean(dataBus);
        if (value.isNew())
        {
            value.setParentId(Long.parseLong(parentId));
        }
        
        IAIMonUserPriEntitySV sv = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
        sv.saveOrUpdate(value);
        
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("entityId", value.getEntityId());
        
        // 设置前台返回数据
        this.setAjax(jsonObj);
    }
    
    public void auto_delete_btn_DelOnclick(IRequestCycle cycle)
        throws Exception
    {
        String entityId = getContext().getParameter("entityId");
        if (StringUtils.isBlank(entityId))
        {
            return;
        }
        try
        {
            IAIMonUserPriEntitySV sv = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
            sv.delete(Long.parseLong(entityId));
        }
        catch (Exception e)
        {
            log.error("Call Permission's Method deleteUserInfo has Exception :" + e.getMessage());
            throw e;
        }
        
    }
    
    public void checkEntityNameCode(IRequestCycle cycle)
        throws Exception
    {
        String retFlag = "T";
        String entityCode = getContext().getParameter("entityCode");
        String entityName = getContext().getParameter("entityName");
        
        IAIMonUserPriEntitySV sv = (IAIMonUserPriEntitySV)ServiceFactory.getService(IAIMonUserPriEntitySV.class);
        boolean retBool = sv.checkByCodeAndName(entityCode, entityName);
        if (!retBool)
        {
            retFlag = "F";
        }
        
        JSONObject retObj = new JSONObject();
        retObj.put("code", retFlag);
        
        this.setAjax(retObj);
    }
    
    public abstract void setUserPrivEntityDetail(JSONObject userPrivEntityDetail);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("userPrivEntityDetail", "com.asiainfo.appframe.ext.exeframe.config.bo.BOAIMonUserPriEntity");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus userPrivEntityDetail = createData("userPrivEntityDetail");
        if (userPrivEntityDetail != null && !userPrivEntityDetail.getDataObject().isEmpty())
        {
            setUserPrivEntityDetail(userPrivEntityDetail.getDataObject());
        }
        initExtend(cycle);
    }
    
}
