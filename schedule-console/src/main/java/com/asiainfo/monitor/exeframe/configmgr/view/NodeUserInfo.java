package com.asiainfo.monitor.exeframe.configmgr.view;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.comframe.utils.TimeUtil;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.monitor.busi.cache.impl.AIMonStaticDataCacheImpl;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonStaticDataSV;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonStaticDataValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeUserValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonUsersValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonNodeUserSV;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonUsersSV;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivUtils;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class NodeUserInfo extends AppPage
{
    /**
     * 前台状态翻译
     * 
     * @param type
     * @param key
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public String doTranslate(String type, String val)
        throws RemoteException, Exception
    {
        String text = null;
        if ("USER_ID".equals(type))
        {
            IAIMonUsersSV uSv = (IAIMonUsersSV)ServiceFactory.getService(IAIMonUsersSV.class);
            IBOAIMonUsersValue value = uSv.qryUserById(val);
            text = value.getUserCode();
        }
        if ("USER_STATE".equals(type))
        {
            text = UserPrivUtils.doTranslate(type, val);
        }
        if ("USER_TYPE".equals(type))
        {
            // 通过codeType,codeValue,从表ai_mon_static_data得到codeName展示到前台页面
            IAIMonStaticDataSV sdSv = (IAIMonStaticDataSV)ServiceFactory.getService(IAIMonStaticDataSV.class);
            IBOAIMonStaticDataValue value = sdSv.queryByCodeTypeAndValue(type, val);
            text = value.getCodeName();
        }
        return text;
    }
    
    /**
     * 查询节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    public void qryNodeUserInfo(IRequestCycle request)
        throws Exception
    {
        String nodeId = getContext().getParameter("nodeId");
        
        IAIMonNodeUserSV nuSv = (IAIMonNodeUserSV)ServiceFactory.getService(IAIMonNodeUserSV.class);
        
        IBOAIMonNodeUserValue[] result = nuSv.qryNodeUserByNodeId(nodeId);
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonNodeUserValue.class);
        JSONArray dataArray = bus.getDataArray();
        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext())
        {
            JSONObject obj = (JSONObject)iterator.next();
            if (obj.get("createDate").toString().length() > 8)
            {
                obj.put("createDate", obj.get("createDate").toString().substring(0, 10));
            }
            if (obj.get("modifyDate").toString().length() > 8)
            {
                obj.put("modifyDate", obj.get("modifyDate").toString().substring(0, 10));
            }
        }
        /* this.setNodeUserTabItems(dataArray); */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nodeUserTabItems", dataArray);
        this.setAjax(jsonObject);
    }
    
    public void qryNodeUserInfoDetail(IRequestCycle request)
        throws Exception
    {
        String nodeUserId = getContext().getParameter("nodeUserId");
        IAIMonNodeUserSV nuSv = (IAIMonNodeUserSV)ServiceFactory.getService(IAIMonNodeUserSV.class);
        IBOAIMonNodeUserValue value = nuSv.qryNodeUserById(nodeUserId);
        
        IDataBus dataBus = null;
        if (value == null)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(value, IBOAIMonNodeUserValue.class);
        }
        /*
         * this.setNodeUserDetail(dataBus.getDataObject());
         */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nodeUserDetail", dataBus.getDataObject());
        this.setAjax(jsonObject);
    }
    
    /**
     * 根据用户标识删除用户信息
     * 
     * @param request
     * @throws Exception
     */
    public void delNodeUser(IRequestCycle cycle)
        throws Exception
    {
        String nodeUserId = getContext().getParameter("nodeUserId");
        IAIMonNodeUserSV uSv = (IAIMonNodeUserSV)ServiceFactory.getService(IAIMonNodeUserSV.class);
        IBOAIMonNodeUserValue value = uSv.qryNodeUserById(nodeUserId);
        
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_MONITOR,
            CommonConstants.OPERATE_TYPE_MODIFY,
            CommonConstants.OPERTATE_OBJECT_NODE,
            value.getNodeId() + ":" + value.getNodeUserId(),
            "删除节点用户关系，节点ID为" + value.getNodeId() + ",用户ID为" + value.getNodeUserId());
        uSv.delete(value);
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
        this.setAjax(jsonObject);
    }
    
    public IDataBus getSelectList(String constCode)
        throws Exception
    {
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        HashMap all = CacheFactory.getAll(AIMonStaticDataCacheImpl.class);
        IBOAIMonStaticDataValue[] result = null;
        if ("USER_TYPE".equals(constCode))
        {
            result = (IBOAIMonStaticDataValue[])all.get("_CT^USER_TYPE");
            for (int i = 0; i < result.length; i++)
            {
                JSONObject obj = new JSONObject();
                String key = result[i].getCodeName();
                String val = result[i].getCodeValue();
                obj.put("TEXT", key);
                obj.put("VALUE", val);
                jsonArray.add(obj);
            }
        }
        else
        {
            IAIMonUsersSV uSv = (IAIMonUsersSV)ServiceFactory.getService(IAIMonUsersSV.class);
            IBOAIMonUsersValue[] values = uSv.qryAllUsersInfo();
            for (int i = 0; i < values.length; i++)
            {
                JSONObject obj = new JSONObject();
                String key = values[i].getUserCode();
                String val = Long.toString(values[i].getUserId());
                obj.put("TEXT", key);
                obj.put("VALUE", val);
                jsonArray.add(obj);
            }
        }
        return new DataBus(context, jsonArray);
    }
    
    /**
     * 保存节点用户信息
     * 
     * @param request
     * @throws Exception
     */
    public void saveOrUpdate(IRequestCycle request)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        String nodeId = getContext().getParameter("nodeId");
        IDataBus dataBus = this.createData("nodeUserDetail");
        IBOAIMonNodeUserValue value = (IBOAIMonNodeUserValue)DataBusHelper.getBOBean(dataBus);
        IAIMonNodeUserSV huSv = (IAIMonNodeUserSV)ServiceFactory.getService(IAIMonNodeUserSV.class);
        IBOAIMonNodeUserValue[] nodeValues = huSv.qryNodeUserByNodeId(nodeId);
        
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_MONITOR,
            CommonConstants.OPERATE_TYPE_MODIFY,
            CommonConstants.OPERTATE_OBJECT_NODE,
            value.getNodeId() + ":" + value.getNodeUserId(),
            "添加节点用户关系，节点ID为" + value.getNodeId() + ",用户ID为" + value.getNodeUserId());
        
        if (value.getNodeUserId() == 0)
        {
            if (nodeValues.length > 0)
            {
                retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", "\u8282\u70b9\u7528\u6237\u5df2\u5b58\u5728");
            }
            else
            {
                value.setNodeId(Long.parseLong(nodeId));
                // 保存节点用户信息之前要先进行校验
                boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
                if (b)
                {
                    huSv.save(value);
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
                }
                else
                {
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                }
            }
        }
        else
        {
            value.setModifyDate(TimeUtil.getSysTime());
            // 保存节点用户信息之前要先进行校验
            boolean b = XssUtil.checkBeforeSave(value, value.getClass(), null);
            if (b)
            {
                huSv.save(value);
                retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
            }
            else
            {
                retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
            }
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("SAVE_FLAG"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(jsonObj);
    }
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public abstract void setNodeUserDetail(JSONObject nodeUserDetail);
    
    public abstract void setNodeUserTabRowIndex(int nodeUserTabRowIndex);
    
    public abstract void setNodeUserTabCount(long nodeUserTabCount);
    
    public abstract void setNodeUserTabItem(JSONObject nodeUserTabItem);
    
    public abstract void setNodeUserTabItems(JSONArray nodeUserTabItems);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("nodeUserTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonNodeUser");
        
        bindBoName("nodeUserDetail", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonNodeUser");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus nodeUserDetail = createData("nodeUserDetail");
        if (nodeUserDetail != null && !nodeUserDetail.getDataObject().isEmpty())
        {
            setNodeUserDetail(nodeUserDetail.getDataObject());
        }
        IDataBus nodeUserTabItem = createData("nodeUserTabItem");
        if (nodeUserTabItem != null && !nodeUserTabItem.getDataObject().isEmpty())
        {
            setNodeUserTabItem(nodeUserTabItem.getDataObject());
            
        }
        initExtend(cycle);
    }
    
}
