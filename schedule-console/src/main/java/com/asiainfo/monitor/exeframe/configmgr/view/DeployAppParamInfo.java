package com.asiainfo.monitor.exeframe.configmgr.view;

import java.rmi.RemoteException;
import java.util.HashMap;
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
import com.asiainfo.common.utils.ProcessJsonUtil;
import com.asiainfo.deploy.api.external.AppParamUtils;
import com.asiainfo.deploy.api.external.DeployStrategyUtils;
import com.asiainfo.deploy.common.bo.BODeployAppParamsBean;
import com.asiainfo.deploy.common.ivalues.IBODeployAppParamsValue;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class DeployAppParamInfo extends AppPage
{
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 获取部署参数
     * 
     * @param request
     * @throws Exception
     */
    public void qryParamInfo(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String applicationId = getContext().getParameter("serverId");
        long appId = Long.parseLong(applicationId);
        BODeployAppParamsBean[] appBeans = AppParamUtils.getConfigedAppParams(appId);
        IDataBus bus = null;
        if (appBeans.length == 0)
        {
            IAIMonServerSV serverSv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
            IBOAIMonServerValue serverValue = serverSv.qryServerByServerId(applicationId);
            long deployStrategyId = DeployStrategyUtils.getDeployStrategyIdByNodeId(serverValue.getNodeId());
            BODeployAppParamsBean[] bean = AppParamUtils.getAppParams(deployStrategyId);
            for (int i = 0; i < bean.length; i++)
            {
                bean[i].setApplicationId(appId);
                bean[i].setParamType("T");
            }
            AppParamUtils.saveAppParams(bean);
            BODeployAppParamsBean[] appBean = AppParamUtils.getConfigedAppParams(appId);
            bus = DataBusHelper.getDataBusByBeans(appBean, IBODeployAppParamsValue.class);
            
        }
        else
        {
            bus = DataBusHelper.getDataBusByBeans(appBeans, IBODeployAppParamsValue.class);
        }
        JSONArray dataArray = bus.getDataArray();
        
        /* this.setDeployAppParamTabItems(dataArray); */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deployAppParamTabItems", PagingUtil.convertArray2Page(dataArray, offset, linage));
        jsonObject.put("total", dataArray.size());
        this.setAjax(jsonObject);
    }
    
    /**
     * 获取参数详情
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryParamDetail(IRequestCycle cycle)
        throws Exception
    {
        String applicationParamId = getContext().getParameter("applicationParamId");
        BODeployAppParamsBean value = AppParamUtils.getAppParam(Long.parseLong(applicationParamId));
        IDataBus dataBus = null;
        if (value == null)
        {
            dataBus = DataBusHelper.getEmptyArrayDataBus();
        }
        else
        {
            dataBus = DataBusHelper.getDataBusByBeans(value, IBODeployAppParamsValue.class);
        }
        
        /* this.setDeployAppParamDetail(dataBus.getDataObject()); */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deployAppParamDetail", dataBus.getDataObject());
        this.setAjax(jsonObject);
    }
    
    /**
     * 保存参数
     * 
     * @param request
     * @throws Exception
     */
    public void saveOrUpdate(IRequestCycle request)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        String applicationId = getContext().getParameter("serverId");
        String applicationParamId = getContext().getParameter("applicationParamId");
        
        // 处理类似"operatorId":null的情形
        getData().put("deployAppParamDetail",
            ProcessJsonUtil.removeNullValue(getData().getString("deployAppParamDetail")));
        IDataBus dataBus = this.createData("deployAppParamDetail");
        BODeployAppParamsBean bean = (BODeployAppParamsBean)DataBusHelper.getBOBean(dataBus);
        
        long appId = Long.parseLong(applicationId);
        // 判断key是否存在
        boolean flag = AppParamUtils.isExistKey(appId, bean.getKey());
        // applicationParamId为空时
        if (bean.getApplicationParamId() == 0)
        {
            if (flag)
            {
                retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", "\u006b\u0065\u0079\u5df2\u5b58\u5728");
            }
            else
            {
                bean.setStsToNew();
                bean.setApplicationId(Long.parseLong(applicationId));
                BODeployAppParamsBean[] beans = new BODeployAppParamsBean[1];
                if (bean != null)
                {
                    beans[0] = bean;
                    // 保存信息之前要先进行校验
                    boolean b = XssUtil.checkBeforeSave(bean, bean.getClass(), null);
                    if (b)
                    {
                        AppParamUtils.saveAppParams(beans);
                        retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
                    }
                    else
                    {
                        retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                        retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                    }
                    // 为新增添加操作日志
                    OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                        CommonConstants.OPERATE_TYPE_ADD,
                        CommonConstants.OPERTATE_OBJECT_APP,
                        appId + "",
                        "配置部署参数，应用ID为" + appId + ",参数键为" + bean.getKey() + ",参数值为" + bean.getValue());
                }
            }
        }
        else
        {
            // applicationParamId不为空时
            long appParamId = Long.parseLong(applicationParamId);
            BODeployAppParamsBean appBean = AppParamUtils.getAppParam(appParamId);
            
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_APP,
                appId + ":" + appParamId,
                "配置部署参数，应用ID为" + appId + ",参数键为" + bean.getKey() + ",参数值为" + bean.getValue());
            
            if (bean.getKey().equals(appBean.getKey()))
            {
                BODeployAppParamsBean[] beans = new BODeployAppParamsBean[1];
                if (bean != null)
                {
                    beans[0] = bean;
                    // 保存信息之前要先进行校验
                    boolean b = XssUtil.checkBeforeSave(bean, bean.getClass(), null);
                    if (b)
                    {
                        AppParamUtils.saveAppParams(beans);
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
                if (flag)
                {
                    retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", "\u006b\u0065\u0079\u5df2\u5b58\u5728");
                }
                else
                {
                    BODeployAppParamsBean[] beans = new BODeployAppParamsBean[1];
                    if (bean != null)
                    {
                        beans[0] = bean;
                        // 保存信息之前要先进行校验
                        boolean b = XssUtil.checkBeforeSave(bean, bean.getClass(), null);
                        if (b)
                        {
                            AppParamUtils.saveAppParams(beans);
                            retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.SUCCESS);
                        }
                        else
                        {
                            retMap.put("SAVE_FLAG", ResultConstants.ResultCodeValue.FAILED);
                            retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                        }
                    }
                }
            }
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("SAVE_FLAG"));
        jsonObj.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(jsonObj);
        
    }
    
    /**
     * 删除部署参数
     * 
     * @param request
     * @throws Exception
     */
    public void delParam(IRequestCycle request)
        throws Exception
    {
        String applicationParamId = getContext().getParameter("applicationParamId");
        BODeployAppParamsBean bean = AppParamUtils.getAppParam(Long.parseLong(applicationParamId));
        // 添加删除的操作日志
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
            CommonConstants.OPERATE_TYPE_DELETE,
            CommonConstants.OPERTATE_OBJECT_APP,
            bean.getApplicationId() + ":" + bean.getApplicationParamId(),
            "配置部署参数，应用ID为" + bean.getApplicationId() + ",参数键为" + bean.getKey() + ",参数值为" + bean.getValue());
        AppParamUtils.delete(bean);
        
        JSONObject result = new JSONObject();
        result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
        this.setAjax(result);
    }
    
    /**
     * 参数类型的翻译
     * 
     * @param type
     * @param val
     * @return
     * @throws RemoteException
     * @throws Exception
     */
    public String doTranslate(String type, String val)
        throws RemoteException, Exception
    {
        String text = null;
        
        if ("PARAM_TYPE".equals(type))
        {
            if ("T".equals(val))
            {
                text = "模板参数";
            }
            if ("C".equals(val))
            {
                text = "自定义参数";
            }
        }
        return text;
    }
    
    public abstract void setDeployAppParamTabCount(long deployAppParamTabCount);
    
    public abstract void setDeployAppParamTabItem(JSONObject deployAppParamTabItem);
    
    public abstract void setDeployAppParamDetail(JSONObject deployAppParamDetail);
    
    public abstract void setDeployAppParamTabRowIndex(int deployAppParamTabRowIndex);
    
    public abstract void setDeployAppParamTabItems(JSONArray deployAppParamTabItems);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("deployAppParamTab", "com.asiainfo.deploy.common.bo.BODeployAppParams");
        
        bindBoName("deployAppParamDetail", "com.asiainfo.deploy.common.bo.BODeployAppParams");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus deployAppParamTabItem = createData("deployAppParamTabItem");
        if (deployAppParamTabItem != null && !deployAppParamTabItem.getDataObject().isEmpty())
        {
            setDeployAppParamTabItem(deployAppParamTabItem.getDataObject());
        }
        IDataBus deployAppParamDetail = createData("deployAppParamDetail");
        if (deployAppParamDetail != null && !deployAppParamDetail.getDataObject().isEmpty())
        {
            setDeployAppParamDetail(deployAppParamDetail.getDataObject());
        }
        initExtend(cycle);
    }
    
}
