package com.asiainfo.schedule.view;

import java.util.HashMap;
import java.util.Map;

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
import com.asiainfo.monitor.busi.service.interfaces.IAIMonNodeSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonPhysicHostSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonServerSV;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonNodeValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonPhysicHostValue;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonServerValue;
import com.asiainfo.monitor.exeframe.configmgr.util.ConfigPageUtil;
import com.asiainfo.monitor.service.ExternalSvUtil;
import com.asiainfo.schedule.service.interfaces.ISchedulerSV;
import com.asiainfo.schedule.util.ScheduleUtil;

/**
 * Created by SMT Copyright: Copyright(c) 2014 Asiainfo-Linkage
 */
public abstract class SplitCfg extends AppPage
{
    
    private static final Logger LOGGER = Logger.getLogger(SplitCfg.class);
    
    /** 可用主机map */
    private Map<String, String> hostMap = null;
    
    private void initExtend(IRequestCycle cycle)
    {
        
    }
    
    private void initExtend()
    {
        
    }
    
    /**
     * 查询分片详细信息
     * 
     * @param cycle
     * @throws Exception
     */
    public void qrySplit(IRequestCycle cycle)
        throws Exception
    {
        // 分页使用
        String offset = getContext().getParameter("offset");
        String linage = getContext().getParameter("linage");
        
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        String taskCode = getContext().getParameter("taskCode").trim();
        JSONArray array = new JSONArray();
        JSONObject result = new JSONObject();
        String[] items = sv.getTaskItemsByTaskCode(taskCode);
        items = ScheduleUtil.sortItemsByIntger(items);
        if (items != null)
        {
            Map<String, Long> serverhostMap = ExternalSvUtil.getServerHosts();
            for (String item : items)
            {
                JSONObject obj = new JSONObject();
                obj.put("taskCode", taskCode);
                obj.put("taskItem", item);
                String serverCode = sv.getServerCode(taskCode, item);
                obj.put("assignServer", serverCode);
                obj.put("host", serverhostMap.get(serverCode));
                array.add(obj);
            }
            result.put("flag", "T");
        }
        JSONObject map = ExternalSvUtil.getServerHostsMap();
        result.put("array", map);
        // this.setSplitInfosItems(array);
        result.put("splitInfosItems", PagingUtil.convertArray2Page(array, offset, linage));
        result.put("splitInfosTotal", array.size());
        this.setAjax(result);
    }
    
    /**
     * 保存分片配置 数据是包含 任务拆分项、对应任务编码和指定应用编码 的对象集合转换的json字符串
     * 
     * @param cycle
     * @throws Exception
     */
    public void saveSplits(IRequestCycle cycle)
        throws Exception
    {
        ISchedulerSV sv = (ISchedulerSV)ServiceFactory.getService(ISchedulerSV.class);
        String dataArray = this.getContext().getParameter("dataArray");
        // 将传过来的json字符串解析后处理
        JSONArray array = JSONArray.fromObject(dataArray);
        if (null != array)
        {
            // 记录操作日志用
            StringBuffer sb = new StringBuffer();
            try
            {
                for (Object object : array)
                {
                    JSONObject obj = JSONObject.fromObject(object);
                    
                    sb.append(obj.getString("taskCode").trim())
                        .append("^")
                        .append(obj.getString("taskItem").trim())
                        .append(";");
                    
                    sv.assignServer2TaskItem(obj.getString("taskCode").trim(),
                        obj.getString("taskItem").trim(),
                        obj.getString("serverCode").trim());
                }
            }
            catch (Exception e)
            {
                LOGGER.error("saveSplits failed", e);
            }
            finally
            {
                OperationLogUtils.addFrontEndLog(CommonConstants.OPERATE_MODULE_CONFIG,
                    CommonConstants.OPERATE_TYPE_MODIFY,
                    CommonConstants.OPERTATE_OBJECT_TASK,
                    sb.toString(),
                    "给分片制定应用");
            }
        }
    }
    
    /**
     * 根据主机获取相关APPCODE
     * 
     * @param cycle
     * @throws Exception
     */
    public void getAppcodeList(IRequestCycle cycle)
        throws Exception
    {
        String hostIdStr = getContext().getParameter("hostId").trim();
        JSONArray array = new JSONArray();
        if (StringUtils.isNotBlank(hostIdStr))
        {
            IAIMonNodeSV nodeSv = (IAIMonNodeSV)ServiceFactory.getService(IAIMonNodeSV.class);
            IAIMonServerSV serverSv = (IAIMonServerSV)ServiceFactory.getService(IAIMonServerSV.class);
            IBOAIMonNodeValue[] nodes = nodeSv.qryNodeInfo(hostIdStr);
            if (nodes != null)
            {
                for (IBOAIMonNodeValue node : nodes)
                {
                    IBOAIMonServerValue[] servers = serverSv.qryServerInfo(node.getNodeId() + "");
                    if (null != servers)
                    {
                        for (IBOAIMonServerValue server : servers)
                        {
                            JSONObject obj = new JSONObject();
                            obj.put("appCode", server.getServerCode());
                            array.add(obj);
                        }
                    }
                }
            }
        }
        else
        {
            LOGGER.error("get AppCode failed,hostId is null");
        }
        this.setAjax(array);
    }
    
    /**
     * 用于初始化页面应用编码下拉菜单
     * 
     * @param cycle
     * @throws Exception
     */
    public void initAppcodeList(IRequestCycle cycle)
        throws Exception
    {
        
    }
    
    /**
     * 获取可用服务器下拉菜单
     * 
     * @return
     */
    public IDataBus getHostList()
    {
        Map<String, String> hostMap = initServerMap();
        IDataBus bus = ConfigPageUtil.getSelectList(hostMap);
        return bus;
    }
    
    /**
     * 初始化可用服务器map
     */
    private Map<String, String> initServerMap()
    {
        Map<String, String> hostMap = new HashMap<String, String>();
        // 查询所有可用服务器
        try
        {
            IAIMonPhysicHostSV sv = (IAIMonPhysicHostSV)ServiceFactory.getService(IAIMonPhysicHostSV.class);
            IBOAIMonPhysicHostValue[] hosts = sv.getAllPhostBean();
            // 去除缓存
            // List<IPhysicHost> hosts = sv.getAllPhost();
            if (null != hosts && hosts.length > 0)
            {
                for (IBOAIMonPhysicHostValue host : hosts)
                {
                    hostMap.put(host.getHostId() + "", host.getHostName() + "(" + host.getHostIp() + ")");
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("get host failed,reason is " + e);
        }
        return hostMap;
    }
    
    public abstract void setSplitInfosRowIndex(int splitInfosRowIndex);
    
    public abstract void setSplitInfosCount(long splitInfosCount);
    
    public abstract void setSplitInfosItems(JSONArray splitInfosItems);
    
    public abstract void setSplitInfosItem(JSONObject splitInfosItem);
    
    @Override
    protected void initPageAttrs()
    {
        initExtend();
    }
    
    public void initPage(IRequestCycle cycle)
    {
        IDataBus splitInfosItem = createData("splitInfosItem");
        if (splitInfosItem != null && !splitInfosItem.getDataObject().isEmpty())
        {
            setSplitInfosItem(splitInfosItem.getDataObject());
        }
        initExtend(cycle);
    }
    
}
