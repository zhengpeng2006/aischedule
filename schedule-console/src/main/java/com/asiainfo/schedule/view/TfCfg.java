package com.asiainfo.schedule.view;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.ailk.common.data.IDataContext;
import com.ailk.common.data.impl.DataBus;
import com.ailk.common.data.impl.DataContext;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.resultConstant.ResultConstants;
import com.asiainfo.schedule.core.client.tf.config.CfgTf;
import com.asiainfo.schedule.core.po.CfgTfBean;
import com.asiainfo.schedule.core.po.CfgTfDtlBean;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class TfCfg extends AppPage
{
    
    private static final Logger LOGGER = Logger.getLogger(TfCfg.class);
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 查询tf配置和详细配置
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryData(IRequestCycle cycle)
        throws Exception
    {
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String taskCode = getContext().getParameter("taskCode").trim();
        // 根据taskCode查询tf配置和详细配置，转换后显示到页面两处
        JSONObject tfCfg = qryTfCfg(taskCode);
        JSONArray TfDtlCfg = qryTfDtlCfg(taskCode);
        
        // this.setTfInfo(tfCfg);
        // this.setTfDetailItems(TfDtlCfg);
        JSONObject obj = new JSONObject();
        obj.put("tfInfo", tfCfg);
        obj.put("tfDetailItems", PagingUtil.convertArray2Page(TfDtlCfg, offset, linage));
        obj.put("tfDetailTotal", TfDtlCfg.size());
        this.setAjax(obj);
    }
    
    /**
     * 保存TF配置
     * 
     * @param cycle
     * @throws Exception
     */
    public void saveTfCfg(IRequestCycle cycle)
        throws Exception
    {
        IDataBus tfInfoBus = createData("tfInfo");
        JSONObject data = tfInfoBus.getDataObject();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        CfgTfBean bean = new CfgTfBean();
        bean.setCfgTfCode(data.getString("taskCode"));
        bean.setErrorSql(data.getString("errSql"));
        bean.setFinishSql(data.getString("finSql"));
        bean.setPkColumns(data.getString("pkCol"));
        bean.setProcessingSql(data.getString("proSql"));
        bean.setQuerySql(data.getString("querySql"));
        bean.setCountSql(data.getString("conutSql"));
        bean.setRemarks(data.getString("remark"));
        bean.setSrcDbAcctCode(data.getString("srcDbCode"));
        bean.setSrcTableName(data.getString("srcTableName"));
        JSONObject obj = new JSONObject();
        try
        {
            // 保存tf配置信息之前要先进行校验
            boolean b =
                XssUtil.checkBeforeSave(bean, bean.getClass(), "ErrorSql,FinishSql,ProcessingSql,QuerySql,CountSql");
            if (b)
            {
                sv.createOrUpdCfgTf(bean);
                obj.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
            }
            else
            {
                obj.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                obj.put(ResultConstants.ResultKey.RESULT_MSG, ResultConstants.ResultCodeValue.CHECK_FAILED);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("save tf config failed,reason is " + e + ";taskCode is " + data.getString("taskCode"));
            obj.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
        }
        finally
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_TASK,
                data.getString("taskCode"),
                "配置TF基础信息");
        }
        this.setAjax(obj);
    }
    
    /**
     * 删除tf详细配置
     * 
     * @param cycle
     * @throws Exception
     */
    public void deleteDtlCfg(IRequestCycle cycle)
        throws Exception
    {
        String cfgTfDtlId = getContext().getParameter("cfgTfDtlId").trim();
        String taskCode = getContext().getParameter("taskCode").trim();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject result = new JSONObject();
        try
        {
            // 调用删除详细配置接口
            sv.deleteCfgTfDtl(taskCode, cfgTfDtlId);
            // 重新根据taskcode查询下所有配置，以刷新页面
            JSONArray TfDtlCfg = qryTfDtlCfg(taskCode);
            this.setTfDetailItems(TfDtlCfg);
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
        }
        catch (Exception e)
        {
            LOGGER.error("deleteDtlCfg failed,reason is " + e);
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
        }
        finally
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_TASK,
                taskCode,
                "删除TF基础配置");
        }
        this.setAjax(result);
    }
    
    /**
     * 保存tf详细配置
     * 
     * @param cycle
     * @throws Exception
     */
    public void saveTfDtl(IRequestCycle cycle)
        throws Exception
    {
        String taskCode = getContext().getParameter("taskCode").trim();
        IDataBus tfInfoBus = createData("colInfo");
        JSONObject data = tfInfoBus.getDataObject();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        
        CfgTfDtlBean bean = new CfgTfDtlBean();
        bean.setCfgTfCode(taskCode);
        bean.setDbAcctCode(data.getString("destDbName"));
        bean.setFinishSql(data.getString("finSql"));
        bean.setRemarks(data.getString("remark"));
        bean.setTableName(data.getString("destTableName"));
        bean.setTfType(data.getString("tfType"));
        CfgTfDtlBean[] beans = new CfgTfDtlBean[] {bean};
        
        OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
            CommonConstants.OPERATE_TYPE_MODIFY,
            CommonConstants.OPERTATE_OBJECT_TASK,
            taskCode,
            "配置TF详细信息");
        JSONObject obj = new JSONObject();
        // 保存tf详细配置信息之前要先进行校验
        try
        {
            boolean b = XssUtil.checkBeforeSave(bean, bean.getClass(), "FinishSql");
            if (b)
            {
                sv.createCfgTfDtl(beans);
            }
            else
            {
                obj.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                obj.put(ResultConstants.ResultKey.RESULT_MSG, ResultConstants.ResultCodeValue.CHECK_FAILED);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("save tfdetils failed,reason is " + e);
        }
        // 重新根据taskcode查询下所有配置，以刷新页面
        JSONArray TfDtlCfg = qryTfDtlCfg(taskCode);
        this.setTfDetailItems(TfDtlCfg);
        this.setAjax(obj);
    }
    
    /**
     * 获取转移类型下拉菜单
     * 
     * @return
     */
    public IDataBus getTfTypeList()
    {
        IDataContext context = new DataContext();
        JSONArray jsonArray = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("TEXT", "HIS");
        obj.put("VALUE", "HIS");
        jsonArray.add(obj);
        obj.put("TEXT", "DEST");
        obj.put("VALUE", "DEST");
        jsonArray.add(obj);
        return new DataBus(context, jsonArray);
    }
    
    /**
     * 根据taskCode查询tf配置
     * 
     * @param taskCode
     * @return
     */
    private JSONObject qryTfCfg(String taskCode)
    {
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject obj = new JSONObject();
        try
        {
            CfgTf tf = sv.getCfgTf(taskCode);
            obj.put("taskCode", taskCode);
            obj.put("srcDbCode", tf.getSrcDbAcctCode());
            obj.put("srcTableName", tf.getSrcTableName());
            obj.put("pkCol", tf.getPkColumns());
            obj.put("remark", tf.getRemarks());
            obj.put("querySql", tf.getQuerySql());
            obj.put("proSql", tf.getProcessingSql());
            obj.put("finSql", tf.getFinishSql());
            obj.put("errSql", tf.getErrorSql());
            obj.put("conutSql", tf.getCountSql());
        }
        catch (Exception e)
        {
            LOGGER.error("qryTfCfg failed,reason is " + e);
        }
        return obj;
    }
    
    /**
     * 根据taskCode查询tf详细配置
     * 
     * @param taskCode
     * @return
     */
    private JSONArray qryTfDtlCfg(String taskCode)
    {
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONArray array = new JSONArray();
        try
        {
            CfgTfDtlBean[] beans = sv.getCfgTfDtlBeans(taskCode);
            if (beans != null)
            {
                for (CfgTfDtlBean bean : beans)
                {
                    JSONObject obj = new JSONObject();
                    obj.put("cfgTfDtlId", bean.getCfgTfDtlId());
                    obj.put("cfgTfCode", bean.getCfgTfCode());
                    obj.put("destTableName", bean.getTableName());
                    obj.put("destDbName", bean.getDbAcctCode());
                    obj.put("tfType", bean.getTfType());
                    obj.put("finSql", bean.getFinishSql());
                    obj.put("remark", bean.getRemarks());
                    array.add(obj);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("qryTfDtlCfg failed,reason is " + e);
        }
        return array;
    }
    
    public abstract void setTfDetailItems(JSONArray tfDetailItems);
    
    public abstract void setTfInfo(JSONObject tfInfo);
    
    public abstract void setTfDetailRowIndex(int tfDetailRowIndex);
    
    public abstract void setTfDetailCount(long tfDetailCount);
    
    public abstract void setColInfo(JSONObject colInfo);
    
    public abstract void setTfDetailItem(JSONObject tfDetailItem);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus tfInfo = createData("tfInfo");
        if (tfInfo != null && !tfInfo.getDataObject().isEmpty())
        {
            setTfInfo(tfInfo.getDataObject());
        }
        IDataBus colInfo = createData("colInfo");
        if (colInfo != null && !colInfo.getDataObject().isEmpty())
        {
            setColInfo(colInfo.getDataObject());
        }
        IDataBus tfDetailItem = createData("tfDetailItem");
        if (tfDetailItem != null && !tfDetailItem.getDataObject().isEmpty())
        {
            setTfDetailItem(tfDetailItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
