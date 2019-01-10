package com.asiainfo.monitor.exeframe.monitorItem.view;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.IRequestCycle;

import com.ai.appframe2.service.ServiceFactory;
import com.ailk.appengine.web.view.AppPage;
import com.ailk.common.data.IDataBus;
import com.asiainfo.common.utils.PagingUtil;
import com.asiainfo.monitor.acquire.service.interfaces.IProcessStatusSV;
import com.asiainfo.schedule.cache.TaskViewCache;
import com.asiainfo.schedule.core.utils.Constants.KeyCodes;
import com.asiainfo.socket.pool.ConnectedSocketPool;
import com.asiainfo.socket.pool.SocketConfig;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class WarningInfo extends AppPage
{
    
    private static transient Log LOG = LogFactory.getLog(WarningInfo.class);
    
    // 判断主机是否联通
    public static boolean pingIp(String hostIp)
    {
        // 去连接池中获取连接
        String port = String.valueOf(SocketConfig.getMonitorPort());
        if (ConnectedSocketPool.getChannel(hostIp, port) == null)
        {
            LOG.error("channel of [" + hostIp + ":" + port + "] not exist in pool.");
            return false;
        }
        
        LOG.debug("channel of [" + hostIp + ":" + port + "] exist in pool.");
        return true;
    }
    
    /**
     * 查询数据
     * 
     * @param request
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void loadTableData(IRequestCycle request)
        throws Exception
    {
        IProcessStatusSV processSv = (IProcessStatusSV)ServiceFactory.getService(IProcessStatusSV.class);
        this.setAjax(processSv.handle());
    }
    
    public void loadTaskInfo(IRequestCycle request)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        JSONArray jsonArr = new JSONArray();
        String serverCode = getContext().getParameter("serverCode");
        
        TaskViewCache cache = TaskViewCache.getInstance();
        List<Map<String, String>> taskViews = cache.getData().get(serverCode);
        if (taskViews != null)
        {
            for (Map<String, String> map : taskViews)
            {
                JSONObject obj = new JSONObject();
                obj.put("taskCode", map.get(KeyCodes.TaskCode.name()));
                obj.put("item", map.get(KeyCodes.TaskItemId.name()));
                obj.put("taskType", map.get(KeyCodes.TaskType.name()));
                obj.put("createTime", map.get(KeyCodes.CreateTime.name()));
                obj.put("state", map.get(KeyCodes.TaskRunSts.name()));
                obj.put("taskJobId", map.get(KeyCodes.TaskJobId.name()));
                obj.put("taskName", map.get(KeyCodes.TaskName.name()));
                jsonArr.add(obj);
            }
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskInfos", PagingUtil.convertArray2Page(jsonArr, offset, linage));
        jsonObject.put("total", jsonArr.size());
        this.setAjax(jsonObject);
    }
    
    /**
     * 校验版本一致性 页面版本和后台版本一致则返回T，否则返回F
     * 
     * @param request
     * @throws Exception
     */
    public void checkVersion(IRequestCycle request)
        throws Exception
    {
        String version = getContext().getParameter("version");
        JSONObject result = new JSONObject();
        long curVersion = TaskViewCache.getInstance().getCurVersion();
        if (StringUtils.isNotBlank(version) && curVersion == Long.parseLong(version))
        {
            result.put("flag", "T");
        }
        else
        {
            result.put("flag", "F");
        }
        this.setAjax(result);
    }
    
    public static void main(String[] args)
        throws Exception
    {
    }
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    public abstract void setTaskTabCount(long taskTabCount);
    
    public abstract void setTaskTabItem(JSONObject taskTabItem);
    
    public abstract void setTaskTabItems(JSONArray taskTabItems);
    
    public abstract void setTaskTabRowIndex(int taskTabRowIndex);
    
    @Override
    protected void initPageAttrs()
    {
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus taskTabItem = createData("taskTabItem");
        if (taskTabItem != null && !taskTabItem.getDataObject().isEmpty())
        {
            setTaskTabItem(taskTabItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
