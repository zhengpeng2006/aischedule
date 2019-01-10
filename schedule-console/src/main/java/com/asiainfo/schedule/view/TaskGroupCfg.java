package com.asiainfo.schedule.view;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
import com.asiainfo.schedule.core.po.TaskGroupBean;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class TaskGroupCfg extends AppPage
{
    
    private static final Logger LOGGER = Logger.getLogger(TaskGroupCfg.class);
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 查询分组
     * 
     * @param cycle
     * @throws Exception
     */
    public void qryGroup(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        TaskGroupBean[] beans = sv.getAllTaskGroup();
        String groupCode = getContext().getParameter("groupCode");
        String groupName = getContext().getParameter("groupName");
        
        JSONArray array = new JSONArray();
        if (beans != null)
        {
        	List<TaskGroupBean> beanList = Arrays.asList(beans);
            Collections.sort(beanList, new Comparator<TaskGroupBean>(){
    			@Override
    			public int compare(TaskGroupBean t1, TaskGroupBean t2) {
    				return t1.getGroupName().compareTo(t2.getGroupName());
    			}
            });
            
			for (TaskGroupBean bean : beanList) {
				if(validateGroupIsNotNeeded(groupName, bean.getGroupName())){
					continue;
				}
				if(validateGroupIsNotNeeded(groupCode, bean.getGroupCode())){
					continue;
				}
				
				JSONObject obj = new JSONObject();
				obj.put("groupName", bean.getGroupName());
				obj.put("groupCode", bean.getGroupCode());
				obj.put("groupDesc", bean.getGroupDesc());
				obj.put("createTime", bean.getCreateTime());
				array.add(obj);
			}
			
            
        }
        // this.setGroupInfosItems(array);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupInfosItems", PagingUtil.convertArray2Page(array, offset, linage));
        jsonObject.put("total", array.size());
        this.setAjax(jsonObject);
    }
    
    private boolean validateGroupIsNotNeeded(String neededGroup, String realGroup) {
		return StringUtils.isNotBlank(neededGroup) && !realGroup.toLowerCase().contains(neededGroup.toLowerCase());
		
	}

	/**
     * 保存分组
     * 
     * @param cycle
     * @throws Exception
     */
    public void saveGroup(IRequestCycle cycle)
        throws Exception
    {
        IDataBus bus = createData("groupInfoInput");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        JSONObject obj = bus.getDataObject();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        TaskGroupBean taskGroup = new TaskGroupBean();
        taskGroup.setGroupCode(obj.getString("groupCode"));
        taskGroup.setGroupName(obj.getString("groupName"));
        taskGroup.setGroupDesc(obj.getString("groupDesc"));
        taskGroup.setCreateTime(sdf.format(new Date()));
        JSONObject result = new JSONObject();
        try
        {
            // 保存任务分组信息之前要先进行校验
            boolean b = XssUtil.checkBeforeSave(taskGroup, taskGroup.getClass(), null);
            if (b)
            {
                sv.createTaskGroup(taskGroup);
                result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
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
            LOGGER.error("saveGroup failed,reason is " + e + ";data is " + taskGroup);
        }
        finally
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_ADD,
                CommonConstants.OPERTATE_OBJECT_TASKGROUP,
                taskGroup.getGroupCode(),
                null);
        }
        this.setAjax(result);
    }
    
    /**
     * 选择分组
     * 
     * @param cycle
     * @throws Exception
     */
    public void deleteGroup(IRequestCycle cycle)
        throws Exception
    {
        String taskGroupCode = getContext().getParameter("taskGroupCode").trim();
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        JSONObject result = new JSONObject();
        try
        {
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.SUCCESS);
            
        }
        catch (Exception e)
        {
            LOGGER.error("delete taskGroup failed,reason is " + e + ";groupCode is " + taskGroupCode);
            result.put(ResultConstants.ResultKey.RESULT_CODE, ResultConstants.ResultCodeValue.ERROR);
        }
        finally
        {
            OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                CommonConstants.OPERATE_TYPE_DELETE,
                CommonConstants.OPERTATE_OBJECT_TASKGROUP,
                taskGroupCode,
                null);
            sv.deleteTaskGroup(taskGroupCode);
        }
        this.setAjax(result);
    }
    
    public abstract void setGroupInfosRowIndex(int groupInfosRowIndex);
    
    public abstract void setGroupInfosItem(JSONObject groupInfosItem);
    
    public abstract void setGroupInfosCount(long groupInfosCount);
    
    public abstract void setGroupInfosItems(JSONArray groupInfosItems);
    
    public abstract void setGroupInfoInput(JSONObject groupInfoInput);
    
    @Override
    protected void initPageAttrs()
    {
        
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus groupInfosItem = createData("groupInfosItem");
        if (groupInfosItem != null && !groupInfosItem.getDataObject().isEmpty())
        {
            setGroupInfosItem(groupInfosItem.getDataObject());
        }
        IDataBus groupInfoInput = createData("groupInfoInput");
        if (groupInfoInput != null && !groupInfoInput.getDataObject().isEmpty())
        {
            setGroupInfoInput(groupInfoInput.getDataObject());
        }
        initExtend(cycle);
    }
    
}
