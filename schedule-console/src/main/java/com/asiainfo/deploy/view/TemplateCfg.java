package com.asiainfo.deploy.view;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.util.DataBusHelper;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.common.utils.ProcessJsonUtil;
import com.asiainfo.deploy.api.interfaces.IAppParamSVProvider;
import com.asiainfo.deploy.api.interfaces.IDeployStrategySVProvider;
import com.asiainfo.deploy.common.bo.BODeployAppTemplateBean;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.deploy.common.ivalues.IBODeployAppTemplateValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.monitor.exeframe.configmgr.util.ConfigPageUtil;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class TemplateCfg extends AppPage
{
    private static final Logger LOGGER = Logger.getLogger(TemplateCfg.class);
    
    /** 脚本类型map在静态数据map中的key */
    private static final String MAP_SCRITP = "scriptStyle";
    
    /** 启动模式map在静态数据map中的key */
    private static final String MAP_START_MODE = "startMode";
    
    /** 操作结果标识 */
    private static final String RESULT_FLAG = "flag";
    
    /** 操作结果标识:成功 */
    private static final String RESULT_FLAG_SUCCESS = "T";
    
    /** 操作结果标识：失败 */
    private static final String RESULT_FLAG_FAILED = "F";
    
    /** 操作结果标识：逻辑异常 */
    private static final String RESULT_FLAG_ERROR = "N";
    
    /** 操作错误详情 */
    private static final String RESULT_MSG = "msg";
    
    /** 页面用静态数据map */
    private static Map<String, Map<String, String>> staticMap = new HashMap<String, Map<String, String>>();
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 查询所有模板
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryTemplate(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        IAppParamSVProvider sv = (IAppParamSVProvider)ServiceFactory.getService(IAppParamSVProvider.class);
        try
        {
            BODeployAppTemplateBean[] value = sv.getAllAppTemplate();
            IDataBus bus = null;
            if (value == null)
                bus = DataBusHelper.getEmptyArrayDataBus();
            else
                bus = DataBusHelper.getDataBusByBeans(value, BODeployAppTemplateBean.class);
            
            /* this.setTemplatesItems(bus.getDataArray()); */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("templatesItems", PagingUtil.convertArray2Page(bus.getDataArray(), offset, linage));
            jsonObject.put("total", bus.getDataArray() == null ? 0 : bus.getDataArray().size());
            this.setAjax(jsonObject);
        }
        catch (Exception e)
        {
            LOGGER.error("qryTemplate failed,reason is " + e.getMessage());
        }
    }
    
    /**
     * 删除模板
     * 
     * @param cycle
     * @throws Exception
     */
    public void deleteTemplate(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        
        String templateIdIdStr = getContext().getParameter("templateId").trim();
        JSONObject result = new JSONObject();
        if (StringUtils.isNotBlank(templateIdIdStr))
        {
            try
            {
                IDeployStrategySVProvider strategySv =
                    (IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class);
                Map<String, Object> condition = new HashMap<String, Object>();
                condition.put(BODeployStrategyBean.S_TemplateId, templateIdIdStr);
                BODeployStrategyBean[] strategies = strategySv.getStrategyByCondition(condition);
                // 如果有策略引用该模板则逻辑报错
                if (null != strategies && strategies.length > 0)
                {
                    retMap.put(RESULT_FLAG, ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", "\u6a21\u677f\u5df2\u88ab\u4f7f\u7528");
                }
                else
                {
                    long templateId = Long.parseLong(templateIdIdStr);
                    IAppParamSVProvider sv = (IAppParamSVProvider)ServiceFactory.getService(IAppParamSVProvider.class);
                    BODeployAppTemplateBean bean = sv.getAppTemplateById(templateId);
                    bean.delete();
                    sv.saveAppTemplate(bean);
                    retMap.put(RESULT_FLAG, ResultConstants.ResultCodeValue.SUCCESS);
                }
            }
            catch (Exception e)
            {
                LOGGER.error("delete Template failed,reason is " + e + ";id is " + templateIdIdStr);
                retMap.put(RESULT_FLAG, ResultConstants.ResultCodeValue.ERROR);
            }
            result.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get(RESULT_FLAG));
            result.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
            
            this.setAjax(result);
        }
        else
        {
            LOGGER.error("delete Template failed,templateId is null");
        }
    }
    
    /**
     * 根据ID查模板详情
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryTemplateById(IRequestCycle cycle)
        throws Exception
    {
        String templateIdStr = getContext().getParameter("templateId").trim();
        if (StringUtils.isNotBlank(templateIdStr))
        {
            long deployStrategyId = Long.parseLong(templateIdStr);
            IAppParamSVProvider sv = (IAppParamSVProvider)ServiceFactory.getService(IAppParamSVProvider.class);
            BODeployAppTemplateBean result = sv.getAppTemplateById(deployStrategyId);
            IDataBus bus = null;
            if (result == null)
                bus = DataBusHelper.getEmptyArrayDataBus();
            else
                bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonPInfoValue.class);
            this.setTamplateInput(bus.getDataObject());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tamplateInput", bus.getDataObject());
            this.setAjax(jsonObject);
        }
    }
    
    /**
     * 保存模板
     * 
     * @param cycle
     * @throws Exception
     */
    public void saveTemplate(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        
        // 处理"operatorId":null的情形
        getData().put("tamplateInput", ProcessJsonUtil.removeNullValue(getData().getString("tamplateInput")));
        IDataBus tamplateInfoBus = createData("tamplateInput");
        IBODeployAppTemplateValue value = (IBODeployAppTemplateValue)DataBusHelper.getBOBean(tamplateInfoBus);
        JSONObject result = new JSONObject();
        try
        {
            IAppParamSVProvider sv = (IAppParamSVProvider)ServiceFactory.getService(IAppParamSVProvider.class);
            // 保存监控模板前进行校验
            boolean b = XssUtil.checkBeforeSave(value, value.getClass(), "Contents");
            if (b)
            {
                sv.saveAppTemplate(value);
                retMap.put(RESULT_FLAG, ResultConstants.ResultCodeValue.SUCCESS);
            }
            else
            {
                retMap.put(RESULT_FLAG, ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
            }
        }
        catch (Exception e)
        {
            retMap.put(RESULT_FLAG, ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("save Templatefailed,reason is " + e + ";data is " + tamplateInfoBus);
        }
        result.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get(RESULT_FLAG));
        result.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        
        this.setAjax(result);
    }
    
    /**
     * 生成脚本类型下拉框
     * 
     * @return
     */
    public IDataBus getScriptStyleList()
    {
        Map<String, String> map = getStaticMap().get(MAP_SCRITP);
        IDataBus bus = ConfigPageUtil.getSelectList(map);
        return bus;
    }
    
    /**
     * 转换脚本类型
     * 
     * @param methodId
     * @return
     */
    public String scriptTrans(String scriptId)
    {
        Map<String, String> map = getStaticMap().get(MAP_SCRITP);
        return map.get(scriptId);
    }
    
    /**
     * 生成启动方式下拉框
     * 
     * @return
     */
    public IDataBus getStartModeList()
    {
        Map<String, String> map = getStaticMap().get(MAP_START_MODE);
        IDataBus bus = ConfigPageUtil.getSelectList(map);
        return bus;
    }
    
    /**
     * 转换启动方式
     * 
     * @param methodId
     * @return
     */
    public String startModeTrans(String modeId)
    {
        Map<String, String> map = getStaticMap().get(MAP_START_MODE);
        return map.get(modeId);
    }
    
    /**
     * 获取静态数据
     * 
     * @return
     */
    private Map<String, Map<String, String>> getStaticMap()
    {
        if (staticMap == null || staticMap.isEmpty())
        {
            initStaticMap();
        }
        return staticMap;
    }
    
    /**
     * 初始化静态数据
     */
    private void initStaticMap()
    {
        Map<String, String> scriptStyleMap = new HashMap<String, String>();
        scriptStyleMap.put("0", "ksh");
        scriptStyleMap.put("1", "csh");
        scriptStyleMap.put("2", "cmd");
        staticMap.put(MAP_SCRITP, scriptStyleMap);
        Map<String, String> startModeMap = new HashMap<String, String>();
        startModeMap.put("0", "\u811a\u672c\u542f\u52a8");
        startModeMap.put("1", "Main\u65b9\u6cd5\u542f\u52a8");
        staticMap.put(MAP_START_MODE, startModeMap);
    }
    
    public abstract void setTemplatesItem(JSONObject templatesItem);
    
    public abstract void setTamplateInput(JSONObject tamplateInput);
    
    public abstract void setTemplatesCount(long templatesCount);
    
    public abstract void setTemplatesRowIndex(int templatesRowIndex);
    
    public abstract void setTemplatesItems(JSONArray templatesItems);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("templates", "com.asiainfo.deploy.common.bo.BODeployAppTemplate");
        
        bindBoName("tamplateInput", "com.asiainfo.deploy.common.bo.BODeployAppTemplate");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus templatesItem = createData("templatesItem");
        if (templatesItem != null && !templatesItem.getDataObject().isEmpty())
        {
            setTemplatesItem(templatesItem.getDataObject());
        }
        IDataBus tamplateInput = createData("tamplateInput");
        if (tamplateInput != null && !tamplateInput.getDataObject().isEmpty())
        {
            setTamplateInput(tamplateInput.getDataObject());
        }
        initExtend(cycle);
    }
    
}
