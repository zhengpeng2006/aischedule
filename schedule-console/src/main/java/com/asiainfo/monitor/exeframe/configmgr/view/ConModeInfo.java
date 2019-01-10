package com.asiainfo.monitor.exeframe.configmgr.view;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostConModeBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonConModeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostConModeValue;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonConModeSV;
import com.asiainfo.monitor.exeframe.configmgr.service.interfaces.IAIMonHostConModeSV;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivUtils;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class ConModeInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public String doTranslate(String type, String val)
        throws RemoteException, Exception
    {
        String text = null;
        if ("USER_STATE".equals(type))
        {
            text = UserPrivUtils.doTranslate(type, val);
        }
        
        return text;
    }
    
    public void qryConModeInfo(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String hostId = getContext().getParameter("hostId");
        
        IAIMonConModeSV cmSv = (IAIMonConModeSV)ServiceFactory.getService(IAIMonConModeSV.class);
        IAIMonHostConModeSV hcSv = (IAIMonHostConModeSV)ServiceFactory.getService(IAIMonHostConModeSV.class);
        
        List conIdList = hcSv.getConIdList(hostId);
        IBOAIMonConModeValue[] result = null;
        if (conIdList.size() > 0)
        {
            result = cmSv.qryByConIdList(conIdList);
        }
        IDataBus bus = null;
        if (result == null)
            bus = DataBusHelper.getEmptyArrayDataBus();
        else
            bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonConModeValue.class);
        JSONArray dataArray = bus.getDataArray();
        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext())
        {
            JSONObject obj = (JSONObject)iterator.next();
            if (obj.get("createDate").toString().length() > 8)
            {
                obj.put("createDate", obj.get("createDate").toString().substring(0, 10));
            }
        }
        /*
         * this.setConTabItems(dataArray);
         */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("conTabItems", PagingUtil.convertArray2Page(dataArray, offset, linage));
        jsonObject.put("total", dataArray.size());
        this.setAjax(jsonObject);
    }
    
    public void qryConModeInfoDetail(IRequestCycle cycle)
        throws Exception
    {
        String conId = getContext().getParameter("conId");
        IAIMonConModeSV cmSv = (IAIMonConModeSV)ServiceFactory.getService(IAIMonConModeSV.class);
        IBOAIMonConModeValue value = cmSv.qryConModeInfoByConId(conId);
        
        IDataBus dataBus = null;
        if (value == null)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(value, IBOAIMonConModeValue.class);
        }
        
        this.setConDetail(dataBus.getDataObject());
    }
    
    public void delConMode(IRequestCycle cycle)
        throws Exception
    {
        String hostId = getContext().getParameter("hostId");
        String conId = getContext().getParameter("conId");
        IAIMonHostConModeSV cmSv = (IAIMonHostConModeSV)ServiceFactory.getService(IAIMonHostConModeSV.class);
        IBOAIMonHostConModeValue[] value = cmSv.qryHostConModeByCondition(hostId, conId);
        
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_MONITOR,
            CommonConstants.OPERATE_TYPE_MODIFY,
            CommonConstants.OPERTATE_OBJECT_HOST,
            hostId + ":" + conId,
            "删除主机连接方式，主机ID为" + hostId + ",连接方式ID为" + conId);
        
        cmSv.delete(value);
    }
    
    public IDataBus getSelectList()
        throws Exception
    {
        IAIMonConModeSV cmSv = (IAIMonConModeSV)ServiceFactory.getService(IAIMonConModeSV.class);
        return cmSv.getSelectList();
    }
    
    public void saveOrUpdate(IRequestCycle request)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        String hostId = getContext().getParameter("hostId");
        IDataBus dataBus = this.createData("conDetail");
        IBOAIMonConModeValue value = (IBOAIMonConModeValue)DataBusHelper.getBOBean(dataBus);
        
        BOAIMonHostConModeBean hostConBean = new BOAIMonHostConModeBean();
        long conId = value.getConId();
        hostConBean.setHostId(Long.parseLong(hostId));
        hostConBean.setConId(conId);
        
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_MONITOR,
            CommonConstants.OPERATE_TYPE_MODIFY,
            CommonConstants.OPERTATE_OBJECT_HOST,
            hostId + ":" + conId,
            "配置主机连接方式，主机ID为" + hostId + ",连接方式ID为" + conId);
        
        IAIMonHostConModeSV hcSv = (IAIMonHostConModeSV)ServiceFactory.getService(IAIMonHostConModeSV.class);
        
        List conIdList = hcSv.getConIdList(hostId);
        JSONObject jsonObj = new JSONObject();
        if (!conIdList.contains(Long.toString(conId)))
        {
            IAIMonHostConModeSV ghSv = (IAIMonHostConModeSV)ServiceFactory.getService(IAIMonHostConModeSV.class);
            ghSv.save(hostConBean);
            jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
        }
        else
        {
            jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
            jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, ResultConstants.ResultCodeValue.CHECK_FAILED);
        }
        
        this.setAjax(jsonObj);
        
    }
    
    public abstract void setConTabCount(long conTabCount);
    
    public abstract void setConTabItem(JSONObject conTabItem);
    
    public abstract void setConDetail(JSONObject conDetail);
    
    public abstract void setConTabItems(JSONArray conTabItems);
    
    public abstract void setConTabRowIndex(int conTabRowIndex);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("conTab", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonConMode");
        
        bindBoName("conDetail", "com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonConMode");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus conTabItem = createData("conTabItem");
        if (conTabItem != null && !conTabItem.getDataObject().isEmpty())
        {
            setConTabItem(conTabItem.getDataObject());
        }
        IDataBus conDetail = createData("conDetail");
        if (conDetail != null && !conDetail.getDataObject().isEmpty())
        {
            setConDetail(conDetail.getDataObject());
        }
        initExtend(cycle);
    }
}
