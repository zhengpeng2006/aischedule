package com.asiainfo.deploy.view;

import java.util.HashMap;
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
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.common.utils.ProcessJsonUtil;
import com.asiainfo.deploy.api.external.DeployStrategyUtils;
import com.asiainfo.deploy.api.interfaces.IDeployStrategySVProvider;
import com.asiainfo.deploy.common.bo.BODeployAppTemplateBean;
import com.asiainfo.deploy.common.bo.BODeployNodeStrategyRelationBean;
import com.asiainfo.deploy.common.bo.BODeployStrategyBean;
import com.asiainfo.deploy.common.bo.BODeployStrategyEngine;
import com.asiainfo.deploy.common.constants.Category.CompressType;
import com.asiainfo.deploy.common.ivalues.IBODeployStrategyValue;
import com.asiainfo.deploy.dao.interfaces.IAppTemplateDAO;
import com.asiainfo.deploy.installpackage.InstallRuleParser;
import com.asiainfo.deploy.installpackage.InstallRuleParser.InstallRule;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoGroupValue;
import com.asiainfo.monitor.busi.exe.task.ivalues.IBOAIMonPInfoValue;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.monitor.exeframe.configmgr.util.ConfigPageUtil;
import com.asiainfo.resultConstant.ResultConstants;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class StrategyCfg extends AppPage
{
    private static final Logger LOGGER = Logger.getLogger(StrategyCfg.class);
    
    /** 静态数据 */
    private static Map<String, Map<String, String>> staticMap;
    
    /** 文件传输在静态数据中的key */
    private static final String KEY_FTPPROTOCOL = "ftpProtocol";
    
    /** 是否需要在静态数据中的key */
    private static final String KEY_Y_N = "YorN";
    
    /** 解压方式在静态数据中的key */
    private static final String KEY_UNZIPMETHOD = "unzipMethod";
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 查询策略
     * 
     * @param cycle
     */
    public void qryStrategies(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        IDataBus queryFormBus = createData("queryForm");
        JSONObject conditions = (JSONObject)queryFormBus.getData();
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put(BODeployStrategyBean.S_DeployStrategyName, "%" + (String)conditions.get("name") + "%");
        IDeployStrategySVProvider sv =
            (IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class);
        try
        {
            IBODeployStrategyValue[] value = sv.getStrategyByCondition(condition);
            IDataBus bus = null;
            if (value == null)
                bus = DataBusHelper.getEmptyArrayDataBus();
            else
                bus = DataBusHelper.getDataBusByBeans(value, IBOAIMonPInfoGroupValue.class);
            /* this.setStategiesItems(bus.getDataArray()); */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("stategiesItems", PagingUtil.convertArray2Page(bus.getDataArray(), offset, linage));
            jsonObject.put("total", bus.getDataArray() == null ? 0 : bus.getDataArray().size());
            this.setAjax(jsonObject);
        }
        catch (Exception e)
        {
            LOGGER.error("qryStrategies failed,reason is " + e);
        }
    }
    
    /**
     * 删除策略
     * 
     * @param cycle
     * @throws Exception
     */
    public void deleteStrategy(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        String deployStrategyIdStr = getContext().getParameter("deployStrategyId").trim();
        IDeployStrategySVProvider sv =
            (IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class);
        if (StringUtils.isNotBlank(deployStrategyIdStr))
        {
            long deployStrategyId = Long.parseLong(deployStrategyIdStr);
            JSONObject result = new JSONObject();
            IBODeployStrategyValue strategy = sv.getStrategyById(deployStrategyId);
            try
            {
                // 查询当前策略是否管理节点，若有则不允许删除
                BODeployNodeStrategyRelationBean[] relations =
                    DeployStrategyUtils.getNodesByStrategyId(deployStrategyId);
                if (relations != null && relations.length > 0)
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg",
                        "\u5f53\u524d\u7b56\u7565\u5b58\u5728\u7ba1\u7406\u8282\u70b9\uff0c\u4e0d\u5141\u8bb8\u5220\u9664");
                }
                else
                {
                    sv.deleteStrategy(deployStrategyId);
                    retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                }
            }
            catch (Exception e)
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.ERROR);
                LOGGER.error("delete Strategy failed,reason is " + e);
            }
            finally
            {
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                    CommonConstants.OPERATE_TYPE_DELETE,
                    CommonConstants.OPERTATE_OBJECT_STRATEGY,
                    strategy.getDeployStrategyName() + ":" + deployStrategyId,
                    null);
            }
            result.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("flag"));
            result.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
            
            this.setAjax(result);
        }
    }
    
    /**
     * 根据主键查询策略
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryStrategyById(IRequestCycle cycle)
        throws Exception
    {
        String deployStrategyIdStr = getContext().getParameter("deployStrategyId").trim();
        if (StringUtils.isNotBlank(deployStrategyIdStr))
        {
            long deployStrategyId = Long.parseLong(deployStrategyIdStr);
            IDeployStrategySVProvider sv =
                (IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class);
            IBODeployStrategyValue result = sv.getStrategyById(deployStrategyId);
            IDataBus bus = null;
            if (result == null)
                bus = DataBusHelper.getEmptyArrayDataBus();
            else
                bus = DataBusHelper.getDataBusByBeans(result, IBOAIMonPInfoValue.class);
            /*
             * this.setStrategyInfo(bus.getDataObject());
             */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("strategyInfo", bus.getDataObject());
            this.setAjax(jsonObject);
        }
        else
        {
            LOGGER.error("qry Strategy By Id failed,reason is deployStrategyId is null");
        }
    }
    
    /**
     * 查询安装规则
     * 
     * @param cycle
     * @throws Exception
     */
    public void ruleCfg(IRequestCycle cycle)
        throws Exception
    {
        String rule = getContext().getParameter("rule").trim();
        JSONArray array = new JSONArray();
        if (StringUtils.isNotBlank(rule))
        {
            List<InstallRule> rules = InstallRuleParser.parser(rule);
            if (rules == null)
            {
                LOGGER.error("ruleCfg failed, rule parse failed");
                return;
            }
            
            for (InstallRule installRule : rules)
            {
                JSONObject obj = new JSONObject();
                obj.put("packageName", installRule.src);
                obj.put("installPath", installRule.dst);
                if (!installRule.needUnzip)
                {
                    obj.put("unzipMethod", CompressType.PLAIN.value());
                }
                else
                {
                    obj.put("unzipMethod", installRule.compressType == null ? "" : installRule.compressType.value());
                }
                array.add(obj);
            }
            
        }
        this.setRulesItems(array);
    }
    
    /**
     * 保存策略
     * 
     * @param cycle
     * @throws Exception
     */
    public void saveStrategy(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        
        // 处理"operatorId":null的情形
        getData().put("strategyInfo", ProcessJsonUtil.removeNullValue(getData().getString("strategyInfo")));
        IDataBus strategyInfoBus = createData("strategyInfo");
        IBODeployStrategyValue value = (IBODeployStrategyValue)DataBusHelper.getBOBean(strategyInfoBus);
        JSONObject result = new JSONObject();
        BODeployStrategyBean bean = null;
        try
        {
            IDeployStrategySVProvider sv =
                (IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class);
            bean = BODeployStrategyEngine.transfer(value);
            // 保存安装规则前
            boolean b = XssUtil.checkBeforeSave(bean, bean.getClass(), null);
            if (b)
            {
                sv.saveStrategy(bean);
            }
            else
            {
                retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
            }
        }
        catch (Exception e)
        {
            retMap.put("flag", ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("save Strategy failed,reason is " + e.getMessage() + ";data is " + strategyInfoBus);
        }
        finally
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_ADD,
                CommonConstants.OPERTATE_OBJECT_STRATEGY,
                value.getDeployStrategyName(),
                null);
        }
        result.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("flag"));
        result.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(result);
    }
    
    /**
     * 保存安装规则
     * 
     * @param cycle
     * @throws Exception
     */
    public void saveRules(IRequestCycle cycle)
        throws Exception
    {
        Map<String, String> retMap = new HashMap<String, String>();
        
        String deployStrategyId = getContext().getParameter("deployStrategyId").trim();
        String rule = getContext().getParameter("data").trim();
        JSONObject result = new JSONObject();
        if (StringUtils.isNotBlank(deployStrategyId))
        {
            IDeployStrategySVProvider sv =
                (IDeployStrategySVProvider)ServiceFactory.getService(IDeployStrategySVProvider.class);
            IBODeployStrategyValue value = null;
            BODeployStrategyBean bean = null;
            try
            {
                value = sv.getStrategyById(Long.parseLong(deployStrategyId));
                // 去最后一个";"
                if (StringUtils.isNotBlank(rule) && rule.lastIndexOf(";") == (rule.length() - 1))
                {
                    rule = rule.substring(0, (rule.length() - 1));
                }
                value.setInstallRule(rule);
                bean = BODeployStrategyEngine.transfer(value);
                // 保存安装规则前
                boolean b = XssUtil.checkString(rule);
                if (b)
                {
                    sv.saveStrategy(bean);
                    retMap.put("flag", ResultConstants.ResultCodeValue.SUCCESS);
                }
                else
                {
                    retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
                    retMap.put("msg", ResultConstants.ResultCodeValue.CHECK_FAILED);
                }
            }
            catch (Exception e)
            {
                LOGGER.error("saveRules failed", e);
                retMap.put("flag", ResultConstants.ResultCodeValue.ERROR);
            }
            finally
            {
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                    CommonConstants.OPERATE_TYPE_MODIFY,
                    CommonConstants.OPERTATE_OBJECT_STRATEGY,
                    value == null ? "" : value.getDeployStrategyName() + ":" + value.getDeployStrategyId(),
                    "配置安装规则");
            }
        }
        else
        {
            retMap.put("flag", ResultConstants.ResultCodeValue.FAILED);
            retMap.put("msg", "saveRules failed: deployStrategyId is null");
            LOGGER.error("saveRules failed: deployStrategyId is null");
        }
        
        result.put(ResultConstants.ResultKey.RESULT_CODE, retMap.get("flag"));
        result.put(ResultConstants.ResultKey.RESULT_MSG, retMap.get("msg"));
        this.setAjax(result);
    }
    
    /**
     * 转换模板ID为名称
     * 
     * @param templateId
     * @return
     * @throws Exception
     */
    public String templateTrans(String templateId)
        throws Exception
    {
        String result = "";
        IAppTemplateDAO dao = (IAppTemplateDAO)ServiceFactory.getService(IAppTemplateDAO.class);
        if (StringUtils.isNotBlank(templateId) && !"null".equalsIgnoreCase(templateId))
        {
            try
            {
                BODeployAppTemplateBean bean = dao.getAppTemplateById(Long.parseLong(templateId));
                result = bean.getTemplateName();
            }
            catch (Exception e)
            {
                LOGGER.error("get AppTemplate By Id failed,reason is " + e + ";templateId is " + templateId);
                throw e;
            }
        }
        return result;
    }
    
    /**
     * 转换文件传输协议
     * 
     * @param ftpProtocol
     * @return
     * @throws Exception
     */
    public String ftpProtocolTrans(String ftpProtocol)
        throws Exception
    {
        return getStaticMap().get(KEY_FTPPROTOCOL).get(ftpProtocol);
    }
    
    /**
     * 获取传输协议下拉
     * 
     * @return
     * @throws Exception
     */
    public IDataBus getftpProtocolList()
        throws Exception
    {
        Map<String, String> map = getStaticMap().get(KEY_FTPPROTOCOL);
        IDataBus bus = ConfigPageUtil.getSelectList(map);
        return bus;
    }
    
    /**
     * 获取下拉菜单
     * 
     * @return
     * @throws Exception
     */
    public IDataBus getTemplateList()
        throws Exception
    {
        Map<String, String> map = new HashMap<String, String>();
        IAppTemplateDAO dao = (IAppTemplateDAO)ServiceFactory.getService(IAppTemplateDAO.class);
        try
        {
            BODeployAppTemplateBean[] beans = dao.getAllAppTemplate();
            if (beans != null)
            {
                for (BODeployAppTemplateBean bean : beans)
                {
                    map.put(bean.getTemplateId() + "", bean.getTemplateName());
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("get All AppTemplate failed,reason is " + e);
            throw e;
        }
        IDataBus bus = ConfigPageUtil.getSelectList(map);
        return bus;
    }
    
    /**
     * 获取传输协议下拉
     * 
     * @return
     * @throws Exception
     */
    public IDataBus getNeedUnzipList()
        throws Exception
    {
        Map<String, String> map = getStaticMap().get(KEY_Y_N);
        IDataBus bus = ConfigPageUtil.getSelectList(map);
        return bus;
    }
    
    /**
     * 获取传输协议下拉
     * 
     * @return
     * @throws Exception
     */
    public IDataBus getUnzipMethodList()
        throws Exception
    {
        Map<String, String> map = getStaticMap().get(KEY_UNZIPMETHOD);
        IDataBus bus = ConfigPageUtil.getSelectList(map);
        return bus;
    }
    
    /**
     * 获取静态数据map
     * 
     * @return
     */
    private Map<String, Map<String, String>> getStaticMap()
    {
        if (staticMap == null)
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
        staticMap = new HashMap<String, Map<String, String>>();
        Map<String, String> ftpProtocolMap = new HashMap<String, String>();
        ftpProtocolMap.put("0", "SFTP");
        ftpProtocolMap.put("1", "FTP");
        staticMap.put(KEY_FTPPROTOCOL, ftpProtocolMap);
        Map<String, String> unzipMethodMap = new HashMap<String, String>();
        unzipMethodMap.put(CompressType.JAR.value(), "JAR");
        unzipMethodMap.put(CompressType.TAR.value(), "TAR");
        unzipMethodMap.put(CompressType.TAR_GZ.value(), "TAR.GZ");
        unzipMethodMap.put(CompressType.PLAIN.value(), "\u4e0d\u89e3\u538b");
        staticMap.put(KEY_UNZIPMETHOD, unzipMethodMap);
    }
    
    public abstract void setRulesRowIndex(int rulesRowIndex);
    
    public abstract void setStategiesRowIndex(int stategiesRowIndex);
    
    public abstract void setStategiesCount(long stategiesCount);
    
    public abstract void setStategiesItem(JSONObject stategiesItem);
    
    public abstract void setStategiesItems(JSONArray stategiesItems);
    
    public abstract void setRulesItem(JSONObject rulesItem);
    
    public abstract void setRulesCount(long rulesCount);
    
    public abstract void setRulesItems(JSONArray rulesItems);
    
    public abstract void setStrategyInfo(JSONObject strategyInfo);
    
    public abstract void setQueryForm(JSONObject queryForm);
    
    @Override
    protected void initPageAttrs()
    {
        bindBoName("stategies", "com.asiainfo.deploy.common.bo.BODeployStrategy");
        
        bindBoName("strategyInfo", "com.asiainfo.deploy.common.bo.BODeployStrategy");
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus stategiesItem = createData("stategiesItem");
        if (stategiesItem != null && !stategiesItem.getDataObject().isEmpty())
        {
            setStategiesItem(stategiesItem.getDataObject());
        }
        IDataBus rulesItem = createData("rulesItem");
        if (rulesItem != null && !rulesItem.getDataObject().isEmpty())
        {
            setRulesItem(rulesItem.getDataObject());
        }
        IDataBus strategyInfo = createData("strategyInfo");
        if (strategyInfo != null && !strategyInfo.getDataObject().isEmpty())
        {
            setStrategyInfo(strategyInfo.getDataObject());
        }
        IDataBus queryForm = createData("queryForm");
        if (queryForm != null && !queryForm.getDataObject().isEmpty())
        {
            setQueryForm(queryForm.getDataObject());
        }
        initExtend(cycle);
    }
    
}
