package com.asiainfo.schedule.view;

import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.operation.log.OperationLogUtils;
import com.asiainfo.common.utils.CommonConstants;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.common.utils.XssUtil;
import com.asiainfo.resultConstant.ResultConstants;
import com.asiainfo.schedule.core.client.tf.config.CfgTfMapping;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class ColRel extends AppPage
{
    
    private static final Logger LOGGER = Logger.getLogger(ColRel.class);
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 查询映射关系
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryCols(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        String cfgTfDtlId = getContext().getParameter("cfgTfDtlId").trim();
        String taskCode = getContext().getParameter("taskCode").trim();
        JSONArray array = new JSONArray();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        
        CfgTfMapping[] mappings = sv.getCfgTfMappings(taskCode, cfgTfDtlId);
        if (null != mappings)
        {
            for (CfgTfMapping mapping : mappings)
            {
                JSONObject obj = new JSONObject();
                obj.put("mappingId", mapping.getMappingId());
                obj.put("cfgTfDtlId", mapping.getCfgTfDtlId());
                obj.put("srcColumnName", mapping.getSrcColumnName());
                obj.put("tfColumnName", mapping.getTfColumnName());
                obj.put("remark", mapping.getRemarks());
                array.add(obj);
            }
        }
        // this.setColRelsItems(array);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("colRelsItems", PagingUtil.convertArray2Page(array, offset, linage));
        jsonObject.put("total", array.size());
        this.setAjax(jsonObject);
    }
    
    /**
     * 删除映射关系
     * 
     * @param cycle
     * @throws Exception
     */
    public void deleteCols(IRequestCycle cycle)
        throws Exception
    {
        String idsStr = getContext().getParameter("ids").trim();
        String cfgTfDtlId = getContext().getParameter("cfgTfDtlId").trim();
        String taskCode = getContext().getParameter("taskCode").trim();
        String[] ids = idsStr.split(",");
        List<String> idList = Arrays.asList(ids);
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject result = new JSONObject();
        try
        {
            sv.deleteCfgTfMapping(taskCode, cfgTfDtlId, idList);
        }
        catch (Exception e)
        {
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("delete tfmapping failed,reason is" + e + ";ids is " + idsStr);
        }
        finally
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_TASK,
                taskCode,
                "删除映射关系");
        }
        this.setAjax(result);
    }
    
    /**
     * 添加一条映射关系
     * 
     * @param cycle
     * @throws Exception
     */
    public void addCol(IRequestCycle cycle)
        throws Exception
    {
        IDataBus tfInfoBus = createData("colInput");
        JSONObject data = tfInfoBus.getDataObject();
        String CfgTfDtlId = getContext().getParameter("cfgTfDtlId").trim();
        String taskCode = getContext().getParameter("taskCode").trim();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        
        CfgTfMapping mapping = new CfgTfMapping();
        mapping.setCfgTfDtlId(CfgTfDtlId);
        mapping.setSrcColumnName(data.getString("srcColumnName"));
        mapping.setTfColumnName(data.getString("tfColumnName"));
        mapping.setRemarks(data.getString("remark"));
        CfgTfMapping[] mappings = new CfgTfMapping[] {mapping};
        JSONObject result = new JSONObject();
        try
        {
            // 保存映射关系信息前进行校验
            boolean b = XssUtil.checkBeforeSave(mapping, mapping.getClass(), null);
            if (b)
            {
                sv.createCfgTfMapping(taskCode, mappings);
            }
            else
            {
                result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.FAILED);
                result.put(ResultConstants.ResultKey.RESULT_MSG, ResultConstants.ResultCodeValue.CHECK_FAILED);
                
            }
        }
        catch (Exception e)
        {
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
            LOGGER.error("add tfMapping failed,reason is " + e + ";tfCode is " + taskCode);
        }
        finally
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_MODIFY,
                CommonConstants.OPERTATE_OBJECT_TASK,
                taskCode,
                "配置映射关系");
        }
        this.setAjax(result);
        
    }
    
    public abstract void setColRelsItem(JSONObject colRelsItem);
    
    public abstract void setColRelsRowIndex(int colRelsRowIndex);
    
    public abstract void setColInput(JSONObject colInput);
    
    public abstract void setColRelsCount(long colRelsCount);
    
    public abstract void setColRelsItems(JSONArray colRelsItems);
    
    @Override
    protected void initPageAttrs()
    {
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus colRelsItem = createData("colRelsItem");
        if (colRelsItem != null && !colRelsItem.getDataObject().isEmpty())
        {
            setColRelsItem(colRelsItem.getDataObject());
        }
        IDataBus colInput = createData("colInput");
        if (colInput != null && !colInput.getDataObject().isEmpty())
        {
            setColInput(colInput.getDataObject());
        }
        initExtend(cycle);
    }
    
}
